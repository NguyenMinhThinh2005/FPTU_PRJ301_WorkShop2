/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Category;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public boolean insert(Category category) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblCategories(categoryName, description) VALUES (?, ?)";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Category> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblCategories";
        List<Category> list = new ArrayList<>();
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(
                    rs.getInt("categoryID"),
                    rs.getString("categoryName"),
                    rs.getString("description")
                ));
            }
        }
        return list;
    }
    
    public Category getCategoryByID(int categoryID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblCategories WHERE categoryID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("categoryID"),
                            rs.getString("categoryName"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }

    public boolean update(Category category) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblCategories SET categoryName=?, description=? WHERE categoryID=?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryID());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean delete(int categoryID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblCategories WHERE categoryID=?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            return ps.executeUpdate() > 0;
        }
    }
    
    // Method to search categories by name
    public List<Category> searchByName(String searchName) throws SQLException, ClassNotFoundException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategories WHERE categoryName LIKE ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + searchName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getString("description")
                    ));
                }
            }
        }
        return list;
    }
}



