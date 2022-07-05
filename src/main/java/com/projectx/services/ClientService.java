package com.projectx.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.projectx.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.models.Client;
import com.projectx.repositories.ClientDao;

@Service("clientService")
public class ClientService {
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private UserService userService;

    /**
     * Get List of all the clients in the database
     *
     * @return a List of client objects
     */
    // Currently, for testing purposes to see the User data in Postman
    public List<Client> findAllClients() {
        return this.clientDao.findAll();
    }

    /**
     * Gets a client from the database by its id
     *
     * @param clientId id of the client
     * @return a client object or null if it does not exist
     */
    public Client findClientById(Integer clientId) {
        return this.clientDao.findById(clientId).orElse(null);
    }

    /**
     * Gets a client from the database by its company name
     *
     * @param companyName name of a company
     * @return a client object or null if it does not exist
     */
    public Client findClientByCompanyName(String companyName) {
        return this.clientDao.findClientByCompanyName(companyName);
    }

    /**
     * Creates a new client in the database
     *
     * @param client client object
     * @return a client object or null if it exists already
     */
    public Client createClient(Client client) {
        Client temp = this.clientDao.findClientByCompanyName(client.getCompanyName());
        if(temp != null) {
            return null;
        } else {
            return this.clientDao.save(client);
        }
    }

    /**
     * Updates a client in the database
     *
     * @param client client object
     * @return a client object or null if it does not exist
     */
    public Client editClient(Client client) {
    	Client temp = this.clientDao.findById(client.getClientId()).orElse(null);
    	if(temp == null) {
    		 return null;
    	} else {
    		temp.setCompanyName(client.getCompanyName());
    		return this.clientDao.save(temp);
    	}
    }

    /**
     * Deletes a client in the database
     *
     * @param client client object
     * @return true if the client is deleted, false otherwise
     */
    public boolean deleteClient(Client client) {
    	Client temp = this.clientDao.findClientByCompanyName(client.getCompanyName());
    	if(temp == null) {
   		 	return false;
    	} else {
    		this.clientDao.delete(temp);
    		return true;
    	}
    }

    /**
     * Gets a List of users attached to a client
     *
     * @param client client object
     * @return List of users
     */
    public List<User> findAllClientUsers(Client client) {
        Client temp = findClientById(client.getClientId()); //checks if client exists
        return new ArrayList<>(temp.getUser());
    }

    /**
     * Gets a user within a client object with the user's id
     *
     * @param client client object
     * @param id id of a user
     * @return user object or null if not found
     */
    public User findClientUser(Client client, int id) {
        List<User> list = findAllClientUsers(client); //findAllClientUsers already gets the list already
        for (User u : list) { //checks for the user
            if (u.getUserId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * Creates a user within a client object with the user's id
     *
     * @param client client object
     * @param id id of the user
     * @return true is the user is created within the client, false otherwise
     */
    public Boolean createClientUser(Client client, int id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return false;
        } else { //takes list from client from database and updates it
            Client temp = findClientById(client.getClientId());
            Set<User> list = temp.getUser();
            Boolean result = list.add(user); //true if added, false if already a duplicate
            temp.setUser(list);
            clientDao.save(temp); //updates list in database
            return result;
        }
    }

    /**
     * Deletes a user within a client object with the user's id
     *
     * @param client client object
     * @param id id of the user
     * @return true is the user is deleted  from within the client, false otherwise
     */
    public Boolean deleteClientUser(Client client, int id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return false;
        } else { //takes list from client from database and updates it
            Client temp = findClientById(client.getClientId());
            Set<User> list = temp.getUser();
            Boolean result = list.remove(user); //true if deleted, false if it does not exist
            temp.setUser(list);
            clientDao.save(temp); //updates list in database
            return result;
        }
    }
}