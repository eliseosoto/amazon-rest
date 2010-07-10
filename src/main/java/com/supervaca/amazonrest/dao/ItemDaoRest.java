package com.supervaca.amazonrest.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.supervaca.amazonrest.SignedRequestsHelper;
import com.supervaca.amazonrest.domain.Item;

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

		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		XMLEventReader eventReader;
		try {
			eventReader = inputFactory.createXMLEventReader(xmlStream);
			// Read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart().equals("Item")) {
						logger.debug(startElement.toString());
					}
				}

				if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("ASIN")) {
					event = eventReader.nextEvent();
					item.setAsin(event.asCharacters().getData());
					continue;
				}
				
				if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("DetailPageURL")) {
					event = eventReader.nextEvent();
					item.setDetailPageURL(event.asCharacters().getData());
					continue;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;
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
