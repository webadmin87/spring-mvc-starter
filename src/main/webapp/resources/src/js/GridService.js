/**
 * Сервис фабрика для обертки грида
 */
(function (angular, $, _) {

    angular.module(MVC_STARTER_APP)
        .service("gridService", [
            "$translate",
            GridService
        ]);

    function GridService($translate) {

        this.getWrapper = function ($scope) {

            var GridWrapper = function(Resource, options, requestParams) {

                var self = this;

                this.defaultPageSize = 20;

                this.Resource = Resource;

                this.paginationOptions = {
                    page: 1,
                    pageSize: this.defaultPageSize,
                    sortDirection: null,
                    sortField: null
                };

                this.gridOptions = angular.extend({
                    paginationPageSizes: [this.defaultPageSize, 40, 80],
                    useExternalPagination: true,
                    paginationPageSize: this.defaultPageSize,
                    useExternalSorting: true
                }, options);

                this.requestParams = requestParams || {};

                this.gridOptions.onRegisterApi = function (gridApi) {

                    self.gridApi = gridApi;

                    gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                        if (sortColumns.length == 0) {
                            self.paginationOptions.sortField = null;
                            self.paginationOptions.sortDirection = null;
                        } else {
                            self.paginationOptions.sortField = sortColumns[0].field;
                            self.paginationOptions.sortDirection = sortColumns[0].sort.direction.toUpperCase();
                        }
                        self.loadData();
                    });

                    gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {

                        self.paginationOptions.page = newPage;
                        self.paginationOptions.pageSize = pageSize;

                        self.loadData();

                    });

                }

            }

            GridWrapper.prototype.loadData = function() {

                var self = this;

                return this.Resource.query(this.getRequestParams(), function(models, headers) {

                    self.gridOptions.data = models;
                    self.gridOptions.totalItems = headers('X-pagination-total-elements');

                });

            }

            GridWrapper.prototype.remove = function(model) {

                var self = this;

                $translate("Confirm remove?").then(function(mes){

                    if(confirm(mes)) {

                        model.$remove(function () {

                            self.loadData();

                        });

                    }

                });

            }

            GridWrapper.prototype.getRequestParams = function() {

                var params = {};

                for(var k in this.paginationOptions) {

                    if(this.paginationOptions[k]) {

                        params[k] = this.paginationOptions[k];

                    }

                }

                return angular.extend(params, this.requestParams);

            }

            return GridWrapper;

        }

    }

})(angular, jQuery, _);
