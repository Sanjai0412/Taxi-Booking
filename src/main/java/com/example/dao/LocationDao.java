package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.config.DatabaseConfig;
import com.example.models.Location;

public class LocationDao {
    
    public Location getCoordinates(int locationId) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM locations WHERE id = ?";

        Location location = null;
        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                location = new Location();
                location.setId(rs.getInt("id"));
                location.setName(rs.getString("name"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
            }

        }catch(SQLException e){
            System.err.println("Failed to fetch Coordinates from DB " + e.getMessage());
        }finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return location;
    }


}
