package org.xmlactions.code.interpreter.stack;

public class CodePos {

	private int pos;		// position.
	private int lineNo;		// line no.
	private int linePos;	// position on line.
	
	public CodePos(int pos, int lineNo, int linePos) {
		this.pos = pos;
		this.lineNo = lineNo;
		this.linePos = linePos;
	}
	
	public void add(int amount) {
		posAdd(amount);
		linePosAdd(amount);
	}
	
	public int posAdd(int amount) {
		this.pos += amount;
		return this.pos;
	}

	public int lineNoAdd(int amount) {
		this.lineNo += amount;
		return this.lineNo;
	}

	public int linePosAdd(int amount) {
		this.linePos += amount;
		return this.linePos;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public int getLinePos() {
		return linePos;
	}

	public void setLinePos(int linePos) {
		this.linePos = linePos;
	}
	
	/**
	 * Moves the position of the curser back by an amount - also moves the linePos and the lineNo back.  
	 * @param amount
	 */
	public void moveBack(int amount) {
		pos -= amount;
		
		while (amount > 0) {
			if (amount > linePos) {
				amount -= linePos;
				linePos = 0;
				lineNo -= 1;
			} else {
				linePos -= amount;
				amount = 0;
			}
		}
		
	}
	
	public String toErrorString() {
		return "lineNo:" + lineNo + " linePos:" + linePos + " pos:" + pos;
	}

}
