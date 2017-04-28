package com.axxessio.axx2sls.registration.facade;

import java.util.HashMap;

public class Response {
	private int statusCode;
	private HashMap<String, String> headers;
	private String body;
	
	public Response() {
	}
	
	public Response(int statusCode, HashMap<String, String> headers, String body) {
		super();
		this.statusCode = statusCode;
		this.headers = headers;
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public String getHeader(String name) {
		if (headers != null && headers.containsKey(name)) {
			return headers.get(name);
		}
		
		return null;
	}

	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		
		if (headers.containsKey(name)){
			headers.replace(name, value);
		} else {
			headers.put(name, value);
		}
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
