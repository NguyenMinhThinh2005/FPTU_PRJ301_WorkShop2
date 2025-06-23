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
import java.util.List;

@WebServlet("/SearchUserController")
public class SearchUserController extends HttpServlet {
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
        String search = req.getParameter("search");
        List<User> list;
        if (search == null || search.trim().isEmpty()) {
            list = new UserDAO().getAllUsers();
        } else {
            list = new UserDAO().searchByUserIDFullNameRoleID(search.trim());
        }
        req.setAttribute("USER_LIST", list);
        req.setAttribute("search", search);
        req.getRequestDispatcher("userList.jsp").forward(req, resp);
    } catch (Exception e) {
        throw new ServletException(e);
    }
}
}

