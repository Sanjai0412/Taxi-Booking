package com.example.servlet.api;

import java.security.Timestamp;
import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.models.RideRequest;
import com.example.models.RideRequestStatus;
import com.example.service.RideRequestService;

@WebServlet("/api/ride-request/create")
public class RideRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if(session == null){
            res.sendRedirect("/taxiapp/login");
            return;
        }
        int userId = (int) session.getAttribute("userId"); 
        int pickupId = Integer.parseInt(req.getParameter("pickup-id"));
        int dropId = Integer.parseInt(req.getParameter("drop-id"));

        RideRequest rideRequest = new RideRequest();
        rideRequest.setUserId(userId);
        rideRequest.setPickupLocationId(pickupId);
        rideRequest.setDropLocationId(dropId);
        
        RideRequestService rideRequestService = new RideRequestService();

        try{
            String message = rideRequestService.sendRideRequest(rideRequest);
            res.setContentType("text/plain");
            // Mark ride as requested in session
            session.setAttribute("rideRequested", true);
            res.sendRedirect("/taxiapp/requested");
        }catch(SQLException e){
            e.printStackTrace();
            res.getWriter().println("Failed to send ride request: " + e.getMessage());
        }catch(IllegalArgumentException e){
            res.getWriter().println(e.getMessage());
        }
    }
    
}
