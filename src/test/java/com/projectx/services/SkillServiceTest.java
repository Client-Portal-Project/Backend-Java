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
        
    }
}
