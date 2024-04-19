<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="style2.css">
</head>
<body>
<div class="container">
    <div class="signin-signup">
        <form action="changePass" method="post">
            <h1 class="via"><i class="fa-solid fa-key"></i></h1>
            <h2 class="title" style="text-align: center;">Change Password</h2>
            <p style="margin-top: 15px; margin-bottom: 10px; width: 50vw; position: inherit; text-align: center;">Write down your brand new password! As creative as you can get</p>
            <div class="input-field" style="width: 30vw;">
                <i class="fa-solid fa-key"></i>
                <input type="text" name="new" placeholder="New Password">
            </div>
            <div class="input-field" style="width: 30vw;">
                <i class="fa-solid fa-key"></i>
                <input type="text" name="new2" placeholder="Again">
            </div>
            <input type="submit" value="Change" class="btn">
        </form>
    </div>
    <% if (request.getAttribute("wrong") != null){%>
    <p style="color: #ec6767;">Whoops! Something's wrong</p>
    <% } %>
</div>
</body>