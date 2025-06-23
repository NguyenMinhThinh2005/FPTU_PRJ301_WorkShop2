<%-- 
    Document   : categoryList
    Created on : Jun 16, 2025, 5:16:33 PM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="dto.Category"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Category> categoryList = (List<Category>) request.getAttribute("CATEGORY_LIST");
    String search = request.getParameter("search");
%>
<html>
<head>
    <title>Category Management</title>
</head>
<body>
<h2>Category List</h2>

<form method="get" action="SearchCategoryController">
    Search: <input type="text" name="search" value="${search != null ? search : ""}"/>
    <input type="submit" value="Search"/>
</form>

<p style="color: red;">${MSG}</p>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Category Name</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="cat" items="${categoryList}">
        <tr>
            <td>${cat.categoryID}</td>
            <td>${cat.categoryName}</td>
            <td>${cat.description}</td>
            <td>
                <a href="editCategory.jsp?categoryID=${cat.categoryID}">Edit</a> |
                <a href="DeleteCategoryController?categoryID=${cat.categoryID}" onclick="return confirm('Are you sure you want to delete this category?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="addCategory.jsp">Add New Category</a>
</body>
</html>