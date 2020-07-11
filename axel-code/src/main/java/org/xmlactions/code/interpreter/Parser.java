package org.xmlactions.code.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.code.interpreter.stack.CodePos;
import org.xmlactions.code.interpreter.stack.StackedCodePos;

public class Parser {
	private static final Logger logger = LoggerFactory.getLogger(Parser.class);

	/** Need this to remember positional markers */
	private StackedCodePos stackedCodePos;
	private final static String spaces = "  ";
	private final String code;
	private final CodePos codePos;
	
	public Parser(final String code) {
		this.code = code + spaces;	// extra spaces helps to improve performance
		codePos = new CodePos(0,0,0);
		stackedCodePos = new StackedCodePos();
	}
	
	
	/**
	 * Checks if we have parsed all code.
	 * @return true if all parsed else false.
	 */
	public boolean isParsed() {
		if (codePos.getPos() >= length()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Length of code excluding appended spaces
	 * @return length of code.
	 */
	public int length() {
		return (code.length() - spaces.length());
	}
	
	/**
	 * Find a specific char exclude comments and line feeds.
	 * <p>
	 * Preserves the current codePos
	 * <p>
	 * 
	 * @param f - find the position of this character starting from the current position. 
	 * @return the index of matching character or -1 if not found. 
	 * 
	 */
	public int findChar(char [] chars) {
		while (isParsed() == false) {
			char c = getChar();
			for (char f : chars) {
				if (c == f) {
					return this.codePos.getPos();
				}
			}
			this.codePos.add(1);
		}
		return -1;
	}
	
	/**
	 * Get the character at position
	 * 
	 * @param index - get character at this position
	 * 
	 * @return character at indexed position as in charAt(index);
	 */
	public char charAt(int index) {
		return code.charAt(index);
	}
	
	/**
	 * This can be a word, ( ) { } [ ] ; 
	 * @return the action
	 */
	public String findNextAction() {
		if (moveToNextAction()) {
			int start = codePos.getPos();
			char c = code.charAt(start);
			codePos.add(1);
			if (isActionSeperatorChar(c)) {
				return "" + c;
			}
			if (moveToActionEnd()) {
				int end = codePos.getPos();
				String word = code.substring(start, end);
				return word;
			}
		}
		return null;
	}

	/**
	 * This can be a word, ( ) { } [ ] ; 
	 * @return the action
	 */
	public String findNextParam() {
		if (moveToNextParam()) {
			int start = codePos.getPos();
			char c = code.charAt(start);
			codePos.add(1);
			if (isActionSeperatorChar(c)) {
				return "" + c;
			}
			if (moveToParamEnd()) {
				int end = codePos.getPos();
				String word = code.substring(start, end);
				return word;
			}
		}
		return null;
	}

	public boolean moveToActionEnd() {
		while (isParsed() == false) {
			char c = this.nextChar();
			if (! isWordChar(c)) {
				return true;
			}
			if (isActionSeperatorChar(c)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean moveToParamEnd() {
		char startChar = this.getChar();
		if (startChar == '"') {
			toEndOfQuote();
			return true;
		}
		while (isParsed() == false) {
			char c = this.nextChar();
			if (! isWordChar(c)) {
				return true;
			}
			if (isActionSeperatorChar(c)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Moves to start of next action
	 * @return true if at start of next action
	 */
	public boolean moveToNextAction() {
		while (isParsed() == false) {
			char c = this.getChar();
			if (isActionSeperatorChar(c)) {
				return true;
			}
			boolean isActionChar = isActionChar(c);
			if (isActionChar == true) {
				return true; 
			}
			codePos.add(1);
		}
		return false;
	}
	
	/**
	 * Moves to start of next action
	 * @return true if at start of next action
	 */
	public boolean moveToNextParam() {
		while (isParsed() == false) {
			char c = this.getChar();
			if (c == '"') {
				return true;
			}
			if (isActionSeperatorChar(c)) {
				return true;
			}
			boolean isActionChar = isActionChar(c);
			if (isActionChar == true) {
				return true; 
			}
			codePos.add(1);
		}
		return false;
	}
	
	public void moveBack(int amount) {
		codePos.posAdd(-1);
	}

	public boolean isWhiteSpace(char c) {
		if (c == ' ' ||
			c == '\n' ||
			c == '\r' ||
			c == '\t') {
			return true;
		}
		return false;		
	}

	public boolean isWordChar(char c) {
		if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c >= '9') || c == '_' || c == '.') {
			return true;
		}
		return false;		
	}
	
	private char [] actionSeperatorChars = new char [] {'(',')','{','}','[',']',';'};
	
	public boolean isActionSeperatorChar(char c) {
		for (char a : actionSeperatorChars) {
			if (a == c) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isActionChar(char c) {
		if (isWordChar(c)) {
			return true;
		}
		return isActionSeperatorChar(c);
	}
	
	private static String noEndOfCommentError = "No end of comment found for /* at ";
	
	public void toEndOfComment() {
		push();
		while(isParsed() == false) {
			char c = nextChar();
			if (c == '*') {
				c = nextChar();
				if (c == '/') {
					pull(false);
					return;
				}
			}
			if (codePos.getPos() > length()) {
				break;
			}
		}
		pull(true);
		throw new InterpreterException(String.format(noEndOfCommentError + toErrorString()));
	}
	
	private static String noEndOfQuoteError = "No end quote found for \" at ";

	public void toEndOfQuote() {
		push();
		while(isParsed() == false) {
			char c = nextChar();
			if (c == '"') {
				pull(false);
				return;
			}
			if (codePos.getPos() > length()) {
				break;
			}
		}
		pull(true);
		throw new InterpreterException(noEndOfQuoteError + toErrorString());
	}
	
	private static String endOfCodeError = "Reached End of Code at line:%s pos:%s";


	public char getChar() {
		if (isParsed()) {
			throw new InterpreterException(String.format(endOfCodeError, codePos.getLineNo(), codePos.getLinePos()));
		}
		char c = code.charAt(codePos.getPos());
		if (c == '\n') {
			codePos.lineNoAdd(1);
			codePos.setLinePos(0);
		} else {
			codePos.linePosAdd(1);
		}
		if (c == '/' && code.charAt(codePos.getPos()+1) == '*') {
			toEndOfComment();
			codePos.linePosAdd(2);
			codePos.posAdd(2);
			c = getChar();
		}
		return c;
	}
	
	public char nextChar() {
		codePos.posAdd(1);
		return getChar();
	}

	public String toErrorString() {
		return codePos.toErrorString();
	}

	/**
	 * Pushes current codePos on stack.
	 * 
	 * @return the current codePos
	 */
	public CodePos push() {
		this.stackedCodePos.push(codePos);
		return codePos;
	}
	
	/**
	 * Sets the current codePos to the last codePos from stack.
	 * 
	 * @param restore - if true will restore to the current position
	 * 
	 * @return the current codePos
	 */
	public CodePos pull(boolean restore) {
		CodePos  cp = this.stackedCodePos.pull();
		if (restore) {
			this.codePos.setLineNo( cp.getLineNo());
			this.codePos.setLinePos(cp.getLinePos());
			this.codePos.setPos(cp.getPos());
		}
		return cp;
	}
}
