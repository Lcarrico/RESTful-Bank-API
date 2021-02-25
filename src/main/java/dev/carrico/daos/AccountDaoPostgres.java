package dev.carrico.daos;

import dev.carrico.entities.Account;
import dev.carrico.entities.Client;
import dev.carrico.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgres implements AccountDAO{
    @Override
    public Account createAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select max(account_id) as max_value from account where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, account.getClientId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int max = rs.getInt("max_value");

            sql = "insert into account (client_id, account_id, balance) values (?, ?, ?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, account.getClientId());
            ps.setInt(2, ++max);
            ps.setInt(3, account.getBalance());

            ps.execute(); // execute the sql statement

            rs = ps.getGeneratedKeys(); // return the value of the generated keys
            rs.next();
            int client_id = rs.getInt("client_id");
            int account_id = rs.getInt("account_id");

            account.setClientId(client_id);
            account.setAccountId(account_id);

            return account;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Account> getAllAccounts() {
        Set<Account> accounts = new HashSet<Account>();
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // while book exists, create a new book and store the info
            // into the new book
            while(rs.next()) {
                Account account = new Account();
                account.setClientId(rs.getInt("client_id"));
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getInt("balance"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e){
            e.printStackTrace();
            return accounts;
        }
    }

    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Account account = new Account();
            account.setClientId(rs.getInt("client_id"));
            account.setAccountId(rs.getInt("account_id"));
            account.setBalance(rs.getInt("balance"));

            return account;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "update account set balance = ? where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getBalance());
            ps.setInt(2, account.getClientId());
            ps.setInt(3, account.getAccountId());
            ps.execute();

            return account;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeAccountByIds(int clientId, int accountId) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, accountId);
            ps.execute();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
