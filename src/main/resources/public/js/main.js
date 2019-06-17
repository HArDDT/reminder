var userId = "f8dc161a-94cf-4947-a37c-ee9fc74647a6";
var token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmOGRjMTYxYS05NGNmLTQ5NDctYTM3Yy1lZTlmYzc0NjQ3YTYiLCJleHAiOjE1NjA4NTgwMjV9.ln0R7Ri6-xz3qrDauB0Tnn7anZa8uQEFxwE-c8rrGJ8";

var app = angular.module('singleAppPage', []);

app.controller('MainController', function($scope, $http) {

    $scope.url = "html/login-form.html";
    $scope.url2 = "html/events-table.html";

    $http({
        method: "GET",
        url: "http://localhost:8080/protected/get_events/"+ userId,
        contentType: 'application/json',
        headers: {"X-Auth-Token":token}
    }).then(function(response) {
        $scope.list = response.data;
    }).then(function (response) {
        alert(response.status);
    });

        // $scope.addItem = function (text, price) {
        //     price = parseFloat(price); // преобразуем введенное значение к числу
        //     if(text != "" && !isNaN(price)) // если текст установлен и введено число, то добавляем
        //     {
        //         $scope.list.events.push({ purchase: text, price: price, done: false });
        //     }
        // }
    });
