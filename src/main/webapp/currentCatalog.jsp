<%@ page import="DTO.GameDTO" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/7/2024
  Time: 9:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="main">
    <div class="list-div">
        <table>
        <thead>
        <tr>
            <th>Games</th>
            <th class="empty"> </th>
            <th class="empty"> </th>
        </tr>
        </thead>
        <tbody>
        <% Set<GameDTO> gameDTOSet = (Set<GameDTO>) request.getAttribute("games");

            for(GameDTO temp : gameDTOSet){ %>
        <tr>
            <td data-cell="Name"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getName()%></a>
            </td>



            <td style="" class="delete-link">
                <div class="nav-button">
                    <a class="btn white-btn" href="mainPage?action=buy&id=<%=temp.getId()%>">Buy Game</a>
                </div>
            </td>
            <td style="" class="delete-link">
                <div class="nav-button">
                    <a class="btn white-btn" href="mainPage?action=toBin&id=<%=temp.getId()%>">Add to Bin</a>
                </div>
            </td>

            <% }%>
        </tr>
        </tbody>
            </table>
</div>
</div>
</body>
</html>
