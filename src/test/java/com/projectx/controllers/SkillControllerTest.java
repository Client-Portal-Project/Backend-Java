package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.services.SkillService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerTest {
    private static final String URI = "/skill";

    private Skill expected;
    private Skill wrong;
    private Applicant applicant;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private SkillService skillService;
    @InjectMocks
    private SkillController skillController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(skillController).build();
        objectMapper = new ObjectMapper();
        Set<Applicant> set = new HashSet<>();
        String dummy = "";
        applicant = new Applicant(1, dummy, dummy, dummy, dummy, null);
        set.add(applicant);
        expected = new Skill(1, dummy, set);
        wrong = new Skill(2, null, null);
    }

    @Test
    @SneakyThrows
    void testCreateSkillSuccess() {
        when(skillService.getSkill(expected.getSkill_id())).thenReturn(null);
        when(skillService.saveSkill(expected)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void testCreateSkillFail() {
        when(skillService.getSkill(wrong.getSkill_id())).thenReturn(wrong);
        mvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testUpdateSkillSuccess() {
        when(skillService.getSkill(expected.getSkill_id())).thenReturn(expected);
        when(skillService.saveSkill(expected)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void testUpdateSkillFail() {
        when(skillService.getSkill(wrong.getSkill_id())).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testDeleteSkillSuccess() {
        when(skillService.getSkill(expected.getSkill_id())).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.delete(URI)
                     .content(objectMapper.writeValueAsString(expected))
                     .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDeleteSkillFail() {
        when(skillService.getSkill(wrong.getSkill_id())).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete(URI)
                        .content(objectMapper.writeValueAsString(wrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testGetSkill() {
        when(skillService.getSkill(expected.getSkill_id())).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", 1) //success
                        .param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        when(skillService.getSkill(2)).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", 2) //fail
                        .param("id", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    @SneakyThrows
    void testGetAllSkills() {
        List<Skill> list = new ArrayList<>();
        list.add(expected);
        when(skillService.getAllSkills(applicant)).thenReturn(list);

        mvc.perform(MockMvcRequestBuilders.get(URI)
                .content(objectMapper.writeValueAsString(applicant))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}
