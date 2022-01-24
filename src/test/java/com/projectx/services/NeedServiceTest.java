package com.projectx.services;

import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.repositories.NeedDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class NeedServiceTest {
    private Need expected;
    @Mock
    NeedDao needDao;
    @InjectMocks
    NeedService needService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        Client client = new Client(1, null);
        expected = new Need(null, null, null,
                null, null, null, client);
    }

    @Test
    void testSaveNeed() {
        when(needDao.save(expected)).thenReturn(expected);
        Need actual = needService.saveNeed(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testGetNeed() {

    }

    @Test
    void testGetAllNeeds() {

    }
}
