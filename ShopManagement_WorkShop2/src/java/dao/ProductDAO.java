package dao;

import dto.Product;
import java.sql.*;
import java.util.*;
import utils.DBUtils;

public class ProductDAO {

    public boolean create(Product product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblProducts(name, categoryID, price, quantity, sellerID, status) VALUES (?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (NumberFormatException ignored) {
        }
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public List<Product> getAll() throws SQLException, ClassNotFoundException { // dung cho admin
        String sql = "SELECT * FROM tblProducts";
        List<Product> list = new ArrayList<>();
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public List<Product> getAllBySellerId(String sellerid) throws SQLException, ClassNotFoundException { // dung cho sel   ler
        String sql = "SELECT * FROM tblProducts where sellerID = ?";
        List<Product> list = new ArrayList<>();
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sellerid);
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
        String sql = "SELECT p.*, pr.discountPercent "
                + "FROM tblProducts p "
                + "INNER JOIN tblPromotionProducts pp ON p.productID = pp.productID "
                + "INNER JOIN tblPromotions pr ON pp.promoID = pr.promoID "
                + "WHERE pr.status = 'active' "
                + "AND pr.startDate <= GETDATE() "
                + "AND pr.endDate >= GETDATE() "
                + "AND p.status = 'active'";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public List<Product> filterProducts(
            Integer categoryID, String status,
            Double minPrice, Double maxPrice,
            String sellerID,
            String sortBy, String sortOrder // mới
    ) throws SQLException, ClassNotFoundException {

        StringBuilder sql = new StringBuilder("SELECT * FROM tblProducts WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (categoryID != null) {
            sql.append(" AND categoryID = ?");
            params.add(categoryID);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (minPrice != null) {
            sql.append(" AND price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND price <= ?");
            params.add(maxPrice);
        }
        if (sellerID != null) {
            sql.append(" AND sellerID = ?");
            params.add(sellerID);
        }

        // Validate sortBy để tránh SQL Injection
        Set<String> allowedSort = new HashSet<>(Arrays.asList("name", "price", "quantity"));
        if (sortBy != null && allowedSort.contains(sortBy)) {
            sql.append(" ORDER BY ").append(sortBy).append(" ");
            if ("desc".equalsIgnoreCase(sortOrder)) {
                sql.append("DESC");
            } else {
                sql.append("ASC");
            }
        }
        List<Product> products = new ArrayList<>();
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("productID"));
                p.setName(rs.getString("name"));
                p.setCategoryID(rs.getInt("categoryID"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSellerID(rs.getString("sellerID"));
                p.setStatus(rs.getString("status"));
                products.add(p);
            }
        }
        return products;
    }

    // Phân trang cho sản phẩm
    public List<Product> getProductsByPage(int offset, int limit) throws SQLException, ClassNotFoundException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts ORDER BY productID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
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

    public int getProductCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM tblProducts";
        try (Connection conn = DBUtils.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // Lấy sản phẩm active và còn hàng cho Buyer
    public List<Product> getAllActiveAvailable() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblProducts WHERE status = 'active' AND quantity > 0";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
}
