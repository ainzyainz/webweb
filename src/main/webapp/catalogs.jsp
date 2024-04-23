<%@ page import="DTO.CatalogDTO" %>
<%@ page import="java.util.Set" %>
<%@ page import="entities.Catalog" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/7/2024
  Time: 8:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalogs</title>
</head>
<body>
<%
     Set<CatalogDTO> catalogDTOSet = (Set<CatalogDTO>) request.getAttribute("catalogs");

    for (CatalogDTO temp : catalogDTOSet) { %>

<a href="currentCatalog?id=<%=temp.getId()%>"><%=temp.getName()%></a>
    <% }%>

</body>
</html>
