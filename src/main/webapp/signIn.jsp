<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="style2.css">
</head>
<body>
<div class="container">
    <div class="signin-signup">
        <form method="post">
            <img class="via" src="logo.png">

            <h2 class="title">Sign in</h2>
            <div class="input-field">
                <i class="fas fa-envelope"></i>
                <input type="text" name="email" placeholder="Email">
            </div>
            <div class="input-field">
                <i class="fas fa-lock"></i>
                <input type="password" name="password" placeholder="Password">
            </div>
            <% if (request.getAttribute("wrong") != null){%>
            <p style="color: #ec6767;">Whoops! Something's wrong</p>
            <% } %>
            <input type="submit" value="Sign-in" class="btn">
            <a class="social-text" href="${pageContext.request.contextPath}/signUp">Create new account?</a>
            <a class="social-text" href="forgotPass.jsp">Forgot your password?</a>
            <div class="social-media">
                <a href="https://web.telegram.org/" class="social-icon">
                    <i class="fa-brands fa-telegram"></i>
                </a>
                <a href="https://twitter.com/home?lang=en" class="social-icon">
                    <i class="fab fa-twitter"></i>
                </a>
                <a href="https://www.google.com/webhp?hl=en&sa=X&ved=0ahUKEwj0hoHc4oqFAxWGh_0HHeyDAmcQPAgJ" class="social-icon">
                    <i class="fab fa-google"></i>
                </a>
            </div>
        </form>
    </div>
    <div class="panels-container"></div>
</div>
</body>