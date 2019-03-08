/**
 *
 */
package com.contact.contactwebapp.common.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public ApplicationException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ApplicationException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ApplicationException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ApplicationException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
