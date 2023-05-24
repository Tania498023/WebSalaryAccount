<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Title</title>
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

<div class="form-style-2 ">
    <form method="post" action="/home">

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
                <option selected value=""disabled>Список пользователей</option>

                <c:forEach items="${usersName}" var="usersName">
                     <option value="${usersName}">${usersName}</option>
                </c:forEach>

            </select>
        </label>

        <input type="submit" value="Save">
    </form>
</div>


<div class="form-style-2">
    <div class="form-style-2-heading">
        Учет времени сотрудников
    </div>
    <table>
        <tr>
            <th>Дата</th>
            <th>Время</th>
            <th>Работы</th>
            <th>Имя</th>
        </tr>

        <c:forEach items="${usersFromServer}" var="records">
            <tr>

                <td>${records.getDate()}</td>
                <td>${records.getHour()}</td>
                <td>${records.getMessage()}</td>
                <td>${records.getLastName().getLastName()}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<%--<a href="<c:url value='/logout' />">Logout</a>--%>
<div class=form-style-2 input[type=button]">
<input type="button" value="Выход" onclick=location.href='logout'>
</div>
</body>
</html>
