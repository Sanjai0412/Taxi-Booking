package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.config.DatabaseConfig;
import com.example.models.Ride;
import com.example.models.RideStatus;

public class RideDao {

    public void createRide(Ride ride) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO rides (user_id, driver_id, pickup_location_id, drop_location_id, status) VALUES (?, ?, ?, ?, ?)";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ride.getUserId());
            pstmt.setInt(2, ride.getDriverId());
            pstmt.setInt(3, ride.getPickupLocationId());
            pstmt.setInt(4, ride.getDropLocationId());
            pstmt.setString(5, ride.getStatus().toString());

            pstmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Failed to Insert ride on DB " + e.getMessage());
        }finally{
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
    }
    public void updateRideStatus(int rideId, String status) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE rides SET status = ? WHERE id = ?";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, status);
            pstmt.setInt(2, rideId);

            pstmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Failed to update Ride status on DB " + e.getMessage());
        }finally{
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
    }
}
