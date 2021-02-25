package dev.carrico.servicetests;

import dev.carrico.daos.ClientDaoLocal;
import dev.carrico.entities.Client;
import dev.carrico.services.ClientService;
import dev.carrico.services.ClientServiceImpl;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTest {

    private ClientService clientService = new ClientServiceImpl(new ClientDaoLocal());

    @Test
    @Order(1)
    void create_client(){
        Client c = new Client("John", "Smith");
        Client createdClient = clientService.createClient(c);

        Assertions.assertNotEquals(createdClient.getCliendId(), 0);
        Assertions.assertEquals(createdClient.getfName(), "John");
    }

    @Test
    @Order(2)
    void get_all_clients(){
        Client c = new Client("John", "Smith");
        clientService.createClient(c);
        c = new Client("James", "Adams");
        clientService.createClient(c);
        c = new Client("George", "Washington");
        clientService.createClient(c);

        Set<Client> clients = clientService.getAllClients();
        Assertions.assertTrue(clients.size() >= 3);
    }

    @Test
    @Order(3)
    void get_client_by_id(){
        Client c = new Client("John", "Smith");
        clientService.createClient(c);
        c = new Client("James", "Adams");
        clientService.createClient(c);

        Client c1 = clientService.getClientById(c.getCliendId());
        Assertions.assertEquals(c1.getfName(),c.getfName());
    }

    @Test
    @Order(4)
    void update_client_by_id(){
        Client c = new Client("John", "Smith");
        clientService.createClient(c);
        c = new Client("James", "Adams");
        clientService.createClient(c);

        Client c1 = clientService.getClientById(2);
        c1.setlName("Smith");
        c1 = clientService.updateClient(c1);

        Assertions.assertEquals(clientService.getClientById(c1.getCliendId()).getlName(), "Smith");
    }

    @Test
    @Order(5)
    void delete_client_by_id(){
        Client c = new Client("John", "Smith");
        c = clientService.createClient(c);

        Assertions.assertTrue(clientService.deleteClientById(c.getCliendId()));
    }
}
