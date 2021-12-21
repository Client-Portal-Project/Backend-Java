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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicantControllerTest {
    private Applicant expected;
    @Autowired
    private MockMvc mvc;
    @Mock
    private ApplicantService applicantService;
    @InjectMocks
    private ApplicantController applicantController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, 1, dummy, dummy, dummy, dummy, user);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping
//    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
//        Applicant createdApplicant = applicantService.createApplicant(applicant);
//        if (createdApplicant != null) {
//            return new ResponseEntity<>(createdApplicant, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    @Test
    void testCreateApplicant() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/applicant")
                .content(asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(expected)));
    }
}
