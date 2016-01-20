/**
 * Контроллер страницы логина
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("LoginCtrl", [
            "$scope",
            "userService",
            "$state",
            LoginCtrl
        ]);

    function LoginCtrl($scope, userService, $state) {

        userService.logout();

        $scope.error;

        $scope.model = {};

        $scope.login = function(model) {

            var promise = userService.login(model);

            promise.then(function() {

                $state.go("main");

            }, function(){

                $scope.error = true;

            });

        }

    }
    
})(angular, _);
