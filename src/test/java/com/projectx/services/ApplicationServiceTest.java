package com.projectx.services;

import com.projectx.models.*;
import com.projectx.repositories.ApplicationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest {
    private Application expected;
    @Mock
    private ApplicationDao applicationDao;
    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        Applicant applicant = new Applicant();
        ApplicantOccupation applicantOccupation = new ApplicantOccupation();
        Need need =  new Need();
        expected = new Application(1, null, 0, applicant, applicantOccupation, need);
    }

    @Test
    void testSaveApplication() {
        when(applicationDao.save(expected)).thenReturn(expected);
        Application actual = applicationService.saveApplication(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteApplication() {
        applicationService.deleteApplication(expected);
        verify(applicationDao, times(1)).delete(expected);
    }

    @Test
    void testGetApplication() {
        when(applicationDao.findById(expected.getApplicationId())).thenReturn(Optional.of(expected));
        Application actual = applicationService.getApplication(1);

        assertEquals(actual, expected);
    }
}
