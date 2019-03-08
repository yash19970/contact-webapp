package com.contact.contactwebapp.common.exception;

import com.contact.contactwebapp.common.dto.ErrorDetail;

import org.apache.logging.log4j.Logger;


public class ValidationException extends ApplicationException {

	private static final long serialVersionUID = 1L;
	ErrorDetail errorDetail;

	/**
	 *
	 */
	public ValidationException() {
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message, Logger logger) {
		super(message);
		logger.error(message);
	}

	/**
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public ValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(ErrorDetail errorDetail) {
		super();
		this.errorDetail = errorDetail;
	}

	/**
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErrorDetail getErrorDetail() {
		return errorDetail;
	}

	@Override
	public String toString() {
		return "ValidationException [errorDetail=" + errorDetail + "]";
	}
	
	

}
