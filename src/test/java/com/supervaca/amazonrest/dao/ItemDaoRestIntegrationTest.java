package com.supervaca.amazonrest.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.supervaca.amazonrest.domain.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/applicationContext.xml" })
public class ItemDaoRestIntegrationTest extends AbstractJUnit4SpringContextTests {
	private static final Logger logger = LoggerFactory.getLogger(ItemDaoRestIntegrationTest.class);
	private ItemDao itemDao;
	
	@Before
	public final void before() {
		itemDao = applicationContext.getBean(ItemDao.class);
	}

	@Test
	public final void testLookup() {
		Item item = itemDao.lookup("B002BSA388", Arrays.asList(new String[] { "ItemAttributes", "Offers", "OfferSummary" }), true);

		logger.debug(item.toString());
	}
	
	@Test
	public final void testGetItems() {
		List<Item> items = itemDao.multiLookup(Arrays.asList(new String[] {"B000ZK9QCS", "B002BSA388"}));

		logger.debug(items.toString());
	}
}
