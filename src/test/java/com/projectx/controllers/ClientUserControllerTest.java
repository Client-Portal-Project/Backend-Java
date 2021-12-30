package com.projectx.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.projectx.helper.JSONStringHelper;
import com.projectx.helper.MockMvcPerformHelper;
import com.projectx.models.Client;
import com.projectx.models.ClientUser;
import com.projectx.models.User;
import com.projectx.services.ClientService;
import com.projectx.services.ClientUserService;
import com.projectx.services.UserService;
import com.projectx.utility.ClientUserRequestObject;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientUserControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private ClientUserService clientUserServ;
	
	@MockBean
	private ClientService clientServ;
	
	@MockBean
	private UserService userServ;
	
	private List<ClientUser> testClientUserList;
	private ClientUser testClientUser1;
	private ClientUser testClientUser2;
	private User testUser1;
	private User testUser2;
	private Client testClient1;
	private Client testClient2;
	private ClientUserRequestObject testClientUserRequestObject;

	//Located in helper package
	public JSONStringHelper jsonHelper;
	public MockMvcPerformHelper mockMvcPerformHelper;
	
	@BeforeEach
	void setUp() {
		mockMvc = webAppContextSetup(context).build();
		
		//Located in helper package
		jsonHelper = new JSONStringHelper();//Convert object to JSON String
		mockMvcPerformHelper = new MockMvcPerformHelper();//Lunches mockMvc.perform action
		
		testClient1 = new Client(1, "Test Company 1");
		testClient2 = new Client(2, "Test Company 2");
		
		testUser1 = new User(1, "Test1", "Test1", "Test1", "Test1", false);
		testUser2 = new User(2, "Test2", "Test2", "Test2", "Test2", false);
		
		testClientUser1 = new ClientUser(1, testUser1, testClient1);
		testClientUser2 = new ClientUser(2, testUser2, testClient2);
		
		testClientUserList =  new ArrayList<>();
		testClientUserList.add(testClientUser1);
		
		testClientUserRequestObject = new ClientUserRequestObject(testUser2.getEmail(), testClient2.getCompanyName());
	}
	
	@Test
	public void testGetAllClientUsers() throws Exception {
		when(clientUserServ.findAllClientUsers()).thenReturn(testClientUserList);
		mockMvcPerformHelper.mockPerform(mockMvc,
					"GET",
					"/clientUser/all",
					jsonHelper.asJSONString(testClientUserList),
					status().isOk(),
					null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByIdSuccess() {
		when(clientUserServ.findClientUserById(testClientUser1.getClientUserId())).thenReturn(testClientUser1);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/id/" + testClientUser1.getClientUserId(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByIdUnsuccess() {
		when(clientUserServ.findClientUserById(testClientUser2.getClientUserId())).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/id/" + testClientUser2.getClientUserId(),
				"Failed to find Client User by id: " + testClientUser2.getClientUserId(),
				status().isNotFound(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByUserIdSuccess() {
		when(userServ.findUserById(testUser1.getUserId())).thenReturn(testUser1);
		when(clientUserServ.findClientUserByUser(testUser1)).thenReturn(testClientUser1);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/user/id/" + testUser1.getUserId(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByUserIdUnsuccess() {
		when(userServ.findUserById(testUser2.getUserId())).thenReturn(null);
		when(clientUserServ.findClientUserByUser(null)).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/user/id/" + testUser2.getUserId(),
				"Failed to find Client User by User Id: " + testUser2.getUserId(),
				status().isNotFound(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByClientIdSuccess() {
		when(clientServ.findClientById(testClient1.getClientId())).thenReturn(testClient1);
		when(clientUserServ.findClientUserByClient(testClient1)).thenReturn(testClientUserList);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/clientUser/client/id/" + testClient1.getClientId(),
				jsonHelper.asJSONString(testClientUserList),
				status().isOk(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByClientIdFail() {
		List<ClientUser> testClientUserListFail = new ArrayList<>();
		when(clientServ.findClientById(testClient2.getClientId())).thenReturn(null);
		when(clientUserServ.findClientUserByClient(testClient2)).thenReturn(testClientUserListFail);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/client/id/" + testClient2.getClientId(),
				"Failed to find Client Users by Client Id: " + testClient2.getClientId(),
				status().isNotFound(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByUserEmailSuccess() {
		when(userServ.findUserByEmail(testUser1.getEmail())).thenReturn(testUser1);
		when(clientUserServ.findClientUserByUser(testUser1)).thenReturn(testClientUser1);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/user/email/" + testUser1.getEmail(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByUserEmailFail() {
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(null);
		when(clientUserServ.findClientUserByUser(null)).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/clientUser/user/email/" + testUser2.getEmail(),
				"Failed to find Client User by User Email: " + testUser2.getEmail(),
				status().isNotFound(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByCompanyNameSuccess() {
		when(clientServ.findClientByCompanyName(testClient1.getCompanyName())).thenReturn(testClient1);
		when(clientUserServ.findClientUserByClient(testClient1)).thenReturn(testClientUserList);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/client/name/" + testClient1.getCompanyName(),
				jsonHelper.asJSONString(testClientUserList),
				status().isOk(),
				null);
	}
	
	@Test @SneakyThrows
	public void testGetClientUserByCompanyNameFail() {
		List<ClientUser> testClientUserListFail = new ArrayList<ClientUser>();
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(null);
		when(clientUserServ.findClientUserByClient(null)).thenReturn(testClientUserListFail);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"GET",
				"/clientUser/client/name/" + testClient2.getCompanyName(),
				"Failed to find Client Users by Company Name: " + testClient2.getCompanyName(),
				status().isNotFound(),
				null);
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserSuccess() {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(testClientUser2);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser",
				jsonHelper.asJSONString(testClientUser2),
				status().isCreated(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserFailByClient() {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser",
				"Failed to create Client User: Company Name '" + testClient2.getCompanyName() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserFailByUser() {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser",
				"Failed to create Client User: user with email '" + testUser2.getEmail() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserFailByClientUser() {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser",
				"Failed to create Client User: client user'" + testClientUser2 + "', already exist.",
				status().isConflict(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserByRequestObjectSuccess() {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(testClientUser2);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser/request",
				jsonHelper.asJSONString(testClientUser2),
				status().isCreated(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserByRequestObjectFailByClient() {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser/request",
				"Failed to create Client User: Company Name '" + testClient2.getCompanyName() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserByRequestObjectFailByUser() {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser/request",
				"Failed to create Client User: user with email '" + testUser2.getEmail() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test @SneakyThrows
	public void testCreateClientUserByRequestObjectFailByClientUser() {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(null);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"POST",
				"/clientUser/request",
				"Failed to create Client User: client user with user email'" + testClientUserRequestObject.getEmail() + "', already exist.",
				status().isConflict(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientUserSuccess() {
		when(clientUserServ.deleteClientUser(testClientUser1)).thenReturn(true);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"DELETE",
				"/clientUser",
				"Client User: " + testClientUser1 + ", was deleted",
				status().isOk(),
				jsonHelper.asJSONString(testClientUser1));
	}
	
	@Test @SneakyThrows
	public void testDeleteClientUserFail() {
		when(clientUserServ.deleteClientUser(testClientUser2)).thenReturn(false);
		mockMvcPerformHelper.mockPerform(mockMvc,
				"DELETE",
				"/clientUser",
				"Failed to find Client User with ID: " + testClientUser2.getClientUserId(),
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
}
