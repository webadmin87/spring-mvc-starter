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

        var defaultPageSize = 20;

        var paginationOptions = {
            page: 1,
            pageSize: defaultPageSize,
            sortDirection: null,
            sortField: null
        };

        $scope.gridOptions = {
            paginationPageSizes: [defaultPageSize, 40, 80],
            useExternalPagination: true,
            paginationPageSize: defaultPageSize,
            useExternalSorting: true,
            columnDefs: [
                { name: 'id' },
                { name: 'username' },
                { name: 'email' }
            ]
        };

        $scope.gridOptions.onRegisterApi = function (gridApi) {

            $scope.gridApi = gridApi;

            $scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
                if (sortColumns.length == 0) {
                    paginationOptions.sortField = null;
                    paginationOptions.sortDirection = null;
                } else {
                    paginationOptions.sortField = sortColumns[0].field;
                    paginationOptions.sortDirection = sortColumns[0].sort.direction.toUpperCase();
                }
                loadData();
            });

            $scope.gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {

                paginationOptions.page = newPage;
                paginationOptions.pageSize = pageSize;

                loadData();

            });

        }

        loadData();

        function getRequestParams() {

            var params = {};

            for(var k in paginationOptions) {

                if(paginationOptions[k]) {

                    params[k] = paginationOptions[k];

                }

            }

            return params;

        }

        function loadData() {

            $scope.page = Resource.page(getRequestParams(), function(page) {

                $scope.gridOptions.data = page.content;
                $scope.gridOptions.totalItems = page.totalElements;


            });

        }

    }
    
})(angular, _);
