package kr.ac.kku.cs.wp.JW_P1.support.sql;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionPoolUtilTest {
    @Test
    public void testConnection() {
        try (Connection connection = ConnectionPoolUtil.getConnection()) {
            ConnectionPoolUtil.printConnectionPoolStatus();

            String query = "SELECT * FROM user";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("User: " + rs.getString("name"));
            }

            ConnectionPoolUtil.printConnectionPoolStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPoolUtil.printConnectionPoolStatus();
            ConnectionPoolUtil.closeDataSource();
        }
    }
}