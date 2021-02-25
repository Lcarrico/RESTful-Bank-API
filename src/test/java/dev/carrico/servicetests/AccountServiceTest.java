package dev.carrico.servicetests;

import dev.carrico.daos.AccountDaoLocal;
import dev.carrico.entities.Account;
import dev.carrico.services.AccountService;
import dev.carrico.services.AccountServiceImpl;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest {

    private AccountService accountService = new AccountServiceImpl(new AccountDaoLocal());


    @BeforeEach
    void setup(){

    }

    @Test
    @Order(1)
    void create_account(){
        Account a = new Account(1, 200);
        Account b = accountService.createAccount(a);

        Assertions.assertNotEquals(b.getAccountId(), 0);
    }

    @Test
    @Order(2)
    void get_accounts_by_client_id(){
        Account a = new Account(1, 200);
        accountService.createAccount(a);
        a = new Account(2, 300);
        accountService.createAccount(a);


        Set<Account> accs = accountService.getAccountsByClientId(2);
        System.out.println(accs);
        Assertions.assertTrue(accs.size() >= 1);
    }

    @Test
    @Order(3)
    void get_accounts_by_client_id_between(){
        Account a = new Account(1, 200);
        accountService.createAccount(a);
        a = new Account(1, 300);
        accountService.createAccount(a);

        a = new Account(1, 400);
        accountService.createAccount(a);

        a = new Account(1, 500);
        accountService.createAccount(a);

        Set<Account> betweenTest = accountService.getAccountsByClientIdBetween(1, 450, 250);
        System.out.println(betweenTest);
        Assertions.assertTrue(betweenTest.size() >= 2);
    }

    @Test
    @Order(4)
    void get_account_by_ids(){
        Account a = new Account(1, 200);
        accountService.createAccount(a);
        a = new Account(1, 300);
        accountService.createAccount(a);
        a = new Account(1, 400);
        accountService.createAccount(a);
        a = new Account(1, 500);
        accountService.createAccount(a);


        Account b = accountService.getAccountByIds(1,2);
        Assertions.assertEquals(b.getBalance(), 300);


    }

    @Test
    @Order(5)
    void update_account(){
        Account a = new Account(1, 200);
        accountService.createAccount(a);
        a = new Account(1, 300);
        accountService.createAccount(a);
        a = new Account(1, 400);
        accountService.createAccount(a);
        a.setBalance(800);

        accountService.updateAccount(a);


    }

    @Test
    @Order(6)
    void delete_account(){
        Account a = new Account(1, 200);
        accountService.createAccount(a);
        a = new Account(1, 300);
        accountService.createAccount(a);
        a = new Account(1, 400);
        Account deleteA = accountService.createAccount(a);

        boolean result = accountService.deleteAccount(deleteA);
        Assertions.assertTrue(result);

    }
}
