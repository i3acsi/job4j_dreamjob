<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" http-equiv="Content-Type" name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>

    <title>AJAX</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<jsp:include page="/nav.jsp"/>
<script>
    function validate() {
        let valid = true
        let hint = $('#hint')
        hint.text("")
        if ($('#newCity').val() === '') {
            hint.text("Название города не может быть пустым")
            valid = false
        }
        return valid
    }

    function addCity() {
        if (validate()){
            const newName = $('#newCity').val()
            const token = '${token}'
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8030/dreamjob/cities.do',
                data: {"id": 0, "name": newName, "token" : token},
                dataType: 'json'
            }).done(function (city) {
                addRow(city.id, city.name)
            }).fail(function (err) {
                if (err.status === 400) {
                    $('#txt').text(err.message)
                } else {
                    alert('op')
                }
            });
        }
    }

    function addRow(id, cityName) {
        let innerHTML = '<tr id = "row' + id + '"><td>' +
            '<button  class="btn btn-primary btn-sm" onclick="updateCity(' + id + ', $(\'#newCity\').val())">'
            + ' <i class="fa fa-edit"></i></button>'
            + '<button  class="btn btn-primary btn-sm" onclick="removeCity(' + id + ')"> '
            + '<i class="fa fa-trash"></i></button>'
            + cityName + '</td></tr>'
        $('table').find('tbody').append(innerHTML)
    }

    function updateCity(id, cityName) {
        if (validate()) {
            const token = '${token}'
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8030/dreamjob/cities.do',
                data: {"id": id, "name": cityName, "token" : token},
                dataType: 'json',
                err: 'error'
            }).done(function (city) {
                removeRow(city.id)
                addRow(city.id, city.name)
            }).fail(function (err) {
                if (err.status === 400) {
                    $('#txt').text(err.message)
                } else {
                    alert('update')
                }
            });
        }
    }

    function removeCity(id) {
        const token = '${token}'
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8030/dreamjob/cities.do',
            data: {
                "id": id, "name": "name",
                "remove": true,
                "token" : token
            },
            dataType: 'json',
        }).done(function (city) {
            removeRow(city.id)
        }).fail(function (err) {
            alert('rem');
        });
    }

    function removeRow(id) {
        let elem = document.getElementById('row' + id)
        elem.remove()
    }

</script>
<script type="text/javascript">
    function clearText() {
        document.getElementById('txt').textContent = ''
    }
</script>
<div class="container pt-3">
    <div class="row">
        <form>
            <div class="form-group">
                <div><label id="txt"></label></div>
                <label for="newCity" id="hint"></label>
                <input type="text" class="form-control" id="newCity"
                       placeholder="Название города" onclick="clearText()">
            </div>
            <button type="button" class="btn btn-primary" onclick="addCity()">Submit</button>
        </form>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Города
            </div>
            <div class="card-body">
                <table class="table" id="table">
                    <tbody>
                    <c:forEach items="${cities}" var="cityJson">
                        <script>
                            city = ${cityJson}
                                addRow(city.id, city.name)
                        </script>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>