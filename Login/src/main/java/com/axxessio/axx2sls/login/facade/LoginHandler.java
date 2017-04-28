package com.axxessio.axx2sls.login.facade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.axxessio.axx2sls.login.common.Helper;
import com.axxessio.axx2sls.login.facade.to.CredentialsTO;
import com.axxessio.axx2sls.login.facade.to.LoginTO;
import com.axxessio.axx2sls.login.service.LoginService;
import com.axxessio.axx2sls.login.service.pdo.Account;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginHandler implements RequestStreamHandler  {
	private static ObjectMapper mapper = new ObjectMapper();

	private Event e;
	private LambdaLogger llog;
	private LoginTO lto;
	private LoginService ls;
	private Response r = new Response(500, null, null);

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    	llog = context.getLogger();
    	
    	if (read(inputStream) == false) {
    		write (outputStream);
    	}
    	
		try {
	    	ls = new LoginService();

			if (lto != null) llog.log(mapper.writeValueAsString(lto));
			
			switch (e.getHttpMethod()) {
				case "DELETE":
					setResponseHeader(200, "DELETE");

					break;
					
	    		case "POST":
	    			Account ac = ls.getAccount(lto.getName());
	    			CredentialsTO cto;
	    			
	    			if (! Helper.getSaltedPwdHash(lto.getPassword(), ac.getSalt()).equals(ac.getPassword())) {
		    	    	setResponseHeader(401, "POST", null);
		    	    	
		    	    	return;
	    			}
	    				    			
					cto = ls.getToken();
	    			
	    	    	//setResponseHeader(200, "POST", cto.toJSON());
	    	    	// no login possible
					setResponseHeader(200, "POST", null);
					
					break;
	    			
	    		default:
	    			
	    			setResponseHeader(400, null, Message.toJSON(400, "ERROR", "Invalid http method received [" + e.getHttpMethod() + "]"));
	    		    
	    		    break;
	    	}
		} catch (NullPointerException | SQLException | IOException xcptn) {
			llog.log(xcptn.getMessage());
			setResponseHeader(500, null, Message.toJSON(500, "ERROR", xcptn.getMessage()));
		}
		
		write (outputStream);
    }
    
    private boolean read (InputStream in) {
		try {
			StringBuffer sb = new StringBuffer();
	        int letter;
	        while((letter = in.read()) != -1) {
	            sb.append((char)letter);
	        }
			
			llog.log(mapper.writeValueAsString(sb));
			
			e = mapper.readValue(sb.toString(), Event.class);
			
			if (e.getBody() != null){
				lto = mapper.readValue(e.getBody(), LoginTO.class);
			}
			
		} catch (IOException ioxcptn) {
			llog.log(ioxcptn.toString());
			setResponseHeader(500, null, Message.toJSON(500, "ERROR", ioxcptn.getMessage()));
			return false;
		}
		return true;
    }
    
    private void setResponseHeader (int code, String methods) {
    	setResponseHeader (code, methods, null); 
    }
    
    private void setResponseHeader (int code, String methods, String body) {
    	r.setStatusCode(code);
    	r.setHeader("Access-Control-Allow-Methods", methods);
    	r.setHeader("Access-Control-Allow-Origin", "*");
		r.setHeader("Content-Type", "application/json");
		r.setBody(body);
    }
    
    private void write (OutputStream out) {
    	OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(out, "UTF-8");
	    	writer.write(mapper.writeValueAsString(r));  
		} catch (IOException ioxcptn) {
			llog.log(ioxcptn.toString());
		} finally {
	    	if (writer != null) {
	    		try {
	    			writer.close();
	    		} catch (IOException ioxcptn) {
	    			llog.log(ioxcptn.toString());
				} 
	    	}
		}
    }
}
