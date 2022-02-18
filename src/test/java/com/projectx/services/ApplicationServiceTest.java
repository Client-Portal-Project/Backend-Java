package com.projectx.services;

import com.projectx.models.*;
import com.projectx.repositories.ApplicationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest {
    private Application expected;
    private List<Application> list;
    @Mock
    private ApplicationDao applicationDao;
    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        Applicant applicant = new Applicant();
        applicant.setApplicantId(1);
        ApplicantOccupation applicantOccupation = new ApplicantOccupation();
        applicantOccupation.setApplicantOccupationalId(1);
        Need need =  new Need();
        need.setNeedId(1);
        expected = new Application(1, null, 0, applicant, applicantOccupation, need);
        list = new ArrayList<>();
        list.add(expected);
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

    @Test
    void testGetApplicationByApplicant() {
        when(applicationDao.findByApplicant_ApplicantId(expected.getApplicant().getApplicantId())).thenReturn(list);
        List<Application> actual = applicationService.getAllApplicationsByApplicant(expected.getApplicant());

        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByApplicantOccupation() {
        when(applicationDao.findByApplicantOccupation_ApplicantOccupationalId(expected
                .getApplicantOccupation().getApplicantOccupationalId())).thenReturn(list);
        List<Application> actual = applicationService
                .getAllApplicationsByApplicantOccupation(expected.getApplicantOccupation());

        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByNeed() {
        when(applicationDao.findByNeed_NeedId(expected.getNeed().getNeedId())).thenReturn(list);
        List<Application> actual = applicationService.getAllApplicationsByNeed(expected.getNeed());

        assertEquals(actual, list);
    }
}
