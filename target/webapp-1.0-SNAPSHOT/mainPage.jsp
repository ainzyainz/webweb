<%@ page import="DTO.UserDTO" %>
<%@ page import="DTO.UserDescriptionDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Game" %>
<%@ page import="DTO.GameDTO" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: abibok77
  Date: 05/04/2024
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="adminMain">


    <%--tyt tipa bestselleri--%>

    <jsp:include page="catalogs.jsp"/>

<% UserDTO user = (UserDTO) request.getSession().getAttribute("current"); %>
    <div class="list-div">
        <table>
            <thead>
            <tr>
                <th>Games</th>
                <th class="empty"> </th>
                <th><a href="mainPage?action=getBin">Bin</a></th>
            </tr>
            </thead>
            <tbody>

            <%
                Set<GameDTO> games = (Set<GameDTO>) request.getSession().getAttribute("games");
                for (GameDTO temp : games) {
            %>

            <tr>
                <td data-cell="Name"><a href="currentGame?id=<%=temp.getId()%>"><%=temp.getName()%></a>
                </td>


                <td style="" class="delete-link">
                    <% if (!user.getBinDTO().getGameDTOSet().contains(temp)&&
                            !user.getLibraryDTO().getGameDTOSet().contains(temp)){
                    %>
                    <div class="nav-button">
                        <a class="btn white-btn" href="mainPage?action=buy&id=<%=temp.getId()%>">Buy Game</a>
                    </div>
                    <% } %>
                </td>
                <td style="" class="delete-link">
                        <% if (!user.getBinDTO().getGameDTOSet().contains(temp)&&
                                !user.getLibraryDTO().getGameDTOSet().contains(temp)){
                            %>
                    <div class="nav-button">

                    <a class="btn white-btn" href="mainPage?action=toBin&id=<%=temp.getId()%>">Add to Bin</a>
                    </div>
                        <% } %>
                </td>

                <% }%>
            </tr>

            </tbody>
        </table>
        <jsp:include page="pagination.jsp"/>
    </div>

</div>
</body>
</html>
