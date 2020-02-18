package com.cloudcampaignexam.tinyurlapi.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TinyUrlHelperTests {

	@Test
	void testUniqueSuffixIsCorrectLength() {
		TinyUrlHelper tuh = new TinyUrlHelper();
		String suffix = null;
		List<String> suffixSet = new ArrayList<>();
		int suffixSize = 2;

		int actualLength = tuh.getUniqueSuffix(suffix,suffixSet,suffixSize).length();

		assertEquals(suffixSize, actualLength);
	}
	@Test
	void testisUniqueIsTruewhenNotInSet() {
		TinyUrlHelper tuh = new TinyUrlHelper();
		String suffix = "a";
		List<String> suffixSet = Arrays.asList("b","c");

		Boolean result = tuh.isUnique(suffix, suffixSet);
		
		assertEquals(true, result);
	}
	@Test
	void testisUniqueIsFalseWhenInSet() {
		TinyUrlHelper tuh = new TinyUrlHelper();
		String suffix = "b";
		List<String> suffixSet = Arrays.asList("b","c");

		Boolean result = tuh.isUnique(suffix, suffixSet);
		
		assertEquals(false, result);
	}
	@Test
	void testisUniqueIsFalseWhenNull() {
		TinyUrlHelper tuh = new TinyUrlHelper();
		String suffix = null;
		List<String> suffixSet = Arrays.asList("b","c");

		Boolean result = tuh.isUnique(suffix, suffixSet);
		
		assertEquals(false, result);
	}
}
