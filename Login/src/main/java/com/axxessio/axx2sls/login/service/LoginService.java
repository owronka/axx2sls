package com.axxessio.axx2sls.login.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.axxessio.axx2sls.login.facade.to.AccountTO;
import com.axxessio.axx2sls.login.facade.to.CredentialsTO;
import com.axxessio.axx2sls.login.service.pdo.Account;

public class LoginService {

	private static final int SESSION_DURATION = 7200;

	private Connection co;
	private QueryRunner qr;
	
    final String url = "jdbc:mysql://login.cxodr64us5yk.eu-central-1.rds.amazonaws.com:3306/login";
    final String driver = "com.mysql.jdbc.Driver";
    final String usr = "db_admin";
    final String pwd = "axxessio";
    
    public LoginService () throws SQLException {
		DbUtils.loadDriver(driver);

		co = DriverManager.getConnection(url, usr, pwd);
        qr = new QueryRunner();
	}
	
	public Account getAccount(String name) throws SQLException {
		Account a = null;;

        ResultSetHandler<Account> resultHandler = new BeanHandler<Account>(Account.class);		
		
        a = qr.query(co, "select AC_ID as id, AC_P_ID as pId, AC_NAME as name, AC_PASSWORD as password, AC_SALT as salt from A2S_ACCOUNT where AC_NAME=?", resultHandler, name);
        
        return a;
	}

	public Account setAccount(AccountTO acto) throws SQLException {
		Account a = new Account(acto);
		
    	if (a.getId() == 0) {
    		ScalarHandler<BigInteger> sh = new ScalarHandler<BigInteger>();
    		
    		qr.update(co, "insert into A2S_ACCOUNT (AC_P_ID, AC_NAME, AC_PASSWORD, AC_SALT) VALUES (?,?,?,?)", 
    				  acto.getpId(), acto.getName(), acto.getPwdHash(), acto.getPwdSalt());

    		BigInteger pid = qr.query(co, "SELECT LAST_INSERT_ID()", sh);
    		
    		a.setId(pid.longValue());
    	} else {
    		qr.update(co, "update A2S_ACCOUNT set AC_P_ID=?, AC_NAME=?, AC_PASSWORD=?, AC_SALT=? where AC_ID=?", 
    				  acto.getpId(), acto.getName(), acto.getPwdHash(), acto.getPwdSalt(), acto.getId());
    	}

		return a;
	}
	
	public CredentialsTO getToken () {
    	GetSessionTokenRequest gstRequest = new GetSessionTokenRequest();
		gstRequest.setDurationSeconds(SESSION_DURATION);

		GetSessionTokenResult gstResult = AmazonService.getSTS().getSessionToken(gstRequest);
		
		return new CredentialsTO(gstResult.getCredentials().getAccessKeyId(),
				  			     gstResult.getCredentials().getSecretAccessKey(),
								 gstResult.getCredentials().getSessionToken(),
								 gstResult.getCredentials().getExpiration());
	}
}
