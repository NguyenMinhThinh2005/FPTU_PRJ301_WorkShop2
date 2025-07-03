<%-- 
    Document   : categoryManagement
    Created on : Jun 26, 2025, 8:44:15 PM
    Author     : lequockhoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Category Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { margin-bottom: 20px; padding: 15px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9; }
        form input[type="text"], form textarea {
            width: 300px; padding: 8px; margin: 5px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }
        form input[type="submit"] {
            background-color: #4CAF50; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer;
        }
        form input[type="submit"]:hover { background-color: #45a049; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .message { color: red; font-weight: bold; margin-bottom: 10px; }
        .success { color: green; font-weight: bold; margin-bottom: 10px; }
        .action-links a { margin-right: 10px; text-decoration: none; color: #007bff; }
        .action-links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Category Management</h1>

    <c:if test="${not empty requestScope.MSG}">
        <p class="message">${requestScope.MSG}</p>
    </c:if>

    <%-- Form for Add/Edit Category --%>
    <c:choose>
        <c:when test="${param.action eq 'ShowAddCategoryForm' || param.action eq 'CreateCategory'}">
            <h2>Add New Category</h2>
            <form action="MainController" method="POST">
                <input type="hidden" name="action" value="CreateCategory"/>
                Category Name: <input type="text" name="categoryName" value="${param.categoryName}" required/><br/>
                Description: <textarea name="description">${param.description}</textarea><br/>
                <input type="submit" value="Create Category"/>
                <a href="MainController?action=SearchCategory">Cancel</a>
            </form>
        </c:when>
        <c:when test="${param.action eq 'ShowEditCategoryForm' || param.action eq 'UpdateCategory'}">
            <h2>Edit Category</h2>
            <form action="MainController" method="POST">
                <input type="hidden" name="action" value="UpdateCategory"/>
                <input type="hidden" name="categoryID" value="${requestScope.CATEGORY.categoryID}"/>
                Category ID: ${requestScope.CATEGORY.categoryID}<br/>
                Category Name: <input type="text" name="categoryName" value="${requestScope.CATEGORY.categoryName}" required/><br/>
                Description: <textarea name="description">${requestScope.CATEGORY.description}</textarea><br/>
                <input type="submit" value="Update Category"/>
                <a href="MainController?action=SearchCategory">Cancel</a>
            </form>
        </c:when>
        <c:otherwise>
            <%-- Search Form --%>
            <h2>Category List</h2>
            <form action="MainController" method="GET">
                <input type="hidden" name="action" value="SearchCategory"/>
                Search: <input type="text" name="search" value="${param.search}"/>
                <input type="submit" value="Search"/>
            </form>

            <p><a href="MainController?action=CreateCategory">Add New Category</a></p>

            <%-- Category List Table --%>
            <c:if test="${not empty requestScope.CATEGORY_LIST}">
                <table>
                    <thead>
                        <tr>
                            <th>Category ID</th>
                            <th>Category Name</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                            <tr>
                                <td>${category.categoryID}</td>
                                <td>${category.categoryName}</td>
                                <td>${category.description}</td>
                                <td class="action-links">
                                    <a href="MainController?action=UpdateCategory&categoryID=${category.categoryID}">Edit</a>
                                    <a href="MainController?action=DeleteCategory&categoryID=${category.categoryID}"
                                       onclick="return confirm('Are you sure you want to delete ${category.categoryName}?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty requestScope.CATEGORY_LIST}">
                <p>No categories found.</p>
            </c:if>
        </c:otherwise>
    </c:choose>
</body>
</html>
