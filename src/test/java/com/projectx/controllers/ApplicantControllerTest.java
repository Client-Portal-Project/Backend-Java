package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Applicant;
import com.projectx.models.User;
import com.projectx.services.ApplicantService;
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
public class ApplicantControllerTest {
    private static final String URI = "/applicant";

    private Applicant expected;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private ApplicantService applicantService;
    @InjectMocks
    private ApplicantController applicantController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(applicantController).build();
        this.objectMapper = new ObjectMapper();
        String dummy = "";
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, dummy, dummy, dummy, dummy, user);
    }

    @Test @SneakyThrows
    void testCreateApplicant() {
        when(applicantService.createApplicant(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        Applicant wrong = expected;
        wrong.setUser(null);
        when(applicantService.createApplicant(wrong)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testUpdateApplicant() {
        when(applicantService.updateApplicant(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.put(URI)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        //try to update when applicant is not in the database
        Applicant wrong = new Applicant();
        when(applicantService.updateApplicant(wrong)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testDeleteApplicant() {
        //expected is already in the database
        when(applicantService.getApplicant(expected.getApplicantId())).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        //wrong is not in the database
        Applicant wrong = expected;
        wrong.setApplicantId(0);
        when(applicantService.getApplicant(0)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test @SneakyThrows
    void testGetApplicant() {
        when(applicantService.getApplicant(expected.getUser().getUserId())).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", 1)
                        .param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        //no applicant with user id of 2 exists in database
        when(applicantService.getApplicant(2)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", 2)
                        .param("id", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testGetAllApplicants() {
        List<Applicant> list = new ArrayList<>();
        list.add(expected);
        when(applicantService.getAllApplicants()).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get(URI))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}
