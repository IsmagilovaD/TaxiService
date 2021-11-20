<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/sign-in.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="/js/login.js"></script>
</head>
<body>
<div class="container">
    <div class="signIn-heading">Вход</div>
    <form id="signIn" method="post">
        <div><label>
                <input id="phone" class="form-control" name="phoneNumber" type="text" placeholder="Номер телефона">
            </label></div>
        <div><label>
                <input id="password" class="form-control" name="password" type="password" placeholder="Пароль">
            </label></div>
        <div class="login-button-div">
            <button class="login-button" type="submit">Войти</button>
        </div>

    </form>

</div>
</body>
</html>