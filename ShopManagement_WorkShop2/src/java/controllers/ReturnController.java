/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.ReturnDAO;

/**
 *
 * @author ACER
 */
@WebServlet(name="ReturnController", urlPatterns={"/ReturnController"})
public class ReturnController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReturnController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReturnController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        String message = "";
        if (action != null) {
            if (action.equals("requestReturn")) {
                try {
                    int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
                    String reason = request.getParameter("reason");
                    boolean result = ReturnDAO.insert(invoiceID, reason);
                    message = result ? "Yêu cầu trả hàng đã được gửi." : "Gửi yêu cầu thất bại.";
                } catch (Exception e) {
                    message = "Lỗi dữ liệu đầu vào.";
                }
            } else if (action.equals("updateStatus")) {
                try {
                    int returnID = Integer.parseInt(request.getParameter("returnID"));
                    String status = request.getParameter("status");
                    boolean result = ReturnDAO.updateStatus(returnID, status);
                    message = result ? "Cập nhật trạng thái thành công." : "Cập nhật thất bại.";
                } catch (Exception e) {
                    message = "Lỗi dữ liệu đầu vào.";
                }
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h2>" + message + "</h2>");
            out.println("</body></html>");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
