package org.xmlactions.code.interpreter;

import java.util.Arrays;
import java.util.List;

public class ChunkCode implements IProcessor {
	
	private final ParseCode parseCode;

	private static final String [] codeNames = new String[] {
		"echo",
		"get",
		"put"
	};
	
	private static final List<String> codes = Arrays.asList(codeNames);
	
	ChunkCode() {
		this.parseCode = new ParseCode();
	}
	
	public static boolean isCode(final String chunk) {
		return codes.contains(chunk);
	}
	
	public void process(Parser parser) {
	}
	
	
}
