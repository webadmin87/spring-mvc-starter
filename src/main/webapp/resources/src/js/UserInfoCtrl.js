/**
 * Контроллер отображающий сраницу об авторизованном пользователе
 */
(function (angular, _) {

    angular.module(MVC_STARTER_APP)
        .controller("UserInfoCtrl", [
            "$scope",
            "userService",
            "$state",
            UserInfoCtrl
        ]);

    function UserInfoCtrl($scope, userService, $state) {

        $scope.getUser = function() {
            return userService.getUser();
        }

        $scope.logout = function() {
            userService.logout();
            $state.go("main");
        }

        $scope.isAuth = function() {
            return userService.isAuth();
        }

    }
    
})(angular, _);
