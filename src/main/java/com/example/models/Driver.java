package com.example.models;

public class Driver {
    private int id;
    private String name;
    private String mobile;
    private int locationId;
    private DriverStatus status;

    public Driver(int id, String name, String mobile, int locationId, DriverStatus status) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.locationId = locationId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
}
