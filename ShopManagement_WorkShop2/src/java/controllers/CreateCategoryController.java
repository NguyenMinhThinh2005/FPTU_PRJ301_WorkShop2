/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.CategoryDAO;
import dto.Category;
import dto.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CreateCategoryController")
public class CreateCategoryController extends HttpServlet {
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
            String name = req.getParameter("categoryName");
            String description = req.getParameter("description");
            
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("MSG", "Tên ngành hàng không được để trống.");
                req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
                return;
            }

            Category category = new Category();
            category.setCategoryName(name);
            category.setDescription(description);

            boolean inserted = new CategoryDAO().insert(category);
            if (inserted) {
                resp.sendRedirect("SearchCategoryController");
            } else {
                req.setAttribute("MSG", "Thêm ngành hàng thất bại.");
                req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

