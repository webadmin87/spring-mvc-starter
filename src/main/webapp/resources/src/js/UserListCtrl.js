/**
 * Контроллер списка пользователей
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("UserListCtrl", [
            "$scope",
            "userService",
            "gridService",
            UserListCtrl
        ]);

    function UserListCtrl($scope, userService, gridService){

        var Resource = userService.getResource();

        var Wrapper = gridService.getWrapper($scope);

        $scope.gridWrapper = new Wrapper (Resource, { columnDefs: [
            {name: 'id'},
            {name: 'username'},
            {name: 'email'}
        ]});

        $scope.gridWrapper.loadData();

    }
    
})(angular, _);
