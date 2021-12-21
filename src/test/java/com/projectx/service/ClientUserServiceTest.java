package com.projectx.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projectx.model.Client;
import com.projectx.model.ClientUser;
import com.projectx.model.User;
import com.projectx.repository.ClientUserDao;


public class ClientUserServiceTest {
	@Mock
	private ClientUserDao clientUserDao;
	
	private ClientUserService clientUserService;
	private ClientUser testClientUser1;
	private Optional<ClientUser> testClientUser1Optional;
	private ClientUser testClientUser2;
	private ClientUser testClientUser3;
	private User testUser1;
	private User testUser2;
	private User testUser3;
	private Client testClient1;
	private Client testClient2;
	private Client testClient3;
	private List<ClientUser> testClientUserList;
	private List<ClientUser> testClientUserSearchList1;
	private List<ClientUser> testClientUserSearchList2;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clientUserService = new ClientUserService(clientUserDao);
		
		testClient1 = new Client(1, "Test Company 1");
		testClient2 = new Client(2, "Test Company 2");
		testClient3 = new Client(3, "Test Company 3");
		
		testUser1 = new User(1, "Test1", "Test1", "Test1", "Test1", false);
		testUser2 = new User(2, "Test2", "Test2", "Test2", "Test2", false);
		testUser3 = new User(3, "Test3", "Test3", "Test3", "Test3", false);
		
		testClientUser1 = new ClientUser(1, testUser1, testClient1);
		testClientUser1Optional = Optional.of(testClientUser1);
		testClientUser2 = new ClientUser(2, testUser2, testClient2);
		testClientUser3 = new ClientUser(3, testUser3, testClient3);
		
		testClientUserList = new ArrayList<ClientUser>();
		testClientUserList.add(testClientUser1);
		testClientUserList.add(testClientUser2);
		
		testClientUserSearchList1 = new ArrayList<ClientUser>();
		testClientUserSearchList1.add(testClientUser1);
		
		testClientUserSearchList2 = new ArrayList<ClientUser>();
		
		when(clientUserDao.findAll()).thenReturn(testClientUserList);
		when(clientUserDao.findById(testClientUser1.getClientUserId())).thenReturn(testClientUser1Optional);
		when(clientUserDao.findById(testClientUser2.getClientUserId())).thenReturn(Optional.ofNullable(null));
		when(clientUserDao.findClientUserByClient(testClient1)).thenReturn(testClientUserSearchList1);
		when(clientUserDao.findClientUserByClient(testClient2)).thenReturn(testClientUserSearchList2);
		when(clientUserDao.findClientUserByUser(testUser1)).thenReturn(testClientUser1);
		when(clientUserDao.findClientUserByUser(testUser3)).thenReturn(null);
		when(clientUserDao.save(new ClientUser(testUser3, testClient3))).thenReturn(testClientUser3);
	}
	
	@Test
	public void testFindAllClientUsers() {
		assertEquals(clientUserService.findAllClientUsers(), testClientUserList);
	}
	
	@Test
	public void testFindClientUserByIdSuccess() {
		assertEquals(Optional.of(clientUserService.findClientUserById(testClientUser1.getClientUserId())), testClientUser1Optional);
	}
	
	@Test
	public void testFindClientUserByIdUnsuccess() {
		assertEquals(clientUserService.findClientUserById(testClientUser2.getClientUserId()), null);
	}
	
	@Test
	public void testFindClientUserByClientSuccess() {
		assertEquals(clientUserService.findClientUserByClient(testClient1), testClientUserSearchList1);
	}
	
	@Test
	public void testFindClientUserByClientUnsuccess() {
		assertEquals(clientUserService.findClientUserByClient(testClient3), testClientUserSearchList2);
	}
	
	@Test
	public void testFindClientUserByUserSuccess() {
		assertEquals(clientUserService.findClientUserByUser(testUser1), testClientUser1);
	}
	
	@Test
	public void testFindClientUserByUserUnsuccess() {
		assertEquals(clientUserService.findClientUserByUser(testUser3), null);
	}
	
	@Test
	public void testCreateClientUserSuccess() {
		assertEquals(clientUserService.createClientUser(testClient3, testUser3), testClientUser3);
	}
	
	@Test
	public void testCreateClientUserUnsuccess() {
		assertEquals(clientUserService.createClientUser(testClient1, testUser1), null);
	}
	
	@Test
	public void testDeleteClientUserSuccess() {
		assertEquals(clientUserService.deleteClientUser(testClientUser1), true);
	}
	
	@Test
	public void testDeleteClientUserUnsuccess() {
		assertEquals(clientUserService.deleteClientUser(testClientUser2), false);
	}
	
	
}
