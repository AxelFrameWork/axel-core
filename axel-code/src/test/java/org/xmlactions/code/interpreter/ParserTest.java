package org.xmlactions.code.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserTest {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ParserTest.class);


	@Test
	public void testParserCode() {
		String code = "  func /*asd/*asd*/asd*/( /* asd */ );";
		Parser parser = new Parser(code);
		String action = parser.findNextAction();
		assertEquals("func", action);
		action = parser.findNextAction();
		assertEquals("(", action);
		action = parser.findNextAction();
		assertEquals(")", action);
		action = parser.findNextAction();
		assertEquals(";", action);
		
	}
	
	@Test
	public void testParserCodeWithParams() {
		String code = "  func /*asd/*asd*/asd*/( /* asd */ \"Hello World!!!\");";
		Parser parser = new Parser(code);
		String action = parser.findNextAction();
		assertEquals("func", action);
		action = parser.findNextAction();
		assertEquals("(", action);
		action = parser.findNextAction();
		assertEquals("Hello", action);
		action = parser.findNextAction();
		assertEquals("World", action);
		action = parser.findNextAction();
		assertEquals(")", action);
		action = parser.findNextAction();
		assertEquals(";", action);
		
	}
	

}
