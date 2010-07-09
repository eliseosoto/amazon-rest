package com.supervaca.amazonrest.dao;

import java.util.List;
import java.util.Map;

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
	
	Item lookup(String asin, List<String> responseGroups, Boolean amazonOnly);

	/**
	 * Gets the Item data for several ASINs at once
	 * 
	 * @param asins
	 *            Collection of valid ASINs
	 * @return
	 */
	List<Item> getItems(List<String> asins);

	/**
	 * Gets the Item data for several ASINs at once and the specified Response
	 * Groups
	 * 
	 * @param asins
	 * @param responseGroups
	 * @return
	 */
	List<Item> getItems(List<String> asins, List<String> responseGroups);

	/**
	 * Searches items by keyword in all categories.
	 * 
	 * @param keyword
	 * @return
	 */
	List<Item> searchItems(String keyword);

	/**
	 * Searches items by keyword in all categories and the specified Response
	 * Groups
	 * 
	 * @param keyword
	 * @param responseGroups
	 * @return
	 */
	List<Item> searchItems(String keyword, List<String> responseGroups);

	/**
	 * Searches items by keyword in all categories and the specified Search
	 * Index
	 * 
	 * @param keyword
	 * @param searchIndex
	 * @return
	 */
	List<Item> searchItems(String keyword, String searchIndex);

	/**
	 * Searches items by keyword in all categories and the specified Search
	 * Index and Response Group
	 * 
	 * @param keyword
	 * @param responseGroups
	 * @param searchIndex
	 * @return
	 */
	List<Item> searchItems(String keywords, List<String> responseGroups,
			String searchIndex);

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
	Map<String, Object> searchItems(String keywords,
			List<String> responseGroups, String searchIndex, Integer pageNumber);
}