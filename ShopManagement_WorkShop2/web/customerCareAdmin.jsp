<%@ page import="java.util.List" %>
<%@ page import="dto.CustomerCare" %>
<%@ page import="dao.CustomerCaresDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Quản lý CSKH</title><link rel="stylesheet" href="style.css"></head>
<body>
<h2>Danh sách phản hồi khách hàng</h2>
<table border="1">
<tr><th>ID</th><th>UserID</th><th>Chủ đề</th><th>Nội dung</th><th>Trạng thái</th><th>Phản hồi</th><th>Hành động</th></tr>
<%
// Giả sử có hàm getAllTickets() trong CustomerCaresDAO
List<CustomerCare> list = (List<CustomerCare>)request.getAttribute("ticketList");
if (list != null) {
    for (CustomerCare t : list) {
%>
<tr>
    <td><%=t.getTicketID()%></td>
    <td><%=t.getUserID()%></td>
    <td><%=t.getSubject()%></td>
    <td><%=t.getContent()%></td>
    <td><%=t.getStatus()%></td>
    <td><%=t.getReply()%></td>
    <td>
        <form action="MainController" method="post" style="display:inline;">
            <input type="hidden" name="action" value="replyTicket" />
            <input type="hidden" name="ticketID" value="<%=t.getTicketID()%>" />
            Trả lời: <input type="text" name="reply" />
            <select name="status">
                <option value="answered">Đã trả lời</option>
                <option value="closed">Đóng</option>
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