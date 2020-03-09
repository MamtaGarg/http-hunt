package com.thoughtworks.httphunt.exception;

/**
 * The Class InvalidPathException.
 */
public class InvalidPathException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6603481960709499539L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new invalid path exception.
	 *
	 * @param message the message
	 */
	public InvalidPathException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "InvalidPathException, not able to access the data from remote URL. " + message;
	}
}
