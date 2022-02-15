package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.repositories.SkillDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

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
        String dummy = "";
        Applicant applicant = new Applicant(1, dummy, dummy, dummy, dummy, null);
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
        when(skillDao.findById(1)).thenReturn(Optional.of(expected));
        Skill actual = skillService.getSkill(expected.getSkillId());

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllSkills() {
        List<Skill> list = new ArrayList<>();
        list.add(expected);
        when(skillDao.findByApplicant_ApplicantId(1)).thenReturn(list);
        Applicant[] array = expected.getApplicants().toArray(new Applicant[expected.getApplicants().size()]);
        List<Skill> actual = skillService.getAllSkills(array[0]);

        assertEquals(actual, list);
    }
}
