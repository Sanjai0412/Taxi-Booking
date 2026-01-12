package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.example.models.Driver;
import com.example.models.DriverStatus;
import com.example.config.DatabaseConfig;

public class DriverDao {

    public List<Driver> getAvailableDrivers() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Driver> drivers = new ArrayList<>();
        try {
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM drivers WHERE status = 'AVAILABLE'");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Driver driver = new Driver(rs.getInt("id"), rs.getString("name"),
                        rs.getString("mobile_no"), rs.getInt("location_id"),
                        DriverStatus.valueOf(rs.getString("status")));
                drivers.add(driver);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null) {
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return drivers;
    }

    public Driver getDriverById(int driverId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Driver driver = null;

        try {
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM drivers WHERE id = ?");
            pstmt.setInt(1, driverId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                driver = new Driver(rs.getInt("id"), rs.getString("name"),
                        rs.getString("mobile_no"), rs.getInt("location_id"),
                        DriverStatus.valueOf(rs.getString("status")));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null) {
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return driver;
    }

    public boolean changeDriverStatus(int driverId, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE drivers SET status = ? WHERE id = ?";

        int rows = 0;
        try {
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, driverId);

            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to Update driver status " + e.getMessage());
        } finally {
            if (pstmt != null)
                pstmt.close();
            if (conn != null) {
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return rows > 0;
    }

    public boolean checkDriverAvailability(int driverId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isAvailable = false;

        try {
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT status FROM drivers WHERE id = ?");
            pstmt.setInt(1, driverId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                isAvailable = status.equals(DriverStatus.AVAILABLE.toString());
            }
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null) {
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
        return isAvailable;
    }

    public void updateDriverLocation(int driverId, int locationId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE drivers SET location_id = ? WHERE id = ?";

        try {
            conn = DatabaseConfig.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            pstmt.setInt(2, driverId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update Driver location on DB " + e.getMessage());
        } finally {
            if (pstmt != null)
                pstmt.close();
            if (conn != null) {
                DatabaseConfig.getInstance().closeConnection(conn);
            }
        }
    }
}