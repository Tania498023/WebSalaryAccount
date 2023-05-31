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
            <td class="tdser">Имя</td>
            <td class="tdser">Роль</td>
            <td class="tdser">Пароль</td>
            <td class="tdser">Оклад</td>
            <td class="tdser">Бонус</td>
            <td class="tdser">Стоимость часа</td>
        </tr>

        <c:forEach items="${usersFromServer}" var="users">
            <tr id="downtr">

                <td>${users.getLastName()}</td>
                <td>${users.getUserRoleHib()}</td>
                <td>${users.getPassword()}</td>
                <td>${users.getMonthSalary()}</td>
                <td>${users.getBonus()}</td>
                <td>${users.getPayPerHour()}</td>
                <td><a href="/users?action=delete&id=${users.getLastName()}">delete</a></td>
                <td><a href="/users?action=update&id=${users.getLastName()}">update</a></td>
            </tr>
        </c:forEach>
    </table>
        <br>
        <br>
    Имя:<input type="text" name="bookname" value="${booking.name}"><br><br>
    Book author:<input type="text" name="bookauthor" value="${booking.author}"><br><br>
    Book year:<input type="text" name="bookyear" value="${booking.year}"><br><br>
    <input type="hidden" value="new" name="action">
    <input type="hidden" value="${booking.id}" name="bookingid">
    <input type="submit" value="submit">
</div>
    </c:if>
</form>
</body>
</html>
