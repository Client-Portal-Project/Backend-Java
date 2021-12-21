package com.projectx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.model.Applicant;
import com.projectx.model.User;
import com.projectx.service.ApplicantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
public class ApplicantControllerTest {
    private Applicant expected;
    private MockMvc mvc;
    @Mock
    private ApplicantService applicantService;
    @InjectMocks
    private ApplicantController applicantController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(applicantController).build();
        String dummy = "";
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, 1, dummy, dummy, dummy, dummy, user);
    }

    //converts Object into a Json String
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateApplicant() throws Exception {
        when(applicantService.createApplicant(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post("/applicant")
                .content(asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(expected)));

        Applicant wrong = expected;
        wrong.setUser(null);
        when(applicantService.createApplicant(wrong)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/applicant")
                        .content(asJsonString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testUpdateApplicant() throws Exception {
        when(applicantService.updateApplicant(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.put("/applicant")
                .content(asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expected)));

        when(applicantService.updateApplicant(null)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.put("/applicant")
                        .content(asJsonString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testDeleteApplicant() throws Exception {
        //expected is already in the database
        when(applicantService.getApplicant(expected.getApplicantId())).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.delete("/applicant")
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        //wrong is not in the database
        Applicant wrong = expected;
        wrong.setApplicantId(0);
        when(applicantService.getApplicant(0)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.delete("/applicant")
                        .content(asJsonString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test
    void testGetApplicant() throws Exception {
        when(applicantService.getApplicant(1)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.get("/applicant/{id}", 1)
                        .param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(content().json(asJsonString(expected)));

        //no applicant with id of 2 exists in database
        when(applicantService.getApplicant(2)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/applicant/{id}", 2)
                        .param("id", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testGetAllApplicants() throws Exception {
        List<Applicant> list = new ArrayList();
        list.add(expected);
        when(applicantService.getAllApplicants()).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get("/applicant"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(list)));
    }
}
