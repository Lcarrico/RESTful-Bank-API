package dev.carrico.services;

import dev.carrico.daos.AccountDAO;
import dev.carrico.entities.Account;
import dev.carrico.exceptions.NoAccountFoundException;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class AccountServiceImpl implements AccountService{
    private AccountDAO adao;

    public AccountServiceImpl(AccountDAO adao){
        this.adao = adao;
    }

    @Override
    public Account createAccount(Account account) {
        return adao.createAccount(account);
    }

    @Override
    public Set<Account> getAccountsByClientId(int clientId) {
        Set<Account> accounts =  adao.getAllAccounts();
        Set<Account> results = new HashSet<Account>();

        for (Account temp : accounts){
            if (temp.getClientId() == clientId){
                results.add(temp);
            }
        }
        return results;
    }

    @Override
    public Set<Account> getAccountsByClientIdBetween(int clientId, int amountLessThan, int amountGreaterThan) {
        Set<Account> clientAccounts = this.getAccountsByClientId(clientId);
        Set<Account> results = new HashSet<Account>();

        for (Account temp : clientAccounts){
            int balance = temp.getBalance();
//            System.out.println(balance + " " + amountLessThan + " " + amountGreaterThan);
            if (balance < amountLessThan && balance > amountGreaterThan){
                results.add(temp);
            }
        }
        return results;
    }

    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        Account account = adao.getAccountByIds(clientId, accountId);
        if (account == null){
            throw new NoAccountFoundException();
        }
        return account;
    }

    @Override
    public Account updateAccount(Account account) {
        return adao.updateAccount(account);

    }

    @Override
    public boolean deleteAccount(Account account) {
        return adao.removeAccountByIds(account.getClientId(), account.getAccountId());
    }
}