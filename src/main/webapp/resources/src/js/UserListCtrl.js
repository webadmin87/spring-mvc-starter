/**
 * Контроллер списка пользователей
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("UserListCtrl", [
            "$scope",
            "userService",
            "gridService",
            "$translate",
            UserListCtrl
        ]);

    function UserListCtrl($scope, userService, gridService, $translate){

        var Resource = userService.getResource();

        var Wrapper = gridService.getWrapper($scope);

        $translate(['Avatar', 'Username', 'Email', 'Actions']).then(function(translations){

            $scope.gridWrapper = new Wrapper (Resource, { columnDefs: [
                {name: 'previews', enableSorting: false, displayName: translations.Avatar,
                    cellTemplate:'<div class="ui-grid-cell-contents">' +
                '<img preview-src="row.entity.previews" height="60" alt="" />' +
                '</div>'},
                {name: 'id'},
                {name: 'username', displayName: translations.Username},
                {name: 'email', displayName: translations.Email},
                { name: 'Actions', enableSorting: false, displayName: translations.Actions,
                    cellTemplate:'<div class="ui-grid-cell-contents">' +
                    '<a ui-sref="users.update({id: row.entity.id})" class="glyphicon glyphicon-pencil"></a> ' +
                    '<a href="" ng-click="grid.appScope.gridWrapper.remove(row.entity)" class="glyphicon glyphicon-trash"></a>' +
                    '</div>'
                }
            ],
                rowHeight:60
            });

            $scope.gridWrapper.loadData();

        });

    }
    
})(angular, _);
