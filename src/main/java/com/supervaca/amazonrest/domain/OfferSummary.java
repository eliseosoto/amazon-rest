package com.supervaca.amazonrest.domain;

public class OfferSummary {
	private Price lowestNewPrice;
	private Price lowestUsedPrice;
	private Price lowestCollectiblePrice;
	private int totalNew;
	private int totalUsed;
	private int totalCollectible;
	private int totalRefurbished;

	public Price getLowestNewPrice() {
		return lowestNewPrice;
	}

	public void setLowestNewPrice(Price lowestNewPrice) {
		this.lowestNewPrice = lowestNewPrice;
	}

	public Price getLowestUsedPrice() {
		return lowestUsedPrice;
	}

	public void setLowestUsedPrice(Price lowestUsedPrice) {
		this.lowestUsedPrice = lowestUsedPrice;
	}

	public Price getLowestCollectiblePrice() {
		return lowestCollectiblePrice;
	}

	public void setLowestCollectiblePrice(Price lowestCollectiblePrice) {
		this.lowestCollectiblePrice = lowestCollectiblePrice;
	}

	public int getTotalNew() {
		return totalNew;
	}

	public void setTotalNew(int totalNew) {
		this.totalNew = totalNew;
	}

	public int getTotalUsed() {
		return totalUsed;
	}

	public void setTotalUsed(int totalUsed) {
		this.totalUsed = totalUsed;
	}

	public int getTotalCollectible() {
		return totalCollectible;
	}

	public void setTotalCollectible(int totalCollectible) {
		this.totalCollectible = totalCollectible;
	}

	public int getTotalRefurbished() {
		return totalRefurbished;
	}

	public void setTotalRefurbished(int totalRefurbished) {
		this.totalRefurbished = totalRefurbished;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OfferSummary [lowestNewPrice=").append(lowestNewPrice).append(", lowestUsedPrice=").append(lowestUsedPrice).append(", lowestCollectiblePrice=").append(lowestCollectiblePrice)
				.append(", totalNew=").append(totalNew).append(", totalUsed=").append(totalUsed).append(", totalCollectible=").append(totalCollectible).append(", totalRefurbished=").append(
						totalRefurbished).append("]");
		return builder.toString();
	}
}
