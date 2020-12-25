<%@ page contentType="text/html; charset=UTF-8" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Работа мечты</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type='text/javascript'>
    function getCityName(id) {
        var res = '';
        var address = id;
        $.ajax({
            url: 'http://localhost:8030/dreamjob/cities',
            type: 'get',
            data: {cityId: + address.toString()},
            dataType: 'json',
            err: 'error'
        }).done(function (data)  {
            alert(data)
            res = data
        }).fail(function (err) {
            alert('err')
            res = err
        });
        alert(res)
        return res
    }
</script>

<jsp:include page="/nav.jsp"/>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Кандидаты
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Имя</th>
                        <th scope="col">Фото</th>
                        <th scope="col">Город</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td>
                                <c:out value="${candidate.name}"/>
                                <br><a href='<c:url value="/candidate/edit_candidate.jsp?id=${candidate.id}"/>'>
                                <i class="fa fa-edit mr-3"></i></a>
                                <br><a href='<c:url value="/candidates.do?id=${candidate.id}&action=remove"/>'>
                                <i class="fa fa-remove mr-3"></i></a>
                            </td>
                            <td>
                                <a href="<c:url value='/upload?id=${candidate.id}'/>">
                                    <i class="fa fa-edit mr-3">Загрузить фото</i></a>
                                <p></p><img src="<c:url value='/download?name=${candidate.id}'/>" width="100px"
                                            height="100px"/>
                                <p></p><a href="<c:url value='/download?name=${candidate.id}'/>">Скачать</a>
                            </td>
                            <td>
                                <c:out value="${candidate.cityName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--</script type='text/javascript'>--%>
<%--function ajaxGet(id) {--%>
<%--    var req = new XMLHttpRequest()--%>
<%--    var resp = '';--%>
<%--    var url ='http://localhost:8030/dreamjob/cities.do?id='+id;--%>
<%--    req.onreadystatechange = function () {--%>
<%--        if (req.readyState === 4){--%>
<%--            resp = req.responseText;--%>
<%--        }--%>
<%--    }--%>
<%--    req.open('get', url)--%>
<%--    req.send()--%>
<%--    while (resp === ''){--%>

<%--    }--%>
<%--    return resp;--%>
<%--}--%>
<%--</script>--%>
</body>
</html>