package com.projectx.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projectx.models.Applicant;
import com.projectx.models.File;
import com.projectx.repositories.FileDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FileServiceTest {
    private File expected;
    private Applicant applicant;
    @Mock
    FileDao fileDao;
    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        applicant = new Applicant(1, dummy, dummy, dummy, dummy, null, null);
        expected = new File(dummy, dummy, null, applicant);
    }

    @Test
    void testStore() throws IOException {
        MultipartFile input = new MockMultipartFile("aaa", "", "", (byte[]) null);
        File outputFile = new File(StringUtils.cleanPath(input.getOriginalFilename()), input.getContentType(),
                input.getBytes(), applicant);
        when(fileDao.save(outputFile)).thenReturn(expected);
        File actual = fileService.store(input, applicant);

        assertEquals(actual, expected);
    }

    @Test
    void testGetFile() {
        when(fileDao.findById(1)).thenReturn(Optional.of(expected));
        File actual = fileService.getFile(1);

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllFiles() {
        List<File> list = new ArrayList<>();
        list.add(expected);
        when(fileDao.findByApplicant_ApplicantId(applicant.getApplicantId()));
        List<File> actual = fileService.getAllFiles(applicant);
        assertEquals(actual, list);
    }
}
