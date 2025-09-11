<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Gửi phản hồi CSKH</title><link rel="stylesheet" href="style.css"></head>
<body>
<h2>Gửi phản hồi chăm sóc khách hàng</h2>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="createTicket" />
    Mã người dùng (userID): <input type="text" name="userID" required /><br/>
    Chủ đề: <input type="text" name="subject" required /><br/>
    Nội dung: <textarea name="content" required></textarea><br/>
    <input type="submit" value="Gửi phản hồi" />
</form>
</body>
</html> 