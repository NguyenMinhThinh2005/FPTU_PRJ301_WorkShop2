<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Promotion Management</title>
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
        .active { color: green; }
        .inactive { color: red; }
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
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-danger { background-color: #f44336; }
        .btn-warning { background-color: #ff9800; }
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
    <h1>Promotion Management</h1>
    <div>
        <a href="welcome.jsp" class="btn">Back to Home</a>
        <c:if test="${sessionScope.LOGIN_USER.roleID eq 'AD'}">
            <a href="createPromotion.jsp" class="btn">Create New Promotion</a>
        </c:if>
    </div>
</div>
<h2>Hello, ${sessionScope.LOGIN_USER.fullName} (Role: ${sessionScope.LOGIN_USER.roleID})</h2>
<c:if test="${not empty MSG}">
    <div class="message">${MSG}</div>
</c:if>
<!-- Search form -->
<div class="search-form">
    <form method="POST" action="MainController">
        <input type="text" name="keyword" placeholder="Search by ID or Name" value="${param.keyword}">
        <input type="hidden" name="action" value="searchPromotion">
        <button type="submit" class="btn">Search</button>
    </form>
</div>
<table>
    <tr>
        <th>No</th>
        <th>ID</th>
        <th>Name</th>
        <th>Discount (%)</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:choose>
        <c:when test="${not empty promotionList}">
            <c:forEach var="promotion" items="${promotionList}" varStatus="counter">
                <tr>
                <form action="MainController" method="POST">
                    <td>${counter.count}</td>
                    <td>
                        <input type="hidden" name="promoID" value="${promotion.promoID}" />
                        ${promotion.promoID}
                    </td>
                    <td>
                        <input type="text" name="name" value="${promotion.name}" required style="width: 120px;"/>
                    </td>
                    <td>
                        <input type="number" name="discountPercent" value="${promotion.discountPercent}" min="0" max="100" step="0.01" required style="width: 80px;"/>
                    </td>
                    <td>
                        <input type="date" name="startDate" value="${promotion.startDate}" required style="width: 120px;"/>
                    </td>
                    <td>
                        <input type="date" name="endDate" value="${promotion.endDate}" required style="width: 120px;"/>
                    </td>
                    <td>
                        <select name="status" style="width: 80px;">
                            <option value="active" ${promotion.status eq 'active' ? 'selected' : ''}>active</option>
                            <option value="inactive" ${promotion.status eq 'inactive' ? 'selected' : ''}>inactive</option>
                        </select>
                    </td>
                    <td class="actions">
                        <button type="submit" name="action" value="updatePromotion" class="btn btn-warning">Update</button>
                        <button type="submit" name="action" value="deletePromotion" class="btn btn-danger" onclick="return confirm('Delete this promotion?')">Delete</button>
                    </td>
                </form>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="8" style="text-align: center;">No promotions found</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>