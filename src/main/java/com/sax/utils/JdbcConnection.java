package com.sax.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class JdbcConnection {
    public static Map<String, String> config = Session.getConfig();
    static final String DB_URL = "jdbc:sqlserver://" + config.get("server") + ":"
            + config.get("port") + ";databaseName="
            + config.get("databaseName") + ";trustServerCertificate=true;encrypt=true";
    static final String USER = config.get("username");
    static final String PASS = config.get("password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
