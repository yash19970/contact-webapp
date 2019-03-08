package com.contact.contactwebapp.common.dto;

public class ErrorDetail {

	private String errorSubCode;
	private String errorDescription;

	/**
	 * @param errorSubCode
	 * @param errorDescription
	 */
	public ErrorDetail(String errorSubCode, String errorDescription) {
		super();
		this.errorSubCode = errorSubCode;
		this.errorDescription = errorDescription;
	}

	public String getErrorSubCode() {
		return errorSubCode;
	}

	public void setErrorSubCode(String errorSubCode) {
		this.errorSubCode = errorSubCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return "ErrorDetail [errorSubCode=" + errorSubCode + ", errorDescription=" + errorDescription + "]";
	}

}
