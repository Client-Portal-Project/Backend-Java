package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.repositories.ApplicantOccupationDao;
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

public class ApplicantOccupationServiceTest {
    private ApplicantOccupation expected;

    @Mock
    private ApplicantOccupationDao applicantOccupationDao;

    @InjectMocks
    private ApplicantOccupationService applicantOccupationService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        Applicant applicant = new Applicant(1, dummy, dummy, dummy, dummy, null);
        expected = new ApplicantOccupation(1, dummy, 1, true, applicant);
    }

    @Test
    void testSaveApplicantOccupation() {
        when(applicantOccupationDao.save(expected)).thenReturn(expected);
        ApplicantOccupation actual = applicantOccupationService.saveApplicantOccupation(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteApplicantOccupation() {
        applicantOccupationService.deleteApplicantOccupation(expected);
        verify(applicantOccupationDao, times(1)).delete(expected);
    }

    @Test
    void testGetApplicantOccupation() {
        when(applicantOccupationDao.findById(expected.getApplicantOccupationalId())).thenReturn(Optional.of(expected));
        ApplicantOccupation actual = applicantOccupationService
                .getApplicantOccupation(expected.getApplicantOccupationalId());

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllApplicantOccupation() {
        List<ApplicantOccupation> list = new ArrayList<>();
        list.add(expected);
        when(applicantOccupationDao.findByApplicant_ApplicantId(expected.getApplicant()
                .getApplicantId())).thenReturn(list);
        List<ApplicantOccupation> actual = applicantOccupationService
                .getAllApplicantOccupation(expected.getApplicant());

        assertEquals(list, actual);
    }
}
