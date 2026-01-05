package com.example.models;

public class Ride {
    private int userId;
    private int driverId;
    private int pickupLocationId;
    private int dropLocationId;
    private RideStatus status;

    public Ride(int userId, int driverId, int pickupLocationId, int dropLocationId, RideStatus status) {
        this.userId = userId;
        this.driverId = driverId;
        this.pickupLocationId = pickupLocationId;
        this.dropLocationId = dropLocationId;
        this.status = status;
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

    
}
