package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.dtos.ResponseFile;
import com.projectx.models.File;
import com.projectx.services.FileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private FileService fileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = webAppContextSetup(context).build();
        expected = new File("aaa", "test", "test", ("test").getBytes(), null);
        objectMapper = new ObjectMapper();
    }

    //need to test throwing an exception to get bad request and false!!!!!
    @Test @SneakyThrows
    void testUploadFile() {
        MockMultipartFile viable = new MockMultipartFile("file", expected.getName(), expected.getType(),
                expected.getData());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/file/"+viable)
                .file(viable))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        MockMultipartFile unviable = new MockMultipartFile("file", "something wrong here",
                "not viable type", new byte[0]);
        when(fileService.store(unviable)).thenThrow(new IOException());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/file/"+unviable)
                        .file(unviable))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test @SneakyThrows
    void testGetListFiles() {
        List<File> list = new ArrayList<>();
        list.add(expected);
        Stream<File> stream = list.stream();

        ResponseFile result = new ResponseFile(expected.getName(), "http://localhost/fileaaa",
                expected.getType(), 4);
        when(fileService.getAllFiles()).thenReturn(stream);
        mockMvc.perform(MockMvcRequestBuilders.get("/file"))
                .andExpect(status().isOk())
                .andExpect(content().json("["+objectMapper.writeValueAsString(result)+"]"));
    }

    @Test @SneakyThrows
    void testGetFile() {
        when(fileService.getFile(expected.getId())).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/file/{id}", expected.getId())
                .param("id", expected.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new String(expected.getData())));
    }
}
