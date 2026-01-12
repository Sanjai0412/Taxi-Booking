package com.example.service;

import java.sql.SQLException;

import com.example.dao.RideDao;
import com.example.models.Ride;

public class RideService {
    RideDao rideDao;

    public RideService() {
        rideDao = new RideDao();
    }

    public void createRide(Ride ride) throws SQLException {
        rideDao.createRide(ride);
    }

    public void startRide(int rideId, String status) throws SQLException {
        rideDao.updateRideStatus(rideId, status);
    }

    public Ride getRideByDriverId(int driverId) throws SQLException {
        return rideDao.getRideByDriverId(driverId);
    }

    public void updateRideStatus(int rideId, String status) throws SQLException {
        rideDao.updateRideStatus(rideId, status);
    }
}
