package com.sophos.poc.wsrestswitchmock.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SwitchRequest {

	@NotNull(message = "El campo 'jwt' es requerido")
	@JsonProperty("jwt")
	private String jwt;
	@NotNull(message = "El campo 'data' es requerido")
	@JsonProperty("data")
	private String data;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
