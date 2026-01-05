package com.example.Servlet;

import com.example.models.Driver;
import com.example.models.DriverStatus;
import com.example.models.Ride;
import com.example.models.RideStatus;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.service.DriverService;
import com.example.service.RideService;

@WebServlet("/ride/book")
public class RideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        int userId = Integer.parseInt(req.getParameter("user-id"));
        int pickupId = Integer.parseInt(req.getParameter("pickup"));
        int dropId = Integer.parseInt(req.getParameter("drop"));
        DriverService driverService;
        Driver driver = null;
        try{
            driverService = new DriverService();
            driver = driverService.getNearestAvailableDriver(pickupId);
        }catch(Exception e){
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request");
            return;
        }
        if(driver == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No available drivers found");
            return;
        }
        
        RideService rideService = new RideService();
        Ride ride = rideService.requestRide(userId, driver.getId(), pickupId, dropId);
        try{
            ride.setStatus(RideStatus.valueOf("ONGOING"));
            rideService.startRide(dropId, ride.getStatus().toString());
            driverService.updateDriverStatus(driver.getId(), DriverStatus.valueOf("ON_RIDE").toString());
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        res.setContentType("text/plain");
        res.getWriter().write("Assigned Driver : " + driver.getName() + "\n Driver Location : " + driver.getLocationId() + "\n Pick up Location : " + pickupId + "\n Drop Location : " + dropId);
        res.getWriter().write("\n" +ride.getUserId() + " " + ride.getDriverId() + " " + ride.getPickupLocationId() + " " + ride.getDropLocationId() + " " + ride.getStatus());
    }
}
