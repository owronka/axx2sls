package com.axxessio.axx2sls.registration.facade;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.axxessio.axx2sls.registration.facade.RegistrationHandler;
import com.axxessio.axx2sls.registration.service.pdo.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.axxessio.axx2sls.registration.TestContext;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreatePersonTest {
    final static String LOG_URL = "jdbc:mysql://login.cxodr64us5yk.eu-central-1.rds.amazonaws.com:3306/login";
    final static String REG_URL = "jdbc:mysql://registration.cxodr64us5yk.eu-central-1.rds.amazonaws.com:3306/registration";
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String USR = "db_admin";
    final static String PWD = "axxessio";

    private static ObjectMapper mapper = new ObjectMapper();

	private Connection co;
	private QueryRunner qr;
	
    @BeforeClass
    public static void createInput() throws IOException {
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("register");

        return ctx;
    }

    @Before
    public void setUp() throws Exception {
		DbUtils.loadDriver(DRIVER);

        qr = new QueryRunner();

        co = DriverManager.getConnection(LOG_URL, USR, PWD);
        qr.update(co, "delete from A2S_ACCOUNT where AC_ID>3");

		co = DriverManager.getConnection(REG_URL, USR, PWD);
        qr.update(co, "delete from A2S_ADDRESS where AD_ID>3");
        qr.update(co, "delete from A2S_PERSON where P_ID>3");
    }

    @Test
    public void testCreatePerson() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    	Context ctx = createContext();
    	RegistrationHandler handler = new RegistrationHandler();
        Response r = null;
        
        try {
        	FileInputStream fis = new FileInputStream("./src/test/resources/CreatePerson.json");
        	
			handler.handleRequest(fis, out, ctx);

			r = mapper.readValue(out.toString(), Response.class);

		} catch (IOException e) {
			e.printStackTrace();
			assert (false);
		}

        if (r != null) {
        	assert (r.getStatusCode() == 200);
        	assertNotNull (r.getBody());
        
        	System.out.println(r.getBody());
        }
    }
    
    @After
    public void tearDown() throws Exception {
        co = DriverManager.getConnection(LOG_URL, USR, PWD);
        qr.update(co, "delete from A2S_ACCOUNT where AC_ID>3");

		co = DriverManager.getConnection(REG_URL, USR, PWD);
        qr.update(co, "delete from A2S_ADDRESS where AD_ID>3");
        qr.update(co, "delete from A2S_PERSON where P_ID>3");
    }
}
