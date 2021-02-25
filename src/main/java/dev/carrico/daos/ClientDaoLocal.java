package dev.carrico.daos;

import dev.carrico.entities.Client;

import java.util.*;

public class ClientDaoLocal implements ClientDAO{
    public static Map<Integer,Client> clientTable = new HashMap<Integer, Client>();
    public static int idMaker;

    @Override
    public Client createClient(Client client) {
        client.setCliendId(++idMaker);
        clientTable.put(idMaker, client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        return new HashSet<Client>(clientTable.values());
    }

    @Override
    public Client getClientById(int clientId) {
        return clientTable.get(clientId);
    }

    @Override
    public Client updateClient(Client client) {
        clientTable.put(client.getCliendId(), client);
        return clientTable.get(client.getCliendId());
    }

    @Override
    public boolean deleteClientById(int clientId) {
        Client client = clientTable.remove(clientId);
        if (client == null)
            return false;
        return true;
    }
}
