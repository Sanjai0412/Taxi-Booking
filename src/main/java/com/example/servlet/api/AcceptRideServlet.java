package com.example.servlet.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.models.RideRequestStatus;
import com.example.models.DriverStatus;
import com.example.models.Ride;
import com.example.models.RideRequest;
import com.example.models.RideStatus;
import com.example.service.DriverService;
import com.example.service.RideRequestService;
import com.example.service.RideService;

@WebServlet("/api/driver/ride-requests/accept")
public class AcceptRideServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);

        if (session == null || !"driver".equals(session.getAttribute("role"))) {
            res.sendRedirect("/taxiapp/login");
            return;
        }
        int driverId = (int) session.getAttribute("userId");

        try {
            if (!new DriverService().isDriverAvailable(driverId)) {
                res.getWriter().write("Driver (you) is currently on another ride.");
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        RideService rideService = new RideService();
        RideRequestService rideRequestService = new RideRequestService();
        int requestId = Integer.parseInt(req.getParameter("rideRequestId"));

        try {
            if (rideRequestService.checkRequestStatus(requestId, RideRequestStatus.CREATED)) {
                rideRequestService.updateRideRequestStatus(requestId, RideRequestStatus.ACCEPTED);

                RideRequest rideRequest = rideRequestService.getRideRequestById(requestId);
                Ride ride = new Ride();
                ride.setUserId(rideRequest.getUserId());
                ride.setDriverId((int) session.getAttribute("userId"));
                ride.setPickupLocationId(rideRequest.getPickupLocationId());
                ride.setDropLocationId(rideRequest.getDropLocationId());
                ride.setStatus(RideStatus.ONGOING);
                ride.setDistance(rideRequest.getEstimatedDistance());
                ride.setFare(rideRequest.getEstimatedFare());

                rideService.createRide(ride);
                new DriverService().updateDriverStatus(driverId, DriverStatus.ON_RIDE.toString());

                res.sendRedirect("/taxiapp/driver/ongoing-rides");
            } else {
                res.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
