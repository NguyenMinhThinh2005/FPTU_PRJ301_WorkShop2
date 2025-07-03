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
            } else if ("CreateUser".equals(action) || "UpdateUser".equals(action) || "DeleteUser".equals(action) || "SearchUser".equals(action)) {
                request.getRequestDispatcher("UserManagement").forward(request, response);
            } else if ("CreateCategory".equals(action) || "UpdateCategory".equals(action) || "DeleteCategory".equals(action) || "SearchCategory".equals(action)) {
                request.getRequestDispatcher("CategoryManagement").forward(request, response);
            } else if ("createPromotion".equals(action)
                    || "deletePromotion".equals(action)
                    || "updatePromotion".equals(action)
                    || "searchPromotion".equals(action)) {
                request.getRequestDispatcher("PromotionManagement").forward(request, response);
            } else if ("addProductToPromotion".equals(action)) {
                request.getRequestDispatcher("PromotionProductManagement").forward(request, response);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response); // Assuming you have an error.jsp
        
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
