<%@ page import="DTO.UserDTO" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/7/2024
  Time: 7:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% UserDTO user = (UserDTO) request.getSession().getAttribute("current"); %>

<head>
    <title><%=user.getUserDescriptionDTO().getName()%></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="main">
<div class="change-form">
    <p>Your balance </p>
    <a class="balance" href="balance?action=addFive">Add 5</a>
    <a class="balance" href="balance?action=addTen">Add 10</a>
    <a class="balance" href="balance?action=addTwenty">Add 20</a>
    <a class="balance" href="balance?action=addFifty">Add 50</a>
    <a class="balance" href="balance?action=addGrand">Add 100</a>

    <form action="profile" method="get">
        <div class="input-box">
            <input type="hidden" name="id" value="<%=user.getId()%>">
            <input type="hidden" name="action" value="changeProfile">
            <p>name:</p>
            <input class="input-field" type="text" name="name" placeholder="<%=user.getUserDescriptionDTO().getName()%>">
        </div>
        <div class="input-box">
            <p>surname:</p>
            <input class="input-field" type="text" name="surname" placeholder="<%=user.getUserDescriptionDTO().getSurname()%>">
        </div>

        <div class="input-box">
            <p>address:</p>
            <input class="input-field" type="text" name="address" placeholder="<%=user.getUserDescriptionDTO().getAddress()%>">
        </div>

        <div class="input-box">
            <p>age:</p>
            <input class="input-field" type="text" name="age" placeholder="<%=user.getUserDescriptionDTO().getAge()%>">
        </div>
        <input type="submit">
    </form>
</div>
</div>
</body>
</html>
