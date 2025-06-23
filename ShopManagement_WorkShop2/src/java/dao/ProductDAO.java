package dao;
import dto.Product;
import java.sql.*;
import java.util.*;
import utils.DBUtils;

public class ProductDAO {

    public boolean create(Product product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblProducts(name, categoryID, price, quantity, sellerID, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getSellerID());
            ps.setString(6, product.getStatus());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblProducts SET name=?, categoryID=?, price=?, quantity=?, sellerID=?, status=? WHERE productID=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getSellerID());
            ps.setString(6, product.getStatus());
            ps.setInt(7, product.getProductID());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblProducts WHERE productID=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<Product> search(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblProducts WHERE productID=? OR name LIKE ?";
        List<Product> list = new ArrayList<>();
        int id = -1;
        try {
            id = Integer.parseInt(keyword);
        } catch (NumberFormatException ignored) {}
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setName(rs.getString("name"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerID(rs.getString("sellerID"));
                product.setStatus(rs.getString("status"));
                list.add(product);
            }
        }
        return list;
    }
    
    public List<Product> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblProducts";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setName(rs.getString("name"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerID(rs.getString("sellerID"));
                product.setStatus(rs.getString("status"));
                list.add(product);
            }
        }
        return list;
    }
    
    public List<Product> getDiscountedProducts() throws SQLException, ClassNotFoundException {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT p.*, pr.discountPercent " +
                 "FROM tblProducts p " +
                 "INNER JOIN tblPromotionProducts pp ON p.productID = pp.productID " +
                 "INNER JOIN tblPromotions pr ON pp.promoID = pr.promoID " +
                 "WHERE pr.status = 'active' " +
                 "AND pr.startDate <= GETDATE() " +
                 "AND pr.endDate >= GETDATE() " +
                 "AND p.status = 'active'";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setProductID(rs.getInt("productID"));
            product.setName(rs.getString("name"));
            product.setCategoryID(rs.getInt("categoryID"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setSellerID(rs.getString("sellerID"));
            product.setStatus(rs.getString("status"));
            product.setDiscountPercent(rs.getDouble("discountPercent"));
            list.add(product);
        }  
    }
    return list;
}
}