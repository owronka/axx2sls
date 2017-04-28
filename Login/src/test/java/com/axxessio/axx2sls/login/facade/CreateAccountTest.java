package com.axxessio.axx2sls.login.facade;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.axxessio.axx2sls.login.facade.LoginHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.axxessio.axx2sls.login.TestContext;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateAccountTest {
	private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void createInput() throws IOException {
   	}

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("login");

        return ctx;
    }

    @Test
    public void testCreateAccount() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    	Context ctx = createContext();
        LoginHandler handler = new LoginHandler();
        Response r = null;
        
        try {
        	FileInputStream fis = new FileInputStream("./src/test/resources/CreateAccount.json");
        	
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
}
