<%@ page import="ru.job4j.dream.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
    String value = "Войти";
    boolean isLoggedIn = false;
    boolean isRoot = false;
    if (user != null) {
        isLoggedIn = true;
        value = user.getName() + " | Выйти";
        isRoot = (user.getEmail().equals("root@local"));
    }
%>
<div class="row">
    <ul class="nav">
        <c:if test="<%=isLoggedIn%>">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit_post.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/cities.do?edit=true">Добавить кандидата</a>
            </li>
            <c:if test="<%=isRoot%>">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/cities.do">Города</a>
                </li>
            </c:if>
        </c:if>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value='/auth.do'/>"> <c:out value="<%=value%>"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/reg.do">Зарегистрировать</a>
        </li>
    </ul>
</div>
</body>
</html>
