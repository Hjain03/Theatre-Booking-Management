package com.theatre.utils.exception;

public class InvalidRowInputException extends Exception {
	/**
	 * when a row will contain non numeric value this exception will be thrown
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRowInputException(String message){
		super(message);
	}
	

}
