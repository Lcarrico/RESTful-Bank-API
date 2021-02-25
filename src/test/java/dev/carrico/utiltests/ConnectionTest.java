package dev.carrico.utiltests;

import dev.carrico.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void generate_connection(){
        Connection conn = ConnectionUtil.createConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }

    @Test
    void get_env_variable(){
        String env = System.getenv("BankURL");
        System.out.println(env);
    }
}
