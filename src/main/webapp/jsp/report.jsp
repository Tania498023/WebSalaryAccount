
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Отчеты по заработной плате</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="form-style-2-heading">
    <table>
        <tr>
            <th>Текущий пользователь</th>

        </tr>
        <tr>
            <td>${user}</td>
            <td>${usersRole}</td>
        </tr>
    </table>
</div>
<div class="form-style-2-heading">
    Учет времени и дохода по всем сотрудникам
</div>
<form method="post" action="/report">

    <label for="startDate">Начало периода
        <input class="input-field" type="date" id="startDate" name="startDate">
    </label>
    <label for="endDate">Конец периода
        <input class="input-field" type="date" id="endDate" name="endDate">
    </label>
    <input type="submit" value="Установить">
    <br>
    <br>
</form>

<style>
    .tab-a {
        width:auto;
        float:left;
        height:200px;
        background:cyan;
        border:0;
    }

</style>
<div style="margin: auto 5%;">
    <table class="tab-a">


        <tr id="">
            <td class="tdser">Сотрудник</td>
            <td class="tdser">Начало периода</td>
            <td class="tdser">Конец периода</td>
            <td class="tdser">Отработано часов</td>

         </tr>
    <c:forEach items="${reportForRec}" var="groupDoxod">

        <tr id="downtr">
            <td class="tdser">${groupDoxod.key}</td>
            <td class="tdser">${nachaloRep}</td>
            <td class="tdser">${konecRep}</td>
            <td class="tdser">${groupDoxod.value}</td>
        </tr>
    </c:forEach>
    </table>

    <table class="tab-a" >

                <tr id="">

                     <td class="tdser">Доход</td>

                </tr>
        <c:forEach items="${doxod}" var="mapDoxod">
            <tr id="downtr">

                <td class="tdser">${mapDoxod.value}</td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>
