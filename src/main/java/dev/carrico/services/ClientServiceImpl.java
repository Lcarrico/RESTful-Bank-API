package dev.carrico.services;

import dev.carrico.daos.ClientDAO;
import dev.carrico.daos.ClientDaoLocal;
import dev.carrico.entities.Client;
import dev.carrico.exceptions.NoClientFoundException;

import java.util.Set;

public class ClientServiceImpl implements ClientService{

    ClientDAO cdao;

    public ClientServiceImpl(ClientDAO cdao){
        this.cdao = cdao;

    }

    @Override
    public Client createClient(Client client) {
        return cdao.createClient(client);
    }

    @Override
    public Set<Client> getAllClients() {
        return cdao.getAllClients();
    }

    @Override
    public Client getClientById(int clientId) {
        Client client = cdao.getClientById(clientId);
        if (client == null){
            throw new NoClientFoundException();
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        Set<Client> clients = getAllClients();
        for (Client c : clients){
            if (c.getCliendId() == client.getCliendId()){
                return cdao.updateClient(client);
            }
        } throw new NoClientFoundException();

    }

    @Override
    public boolean deleteClientById(int clientId) {
        return cdao.deleteClientById(clientId);
    }
}
