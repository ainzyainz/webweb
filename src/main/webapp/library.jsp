<%@ page import="entities.User" %>
<%@ page import="DTO.UserDTO" %>
<%@ page import="DTO.UserDescriptionDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="DTO.LibraryDTO" %>
<%@ page import="DTO.GameDTO" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>mainStudent</title>
</head>
<body>
<%--<% int currentPage = (int) request.getAttribute("currentPage");
%>--%>
<head>
    <title>Your library</title>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"/>
</head>
<body>
<link rel="stylesheet" type="text/css" href="style1.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<jsp:include page="header.jsp"/>
<a>My Library</a>
<div class="adminMain">
    <div class="list-div">
        <table>
            <thead>
            <tr>
                <th>Name</th>
            </tr>
            </thead>
            <%
                UserDTO user = (UserDTO) request.getSession().getAttribute("current");
                Set<GameDTO> set = (Set<GameDTO>) request.getAttribute("games");


                for (GameDTO temp : set) { %>
            <tbody>
            <td style="color: white" data-cell="Name"><%= temp.getName() %>
            </td>
            <% }%>
            </tbody>
        </table>
        <jsp:include page="paginationLibrary.jsp"/>
    </div>
</div>
</body>