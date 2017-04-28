package com.axxessio.axx2sls.login.facade.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginTO extends GenericTO {
	private String name;
	private String password;

	public LoginTO () {
	}
	
	@JsonCreator
	public LoginTO (@JsonProperty("http_method") String httpMethod,
					@JsonProperty("name") String name, 
				    @JsonProperty("password") String password) {
		super ();
		
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
