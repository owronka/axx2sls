package com.axxessio.axx2sls.registration.facade.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationTO extends GenericTO {
	private PersonTO person;
	private AccountTO account;
	private AddressTO address;

	public RegistrationTO () {
	}

	@JsonCreator
	public RegistrationTO(@JsonProperty("person") PersonTO pto, @JsonProperty("account") AccountTO acto, @JsonProperty("address") AddressTO adto) {
		super();

		this.person = pto;
		this.account = acto;
		this.address = adto;
	}

	public PersonTO getPerson() {
		return person;
	}

	public void setPerson(PersonTO pto) {
		this.person = pto;
	}

	public AccountTO getAccount() {
		return account;
	}

	public void setAccount(AccountTO acto) {
		this.account = acto;
	}

	public AddressTO getAddress() {
		return address;
	}

	public void setAddress(AddressTO adto) {
		this.address = adto;
	}
}
