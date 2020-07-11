package org.xmlactions.code.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.code.interpreter.stack.CodePos;
import org.xmlactions.code.interpreter.stack.StackedCodePos;

public class Interpreter {

	private static final Logger logger = LoggerFactory.getLogger(Interpreter.class);
	
	private final Parser parser;
	private final ParseCode parseCode;

	
	public Interpreter(final String code) {
		this.parser = new Parser(code);
		this.parseCode = new ParseCode();
	}
	
	public void process() {
		while (parser.isParsed() == false) {
			String name = parser.findNextAction();
			String action = parser.findNextAction();
			
			if (name != null && action.equals("(")) {
				// must be code
				processCode(name, action);
			}
//			if (ChunkCode.isCode(chunk)) {
//				logger.debug("we got code chunk");
//			}
			
			// is it variable definition
			// is it axel instruction
			// is it conditional
			// is it evaluation
			
		}
	}
	
	public void processCode(String name, String action) {
		parseCode.process(parser);
	}
	
	
}
