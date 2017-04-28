package com.axxessio.axx2sls.registration.facade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.axxessio.axx2sls.registration.common.Security;
import com.axxessio.axx2sls.registration.facade.to.AccountTO;
import com.axxessio.axx2sls.registration.facade.to.AddressTO;
import com.axxessio.axx2sls.registration.facade.to.PersonTO;
import com.axxessio.axx2sls.registration.facade.to.RegistrationTO;
import com.axxessio.axx2sls.registration.service.RegistrationService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistrationHandler implements RequestStreamHandler {

	private static ObjectMapper mapper = new ObjectMapper();

	private Event e;
	private LambdaLogger llog;
	private RegistrationTO rto;
	private RegistrationService rs;
	private Response r = new Response(500, null, null);


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    	llog = context.getLogger();
    	
    	if (read(inputStream) == false) {
    		write (outputStream);
    	}
    	
		try {
			rs = new RegistrationService();

			llog.log(mapper.writeValueAsString(rto));
    	
			switch (e.getHttpMethod()) {
	    		case "POST":
	    			long pid;
	    			String 	mid;

	    			
	    			PersonTO pto = rto.getPerson();
	    			AccountTO acto = rto.getAccount();
	    			AddressTO adto = rto.getAddress();
	    			
//	    			pid = rs.setPerson(pto, acto).getId();
	    			
//	    			pto.setPersonId(pid);
	    			
	    			// Secure random salt
	    			String salt = Security.generateSalt();
	    			// hashed password
	    			String genPassword = Security.getSaltedPwdHash(acto.getPwdHash(), salt);
// no registration is possible
//	    			acto.setpId(pid);
//	    			acto.setPwdHash(genPassword);
//	    			acto.setPwdSalt(salt);
//	    			
//	    			mid = rs.setAccount(acto);
//	    			
//	    			llog.log("messaging id for login account creation is [" + mid + "]");
//	    			
//	    			rs.setAddress(adto, pid);
//	    			
//	    			rs.close();
	    			
	    	    	r.setStatusCode(200);
	    	    	r.setHeader("Access-Control-Allow-Methods", "POST");
	    	    	r.setHeader("Access-Control-Allow-Origin", "*");
	    			r.setHeader("Content-Type", "application/json");
	    	    	// no registration is possible
	    			//r.setBody(pto.toJSON());
	    			
	    			break;
	    			
	    		default:
	    				
	    			r.setStatusCode(400);
	    			r.setBody(Message.toJSON(400, "ERROR", "Invalid http method received [" + e.getHttpMethod() + "]"));
	    		    
	    		    break;
	    	}
		} catch (NullPointerException | SQLException | IOException xcptn) {
			llog.log(xcptn.getMessage());
			r.setStatusCode(500);
			r.setBody(Message.toJSON(500, "ERROR", xcptn.getMessage()));
		}
		
		write (outputStream);
	}
    private boolean read (InputStream in) {
		try {
			e = mapper.readValue(in, Event.class);

			llog.log(mapper.writeValueAsString(e));
			
			if (e.getBody() == null){
				r.setStatusCode(400);
				r.setBody(Message.toJSON(400, "ERROR", "body is empty"));
				
				return false;
			}
			
			rto = mapper.readValue(e.getBody(), RegistrationTO.class);

		} catch (IOException ioxcptn) {
			llog.log(ioxcptn.toString());
			r.setStatusCode(500);
			r.setBody(Message.toJSON(500, "ERROR", ioxcptn.getMessage()));
			return false;
		}
		return true;
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
