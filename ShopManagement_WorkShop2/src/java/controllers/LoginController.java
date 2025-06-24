/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.UserDAO;
import dto.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userID = req.getParameter("userName");
        String password = req.getParameter("passWord");
        try {
            User user = new UserDAO().login(userID, password);
            if (user != null) {
                req.getSession().setAttribute("LOGIN_USER", user);
                resp.sendRedirect("welcome.jsp");
            } else {
                req.setAttribute("MSG", "cant find user pls try again");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getSession().invalidate(); 
        resp.sendRedirect("login.jsp");
    }
}

