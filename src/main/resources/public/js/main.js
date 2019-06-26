var app = angular.module('singleAppPage', []);
app.controller('MainController', function($scope, $http) {

    var userId = 1;
    var token = "";
    var authData = {
        email: "dimatihiy@ukr.net",
        password: 123
    };

    $scope.url = "html/login-form.html";
    $scope.url2 = "html/events-table.html";

    signUp($http, $scope);

    function signUp(){
        $http({
            method: "POST",
            url: "http://localhost:8080/sing-up",
            contentType: 'application/json',
            params : authData
        }).then(function(response) {
            token = response.data["X-Auth-Token"];
            getEvents(token);
        });
    }

    function getEvents(token) {
        $http({
            method: "GET",
            url: "http://localhost:8080/protected/events/"+ userId,
            contentType: 'application/json',
            headers: {"X-Auth-Token": token}
        }).then(function(response) {
            $scope.list = response.data;
        });
    }

});

