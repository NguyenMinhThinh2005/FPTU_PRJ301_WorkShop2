<%-- 
    Document   : welcome
    Created on : Jun 16, 2025, 5:15:20 PM
    Author     : lequockhoa
--%>

<%@ page import="dto.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("LOGIN_USER");
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
<h1>Welcome, ${user.fullName}!</h1>
<p>Role: ${user.roleID}</p>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="Logout" />
    <input type="submit" value="??ng xu?t" />
</form>
</body>
</html>
