<%-- 
    Document   : login
    Created on : Jun 8, 2025, 12:19:50 PM
    Author     : Thinh
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <p style="color: red"> <c:out value="${sessionScope.MSG}"/> </p>
        <form action="LoginController" method="POST">
            UserName: <input type="text" name="userName" placeholder="Username" required> <br>
            PassWord: <input type="password" name="passWord" placeholder="Password" required> <br>
            <input type="submit" name="action" value="login"> 
        </form>
    </body>
</html>
