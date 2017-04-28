package com.axxessio.axx2sls.login.facade.to;

import java.util.Date;

import com.axxessio.axx2sls.login.common.Helper;

public class CredentialsTO extends GenericTO {
	private String accessKeyID;
	private String secretAccessKey;
	private String sessionToken;
	private String expirationTime;

	public CredentialsTO(String accessKeyID, String secretAccessKey, String sessionToken, Date expirationTime) {
		super();
		this.accessKeyID = accessKeyID;
		this.secretAccessKey = secretAccessKey;
		this.sessionToken = sessionToken;
		this.expirationTime = Helper.getISO8601StringFromDate(expirationTime);
	}
	
	public CredentialsTO(String accessKeyID, String secretAccessKey, String sessionToken, String expirationTime) {
		super();
		this.accessKeyID = accessKeyID;
		this.secretAccessKey = secretAccessKey;
		this.sessionToken = sessionToken;
		this.expirationTime = expirationTime;
	}
	
	public String getAccessKeyID() {
		return accessKeyID;
	}
	
	public void setAccessKeyID(String accessKeyID) {
		this.accessKeyID = accessKeyID;
	}
	
	public String getSecretAccessKey() {
		return secretAccessKey;
	}
	
	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	public String getExpirationTime() {
		return expirationTime;
	}
	
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = Helper.getISO8601StringFromDate(expirationTime);
	}
	
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	
}
