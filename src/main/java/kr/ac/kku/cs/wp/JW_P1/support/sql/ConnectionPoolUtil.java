package kr.ac.kku.cs.wp.JW_P1.support.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolUtil {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolUtil.class);
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/cswp_학번?serverTimezone=UTC");
            config.setUsername("github 계정");
            config.setPassword("학번");
            config.setAutoCommit(false);
            config.setMinimumIdle(3);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            logger.error("Error initializing datasource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void printConnectionPoolStatus() {
        if (dataSource != null) {
            logger.debug("Total connections: " + dataSource.getHikariPoolMXBean().getTotalConnections());
            logger.debug("Active connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
            logger.debug("Idle connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
        }
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}