package controllers;

import dao.CategoryDAO;
import dto.Category;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Thêm import cho HttpSession
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryManagement", urlPatterns = {"/CategoryManagement"})
public class CategoryManagement extends HttpServlet {

    private static final String ADMIN_ROLE = "AD";
    private CategoryDAO categoryDAO = new CategoryDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String forwardPage = "categoryManagement.jsp"; // Luôn chuyển tiếp đến JSP thống nhất

        // Kiểm tra session và quyền truy cập
        User loginUser  = (User ) request.getSession().getAttribute("LOGIN_USER");
        if (loginUser  == null || !ADMIN_ROLE.equals(loginUser .getRoleID())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Xử lý các hành động
            switch (action) {
                case "CreateCategory":
                    create(request, response);
                    break;
                case "UpdateCategory":
                    update(request, response);
                    break;
                case "DeleteCategory":
                    delete(request, response);
                    break;
                case "SearchCategory":
                    // Xử lý tìm kiếm
                    break;
                default:
                    // Hành động mặc định nếu không có hành động nào được chỉ định
                    break;
            }

            // Sau khi thực hiện hành động (Create, Update, Delete), hoặc cho tìm kiếm ban đầu,
            // lấy lại danh sách các danh mục để hiển thị trên categoryManagement.jsp
            updateCategoryList(request);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error: " + e.getMessage());
            // Trong trường hợp có lỗi, vẫn cố gắng tải danh sách
            try {
                updateCategoryList(request);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("MSG", request.getAttribute("MSG") + " And error loading list: " + ex.getMessage());
            }
        } finally {
            request.getRequestDispatcher(forwardPage).forward(request, response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String name = request.getParameter("categoryName");
        String description = request.getParameter("description");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("MSG", "Tên ngành hàng không được để trống.");
            request.setAttribute("action", "ShowAddCategoryForm"); // Giữ lại trên form thêm
            request.setAttribute("param.categoryName", name); // Tái sử dụng các trường trong form
            request.setAttribute("param.description", description);
            return;
        }

        Category category = new Category();
        category.setCategoryName(name);
        category.setDescription(description);

        boolean inserted = categoryDAO.insert(category);
        if (inserted) {
            request.setAttribute("MSG", "Thêm ngành hàng thành công.");
        } else {
            request.setAttribute("MSG", "Thêm ngành hàng thất bại.");
            request.setAttribute("action", "ShowAddCategoryForm"); // Giữ lại trên form thêm
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String name = request.getParameter("categoryName");
        String description = request.getParameter("description");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("MSG", "Tên ngành hàng không được để trống.");
            request.setAttribute("action", "ShowEditCategoryForm"); // Giữ lại trên form sửa
            request.setAttribute("CATEGORY", new Category(categoryID, name, description)); // Tái sử dụng các trường trong form
            return;
        }

        Category category = new Category();
        category.setCategoryID(categoryID);
        category.setCategoryName(name);
        category.setDescription(description);

        boolean updated = categoryDAO.update(category);

        if (updated) {
            request.setAttribute("MSG", "Cập nhật ngành hàng thành công.");
        } else {
            request.setAttribute("MSG", "Cập nhật ngành hàng thất bại.");
            request.setAttribute("action", "ShowEditCategoryForm"); // Giữ lại trên form sửa
            request.setAttribute("CATEGORY", category); // Tái sử dụng các trường trong form
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        boolean deleted = categoryDAO.delete(categoryID);
        request.setAttribute("MSG", deleted ? "Xóa thành công." : "Xóa thất bại.");
    }

    private void updateCategoryList(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String searchKeyword = request.getParameter("search");
        List<Category> categoryList;
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            categoryList = categoryDAO.getAll();
        } else {
            categoryList = categoryDAO.searchByName(searchKeyword.trim());
        }
        request.setAttribute("CATEGORY_LIST", categoryList);
        request.setAttribute("search", searchKeyword); // Giữ từ khóa tìm kiếm trong trường nhập
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
