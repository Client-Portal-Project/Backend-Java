package com.projectx.services;

import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.repositories.NeedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("needService")
public class NeedService {
    @Autowired
    NeedDao needDao;

    public Need saveNeed(Need need) {
        return needDao.save(need);
    }

    public void deleteNeed(Need need) {
        needDao.delete(need);
    }

    public Need getNeed(int id) {
        return needDao.findById(id).orElse(null);
    }

    public List<Need> getAllNeeds(Client client) {
        return needDao.findByClient_ClientId(client.getClientId());
    }
}
