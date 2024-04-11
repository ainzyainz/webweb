<%@ page import="DTO.CatalogDTO" %>
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
    <title>Catalog</title>
</head>
<body>
<% Set<CatalogDTO> catalogDTOSet = (Set<CatalogDTO>) request.getSession().getAttribute("catalogs");
%>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Amount of games</th>
        <th><a href="mainPageAdmin?action=createCatalog">Add</a></th>
    </tr>
    </thead>
    <tbody>

    <%
        for (CatalogDTO temp : catalogDTOSet) {
    %>

    <tr>
        <td data-cell="Id"><p><%=temp.getId()%>
        </p>
        </td>
        <td data-cell="Name"><p><%=temp.getName()%>
        </p>
        </td>
        <td data-cell="Name"><p><%=temp.getGameDTOS().size()%>
        </p>
        </td>

        <td style="" class="delete-link">
            <div class="nav-button">
                <a class="btn white-btn" href="mainPageAdmin?action=deleteCatalog&id=<%=temp.getId()%>">Delete
                    Catalog</a>
            </div>
        </td>
        <td style="" class="edit-link">
            <div class="nav-button">
                <a class="btn white-btn" href="mainPage?action=toBin&id=<%=temp.getId()%>">Add to Bin</a>
            </div>
        </td>

        <% }%>
    </tr>
    </tbody>
</table>
<a href="mainPageAdmin">Back</a>
</body>
</html>
