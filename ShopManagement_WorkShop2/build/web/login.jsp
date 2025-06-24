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
        <title>Shop Management - Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
            .login-container {
                background: white;
                padding: 40px;
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
                text-align: center;
            }
            
            .logo {
                font-size: 2.5em;
                color: #667eea;
                margin-bottom: 10px;
                font-weight: bold;
            }
            
            .subtitle {
                color: #666;
                margin-bottom: 30px;
                font-size: 1.1em;
            }
            
            .form-group {
                margin-bottom: 20px;
                text-align: left;
            }
            
            .form-group label {
                display: block;
                margin-bottom: 8px;
                color: #333;
                font-weight: 500;
            }
            
            .form-group input {
                width: 100%;
                padding: 15px;
                border: 2px solid #e1e5e9;
                border-radius: 10px;
                font-size: 16px;
                transition: border-color 0.3s ease;
            }
            
            .form-group input:focus {
                outline: none;
                border-color: #667eea;
            }
            
            .login-btn {
                width: 100%;
                padding: 15px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                border-radius: 10px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: transform 0.2s ease;
            }
            
            .login-btn:hover {
                transform: translateY(-2px);
            }
            
            .error-message {
                background: #ff6b6b;
                color: white;
                padding: 15px;
                border-radius: 10px;
                margin-bottom: 20px;
                font-size: 14px;
            }
            
            .footer {
                margin-top: 30px;
                color: #666;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="logo">🛍️</div>
            <h1 style="color: #333; margin-bottom: 5px;">Shop Management</h1>
            <p class="subtitle">Đăng nhập để tiếp tục</p>
            
            <c:if test="${not empty sessionScope.MSG}">
                <div class="error-message">
                    <c:out value="${sessionScope.MSG}"/>
                </div>
            </c:if>
            
            <form action="MainController" method="POST">
                <div class="form-group">
                    <label for="userName">Tên đăng nhập</label>
                    <input type="text" id="userName" name="userName" placeholder="Nhập tên đăng nhập" required>
                </div>
                
                <div class="form-group">
                    <label for="passWord">Mật khẩu</label>
                    <input type="password" id="passWord" name="passWord" placeholder="Nhập mật khẩu" required>
                </div>
                
                <button type="submit" name="action" value="login" class="login-btn">
                    Đăng nhập
                </button>
            </form>
            
            <div class="footer">
                © 2025 Shop Management System
            </div>
        </div>
    </body>
</html>
