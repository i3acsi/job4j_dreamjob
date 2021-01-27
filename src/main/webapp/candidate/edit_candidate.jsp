<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.model.City" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.job4j.dream.service.Mapper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
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
    String id = request.getParameter("id");
    Candidate candidate;
    if (id == null) {
        candidate = new Candidate(0, "", 0);
//        city = new City()
    } else {
        String name = request.getParameter("name");
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        candidate = new Candidate(Integer.parseInt(id), name, cityId);
    }
%>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function validate() {
        let result = true
        if (document.getElementById('name').textContent === '') {
            alert('false')
            result = false
        }
        alert('true')
        return result;
    }
    function redirect() {
        document.location.href =  location.origin + '/dreamjob/candidates.do'
    }
    function saveCandidate() {
        let id = <%=candidate.getId()%>;
        let name = $('#name').val();
        let cityId = $('select').find("option:selected").val()
        const token = '${token}'
        $.post({
            url: 'http://localhost:8030/dreamjob/candidates.do',
            data: {
                "id": id,
                "name": name,
                "cityId": cityId,
                "token" : token
            }
        }).done(function () {
            redirect()
        }).fail(function () {
            redirect()
        });
    }
</script>
<jsp:include page="/nav.jsp"/>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>" id="name">
                    </div>
                    <div class="form-group">
                        <label>Город</label>
                        <c:set var="cityId" value="<%=candidate.getCityId()%>" scope="application"/>
                        <select id="select">
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}" ${city.id == cityId ? 'selected' : 'true'}>${city.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="saveCandidate()">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>