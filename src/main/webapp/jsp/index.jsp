<%@ page import="java.util.ArrayList" %>
<%@ page import="my.project.models.UserHib" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>index</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
Если вы хотите начать работу с базой данных пользователей - <br>
нажмите кнопку ниже:

<form action = "users" method="get">
    <input type="submit" value="Начать работу с базой данных">
</form>
</body>
</html>
