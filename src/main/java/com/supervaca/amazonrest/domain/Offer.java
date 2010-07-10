package com.supervaca.amazonrest.domain;

public class Offer {
	private Merchant merchant;
	private OfferAttributes offerAttributes;
	private OfferListing offerListing;

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public OfferAttributes getOfferAttributes() {
		return offerAttributes;
	}

	public void setOfferAttributes(OfferAttributes offerAttributes) {
		this.offerAttributes = offerAttributes;
	}

	public OfferListing getOfferListing() {
		return offerListing;
	}

	public void setOfferListing(OfferListing offerListing) {
		this.offerListing = offerListing;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Offer [merchant=").append(merchant).append(", offerAttributes=").append(offerAttributes).append(", offerListing=").append(offerListing).append("]");
		return builder.toString();
	}
}
