/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.ProductDAO;
import dto.Product;
import dto.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        if (action == null) {
            action = "";
        }
        User user = (User) request.getSession().getAttribute("LOGIN_USER");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        List<Product> productList = null;
        try {
            switch (action) {
                case "createProduct":
                    create(request, response);
                    break;
                case "deleteProduct":
                    delete(request, response);
                    break;
                case "updateProduct":
                    update(request, response);
                    break;
                case "searchProduct":
                    productList = search(request, response);
                    break;
                case "filterProduct":
                    productList = filter(request, response, user);
                    request.setAttribute("productList", productList);
                    request.getRequestDispatcher("productList.jsp").forward(request, response);
                    break;
            }

            if (productList == null) {
                if ("SE".equals(user.getRoleID())) {
                    productList = productDao.getAllBySellerId(user.getUserID());
                } else {
                    productList = productDao.getAll();
                }
            }
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("productList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> filter(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        Integer cat = null;
        try {
            cat = Integer.parseInt(request.getParameter("categoryID"));
        } catch (Exception ignored) {
        }
        String status = request.getParameter("status");
        Double minPrice = null, maxPrice = null;
        try {
            minPrice = Double.parseDouble(request.getParameter("minPrice"));
        } catch (Exception ignored) {
        }
        try {
            maxPrice = Double.parseDouble(request.getParameter("maxPrice"));
        } catch (Exception ignored) {
        }
        String sid = "SE".equals(user.getRoleID()) ? user.getUserID() : null;

        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        List<Product> plist = null;
        try {
            plist = productDao.filterProducts(cat, status, minPrice, maxPrice, sid, sortBy, sortOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plist;
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        try {
            User user = (User) request.getSession().getAttribute("LOGIN_USER");
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

    public void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        if (productDao.delete(productID)) {
            request.setAttribute("MSG", "delete prodcut success");
        } else {
            request.setAttribute("MSG", "delete prodcut false");
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("LOGIN_USER");
            String name = request.getParameter("name");
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String status = request.getParameter("status");
            int productID = Integer.parseInt(request.getParameter("productID"));
            Product p = new Product(name, categoryID, price, quantity, user.getUserID(), status);
            p.setProductID(productID);
            if (productDao.update(p)) {
                request.setAttribute("MSG", "update prodcut success");
            } else {
                request.setAttribute("MSG", "update prodcut false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> search(HttpServletRequest request, HttpServletResponse response) {
        List<Product> list = new ArrayList();
        try {
            String keyword = request.getParameter("keyword");

            list = productDao.search(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
