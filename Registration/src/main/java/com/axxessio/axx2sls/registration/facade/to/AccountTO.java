package com.axxessio.axx2sls.registration.facade.to;

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
    public AccountTO(@JsonProperty("id") long newId, 
    				 @JsonProperty("pId") long newpId, 
    				 @JsonProperty("name") String newName, 
    				 @JsonProperty("pwdHash") String newPwdHash, 
    				 @JsonProperty("pwdSalt") String newPwdSalt) {
		super();
		this.id = newId;
		this.pId = newpId;
		this.name = newName;
		this.pwdHash = newPwdHash;
		this.pwdSalt = newPwdSalt;
	}
	
    public AccountTO(String newName, String newPwdHash, String newPwdSalt) {
		super();
		this.name    = newName;
		this.pwdHash = newPwdHash;
		this.pwdSalt = newPwdSalt;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long newId) {
		this.id = newId;
	}

	public long getpId() {
		return pId;
	}

	public void setpId(long newpId) {
		this.pId = newpId;
	}

    public String getName() {
		return name;
	}
	
    public void setName(String newName) {
		this.name = newName;
	}
	
    public String getPwdHash() {
		return pwdHash;
	}
	
    public void setPwdHash(String newPwdHash) {
		this.pwdHash = newPwdHash;
	}

    public String getPwdSalt() {
		return pwdSalt;
	}
	
    public void setPwdSalt(String newPwdSalt) {
		this.pwdSalt = newPwdSalt;
	}
}
  