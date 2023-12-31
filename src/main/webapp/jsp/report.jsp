
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
    Учет времени и дохода
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


<%--/*отчет по всем с группировкой времени и дохода для Менеджера*/--%>

<c:if test = "${usersRole eq 'MANAGER'}">
<%--    <c:if test = "${action != 'forOne'}">--%>
<table class="tab-a">
        <caption>Итоговый отчет по всем сотрудникам за период с ${startDay} до ${endDay} </caption>
        <tr >
            <td class="tdser">Сотрудник</td>
            <td class="tdser">Начало периода</td>
            <td class="tdser">Конец периода</td>
            <td class="tdser">Отработано часов</td>
         </tr>
    <c:forEach items="${reportForRec}" var="groupDoxod">
        <tr >
            <td class="tdser">${groupDoxod.key}</td>
            <td class="tdser">${startDay}</td>
            <td class="tdser">${endDay}</td>
            <td class="tdser">${groupDoxod.value}</td>
        </tr>
    </c:forEach>
    </table>

    <table class="tab-a">
        <caption><br></caption>
        <tr >
            <td class="tdser">Доход общий</td>
        </tr>
        <c:forEach items="${doxod}" var="mapDoxod">
            <tr >
                <td class="tdser">${mapDoxod.value}</td>
            </tr>
        </c:forEach>

    </table>
<%--    </c:if>--%>


<%--отчет по конкретному сотруднику для Менеджера--%>
<%--    <c:if test = "${action eq 'forOne'}">--%>
    <table class="tab-b">
        <caption>Отчет по сотруднику ${userForReport} за период с ${startDay} по ${endDay}</caption>
        <tr >
            <td class="tdser">Дата</td>
            <td class="tdser">Отработано часов</td>
            <td class="tdser">Описание работ</td>
        </tr>
        <c:forEach items="${listRecForReport}" var="repByOneForManager">

            <tr >
                <td class="tdser">${repByOneForManager.getDate()}</td>
                <td class="tdser">${repByOneForManager.getHour()}</td>
                <td class="tdser">${repByOneForManager.getMessage()}</td>
            </tr>
        </c:forEach>
        <td class="tdserx">  Итого  </td>
        <td class="tdserx">  ${sumHour} </td>
    </table>

</c:if>
<%--</c:if>--%>
<%--отчет по определенному сотруднику для НЕ менеджера--%>

<c:if test = "${usersRole != 'MANAGER'}">

<table class="tab-a">
        <caption>Отчет за период c ${startDay} до ${endDay} по сотруднику </caption>
        <tr >
            <td class="tdser">Дата</td>
            <td class="tdser">Отработано часов</td>
            <td class="tdser">Описание работ</td>
        </tr>
        <c:forEach items="${repForOne}" var="repByOne">
            <tr >
                <td class="tdser">${repByOne.getDate()}</td>
                <td class="tdser">${repByOne.getHour()}</td>
                <td class="tdser">${repByOne.getMessage()}</td>
            </tr>
        </c:forEach>

            <td class="tdserx">  Итого  </td>
            <td class="tdserx">  ${sumHours} </td>
            <td class="tdserx"></td>
</table>

    <table class="tab-a">
        <caption>  ${checkUser}  </caption>
        <tr >
            <td class="tdser">Доход</td>
        </tr>
        <c:forEach items="${listZarplata}" var="zarplata">
            <tr >
                <td class="tdser">${zarplata}</td>
            </tr>
        </c:forEach>
            <td class="tdserx">  ${salaryPerMonth} </td>
        </table>

</c:if>

</body>
</html>
