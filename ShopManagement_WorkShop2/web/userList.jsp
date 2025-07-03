<%-- 
    Document   : userList
    Created on : Jun 16, 2025, 5:15:37 PM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="dto.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<User> userList = (List<User>) request.getAttribute("USER_LIST");
    String search = request.getParameter("search");
%>
<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>User List</h2>

<form method="get" action="SearchUserController">
    Search: <input type="text" name="search" value="${search != null ? search : ""}"/>
    <input type="submit" value="Search"/>
</form>

<p style="color: red;">${MSG}</p>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>UserID</th>
        <th>Full Name</th>
        <th>Role</th>
        <th>Phone</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userID}</td>
            <td>${user.fullName}</td>
            <td>${user.roleID}</td>
            <td>${user.phone}</td>
            <td>
                <a href="editUser.jsp?userID=${user.userID}">Edit</a> |
                <a href="DeleteUserController?userID=${user.userID}" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="addUser.jsp">Add New User</a>
</body>
</html>
