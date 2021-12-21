package com.projectx.service;

import com.projectx.model.Applicant;
import com.projectx.model.User;
import com.projectx.repository.ApplicantDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicantServiceTest {
    private Applicant expected;
    @Mock
    ApplicantDao applicantDao;
    @InjectMocks
    private ApplicantService applicantService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, 1, dummy, dummy, dummy, dummy, user);
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
        Applicant actual = applicantService.deleteApplicant(expected);
        assertEquals(actual, expected);

        actual = applicantService.updateApplicant(null);
        assertNull(actual);
    }

    @Test
    void testGetAllApplicant() {
        List<Applicant> list = new ArrayList();
        list.add(expected);

        when(applicantDao.findAll()).thenReturn(list);
        List<Applicant> actual = applicantService.getAllApplicants();
        assertEquals(actual, list);
    }

    @Test
    void TestGetApplicant() {
        when(applicantDao.findByUser_UserId(expected.getUser().getUserId())).thenReturn(expected);
        Applicant actual = applicantService.getApplicant(1);

        assertEquals(actual, expected);
    }
}
