package com.axxessio.axx2sls.login.facade.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountTO extends GenericTO {
	
	private long id;
	private long pId;
	
	private String name;
	private String pwdHash;
	private String pwdSalt;

	public AccountTO () {
	}
	
	@JsonCreator
	public AccountTO (@JsonProperty("id") long id, 
					  @JsonProperty("pId") long pId, 
					  @JsonProperty("name") String name, 
					  @JsonProperty("pwdHash") String pwdHash, 
					  @JsonProperty("pwdSalt") String pwdSalt) {
		super ();
		
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.pwdHash = pwdHash;
		this.pwdSalt = pwdSalt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwdHash() {
		return pwdHash;
	}

	public void setPwdHash(String pwdHash) {
		this.pwdHash = pwdHash;
	}

	public String getPwdSalt() {
		return pwdSalt;
	}

	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}
}
