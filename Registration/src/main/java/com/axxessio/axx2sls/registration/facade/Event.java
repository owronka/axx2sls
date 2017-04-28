package com.axxessio.axx2sls.registration.facade;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Event {
	private static ObjectMapper mapper = new ObjectMapper();

	private String  path;
    private HashMap<String, String> headers;
    private HashMap<String, String> pathParameters;
    private boolean isBase64Encoded;
	private String  resource;
    private String  httpMethod;
    private HashMap<String, String> queryStringParameters;
    private HashMap<String, String> stageVariables;
    private String  body;

    public Event () {
    }
    
    public Event(String path, HashMap<String, String> headers, HashMap<String, String> pathParameters, boolean isBase64Encoded, String resource,
			String httpMethod, HashMap<String, String> queryStringParameters, HashMap<String, String> stageVariables, String body) {
		super();
		this.path = path;
		this.headers = headers;
		this.pathParameters = pathParameters;
		this.isBase64Encoded = isBase64Encoded;
		this.resource = resource;
		this.httpMethod = httpMethod;
		this.queryStringParameters = queryStringParameters;
		this.stageVariables = stageVariables;
		this.body = body;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public String getPathParameter(String key) {
		if (pathParameters != null && pathParameters.containsKey(key)) {
			return pathParameters.get(key);
		}
		
		return null;
	}
	
	public HashMap<String, String> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(HashMap<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public boolean getIsBase64Encoded() {
		return isBase64Encoded;
	}

	public void setIsBase64Encoded(boolean isBase64Encoded) {
		this.isBase64Encoded = isBase64Encoded;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public HashMap<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}

	public String getQueryStringParameter(String key) {
		if (queryStringParameters != null && queryStringParameters.containsKey(key)) {
			return queryStringParameters.get(key);
		}
		
		return null;
	}
	
	public void setQueryStringParameters(HashMap<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}

	public HashMap<String, String> getStageVariables() {
		return stageVariables;
	}

	public void setStageVariables(HashMap<String, String> stageVariables) {
		this.stageVariables = stageVariables;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String toJSON() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
