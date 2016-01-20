/**
 * Контроллер списка пользователей
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("UserListCtrl", [
            "$scope",
            "userService",
            UserListCtrl
        ]);

    function UserListCtrl($scope, userService){

        var Resource = userService.getResource();

        $scope.models = Resource.query();

    }
    
})(angular, _);
