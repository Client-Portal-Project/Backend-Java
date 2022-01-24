package com.projectx.services;

import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.repositories.NeedDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NeedService {
    @Autowired
    NeedDao needDao;

    public Need saveNeed(Need need) {
        return needDao.save(need);
    }

    public Need getNeed(int id) {
        return needDao.getById(id);
    }

    public List<Need> getAllNeeds(Client client) {
        return needDao.findByNeed_ClientId(client.getClientId());
    }
}
