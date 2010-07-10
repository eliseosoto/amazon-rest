package com.supervaca.amazonrest.domain;


public class Item {
	private String asin;
	private String detailPageURL;

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getDetailPageURL() {
		return detailPageURL;
	}

	public void setDetailPageURL(String detailPageURL) {
		this.detailPageURL = detailPageURL;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [asin=").append(asin).append(", detailPageURL=").append(detailPageURL).append("]");
		return builder.toString();
	}
}
