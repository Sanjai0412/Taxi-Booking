package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.config.DatabaseConfig;
import com.example.models.RideRequest;
import com.example.models.RideRequestStatus;

public class RideRequestDao {
    
    public void createRideRequest(RideRequest rideRequest) throws SQLException {
        
        rideRequest.setStatus(RideRequestStatus.CREATED);

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO ride_requests(user_id, pickup_location_id, drop_location_id, estimated_distance, estimated_fare, status) VALUES(?, ?, ?, ?, ?, ?)";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, rideRequest.getUserId());
            pstmt.setInt(2, rideRequest.getPickupLocationId());
            pstmt.setInt(3, rideRequest.getDropLocationId());
            pstmt.setDouble(4, rideRequest.getEstimatedDistance());
            pstmt.setBigDecimal(5, rideRequest.getEstimatedFare());
            pstmt.setString(6, rideRequest.getStatus().toString());

            pstmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Failed to insert Ride request object to DB " + e.getMessage());
        }finally{
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
    }

    public List<RideRequest> getActiveRideRequests() throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM ride_requests WHERE status = ?";

        List<RideRequest> rideRequests = new ArrayList<>();
        RideRequest rideRequest;
        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, RideRequestStatus.CREATED.toString());

            rs = pstmt.executeQuery();

            while(rs.next()){
                rideRequest = new RideRequest();
                rideRequest.setId(rs.getInt("id"));
                rideRequest.setUserId(rs.getInt("user_id"));
                rideRequest.setPickupLocationId(rs.getInt("pickup_location_id"));
                rideRequest.setDropLocationId(rs.getInt("drop_location_id"));
                rideRequest.setEstimatedDistance(rs.getDouble("estimated_distance"));
                rideRequest.setEstimatedFare(rs.getBigDecimal("estimated_fare"));
                rideRequest.setStatus(RideRequestStatus.valueOf(rs.getString("status")));
                rideRequest.setCreatedAt(rs.getTimestamp("created_at"));

                rideRequests.add(rideRequest);
            }
        }finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return rideRequests;
    }

    public void updateRideRequestStatus(int rideRequestId, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE ride_requests SET status = ? WHERE id = ?";

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, status);
            pstmt.setInt(2, rideRequestId);

            pstmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Failed to update Ride request status on DB " + e.getMessage());
        }finally{
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
    }

    public RideRequest getRideRequestById(int rideRequestId) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        RideRequest rideRequest = null;

        try{
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM ride_requests WHERE id = ?");
            pstmt.setInt(1, rideRequestId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                rideRequest = new RideRequest();
                rideRequest.setId(rs.getInt("id"));
                rideRequest.setUserId(rs.getInt("user_id"));
                rideRequest.setPickupLocationId(rs.getInt("pickup_location_id"));
                rideRequest.setDropLocationId(rs.getInt("drop_location_id"));
                rideRequest.setEstimatedDistance(rs.getDouble("estimated_distance"));
                rideRequest.setEstimatedFare(rs.getBigDecimal("estimated_fare"));
                rideRequest.setStatus(RideRequestStatus.valueOf(rs.getString("status")));
                rideRequest.setCreatedAt(rs.getTimestamp("created_at"));
            }
        }finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null){
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return rideRequest;
    }
}
