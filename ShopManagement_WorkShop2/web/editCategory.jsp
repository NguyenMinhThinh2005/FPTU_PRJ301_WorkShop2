<%-- 
    Document   : editCategory
    Created on : Jun 17, 2025, 10:41:13 AM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.Category" %>
<%
    Category category = (Category) request.getAttribute("CATEGORY");
%>
<html>
<head>
    <title>Edit Category</title>
</head>
<body>
<h2>Edit Category</h2>

<form action="UpdateCategoryController" method="post">
    <input type="hidden" name="categoryID" value="<%= category.getCategoryID() %>"/>
    Category Name: <input type="text" name="categoryName" value="<%= category.getCategoryName() %>" required/><br/>
    Description: <textarea name="description"><%= category.getDescription() %></textarea><br/>
    <input type="submit" value="Update"/>
</form>

<p style="color: red;">${MSG}</p>

<a href="SearchCategoryController">Back to Category List</a>
</body>
</html>
