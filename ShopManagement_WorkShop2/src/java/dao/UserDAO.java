/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.User;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class UserDAO {

    public User login(String userID, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT fullName, roleID, phone FROM tblUsers WHERE userID = ? AND password = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("fullName"), userID, password, rs.getString("roleID"), rs.getString("phone"));
                }
                return null;
            }
        }
    }

    public boolean insert(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblUsers(userID, fullName, roleID, password, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRoleID());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhone());
            return ps.executeUpdate() > 0;
        }
    }
// Method to retrieve all users with optional search
    public List<User> getAll(String search) throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID, password, phone FROM tblUsers WHERE userID LIKE ? OR fullName LIKE ?";
        try (Connection conn = DBUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    list.add(new User(userID, fullName, roleID, password, phone)); // Include phone in User object
                }
            }
        }
        return list;
    }
    
    // Method to check if userID already exists
    public boolean checkDuplicate(String userID) throws Exception {
        String sql = "SELECT userID FROM tblUsers WHERE userID = ?";
        try (Connection conn = DBUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT userID, fullName, roleID, phone FROM tblUsers";
        List<User> users = new ArrayList<>();
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getString("userID"), rs.getString("fullName"), rs.getString("roleID"), null, rs.getString("phone")));
            }
        }
        return users;
    }

    public boolean update(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblUsers SET fullName=?, roleID=?, password=?, phone=? WHERE userID=?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getRoleID());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getUserID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(String userID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblUsers WHERE userID=?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        }
    }
    public User getUserByID(String userID) throws SQLException, ClassNotFoundException {
    String sql = "SELECT userID, fullName, roleID, password, phone FROM tblUsers WHERE userID = ?";
    try (Connection con = DBUtils.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, userID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setRoleID(rs.getString("roleID"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                return user;
            }
            return null; // Không tìm thấy userID
        }
    }
}

     // Trong UserDAO
    public List<User> search(String keyword, int offset, int limit) throws SQLException, ClassNotFoundException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID, phone FROM tblUsers "+
                     "WHERE userID LIKE ? OR fullName LIKE ? "+
                     "ORDER BY userID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setInt(3, offset);
            ps.setInt(4, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(rs.getString("userID"), rs.getString("fullName"),
                                      rs.getString("roleID"), null, rs.getString("phone")));
                }
            }
        }
        return list;
    }
    // Trong UserController sử dụng tham số 'page' để điều chỉnh offset
        // UserDAO.java
    public List<User> getUsersByPage(int offset, int limit) throws SQLException, ClassNotFoundException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID, phone FROM tblUsers ORDER BY userID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User(
                        rs.getString("userID"),
                        rs.getString("fullName"),
                        rs.getString("roleID"),
                        null,
                        rs.getString("phone")
                    );
                    list.add(u);
                }
            }
        }
        return list;
    }

    public int getUserCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM tblUsers";
        try (Connection con = DBUtils.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
    public List<User> searchByUserIDFullNameRoleID(String search) throws Exception {
    List<User> list = new ArrayList<>();
    String sql = "SELECT userID, fullName, roleID, phone FROM tblUsers WHERE userID LIKE ? OR fullName LIKE ? OR roleID LIKE ?";
    try (Connection con = DBUtils.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        String key = "%" + search + "%";
        ps.setString(1, key);
        ps.setString(2, key);
        ps.setString(3, key);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    null,
                    rs.getString("phone")
                ));
            }
        }
    }
    return list;
}

  }

