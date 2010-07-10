package com.supervaca.amazonrest.domain;

public class OfferListing {
	private String offerListingId;
	private Price price;
	private AmountSaved amountSaved;
	private int percentageSaved;
	private String availability;

	public String getOfferListingId() {
		return offerListingId;
	}

	public void setOfferListingId(String offerListingId) {
		this.offerListingId = offerListingId;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public AmountSaved getAmountSaved() {
		return amountSaved;
	}

	public void setAmountSaved(AmountSaved amountSaved) {
		this.amountSaved = amountSaved;
	}

	public int getPercentageSaved() {
		return percentageSaved;
	}

	public void setPercentageSaved(int percentageSaved) {
		this.percentageSaved = percentageSaved;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OfferListing [amountSaved=").append(amountSaved).append(", availability=").append(availability).append(", offerListingId=").append(offerListingId).append(", percentageSaved=")
				.append(percentageSaved).append(", price=").append(price).append("]");
		return builder.toString();
	}
}
