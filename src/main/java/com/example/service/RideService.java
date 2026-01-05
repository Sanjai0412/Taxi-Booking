package com.example.service;

import java.sql.SQLException;

import com.example.dao.RideDao;
import com.example.models.Ride;
import com.example.models.RideStatus;

public class RideService {
    RideDao rideDao;

    public RideService(){
        rideDao = new RideDao();
    }
    public Ride requestRide(int userId, int driverId, int pickupId, int dropId){
        Ride ride = new Ride(userId, driverId, pickupId, dropId, RideStatus.valueOf("REQUESTED"));
        try{
            rideDao.createRide(ride);
            return ride;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return ride;
        }
        
    }

    public void startRide(int rideId, String status) throws SQLException{
        rideDao.updateRideStatus(rideId, status);
    }
}
