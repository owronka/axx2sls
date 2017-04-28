package com.axxessio.axx2sls.registration.service.pdo;

import java.io.Serializable;

import com.axxessio.axx2sls.registration.facade.to.AccountTO;
import com.axxessio.axx2sls.registration.facade.to.PersonTO;


/**
 * The persistent class for the DPUSER database table.
 * 
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
    private String salutation;
    private String firstName;
    private String lastName;
    private String email;
    private String accountName;
    
    public Person() {
    }

    public Person(PersonTO pto, AccountTO atoc) {
    	this.id = pto.getPersonId();
    	this.email = pto.getEmail();
    	this.firstName = pto.getFirstName();
    	this.lastName = pto.getLastName();
    	this.salutation = pto.getSalutation();
    	
    	this.accountName = atoc.getName();
    }
    
    public PersonTO getPerson () {
    	return new PersonTO(this.id, this.salutation, this.firstName, this.lastName, this.email);
    }
    
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSalutation() {
		return this.salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}