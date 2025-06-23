/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.ProductDAO;
import dto.Product;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author Thinh
 */
@WebServlet(name = "ProductManagement", urlPatterns = {"/ProductManagement"})
public class ProductManagement extends HttpServlet {

    private ProductDAO productDao = new ProductDAO();
     
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
        String action = request.getParameter("action");
        User user = (User) request.getAttribute("LOGIN_USER");
        try {
            switch (action) {
                case "createProduct":
                    create(request, response);
                    request.setAttribute("productList", productDao.getAllBySellerId(user.getUserID()));
                    request.getRequestDispatcher("productList.jsp").forward(request, response);
                    break;
                case "deleteProduct":
                    delete(request, response);
                    request.setAttribute("productList", productDao.getAllBySellerId(user.getUserID()));
                    request.getRequestDispatcher("productList.jsp").forward(request, response);
                    break;
                case "updateProduct":
                    
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        try {
            User user = (User) request.getAttribute("LOGIN_USER");
            String name = request.getParameter("name");
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String status = request.getParameter("status");
            Product p = new Product(name, categoryID, price, quantity, user.getUserID(), status);
            if (productDao.create(p)) {
                request.setAttribute("MSG", "create prodcut success");
            } else {
                request.setAttribute("MSG", "create prodcut false");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in create Product");
        }
    }
    
    public void delete (HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        if (productDao.delete(productID)) {
            request.setAttribute("MSG", "delete prodcut success");
        } else {
            request.setAttribute("MSG", "delete prodcut false");
        }
    }

    public void update (HttpServletRequest request, HttpServletResponse response) {
        
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
        processRequest(request, response);
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
