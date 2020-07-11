package org.xmlactions.code.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseCode extends ParseParams implements IProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ParseCode.class);

	private final static char [] codeParamChars = new char[] {',', ')'};
	/**
	 * When we get here we've found a function name and a (
	 */
	@Override
	public void process(Parser parser) {
		
		while(true) {
			// 1st check for parameters
			int index = parser.findChar(codeParamChars);
			if (index < 0) {
				throw new InterpreterException("Invalid Code missing terminating terminating barcket ')' @ " + parser.toErrorString());
			}
			char c = parser.charAt(index);
			if (c == ')') {
				closeCodeBracket(parser);
				break;
			} else {
				getParameter(parser, index);
			}
		}
	}

	private void getParameter(Parser parser, int index) {
		logger.debug("getParameter");
		String name = parser.findNextParam();

	}
	
	private void closeCodeBracket(Parser parser) {
		logger.debug("closeCodeBracket");
	}
	
}
