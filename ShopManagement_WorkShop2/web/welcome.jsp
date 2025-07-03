<%-- 
    Document   : welcome
    Created on : Jun 16, 2025, 5:15:20 PM
    Author     : lequockhoa
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.User" %>
<%@ page session="true" %>
<%
    User user = (User) request.getSession().getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }   
%>
<html>
    <head>
        <title>Welcome</title>
    </head>
    <body>
        <h1>Welcome, ${sessionScope.LOGIN_USER.fullName}!</h1>
        <p>Role: ${sessionScope.LOGIN_USER.roleID}</p>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="Logout" />
            <input type="submit" value="Logout" />
        </form>
        <c:if test="${sessionScope.LOGIN_USER.roleID eq 'AD' or sessionScope.LOGIN_USER.roleID eq 'SE'}">
            <a href="ProductManagement">Product Management</a>
        </c:if>
            <a href="PromotionManagement">Promotion Management</a>
    </body>
</html>
