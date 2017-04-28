package com.axxessio.axx2sls.login.facade.to;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericTO {
	ObjectMapper mapper = new ObjectMapper();
	
	public String toJSON () throws JsonProcessingException {
		return mapper.writeValueAsString(this);
	}
}
