package dev.carrico.daos;

import dev.carrico.entities.Client;

import java.util.Set;

public interface ClientDAO {

    // create client
    Client createClient(Client client);

    // read clients
    Set<Client> getAllClients();
    Client getClientById(int clientId);

    // update clients
    Client updateClient(Client client);

    // delete client
    boolean deleteClientById(int clientId);


}
