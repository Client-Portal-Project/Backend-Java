package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Applicant;
import com.projectx.models.File;
import com.projectx.models.User;
import com.projectx.services.ApplicantService;
import com.projectx.services.FileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public class FileControllerTest {
    private File expected;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Applicant applicant;
    private static final String URI = "/file";

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private FileService fileService;
    @MockBean
    private ApplicantService applicantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = webAppContextSetup(context).build();
        User user = new User(1, null, null, null, null, true);
        applicant = new Applicant(1, "", "", "", "",
                user);
        expected = new File(1, "test", "test", ("test").getBytes(), null, applicant);
        objectMapper = new ObjectMapper();
    }

    @Test @SneakyThrows
    void testUploadFile() {
        MockMultipartFile viable = new MockMultipartFile("file", expected.getName(), expected.getType(),
                expected.getData());
        mockMvc.perform(MockMvcRequestBuilders.multipart(URI)
                .file(viable)
                .content(objectMapper.writeValueAsString(applicant))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test @SneakyThrows
    void testFailUploadFile() {
        MockMultipartFile wrong = new MockMultipartFile("file", "something is wrong here",
                "wrong type", new byte[0]);
        when(fileService.store(wrong, applicant)).thenThrow(new IOException());
        mockMvc.perform(MockMvcRequestBuilders.multipart(URI)
                        .file(wrong)
                        .content(objectMapper.writeValueAsString(applicant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test @SneakyThrows
    void testGetListFiles() {
        List<File> list = new ArrayList<>();
        list.add(expected);

        List<File> result = new ArrayList<>();
        File temp = expected;
        temp.setSize((long) expected.getData().length);
        temp.setData(("What is this?????").getBytes());
        result.add(temp);

        when(fileService.getAllFiles(applicant)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                .content(objectMapper.writeValueAsString(applicant))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test @SneakyThrows
    void testGetFile() {
        when(fileService.getFile(expected.getId())).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get(URI+"/{id}", expected.getId())
                .param("id", String.valueOf(expected.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new String(expected.getData())));
    }
}
