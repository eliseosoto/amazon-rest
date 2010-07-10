package com.supervaca.amazonrest.domain;

import java.util.ArrayList;
import java.util.List;

public class Offers {
	private int totalOffers;
	private int totalOfferPages;
	private List<Offer> offers =  new ArrayList<Offer>();

	public int getTotalOffers() {
		return totalOffers;
	}

	public void setTotalOffers(int totalOffers) {
		this.totalOffers = totalOffers;
	}

	public int getTotalOfferPages() {
		return totalOfferPages;
	}

	public void setTotalOfferPages(int totalOfferPages) {
		this.totalOfferPages = totalOfferPages;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Offers [offers=").append(offers).append(", totalOfferPages=").append(totalOfferPages).append(", totalOffers=").append(totalOffers).append("]");
		return builder.toString();
	}
}
