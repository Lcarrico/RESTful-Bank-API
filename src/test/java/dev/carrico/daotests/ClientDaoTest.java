package dev.carrico.daotests;

import dev.carrico.daos.ClientDAO;
import dev.carrico.daos.ClientDaoLocal;
import dev.carrico.daos.ClientDaoPostgres;
import dev.carrico.entities.Client;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {

    private static ClientDAO bdao = new ClientDaoPostgres();

    @Test
    @Order(1)
    void create_client(){
        Client c = new Client("John", "Smith");
        bdao.createClient(c);

        Set<Client> clients = bdao.getAllClients();
        System.out.println(clients);
    }

    @Test
    @Order(2)
    void get_all_clients(){
        Client c = new Client("John", "Smith");
        bdao.createClient(c);

        c = new Client("James", "Adam");
        bdao.createClient(c);

        c = new Client("George", "Washington");
        bdao.createClient(c);

        Set<Client> clients = bdao.getAllClients();
        System.out.println(clients);
    }

    @Test
    @Order(3)
    void get_client_by_id(){
        Client c = new Client("John", "Smith");
        bdao.createClient(c);

        c = new Client("James", "Adam");
        bdao.createClient(c);

        c = new Client("George", "Washington");
        bdao.createClient(c);

        Client c2 = bdao.getClientById(2);
        System.out.println(c2);
    }

    @Test
    @Order(4)
    void update_client(){
        Client c = new Client("John", "Smith");
        bdao.createClient(c);
        System.out.println(bdao.getAllClients());

        c = bdao.getClientById(1);
        c.setfName("Johnny");
        bdao.updateClient(c);
        System.out.println(bdao.getAllClients());
    }

    @Test
    @Order(5)
    void delete_client_by_id(){
        Client c = new Client("John", "Smith");
        bdao.createClient(c);

        c = new Client("James", "Adam");
        bdao.createClient(c);

        c = new Client("George", "Washington");
        bdao.createClient(c);

        System.out.println(bdao.getAllClients());

        bdao.deleteClientById(2);
        System.out.println(bdao.getAllClients());
    }
}
