<%-- 
    Document   : addCategory
    Created on : Jun 16, 2025, 5:16:45 PM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Add Category</h2>

<form action="CreateCategoryController" method="post">
    Category Name: <input type="text" name="categoryName" required/><br/>
    Description: <textarea name="description"></textarea><br/>
    <input type="submit" value="Add"/>
</form>

<p style="color: red;">${MSG}</p>

<a href="SearchCategoryController">Back to Category List</a>
</body>
</html>
