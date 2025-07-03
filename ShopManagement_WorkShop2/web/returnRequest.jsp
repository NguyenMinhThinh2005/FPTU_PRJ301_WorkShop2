<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Yêu cầu trả hàng</title><link rel="stylesheet" href="style.css"></head>
<body>
<h2>Gửi yêu cầu trả hàng</h2>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="requestReturn" />
    Mã hóa đơn (invoiceID): <input type="text" name="invoiceID" required /><br/>
    Lý do trả hàng: <input type="text" name="reason" required /><br/>
    <input type="submit" value="Gửi yêu cầu" />
</form>
</body>
</html> 