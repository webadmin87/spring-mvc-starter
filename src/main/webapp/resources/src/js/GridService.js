/**
 * Сервис фабрика для обертки грида
 */
(function (angular, $, _) {

    angular.module(MVC_STARTER_APP)
        .service("gridService", [
            "$translate",
            "$timeout",
            GridService
        ]);

    function GridService($translate, $timeout) {

        this.getWrapper = function ($scope) {

            var GridWrapper = function(Resource, options, requestParams) {

                var self = this;

                this.filter = null;

                this.filterTimeout = null;

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

                    // Сортировка
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

                    // Пагинация
                    gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {

                        self.paginationOptions.page = newPage;
                        self.paginationOptions.pageSize = pageSize;

                        self.loadData();

                    });

                    // Фильтрация
                    if(self.gridOptions.enableFiltering && self.gridOptions.useExternalFiltering) {

                        gridApi.core.on.filterChanged($scope, function () {

                            if(self.filterTimeout) {
                                $timeout.cancel(self.filterTimeout);
                                self.filterTimeout = null;
                            }

                            var grid = this.grid;

                            var filter = {}

                            for (var k in grid.columns) {

                                var col = grid.columns[k];

                                if (col.enableFiltering && col.filters[0].term) {
                                    filter[col.field] = col.filters[0].term;
                                }

                            }

                            if (!angular.equals(filter, {}))
                                self.filter = filter;
                            else
                                self.filter = null;


                            self.filterTimeout = $timeout(function () {
                                self.loadData();
                            }, 500);

                        });

                    }

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

                if(this.filter)
                    params.filter = angular.toJson(this.filter);

                return angular.extend(params, this.requestParams);

            }

            return GridWrapper;

        }

    }

})(angular, jQuery, _);
