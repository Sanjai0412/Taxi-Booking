package com.example.servlet.page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.models.DriverRideRequestView;
import com.example.service.DriverService;

import javax.servlet.ServletException;

import java.io.IOException;

import javax.servlet.RequestDispatcher;

@WebServlet("/taxiapp/driver/ongoing-rides")
public class DriverOnGoingRideServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || !"driver".equals(session.getAttribute("role"))) {
            res.sendRedirect("/taxiapp/login");
            return;
        }
        int driverId = (int) session.getAttribute("userId");
        DriverService driverService = new DriverService();
        DriverRideRequestView ongoingRideInfo = null;
        try {
            ongoingRideInfo = driverService.getRideRequestViewByDriverId(driverId);
        } catch (Exception e) {
            System.err.println("Failed to fetch ongoing ride for driver: " + e.getMessage());
        }
        req.setAttribute("ongoingRide", ongoingRideInfo);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/driverRidePage.jsp");
        dispatcher.forward(req, res);
    }
}
