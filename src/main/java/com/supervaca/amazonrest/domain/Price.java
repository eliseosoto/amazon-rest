package com.supervaca.amazonrest.domain;

import java.math.BigDecimal;

public class Price {
	private long amount;
	private String currencyCode;
	private String formattedPrice;
	private BigDecimal bdAmount;

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public BigDecimal getBdAmount() {
		return bdAmount;
	}

	public void setBdAmount(BigDecimal bdAmount) {
		this.bdAmount = bdAmount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Price [amount=").append(amount).append(", bdAmount=").append(bdAmount).append(", currencyCode=").append(currencyCode).append(", formattedPrice=").append(formattedPrice)
				.append("]");
		return builder.toString();
	}
}
