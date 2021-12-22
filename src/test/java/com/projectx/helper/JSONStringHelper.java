package com.projectx.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONStringHelper {
	public String asJSONString(Object obj) throws Exception {
		 try {
		      return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
	}
}
