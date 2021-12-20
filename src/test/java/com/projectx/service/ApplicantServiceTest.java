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
        Blob resume = mock(Blob.class);
        User user = new User(1, dummy, dummy, dummy, dummy, true);
        expected = new Applicant(1, resume, dummy, dummy, dummy, dummy, user);
    }

    @Test
    void testCreateApplicant() {
        when(applicantDao.save(expected)).thenReturn(expected);
        Applicant actual = applicantService.createApplicant(expected);

        assertEquals(actual, expected);
    }
}
