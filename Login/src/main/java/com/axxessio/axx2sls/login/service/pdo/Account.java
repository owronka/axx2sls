package com.axxessio.axx2sls.login.service.pdo;

import java.io.Serializable;

import com.axxessio.axx2sls.login.facade.to.AccountTO;

/**
 * The persistent class for the DPUSER database table.
 * 
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private long pId;
	private String name;
	private String password;
	private String salt;
	
	public Account() {
	}

	public Account(long id, long pId, String name, String newPassword, String newSalt) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.password = newPassword;
		this.salt = newSalt;
	}

	public Account(AccountTO acto) {
		this.id = acto.getId();
		this.pId = acto.getpId();
		this.name = acto.getName();
		this.password = acto.getPwdHash();
		this.salt = acto.getPwdSalt();
	}

	public AccountTO getAccount() {
		return new AccountTO (this.id, this.pId, this.name, this.password, this.salt); 
	}
	
	public long getId() {
		return this.id;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}