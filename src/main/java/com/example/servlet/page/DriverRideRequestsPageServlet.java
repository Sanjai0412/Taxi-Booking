package com.example.servlet.page;

import java.util.List;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.models.Driver;
import com.example.service.DriverService;

@WebServlet("/taxiapp/driver/ride-requests")
public class DriverRideRequestsPageServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if(session == null || !"driver".equals(session.getAttribute("role"))){
            res.sendRedirect("/taxiapp/login");
            return;
        }

        try{
            int driverId =(int) session.getAttribute("userId");
            DriverService driverService = new DriverService();
            Driver driver = driverService.getDriverById(driverId); 
            
            req.setAttribute("nearbyRequests", driverService.getNearbyRideRequests(driver.getLocationId()));
            
            req.getRequestDispatcher("/driver.jsp").forward(req, res);
        }catch(Exception e){
            System.err.println("Failed to load Driver Ride Requests Page " + e.getMessage());
        }
    }
}
