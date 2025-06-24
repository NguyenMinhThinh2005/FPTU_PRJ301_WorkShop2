/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Thinh
 */
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "login.jsp";
        try {
            String action = request.getParameter("action");
            if ("Logout".equals(action)) {
                response.sendRedirect("LogoutController");
            } else if ("CreateUser".equals(action)) {
                request.getRequestDispatcher("CreateUserController").forward(request, response);
            } else if ("UpdateUser".equals(action)) {
                request.getRequestDispatcher("UpdateUserController").forward(request, response);
            } else if ("DeleteUser".equals(action)) {
                request.getRequestDispatcher("DeleteUserController").forward(request, response);
            } else if ("SearchUser".equals(action)) {
                request.getRequestDispatcher("SearchUserController").forward(request, response);
            } else if ("CreateCategory".equals(action)) {
                request.getRequestDispatcher("CreateCategoryController").forward(request, response);
            } else if ("UpdateCategory".equals(action)) {
                request.getRequestDispatcher("UpdateCategoryController").forward(request, response);
            } else if ("DeleteCategory".equals(action)) {
                request.getRequestDispatcher("DeleteCategoryController").forward(request, response);
            } else if ("SearchCategory".equals(action)) {
                request.getRequestDispatcher("SearchCategoryController").forward(request, response);
            } else if (action.equals("createProduct")) {
                request.getRequestDispatcher("ProductManagement").forward(request, response);
            } else if (action.equals("deleteProduct")) {
                request.getRequestDispatcher("ProductManagement").forward(request, response);
            } else if (action.equals("updateProduct")) {
                request.getRequestDispatcher("ProductManagement").forward(request, response);
            } else if (action.equals("searchProduct")) {
                request.getRequestDispatcher("ProductManagement").forward(request, response);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
