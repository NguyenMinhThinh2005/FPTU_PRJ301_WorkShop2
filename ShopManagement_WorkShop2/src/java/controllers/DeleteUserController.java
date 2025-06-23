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

@WebServlet("/DeleteUserController")
public class DeleteUserController extends HttpServlet {
    private static final String ADMIN_ROLE = "AD";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User loginUser = (User) req.getSession().getAttribute("LOGIN_USER");
        if (loginUser == null || !ADMIN_ROLE.equals(loginUser.getRoleID())) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            String userID = req.getParameter("userID");
            if (userID != null && userID.equals(loginUser.getUserID())) {
                req.setAttribute("MSG", "Bạn không thể xóa chính mình.");
                req.getRequestDispatcher("SearchUserController").forward(req, resp);
                return;
            }
            boolean deleted = new UserDAO().delete(userID);
            if (deleted) {
                resp.sendRedirect("SearchUserController");
            } else {
                req.setAttribute("MSG", "Xóa người dùng không thành công.");
                req.getRequestDispatcher("SearchUserController").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

