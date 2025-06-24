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

@WebServlet("/CreateUserController")
public class CreateUserController extends HttpServlet {
    private static final String ADMIN_ROLE = "AD";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User loginUser = (User) req.getSession().getAttribute("LOGIN_USER");
        if (loginUser == null || !ADMIN_ROLE.equals(loginUser.getRoleID())) {
            resp.sendRedirect("login.jsp");
            return;
        }
      

        try {
            String userID = req.getParameter("userID");
            String fullName = req.getParameter("fullName");
            String roleID = req.getParameter("roleID");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            
            if (userID == null || fullName == null || roleID == null || password == null
                || userID.trim().isEmpty() || fullName.trim().isEmpty() || roleID.trim().isEmpty() || password.trim().isEmpty()) {
                req.setAttribute("MSG", "Vui lòng điền đầy đủ các trường bắt buộc.");
                req.getRequestDispatcher("addUser.jsp").forward(req, resp);
                return;
            }
            
            User newUser = new User(userID, fullName, roleID, password, phone);
            boolean result = new UserDAO().insert(newUser);

            if (result) {
                resp.sendRedirect("SearchUserController");
            } else {
                req.setAttribute("MSG", "Không thể thêm người dùng.");
                req.getRequestDispatcher("addUser.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

