package com.supervaca.amazonrest.xml;

import java.io.File;

import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
