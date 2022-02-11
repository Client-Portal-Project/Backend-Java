package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.*;
import com.projectx.services.ApplicationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class ApplicationControllerTest {
    private static final String URI = "/application";
    private Application expected;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private ApplicationService applicantService;
    @InjectMocks
    private ApplicationController applicationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(applicationController).build();
        objectMapper = new ObjectMapper();
        Applicant applicant = new Applicant();
        ApplicantOccupation occupation = new ApplicantOccupation();
        Need need = new Need();
        expected = new Application(1, null, null, applicant, occupation, need);
    }

    @Test
    @SneakyThrows
    void testCreateApplication() {

    }
}
