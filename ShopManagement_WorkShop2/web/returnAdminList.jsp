<%@ page import="java.util.List" %>
<%@ page import="dto.Return" %>
<%@ page import="dao.ReturnDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Quản lý trả hàng</title><link rel="stylesheet" href="style.css"></head>
<body>
<h2>Danh sách yêu cầu trả hàng</h2>
<table border="1">
<tr><th>ID</th><th>InvoiceID</th><th>Reason</th><th>Status</th><th>Hành động</th></tr>
<%
// Giả sử có hàm getAllReturns() trong ReturnDAO
List<Return> list = (List<Return>)request.getAttribute("returnList");
if (list != null) {
    for (Return r : list) {
%>
<tr>
    <td><%=r.getReturnID()%></td>
    <td><%=r.getInvoiceID()%></td>
    <td><%=r.getReason()%></td>
    <td><%=r.getStatus()%></td>
    <td>
        <form action="MainController" method="post" style="display:inline;">
            <input type="hidden" name="action" value="updateStatus" />
            <input type="hidden" name="returnID" value="<%=r.getReturnID()%>" />
            <select name="status">
                <option value="Approved">Duyệt</option>
                <option value="Rejected">Từ chối</option>
            </select>
            <input type="submit" value="Cập nhật" />
        </form>
    </td>
</tr>
<%  }
} %>
</table>
</body>
</html> 