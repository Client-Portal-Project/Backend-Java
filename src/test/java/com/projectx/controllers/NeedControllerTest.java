package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.services.NeedService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NeedControllerTest {
    @Mock
    NeedService needService;
    @InjectMocks
    NeedController needController;

    private MockMvc mvc;
    private Need expected;
    private ObjectMapper objectMapper;
    private static final String URI = "/need";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(needController).build();
        objectMapper = new ObjectMapper();
        Client client = new Client(1, null);
        expected = new Need(1, null, null, null,
                null, null, null, null, client, null, null);
    }

    @Test @SneakyThrows
    void testCreateNeedSuccess() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(null);
        when(needService.saveNeed(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test @SneakyThrows
    void testCreateNeedFail() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testEditNeedSuccess() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(expected);
        when(needService.saveNeed(expected)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test @SneakyThrows
    void testEditNeedFail() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testDeleteNeedSuccess() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test @SneakyThrows
    void testDeleteNeedFail() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test @SneakyThrows
    void testGetNeedSuccess() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.get(URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test @SneakyThrows
    void testGetNeedFail() {
        when(needService.getNeed(expected.getNeed_id()))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get(URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test @SneakyThrows
    void testGetAllNeeds() {
        List<Need> list = new ArrayList<>();
        list.add(expected);
        when(needService.getAllNeeds(expected.getClient()))
                .thenReturn(list);

        mvc.perform(MockMvcRequestBuilders.get(URI)
                        .content(objectMapper.writeValueAsString(expected.getClient()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}
