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
<div class="form-style-2">
    <div class="form-style-2-heading">
        Зарегистрируйтесь!
    </div>
    <form method="post" action="/signUp">
        <label for="name">Имя
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label for="role">Роль
            <input class="input-field" type="role" id="role" name="role">
        </label>
        <label for="password">Пароль
            <input class="input-field" type="password" id="password" name="password">
        </label>

        <input type="submit" value="Регистрация">
        <input type="button" value="Авторизация" onclick=location.href='login'>
    </form>
</div>
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
</body>
</html>
