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

        var defaultPageSize = 1

        $scope.gridOptions = {
            paginationPageSizes: [defaultPageSize, 2, 3],
            useExternalPagination: true,
            paginationPageSize: defaultPageSize,
            columnDefs: [
                { name: 'username' },
                { name: 'email' }
            ]
        };

        $scope.gridOptions.onRegisterApi = function (gridApi) {

            $scope.gridApi = gridApi;

            $scope.gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {

                loadData(newPage, pageSize);
            });

        }

        loadData(1, defaultPageSize);

        function loadData(page, pageSize) {

            $scope.page = Resource.page({page: page, pageSize: pageSize}, function(page) {

                $scope.gridOptions.data = page.content;
                $scope.gridOptions.totalItems = page.totalElements;


            });

        }

    }
    
})(angular, _);
