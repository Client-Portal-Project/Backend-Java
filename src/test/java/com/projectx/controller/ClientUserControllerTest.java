package com.projectx.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

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
import com.projectx.model.Client;
import com.projectx.model.ClientUser;
import com.projectx.model.User;
import com.projectx.service.ClientService;
import com.projectx.service.ClientUserService;
import com.projectx.service.UserService;
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
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(context).build();
		
		//Located in helper package
		this.jsonHelper = new JSONStringHelper();//Convert object to JSON String
		this.mockMvcPerformHelper = new MockMvcPerformHelper();//Lunches mockMvc.perform action
		//function mockPerform(MockMvc mockMvc,String method, String url, String result, ResultMatcher status, String content)
		//details in package helper at MockMvcPerformHelper class
		
		testClient1 = new Client(1, "Test Company 1");
		testClient2 = new Client(2, "Test Company 2");
		
		testUser1 = new User(1, "Test1", "Test1", "Test1", "Test1", false);
		testUser2 = new User(2, "Test2", "Test2", "Test2", "Test2", false);
		
		testClientUser1 = new ClientUser(1, testUser1, testClient1);
		testClientUser2 = new ClientUser(2, testUser2, testClient2);
		
		testClientUserList =  new ArrayList<ClientUser>();
		testClientUserList.add(testClientUser1);
		
		testClientUserRequestObject = new ClientUserRequestObject(testUser2.getEmail(), testClient2.getCompanyName());
	}
	
	@Test
	public void testGetAllClientUsers() throws Exception {
		when(clientUserServ.findAllClientUsers()).thenReturn(testClientUserList);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
					"GET",
					"/api/clientUsers",
					jsonHelper.asJSONString(testClientUserList),
					status().isOk(),
					null);
	}
	
	@Test
	public void testGetClientUserByIdSuccess() throws Exception {
		when(clientUserServ.findClientUserById(testClientUser1.getClientUserId())).thenReturn(testClientUser1);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/id/" + testClientUser1.getClientUserId(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test
	public void testGetClientUserByIdUnsuccess() throws Exception {
		when(clientUserServ.findClientUserById(testClientUser2.getClientUserId())).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/id/" + testClientUser2.getClientUserId(),
				"Failed to find Client User by id: " + testClientUser2.getClientUserId(),
				status().isNotFound(),
				null);
	}
	
	@Test
	public void testGetClientUserByUserIdSuccess() throws Exception {
		when(userServ.findUserById(testUser1.getUserId())).thenReturn(testUser1);
		when(clientUserServ.findClientUserByUser(testUser1)).thenReturn(testClientUser1);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/user/id/" + testUser1.getUserId(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test
	public void testGetClientUserByUserIdUnsuccess() throws Exception {
		when(userServ.findUserById(testUser2.getUserId())).thenReturn(null);
		when(clientUserServ.findClientUserByUser(null)).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/user/id/" + testUser2.getUserId(),
				"Failed to find Client User by User Id: " + testUser2.getUserId(),
				status().isNotFound(),
				null);
	}
	
	@Test
	public void testGetClientUserByClientIdSuccess() throws Exception {
		when(clientServ.findClientById(testClient1.getClientId())).thenReturn(testClient1);
		when(clientUserServ.findClientUserByClient(testClient1)).thenReturn(testClientUserList);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/client/id/" + testClient1.getClientId(),
				jsonHelper.asJSONString(testClientUserList),
				status().isOk(),
				null);
	}
	
	@Test
	public void testGetClientUserByClientIdUnsuccess() throws Exception {
		List<ClientUser> testClientUserListFail = new ArrayList<ClientUser>();
		when(clientServ.findClientById(testClient2.getClientId())).thenReturn(null);
		when(clientUserServ.findClientUserByClient(testClient2)).thenReturn(testClientUserListFail);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/client/id/" + testClient2.getClientId(),
				"Failed to find Client Users by Client Id: " + testClient2.getClientId(),
				status().isNotFound(),
				null);
	}
	
	@Test
	public void testGetClientUserByUserEmailSuccess() throws Exception {
		when(userServ.findUserByEmail(testUser1.getEmail())).thenReturn(testUser1);
		when(clientUserServ.findClientUserByUser(testUser1)).thenReturn(testClientUser1);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/user/email/" + testUser1.getEmail(),
				jsonHelper.asJSONString(testClientUser1),
				status().isOk(),
				null);
	}
	
	@Test
	public void testGetClientUserByUserEmailUnsuccess() throws Exception {
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(null);
		when(clientUserServ.findClientUserByUser(null)).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/user/email/" + testUser2.getEmail(),
				"Failed to find Client User by User Email: " + testUser2.getEmail(),
				status().isNotFound(),
				null);
	}
	
	@Test
	public void testGetClientUserByCompanyNameSuccess() throws Exception {
		when(clientServ.findClientByCompanyName(testClient1.getCompanyName())).thenReturn(testClient1);
		when(clientUserServ.findClientUserByClient(testClient1)).thenReturn(testClientUserList);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/client/name/" + testClient1.getCompanyName(),
				jsonHelper.asJSONString(testClientUserList),
				status().isOk(),
				null);
	}
	
	@Test
	public void testGetClientUserByCompanyNameUnsuccess() throws Exception {
		List<ClientUser> testClientUserListFail = new ArrayList<ClientUser>();
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(null);
		when(clientUserServ.findClientUserByClient(null)).thenReturn(testClientUserListFail);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"GET",
				"/api/clientUser/client/name/" + testClient2.getCompanyName(),
				"Failed to find Client Users by Company Name: " + testClient2.getCompanyName(),
				status().isNotFound(),
				null);
	}
	
	@Test
	public void testCreateClientUserSuccess() throws Exception {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(testClientUser2);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser",
				jsonHelper.asJSONString(testClientUser2),
				status().isCreated(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test
	public void testCreateClientUserUnsuccessByClient() throws Exception {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser",
				"Failed to create Client User: Company Name '" + testClient2.getCompanyName() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test
	public void testCreateClientUserUnsuccessByUser() throws Exception {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser",
				"Failed to create Client User: user with email '" + testUser2.getEmail() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test
	public void testCreateClientUserUnsuccessByClientUser() throws Exception {
		when(clientServ.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testUser2.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser",
				"Failed to create Client User: client user'" + testClientUser2 + "', already exist.",
				status().isConflict(),
				jsonHelper.asJSONString(testClientUser2));
	}
	
	@Test
	public void testCreateClientUserByRequestObjectSuccess() throws Exception {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(testClientUser2);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser/request",
				jsonHelper.asJSONString(testClientUser2),
				status().isCreated(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test
	public void testCreateClientUserByRequestObjectUnsuccessByClient() throws Exception {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser/request",
				"Failed to create Client User: Company Name '" + testClient2.getCompanyName() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test
	public void testCreateClientUserByRequestObjectUnsuccessByUser() throws Exception {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser/request",
				"Failed to create Client User: user with email '" + testUser2.getEmail() + "', doesn't exist in the system",
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test
	public void testCreateClientUserByRequestObjectUnsuccessByClientUser() throws Exception {
		when(clientServ.findClientByCompanyName(testClientUserRequestObject.getCompanyName())).thenReturn(testClient2);
		when(userServ.findUserByEmail(testClientUserRequestObject.getEmail())).thenReturn(testUser2);
		when(clientUserServ.createClientUser(testClient2, testUser2)).thenReturn(null);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"POST",
				"/api/clientUser/request",
				"Failed to create Client User: client user with user email'" + testClientUserRequestObject.getEmail() + "', already exist.",
				status().isConflict(),
				jsonHelper.asJSONString(testClientUserRequestObject));
	}
	
	@Test
	public void testDeleteClientUserSuccess() throws Exception {
		when(clientUserServ.deleteClientUser(testClientUser1)).thenReturn(true);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"DELETE",
				"/api/clientUser",
				"Client User: " + testClientUser1 + ", was deleted",
				status().isOk(),
				jsonHelper.asJSONString(testClientUser1));
	}
	
	@Test
	public void testDeleteClientUserUnsuccess() throws Exception {
		when(clientUserServ.deleteClientUser(testClientUser2)).thenReturn(false);
		this.mockMvcPerformHelper.mockPerform(this.mockMvc,
				"DELETE",
				"/api/clientUser",
				"Failed to find Client User with ID: " + testClientUser2.getClientUserId(),
				status().isNotFound(),
				jsonHelper.asJSONString(testClientUser2));
	}
}
