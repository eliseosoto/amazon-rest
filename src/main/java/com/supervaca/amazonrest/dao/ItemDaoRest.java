package com.supervaca.amazonrest.dao;

import java.net.URI;
import java.net.URISyntaxException;
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
