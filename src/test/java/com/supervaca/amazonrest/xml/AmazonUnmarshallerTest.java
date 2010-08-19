package com.supervaca.amazonrest.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supervaca.amazonrest.dao.SearchItemsResults;
import com.supervaca.amazonrest.domain.Item;
import com.supervaca.amazonrest.exception.ItemLookupException;

public class AmazonUnmarshallerTest {
	private static final Logger logger = LoggerFactory.getLogger(AmazonUnmarshallerTest.class);
	private StreamSource xmlStream;

	@Before
	public void setUp() throws Exception {
		xmlStream = null;
	}

	@Test
	public void testItem() throws Exception {
		String fileName = Thread.currentThread().getContextClassLoader().getResource("xml-responses/mario-galaxy-2.xml").getFile();
		xmlStream = new StreamSource(new File(fileName));

		long start = System.currentTimeMillis();
		Item item = AmazonUnmarshaller.unmarshalItemLookup(xmlStream);
		long end = System.currentTimeMillis();

		logger.debug("Item: {}", item);
		logger.debug("Parsing took: {}", end - start);
	}

	@Test(expected = ItemLookupException.class)
	public void testItemLookupErrorResponse() throws Exception {
		String fileName = Thread.currentThread().getContextClassLoader().getResource("xml-responses/itemLookupErrorResponse.xml").getFile();
		xmlStream = new StreamSource(new File(fileName));

		long start = System.currentTimeMillis();
		Item item = AmazonUnmarshaller.unmarshalItemLookup(xmlStream);
		long end = System.currentTimeMillis();

		logger.debug("Item: {}", item);
		logger.debug("Parsing took: {}", end - start);
	}

	@Test
	public void testItemSearchResponsePs3() throws Exception {
		String fileName = Thread.currentThread().getContextClassLoader().getResource("xml-responses/itemSearchResponse-ps3.xml").getFile();
		xmlStream = new StreamSource(new File(fileName));

		long start = System.currentTimeMillis();
		SearchItemsResults searchItemsResults = AmazonUnmarshaller.unmarshalSearchItems(xmlStream);
		long end = System.currentTimeMillis();

		for (Item item : searchItemsResults.getItems()) {
			logger.debug("ASIN: {}, Title: {}, List Price: {}", new String[] { item.getAsin(), item.getItemAttributes().getTitle(),
					item.getItemAttributes().getListPrice().getFormattedPrice()});
		}

		logger.debug("Parsing took: {}", end - start);
		assertEquals(23989, searchItemsResults.getTotalResults());
		assertEquals(2399, searchItemsResults.getTotalPages());
		assertEquals(10, searchItemsResults.getItems().size());
	}
	
	@Test
	public void testItemImages() throws Exception {
		String fileName = Thread.currentThread().getContextClassLoader().getResource("xml-responses/mario-galaxy-2.xml").getFile();
		xmlStream = new StreamSource(new File(fileName));

		long start = System.currentTimeMillis();
		Item item = AmazonUnmarshaller.unmarshalItemLookup(xmlStream);
		long end = System.currentTimeMillis();

		logger.debug("Item: {}", item);
		logger.debug("Parsing took: {}", end - start);
		assertNotNull(item.getSmallImage());
		assertEquals("http://ecx.images-amazon.com/images/I/51EOSYFRxEL._SL75_.jpg", item.getSmallImage().getUrl());
		assertEquals(75, item.getSmallImage().getHeight());
		assertEquals(53, item.getSmallImage().getWidth());
		//
		assertNotNull(item.getMediumImage());
		assertEquals("http://ecx.images-amazon.com/images/I/51EOSYFRxEL._SL160_.jpg", item.getMediumImage().getUrl());
		assertEquals(160, item.getMediumImage().getHeight());
		assertEquals(114, item.getMediumImage().getWidth());
		//
		assertNotNull(item.getLargeImage());
		assertEquals("http://ecx.images-amazon.com/images/I/51EOSYFRxEL.jpg", item.getLargeImage().getUrl());
		assertEquals(500, item.getLargeImage().getHeight());
		assertEquals(356, item.getLargeImage().getWidth());
	}
}
