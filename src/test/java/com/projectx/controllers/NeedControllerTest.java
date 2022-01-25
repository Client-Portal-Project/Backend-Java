package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.services.NeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class NeedControllerTest {
    @Mock
    NeedService needService;
    @InjectMocks
    NeedController needController;

    private MockMvc mvc;
    private Need expected;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(needController).build();
        objectMapper = new ObjectMapper();
        String dummy = "";
        Client client = new Client(1, null);
        expected = new Need(1, null, null, null,
                null, null, null, null, client, null);
    }

    @Test
    void testCreateNeed() {
        
    }
}
