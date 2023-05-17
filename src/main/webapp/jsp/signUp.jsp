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
        <label for="name">Name
            <input class="input-field" type="text" id="name" name="name">
        </label>

        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <input type="submit" value="Регистрация">
        <input type="button" value="Авторизация" onclick=location.href='login'>
    </form>
</div>

<%--<div class="form-style-2">--%>
<%--    <div class="form-style-2-heading">--%>
<%--        Already registered!--%>
<%--    </div>--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>Имя</th>--%>
<%--            <th>Роль</th>--%>

<%--        </tr>--%>
<%--        <c:forEach items="${usersFromServer}" var="user">--%>
<%--            <tr>--%>
<%--                <td>${user.lastName}</td>--%>
<%--                <td>${user.getUserRoleHib()}</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>
<%--</div>--%>
</body>
</html>
