<%@ page import="DTO.GameDTO" %>
<%@ page import="DTO.ReviewDTO" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: abibok77
  Date: 07/04/2024
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%GameDTO gameDTO = (GameDTO) request.getAttribute("game");%>
<p><%=gameDTO.getName()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getDirectX()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getOS()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getMemory()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getGraphics()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getProcessor()%></p>
<p><%=gameDTO.getGameRequirementsDTO().getStorage()%></p>
<p><%=gameDTO.getGameStatisticsDTO().getPurchaseCounter()%></p>

<p>----------------</p>

<%--<%Set<ReviewDTO> reviewDTOSet = gameDTO.getReviewDTOSet();

for(ReviewDTO temp : reviewDTOSet){ %>

<p><%=temp.getUserDTO().getUserDescriptionDTO().getName()%></p>
<p><%=temp.getReviewText()%></p>

<% } %>--%>
</body>
</html>
