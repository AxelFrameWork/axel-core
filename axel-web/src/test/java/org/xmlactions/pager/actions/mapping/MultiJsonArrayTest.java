package org.xmlactions.pager.actions.mapping;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.arrays.MultiArrayIndexer;

public class MultiJsonArrayTest {
	
    private static Logger log = LoggerFactory.getLogger(MultiJsonArrayTest.class);


	@Test
	public void testMultiArrayIndexing0() {
		Integer indexingArrays[] = {0};
		Integer arrayLimits[] = {0};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("0", result, mai);
			
		} while (result == true);
	}

	@Test
	public void testMultiArrayIndexing1_0() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {0,0,0};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("0,0,0", result, mai);
			
		} while (result == true);
	}
	
	@Test
	public void testMultiArrayIndexing1_1() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {1,1,1};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("1,1,1", result, mai);
			
		} while (result == true);
	}
	
	@Test
	public void testMultiArrayIndexing1_112() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {1,1,2};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("1,1,2", result, mai);
			
		} while (result == true);
	}

	@Test
	public void testMultiArrayIndexing1_122() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {1,2,2};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("1,2,2", result, mai);
			
		} while (result == true);
	}
	
	@Test
	public void testMultiArrayIndexing1_222() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {2,2,2};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("2,2,2", result, mai);
			
		} while (result == true);
	}

	@Test
	public void testMultiArrayIndexing1_121() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {1,2,1};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("1,2,1", result, mai);
			
		} while (result == true);
	}
	
	@Test
	public void testMultiArrayIndexing2() {
		Integer indexingArrays[] = {0,0,0};
		Integer arrayLimits[] = {2,2,3};
		
		MultiArrayIndexer mai = new MultiArrayIndexer(indexingArrays, arrayLimits);
		
		boolean result;
		do {
			result = mai.hasNext();
			showResult("2,2,3", result, mai);
			
		} while (result == true);
	}
	
	private void showResult(String owner, boolean result, MultiArrayIndexer mai) {
		log.debug(owner + " result:" + result + "::" + mai.toString());
	}
	
}
