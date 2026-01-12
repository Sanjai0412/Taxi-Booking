package com.example.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import com.example.models.Ride;
import com.example.models.DriverStatus;
import com.example.models.RideStatus;
import com.example.service.DriverService;
import com.example.service.RideService;

@WebServlet("/api/driver/ride/complete")
public class CompleteRideServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || !"driver".equals(session.getAttribute("role"))) {
            res.sendRedirect("/taxiapp/login");
            return;
        }
        int driverId = (int) session.getAttribute("userId");
        Ride ride;
        int requestId = Integer.parseInt(req.getParameter("rideId"));
        try {
            ride = new RideService().getRideByDriverId(driverId);
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        try {
            if (ride != null && ride.getId() == requestId) {
                new RideService().updateRideStatus(ride.getId(), RideStatus.COMPLETED.toString());
                new DriverService().updateDriverStatus(driverId, DriverStatus.AVAILABLE.toString());
                new DriverService().updateDriverLocation(driverId, ride.getDropLocationId());
                res.sendRedirect("/taxiapp/driver/ride-requests");
            } else {
                res.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
