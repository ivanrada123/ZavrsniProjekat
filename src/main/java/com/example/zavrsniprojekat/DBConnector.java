package com.example.zavrsniprojekat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    // Poveznica na bazu

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/zavrsniprojekat?" +
                "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=" +
                "false&serverTimezone=CET", "root", "root");

        return connection;
    }
}