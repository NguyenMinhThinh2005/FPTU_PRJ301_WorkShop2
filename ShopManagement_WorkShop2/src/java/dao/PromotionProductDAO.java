package dao;
import java.sql.*;
import utils.DBUtils;

public class PromotionProductDAO {
    // Gắn sản phẩm với promotion
    public boolean addProductToPromotion(int promoID, int productID) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblPromotionProducts (promoID, productID) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            ps.setInt(2, productID);
            return ps.executeUpdate() > 0;
        }
    }

    // Xóa sản phẩm khỏi promotion
    public boolean removeProductFromPromotion(int promoID, int productID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblPromotionProducts WHERE promoID = ? AND productID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            ps.setInt(2, productID);
            return ps.executeUpdate() > 0;
        }
    }
}