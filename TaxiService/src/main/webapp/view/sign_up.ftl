<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/sign-in.css">
</head>
<body>
<div class="container">
    <div class="signIn-heading">Регистрация</div>
    <form method="post">
        <label>
            <input class="form-control" name="firstName" type="text" placeholder="Имя">
        </label>
        <label>
            <input class="form-control" name="lastName" type="text" placeholder="Фамилия">
        </label>
        <label>
            <input class="form-control" name="phoneNumber" type="text" placeholder="Номер телефона">
        </label>
        <label>
            <input class="form-control" name="password" type="password" placeholder="Пароль">
        </label>
        <label>
            <input class="form-control" name="age" type="number" min="3" max="100" placeholder="Возраст">
        </label>
        <div class="login-button-div">
            <button class="login-button" type="submit">Зарегистрироваться</button>
        </div>
    </form>
</div>
</body>
</html>