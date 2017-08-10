
var URL = "http://192.168.0.5/http/";
var requestData = {
    name: 'Nagaraj',
    ID: 3123
};

function getData() {

    $.ajax ({
        url : URL + 'getJson.php',
        type: 'GET',
        accept: 'application/json',
        success: function (data) {
            $('#get-response').text(JSON.stringify(data));
        }
    });

}

function postData() {

    $.ajax ({
        url : URL + 'postJson.php',
        type: 'GET',
        accept: 'application/json',
        contentType: 'application/json',
        data: JSON.stringify(requestData)
        success: function (data) {
            $('#post-response').text(JSON.stringify(data));
        }
    });

}