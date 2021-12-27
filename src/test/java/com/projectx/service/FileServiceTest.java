package com.projectx.service;

import java.util.stream.Stream;
import com.projectx.model.File;
import com.projectx.repository.FileDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
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
        MultipartFile input = new MockMultipartFile("", "", "", (byte[]) null);
        when(fileDao.save(expected)).thenReturn(expected);
        File actual = fileService.store(input);

        assertEquals(actual, expected);
    }

    @Test
    void testGetFile() {
        when(fileDao.findById("aaa").get()).thenReturn(expected);
        File actual = fileService.getFile("aaa");

        assertEquals(actual, expected);
    }

//    public Stream<File> getAllFiles() {
//        return fileRepository.findAll().stream();
//    }
    @Test
    void testGetAllFiles() {
        Stream<File> result = Stream.of(expected);
        when(fileDao.findAll().stream()).thenReturn(result);

        Stream<File> actual = fileService.getAllFiles();
        
        assertEquals(actual, result);
    }
}
