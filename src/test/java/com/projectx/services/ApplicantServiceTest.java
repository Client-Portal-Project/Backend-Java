package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.models.User;
import com.projectx.repositories.ApplicantDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicantServiceTest {
    private Applicant expected;

    @Mock
    private ApplicantDao applicantDao;

    @InjectMocks
    private ApplicantService applicantService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, dummy, dummy, dummy, dummy, user);
    }

    @Test
    void testCreateApplicant() {
        when(applicantDao.save(expected)).thenReturn(expected);
        Applicant actual = applicantService.createApplicant(expected);
        assertEquals(actual, expected);
    }

    @Test
    void testUpdateApplicant() {
        when(applicantDao.save(expected)).thenReturn(expected);
        Applicant actual = applicantService.updateApplicant(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteApplicant() {
        applicantService.deleteApplicant(expected);

        verify(applicantDao, times(1)).delete(expected);
    }

    @Test
    void testGetAllApplicant() {
        List<Applicant> list = new ArrayList<>();
        list.add(expected);

        when(applicantDao.findAll()).thenReturn(list);
        List<Applicant> actual = applicantService.getAllApplicants();
        assertEquals(actual, list);
    }

    @Test
    void testGetApplicant() {
        when(applicantDao.findByUser_UserId(expected.getUser().getUserId())).thenReturn(expected);
        Applicant actual = applicantService.getApplicant(1);

        assertEquals(actual, expected);
    }

//    @Test
//    void testGetApplicantByEmploymentStatus() {
//        List<Applicant> list = new ArrayList<>();
//        list.add(expected);
//
//        when(applicantDao.findbyemploymentstatus(expected.getEmployment_status())).thenReturn(list);
//        List<Applicant> actual = applicantService.getApplicantByEmploymentStatus(expected.getEmployment_status());
//
//        assertEquals(actual, list);
//    }

    // Not sure if I like the new Skill() check. Need to look into set retrieval to get/mock an actual skill from mocked applicant
    @Test
    void testGetApplicantBySkillsIsContaining() {
        List<Applicant> list = new ArrayList<>();
        list.add(expected);
        Skill skill = new Skill();
        when(applicantDao.findByApplicantSkillsIsContaining(skill)).thenReturn(list);
        List<Applicant> actual = applicantService.getApplicantSkillsIsContaining(skill);

        assertEquals(actual, list);

    }
}
