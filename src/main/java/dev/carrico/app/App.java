package dev.carrico.app;

import dev.carrico.controllers.AccountController;
import dev.carrico.controllers.ClientController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        ClientController cc = new ClientController();
        AccountController ac = new AccountController(cc.getCServ());

        // client http requests
        app.post("/clients", cc.createClientHandler);

        app.get("/clients", cc.getClientsHandler);

        app.get("/clients/:clientId", cc.getClientHandler);

        app.put("/clients/:clientId", cc.updateClient);

        app.delete("/clients/:clientId", cc.deleteClient);


        // account http requests
        app.post("/clients/:clientId/accounts", ac.createAccountHandler);

        app.get("/clients/:clientId/accounts", ac.getAccountsHandler);

        app.get("/clients/:clientId/accounts/:accountId", ac.getAccountHandler);

        app.put("/clients/:clientId/accounts/:accountId", ac.updateAccountHandler);

        app.delete("/clients/:clientId/accounts/:accountId", ac.deleteAccountHandler);

        app.start();

    }

}
