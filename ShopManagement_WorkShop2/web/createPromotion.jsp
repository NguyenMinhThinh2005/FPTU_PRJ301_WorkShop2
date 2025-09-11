<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Promotion</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            max-width: 500px;
            margin: 0 auto;
            font-family: Arial, sans-serif;
        }
        h1 {
            margin-bottom: 24px;
        }
        form {
            background: #f9f9f9;
            padding: 24px;
            border-radius: 6px;
            border: 1px solid #ddd;
        }
        .form-group {
            margin-bottom: 18px;
        }
        label {
            display: block;
            margin-bottom: 6px;
        }
        input, select {
            width: 100%;
            padding: 8px;
            font-size: 16px;
        }
        .btn {
            padding: 10px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .btn-cancel {
            background-color: #aaa;
        }
        .top-navigation {
            margin-bottom: 24px;
            display: flex;
            justify-content: space-between;
        }
        .message {
            color: green;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<div class="top-navigation">
    <a href="PromotionManagement">Back to List</a>
    <a href="welcome.jsp">Home</a>
</div>
<h1>Create New Promotion</h1>

<c:if test="${not empty MSG}">
    <div class="message">${MSG}</div>
</c:if>

<form method="POST" action="MainController">
    <input type="hidden" name="action" value="createPromotion" />
    <div class="form-group">
        <label for="name">Promotion Name:</label>
        <input type="text" id="name" name="name" required maxlength="100"/>
    </div>
    <div class="form-group">
        <label for="discountPercent">Discount Percent (%):</label>
        <input type="number" id="discountPercent" name="discountPercent" min="0" max="100" step="0.01" required/>
    </div>
    <div class="form-group">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" required/>
    </div>
    <div class="form-group">
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" required/>
    </div>
    <div class="form-group">
        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="active">Active</option>
            <option value="inactive">Inactive</option>
        </select>
    </div>
    <button type="submit" class="btn">Create Promotion</button>
    <a href="PromotionManagement" class="btn btn-cancel" style="margin-left: 10px;">Cancel</a>
</form>
</body>
</html>