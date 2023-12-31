<%@ page import="java.util.ArrayList" %>
<%@ page import="my.project.models.UserHib" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">

</head>
<body>

<div class="form-style-2-heading">
    <c:if test = "${roleForSign eq 'MANAGER'}">
        <table>
            <tr>
                <th>Текущий пользователь</th>
            </tr>
            <tr>
                <td>${user}</td>
                <td>${usersRole}</td>
            </tr>
        </table>
    </c:if>
</div>

<div class="form-style-2">
    <div class="form-style-2-heading">
        Зарегистрироваться/добавить пользователя
    </div>
    <form method="post" action="/signUp" >
        <input type="hidden" value="${'new'}" name="action">
        <label for="name">Имя
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label>Роль
            <select name="role" >
                <option selected value=""disabled> </option>
                    <c:forEach items="${listRoleFromServer}" var="listRoleFromServer">
                        <option value="${listRoleFromServer}">${listRoleFromServer}</option>
                    </c:forEach>
            </select>
        </label>
        <label for="password">Пароль
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <c:if test = "${usersRole eq 'MANAGER'}">

            <label for="monthSalary">Оклад
                <input class="input-field" type="monthSalary" id="monthSalary" name="monthSalary">
            </label>
            <label for="bonus">Бонус
                <input class="input-field" type="bonus" id="bonus" name="bonus">
            </label>

            <label for="payPerHour">Стоимость часа
                <input class="input-field" type="payPerHour" id="payPerHour" name="payPerHour">
            </label>
        </c:if>


        <c:if test = "${usersRole ne 'MANAGER'}">
            <label for="monthSalary">
                <input type="hidden" class="input-field" type="monthSalary" id="monthSalary" name="monthSalary" value = "0.0">
            </label>
            <label for="bonus">
                <input type="hidden" class="input-field" type="bonus" id="bonus" name="bonus"value = "0.0">
            </label>
            <label for="payPerHour">
                <input type="hidden" class="input-field" type="payPerHour" id="payPerHour" name="payPerHour"value = "0.0">
            </label>
        </c:if>

        <input type="submit" value="Сохранить пользователя">
    </form>
</div>

<c:if test = "${roleForSign eq 'MANAGER'}">

    <div class="form-style-2">
        <div class="form-style-2-heading">
            Список сотрудников
        </div>

    <table>
        <tr id="toptr">
            <td class="tdser">ID</td>
            <td class="tdser">Имя</td>
            <td class="tdser">Роль</td>
            <td class="tdser">Пароль</td>
            <td class="tdser">Оклад</td>
            <td class="tdser">Бонус</td>
            <td class="tdser">Стоимость часа</td>
        </tr>

        <c:forEach items="${usersFromServer}" var="users">
            <tr id="downtr">
                <td class="tdser">${users.getId()}</td>
                <td class="tdser">${users.getLastName()}</td>
                <td class="tdser">${users.getUserRoleHib()}</td>
                <td class="tdser">${users.getPassword()}</td>
                <td class="tdser">${users.getMonthSalary()}</td>
                <td class="tdser">${users.getBonus()}</td>
                <td class="tdser">${users.getPayPerHour()}</td>
                <td class="tdser">
                        <button type="submit" onclick=location.href='/signUp?action=update&idSelectedUser=${users.getId()}' >Изменить </button>
                </td>
                    <%--    удаление--%>

                <form method="post" action="/signUp" >
                    <td class="tdser">
                         <button type='submit' name = 'idSelectedUser' value='${users.getId()}'>Удалить </button>
                    </td>
                </form>

            </tr>
        </c:forEach>
    </table>
        <br>
        <br>
    <form method="post" action="/signUp" >
        <input type="hidden" value="${'update'}" name="action">

        Имя:<input type="text" name="username" value="${idForUpdate.getLastName()}"><br><br>
        <label>Роль
            <select name="userrole">
                <option value="${idForUpdate.getUserRoleHib()}">${idForUpdate.getUserRoleHib()}</option>
                    <c:forEach items="${listRoleFromServer}" var="listRoleFromServer">
                        <option value="${listRoleFromServer}">${listRoleFromServer}</option>
                    </c:forEach>
            </select>
        </label>
        Пароль:<input type="text" name="userpass" value="${idForUpdate.getPassword()}"><br><br>
        Оклад:<input type="text" name="usersalary" value="${idForUpdate.getMonthSalary()}"><br><br>
        Бонус:<input type="text" name="userbonus" value="${idForUpdate.getBonus()}"><br><br>
        Стоимость часа:<input type="text" name="userperhour" value="${idForUpdate.getPayPerHour()}"><br><br>

        <input type="submit" value="Редактировать пользователя">

        <input type="button" value="Главное меню" onclick=location.href='home'>
    </form>
</c:if>
<input type="button" value="Выход из приложения" onclick=location.href='logout'>

</div>
</body>
</html>
