<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="../stylesheet2.css">
</head>
<body>
<div class="container">
    <div class="signin-signup">
        <form action="verifyCode" method="get">
            <h1 class="via"><i class="fa-solid fa-envelope"></i></h1>
            <h2 class="title" style="text-align: center;">Verify Email</h2>
            <p style="margin-top: 15px; margin-bottom: 10px; width: 50vw; position: inherit; text-align: center;">In order to change your password write down your email</p>
            <% if (request.getAttribute("wrong") != null){%>
            <p style="color: #ec6767;">Whoops! Something's wrong</p>
            <% } %>
            <div class="input-field" style="width: 30vw;">
                <i class="fas fa-envelope"></i>
                <input type="text" name="email" placeholder="Email">
            </div>
            <input type="submit" value="Verify" class="btn">
        </form>
    </div>
    <div class="panels-container"></div>
</div>
</body>