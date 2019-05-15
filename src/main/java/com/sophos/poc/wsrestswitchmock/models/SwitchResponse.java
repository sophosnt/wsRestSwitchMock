package com.sophos.poc.wsrestswitchmock.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SwitchResponse {

	@JsonProperty("statusCode")
	private int statusCode;
	@JsonProperty("data")
	private String data;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
