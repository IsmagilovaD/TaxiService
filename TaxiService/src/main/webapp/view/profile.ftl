<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="../resources/css/profile.css">
    <title>Profile</title>

</head>
<body>
<div class="long-container">
    <div class="top-content">
        <div class="profile-heading">Профиль</div>

        <div class="sign-out-container">
            <form class="upload" action="/sign-out">
                <button class="logout-button">Выход</button>
            </form>
        </div>
    </div>
    <#if user.avatarId??>
        <img class="avatar" alt="IMAGE" src="/files/${user.avatarId}"/>
    <#else>
        <img class="avatar" alt="IMAGE" src="../img/no-avatar.png"/>
    </#if>

    <div class="name">${user.firstName}</div>

    <form class="upload" action="/avatar-upload">
        <button class="light-button">Загрузить аватар</button>
    </form>

    <form class="upload" action="/take-trip">
        <button class="light-button">Заказать такси</button>
    </form>

    <form class="upload" action="/shifts">
        <button class="light-button">Посмотреть поездки</button>
    </form>
</div>
</body>
</html>