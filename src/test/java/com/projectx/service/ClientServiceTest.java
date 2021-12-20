package com.projectx.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
	private Client testClient2;
	private List<Client> testClientList;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clientServ = new ClientService(clientDao);
		
		testClient1 = new Client(1, "Test Company 1");
		testClient2 = new Client(2, "Test Company 2");
		
		testClientList = new ArrayList<Client>();
		
		when(clientDao.getById(testClient1.getClientId())).thenReturn(testClient1);
	}
	
	@Test
	public void testFindClientByIdSuccess() {
		assertEquals(clientServ.findClientById(testClient1.getClientId()), testClient1);
	}
	
	@Test
	public void testFindClientByIdUnsuccess() {
		assertEquals(clientServ.findClientById(0), null);
	}
}
