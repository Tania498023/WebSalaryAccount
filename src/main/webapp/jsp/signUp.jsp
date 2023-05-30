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
        <input type="submit" value="Регистрация">
<%--        <input type="button" value="Авторизация" onclick=location.href='login'>--%>
    </form>
</div>


    <c:if test = "${roleForSign eq 'MANAGER'}">

<div class="form-style-2">
    <div class="form-style-2-heading">
        Список сотрудников
    </div>
    <table>
        <tr>
            <th>Имя</th>
            <th>Роль</th>
            <th>Пароль</th>
        </tr>

        <c:forEach items="${usersFromServer}" var="users">
            <tr>

                <td>${users.getLastName()}</td>
                <td>${users.getUserRoleHib()}</td>
                <td>${users.getPassword()}</td>

            </tr>
        </c:forEach>
    </table>

</div>
    </c:if>

</body>
</html>
