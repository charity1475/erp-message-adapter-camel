package org.chakray.erpmessageadapter.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.chakray.erpmessageadapter.processors.ReceiptsProcessor;
import org.chakray.erpmessageadapter.records.ClearingReceipt;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

@Component
public class MessageTranslatorRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {

		/*
		* Start by configuring your rest component and listening port
		* */
		restConfiguration().component("servlet").host("0.0.0.0")
				.port("8080").contextPath("/erp").enableCORS(true);

		/*
		* Handle validation exceptions
		* */
		onException(JsonValidationException.class).handled(true)
				.log(LoggingLevel.ERROR, "Validation Error : ${exception.message}")
				.setHeader("Content-Type", constant("application/json"))
				.process(exchange -> {
					Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
					JSONObject errorResponse = new JSONObject();
					errorResponse.put("error", "Invalid payload");
					errorResponse.put("message", caused.getMessage().toString());
					exchange.getIn().setBody(errorResponse);
				}).setBody(simple("${body}"));

		/*
		* Handle exception to be returned by this route
		* */
		onException(Exception.class).handled(true)
				.log(LoggingLevel.ERROR, "Server Error : ${exception.message}")
				.setHeader("Content-Type", constant("application/json"))
				.setHeader("CamelHttpResponseCode", constant("500"))
				.process(exchange -> {
					Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
					JSONObject errorResponse = new JSONObject();
					errorResponse.put("error", "Internal Server Error");
					errorResponse.put("message", caused.getMessage().toString());
					exchange.getIn().setBody(errorResponse);
				}).setBody(simple("${body}"));

		/*
		*  Define the route that will be listening for incoming messages
		* */
		rest("receipts/1.0.0")
				.produces("application/json")
				.post("/posting").type(ClearingReceipt.class)
				.to("direct:translate-clearing-to-erp-receipt-route");


		/*
		* Validate JSON schema and Translate the message to a format compatible with ERP
		* */
		from("direct:translate-clearing-to-erp-receipt-route")
				.routeId("com.chakray.routes.translate-clearing-to-erp-receipt-route")
				.log(LoggingLevel.INFO, "Translating Receipt: ${body}")
				.to("json-validator:classpath:schemas/clearing-receipt-schema.json")
				.unmarshal().json(ClearingReceipt.class)
				.process(new ReceiptsProcessor())
				.setHeader(HTTP_RESPONSE_CODE, constant(200))
				.setHeader("Content-Type", constant("application/json"))
				.setBody(simple("${body}"));
	}
}
