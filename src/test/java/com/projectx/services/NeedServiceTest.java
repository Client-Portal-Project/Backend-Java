package com.projectx.services;

import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.repositories.NeedDao;
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
        expected = new Need(1, null, null, null,
                null, null, null, null, client, null, null);
    }

    @Test
    void testSaveNeed() {
        when(needDao.save(expected)).thenReturn(expected);
        Need actual = needService.saveNeed(expected);

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteNeed() {
        needService.deleteNeed(expected);
        verify(needDao, times(1)).delete(expected);
    }

    @Test
    void testGetNeed() {
        when(needDao.findById(expected.getNeed_id())).thenReturn(Optional.of(expected));
        Need actual = needService.getNeed(1);

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllNeeds() {
        List<Need> list = new ArrayList<>();
        list.add(expected);
        when(needDao.findByClient_ClientId(expected.getClient().getClientId())).thenReturn(list);
        List<Need> actual = needService.getAllNeeds(expected.getClient());

        assertEquals(list, actual);
    }
}
