package controllers;

import dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import dto.Product;

@WebServlet("/DiscountedProducts")
public class DiscountedProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            List<Product> discountList = new ProductDAO().getDiscountedProducts();
            request.setAttribute("discountList", discountList);
        } catch (Exception ex) {
            request.setAttribute("message", "Lỗi: " + ex.getMessage());
        }
        request.getRequestDispatcher("discountedProductList.jsp").forward(request, response);
    }
}