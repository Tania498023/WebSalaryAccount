<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>


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

<div class="form-style-2 ">
<%--    создание новой записи(record) --%>
    <form method="post" action="/home">
        <input type="hidden" value="${'new'}" name="action">
        <label for="date">Date
            <input class="input-field" type="date" id="date" name="date">
        </label>
        <label for="hour">Hours
            <input class="input-field" type="number" id="hour" name="hour">
        </label>
        <label for="message">Message
            <input class="input-field" type="text" id="message" name="message">
        </label>
        <label>Name

            <select name="lastName" >

                <c:if test = "${usersRole eq 'MANAGER'}">
                    <option selected value=""disabled> </option>
                <c:forEach items="${usersName}" var="usersName">
                     <option value="${usersName}">${usersName}</option>
                </c:forEach>
                </c:if>
                <c:if test = "${usersRole != 'MANAGER'}">
                    <option selected value=""disabled> </option>
                    <option value="${user}">${user}</option>
                </c:if>

            </select>
        </label>

        <input type="submit" value="Добавить запись">
    </form>

        <c:if test = "${chekRoleForHome eq 'MANAGER'}">


    <div class="form-style-2-heading">
        Корректировка записи
    </div>
<table>
        <tr id="toptr">
            <td class="tdser">Имя</td>
            <td class="tdser">Дата</td>
            <td class="tdser">Время</td>
            <td class="tdser">Работы</td>

        </tr>

        <c:forEach items="${usersFromServer}" var="records">
            <tr id="downtr">
                <td class="tdser">${records.getLastName().getLastName()}</td>
                <td class="tdser">${records.getDate()}</td>
                <td class="tdser">${records.getHour()}</td>
                <td class="tdser">${records.getMessage()}</td>


                <td class="tdser">
                    <button type="submit" onclick=location.href='/home?userForReport=${records.getLastName().getLastName()}' >Отчет </button>
                </td>

                <td class="tdser">
                    <button type="submit" onclick=location.href='/home?action=update&idSelectedRec=${records.getId()}' >Изменить </button>
                </td>

                <form method="post" action="/home" >
                    <td class="tdser">
                        <button type='submit' name = 'idSelectedRec' value='${records.getId()}'>Удалить </button>
                    </td>
                </form>
            </tr>
        </c:forEach>
</table>
    <br>
    <br>
<form method="post" action="/home">
        <input type="hidden" value="${'update'}" name="action">
        Имя:<input type="text" name="recForUpdate" value="${recForUpdate.getLastName().getLastName()}" ><br><br>
        Дата:<input type="text" name="recDate" value="${recForUpdate.getDate()}" ><br><br>
        Время:<input type="text" name="recHours" value="${recForUpdate.getHour()}" ><br><br>
        Работы:<input type="text" name="recMess" value="${recForUpdate.getMessage()}" ><br><br>

          <input type="submit" value="Изменить запись">
</form>

</div>


</c:if>

<div class=form-style-2 input[type=button]">
<c:if test = "${chekRoleForHome eq 'MANAGER'}">
    <input type="button" value="Добавить пользователя" onclick=location.href='signUp'>
</c:if>
    <input type="button" value="Сформировать отчет по зарплате" onclick=location.href='report'>
</div>

    <div class=form-style-2 >
    <input type="button" value="Выход из приложения" onclick=location.href='logout'>
    </div>

</body>
</html>
