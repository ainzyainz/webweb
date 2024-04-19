<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2024
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%int currentPage = (int) request.getSession().getAttribute("currentPage");
    int noOfPages = (int) request.getSession().getAttribute("noOfPages");
    if (currentPage != 1) {
%>
<td style="display: inline-block">
    <a href="mainPage?page=<%=currentPage-1%>">Previous</a>
</td>
<%} %>


<%
    if (noOfPages < 5) {
        for (int i = 1; i <= noOfPages; i++) {
            if (i == currentPage) {
%>
<%=i%>

<% } else {%>

<a href="mainPage?page=<%=i%>"><%=i%></a>

<%
        }
    }
} else {
%>

<a href="mainPage?page=1">1</a>
<a href="mainPage?page=2">1</a>

<p><%=currentPage%></p>



<a href="mainPage?page=<%=noOfPages-1%>"><%=noOfPages-1%></a>
<a href="mainPage?page=<%=noOfPages%>"><%=noOfPages%></a>
<% }

    if (currentPage < noOfPages) {
%>
<a href="mainPage?page=<%=currentPage+1%>">Next</a>
<% }%>

</body>
</html>
