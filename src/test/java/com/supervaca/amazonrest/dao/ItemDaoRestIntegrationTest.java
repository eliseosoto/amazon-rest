package com.supervaca.amazonrest.dao;

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

	@Test
	public final void testLookupJaxb() {
		ItemDao itemDao = applicationContext.getBean(ItemDao.class);
		
		Item item = itemDao.lookup("B002BSA388");

		logger.debug(item.toString());
	}
}
