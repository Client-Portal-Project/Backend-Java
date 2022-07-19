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
    private NeedDao needDao;

    /**
     * Creates of updates a need object in the database
     *
     * @param need need object
     * @return need object if successful or null otherwise
     */
    public Need saveNeed(Need need) {
        return needDao.save(need);
    }

    /**
     * Deletes a need object in the database
     *
     * @param need need object
     */
    public void deleteNeed(Need need) {
        needDao.delete(need);
    }

    /**
     * Gets a need from the database by using its id
     *
     * @param id id of a need
     * @return need object if found, null otherwise
     */
    public Need getNeed(int id) {
        return needDao.findById(id).orElse(null);
    }

    /**
     * Gets a List of need objects from the database that are linked with a client
     *
     * @param client client object
     * @return List of need objects
     */
    public List<Need> getAllNeeds(Client client) {
        return needDao.findByClient_ClientId(client.getClientId());
    }
}
