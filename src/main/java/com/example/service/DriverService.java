package com.example.service;

import com.example.models.Driver;
import com.example.models.DriverRideRequestView;
import com.example.models.RideRequest;
import com.example.models.Location;
import com.example.models.Ride;
import com.example.dao.RideRequestDao;
import com.example.util.DistanceUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.example.dao.AppConfigDao;
import com.example.dao.DriverDao;
import com.example.dao.LocationDao;
import com.example.dao.RideDao;

public class DriverService {

    DriverDao driverDao;

    public DriverService() {
        driverDao = new DriverDao();
    }

    public Driver getDriverById(int driverId) throws SQLException {
        return driverDao.getDriverById(driverId);
    }

    public List<RideRequest> getActiveRideRequests() throws SQLException {
        RideRequestDao rideRequestDao = new RideRequestDao();
        return rideRequestDao.getActiveRideRequests();
    }

    public double getDriverRadius() throws SQLException {
        AppConfigDao appConfigDao = new AppConfigDao();
        double radius = Double.parseDouble(appConfigDao.getConfigValue("DRIVER_RADIUS"));
        return radius;
    }

    public List<DriverRideRequestView> getNearbyRideRequests(int driverLocationId) throws SQLException {
        List<RideRequest> activeRequests = getActiveRideRequests();
        List<DriverRideRequestView> nearbyRequestsView = new ArrayList<>();
        LocationDao location = new LocationDao();

        Location driverLocation = location.getCoordinates(driverLocationId);
        Location pickupLocation;
        Location dropLocation;

        double driverRadius = getDriverRadius();
        List<RideRequest> nearbyRequests = new ArrayList<>();

        for (RideRequest request : activeRequests) {
            pickupLocation = location.getCoordinates(request.getPickupLocationId());
            dropLocation = location.getCoordinates(request.getDropLocationId());
            double distance = DistanceUtil.calculateDistance(driverLocation, pickupLocation);
            if (distance <= driverRadius) {
                nearbyRequests.add(request);
                DriverRideRequestView view = new DriverRideRequestView();
                view.setRideRequestId(request.getId());
                view.setPickupLocationName(pickupLocation.getName());
                view.setDropLocationName(dropLocation.getName());
                view.setEstimatedDistance(request.getEstimatedDistance());
                view.setEstimatedFare(request.getEstimatedFare());
                nearbyRequestsView.add(view);
            }
        }
        return nearbyRequestsView;
    }

    public void updateDriverStatus(int driverId, String status) throws SQLException {
        driverDao.changeDriverStatus(driverId, status);
    }

    public void updateDriverLocation(int driverId, int locationId) throws SQLException {
        driverDao.updateDriverLocation(driverId, locationId);
    }

    public DriverRideRequestView getRideRequestViewByDriverId(int driverId) throws SQLException {
        RideDao rideDao = new RideDao();
        Ride ride = rideDao.getRideByDriverId(driverId);
        if (ride == null) {
            return null;
        }
        Location pickupLocation = new LocationDao().getCoordinates(ride.getPickupLocationId());
        Location dropLocation = new LocationDao().getCoordinates(ride.getDropLocationId());
        DriverRideRequestView view = new DriverRideRequestView();
        view.setRideRequestId(ride.getId());
        view.setPickupLocationName(pickupLocation.getName());
        view.setDropLocationName(dropLocation.getName());
        view.setEstimatedDistance(ride.getDistance());
        view.setEstimatedFare(ride.getFare());
        return view;
    }

    public boolean isDriverAvailable(int driverId) throws SQLException {
        return driverDao.checkDriverAvailability(driverId);
    }
}