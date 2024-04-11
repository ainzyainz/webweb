<%@ page import="DTO.UserDTO" %>
<%@ page import="java.util.Set" %>
<%--
  Created by IntelliJ IDEA.
  User: abibok77
  Date: 11/04/2024
  Time: 7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>

<body>
<% Set<UserDTO> users = (Set<UserDTO>) request.getSession().getAttribute("users");
%>
<form action="mainPageAdmin" method="get">
    <input type="hidden" name="action" value="readUser">
    <input type="text" name="search">
    <input type="submit">
</form>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</th>
        <th>Password</th>
        <th>Age</th>
        <th>Address</th>
        <th>Balance</th>
        <th><a href="mainPageAdmin?action=createUser">Add</a></th>
    </tr>
    </thead>
    <tbody>

    <%
        for (UserDTO temp : users) {
    %>

    <tr>
        <td data-cell="Id"><p><%=temp.getId()%></p>
        </td>
        <td data-cell="Name"><p><%=temp.getUserDescriptionDTO().getName()%></p>
        </td>
        <td data-cell="Surname"><p><%=temp.getUserDescriptionDTO().getSurname()%></p>
        </td>
        <td data-cell="Email"><p><%=temp.getEmail()%></p>
        </td>
        <td data-cell="Password"><p><%=temp.getPassword()%></p>
        </td>
        <td data-cell="Age"><p><%=temp.getUserDescriptionDTO().getAge()%></p>
        </td>
        <td data-cell="Address"><p><%=temp.getUserDescriptionDTO().getAddress()%></p>
        </td>
        <td data-cell="Name"><p><%=temp.getBalanceDTO().getBalance()%></p>
        </td>

        <td style="" class="delete-link">
            <div class="nav-button">
                <a class="btn white-btn" href="mainPageAdmin?action=deleteUser&id=<%=temp.getId()%>">Delete User</a>
            </div>
        </td>
        <td style="" class="delete-link">
            <div class="nav-button">
                <a class="btn white-btn" href="mainPageAdmin?action=displayEdit&email=<%=temp.getEmail()%>">Edit</a>
            </div>
        </td>
        <% }%>
    </tr>
    </tbody>
</table>
<a href="mainPageAdmin">Back</a>

<form action="mainPageAdmin" method="get">
    <input type="hidden" name="action" value="createUser"/>
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="password" placeholder="Password">
    <br>
    <input type="text" name="name" placeholder="Name">
    <input type="text" name="surname" placeholder="Surname">
    <br>
    <input type="submit">
</form>


<% UserDTO user = (UserDTO) request.getAttribute("user");%>
<% if (user!=null){
    %>
<form action="mainPageAdmin" method="get">
        <input type="hidden" name="action" value="updateUser">
        <input type="hidden" name="id" value="<%=user.getId()%>">
        <input type="text" name="name" placeholder="<%=user.getUserDescriptionDTO().getName()%>">
        <input type="text" name="surname" placeholder="<%=user.getUserDescriptionDTO().getSurname()%>">
        <input type="text" name="address" placeholder="<%=user.getUserDescriptionDTO().getAddress()%>">
        <input type="text" name="age" placeholder="<%=user.getUserDescriptionDTO().getAge()%>">
        <input type="submit">
    </form>
<% } %>

</body>
</html>
