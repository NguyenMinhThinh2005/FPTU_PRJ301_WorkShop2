<%-- 
    Document   : productList
    Created on : Jun 23, 2025, 7:15:48 PM
    Author     : Thinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ProductManagement</title>
    </head>
    <body>
        <%
            User user = (User) request.getSession().getAttribute("LOGIN_USER");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }   
        %>
        <h1>HI, ${sessionScope.LOGIN_USER.fullName}!</h1>

        <h2>Product List</h2>
        <form method="POST" action="MainController">
            Search: <input type="text" name="searchProduct" placeholder="input id or name ">
            <input type="submit" value="Find">
        </form>

        <table>
            <tr>
                <th>No</th>
                <th>ProductID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>status</th>
            </tr>
        </table>
    </body>
</html>
