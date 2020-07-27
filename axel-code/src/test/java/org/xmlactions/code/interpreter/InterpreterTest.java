package org.xmlactions.code.interpreter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterpreterTest {
	
	private static final Logger logger = LoggerFactory.getLogger(InterpreterTest.class);


	
	@Test
	public void testIntepreterChunks() {
		
		String code = "echo(\"Hello World!!!\":string):string";
		Interpreter interpreter = new Interpreter(code);
		// interpreter.process();
	}
}
