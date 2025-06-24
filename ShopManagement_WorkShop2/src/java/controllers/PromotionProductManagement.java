package controllers;

import dao.PromotionProductDAO;
import dao.PromotionDAO;
import dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/PromotionProductManagement")
public class PromotionProductManagement extends HttpServlet {
    private PromotionProductDAO promoProdDao = new PromotionProductDAO();
    private PromotionDAO promoDao = new PromotionDAO();
    private ProductDAO prodDao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // Load lên data để render form
        req.setAttribute("promotionList", promoDao.getAll());
        try {
            req.setAttribute("productList", prodDao.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionProductManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PromotionProductManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.getRequestDispatcher("promotionProductLink.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String action = req.getParameter("action");
        String msg = "";
        try {
            if ("addProductToPromotion".equals(action)) {
                int promoID = Integer.parseInt(req.getParameter("promoID"));
                int productID = Integer.parseInt(req.getParameter("productID"));
                boolean ok = promoProdDao.addProductToPromotion(promoID, productID);
                msg = ok ? "Gắn sản phẩm thành công!" : "Đã có sản phẩm này trong chương trình!";
            }
            // Có thể thêm action xóa ánh xạ ở đây nếu muốn
        } catch (Exception ex) {
            msg = "Lỗi: " + ex.getMessage();
            ex.printStackTrace();
        }
        req.setAttribute("message", msg);
        doGet(req, resp); // load lại form + list
    }
}