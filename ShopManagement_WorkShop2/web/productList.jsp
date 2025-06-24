<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Management</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .active {
            color: green;
        }
        .inactive {
            color: red;
        }
        .actions {
            display: flex;
            gap: 5px;
        }
        .search-form {
            margin: 20px 0;
        }
        .top-navigation {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .btn {
            padding: 8px 12px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            background-color: #dff0d8;
            border: 1px solid #d6e9c6;
            color: #3c763d;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="top-navigation">
    <h1>Product Management</h1>
    <div>
        <a href="welcome.jsp" class="btn">Back to Home</a>
        <c:if test="${sessionScope.LOGIN_USER.roleID eq 'SE'}">
            <a href="createProduct.jsp" class="btn">Create New Product</a>
        </c:if>
    </div>
</div>

<h2>Hello, ${sessionScope.LOGIN_USER.fullName} (Role: ${sessionScope.LOGIN_USER.roleID})</h2>

<c:if test="${not empty MSG}">
    <div class="message">${MSG}</div>
</c:if>

<!-- FORM FILTER + SORT -->
<div class="search-form">
    <form method="POST" action="MainController">
        <input type="hidden" name="action" value="filterProduct" />

        Category:
        <select name="categoryID">
            <option value="">--All--</option>
            <option value="1" ${param.categoryID == '1' ? 'selected' : ''}>Category 1</option>
            <option value="2" ${param.categoryID == '2' ? 'selected' : ''}>Category 2</option>
        </select>

        Status:
        <select name="status">
            <option value="">--All--</option>
            <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
            <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
        </select>

        Price:
        <input type="number" name="minPrice" placeholder="min" value="${param.minPrice}" style="width:80px;"/> -
        <input type="number" name="maxPrice" placeholder="max" value="${param.maxPrice}" style="width:80px;"/>

        <!-- Sort -->
        Sort by:
        <select name="sortBy">
            <option value="">--Default--</option>
            <option value="name" ${param.sortBy == 'name' ? 'selected' : ''}>Name</option>
            <option value="price" ${param.sortBy == 'price' ? 'selected' : ''}>Price</option>
            <option value="quantity" ${param.sortBy == 'quantity' ? 'selected' : ''}>Quantity</option>
            <!-- Thêm nữa tùy ý -->
        </select>
        <select name="sortOrder">
            <option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>Increase</option>
            <option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>Decrease</option>
        </select>

        <button type="submit" class="btn">Filter/Sort</button>
    </form>
</div>

<!-- Bảng sản phẩm -->
<table>
    <tr>
        <th>No</th>
        <th>Product ID</th>
        <th>Name</th>
        <th>Category ID</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Seller ID</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:choose>
        <c:when test="${not empty productList}">
            <c:forEach var="product" items="${productList}" varStatus="counter">
                <c:choose>
                    <c:when test="${sessionScope.LOGIN_USER.roleID eq 'AD' || sessionScope.LOGIN_USER.userID eq product.sellerID}">
                        <tr>
                        <form action="MainController" method="POST" class="actions">
                            <td>${counter.count}</td>
                            <td>
                                <input type="hidden" name="productID" value="${product.productID}"/>
                                ${product.productID}
                            </td>
                            <td>
                                <input type="text" name="name" value="${product.name}" required style="width: 120px;"/>
                            </td>
                            <td>
                                <input type="number" name="categoryID" value="${product.categoryID}" required min="1" style="width: 60px;"/>
                            </td>
                            <td>
                                <input type="number" name="price" value="${product.price}" step="0.01" min="0" required style="width: 80px;"/>
                            </td>
                            <td>
                                <input type="number" name="quantity" value="${product.quantity}" min="0" required style="width: 60px;"/>
                            </td>
                            <td>
                                ${product.sellerID}
                            </td>
                            <td>
                                <select name="status" style="width: 80px;">
                                    <option value="active" ${product.status eq 'active' ? 'selected' : ''}>active</option>
                                    <option value="inactive" ${product.status eq 'inactive' ? 'selected' : ''}>inactive</option>
                                </select>
                            </td>
                            <td class="actions">
                                <button type="submit" name="action" value="updateProduct" class="btn btn-warning">Update</button>
                                <button type="submit" name="action" value="deleteProduct" class="btn btn-danger" onclick="return confirm('Are you sure to delete this product?')">Delete</button>
                            </td>
                        </form>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>${counter.count}</td>
                            <td>${product.productID}</td>
                            <td>${product.name}</td>
                            <td>${product.categoryID}</td>
                            <td>$${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>${product.sellerID}</td>
                            <td class="${product.status == 'active' ? 'active' : 'inactive'}">${product.status}</td>
                            <td></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="9" style="text-align: center;">No products found</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>