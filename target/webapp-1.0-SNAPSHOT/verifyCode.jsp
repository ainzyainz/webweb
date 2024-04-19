<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="style2.css">
</head>
<body>
<div class="container">
    <div class="signin-signup">
        <form method="post" action="verifyCode">
            <h1 class="via"><i class="fa-solid fa-lock"></i></h1>
            <h2 class="title" style="text-align: center;">Verify Password</h2>

            <p style="margin-top: 15px; position: inherit; width: 50vw; text-align: center; margin-bottom: 5px;">Now write down the code bitch:</p>
            <% if (request.getAttribute("wrong") != null){%>
            <p style="color: #ec6767;">Whoops! Something's wrong</p>
            <% } %>
            <div class="input-field" style="width: 30vw;">
                <i class="fa-solid fa-lock"></i>
                <input type="text" name="inputCode" placeholder="Code">
            </div>
            <input type="submit" value="Verify" class="btn">

        </form>
    </div>
    <div class="panels-container"></div>
</div>
</body>