<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Product</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], 
        input[type="number"],
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-link {
            text-decoration: none;
            color: #2196F3;
            margin-left: 10px;
        }
        .error {
            color: red;
            font-size: 0.9em;
        }
        .top-navigation {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            align-items: center;
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
        <h1>Create New Product</h1>
        <div>
            <a href="ProductManagement" class="btn-link">Back to Products</a>
            <a href="welcome.jsp" class="btn-link">Home</a>
        </div>
    </div>
    
    <c:if test="${not empty MSG}">
        <div class="error">${MSG}</div>
    </c:if>
    
    <form action="MainController" method="POST">
        <div class="form-group">
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        
        <div class="form-group">
            <label for="categoryID">Category:</label>
            <select id="categoryID" name="categoryID" required>

                <c:if test="${not empty categoryList}">
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.categoryID}">${category.name}</option>
                    </c:forEach>
                </c:if>

                <c:if test="${empty categoryList}">
                    <option value="1">Category 1</option>
                    <option value="2">Category 2</option>
                    <option value="3">Category 3</option>

                </c:if>
            </select>
        </div>
        
        <div class="form-group">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" min="0" required>
        </div>
        
        <div class="form-group">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="0" required>
        </div>
        
        <div class="form-group"> 
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>
        </div>
        
        <div class="form-group">
            <input type="hidden" name="action" value="createProduct">
            <button type="submit" class="btn">Create Product</button>
            <a href="ProductManagement" class="btn-link">Cancel</a>
        </div>
    </form>
</body>
</html>