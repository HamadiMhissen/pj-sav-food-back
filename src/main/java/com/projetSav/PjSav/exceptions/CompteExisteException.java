package com.projetSav.PjSav.exceptions;

import java.util.Arrays;

public class CompteExisteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompteExisteException(String message) {
		super(message);
		this.setStackTrace(new StackTraceElement[0]);

	    // Alternative: Keep the method where the exception was thrown.
	 // this.setStackTrace(Arrays.copyOf(this.getStackTrace(), 1)); 
	}
	CompteExisteException(String message, Throwable cause) {
		super(message, cause);
	}

}
