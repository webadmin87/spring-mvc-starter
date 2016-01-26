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
            {name: 'email'},
            { name: 'Actions',
                cellTemplate:'<div class="ui-grid-cell-contents">' +
                '<a ui-sref="users.update({id: row.entity.id})">Edit</a> ' +
                '<a href="" ng-click="grid.appScope.gridWrapper.remove(row.entity)">Remove</a>' +
                '</div>' }
        ]});

        $scope.gridWrapper.loadData();

    }
    
})(angular, _);
