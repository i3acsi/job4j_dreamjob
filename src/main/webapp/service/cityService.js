function addCity() {
    if (validate()) {
        const newName = $('#newCity').val()
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8030/dreamjob/cities.do',
            data: {"id": 0, "name": newName},
            dataType: 'json',
            err: 'error'
        }).done(function (city) {
            addRow(city.id, city.name)
        }).fail(function (err) {
            if (err.status === 400) {
                $('#txt').text('Город с таким названием уже существует')
            } else {
                alert(err)
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
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8030/dreamjob/cities.do',
            data: {"id": id, "name": cityName},
            dataType: 'json',
            err: 'error'
        }).done(function (city) {
            removeRow(city.id)
            addRow(city.id, city.name)
        }).fail(function (err) {
            if (err.status === 400) {
                $('#txt').text('Город с таким названием уже существует')
            } else {
                alert(err)
            }
        });
    }
}

function removeCity(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8030/dreamjob/cities.do',
        data: {
            "id": id, "name": "name",
            "remove": true
        },
        dataType: 'json',
        err: 'error'
    }).done(function (city) {
        removeRow(city.id)
    }).fail(function (err) {
        alert(err);
    });
}

function removeRow(id) {
    let elem = document.getElementById('row' + id)
    elem.remove()
}

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