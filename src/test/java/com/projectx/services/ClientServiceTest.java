package com.projectx.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projectx.models.Client;
import com.projectx.repositories.ClientDao;

public class ClientServiceTest {
	@Mock
	private ClientDao clientDao;

	@InjectMocks
	private ClientService clientServ;

	private Client testClient1;
	private Optional<Client> testClient1Optional;
	private Client testEditClient1;
	private Client testClient2;
	private List<Client> testClientList;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		clientServ = new ClientService(clientDao);
		
		testClient1 = new Client(1, "Test Company 1");
		testClient1Optional = Optional.of(testClient1);
		testClient2 = new Client(2, "Test Company 2");
		testEditClient1 = new Client(1, "Test");
		
		testClientList = new ArrayList<Client>();
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
	
}
