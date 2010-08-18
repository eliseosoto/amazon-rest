package com.supervaca.amazonrest.dao;

import java.util.List;

import com.supervaca.amazonrest.domain.Item;

/**
 * Operations that can be performed on an Item, it uses the webservices to
 * populate the data.
 * 
 * @author eliseo.soto
 * 
 */
public interface ItemDao {
	/**
	 * Returns the Item data for the given ASIN
	 * 
	 * @param asin
	 *            A valid ASIN
	 * @return
	 */
	Item lookup(String asin);

	/**
	 * Returns the Item data for the given ASIN and the specified Response
	 * Groups
	 * 
	 * @param asin
	 * @param responseGroups
	 * @return
	 */
	Item lookup(String asin, List<String> responseGroups);

	/**
	 * Returns the Item data for the given ASIN and the specified Response
	 * Groups, can decide if you only want products sold by Amazon
	 * 
	 * @param asin
	 * @param responseGroups
	 * @param amazonOnly
	 * @return
	 */
	Item lookup(String asin, List<String> responseGroups, Boolean amazonOnly);

	/**
	 * Gets the Item data for several ASINs at once
	 * 
	 * @param asins
	 *            Collection of valid ASINs
	 * @return
	 */
	List<Item> multiLookup(List<String> asins);

	/**
	 * Gets the Item data for several ASINs at once and the specified Response
	 * Groups
	 * 
	 * @param asins
	 * @param responseGroups
	 * @return
	 */
	List<Item> multiLookup(List<String> asins, List<String> responseGroups);

	/**
	 * Gets the Item data for several ASINs at once and the specified Response
	 * Groups, , can decide if you only want products sold by Amazon
	 * 
	 * @param asins
	 * @param responseGroups
	 * @param amazonOnly
	 * @return
	 */
	List<Item> multiLookup(List<String> asins, List<String> responseGroups, Boolean amazonOnly);

	/**
	 * Searches items by keyword in all categories.
	 * 
	 * @param keyword
	 * @return
	 */
	List<Item> searchItems(String keywords);

	/**
	 * Searches items by keyword in all categories and the specified Response
	 * Groups
	 * 
	 * @param keyword
	 * @param responseGroups
	 * @return
	 */
	List<Item> searchItems(String keywords, List<String> responseGroups);

	/**
	 * Searches items by keyword in all categories and the specified Search
	 * Index
	 * 
	 * @param keyword
	 * @param searchIndex
	 * @return
	 */
	SearchItemsResults searchItems(String keywords, String searchIndex);

	/**
	 * Searches items by keyword in all categories and the specified Search
	 * Index and Response Group
	 * 
	 * @param keyword
	 * @param responseGroups
	 * @param searchIndex
	 * @return
	 */
	SearchItemsResults searchItems(String keywords, List<String> responseGroups, String searchIndex);

	/**
	 * Searches items by keyword in all categories and the specified Search
	 * Index and Response Group and page number It returns a map with multiple
	 * results: List<Item> items BigInteger totalResults BigInteger totalPages)
	 * 
	 * @param keyword
	 * @param responseGroups
	 * @param searchIndex
	 * @param pageNumber
	 * @return
	 */
	SearchItemsResults searchItems(String keywords, List<String> responseGroups, String searchIndex, Integer pageNumber, Boolean amazonOnly);
}