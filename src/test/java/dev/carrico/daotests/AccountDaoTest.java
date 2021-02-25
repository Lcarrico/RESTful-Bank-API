package dev.carrico.daotests;

import dev.carrico.daos.AccountDAO;
import dev.carrico.daos.AccountDaoLocal;
import dev.carrico.daos.AccountDaoPostgres;
import dev.carrico.entities.Account;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest {
    AccountDAO adao = new AccountDaoPostgres();

    @Test
    @Order(1)
    void create_account(){
        Account a = new Account(1);
        a.setBalance(500);
        adao.createAccount(a);
        System.out.println(adao.getAllAccounts());
    }

    @Test
    @Order(2)
    void get_all_accounts(){
        Account a = new Account(1, 100);
        adao.createAccount(a);
        a = new Account(1, 50);
        adao.createAccount(a);
        a = new Account(1, 120);
        adao.createAccount(a);
        a = new Account(2, 500);
        adao.createAccount(a);
        a = new Account(2, 800);
        adao.createAccount(a);

        Set<Account> accounts = adao.getAllAccounts();
        System.out.println(accounts);

    }

    @Test
    @Order(3)
    void get_account_by_ids(){
        Account a = new Account(1, 100);
        adao.createAccount(a);
        a = new Account(1, 50);
        adao.createAccount(a);
        a = new Account(1, 120);
        adao.createAccount(a);
        a = new Account(2, 500);
        adao.createAccount(a);
        a = new Account(2, 800);
        adao.createAccount(a);

        System.out.println(adao.getAccountByIds(1, 3));
    }

    @Test
    @Order(4)
    void update_account(){
        Account a = new Account(1, 100);
        adao.createAccount(a);
        a = new Account(1, 50);
        adao.createAccount(a);
        a = new Account(1, 120);
        adao.createAccount(a);
        a = new Account(2, 500);
        adao.createAccount(a);
        a = new Account(2, 800);
        adao.createAccount(a);

        a = adao.getAccountByIds(2, 1);
        int first_balance = 250;
        a.setBalance(first_balance);
        a = adao.updateAccount(a);
        a.setBalance(450);
        adao.updateAccount(a);

        Assertions.assertNotEquals(first_balance, adao.getAccountByIds(2, 1).getBalance());
    }

    @Test
    @Order(5)
    void remove_account_by_id(){
        Account a = new Account(1);
        adao.createAccount(a);
        a = new Account(1);
        a.setBalance(100);
        adao.createAccount(a);
        a = new Account(1);
        adao.createAccount(a);
        System.out.println(adao.getAllAccounts());

        adao.removeAccountByIds(5, 2);
        System.out.println(adao.getAllAccounts());
    }




}
