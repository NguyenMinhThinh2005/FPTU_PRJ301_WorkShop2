<%-- 
    Document   : editUser
    Created on : Jun 17, 2025, 10:39:29 AM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.User" %>
<%
    User user = (User) request.getAttribute("USER");
%>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h2>Edit User</h2>

<form action="UpdateUserController" method="post">
    <input type="hidden" name="userID" value="<%= user.getUserID() %>" />
    Full Name: <input type="text" name="fullName" value="<%= user.getFullName() %>" required/><br/>
    Role:
    <select name="roleID" required>
        <option value="AD" <%= "AD".equals(user.getRoleID()) ? "selected" : "" %>>Admin</option>
        <option value="SE" <%= "SE".equals(user.getRoleID()) ? "selected" : "" %>>Seller</option>
        <option value="BU" <%= "BU".equals(user.getRoleID()) ? "selected" : "" %>>Buyer</option>
        <option value="MK" <%= "MK".equals(user.getRoleID()) ? "selected" : "" %>>Marketing</option>
        <option value="AC" <%= "AC".equals(user.getRoleID()) ? "selected" : "" %>>Accountant</option>
        <option value="DL" <%= "DL".equals(user.getRoleID()) ? "selected" : "" %>>Delivery</option>
        <option value="CS" <%= "CS".equals(user.getRoleID()) ? "selected" : "" %>>Customer Service</option>
    </select><br/>
    Password: <input type="password" name="password" value="<%= user.getPassword() %>" required/><br/>
    Phone: <input type="text" name="phone" value="<%= user.getPhone() %>" /><br/>
    <input type="submit" value="Update"/>
</form>

<p style="color:red;">${MSG}</p>

<a href="SearchUserController">Back to User List</a>
</body>
</html>
