package org.chakray.erpmessageadapter.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.chakray.erpmessageadapter.records.ClearingReceipt;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReceiptsProcessor implements Processor {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		ClearingReceipt clearingReceipt = exchange.getIn().getBody(ClearingReceipt.class);
		if (clearingReceipt == null) {
			throw new IllegalArgumentException("ClearingReceipt is null");
		}
		logger.info("Processing Receipt # {}", clearingReceipt.getTransactionId());

		JSONObject erpReceipt = new JSONObject();

		erpReceipt.put("ERPTransaction", new JSONObject()
				.put("RefNo", clearingReceipt.getTransactionId())
				.put("ClientDetails", new JSONObject()
						.put("FullName", clearingReceipt.getCustomerName())
						.put("CustomerID", clearingReceipt.getCustomerId()))
				.put("Financials", new JSONObject()
						.put("TotalAmount", clearingReceipt.getAmount())
						.put("CurrencyCode", clearingReceipt.getCurrency())
						.put("PaymentMode", clearingReceipt.getPaymentMethod()))
				.put("Timestamp", clearingReceipt.getDate()));
		logger.info("Processed Receipt :  {}", erpReceipt);
		exchange.getIn().setBody(erpReceipt);
	}
}
