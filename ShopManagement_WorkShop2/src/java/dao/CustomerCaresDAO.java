package dao;

import dto.CustomerCare;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class CustomerCaresDAO {
    public static boolean createTicket(String userID, String subject, String content) {
        String sql = "INSERT INTO CustomerCare(userID, subject, content, status) VALUES (?, ?, ?, 'new')";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setString(2, subject);
            ps.setString(3, content);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean replyTicket(int ticketID, String reply, String status) {
        String sql = "UPDATE CustomerCare SET reply = ?, status = ? WHERE ticketID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, reply);
            ps.setString(2, status);
            ps.setInt(3, ticketID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<CustomerCare> getAllTickets() {
        List<CustomerCare> list = new ArrayList<>();
        String sql = "SELECT * FROM CustomerCare";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerCare cc = new CustomerCare(
                    rs.getInt("ticketID"),
                    rs.getString("userID"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getString("status"),
                    rs.getString("reply")
                );
                list.add(cc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<CustomerCare> getTicketsByPage(int offset, int limit) {
        List<CustomerCare> list = new ArrayList<>();
        String sql = "SELECT * FROM CustomerCare ORDER BY ticketID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CustomerCare cc = new CustomerCare(
                        rs.getInt("ticketID"),
                        rs.getString("userID"),
                        rs.getString("subject"),
                        rs.getString("content"),
                        rs.getString("status"),
                        rs.getString("reply")
                    );
                    list.add(cc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getTicketCount() {
        String sql = "SELECT COUNT(*) FROM CustomerCare";
        try (Connection con = DBUtils.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<CustomerCare> search(String keyword) {
        List<CustomerCare> list = new ArrayList<>();
        String sql = "SELECT * FROM CustomerCare WHERE userID LIKE ? OR subject LIKE ? OR content LIKE ? OR status LIKE ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CustomerCare cc = new CustomerCare(
                        rs.getInt("ticketID"),
                        rs.getString("userID"),
                        rs.getString("subject"),
                        rs.getString("content"),
                        rs.getString("status"),
                        rs.getString("reply")
                    );
                    list.add(cc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
} 