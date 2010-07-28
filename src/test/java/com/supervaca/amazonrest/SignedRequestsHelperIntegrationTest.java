package com.supervaca.amazonrest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/applicationContext.xml" })
public class SignedRequestsHelperIntegrationTest extends AbstractJUnit4SpringContextTests {
	private static final Logger logger = LoggerFactory.getLogger(SignedRequestsHelperIntegrationTest.class);
	private SignedRequestsHelper helper;

	@Test
	public final void testSign() {
		logger.debug("Starting test");

		helper = applicationContext.getBean(SignedRequestsHelper.class);

		Map<String, String> params = new TreeMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", "B0036WT4KG");
		params.put("ResponseGroup", "ItemAttributes,Offers,Images,Reviews");
		String url = helper.sign(params);

		logger.debug(url);
	}

	@Test
	public final void testRestTemplate() throws RestClientException, URISyntaxException, XMLStreamException {
		helper = applicationContext.getBean(SignedRequestsHelper.class);

		boolean amazonOnly = true;
		Map<String, String> params = new TreeMap<String, String>();
		params.put("IdType", "ASIN");
		params.put("Condition", "All");
		params.put("Version", "2009-11-01");
		params.put("Service", "AWSECommerceService");
		params.put("MerchantId", amazonOnly ? "Amazon" : "All");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", "B002BSA388");
		params.put("ResponseGroup", "ItemAttributes,Offers,OfferSummary,Images");
		String url = helper.sign(params);
		
		logger.debug(url);

		RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);

		StreamSource response = restTemplate.getForObject(new URI(url), StreamSource.class);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(response.getInputStream());

		while (true) {
			int event = parser.next();
			if (event == XMLStreamConstants.END_DOCUMENT) {
				parser.close();
				break;
			}
			if (event == XMLStreamConstants.START_ELEMENT) {
				logger.debug("localName: {}", parser.getLocalName());
			}
			
			if (event == XMLStreamConstants.CHARACTERS) {
				logger.debug("text: {}", parser.getText());
			}
		}
	}
}
