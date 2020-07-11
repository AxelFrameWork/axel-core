package org.xmlactions.code.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.pager.actions.Param;

public class ParseParamsTest {

	private static final Logger logger = LoggerFactory.getLogger(ParseCodeTest.class);


	ParseParams parseParams = new ParseParams();
	
	@Test
	public void testRemoveBrackets() {
		String code = "xxx( \"blah\":string):string;";
		String params = parseParams.removeBrackets(code);
		assertEquals(" \"blah\":string", params);

		code = "xxx( \"blah\"):string;";
		params = parseParams.removeBrackets(code);
		assertEquals(" \"blah\"", params);

		code = "xxx(\"blah\"):string;";
		params = parseParams.removeBrackets(code);
		assertEquals("\"blah\"", params);

		code = "xxx(blah):string;";
		params = parseParams.removeBrackets(code);
		assertEquals("blah", params);

		//logger.debug("params:" + params);
		
	}

	@Test
	public void testGetStringParam() {
		String code = "xxx( \"blah\\\",:x\":string):string;";
		String paramsString = parseParams.removeBrackets(code);
		List<Param> list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("blah\\\",:x", list.get(0).getValue());
		assertEquals("string", list.get(0).getType());

		code = "xxx( \"blah\\\",:x\"):string;";
		paramsString = parseParams.removeBrackets(code);
		list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("blah\\\",:x", list.get(0).getValue());
		assertEquals("string", list.get(0).getType());
		
		code = "xxx( blah\\\" 312):string;";
		paramsString = parseParams.removeBrackets(code);
		list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("blah\\\" 312", list.get(0).getValue());
		assertEquals("string", list.get(0).getType());
		
		// logger.debug("params:" + list);
		
	}

	@Test
	public void testGetIntParam() {
		String code = "xxx( 123 :int):string;";
		String paramsString = parseParams.removeBrackets(code);
		List<Param> list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("123", list.get(0).getValue());
		assertEquals(123, list.get(0).convert(list.get(0).getValue()));
		assertEquals("int", list.get(0).getType());

		code = "xxx( 123 ):string;";
		paramsString = parseParams.removeBrackets(code);
		list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("123", list.get(0).getValue());
		assertEquals(123, list.get(0).convert(list.get(0).getValue()));
		assertEquals("int", list.get(0).getType());
		// logger.debug("params:" + list);
	}

	@Test
	public void testGetDoubleParam() {
		String code = "xxx( 123 :double):string;";
		String paramsString = parseParams.removeBrackets(code);
		List<Param> list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("123", list.get(0).getValue());
		assertEquals(123.0, list.get(0).convert(list.get(0).getValue()));
		assertEquals("double", list.get(0).getType());

		code = "xxx( 123.012 ):string;";
		paramsString = parseParams.removeBrackets(code);
		list = parseParams.getParams(paramsString);
		assertEquals(1, list.size());
		assertEquals("123.012", list.get(0).getValue());
		assertEquals(123.012, list.get(0).convert(list.get(0).getValue()));
		assertEquals("double", list.get(0).getType());
		// logger.debug("params:" + list);
	}
	
	@Test
	public void testGetMultipleParams() {
		String code = "xxx( \"blah\\\",:x\":string, \"blah\\\",:x\":string, 123:int, 100.10:double, 321, 321.123):string;";
		String paramsString = parseParams.removeBrackets(code);
		List<Param> list = parseParams.getParams(paramsString);
		assertEquals(6, list.size());

		Param p = list.get(0);
		assertEquals("blah\\\",:x", p.getValue());
		assertEquals("string", p.getType());

		p = list.get(1);
		assertEquals("blah\\\",:x", p.getValue());
		assertEquals("string", p.getType());

		p = list.get(2);
		assertEquals("123", p.getValue());
		assertEquals(123, p.convert(p.getValue()));
		assertEquals("int", p.getType());

		p = list.get(3);
		assertEquals("100.10", p.getValue());
		assertEquals(100.10, p.convert(p.getValue()));
		assertEquals("double", p.getType());

		p = list.get(4);
		assertEquals("321", p.getValue());
		assertEquals(321, p.convert(p.getValue()));
		assertEquals("int", p.getType());

		p = list.get(5);
		assertEquals("321.123", p.getValue());
		assertEquals(321.123, p.convert(p.getValue()));
		assertEquals("double", p.getType());
		// logger.debug("params:" + list);
		
	}
}
