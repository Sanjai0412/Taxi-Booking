package com.example.service;

import com.example.models.Driver;
import java.util.List;
import java.sql.SQLException;

import com.example.dao.DriverDao;

public class DriverService {

    DriverDao driverDao;
    public DriverService(){
        driverDao = new DriverDao();
    }
    
    public Driver getNearestAvailableDriver(int pickupLocationId) throws SQLException{
        List<Driver> availableDrivers = driverDao.getAvailableDrivers();
        if(availableDrivers.isEmpty()){
            System.err.println("No available drivers found");
            return availableDrivers.get(0);
        }

        Driver nearbyDriver = null;

        int minimumDistance = Integer.MAX_VALUE;
        for(Driver driver : availableDrivers){
            if(Math.abs(driver.getLocationId() - pickupLocationId) < minimumDistance){
                minimumDistance = Math.abs(driver.getLocationId() - pickupLocationId);
                nearbyDriver = driver;
            }
        }
        return nearbyDriver;
    }

    public void updateDriverStatus(int driverId, String status) throws SQLException{
        driverDao.changeDriverStatus(driverId, status);
    }
}
