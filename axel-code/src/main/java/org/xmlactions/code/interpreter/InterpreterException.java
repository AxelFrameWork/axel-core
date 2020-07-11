package org.xmlactions.code.interpreter;

public class InterpreterException extends RuntimeException {
	
	public InterpreterException (String errorMessage) {
		super(errorMessage);
	}

	public InterpreterException (String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

}
