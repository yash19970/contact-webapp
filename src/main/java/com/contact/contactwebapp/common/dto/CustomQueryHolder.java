package com.contact.contactwebapp.common.dto;

import java.util.Map;
public class CustomQueryHolder {

	private String queryString;
	private Map<String, Object> inParamMap;

	public Map<String, Object> getInParamMap() {
		return inParamMap;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setInParamMap(final Map<String, Object> inParamMap) {
		this.inParamMap = inParamMap;
	}

	public void setQueryString(final String queryString) {
		this.queryString = queryString;
	}

}
