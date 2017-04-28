package com.axxessio.axx2sls.content.facade;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.axxessio.axx2sls.content.TestContext;
import com.axxessio.axx2sls.content.facade.ContentHandler;
import com.axxessio.axx2sls.content.facade.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetAreaTest {
	private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void createInput() throws IOException {
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("register");

        return ctx;
    }

    @Test
    public void testGetArea() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    	Context ctx = createContext();
    	ContentHandler handler = new ContentHandler();
        Response r = null;
        
        try {
        	FileInputStream fis = new FileInputStream("./src/test/resources/ReadAreaEvent.json");
        	
			handler.handleRequest(fis, out, ctx);

			r = mapper.readValue(out.toString(), Response.class);

			fis.close();

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
