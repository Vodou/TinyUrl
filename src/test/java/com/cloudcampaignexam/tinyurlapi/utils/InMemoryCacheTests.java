package com.cloudcampaignexam.tinyurlapi.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InMemoryCacheTests {

	@Test
	void testAddObjects() {
		int maxObjects = 6;
        InMemoryCache<String, String> cache = new InMemoryCache<String, String>(0, 0, maxObjects);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
		cache.put("Facebook", "Facebook");
		
		assertEquals(6, cache.size());
        
	}
	
	@Test
	void testAddObjectsAboveMax() {

        int maxObjects = 5;
        InMemoryCache<String, String> cache = new InMemoryCache<String, String>(0, 0, maxObjects);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
		cache.put("Facebook", "Facebook");
		
		assertEquals(5, cache.size());
        
	}
	@Test
	void testRemoveObjects() {

        int maxObjects = 5;
        InMemoryCache<String, String> cache = new InMemoryCache<String, String>(0, 0, maxObjects);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
		cache.put("Facebook", "Facebook");

		cache.remove("Paypal");
		
		assertEquals(4, cache.size());     
	}
	@Test
	void testContainsKeyIsFalseWhenKeyNotFound() {

        int maxObjects = 5;
        InMemoryCache<String, String> cache = new InMemoryCache<String, String>(0, 0, maxObjects);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
       
		Boolean result = cache.containsKey("FaceBook");
		
		assertEquals(false, result);     
	}
	@Test
	void testContainsKeyIsTrueWhenKeyFound() {

        int maxObjects = 5;
        InMemoryCache<String, String> cache = new InMemoryCache<String, String>(0, 0, maxObjects);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
       
		Boolean result = cache.containsKey("Google");
		
		assertEquals(true, result);     
    }
}
