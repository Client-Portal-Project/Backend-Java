package com.projectx.helper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class MockMvcPerformHelper {
	public void mockPerform(MockMvc mockMvc,String method, String url, String result, ResultMatcher status, String content) throws Exception{
		if (method.equals("GET")) {
			mockMvc.perform(MockMvcRequestBuilders.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status)
					.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
					.andExpect(MockMvcResultMatchers.content().string(result));
			return;
		}
		
		
		if (method.equals("POST")) {
			mockMvc.perform(MockMvcRequestBuilders.post(url)
					.content(content)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status)
					.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
					.andExpect(MockMvcResultMatchers.content().string(result));
			return;
		}
		
		
		if (method.equals("PUT")) {
			mockMvc.perform(MockMvcRequestBuilders.put(url)
					.content(content)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status)
					.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
					.andExpect(MockMvcResultMatchers.content().string(result));
			return;
		}
		
		if (method.equals("DELETE")) {
			mockMvc.perform(MockMvcRequestBuilders.delete(url)
					.content(content)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status)
					.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
					.andExpect(MockMvcResultMatchers.content().string(result));
			return;
		}
		
		throw new IncorrectMethodNameException("Method name: " + method + ", is incorrect");
	}
}
