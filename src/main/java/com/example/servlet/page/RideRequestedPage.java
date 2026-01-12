package com.example.servlet.page;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/taxiapp/requested")
public class RideRequestedPage extends HttpServlet {
 
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        if(req.getSession(false) == null || req.getSession(false).getAttribute("rideRequested") == null){
            res.sendRedirect("/taxiapp/login");
            return;
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/rideRequestedPage.jsp");
        dispatcher.forward(req, res);
    }
}
