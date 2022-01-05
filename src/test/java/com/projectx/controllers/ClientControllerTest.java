package com.projectx.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.projectx.models.Client;
import com.projectx.services.ClientService;

@SpringBootTest
public class ClientControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private ClientService clientServ;

	private ObjectMapper objectMapper;
	private List<Client> testClientList;
	private Client testClient1;
	private Client testClient2;
	private User testUser;

	private static final String URI = "/client";
	private static final String USER1 = "/user/1";
	
	@BeforeEach
	void setUp() {
		this.mockMvc = webAppContextSetup(context).build();
		this.objectMapper = new ObjectMapper();
		
		testClient1 = new Client(1, "Test1");
		testClient2 = new Client(null, "Test2");
		testUser = new User(null, "", "", "", "", false);
		
		testClientList = new ArrayList<>();
		testClientList.add(testClient1);
	}
	
	@Test @SneakyThrows
	public void testGetAllClients() {
		when(clientServ.findAllClients()).thenReturn(testClientList);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testClientList)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByIdSuccess() {
		when(clientServ.findClientById(testClient1.getClientId())).thenReturn(testClient1);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/id/" + testClient1.getClientId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testClient1)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByIdFail() {
		int testId = 100;
		when(clientServ.findClientById(testId)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/id/" + testId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by id: "
						+ testId));
	}
	
	@Test @SneakyThrows
	public void testGetClientByCompanyNameSuccess() {
		when(clientServ.findClientByCompanyName("Test1")).thenReturn(testClient1);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/name/" + testClient1.getCompanyName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testClient1)));
	}
	
	@Test @SneakyThrows
	public void testGetClientByCompanyNameFail() {
		String testCompanyName = "TestFail";
		when(clientServ.findClientByCompanyName(testCompanyName)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/name/" + testCompanyName)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company " +
						"Name: " + testCompanyName));
	}
	
	@Test @SneakyThrows
	public void testCreateClientSuccess() {
		Client returnClient = new Client(2, "Test2");
		when(clientServ.createClient(testClient2)).thenReturn(returnClient);
		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.content(objectMapper.writeValueAsString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(returnClient)));
	}
	

	@Test @SneakyThrows
	public void testCreateClientFail() {
		when(clientServ.createClient(testClient1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Failed to create: "
						+ testClient1 + ", it is already exist"));
	}
	
	@Test @SneakyThrows
	public void testEditClientSuccess() {
		Client returnClient = new Client(2, "TestEdit");
		when(clientServ.editClient(testClient2)).thenReturn(returnClient);
		mockMvc.perform(MockMvcRequestBuilders.put(URI)
				.content(objectMapper.writeValueAsString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(returnClient)));
	}
	

	@Test @SneakyThrows
	public void testEditClientFail() {
		when(clientServ.editClient(testClient1)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.put(URI)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Id: "
						+ testClient1.getClientId()));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientSuccess() {
		when(clientServ.deleteClient(testClient1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Client: " + testClient1
						+ ", was deleted"));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientFail() {
		when(clientServ.deleteClient(testClient2)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI)
				.content(objectMapper.writeValueAsString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company " +
						"Name: " + testClient2.getCompanyName()));
	}

	@Test @SneakyThrows
	void testGetAllClientUsers() {
		List<User> list = new ArrayList<>();
		list.add(testUser);
		when(clientServ.findAllClientUsers(testClient1)).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/user")
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(list)));
	}

	@Test @SneakyThrows
	void testGetClientUser() {
		when(clientServ.findClientUser(testClient1, 1)).thenReturn(testUser);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + USER1)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser)));

		when(clientServ.findClientUser(testClient1, 2)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/user/2")
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test @SneakyThrows
	void testCreateClientUser(){
		when(clientServ.createClientUser(testClient1, 1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.put(URI + USER1)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		when(clientServ.createClientUser(testClient2, 1)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.put(URI + USER1)
				.content(objectMapper.writeValueAsString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test @SneakyThrows
	void testDeleteClientUser() {
		when(clientServ.deleteClientUser(testClient1, 1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI + USER1)
				.content(objectMapper.writeValueAsString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		when(clientServ.deleteClientUser(testClient2, 1)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.delete(URI + USER1)
				.content(objectMapper.writeValueAsString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
