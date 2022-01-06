package com.projectx.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Mock
    FileDao fileDao;
    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String dummy = "";
        expected = new File(dummy, dummy, null);
    }

    @Test
    void testStore() throws IOException {
        MultipartFile input = new MockMultipartFile("aaa", "", "", (byte[]) null);
        File outputFile = new File(StringUtils.cleanPath(input.getOriginalFilename()), input.getContentType(),
                input.getBytes());
        when(fileDao.save(outputFile)).thenReturn(expected);
        File actual = fileService.store(input);

        assertEquals(actual, expected);
    }

    @Test
    void testGetFile() {
        when(fileDao.findById("aaa")).thenReturn(Optional.of(expected));
        File actual = fileService.getFile("aaa");

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllFiles() {
        List<File> list = new ArrayList<>();
        list.add(expected);
        when(fileDao.findAll()).thenReturn(list);
        List<File> actual = fileService.getAllFiles().map(dbFile ->
                new File(dbFile.getName(), dbFile.getType(), dbFile.getData())
        ).collect(Collectors.toList());

        assertEquals(actual, list);
    }
}
