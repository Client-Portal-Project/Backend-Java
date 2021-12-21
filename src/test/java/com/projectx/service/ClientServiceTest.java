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
import com.projectx.repository.ClientDao;

public class ClientServiceTest {
	@Mock
	private ClientDao clientDao;
	
	private ClientService clientServ;
	private Client testClient1;
	private Optional<Client> testClient1Optional;
	private Client testEditClient1;
	private Client testClient2;
	private List<Client> testClientList;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clientServ = new ClientService(clientDao);
		
		testClient1 = new Client(1, "Test Company 1");
		testClient1Optional = Optional.of(testClient1);
		testClient2 = new Client(2, "Test Company 2");
		testEditClient1 = new Client(1, "Test");
		
		testClientList = new ArrayList<Client>();
		testClientList.add(testClient1);
		testClientList.add(testClient2);
		
		when(clientDao.findById(testClient1.getClientId())).thenReturn(testClient1Optional);
		when(clientDao.findById(testClient2.getClientId())).thenReturn(Optional.ofNullable(null));
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
		assertEquals(clientServ.findClientById(testClient2.getClientId()), null);
	}
	
	@Test
	public void testFindClientByCompanyNameSuccess() {
		assertEquals(clientServ.findClientByCompanyName(testClient1.getCompanyName()), testClient1);
	}
	
	@Test
	public void testFindClientByCompanyNameUnsuccess() {
		assertEquals(clientServ.findClientByCompanyName("qwd"), null);
	}
	
	@Test
	public void testCreateClientSuccess() {
		assertEquals(clientServ.createClient(testClient2), testClient2);
	}
	
	@Test
	public void testCreateClientUnsuccess() {
		assertEquals(clientServ.createClient(testClient1), null);
	}
	
	@Test
	public void testEditClientSuccess() {
		assertEquals(clientServ.editClient(testEditClient1), testEditClient1);
	}
	
	@Test
	public void testEditClientUnsuccess() {
		assertEquals(clientServ.editClient(testClient2), null);
	}
	
	@Test
	public void testDeleteClientSuccess() {
		assertEquals(clientServ.deleteClient(testClient1), true);
	}
	
	@Test
	public void testDeleteClientUnsuccess() {
		assertEquals(clientServ.deleteClient(testClient2), false);
	}
	
}
