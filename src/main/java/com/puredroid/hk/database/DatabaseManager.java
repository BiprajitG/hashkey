package com.puredroid.hk.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_PATH = "passwords.db";
    private static final String SCHEMA_PATH = "schema.sql";

    private DatabaseManager() {
        // Initialize the database connection here using DB_PATH
    }

    public static synchronized DatabaseManager getInstance(){
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH);
        }
        return connection;
    }

    public void initializeDatabase() throws SQLException, IOException {
        try (Connection conn = getConnection();
             InputStream in = getClass().getClassLoader().getResourceAsStream(SCHEMA_PATH);
             Scanner scanner = new Scanner(in, "UTF-8")) {
                scanner.useDelimiter(";");
                Statement statement = conn.createStatement();
                while(scanner.hasNext()){
                    String sqlStatement = scanner.next().trim();
                    if(!sqlStatement.isEmpty()){
                        statement.execute(sqlStatement);
                    }
                }  
                System.out.println("Database initialized successfully."); 
            }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
        }
    }
}
