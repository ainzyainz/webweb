<%@ page import="DTO.UserDTO" %><%--
  Created by IntelliJ IDEA.
  User: 37529
  Date: 18.03.2024
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style1.css">
    <title>Main Page</title>
</head>
<body>
<% UserDTO user = (UserDTO) request.getSession().getAttribute("current"); %>

<nav class="nav">
    <img class="nav-logo" src="logo.png">
    <p class="nav-logo">GamesPays</p>
    <div class="nav-menu" id="navMenu">
        <ul>
            <li>
                <a class="link" href="mainPageAdmin?action=users">View Users</a>
            </li>
            <li>
                <a class="link" href="mainPageAdmin?action=games">View Games</a>
            </li>
            <li>
                <a class="link" href="mainPageAdmin?action=catalogs">View Catalogs</a>
            </li>
            <li>
                <a class="link" href="mainPageAdmin">Back</a>
            </li>
            <li class="nav-button">
                <button class="btn white-btn" id="loginBtn" onclick="location.href='signUp.jsp';">Exit Profile</button>
            </li>

        </ul>
    </div>
</nav>
</body>
</html>
