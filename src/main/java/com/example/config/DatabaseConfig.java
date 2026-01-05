package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    private static DatabaseConfig instance;

    private String url = "jdbc:postgresql://localhost:5432/taxi-booking";
    private String username = "postgres";
    private String password = "sanj";

    private DatabaseConfig(){
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.err.println("Postgres Driver not found " + e.getMessage());
        }
    }
    public static DatabaseConfig getInstance(){
        if(instance == null){
                instance = new DatabaseConfig();
        }
        return instance;
    }
        
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
    public void closeConnection(Connection conn){
        if(conn != null){
           try{
                conn.close();
           }catch(SQLException e){
                System.err.println("Failed to close DB connection " + e.getMessage());
           }
        }
    }
}