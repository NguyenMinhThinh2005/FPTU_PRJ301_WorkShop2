package controllers;

import dao.UserDAO;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserManagement", urlPatterns = {"/UserManagement"})
public class UserManagement extends HttpServlet {
    

    private static final String ADMIN_ROLE = "AD";
    private UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String forwardPage = "userManagement.jsp"; // Always forward to the unified JSP

        User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
        if (loginUser == null || !ADMIN_ROLE.equals(loginUser.getRoleID())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            switch (action) {
                case "CreateUser":
                    create(request, response);
                    break;
                case "UpdateUser":
                    update(request, response);
                    break;
                case "DeleteUser":
                    delete(request, response, loginUser.getUserID());
                    break;
                case "SearchUser":
                    // This case will handle initial load and actual search
                    break;
                default:
                    // Default action if none specified (e.g., direct access to /UserManagement)
                    break;
            }

            // After any action (Create, Update, Delete), or for initial SearchUser,
            // re-fetch the list of users to display on userManagement.jsp
            String searchKeyword = request.getParameter("search");
            List<User> userList;
            if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
                userList = userDAO.getAllUsers();
            } else {
                userList = userDAO.searchByUserIDFullNameRoleID(searchKeyword.trim());
            }
            request.setAttribute("USER_LIST", userList);
            request.setAttribute("search", searchKeyword); // Keep search keyword in input field

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error: " + e.getMessage());
            // In case of error, still try to load the list
            try {
                request.setAttribute("USER_LIST", userDAO.getAllUsers());
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("MSG", request.getAttribute("MSG") + " And error loading list: " + ex.getMessage());
            }
        } finally {
            request.getRequestDispatcher(forwardPage).forward(request, response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String userID = request.getParameter("userID");
        String fullName = request.getParameter("fullName");
        String roleID = request.getParameter("roleID");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        if (userID == null || fullName == null || roleID == null || password == null
                || userID.trim().isEmpty() || fullName.trim().isEmpty() || roleID.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("MSG", "Vui lòng điền đầy đủ các trường bắt buộc.");
            // Set action to ShowAddUserForm so JSP displays the form again with error
            request.setAttribute("action", "ShowAddUserForm");
            // Re-populate form fields
            request.setAttribute("param.userID", userID);
            request.setAttribute("param.fullName", fullName);
            request.setAttribute("param.roleID", roleID);
            request.setAttribute("param.password", password);
            request.setAttribute("param.phone", phone);
            return;
        }

        try {
            User newUser = new User(userID, fullName, roleID, password, phone);
            boolean result = userDAO.insert(newUser);

            if (result) {
                request.setAttribute("MSG", "Thêm người dùng thành công.");
            } else {
                request.setAttribute("MSG", "Không thể thêm người dùng.");
                request.setAttribute("action", "ShowAddUserForm"); // Stay on add form
            }
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) { // SQLState for unique constraint violation
                request.setAttribute("MSG", "UserID đã tồn tại. Vui lòng chọn UserID khác.");
            } else {
                request.setAttribute("MSG", "Lỗi cơ sở dữ liệu khi thêm người dùng: " + e.getMessage());
            }
            request.setAttribute("action", "ShowAddUserForm"); // Stay on add form
            // Re-populate form fields
            request.setAttribute("param.userID", userID);
            request.setAttribute("param.fullName", fullName);
            request.setAttribute("param.roleID", roleID);
            request.setAttribute("param.password", password);
            request.setAttribute("param.phone", phone);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String userID = request.getParameter("userID");
        String fullName = request.getParameter("fullName");
        String roleID = request.getParameter("roleID");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        if (fullName == null || roleID == null || password == null
                || fullName.trim().isEmpty() || roleID.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("MSG", "Vui lòng điền đầy đủ các trường.");
            // Set action to ShowEditUserForm so JSP displays the form again with error
            request.setAttribute("action", "ShowEditUserForm");
            // Re-populate form fields
            request.setAttribute("USER", new User(userID, fullName, roleID, password, phone));
            return;
        }

        User user = new User(userID, fullName, roleID, password, phone);
        boolean updated = userDAO.update(user);

        if (updated) {
            request.setAttribute("MSG", "Cập nhật người dùng thành công.");
        } else {
            request.setAttribute("MSG", "Cập nhật người dùng thất bại.");
            request.setAttribute("action", "ShowEditUserForm"); // Stay on edit form
            request.setAttribute("USER", user); // Re-populate form fields
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, String currentLoginUserID) throws SQLException, ClassNotFoundException {
        String userID = request.getParameter("userID");
        if (userID != null && userID.equals(currentLoginUserID)) {
            request.setAttribute("MSG", "Bạn không thể xóa chính mình.");
            return;
        }
        boolean deleted = userDAO.delete(userID);
        if (deleted) {
            request.setAttribute("MSG", "Xóa người dùng thành công.");
        } else {
            request.setAttribute("MSG", "Xóa người dùng không thành công.");
        }
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
