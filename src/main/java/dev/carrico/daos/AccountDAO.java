package dev.carrico.daos;

import dev.carrico.entities.Account;

import java.util.Set;

public interface AccountDAO {
    Account createAccount(Account account);

    Set<Account> getAllAccounts();

    Account getAccountByIds(int clientId, int accountId);

    Account updateAccount(Account account);

    boolean removeAccountByIds(int clientId, int accountId);
}