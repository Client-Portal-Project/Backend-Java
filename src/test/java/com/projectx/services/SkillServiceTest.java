package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.repositories.SkillDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SkillServiceTest {
    private Skill expected;
    @Mock
    private SkillDao skillDao;
    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        Set<Applicant> set = new HashSet<>();
        Applicant applicant = new Applicant();
        set.add(applicant);
        expected = new Skill(1, null, set);
    }

    @Test
    void testSaveSkill() {
        when(skillDao.save(expected)).thenReturn(expected);
        Skill actual = skillService.saveSkill(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteSkill() {
        skillService.deleteSkill(expected);
        verify(skillDao, times(1)).delete(expected);
    }

    @Test
    void testGetSkill() {
        
    }
}
