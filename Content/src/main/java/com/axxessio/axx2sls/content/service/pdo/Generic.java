package com.axxessio.axx2sls.content.service.pdo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Generic {
	ObjectMapper mapper = new ObjectMapper();
	
	public String toJSON () throws JsonProcessingException {
		return mapper.writeValueAsString(this);
	}
}
