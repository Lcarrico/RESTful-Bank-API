package dev.carrico.services;

import dev.carrico.entities.Account;
import dev.carrico.entities.Client;

import java.util.Set;

public interface ClientService {

    Client createClient(Client client);

    Set<Client> getAllClients();

    Client getClientById(int clientId);

    Client updateClient(Client client);

    boolean deleteClientById(int clientId);
}
