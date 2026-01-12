package com.example.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("user-id"));
            String role = req.getParameter("role");
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("role", role);

            if ("customer".equals(role)) {
                res.sendRedirect("/taxiapp");
            } else {
                res.sendRedirect("/taxiapp/driver/ride-requests");
            }
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Invalid user ID format: " + e.getMessage());
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }
}
