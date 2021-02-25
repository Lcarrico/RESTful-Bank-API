package dev.carrico.daos;

import dev.carrico.entities.Client;
import dev.carrico.exceptions.ForeignKeyConstraintException;
import dev.carrico.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDaoPostgres implements ClientDAO{
    @Override
    public Client createClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into client (first_name, last_name) values (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getfName());
            ps.setString(2, client.getlName());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("client_id");
            client.setCliendId(key);
            return client;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {
        Set<Client> clients = new HashSet<Client>();
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Client client = new Client();
                client.setCliendId(rs.getInt("client_id"));
                client.setfName(rs.getString("first_name"));
                client.setlName(rs.getString("last_name"));
                clients.add(client);
            }
            return clients;
        } catch (SQLException e){
            e.printStackTrace();
            return clients;
        }
    }


    @Override
    public Client getClientById(int clientId) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Client client = new Client();
            client.setCliendId(rs.getInt("client_id"));
            client.setfName(rs.getString("first_name"));
            client.setlName(rs.getString("last_name"));
            return client;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client updateClient(Client client) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "update client set first_name = ?, last_name = ? where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getfName());
            ps.setString(2, client.getlName());
            ps.setInt(3, client.getCliendId());
            ps.execute();

            return client;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int clientId) {
        try (Connection conn = ConnectionUtil.createConnection()){
            String sql = "select count(account_id) as count_value from account where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt("count_value");
            if (count > 0){
                throw new ForeignKeyConstraintException();
            }

            sql = "delete from client where client_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.execute();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
