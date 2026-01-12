package com.example.models;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Ride {
    private int id;
    private int userId;
    private int driverId;
    private int pickupLocationId;
    private int dropLocationId;
    private double distance;
    private BigDecimal fare;
    private RideStatus status;
    private Timestamp createdAt;

    public Ride() {}

    public Ride(int userId, int driverId, int pickupLocationId, int dropLocationId, RideStatus status, double distance, BigDecimal fare, Timestamp createdAt) {
        this.userId = userId;
        this.driverId = driverId;
        this.pickupLocationId = pickupLocationId;
        this.dropLocationId = dropLocationId;
        this.status = status;
        this.distance = distance;
        this.fare = fare;
        this.createdAt = createdAt;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getPickupLocationId() {
        return pickupLocationId;
    }

    public void setPickupLocationId(int pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
    }

    public int getDropLocationId() {
        return dropLocationId;
    }

    public void setDropLocationId(int dropLocationId) {
        this.dropLocationId = dropLocationId;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public BigDecimal getFare() {
        return fare;
    }
    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
}
