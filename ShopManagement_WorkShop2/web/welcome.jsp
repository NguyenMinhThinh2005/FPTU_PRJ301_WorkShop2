<%-- 
    Document   : welcome
    Created on : Jun 16, 2025, 5:15:20 PM
    Author     : lequockhoa
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page session="true" %>
<%
    User user = (User  ) request.getSession().getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }   
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1>Welcome, ${sessionScope.LOGIN_USER.fullName}!</h1>
        <p>Role: ${sessionScope.LOGIN_USER.roleID}</p>
        
        <div class="mt-4">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="Logout" />
                <button type="submit" class="btn btn-danger">Logout</button>
            </form>
        </div>

        <div class="mt-4">
            <c:if test="${sessionScope.LOGIN_USER.roleID eq 'AD' or sessionScope.LOGIN_USER.roleID eq 'SE'}">
                <a href="UserManagement" class="btn btn-primary">User  Management</a>
                <a href="ProductManagement" class="btn btn-info">Product Management</a>
                <a href="CategoryManagement" class="btn btn-warning">Category Management</a>
            </c:if>
            <a href="PromotionManagement" class="btn btn-success">Promotion Management</a>
            <c:if test="${sessionScope.LOGIN_USER.roleID eq 'BU'}">
                <a href="ProductManagement" class="btn" style="background:#007bff;">Xem sản phẩm</a>
            </c:if>
        </div>
    </div>
</body>
</html>
