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

@WebServlet("/UpdateCategoryController")
public class UpdateCategoryController extends HttpServlet {
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
            int categoryID = Integer.parseInt(req.getParameter("categoryID"));
            String name = req.getParameter("categoryName");
            String description = req.getParameter("description");
            
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("MSG", "Tên ngành hàng không được để trống.");
                req.getRequestDispatcher("editCategory.jsp").forward(req, resp);
                return;
            }

            Category category = new Category();
            category.setCategoryID(categoryID);
            category.setCategoryName(name);
            category.setDescription(description);

            boolean updated = new CategoryDAO().update(category);

            if (updated) {
                resp.sendRedirect("SearchCategoryController");
            } else {
                req.setAttribute("MSG", "Cập nhật ngành hàng thất bại.");
                req.setAttribute("CATEGORY", category);
                req.getRequestDispatcher("editCategory.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

