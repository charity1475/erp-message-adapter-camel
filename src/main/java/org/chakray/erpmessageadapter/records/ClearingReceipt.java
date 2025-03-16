package org.chakray.erpmessageadapter.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClearingReceipt{
	@JsonProperty("transactionId")
	private String transactionId;
	@JsonProperty("customerName")
	private String customerName;
	@JsonProperty("customerId")
	private String customerId;
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("date")
	private String date;
	@JsonProperty("paymentMethod")
	private String paymentMethod;

	public ClearingReceipt() {
	}

	public ClearingReceipt(String transactionId, String customerName, String customerId, BigDecimal amount, String currency, String date, String paymentMethod) {
		this.transactionId = transactionId;
		this.customerName = customerName;
		this.customerId = customerId;
		this.amount = amount;
		this.currency = currency;
		this.date = date;
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}