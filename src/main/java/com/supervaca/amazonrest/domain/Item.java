package com.supervaca.amazonrest.domain;

public class Item {
	private String asin;
	private String detailPageURL;
	private ItemAttributes itemAttributes;
	private OfferSummary offerSummary;
	private Offers offers;
	private Image smallImage;
	private Image mediumImage;
	private Image largeImage;

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

	public ItemAttributes getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(ItemAttributes itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public OfferSummary getOfferSummary() {
		return offerSummary;
	}

	public void setOfferSummary(OfferSummary offerSummary) {
		this.offerSummary = offerSummary;
	}

	public Offers getOffers() {
		return offers;
	}

	public void setOffers(Offers offers) {
		this.offers = offers;
	}

	public Image getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(Image smallImage) {
		this.smallImage = smallImage;
	}

	public Image getMediumImage() {
		return mediumImage;
	}

	public void setMediumImage(Image mediumImage) {
		this.mediumImage = mediumImage;
	}

	public Image getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(Image largeImage) {
		this.largeImage = largeImage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [asin=").append(asin).append(", detailPageURL=").append(detailPageURL).append(", itemAttributes=")
				.append(itemAttributes).append(", offerSummary=").append(offerSummary).append(", offers=").append(offers).append(", smallImage=")
				.append(smallImage).append(", mediumImage=").append(mediumImage).append(", largeImage=").append(largeImage).append("]");
		return builder.toString();
	}
}
