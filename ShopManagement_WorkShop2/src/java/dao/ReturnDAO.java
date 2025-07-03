package dao;
import java.sql.*;
import utils.DBUtils;
public class ReturnDAO {
    public static boolean insert(int invoiceID, String reason) {
        String sql = "INSERT INTO tblReturns(invoiceID, reason, status) VALUES (?, ?, 'Pending')";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ps.setString(2, reason);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateStatus(int returnID, String status) {
        String sql = "UPDATE tblReturns SET status = ? WHERE returnID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, returnID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static java.util.List<dto.Return> getAll() {
        java.util.List<dto.Return> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM tblReturns";
        try (Connection con = utils.DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new dto.Return(
                    rs.getInt("returnID"),
                    rs.getInt("invoiceID"),
                    rs.getString("reason"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static java.util.List<dto.Return> getByPage(int offset, int limit) {
        java.util.List<dto.Return> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM tblReturns ORDER BY returnID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = utils.DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new dto.Return(
                        rs.getInt("returnID"),
                        rs.getInt("invoiceID"),
                        rs.getString("reason"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getCount() {
        String sql = "SELECT COUNT(*) FROM tblReturns";
        try (Connection con = utils.DBUtils.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static java.util.List<dto.Return> search(String keyword) {
        java.util.List<dto.Return> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM tblReturns WHERE CAST(invoiceID AS VARCHAR) LIKE ? OR reason LIKE ? OR status LIKE ?";
        try (Connection con = utils.DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new dto.Return(
                        rs.getInt("returnID"),
                        rs.getInt("invoiceID"),
                        rs.getString("reason"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}