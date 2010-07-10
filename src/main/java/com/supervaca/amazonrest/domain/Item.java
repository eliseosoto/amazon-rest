package com.supervaca.amazonrest.domain;


public class Item {
	private String asin;
	private String detailPageURL;
	private Offers offers;

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

	public Offers getOffers() {
		return offers;
	}

	public void setOffers(Offers offers) {
		this.offers = offers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [asin=").append(asin).append(", detailPageURL=").append(detailPageURL).append(", offers=").append(offers).append("]");
		return builder.toString();
	}


	
}
