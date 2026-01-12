package com.example.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RideRequest {
    private int id;
    private int userId;
    private int pickupLocationId;
    private int dropLocationId;
    private double estimatedDistance;
    private BigDecimal estimatedFare;
    private RideRequestStatus status;
    private Timestamp createdAt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public RideRequestStatus getStatus() {
        return status;
    }
    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
