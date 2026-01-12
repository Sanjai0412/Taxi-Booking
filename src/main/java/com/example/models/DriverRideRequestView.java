package com.example.models;

import java.math.BigDecimal;

public class DriverRideRequestView {
    private int rideRequestId;
    private String pickupLocationName;
    private String dropLocationName;
    private double estimatedDistance;
    private BigDecimal estimatedFare;

    public int getRideRequestId() {
        return rideRequestId;
    }
    public void setRideRequestId(int rideRequestId) {
        this.rideRequestId = rideRequestId;
    }
    public String getPickupLocationName() {
        return pickupLocationName;
    }
    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }
    public String getDropLocationName() {
        return dropLocationName;
    }
    public void setDropLocationName(String dropLocationName) {
        this.dropLocationName = dropLocationName;
    }
    public double getEstimatedDistance() {
        return estimatedDistance;
    }
    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }
    public BigDecimal getEstimatedFare() {
        return estimatedFare;
    }
    public void setEstimatedFare(BigDecimal estimatedFare) {
        this.estimatedFare = estimatedFare;
    }
}
