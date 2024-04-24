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
            <li><a href="mainPage" class="link">Store</a></li>
            <li class="input-box">
                <form style="margin-top: 18px;" action="mainPage" method="get">
                    <input type="hidden" name="action" value="read">
                    <input type="text" class="input-field" name="search" placeholder="Search...">
                </form>
            </li>
            <li><a href="profile?action=getLibrary" class="link">Library</a></li>
            <li><a href="profile?action=getProfile" class="link"><%=user.getUserDescriptionDTO().getName()%></a></li>
            <li><a href="profile?action=getProfile" class="link">$<%=user.getBalanceDTO().getBalance()%></a></li>
            <li class="nav-button">
                <button class="btn white-btn" id="loginBtn" onclick="location.href='profile?action=exit';">Exit Profile</button>
            </li>
        </ul>
    </div>

</nav>
</body>
</html>
