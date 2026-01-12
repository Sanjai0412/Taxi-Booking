package com.example.servlet.page;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/taxiapp/login")
public class LoginPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("role") != null){
                String role = (String) session.getAttribute("role");
                if("customer".equals(role)){
                res.sendRedirect("/taxiapp");
            }else{
                res.sendRedirect("/taxiapp/driver");
            }
            return;
        }
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, res);
    }
}
