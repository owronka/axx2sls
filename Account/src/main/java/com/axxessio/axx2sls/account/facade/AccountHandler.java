package com.axxessio.axx2sls.account.facade;

import java.io.IOException;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.axxessio.axx2sls.login.facade.to.AccountTO;
import com.axxessio.axx2sls.login.service.LoginService;
import com.axxessio.axx2sls.login.service.pdo.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountHandler implements RequestHandler<SNSEvent, Object> {
	private static ObjectMapper mapper = new ObjectMapper();

	private LambdaLogger llog;
	private LoginService ls;


    public Object handleRequest(SNSEvent e, Context context) {
    	Account a = null;
    	
    	llog = context.getLogger();

    	try {
	    	ls = new LoginService();
	
	    	
	    	llog.log(mapper.writeValueAsString(e));
	    	
	    	String msg = e.getRecords().get(0).getSNS().getMessage();
	    	
	    	llog.log(msg);

	    	AccountTO acto = mapper.readValue(msg, AccountTO.class);
	    	
	    	a = ls.setAccount(acto);
	    } catch (NullPointerException | SQLException | IOException xcptn) {
			llog.log(xcptn.getMessage());
		}
    	
    	return a;
	}
}
