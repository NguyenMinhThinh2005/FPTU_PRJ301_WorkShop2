<%-- 
    Document   : addUser 
    Created on : Jun 16, 2025, 5:15:52 PM
    Author     : lequockhoa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New User</title>
</head>
<body>
<h2>Add User</h2>

<form action="CreateUserController" method="post">
    UserID: <input type="text" name="userID" required/><br/>
    Full Name: <input type="text" name="fullName" required/><br/>
    Role:
    <select name="roleID" required>
        <option value="AD">Admin</option>
        <option value="SE">Seller</option>
        <option value="BU">Buyer</option>
        <option value="MK">Marketing</option>
        <option value="AC">Accountant</option>
        <option value="DL">Delivery</option>
        <option value="CS">Customer Service</option>
    </select><br/>
    Password: <input type="password" name="password" required/><br/>
    Phone: <input type="text" name="phone"/><br/>
    <input type="submit" value="Add"/>
</form>

<p style="color:red;">${MSG}</p>

<a href="SearchUserController">Back to User List</a>
</body>
</html>