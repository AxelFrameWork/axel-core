package org.xmlactions.code.interpreter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseCodeTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ParseCodeTest.class);


	ParseCode parseCode = new ParseCode();
	
	@Test
	public void testParseCode1() {
		String code = "echo(\"Hello World!!!\":string):string";
		Parser parser = new Parser(code);
		parseCode.process(parser);
		// logger.debug("\nresult:" + result);
	}
}
