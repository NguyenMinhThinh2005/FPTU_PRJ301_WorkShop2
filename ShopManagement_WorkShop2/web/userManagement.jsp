<%-- 
    Document   : userManagement
    Created on : Jun 26, 2025, 8:43:52 PM
    Author     : lequockhoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { margin-bottom: 20px; padding: 15px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9; }
        form input[type="text"], form input[type="password"], form input[type="tel"], form select {
            width: 200px; padding: 8px; margin: 5px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
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
    <h1>User Management</h1>

    <c:if test="${not empty requestScope.MSG}">
        <p class="message">${requestScope.MSG}</p>
    </c:if>

    <%-- Form for Add/Edit User --%>
    <c:choose>
        <c:when test="${param.action eq 'ShowAddUserForm' || param.action eq 'CreateUser'}">
            <h2>Add New User</h2>
            <form action="MainController" method="POST">
                <input type="hidden" name="action" value="CreateUser"/>
                User ID: <input type="text" name="userID" value="${param.userID}" required/><br/>
                Full Name: <input type="text" name="fullName" value="${param.fullName}" required/><br/>
                Role ID:
                <select name="roleID" required>
                    <option value="AD" ${param.roleID eq 'AD' ? 'selected' : ''}>Admin</option>
                    <option value="US" ${param.roleID eq 'US' ? 'selected' : ''}>User</option>
                    <option value="SE" ${param.roleID eq 'SE' ? 'selected' : ''}>Seller</option>
                    <option value="MK" ${param.roleID eq 'MK' ? 'selected' : ''}>Marketing</option>
                </select><br/>
                Password: <input type="password" name="password" value="${param.password}" required/><br/>
                Phone: <input type="tel" name="phone" value="${param.phone}"/><br/>
                <input type="submit" value="Create User"/>
                <a href="MainController?action=SearchUser">Cancel</a>
            </form>
        </c:when>
        <c:when test="${param.action eq 'ShowEditUserForm' || param.action eq 'UpdateUser'}">
            <h2>Edit User</h2>
            <form action="MainController" method="POST">
                <input type="hidden" name="action" value="UpdateUser"/>
                <input type="hidden" name="userID" value="${requestScope.USER.userID}"/>
                User ID: ${requestScope.USER.userID}<br/>
                Full Name: <input type="text" name="fullName" value="${requestScope.USER.fullName}" required/><br/>
                Role ID:
                <select name="roleID" required>
                    <option value="AD" ${requestScope.USER.roleID eq 'AD' ? 'selected' : ''}>Admin</option>
                    <option value="US" ${requestScope.USER.roleID eq 'US' ? 'selected' : ''}>User</option>
                    <option value="SE" ${requestScope.USER.roleID eq 'SE' ? 'selected' : ''}>Seller</option>
                    <option value="MK" ${requestScope.USER.roleID eq 'MK' ? 'selected' : ''}>Marketing</option>
                </select><br/>
                Password: <input type="password" name="password" value="${requestScope.USER.password}" required/><br/>
                Phone: <input type="tel" name="phone" value="${requestScope.USER.phone}"/><br/>
                <input type="submit" value="Update User"/>
                <a href="MainController?action=SearchUser">Cancel</a>
            </form>
        </c:when>
        <c:otherwise>
            <%-- Search Form --%>
            <h2>User List</h2>
            <form action="MainController" method="GET">
                <input type="hidden" name="action" value="SearchUser"/>
                Search: <input type="text" name="search" value="${param.search}"/>
                <input type="submit" value="Search"/>
            </form>

            <p><a href="MainController?action=CreateUser">Add New User</a></p>

            <%-- User List Table --%>
            <c:if test="${not empty requestScope.USER_LIST}">
                <table>
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Full Name</th>
                            <th>Role ID</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${requestScope.USER_LIST}">
                            <tr>
                                <td>${user.userID}</td>
                                <td>${user.fullName}</td>
                                <td>${user.roleID}</td>
                                <td>${user.phone}</td>
                                <td class="action-links">
                                    <a href="MainController?action=UpdateUser&userID=${user.userID}">Edit</a>
                                    <a href="MainController?action=DeleteUser&userID=${user.userID}"
                                       onclick="return confirm('Are you sure you want to delete ${user.fullName}?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty requestScope.USER_LIST}">
                <p>No users found.</p>
            </c:if>
        </c:otherwise>
    </c:choose>
</body>
</html>
