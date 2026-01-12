package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.config.DatabaseConfig;

public class AppConfigDao {
    
    public String getConfigValue(String key) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String value = null;
        String sql = "SELECT config_value FROM app_config WHERE config_key = ?";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, key);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                value = rs.getString("config_value");
            }
            rs.close();
            pstmt.close();
        }catch(SQLException e){
            System.err.println("Failed to fetch config value " + e.getMessage()); 
        }finally{
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return value;
    }
}
