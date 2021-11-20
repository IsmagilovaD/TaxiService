<!DOCTYPE html>
<html lang="en">
<#setting date_format="dd MMMM yyyy 'г.,' HH:mm">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/take_trip.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/successful-trip.css">
    <title>Successful trip</title>
</head>
<body>
<div class="long-container">
    <div class="successfulTrip-heading">Ваша поездка успешно оформлена!</div>
    <div class="info-card">
        <div class="green-text">Откуда: ${shift.departurePlace}</div>
        <div class="green-text">Куда: ${shift.arrivalPlace}</div>
        <div class="green-text">Время поездки: ${shift.time?datetime}</div>
        <div class="green-text">Ваш водитель: ${shift.driver.firstName}</div>
        <#if shift.driver.avatarId??>
            <img class="driver-avatar" alt="IMAGE" src="/files/${shift.driver.avatarId}"/>
        <#else>
            <img class="driver-avatar" alt="IMAGE" src="../img/no-avatar.png"/>
        </#if>


        <form class="upload" action="/shifts">
            <button class="light-button">Посмотреть поездки</button>
        </form>
        <form class="upload" action="/profile">
            <button class="light-button">Профиль</button>
        </form>
    </div>
</div>
</body>
</html>