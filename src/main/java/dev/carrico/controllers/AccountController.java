package dev.carrico.controllers;

import com.google.gson.Gson;
import dev.carrico.daos.AccountDaoLocal;
import dev.carrico.daos.AccountDaoPostgres;
import dev.carrico.entities.Account;
import dev.carrico.exceptions.NoAccountFoundException;
import dev.carrico.exceptions.NoClientFoundException;
import dev.carrico.services.AccountService;
import dev.carrico.services.AccountServiceImpl;
import dev.carrico.services.ClientService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class AccountController {

    private ClientService cserv;
    private static Logger logger = Logger.getLogger(AccountController.class.getName());
    public AccountController(ClientService cserv){
        this.cserv = cserv;
    }

    private AccountService aserv = new AccountServiceImpl(new AccountDaoPostgres());
    public Handler createAccountHandler = (Context ctx) -> {
        Gson gson = new Gson();
        try {
            String body = ctx.body();
            if (body.equals("")){
                body = "{}";
            }
            Account account = gson.fromJson(body, Account.class);
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));

            cserv.getClientById(clientId);

            account.setClientId(clientId);
            Account resultAccount = aserv.createAccount(account);
            String resultAccountJSON = gson.toJson(resultAccount);
            System.out.println(resultAccountJSON);
            logger.info("A new account for client " + account.getClientId() + " was registered");
            ctx.result(resultAccountJSON);
            ctx.status(201);
        } catch (NoClientFoundException e){
            ctx.status(404);
        }
        catch (Exception e){
            ctx.status(400);
        }
    };

    public Handler getAccountsHandler = (Context ctx) -> {
        try {
            int amountLessThan = Integer.parseInt(ctx.queryParam("amountLessThan",Integer.toString(Integer.MAX_VALUE)));
            int amountGreaterThan = Integer.parseInt(ctx.queryParam("amountGreaterThan","-1"));

            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            cserv.getClientById(clientId);
//            Set<Account> accounts = this.aserv.getAccountsByClientId(clientId);
            Set<Account> accounts = this.aserv.getAccountsByClientIdBetween(clientId, amountLessThan, amountGreaterThan);
            Gson gson = new Gson();
            String accountsJSON = gson.toJson(accounts);
            ctx.result(accountsJSON);
            ctx.status(200);
        } catch (NoClientFoundException e){
            ctx.status(404);
        } catch (Exception e){
            ctx.status(400);
        }
    };

    public Handler getAccountHandler = (Context ctx) -> {
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            int accountId = Integer.parseInt(ctx.pathParam("accountId"));
            Account account = aserv.getAccountByIds(clientId, accountId);
            Gson gson = new Gson();
            String accountJSON = gson.toJson(account);
            ctx.result(accountJSON);
            ctx.status(200);
        } catch (NoAccountFoundException e){
            ctx.status(404);
        } catch (Exception e){
            ctx.status(400);
        }
    };

    public Handler updateAccountHandler = (Context ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            this.cserv.getClientById(clientId);
            int accountId = Integer.parseInt(ctx.pathParam("accountId"));
            this.aserv.getAccountByIds(clientId, accountId);

            Account account = gson.fromJson(body, Account.class);
            account.setClientId(clientId);
            account.setAccountId(accountId);

            Account resultAccount = this.aserv.updateAccount(account);
            String resultAccountJSON = gson.toJson(resultAccount);

            ctx.result(resultAccountJSON);

            ctx.status(200);
        } catch (NoClientFoundException e){
            ctx.status(404);
        } catch (NoAccountFoundException e) {
            ctx.status(404);
        } catch (Exception e) {
            ctx.status(400);
        }
    };

    public Handler deleteAccountHandler = (Context ctx) -> {
        try {
            int clientId = Integer.parseInt(ctx.pathParam("clientId"));
            this.cserv.getClientById(clientId);
            int accountId = Integer.parseInt(ctx.pathParam("accountId"));

            boolean result = this.aserv.deleteAccount(new Account(clientId, accountId, 0));
            if (result){
                logger.info("Account " + accountId + " for client " + clientId + " was deleted");
                ctx.status(204);
            } else{
                ctx.status(404);
            }
        } catch (NoClientFoundException e){
            ctx.status(404);
        } catch (NoAccountFoundException e) {
            ctx.status(404);
        } catch (Exception e){
            ctx.status(400);
        }
    };
}
