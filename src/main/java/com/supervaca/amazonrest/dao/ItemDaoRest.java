package com.supervaca.amazonrest.dao;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.supervaca.amazonrest.SignedRequestsHelper;
import com.supervaca.amazonrest.domain.Item;
import com.supervaca.amazonrest.domain.Merchant;
import com.supervaca.amazonrest.domain.Offer;
import com.supervaca.amazonrest.domain.OfferListing;
import com.supervaca.amazonrest.domain.Offers;
import com.supervaca.amazonrest.domain.Price;

public class ItemDaoRest implements ItemDao {
	private static final Logger logger = LoggerFactory.getLogger(ItemDaoRest.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SignedRequestsHelper signedRequestsHelper;

	@Override
	public List<Item> getItems(List<String> asins) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItems(List<String> asins, List<String> responseGroups) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item lookup(String asin) {
		Item item = new Item();

		boolean amazonOnly = true;
		Map<String, String> params = new TreeMap<String, String>();
		params.put("IdType", "ASIN");
		params.put("Condition", "All");
		params.put("Version", "2009-11-01");
		params.put("Service", "AWSECommerceService");
		params.put("MerchantId", amazonOnly ? "Amazon" : "All");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", asin);
		params.put("ResponseGroup", "ItemAttributes,Offers,OfferSummary,Images");
		String url = signedRequestsHelper.sign(params);

		StreamSource xmlStream = submitRequest(url);

		long start = System.currentTimeMillis();
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		XMLEventReader eventReader;
		try {
			eventReader = inputFactory.createXMLEventReader(xmlStream);
			// Read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (isStartElementEqual(event, "Item")) {
					logger.debug(event.toString());
				}

				if (isStartElementEqual(event, "ASIN")) {
					event = eventReader.nextEvent();
					item.setAsin(event.asCharacters().getData());
					continue;
				}

				if (isStartElementEqual(event, "DetailPageURL")) {
					event = eventReader.nextEvent();
					item.setDetailPageURL(event.asCharacters().getData());
					continue;
				}

				if (isStartElementEqual(event, "Offers")) {
					Offers offers = new Offers();
					while (eventReader.hasNext() && !(isEndElementEqual(eventReader.peek(), "Offers"))) {
						event = eventReader.nextEvent();

						if (isStartElementEqual(event, "TotalOffers")) {
							event = eventReader.nextEvent();
							offers.setTotalOffers(Integer.valueOf(event.asCharacters().getData()));
							continue;
						}

						if (isStartElementEqual(event, "TotalOfferPages")) {
							event = eventReader.nextEvent();
							offers.setTotalOfferPages(Integer.valueOf(event.asCharacters().getData()));
							continue;
						}

						if (isStartElementEqual(event, "Offer")) {
							Offer offer = new Offer();
							while (eventReader.hasNext() && !(isEndElementEqual(eventReader.peek(), "Offer"))) {
								event = eventReader.nextEvent();

								if (isStartElementEqual(event, "Merchant")) {
									Merchant merchant = new Merchant();
									while (eventReader.hasNext() && !(isEndElementEqual(eventReader.peek(), "Merchant"))) {
										event = eventReader.nextEvent();
										if (isStartElementEqual(event, "MerchantId")) {
											event = eventReader.nextEvent();
											merchant.setMerchantId(event.asCharacters().getData());
											continue;
										}

										if (isStartElementEqual(event, "GlancePage")) {
											event = eventReader.nextEvent();
											merchant.setGlancePage(event.asCharacters().getData());
											continue;
										}
									}
									offer.setMerchant(merchant);
								}

								if (isStartElementEqual(event, "OfferListing")) {
									OfferListing offerListing = new OfferListing();
									while (eventReader.hasNext() && !(isEndElementEqual(eventReader.peek(), "OfferListing"))) {
										event = eventReader.nextEvent();
										if (isStartElementEqual(event, "OfferListingId")) {
											event = eventReader.nextEvent();
											offerListing.setOfferListingId(event.asCharacters().getData());
											continue;
										}

										if (isStartElementEqual(event, "Price")) {
											Price price = new Price();
											while (eventReader.hasNext() && !(isEndElementEqual(eventReader.peek(), "Price"))) {
												event = eventReader.nextEvent();

												if (isStartElementEqual(event, "Amount")) {
													event = eventReader.nextEvent();
													Long amount = Long.valueOf(event.asCharacters().getData());
													price.setAmount(amount);
													price.setBdAmount(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100)));
													continue;
												}

												if (isStartElementEqual(event, "CurrencyCode")) {
													event = eventReader.nextEvent();
													price.setCurrencyCode(event.asCharacters().getData());
													continue;
												}

												if (isStartElementEqual(event, "FormattedPrice")) {
													event = eventReader.nextEvent();
													price.setFormattedPrice(event.asCharacters().getData());
													continue;
												}
											}
											offerListing.setPrice(price);
										}
									}
									offer.setOfferListing(offerListing);
								}
							}
							offers.getOffers().add(offer);
						}
					}
					item.setOffers(offers);
				}

				// If we reach the end of an item element we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals("Item")) {
						break;
					}
				}
			}
		} catch (XMLStreamException e) {
			logger.error("XMLStream error", e);
		}
		long end = System.currentTimeMillis();

		logger.debug("Parsing took {} millis", end - start);

		return item;
	}

	private boolean isStartElementEqual(XMLEvent event, String startElement) {
		return event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(startElement);
	}

	private boolean isEndElementEqual(XMLEvent event, String endElement) {
		return event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(endElement);
	}

	private StreamSource submitRequest(String url) {
		StreamSource stream = null;
		try {
			stream = restTemplate.getForObject(new URI(url), StreamSource.class);
		} catch (URISyntaxException e) {
			logger.error("Error converting string to URL", e);
		}

		return stream;
	}

	@Override
	public Item lookup(String asin, List<String> responseGroups) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item lookup(String asin, List<String> responseGroups, Boolean amazonOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(String keyword, List<String> responseGroups) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(String keyword, String searchIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(String keywords, List<String> responseGroups, String searchIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> searchItems(String keywords, List<String> responseGroups, String searchIndex, Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
