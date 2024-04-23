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
<p><%=gameDTO.getName()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getDirectX()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getOS()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getMemory()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getGraphics()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getProcessor()%>
</p>
<p><%=gameDTO.getGameRequirementsDTO().getStorage()%>
</p>
<p><%=gameDTO.getGameStatisticsDTO().getPurchaseCounter()%>
</p>

<p>----------------</p>
<form>
    <p>Writing a review</p>
    <input type="text" name="text" placeholder="Review text"/>
    <input type="submit">
    <input type="hidden" name="action" value="writeReview"/>
    <input type="hidden" name="id" value="<%=gameDTO.getId()%>"/>
</form>
<% if (request.getAttribute("wrong") != null) { %>
<p>Whoops! Something went wrong!</p>
<p>Try reloading the page!</p>
<% }%>
<p>Reviews:</p>
<%
    Set<ReviewDTO> reviewDTOSet = (Set<ReviewDTO>) request.getAttribute("reviews");
    for (ReviewDTO temp : reviewDTOSet) { %>
<p>--------</p>
<p><%=temp.getUserDTO().getUserDescriptionDTO().getName()%> <%=temp.getUserDTO().getUserDescriptionDTO().getSurname()%>
</p>
<p><%=temp.getReviewText()%>
</p>
<p>--------</p>
<% } %>
</body>
</html>
