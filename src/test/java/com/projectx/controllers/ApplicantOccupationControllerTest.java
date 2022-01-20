package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.services.ApplicantOccupationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApplicantOccupationControllerTest {
    private static final String URI = "/occupation";

    private ApplicantOccupation expected;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private ApplicantOccupationService applicantOccupationService;
    @InjectMocks
    private ApplicantOccupationController applicantOccupationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(applicantOccupationController).build();
        objectMapper = new ObjectMapper();
        String dummy = "";
        Applicant applicant = new Applicant(1, dummy, dummy, dummy, null, null);
        expected = new ApplicantOccupation(1, dummy, 1, true, applicant);
    }

    @Test @SneakyThrows
    void testCreateApplicantOccupationSuccess() {
        when(applicantOccupationService.getApplicantOccupation(expected.getApplicantOccupationalId())).thenReturn(expected);
        when(applicantOccupationService.saveApplicantOccupation(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test @SneakyThrows
    void testCreateApplicantOccupationFail() {
        when(applicantOccupationService.getApplicantOccupation(expected.getApplicantOccupationalId())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
}
