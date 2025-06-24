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
import dao.CustomerCaresDAO;

/**
 *
 * @author ACER
 */
@WebServlet(name="CustomerCaresController", urlPatterns={"/CustomerCaresController"})
public class CustomerCaresController extends HttpServlet {
   
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
            out.println("<title>Servlet CustomerCaresController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerCaresController at " + request.getContextPath () + "</h1>");
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
            if (action.equals("createTicket")) {
                String userID = request.getParameter("userID");
                String subject = request.getParameter("subject");
                String content = request.getParameter("content");
                boolean result = CustomerCaresDAO.createTicket(userID, subject, content);
                message = result ? "Gửi phản hồi thành công." : "Gửi phản hồi thất bại.";
            } else if (action.equals("replyTicket")) {
                try {
                    int ticketID = Integer.parseInt(request.getParameter("ticketID"));
                    String reply = request.getParameter("reply");
                    String status = request.getParameter("status");
                    boolean result = CustomerCaresDAO.replyTicket(ticketID, reply, status);
                    message = result ? "Phản hồi thành công." : "Phản hồi thất bại.";
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
