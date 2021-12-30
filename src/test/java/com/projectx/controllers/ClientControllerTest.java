package com.projectx.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import com.projectx.helper.JSONStringHelper;
import com.projectx.models.User;
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

import com.projectx.models.Client;
import com.projectx.services.ClientService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
	
	private MockMvc mockMvc;
	private JSONStringHelper jsonHelper;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private ClientService clientServ;
	
	private List<Client> testClientList;
	private Client testClient1;
	private Client testClient2;
	private User testUser;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(context).build();
		this.jsonHelper = new JSONStringHelper();
		
		testClient1 = new Client(1, "Test1");
		testClient2 = new Client(null, "Test2");
		testUser = new User(null, "", "", "", "", false);
		
		testClientList = new ArrayList<Client>();
		testClientList.add(testClient1);
	}
	
	@Test
	public void testGetAllClients() throws Exception{
		when(clientServ.findAllClients()).thenReturn(testClientList);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(testClientList)));
	}
	
	@Test
	public void testGetClientByIdSuccess() throws Exception{
		when(clientServ.findClientById(testClient1.getClientId())).thenReturn(testClient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/" + testClient1.getClientId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(testClient1)));
	}
	
	@Test
	public void testGetClientByIdUnsuccess() throws Exception{
		int testId = 100;
		when(clientServ.findClientById(testId)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/" + testId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by id: "
						+ testId));
	}
	
	@Test ////////////////////////////////////////////////////////////////////////////---------------------------------
	public void testGetClientByCompanyNameSuccess() throws Exception{
		when(clientServ.findClientByCompanyName("Test1")).thenReturn(testClient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/" + testClient1.getCompanyName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(testClient1)));
	}
	
	@Test
	public void testGetClientByCompanyNameUnsuccess() throws Exception{
		String testCompanyName = "TestFail";
		when(clientServ.findClientByCompanyName(testCompanyName)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/" + testCompanyName)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company " +
						"Name: " + testCompanyName));
	}
	
	@Test
	public void testCreateClientSuccess() throws Exception {
		Client returnClient = new Client(2, "Test2");
		when(clientServ.createClient(testClient2)).thenReturn(returnClient);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/client")
				.content(jsonHelper.asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(returnClient)));
	}
	
	@Test
	public void testCreateClientUnsuccess() throws Exception {
		Client returnClient = null;
		when(clientServ.createClient(testClient1)).thenReturn(returnClient);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/client")
				.content(jsonHelper.asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to create: "
						+ testClient1 + ", it is already exist"));
	}
	
	@Test
	public void testEditClientSuccess() throws Exception {
		Client returnClient = new Client(2, "TestEdit");
		when(clientServ.editClient(testClient2)).thenReturn(returnClient);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/client")
				.content(jsonHelper.asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(returnClient)));
	}
	
	@Test
	public void testEditClientUnsuccess() throws Exception {
		Client returnClient = null;
		when(clientServ.editClient(testClient1)).thenReturn(returnClient);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/client")
				.content(jsonHelper.asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Id: "
						+ testClient1.getClientId()));
	}
	
	@Test
	public void testDeleteClientSuccess() throws Exception {
		when(clientServ.deleteClient(testClient1)).thenReturn(true);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/client")
				.content(jsonHelper.asJSONString(testClient1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Client: " + testClient1
						+ ", was deleted"));
	}
	
	@Test
	public void testDeleteClientUnsuccess() throws Exception {
		when(clientServ.deleteClient(testClient2)).thenReturn(false);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/client")
				.content(jsonHelper.asJSONString(testClient2))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("Failed to find Client by Company " +
						"Name: " + testClient2.getCompanyName()));
	}

	@Test
	void testGetAllClientUsers() throws Exception {
		List<User> list = new ArrayList<>();
		list.add(testUser);
		when(clientServ.findAllClientUsers(testClient1)).thenReturn(list);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/user")
						.content(jsonHelper.asJSONString(testClient1))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(list)));
	}

	@Test
	void testGetClientUser() throws Exception {
		when(clientServ.findClientUser(testClient1, 1)).thenReturn(testUser);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/user/1")
						.content(jsonHelper.asJSONString(testClient1))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonHelper.asJSONString(testUser)));

		when(clientServ.findClientUser(testClient1, 2)).thenReturn(null);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/client/user/2")
						.content(jsonHelper.asJSONString(testClient1))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	void testCreateClientUser() throws Exception {
		when(clientServ.createClientUser(testClient1, 1)).thenReturn(true);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/client/user/1")
						.content(jsonHelper.asJSONString(testClient1))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		when(clientServ.createClientUser(testClient2, 1)).thenReturn(false);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/client/user/1")
						.content(jsonHelper.asJSONString(testClient2))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
//	@DeleteMapping("client/user/{id}") //id is the userId
//	public ResponseEntity deleteClientUser(@RequestBody Client client, @PathVariable int id) {
//		Boolean check = this.clientServ.deleteClientUser(client, id);
//		if (check) {
//			return new ResponseEntity(HttpStatus.OK);
//		} else {
//			return new ResponseEntity(HttpStatus.BAD_REQUEST);
//		}
//	}
	@Test
	void testDeleteClientUser() throws Exception {
		when(clientServ.deleteClientUser(testClient1, 1)).thenReturn(true);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/client/user/1")
						.content(jsonHelper.asJSONString(testClient1))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		when(clientServ.deleteClientUser(testClient2, 1)).thenReturn(false);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/client/user/1")
						.content(jsonHelper.asJSONString(testClient2))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
