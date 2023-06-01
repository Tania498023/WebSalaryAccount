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

    <form method="post" action="/signUp">

        <label for="name">Имя
            <input class="input-field" type="text" id="name" name="name">

        </label>
        <label>Роль
            <select name="role" >

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


        <input type="hidden" value="${'new'}" name="action">
<%--        <input type="button" value="Авторизация" onclick=location.href='login'>--%>
        <input type="submit" value="Сохранить">

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
                <td>${users.getId()}</td>
                <td>${users.getLastName()}</td>
                <td>${users.getUserRoleHib()}</td>
                <td>${users.getPassword()}</td>
                <td>${users.getMonthSalary()}</td>
                <td>${users.getBonus()}</td>
                <td>${users.getPayPerHour()}</td>
                <td><a href="/signUp?action=delete&id=${users.getId()}">delete</a></td>
                <td><a href="/signUp?action=update&id=${users.getId()}">update</a></td>
            </tr>
        </c:forEach>
    </table>
        <br>
        <br>
    Имя:<input type="text" name="username" value="${usUp.getLastName()}"><br><br>
    Роль:<input type="text" name="userrole" value="${usUp.getUserRoleHib()}"><br><br>
    Пароль:<input type="text" name="userpass" value="${usUp.getPassword()}"><br><br>
    Оклад:<input type="text" name="usersalary" value="${usUp.getMonthSalary()}"><br><br>
    Бонус:<input type="text" name="userbonus" value="${usUp.getBonus()}"><br><br>
    Стоимость часа:<input type="text" name="userperhour" value="${usUp.getPayPerHour()}"><br><br>

    <input type="hidden" value="${tre.id==null?'new':'update'}" name="action">
    <input type="submit" value="Изменить">
</div>
    </c:if>
</form>
</body>
</html>
