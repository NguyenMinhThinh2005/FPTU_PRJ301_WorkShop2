<%-- 
    Document   : login
    Created on : Jun 8, 2025, 12:19:50 PM
    Author     : Thinh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h3 class="text-center">Login</h3>
                    </div>
                    <div class="card-body">
                        <form action="LoginController" method="POST">
                            <div class="mb-3">
                                <label class="form-label">Username</label>
                                <input type="text" name="userName" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label>
                                <input type="password" name="passWord" class="form-control" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Login</button>
                            </div>
                        </form>
                        <c:if test="${not empty requestScope.MSG}">
                            <div class="alert alert-danger mt-3">${requestScope.MSG}</div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
