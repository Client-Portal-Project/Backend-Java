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

import static org.junit.jupiter.api.Assertions.*;
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
        Need need =  new Need();
        Client client = new Client(1);
        need.setNeedId(1);
        expected = new Application(1, 0, applicant, applicantOccupation, need, client);
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
        Optional<Application> actual = applicationService.findById(1);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), expected);
    }

  @Test
    void testGetApplicationByApplicant() {
        when(applicationDao.findByApplicant_ApplicantId(expected.getApplicant().getApplicantId())).thenReturn(list);
        List<Application> actual = applicationService.getAllApplicationsByApplicant(expected.getApplicant());

        assertFalse(actual.isEmpty());
        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByApplicantOccupation() {
        when(applicationDao.findByApplicantOccupation_ApplicantOccupationalId(expected
                .getApplicantOccupation().getApplicantOccupationalId())).thenReturn(list);
        List<Application> actual = applicationService
                .getAllApplicationsByApplicantOccupation(expected.getApplicantOccupation());

        assertFalse(actual.isEmpty());
        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByNeed() {
        when(applicationDao.findbyneed_needid(expected.getNeed().getNeedId())).thenReturn(list);
        List<Application> actual = applicationService.getAllApplicationsByNeed(expected.getNeed());

        assertFalse(actual.isEmpty());
        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByEmploymentStatusAndNeed(){
        when(applicationDao.findApplicationsByApplicant_EmploymentStatusAndNeed_NeedId(expected.getApplicant().getEmploymentStatus()
                , expected.getNeed().getNeedId())).thenReturn(list);
        List<Application> actual = applicationService.getApplicationByEmploymentStatusAndNeed(expected.getApplicant().getEmploymentStatus()
                , expected.getNeed().getNeedId());

        assertFalse(actual.isEmpty());
        assertEquals(actual, list);
    }

    @Test
    void testGetApplicationByEmploymentStatusAndClient(){
        when(applicationDao.findApplicationsByApplicant_EmploymentStatusAndClient_ClientId(expected.getApplicant().getEmploymentStatus()
                , expected.getClient().getClientId()))
                .thenReturn(list);
        List<Application> actual = applicationService.getApplicationByEmploymentStatusAndClient(expected.getApplicant().getEmploymentStatus()
                , expected.getClient().getClientId());

        assertFalse(actual.isEmpty());
        assertEquals(actual, list);
    }

    @Test
    void getApplicationByClient() {
        when(applicationDao.findApplicationsByClient_ClientId(expected.getClient().getClientId()))
                .thenReturn(list);
        List<Application> actual = applicationService.getApplicationByClient(expected.getClient().getClientId());

        assertFalse(actual.isEmpty());
        assertEquals(actual,list);
    }
}
