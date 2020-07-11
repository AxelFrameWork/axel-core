package org.xmlactions.code.interpreter.stack;

import java.util.ArrayList;
import java.util.List;

public class StackedCodePos {

	private List<CodePos> stack = new ArrayList<>();
	
	public void push(CodePos codePos) {
		stack.add(codePos);
	}

	public void push(int pos, int lineNo, int linePos) {
		CodePos  codePos = new CodePos(pos, lineNo, linePos);
		stack.add(codePos);
	}

	public CodePos pull() {
		if (stack.size() == 0) {
			throw new IndexOutOfBoundsException("No CodePos on stack. Stack is empty");
		}
		return stack.remove(stack.size()-1);
	}
}
