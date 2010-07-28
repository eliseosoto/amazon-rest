package com.supervaca.amazonrest.xml;

import java.io.File;

import javax.xml.transform.stream.StreamSource;

import static org.junit.Assert.*;
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
}
