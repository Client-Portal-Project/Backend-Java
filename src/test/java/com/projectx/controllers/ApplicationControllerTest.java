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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApplicationControllerTest {
    private static final String URI = "/application";
    private Application expected;
    private List<Application> list;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private ApplicationService applicationService;
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
        expected = new Application(1, applicant, occupation, need);

        list = new ArrayList<>();
        list.add(expected);
    }

    @Test
    @SneakyThrows
    void testCreateApplicationSuccess() {
        when(applicationService.saveApplication(expected)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void testCreateApplicationFail() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(expected);
        when(applicationService.saveApplication(expected)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testUpdateApplicationSuccess() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(expected);
        when(applicationService.saveApplication(expected)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void testUpdateApplicationFail() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testDeleteApplicationSuccess() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDeleteApplicationFail() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testGetApplicationSuccess() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.get(URI+"/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void testGetApplicationFail() {
        when(applicationService.getApplication(expected.getApplicationId())).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.get(URI+"/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testGetApplicationByApplicant() {
        when(applicationService.getAllApplicationsByApplicant(expected.getApplicant())).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get(URI+"/applicant")
                        .content(objectMapper.writeValueAsString(expected.getApplicant()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    @SneakyThrows
    void testGetApplicationByOccupation() {
        when(applicationService.getAllApplicationsByApplicantOccupation(expected
                .getApplicantOccupation())).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get(URI+"/occupation")
                        .content(objectMapper.writeValueAsString(expected.getApplicantOccupation()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    @SneakyThrows
    void testGetApplicationByNeed() {
        when(applicationService.getAllApplicationsByNeed(expected.getNeed())).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get(URI+"/need")
                        .content(objectMapper.writeValueAsString(expected.getNeed()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}
