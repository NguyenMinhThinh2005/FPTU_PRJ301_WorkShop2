/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Promotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author thinhpro
 */
public class PromotionDAO {

    public boolean create(Promotion promotion) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String sql = "insert into tblPromotions(name,discountPercent,startDate,endDate,status) values (?,?,?,?,?)";
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.setString(1, promotion.getName());
            ps.setDouble(2, promotion.getDiscountPercent());
            ps.setDate(3, promotion.getStartDate());
            ps.setDate(4, promotion.getEndDate());
            ps.setString(5, promotion.getStatus());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return result;
    }

    public boolean update(Promotion promotion) throws SQLException, ClassCastException, ClassNotFoundException {
        boolean result = false;
        Connection conn = DBUtils.getConnection();
        String sql = "UPDATE tblPromotions set name = ? , discountPercent = ?, startDate = ?, endDate = ?, status = ? where promoID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.setString(1, promotion.getName());
            ps.setDouble(2, promotion.getDiscountPercent());
            ps.setDate(3, promotion.getStartDate());
            ps.setDate(4, promotion.getEndDate());
            ps.setString(5, promotion.getStatus());
            ps.setInt(6, promotion.getPromoID());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deelete(int id) {
        boolean result = false;
        String sql = "delete from tblPromotions where promoID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Promotion> search(String keyword) {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions WHERE promoID = ? OR name LIKE ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            int id = -1;
            try {
                id = Integer.parseInt(keyword);
            } catch (NumberFormatException e) {
                id = -1;
            }
            ps.setInt(1, id);
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromoID(rs.getInt("promoID"));
                promotion.setDiscountPercent(rs.getDouble("discountPercent"));
                promotion.setStartDate(rs.getDate("startDate"));
                promotion.setEndDate(rs.getDate("endDate"));
                promotion.setStatus(rs.getString("status"));
                list.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Promotion> getAll() {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromoID(rs.getInt("promoID"));
                promotion.setDiscountPercent(rs.getDouble("discountPercent"));
                promotion.setStartDate(rs.getDate("startDate"));
                promotion.setEndDate(rs.getDate("endDate"));
                promotion.setStatus(rs.getString("status"));
                list.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
