<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.User"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gắn Sản phẩm với Khuyến mãi</title>
    <style>
        .msg { color: green; }
        table { border-collapse: collapse; width: 80%; margin: 24px auto; }
        th, td { border: 1px solid #999; padding: 6px;}
    </style>
</head>
<body>
<%
    User user = (User) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<h2>Gắn sản phẩm vào chương trình khuyến mãi</h2>

<c:if test="${not empty message}">
    <div class="msg">${message}</div>
</c:if>

<form action="MainController" method="post" style="margin-bottom:20px;">
    <input type="hidden" name="action" value="addProductToPromotion"/>
    Promotion:
    <select name="promoID" required>
        <c:forEach var="pr" items="${promotionList}">
            <option value="${pr.promoID}">${pr.name} (${pr.discountPercent}%)</option>
        </c:forEach>
    </select>
    Product:
    <select name="productID" required>
        <c:forEach var="prd" items="${productList}">
            <option value="${prd.productID}">${prd.name}</option>
        </c:forEach>
    </select>
    <button type="submit">Gắn Sản Phẩm</button>
</form>

<a href="welcome.jsp">Quay về Home</a>
</body>
</html>