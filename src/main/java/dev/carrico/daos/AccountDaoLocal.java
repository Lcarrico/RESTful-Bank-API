package dev.carrico.daos;

import dev.carrico.entities.Account;

import java.util.*;

public class AccountDaoLocal implements AccountDAO{

    // Map key is a an array of two integers. First is client id, the second is account id.
    private Map<List<Integer>,Account> accountsTable = new HashMap<List<Integer>, Account>();
    private Map<Integer, Integer> accountIdTracker = new HashMap<Integer, Integer>();

    private ArrayList<Integer> generateIdPair(int clientId, int accountId){
        ArrayList<Integer> id_pair = new ArrayList<Integer>();
        id_pair.add(clientId);
        id_pair.add(accountId);
        return id_pair;
    }

    private ArrayList<Integer> generateIdPair(Account account){
        return generateIdPair(account.getClientId(), account.getAccountId());
    }

    @Override
    public Account createAccount(Account account) {
        int accountId;
        if (accountIdTracker.containsKey(account.getClientId())){
            accountId = accountIdTracker.get(account.getClientId());

        } else {
            accountId = 0;
        }
        account.setAccountId(++accountId);
        accountIdTracker.put(account.getClientId(), accountId);

        ArrayList<Integer> id_pair = generateIdPair(account);
        accountsTable.put(id_pair, account);
        return account;
    }

    @Override
    public Set<Account> getAllAccounts() {
        return new HashSet<Account>(accountsTable.values());
    }

    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        List<Integer> id_pair = generateIdPair(clientId, accountId);
        return accountsTable.get(id_pair);
    }

    @Override
    public Account updateAccount(Account account) {
        List<Integer> id_pair = generateIdPair(account);
        accountsTable.put(id_pair, account);
        return accountsTable.get(account.getAccountId());
    }

    @Override
    public boolean removeAccountByIds(int clientId, int accountId) {
        List<Integer> id_pair = generateIdPair(clientId, accountId);
        Account account = accountsTable.remove(id_pair);
        if (account == null){
            return false;
        }
        return true;
    }
}
