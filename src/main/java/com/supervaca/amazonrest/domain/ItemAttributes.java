package com.supervaca.amazonrest.domain;

import java.util.List;

public class ItemAttributes {
	private String title;
	private String upc;
	private Price listPrice;
	private Price tradeInValue;
	private List<String> features;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public Price getListPrice() {
		return listPrice;
	}

	public void setListPrice(Price listPrice) {
		this.listPrice = listPrice;
	}

	public Price getTradeInValue() {
		return tradeInValue;
	}

	public void setTradeInValue(Price tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemAttributes [title=").append(title).append(", upc=").append(upc).append(", listPrice=").append(listPrice).append(", tradeInValue=").append(tradeInValue).append(
				", features=").append(features).append("]");
		return builder.toString();
	}
}
