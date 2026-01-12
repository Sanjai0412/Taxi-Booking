package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.example.config.DatabaseConfig;
import com.example.models.Ride;
import com.example.models.RideStatus;

public class RideDao {

    public void createRide(Ride ride) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO rides (user_id, driver_id, pickup_location_id, drop_location_id, status, distance, fare) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ride.getUserId());
            pstmt.setInt(2, ride.getDriverId());
            pstmt.setInt(3, ride.getPickupLocationId());
            pstmt.setInt(4, ride.getDropLocationId());
            pstmt.setString(5, ride.getStatus().toString());
            pstmt.setDouble(6, ride.getDistance());
            pstmt.setBigDecimal(7, ride.getFare());

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
    public Ride getRideByDriverId(int driverId) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM rides WHERE driver_id = ? AND status = ?";  
        Ride ride = null;

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, driverId);
            pstmt.setString(2, RideStatus.ONGOING.toString());

            rs = pstmt.executeQuery();

            if(rs.next()){
                ride = new Ride();
                ride.setId(rs.getInt("id"));
                ride.setUserId(rs.getInt("user_id"));
                ride.setDriverId(rs.getInt("driver_id"));
                ride.setPickupLocationId(rs.getInt("pickup_location_id"));
                ride.setDropLocationId(rs.getInt("drop_location_id"));
                ride.setStatus(RideStatus.valueOf(rs.getString("status")));
                ride.setDistance(rs.getDouble("distance"));
                ride.setFare(rs.getBigDecimal("fare"));
                ride.setCreatedAt(rs.getTimestamp("created_at"));
            }
        }finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return ride;
    }
}
