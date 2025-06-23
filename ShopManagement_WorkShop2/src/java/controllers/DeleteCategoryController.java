/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.CategoryDAO;
import dto.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteCategoryController")
public class DeleteCategoryController extends HttpServlet {
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
            int categoryID = Integer.parseInt(req.getParameter("categoryID"));
            boolean deleted = new CategoryDAO().delete(categoryID);
            req.setAttribute("MSG", deleted ? "Xóa thành công." : "Xóa thất bại.");
            req.getRequestDispatcher("SearchCategoryController").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

