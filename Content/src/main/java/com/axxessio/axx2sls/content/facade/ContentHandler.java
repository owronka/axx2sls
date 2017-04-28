package com.axxessio.axx2sls.content.facade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.axxessio.axx2sls.content.facade.to.AreaTO;
import com.axxessio.axx2sls.content.facade.to.PictureTO;
import com.axxessio.axx2sls.content.service.ContentService;
import com.axxessio.axx2sls.content.service.pdo.Area;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContentHandler implements RequestStreamHandler {
	private static ObjectMapper mapper = new ObjectMapper();

	private AreaTO ato = null;
	private PictureTO pto = null;
	
	private Event e;
	private LambdaLogger llog;
	private ContentService cs;
	private Response r = new Response(500, null, null);


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    	llog = context.getLogger();
    	
		try {
			cs = new ContentService(llog);

	    	if (read(inputStream) == false) {
	    		write (outputStream);
	    		
	    		return;
	    	}
	    	
	    	llog.log(mapper.writeValueAsString(e));
	    	
			if ("/content/area".equals(e.getResource()) || "/content/area/{id}".equals(e.getResource())){
				if (ato != null) llog.log(mapper.writeValueAsString(ato));
		    	
				switch (e.getHttpMethod()) {
		    		case "DELETE":
		    			cs.delArea(e.getPathParameter("id"));

		    			setResponseHeader(200, "DELETE");
		    			
		    			break;
		    			
		    		case "GET":
		    			ato = new AreaTO(cs.getArea(e.getPathParameter("id")));
		    			
		    			setResponseHeader(200, "GET", ato.toJSON());
		    			
		    			break;
		    			
		    		case "POST":
		    			Area a = ato.getAtos().get(0);
		    			
		    			a.setDate(new Date());
		    			a.setFolder(createFolderName());

		    			cs.setArea(a);
		    			
		    			setResponseHeader(200, "POST", ato.toJSON());
		    			
		    			break;
		    			
		    		case "PUT":
		    			cs.setArea(ato.getAtos().get(0));
		    			
		    			setResponseHeader(200, "PUT", ato.toJSON());
		    			
		    			break;
		    			
		    		default:
		    				
		    			setResponseHeader(400, null, Message.toJSON(400, "ERROR", "Invalid http method received [" + e.getHttpMethod() + "]"));
		    		    
		    		    break;
		    	}
			}

			if ("/content/areas".equals(e.getResource())){
				if (ato != null) llog.log(mapper.writeValueAsString(ato));
		    	
				switch (e.getHttpMethod()) {
		    		case "GET":
		    			ato = new AreaTO(cs.getAreas());
		    			
		    			setResponseHeader(200, "GET", ato.toJSON());
		    			
		    			break;
		    			
		    		default:
		    				
		    			setResponseHeader(400, null, Message.toJSON(400, "ERROR", "Invalid http method received [" + e.getHttpMethod() + "]"));
		    		    
		    		    break;
		    	}
			} 
				
			if ("/content/pictures".equals(e.getResource())){
				llog.log(mapper.writeValueAsString(pto));
		    	
				switch (e.getHttpMethod()) {
		    		case "GET":
		    			pto = new PictureTO(cs.getArea(e.getQueryStringParameter("areaId")), cs.getPictures(e.getQueryStringParameter("areaId")));
		    			
		    			setResponseHeader(200, "GET", pto.toJSON());
		    			
		    			break;
		    			
		    		default:
		    				
		    			setResponseHeader(400, null, Message.toJSON(400, "ERROR", "Invalid http method received [" + e.getHttpMethod() + "]"));
		    		    
		    		    break;
		    	}
			}
		} catch (NullPointerException xcptn) {
			llog.log(xcptn.getMessage());
			setResponseHeader(500, null, Message.toJSON(500, "ERROR", xcptn.getMessage()));
		}
		
		write (outputStream);
	}
    
	private String createFolderName() {
		Random folderNameCreator = new Random();
		String folderName = Long.toString(Math.abs(folderNameCreator.nextLong()));
		return folderName;
	}

	private boolean read (InputStream in) {
		try {
			e = mapper.readValue(in, Event.class);

			if (e.getResource().startsWith("/content/area")) {
				if (e.getBody() != null){
					ato = mapper.readValue(e.getBody(), AreaTO.class);
				}
				return true;
			}

			if (e.getResource().startsWith("/content/picture")) {
				if (e.getBody() != null){
					pto = mapper.readValue(e.getBody(), PictureTO.class);
				}
				return true;
			}
		} catch (IOException ioxcptn) {
			llog.log(ioxcptn.toString());
			r.setStatusCode(500);
			r.setBody(Message.toJSON(500, "ERROR", ioxcptn.getMessage()));
			return false;
		}
		return false;
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
