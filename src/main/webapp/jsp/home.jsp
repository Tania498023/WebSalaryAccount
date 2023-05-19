<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>

<form method="post" action="/home">
<%--    <div class="form-style-2">--%>
        <div class="form-style-2-heading">
<%--            Текущий пользователь--%>
<%--        </div>--%>
        <table>
            <tr>
                <th>Текущий пользователь</th>

             </tr>
            <tr>

                <td>${user}</td>

            </tr>
        </table>
    </div>

    <label for="date">Date
        <input class="input-field" type="date" id="date" name="date">
    </label>
    <label for="hour">Hours
        <input class="input-field" type="number" id="hour" name="hour">
    </label>
    <label for="message">Message
        <input class="input-field" type="text" id="message" name="message">
    </label>

    <label for="lastName">Name
    <select name="lastName" id="lastName" >
        <option selected value=""disabled>Список пользователей</option>

        <c:forEach items="${userFromServer}" var="userFromServer">

             <option value="">${userFromServer}</option>
        </c:forEach>

    </select>
    </label>
<div class="form-style-2 input[type=submit]">
    <input type="submit" value="Save">
</div>
</form>

<div class="form-style-2">
    <div class="form-style-2-heading">
        Учет времени сотрудников
    </div>
    <table>
        <tr>
            <th>Date</th>
            <th>Hours</th>
            <th>Message</th>
            <th>Name</th>
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
</body>
</html>
