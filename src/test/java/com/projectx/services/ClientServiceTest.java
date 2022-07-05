package com.projectx.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import com.projectx.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.projectx.models.Client;
import com.projectx.repositories.ClientDao;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {
	@Mock
	private ClientDao clientDao;
	@Mock
	private UserService userService;

	@InjectMocks
	private ClientService clientServ;

	private Client testClient1;
	private Optional<Client> testClient1Optional;
	private Client testEditClient1;
	private Client testClient2;
	private List<Client> testClientList;
	private User testUser;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		testUser = new User(1, "test1", "", "", "", true);
		User testUserExtra = new User(2, "test2", "", "", "", true);
		testClient1 = new Client(1, "Test Company 1");
		Set<User> expected = new HashSet<>();
		expected.add(testUser);
		testClient1.setUser(expected);
		testClient1Optional = Optional.of(testClient1);
		testClient2 = new Client(2, "Test Company 2");
		testEditClient1 = new Client(1, "Test");
		testEditClient1.setUser(expected);
		
		testClientList = new ArrayList<>();
		testClientList.add(testClient1);
		testClientList.add(testClient2);
		
		when(clientDao.findById(testClient1.getClientId())).thenReturn(testClient1Optional);
		when(clientDao.findById(testClient2.getClientId())).thenReturn(Optional.empty());
		when(clientDao.findAll()).thenReturn(testClientList);
		when(clientDao.findClientByCompanyName(testClient1.getCompanyName())).thenReturn(testClient1);
		when(clientDao.findClientByCompanyName("qwd")).thenReturn(null);
		when(clientDao.findClientByCompanyName(testClient2.getCompanyName())).thenReturn(null);
		when(clientDao.save(testClient2)).thenReturn(testClient2);
		when(clientDao.save(testEditClient1)).thenReturn(testEditClient1);

		when(userService.findUserById(testUser.getUserId())).thenReturn(testUser);
		when(userService.findUserById(3)).thenReturn(null);
		when(userService.findUserById(2)).thenReturn(testUserExtra);
	}
	
	@Test
	public void testFindAllClients() {
		assertEquals(clientServ.findAllClients(), testClientList);
	}
	
	@Test
	public void testFindClientByIdSuccess() {
		assertEquals(Optional.of(clientServ.findClientById(testClient1.getClientId())), testClient1Optional);
	}
	
	@Test
	public void testFindClientByIdUnsuccess() {
		assertNull(clientServ.findClientById(testClient2.getClientId()));
	}
	
	@Test
	public void testFindClientByCompanyNameSuccess() {
		assertEquals(clientServ.findClientByCompanyName(testClient1.getCompanyName()), testClient1);
	}
	
	@Test
	public void testFindClientByCompanyNameUnsuccess() {
		assertNull(clientServ.findClientByCompanyName("qwd"));
	}
	
	@Test
	public void testCreateClientSuccess() {
		assertEquals(clientServ.createClient(testClient2), testClient2);
	}
	
	@Test
	public void testCreateClientUnsuccess() {
		assertNull(clientServ.createClient(testClient1));
	}
	
	@Test
	public void testEditClientSuccess() {
		assertEquals(clientServ.editClient(testEditClient1), testEditClient1);
	}
	
	@Test
	public void testEditClientUnsuccess() {
		assertNull(clientServ.editClient(testClient2));
	}
	
	@Test
	public void testDeleteClientSuccess() {
		assertTrue(clientServ.deleteClient(testClient1));
	}
	
	@Test
	public void testDeleteClientUnsuccess() {
		assertFalse(clientServ.deleteClient(testClient2));
	}

	@Test
	void testFindAllClientUsers() {
		assertArrayEquals(clientServ.findAllClientUsers(testClient1).toArray(), testClient1.getUser().toArray()); //to not use array, need to override hashcode()
	}

	@Test
	void testFindClientUser() {
		assertNull(clientServ.findClientUser(testClient1, 3));
		assertEquals(clientServ.findClientUser(testClient1, 1), testUser);
	}

	@Test
	void testCreateClientUser() {
		assertFalse(clientServ.createClientUser(testClient1, 3));
		assertFalse(clientServ.createClientUser(testClient1, 1));
		assertTrue(clientServ.createClientUser(testClient1, 2));
	}

	@Test
	void testDeleteClientUser() {
		assertFalse(clientServ.deleteClientUser(testClient1, 3));
		assertTrue(clientServ.deleteClientUser(testClient1, 1));
		assertFalse(clientServ.deleteClientUser(testClient1, 2));
	}
}
