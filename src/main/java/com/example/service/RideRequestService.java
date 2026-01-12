package com.example.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import com.example.models.Location;
import com.example.dao.LocationDao;
import com.example.dao.RideRequestDao;
import com.example.models.RideRequest;
import com.example.models.RideRequestStatus;
import com.example.util.DistanceUtil;

public class RideRequestService {
    public String sendRideRequest(RideRequest rideRequest) throws SQLException {

        LocationDao locationDao = new LocationDao();
        Location pickupLocation = locationDao.getCoordinates(rideRequest.getPickupLocationId());
        Location dropLocation = locationDao.getCoordinates(rideRequest.getDropLocationId());

        if (pickupLocation == null || dropLocation == null) {
            throw new IllegalArgumentException("Invalid Location id");
        }
        if (pickupLocation.getId() == dropLocation.getId()) {
            throw new IllegalArgumentException("Pickup and Drop locations cannot be the same");
        }
        // calculate distance and fare
        double distance = DistanceUtil.calculateDistance(pickupLocation, dropLocation);
        BigDecimal fare = calculateFare(distance);
        rideRequest.setEstimatedDistance(distance);
        rideRequest.setEstimatedFare(fare);

        RideRequestDao rideRequestDao = new RideRequestDao();
        rideRequestDao.createRideRequest(rideRequest);

        return "Ride request sent successfully";
    }

    public BigDecimal calculateFare(double distance) {

        BigDecimal baseFare = new BigDecimal("5");
        BigDecimal perKmRate = new BigDecimal("10"); // rate per kilometer
        return baseFare.add(perKmRate.multiply(BigDecimal.valueOf(distance)));
    }

    public boolean checkRequestStatus(int requestId, RideRequestStatus status) throws SQLException {
        RideRequestDao rideRequestDao = new RideRequestDao();
        RideRequest rideRequest = rideRequestDao.getRideRequestById(requestId);
        if (rideRequest == null) {
            throw new IllegalArgumentException("Invalid Ride Request ID");
        }
        return rideRequest.getStatus() == status;
    }

    public void updateRideRequestStatus(int requestId, RideRequestStatus status) throws SQLException {
        RideRequestDao rideRequestDao = new RideRequestDao();
        rideRequestDao.updateRideRequestStatus(requestId, status.toString());
    }

    public RideRequest getRideRequestById(int requestId) {
        RideRequestDao rideRequestDao = new RideRequestDao();
        try {
            return rideRequestDao.getRideRequestById(requestId);
        } catch (SQLException e) {
            System.err.println("Failed to fetch Ride request from DB " + e.getMessage());
            return null;
        }
    }
}
