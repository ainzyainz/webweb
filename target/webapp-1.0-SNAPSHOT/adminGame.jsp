<%@ page import="DTO.GameDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: abibok77
  Date: 11/04/2024
  Time: 7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Games</title>
</head>
<body>
<div class="main">
    <jsp:include page="adminHeader.jsp"/>
    <form action="mainPageAdmin" method="get">
        <input type="hidden" name="action" value="ZALUPA">
        <input type="text" name="search">
        <input type="submit">
    </form>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>PurchaseCounter</th>
            <th>Price</th>
            <th>OS</th>
            <th>Processor</th>
            <th>Memory</th>
            <th>Graphics</th>
            <th>DirectX</th>
            <th>Storage</th>
            <th class="empty"> </th>
            <th><a href="mainPageAdmin?action=createGame">Add</a></th>
        </tr>
        </thead>
        <tbody>

        <%
            Set<GameDTO> games = (Set<GameDTO>) request.getSession().getAttribute("games");
            for (GameDTO temp : games) {
        %>

        <tr>
            <td data-cell="Id"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getId()%></a>
            </td>
            <td data-cell="Name"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getName()%></a>
            </td>
            <td data-cell="PurchaseCounter"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameStatisticsDTO().getPurchaseCounter()%></a>
            </td>
            <td data-cell="Price"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameStatisticsDTO().getPrice()%></a>
            </td>
            <td data-cell="OS"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getOS()%></a>
            </td>
            </td>
            <td data-cell="Processor"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getProcessor()%></a>
            </td>
            <td data-cell="Memory"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getMemory()%></a>
            </td>
            <td data-cell="Graphics"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getGraphics()%></a>
            </td>
            <td data-cell="DirectX"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getDirectX()%></a>
            </td>
            <td data-cell="Storage"><a href="currentGame?name=<%=temp.getName()%>"><%=temp.getGameRequirementsDTO().getStorage()%></a>
            </td>

            <td style="" class="delete-link">
                <div class="nav-button">
                    <a class="btn white-btn" href="mainPageAdmin?action=deleteGame&id=<%=temp.getId()%>">Delete Game</a>
                </div>
            </td>
            <td style="" class="delete-link">
                <div class="nav-button">
                    <a class="btn white-btn" href="mainPageAdmin?action=displayEditGame&name=<%=temp.getName()%>">Edit</a>
                </div>
            </td>

            <% }%>
        </tr>
        </tbody>
    </table>
    <a href="mainPageAdmin">Back</a>

    <form action="mainPageAdmin" method="get">
        <input type="hidden" name="action" value="createGame"/>
        <input type="text" name="price" placeholder="Price">
        <input type="text" name="name" placeholder="Name">
        <br>
        <input type="text" name="OS" placeholder="OS">
        <input type="text" name="processor" placeholder="Processor">
        <br>
        <input type="text" name="memory" placeholder="Memory">
        <input type="text" name="graphics" placeholder="Graphics">
        <br>
        <input type="text" name="directX" placeholder="DirectX">
        <input type="text" name="storage" placeholder="Storage">
        <br>
        <input type="submit">
    </form>

    <% GameDTO game = (GameDTO) request.getAttribute("game");%>
    <% if (game!=null){
    %>
    <form action="mainPageAdmin" method="get">
        <input type="hidden" name="action" value="updateGame">
        <input type="hidden" name="id" value="<%=game.getId()%>">
        <br>
        <input type="text" name="price" placeholder="<%=game.getGameStatisticsDTO().getPrice()%>">
        <input type="text" name="name" placeholder="<%=game.getName()%>">
        <br>
        <input type="text" name="OS" placeholder="<%=game.getGameRequirementsDTO().getOS()%>">
        <input type="text" name="processor" placeholder="<%=game.getGameRequirementsDTO().getProcessor()%>">
        <br>
        <input type="text" name="memory" placeholder="<%=game.getGameRequirementsDTO().getMemory()%>">
        <input type="text" name="graphics" placeholder="<%=game.getGameRequirementsDTO().getGraphics()%>">
        <br>
        <input type="text" name="directX" placeholder="<%=game.getGameRequirementsDTO().getDirectX()%>">
        <input type="text" name="storage" placeholder="<%=game.getGameRequirementsDTO().getStorage()%>">
        <br>
        <input type="submit">
    </form>

    <p>Add to catalog</p>
    <form action="mainPageAdmin" method="get">
        <input type="hidden" name="action" value="toCatalog">
        <input type="hidden" name="id" value="<%=game.getId()%>"/>
        <input type="text" name="catalog_id"/>
        <input type="submit"/>
    </form>

    <% } %>


</div>
</body>
</html>
