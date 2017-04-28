package com.axxessio.axx2sls.account.facade;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.axxessio.axx2sls.account.TestContext;
import com.axxessio.axx2sls.account.facade.AccountHandler;
import com.axxessio.axx2sls.login.service.pdo.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class SetAccountTest {
	private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void createInput() throws IOException {
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("account");

        return ctx;
    }

    @Test
    public void testSetArea() {
		Account a = null;
    	AccountHandler handler = new AccountHandler();
    	Context ctx = createContext();
        
        try {
        	FileInputStream fis = new FileInputStream("./src/test/resources/InsertAccountEvent.json");
        	
        	SNSEvent e = mapper.readValue(fis, SNSEvent.class);
        	
			fis.close();

			a = (Account) handler.handleRequest(e, ctx);

        	System.out.println(a.toString());

		} catch (IOException e) {
			e.printStackTrace();
			assert (false);
		}

       	assertNotNull (a);
    }
}
