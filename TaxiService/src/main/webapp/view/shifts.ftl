<!DOCTYPE html>
<html lang="en">
<#setting date_format="dd MMMM yyyy 'г.,' HH:mm">
<head>
    <meta charset="UTF-8">
    <title>Shifts</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="../resources/css/shift.css">
</head>
<body>
<div class="long-container">
    <table class="shift-table">
        <tr>
            <th class="green-header">Место отправления</th>
            <th class="green-header">Место прибытия</th>
            <th class="green-header">Время</th>
            <th class="green-header">Водитель</th>
            <th></th>
        </tr>
        <#list shifts as item>
            <tr>
                <td class="green-table-text">${item.departurePlace}</td>
                <td class="green-table-text">${item.arrivalPlace}</td>
                <td class="green-table-text">${item.time?datetime}</td>
                <td class="green-table-text">${item.driver.firstName} </td>
                <td><img class="driver-avatar" alt="IMAGE" src="/files/${item.driver.avatarId}"/></td>
            </tr>
        </#list>
    </table>
</div>
</body>