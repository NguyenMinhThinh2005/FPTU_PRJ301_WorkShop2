package controllers;

import dao.PromotionDAO;
import dto.Promotion;
import dto.User;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PromotionManagement", urlPatterns = {"/PromotionManagement"})
public class PromotionManagement extends HttpServlet {
    private PromotionDAO promotionDao = new PromotionDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        User user = (User) request.getSession().getAttribute("LOGIN_USER");
        if (user == null || !"AD".equals(user.getRoleID()) && !"MK".equals(user.getRoleID())) {     
            response.sendRedirect("login.jsp");
            return;
        }

        List<Promotion> promotionList = null;
        try {
            switch (action) {
                case "createPromotion":
                    create(request, response);
                    break;
                case "deletePromotion":
                    delete(request, response);
                    break;
                case "updatePromotion":
                    update(request, response);
                    break;
                case "searchPromotion":
                    promotionList = search(request, response);
                    break;
            }

            if (promotionList == null) {
                promotionList = promotionDao.getAll();
            }
            request.setAttribute("promotionList", promotionList);
            request.getRequestDispatcher("promotionList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String status = request.getParameter("status");
            Promotion promotion = new Promotion(name, discountPercent, startDate, endDate, status); // Bổ sung constructor cho Promotion DTO nếu chưa có
            if (promotionDao.create(promotion)) {
                request.setAttribute("MSG", "Create promotion success");
            } else {
                request.setAttribute("MSG", "Create promotion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error in create");
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int promoID = Integer.parseInt(request.getParameter("promoID"));
            if (promotionDao.delete(promoID)) {
                request.setAttribute("MSG", "Delete promotion success");
            } else {
                request.setAttribute("MSG", "Delete promotion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int promoID = Integer.parseInt(request.getParameter("promoID"));
            String name = request.getParameter("name");
            double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String status = request.getParameter("status");
            Promotion promotion = new Promotion(name, discountPercent, startDate, endDate, status);
            promotion.setPromoID(promoID);
            if (promotionDao.update(promotion)) {
                request.setAttribute("MSG", "Update promotion success");
            } else {
                request.setAttribute("MSG", "Update promotion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error in update");
        }
    }

    public List<Promotion> search(HttpServletRequest request, HttpServletResponse response) {
        List<Promotion> list = null;
        try {
            String keyword = request.getParameter("keyword");
            list = promotionDao.search(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}