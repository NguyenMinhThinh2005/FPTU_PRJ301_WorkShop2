<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Sản phẩm giảm giá</title>
    <style>
        table { border-collapse: collapse; width: 85%; margin: 24px auto; }
        th, td { border: 1px solid #999; padding: 6px;}
        th { background: #eee; }
    </style>
</head>
<body>
<h2>Danh sách Sản phẩm đang giảm giá</h2>
<table>
    <tr>
        <th>Tên sản phẩm</th>
        <th>Giá gốc</th>
        <th>% giảm</th>
        <th>Giá còn lại</th>
    </tr>
    <c:forEach var="p" items="${discountList}">
        <tr>
            <td>${p.name}</td>
            <td><fmt:formatNumber value="${p.price}" pattern="#,##0.##"/></td>
            <td>${p.discountPercent}</td>
            <td>
                <fmt:formatNumber value="${p.price - (p.price*p.discountPercent/100)}" pattern="#,##0.##"/>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="welcome.jsp">Quay về Home</a>
</body>
</html>