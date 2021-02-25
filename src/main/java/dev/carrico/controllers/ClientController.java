package dev.carrico.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.carrico.daos.ClientDaoLocal;
import dev.carrico.daos.ClientDaoPostgres;
import dev.carrico.entities.Client;
import dev.carrico.exceptions.ForeignKeyConstraintException;
import dev.carrico.exceptions.NoClientFoundException;
import dev.carrico.services.ClientService;
import dev.carrico.services.ClientServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class ClientController {
    private ClientService cserv = new ClientServiceImpl(new ClientDaoPostgres());
    private static Logger logger = Logger.getLogger(ClientController.class.getName());


    public ClientService getCServ(){
        return cserv;
    }

    public Handler createClientHandler = (Context ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        try {
            Client client = gson.fromJson(body, Client.class);
            System.out.println(client);
            Client resultClient = this.cserv.createClient(client);
            String resultClientJSON = gson.toJson(resultClient);

            logger.info("A new client " + client.getCliendId() + " was registered");
            ctx.status(201);
            ctx.result(resultClientJSON);
        } catch (Exception e) {
            ctx.status(400);
        }
    };


    public Handler getClientsHandler = (Context ctx) -> {
        Set<Client> clients = this.cserv.getAllClients();
        Gson gson = new Gson();
        String clientsJSON = gson.toJson(clients);
        ctx.result(clientsJSON);
        ctx.status(200);
    };

    public Handler getClientHandler = (Context ctx) -> {
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            Client client = cserv.getClientById(clientId);
            Gson gson = new Gson();
            String clientJSON = gson.toJson(client);
            ctx.result(clientJSON);
            ctx.status(200);
        }
        catch (NoClientFoundException e){
            ctx.status(404);
        }
        catch (Exception e){
            ctx.status(400);
        }
    };

    public Handler updateClient = (Context ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            Client client = gson.fromJson(body, Client.class);
            client.setCliendId(clientId);

            Client resultClient = this.cserv.updateClient(client);
            String resultClientJSON = gson.toJson(resultClient);


            ctx.status(200);
            ctx.result(resultClientJSON);
        } catch (NoClientFoundException e) {
            ctx.status(404);
        }
        catch (Exception e) {
            ctx.status(400);
        }
    };

    public Handler deleteClient = (Context ctx) -> {
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            boolean result = this.cserv.deleteClientById(clientId);
            if (result){
                logger.info("Client " + clientId + " was deleted");
                ctx.status(204);

            } else{
                ctx.status(404);
            }
        } catch (ForeignKeyConstraintException e){
            ctx.status(400);
        } catch (Exception e){
            ctx.status(400);
        }
    };
}
