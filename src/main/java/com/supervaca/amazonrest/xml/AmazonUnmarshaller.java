package com.supervaca.amazonrest.xml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import com.supervaca.amazonrest.domain.Item;
import com.supervaca.amazonrest.domain.ItemAttributes;
import com.supervaca.amazonrest.domain.Merchant;
import com.supervaca.amazonrest.domain.Offer;
import com.supervaca.amazonrest.domain.OfferListing;
import com.supervaca.amazonrest.domain.OfferSummary;
import com.supervaca.amazonrest.domain.Offers;
import com.supervaca.amazonrest.domain.Price;

public class AmazonUnmarshaller {
	public static boolean isStartElementEqual(XMLEvent event, String startElement) {
		return event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(startElement);
	}

	public static boolean isEndElementEqual(XMLEvent event, String endElement) {
		return event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(endElement);
	}

	public static Item unmarshalItemLookup(StreamSource xmlStream) throws FactoryConfigurationError, XMLStreamException {
		XMLEventReader eventReader;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(xmlStream);
		Item retVal = null;
		// Read the XML document
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(event, "Item")) {
				retVal = AmazonUnmarshaller.unmarshalItem(eventReader);
				break;
			}
		}
		return retVal;
	}

	public static Item unmarshalItem(XMLEventReader eventReader) throws XMLStreamException {
		Item retVal;
		Item item = new Item();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "Item"))) {
			XMLEvent itemEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(itemEvent, "ASIN")) {
				itemEvent = eventReader.nextEvent();
				item.setAsin(itemEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemEvent, "DetailPageURL")) {
				itemEvent = eventReader.nextEvent();
				item.setDetailPageURL(itemEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemEvent, "ItemAttributes")) {
				ItemAttributes itemAttributes = AmazonUnmarshaller.unmarshalItemAttributes(eventReader);
				;
				item.setItemAttributes(itemAttributes);
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemEvent, "OfferSummary")) {
				OfferSummary offerSummary = AmazonUnmarshaller.unmarshalOfferSummary(eventReader);
				item.setOfferSummary(offerSummary);
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemEvent, "Offers")) {
				Offers offers = AmazonUnmarshaller.unmarshalOffers(eventReader);
				item.setOffers(offers);
			}
		}
		retVal = item;
		return retVal;
	}

	public static ItemAttributes unmarshalItemAttributes(XMLEventReader eventReader) throws XMLStreamException {
		ItemAttributes itemAttributes = new ItemAttributes();
		List<String> features = new ArrayList<String>();
		itemAttributes.setFeatures(features);
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "ItemAttributes"))) {
			XMLEvent itemAttributesEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(itemAttributesEvent, "Title")) {
				itemAttributesEvent = eventReader.nextEvent();
				itemAttributes.setTitle(itemAttributesEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemAttributesEvent, "UPC")) {
				itemAttributesEvent = eventReader.nextEvent();
				itemAttributes.setUpc(itemAttributesEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemAttributesEvent, "ListPrice")) {
				Price listPrice = AmazonUnmarshaller.unmarshalPrice(eventReader, "ListPrice");
				itemAttributes.setListPrice(listPrice);
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemAttributesEvent, "TradeInValue")) {
				Price tradeInValue = AmazonUnmarshaller.unmarshalPrice(eventReader, "TradeInValue");
				itemAttributes.setTradeInValue(tradeInValue);
			}

			if (AmazonUnmarshaller.isStartElementEqual(itemAttributesEvent, "Feature")) {
				itemAttributesEvent = eventReader.nextEvent();
				features.add(itemAttributesEvent.asCharacters().getData());
				continue;
			}
		}
		return itemAttributes;
	}

	public static OfferSummary unmarshalOfferSummary(XMLEventReader eventReader) throws XMLStreamException {
		OfferSummary offerSummary = new OfferSummary();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "OfferSummary"))) {
			XMLEvent offerSummaryEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "LowestNewPrice")) {
				Price lowestNewPrice = AmazonUnmarshaller.unmarshalPrice(eventReader, "LowestNewPrice");
				offerSummary.setLowestNewPrice(lowestNewPrice);
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "LowestUsedPrice")) {
				Price lowestUsedPrice = AmazonUnmarshaller.unmarshalPrice(eventReader, "LowestUsedPrice");
				offerSummary.setLowestUsedPrice(lowestUsedPrice);
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "LowestCollectiblePrice")) {
				Price lowestCollectiblePrice = AmazonUnmarshaller.unmarshalPrice(eventReader, "LowestCollectiblePrice");
				offerSummary.setLowestCollectiblePrice(lowestCollectiblePrice);
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "TotalNew")) {
				offerSummaryEvent = eventReader.nextEvent();
				offerSummary.setTotalNew(Integer.valueOf(offerSummaryEvent.asCharacters().getData()));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "TotalUsed")) {
				offerSummaryEvent = eventReader.nextEvent();
				offerSummary.setTotalUsed(Integer.valueOf(offerSummaryEvent.asCharacters().getData()));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "TotalCollectible")) {
				offerSummaryEvent = eventReader.nextEvent();
				offerSummary.setTotalCollectible(Integer.valueOf(offerSummaryEvent.asCharacters().getData()));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerSummaryEvent, "TotalRefurbished")) {
				offerSummaryEvent = eventReader.nextEvent();
				offerSummary.setTotalRefurbished(Integer.valueOf(offerSummaryEvent.asCharacters().getData()));
				continue;
			}
		}
		return offerSummary;
	}

	public static Offers unmarshalOffers(XMLEventReader eventReader) throws XMLStreamException {
		Offers offers = new Offers();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "Offers"))) {
			XMLEvent offersEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(offersEvent, "TotalOffers")) {
				offersEvent = eventReader.nextEvent();
				offers.setTotalOffers(Integer.valueOf(offersEvent.asCharacters().getData()));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offersEvent, "TotalOfferPages")) {
				offersEvent = eventReader.nextEvent();
				offers.setTotalOfferPages(Integer.valueOf(offersEvent.asCharacters().getData()));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offersEvent, "Offer")) {
				Offer offer = AmazonUnmarshaller.unmarshalOffer(eventReader);
				offers.getOffers().add(offer);
			}
		}
		return offers;
	}

	public static OfferListing unmarshalOfferListing(XMLEventReader eventReader) throws XMLStreamException {
		OfferListing offerListing = new OfferListing();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "OfferListing"))) {
			XMLEvent offerListingEvent = eventReader.nextEvent();
			if (AmazonUnmarshaller.isStartElementEqual(offerListingEvent, "OfferListingId")) {
				offerListingEvent = eventReader.nextEvent();
				offerListing.setOfferListingId(offerListingEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerListingEvent, "Price")) {
				Price price = AmazonUnmarshaller.unmarshalPrice(eventReader, "Price");
				offerListing.setPrice(price);
			}
		}
		return offerListing;
	}

	public static Offer unmarshalOffer(XMLEventReader eventReader) throws XMLStreamException {
		Offer offer = new Offer();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "Offer"))) {
			XMLEvent offerEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(offerEvent, "Merchant")) {
				Merchant merchant = AmazonUnmarshaller.unmarshalMerchant(eventReader);
				offer.setMerchant(merchant);
			}

			if (AmazonUnmarshaller.isStartElementEqual(offerEvent, "OfferListing")) {
				OfferListing offerListing = AmazonUnmarshaller.unmarshalOfferListing(eventReader);
				offer.setOfferListing(offerListing);
			}
		}
		return offer;
	}

	public static Merchant unmarshalMerchant(XMLEventReader eventReader) throws XMLStreamException {
		Merchant merchant = new Merchant();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), "Merchant"))) {
			XMLEvent merchantEvent = eventReader.nextEvent();
			if (AmazonUnmarshaller.isStartElementEqual(merchantEvent, "MerchantId")) {
				merchantEvent = eventReader.nextEvent();
				merchant.setMerchantId(merchantEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(merchantEvent, "GlancePage")) {
				merchantEvent = eventReader.nextEvent();
				merchant.setGlancePage(merchantEvent.asCharacters().getData());
				continue;
			}
		}
		return merchant;
	}

	public static Price unmarshalPrice(XMLEventReader eventReader, String priceName) throws XMLStreamException {
		Price price = new Price();
		while (eventReader.hasNext() && !(AmazonUnmarshaller.isEndElementEqual(eventReader.peek(), priceName))) {
			XMLEvent priceEvent = eventReader.nextEvent();

			if (AmazonUnmarshaller.isStartElementEqual(priceEvent, "Amount")) {
				priceEvent = eventReader.nextEvent();
				Long amount = Long.valueOf(priceEvent.asCharacters().getData());
				price.setAmount(amount);
				price.setBdAmount(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100)));
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(priceEvent, "CurrencyCode")) {
				priceEvent = eventReader.nextEvent();
				price.setCurrencyCode(priceEvent.asCharacters().getData());
				continue;
			}

			if (AmazonUnmarshaller.isStartElementEqual(priceEvent, "FormattedPrice")) {
				priceEvent = eventReader.nextEvent();
				price.setFormattedPrice(priceEvent.asCharacters().getData());
				continue;
			}
		}
		return price;
	}
}
