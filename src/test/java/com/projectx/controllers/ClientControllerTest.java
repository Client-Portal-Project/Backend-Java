package com.projectx.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import net.bytebuddy.implementation.bytecode.Throw;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Client;
import com.projectx.services.ClientService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
	
	private MockMvc mockMvc;
	private static final String URI = "/client";
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private ClientService clientServ;
	
	private List<Client> testClientList;
	private Client testClient1;
	private Client testClient2;
	
	public static String asJSONString(Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	}
	
	@BeforeEach
	void setUp(){
		mockMvc = webAppContextSetup(context).build();
		
		testClient1 = new Client(1, "Test1");
		testClient2 = new Client(null, "Test2");
		
		testClientList = new ArrayList<>();
		testClientList.add(testClient1);
		
	}
	
	@Test @SneakyThrows
	public void testGetAllClients() {
		when(clientServ.findAllClients()).thenReturn(testClientList);
		this.mockMvc.perform(MockMvcRequestBuilders.get(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(asJSONString(testClientList)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByIdSuccess() {
		when(clientServ.findClientById(testClient1.getClientId())).thenReturn(testClient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/id/" + testClient1.getClientId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(asJSONString(testClient1)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByIdFail() {
		int testId = 100;
		when(clientServ.findClientById(testId)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/id/" + testId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by id: " + testId));
	}
	
	@Test @SneakyThrows
	public void testGetClientByCompanyNameSuccess() {
		when(clientServ.findClientByCompanyName("Test1")).thenReturn(testClient1);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/name/" + testClient1.getCompanyName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(asJSONString(testClient1)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByCompanyNameFail() {
		String testCompanyName = "TestFail";
		when(clientServ.findClientByCompanyName(testCompanyName)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/name/" + testCompanyName)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company Name: " + testCompanyName));
	}
	
	@Test @SneakyThrows
	public void testCreateClientSuccess() {
		Client returnClient = new Client(2, "Test2");
		when(clientServ.createClient(testClient2)).thenReturn(returnClient);
		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.content(asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(asJSONString(returnClient)));
	}
	
	@Test @SneakyThrows
	public void testCreateClientFail() {
		when(clientServ.createClient(testClient1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.content(asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to create: " + testClient1 + ", it is already exist"));
	}
	
	@Test
	public void testEditClientSuccess() throws Throwable {
		Client returnClient = new Client(2, "TestEdit");
		when(clientServ.editClient(testClient2)).thenReturn(returnClient);
		mockMvc.perform(MockMvcRequestBuilders.put(URI)
				.content(asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(asJSONString(returnClient)));
	}
	
	@Test @SneakyThrows
	public void testEditClientFail() {
		when(clientServ.editClient(testClient1)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.put(URI)
				.content(asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Id: " + testClient1.getClientId()));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientSuccess() {
		when(clientServ.deleteClient(testClient1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI)
				.content(asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Client: " + testClient1 + ", was deleted"));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientFail() {
		when(clientServ.deleteClient(testClient2)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI)
				.content(asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company Name: " + testClient2.getCompanyName()));
	}
}
