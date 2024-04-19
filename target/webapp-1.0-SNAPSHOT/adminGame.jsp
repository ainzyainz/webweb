<%@ page import="DTO.GameDTO" %>
<%@ page import="java.util.Set" %>
<html>
<head>
    <title>Games</title>
    <link rel="stylesheet" href="style1.css">
</head>
<body>
<jsp:include page="adminHeader.jsp"/>
<div class="adminMain">
    <div class="search-div">
        <form action="mainPageAdmin" method="get">
            <input type="hidden" name="action" value="ZALUPA">
            <input class="input-field" type="text" placeholder="Search Game" name="search">
        </form>
    </div>
    <div class="table-div">
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
                <th><a href="#addGameInput">Add</a></th>
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

                <td class="delete-link">
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
    </div>

    <div id="addGameInput">
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
            <input type="submit" value="Create">
        </form>
    </div>

    <% GameDTO game = (GameDTO) request.getAttribute("game");%>
    <% if (game!=null){
    %>
    <div>
        <p>Edit game</p>
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
    </div>
    <div>
        <p>Add to catalog</p>
        <form action="mainPageAdmin" method="get">
            <input type="hidden" name="action" value="toCatalog">
            <input type="hidden" name="id" value="<%=game.getId()%>"/>
            <input type="text" name="catalog_id"/>
            <input type="submit"/>
        </form>
    </div>

    <% } %>


</div>
</body>
</html>
