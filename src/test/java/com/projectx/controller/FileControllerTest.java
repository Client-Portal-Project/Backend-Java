package com.projectx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.model.File;
import com.projectx.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FileControllerTest {
    private File expected;
    private MockMvc mvc;
    @Mock
    private FileService fileService;
    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(fileController).build();
        expected = new File("aaa", "test", "test", ("test").getBytes(), null);
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

    //need to test throwing an exception to get bad request and false!!!!!
    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile viable = new MockMultipartFile("file", expected.getName(), expected.getType(),
                expected.getData());
        mvc.perform(MockMvcRequestBuilders.multipart("/api/upload")
                .file(viable))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetListFiles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/files"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

//    @CrossOrigin
//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        File file = fileService.getFile(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .body(file.getData());
//    }
    @Test
    void testGetFile() throws Exception {
        when(fileService.getFile(any(String.class))).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.get("/api/files/{id}", "aaa")
                .param("id", "aaa"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(expected.getData())));
    }
}
