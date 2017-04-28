package com.axxessio.axx2sls.registration.service.pdo;

import java.io.Serializable;

import com.axxessio.axx2sls.registration.facade.to.AddressTO;

/**
 * The persistent class for the DPUSER database table.
 * 
 */
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String country;
	private String city;
	private String zip;
	private String houseNumber;
	private String street;
	private String type;
	
	private Person person;
	
	public Address() {
	}

	public Address(AddressTO addressTO) {
		this.id = addressTO.getAddressId();
		this.country = addressTO.getCountry();
		this.city = addressTO.getCity();
		this.zip = addressTO.getZip();
		this.houseNumber = addressTO.getHouseNumber();
		this.street = addressTO.getStreet();
		this.type = addressTO.getType();
	}

	public AddressTO getAddress() {
		return new AddressTO(this.id, this.country, this.city, this.zip, this.houseNumber, this.street, this.type); 
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}