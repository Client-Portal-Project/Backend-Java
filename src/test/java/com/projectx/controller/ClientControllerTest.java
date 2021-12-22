package com.projectx.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.model.Client;
import com.projectx.service.ClientService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private ClientService clientServ;
	
	private Map<String, String> expectedInputJSON;
	private List<Client> testClientList;
	private Client testClient1;
	
	public static String asJSONString(Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	}
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(context).build();
		
		expectedInputJSON = new HashMap<String, String>();
		
		testClient1 = new Client(1, "Test1");
		
		testClientList = new ArrayList<Client>();
		
		expectedInputJSON.put(String.valueOf(testClient1.getClientId()), testClient1.getCompanyName());
		
	}
	
	@Test
	public void testGetAllClients() throws Exception{
		when(clientServ.findAllClients()).thenReturn(testClientList);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")
				//.content(asJSONString(expectedInputJSON))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetClientByIdSuccess() throws Exception{
		when(clientServ.findClientById(1)).thenReturn(testClient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/client/id/1")
				.content(asJSONString(expectedInputJSON))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetClientByIdUnsuccess() throws Exception{
		when(clientServ.findClientById(100)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/client/id/100")
				.content(asJSONString(expectedInputJSON))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
