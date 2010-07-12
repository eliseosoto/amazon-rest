package com.supervaca.amazonrest.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.supervaca.amazonrest.SignedRequestsHelper;
import com.supervaca.amazonrest.domain.Item;
import com.supervaca.amazonrest.xml.AmazonUnmarshaller;

public class ItemDaoRest implements ItemDao {
	private static final String API_VERSION = "2009-11-01";

	private static final Logger logger = LoggerFactory.getLogger(ItemDaoRest.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SignedRequestsHelper signedRequestsHelper;

	@Override
	public Item lookup(String asin) {
		String[] responseGroups = { "ItemAttributes", "Offers", "OfferSummary", "Images" };
		return lookup(asin, Arrays.asList(responseGroups), false);
	}

	@Override
	public Item lookup(String asin, List<String> responseGroups) {
		return lookup(asin, responseGroups, false);
	}

	@Override
	public Item lookup(String asin, List<String> responseGroups, Boolean amazonOnly) {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("IdType", "ASIN");
		params.put("Condition", "All");
		params.put("Version", API_VERSION);
		params.put("Service", "AWSECommerceService");
		params.put("MerchantId", amazonOnly ? "Amazon" : "All");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", asin);
		params.put("ResponseGroup", join(responseGroups, ","));
		String url = signedRequestsHelper.sign(params);

		StreamSource xmlStream = submitRequest(url);

		long start = System.currentTimeMillis();
		// First create a new XMLInputFactory
		// Setup a new eventReader
		Item item = null;
		try {
			item = AmazonUnmarshaller.unmarshalItemLookup(xmlStream);
		} catch (XMLStreamException e) {
			logger.error("XMLStream error", e);
		}
		long end = System.currentTimeMillis();

		logger.debug("Parsing took {} millis", end - start);

		return item;
	}

	@Override
	public List<Item> multiLookup(List<String> asins) {
		String[] responseGroups = { "ItemAttributes", "Offers", "OfferSummary", "Images" };
		return multiLookup(asins, Arrays.asList(responseGroups), false);
	}

	@Override
	public List<Item> multiLookup(List<String> asins, List<String> responseGroups) {
		return multiLookup(asins, responseGroups, false);
	}

	@Override
	public List<Item> multiLookup(List<String> asins, List<String> responseGroups, Boolean amazonOnly) {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("IdType", "ASIN");
		params.put("Condition", "All");
		params.put("Version", API_VERSION);
		params.put("Service", "AWSECommerceService");
		params.put("MerchantId", amazonOnly ? "Amazon" : "All");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", join(asins, ","));
		params.put("ResponseGroup", join(responseGroups, ","));
		String url = signedRequestsHelper.sign(params);
		
		StreamSource xmlStream = submitRequest(url);

		long start = System.currentTimeMillis();
		// First create a new XMLInputFactory
		// Setup a new eventReader
		List<Item> items = new ArrayList<Item>();
		try {
			items = AmazonUnmarshaller.unmarshalMultiItemLookup(xmlStream);
		} catch (XMLStreamException e) {
			logger.error("XMLStream error", e);
		}
		long end = System.currentTimeMillis();

		logger.debug("Parsing took {} millis", end - start);

		return items;
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
	public SearchItemsResults searchItems(String keyword, String searchIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchItemsResults searchItems(String keywords, List<String> responseGroups, String searchIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchItemsResults searchItems(String keywords, List<String> responseGroups, String searchIndex, Integer pageNumber, Boolean amazonOnly) {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("Operation", "ItemSearch");
		params.put("SearchIndex", searchIndex);
		params.put("ResponseGroup", join(responseGroups, ","));
		params.put("Keywords", keywords);
		params.put("MerchantId", amazonOnly ? "Amazon" : "All");
		params.put("ItemPage", pageNumber.toString());
		params.put("Version", API_VERSION);
		params.put("Service", "AWSECommerceService");
		
		String url = signedRequestsHelper.sign(params);
		
		StreamSource xmlStream = submitRequest(url);

		SearchItemsResults searchItemsResults = null;
		long start = System.currentTimeMillis();
		// First create a new XMLInputFactory
		// Setup a new eventReader
		try {
			searchItemsResults = AmazonUnmarshaller.unmarshalSearchItems(xmlStream);
		} catch (XMLStreamException e) {
			logger.error("XMLStream error", e);
		}
		long end = System.currentTimeMillis();

		logger.debug("Parsing took {} millis", end - start);

		return searchItemsResults;
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

	private static String join(List<? extends CharSequence> s, String delimiter) {
		int capacity = 0;
		int delimLength = delimiter.length();
		Iterator<? extends CharSequence> iter = s.iterator();
		if (iter.hasNext()) {
			capacity += iter.next().length() + delimLength;
		}

		StringBuilder buffer = new StringBuilder(capacity);
		iter = s.iterator();
		if (iter.hasNext()) {
			buffer.append(iter.next());
			while (iter.hasNext()) {
				buffer.append(delimiter);
				buffer.append(iter.next());
			}
		}
		return buffer.toString();
	}
}
