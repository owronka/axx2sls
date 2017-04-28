package com.axxessio.axx2sls.registration.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Future;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.amazonaws.services.lambda.model.InvokeAsyncResult;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.axxessio.axx2sls.registration.facade.Event;
import com.axxessio.axx2sls.registration.facade.to.AccountTO;
import com.axxessio.axx2sls.registration.facade.to.AddressTO;
import com.axxessio.axx2sls.registration.facade.to.PersonTO;
import com.axxessio.axx2sls.registration.service.pdo.Address;
import com.axxessio.axx2sls.registration.service.pdo.Person;
import com.fasterxml.jackson.core.JsonProcessingException;

public class RegistrationService {

	private Connection co;
	private QueryRunner qr;
	
    final static String URL = "jdbc:mysql://registration.cxodr64us5yk.eu-central-1.rds.amazonaws.com:3306/registration";
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String USR = "db_admin";
    final static String PWD = "axxessio";
    final static String TOPIC_ARN = "arn:aws:sns:eu-central-1:597535499425:AccountTopic";
    
    public RegistrationService () throws SQLException {
		DbUtils.loadDriver(DRIVER);

		co = DriverManager.getConnection(URL, USR, PWD);
        qr = new QueryRunner();
	}
	
	public Person getPerson(long id) throws SQLException {
		Person person = null;;

        ResultSetHandler<Person> resultHandler = new BeanHandler<Person>(Person.class);		
		
        Person p = qr.query(co, "select P_ID, P_SALUTATION, P_FIRST_NAME, P_LAST_NAME, P_EMAIL, P_ACCONT_NAME from A2S_PERSON where P_ID=?", resultHandler, id);
        System.out.println(p.getId());
       
        return person;
	}

	public Address setAddress(AddressTO adto, long pId) throws SQLException {
		Address a = new Address(adto);
		
    	if (a.getId() == 0) {
    		qr.update(co, "insert into A2S_ADDRESS (AD_P_ID, AD_CITY, AD_COUNTRY, AD_HOUSENUMBER, AD_STREET, AD_TYPE, AD_ZIP) VALUES (?,?,?,?,?,?,?)", 
    				  pId, adto.getCity(), adto.getCountry(), adto.getHouseNumber(), adto.getStreet(), adto.getType(), adto.getZip());

    	} else {
    		qr.update(co, "update A2S_ADDRESS set AD_P_ID=?, AD_CITY=?, AD_COUNTRY=?, AD_HOUSENUMBER=?, AD_STREET=?, AD_TYPE=?, AD_ZIP=? where AD_ID=?", 
    				pId, adto.getCity(), adto.getCountry(), adto.getHouseNumber(), adto.getStreet(), adto.getType(), adto.getZip());
    	}

		return a;
	}

	public String setAccount(AccountTO acto) throws SQLException, JsonProcessingException {

		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, acto.toJSON());
		PublishResult publishResult = AmazonService.getSNS().publish(publishRequest);
		
		return publishResult.getMessageId();
		
/*		
		Event e = new Event ("/login/account", null, null, false, "/login/account", "POST", null, null, acto.toJSON());
		InvokeRequest iaRequest = new InvokeRequest().withFunctionName("loginFunction").withPayload(e.toJSON());  
		Future<InvokeResult> iaResult = AmazonService.getLA().invokeAsync(iaRequest);

		return null;		
*/		
	}

	public Person setPerson(PersonTO pto, AccountTO acto) throws SQLException {
		Person p = new Person(pto, acto);
		
    	if (p.getId() == 0) {
    		ScalarHandler<BigInteger> sh = new ScalarHandler<BigInteger>();
    		
    		qr.update(co, "insert into A2S_PERSON (P_SALUTATION, P_FIRST_NAME, P_LAST_NAME, P_EMAIL, P_ACCOUNT_NAME) VALUES (?,?,?,?, ?)", 
    				  p.getSalutation(), p.getFirstName(), p.getLastName(), p.getEmail(), acto.getName());
    		
    		BigInteger pid = qr.query(co, "SELECT LAST_INSERT_ID()", sh);
    		
    		p.setId(pid.longValue());
    	} else {
    		qr.update(co, "update A2S_PERSON set P_SALUTATION=?, P_FIRST_NAME=?, P_LAST_NAME=?, P_EMAIL=?, P_ACCOUNT_NAME=? where P_ID=?", 
    				  p.getSalutation(), p.getFirstName(), p.getLastName(), p.getEmail(), p.getId(), acto.getName());
    	}
		return p;
	}
	
	public void close () throws SQLException {
        DbUtils.close(co);
	}
}
