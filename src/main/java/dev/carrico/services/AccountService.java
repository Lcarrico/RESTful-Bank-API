package dev.carrico.services;

import dev.carrico.entities.Account;

import java.util.Set;

public interface AccountService {

    Account createAccount(Account account);

    Set<Account> getAccountsByClientId(int clientId);

    Set<Account> getAccountsByClientIdBetween(int clientId, int amountLessThan, int amountGreaterThan);

    Account getAccountByIds(int clientId, int accountId);

    Account updateAccount(Account account);

    boolean deleteAccount(Account account);

}
