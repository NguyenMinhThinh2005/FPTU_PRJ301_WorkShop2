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
                   HttpSession session = req.getSession(); // Lấy hoặc tạo session
                   session.setAttribute("LOGIN_USER", user); // Lưu user vào session

                   // --- Thêm các dòng này để log ---
                   System.out.println("--- LOGIN SUCCESS ---");
                   System.out.println("User  ID: " + user.getUserID());
                   System.out.println("Session ID: " + session.getId());
                   System.out.println("Is new session? " + session.isNew());
                   System.out.println("---------------------");

                   resp.sendRedirect("welcome.jsp"); // Chuyển hướng đến trang welcome
               } else {
                   req.setAttribute("MSG", "Cannot find user, please try again.");
                   req.getRequestDispatcher("login.jsp").forward(req, resp);
               }
           } catch (Exception e) {
               throw new ServletException(e);
           }
       }
   }
   
   

