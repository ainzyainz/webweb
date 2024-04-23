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
<%int currentPage = (int) request.getAttribute("currentPage");
    int noOfPages = (int) request.getAttribute("noOfPages");
    if (currentPage != 1) {
%>
<td style="display: inline-block">
    <a href="mainPage?currentPage=<%=currentPage-1%>">Previous</a>
</td>
<%} %>


<%
    if (noOfPages < 5) {
        for (int i = 1; i <= noOfPages; i++) {
            if (i == currentPage) {
%>
<%=i%>

<% } else {%>

<a href="mainPage?currentPage=<%=i%>"><%=i%></a>

<%
        }
    }
} else {
%>

<a href="mainPage?currentPage=1">1</a>
<a href="mainPage?currentPage=2">1</a>

<p><%=currentPage%></p>



<a href="mainPage?currentPage=<%=noOfPages-1%>"><%=noOfPages-1%></a>
<a href="mainPage?currentPage=<%=noOfPages%>"><%=noOfPages%></a>
<% }

    if (currentPage < noOfPages) {
%>
<a href="mainPage?currentPage=<%=currentPage+1%>">Next</a>
<% }%>

</body>
</html>
