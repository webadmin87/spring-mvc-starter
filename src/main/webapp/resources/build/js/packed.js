(function (angular) {

    angular.module('springMvcStarter', [
        'ngResource',
        'ui.router',
        'ui.bootstrap',
        'ui.grid',
        'ui.grid.pagination'
    ])
        .constant('urlMapping', {

        })

        .config(['$resourceProvider', function($resourceProvider) {
            // Don't strip trailing slashes from calculated URLs
            $resourceProvider.defaults.stripTrailingSlashes = false;
        }])

        .config(['$provide', '$httpProvider', function ($provide, $httpProvider) {

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            $httpProvider.defaults.headers.common['Accept'] = '*';

            $httpProvider.defaults.cache = true;

            // Перехват http. Обработка ошибок и аутентификация

            $provide.factory('AppHttpInterceptor', ["$q", "$location", "authStorage", function ($q, $location, authStorage) {
                return {

                    request: function(config) {
                        var user = authStorage.getUser();
                        if (user) {
                            config.headers['X-AUTH-TOKEN'] = user.token;
                        }
                        return config;
                    },

                    // Ошибка ответа
                    responseError: function (rejection) {
                        if (rejection.status == 401) {
                            authStorage.reset();
                            $location.path("/login");
                        } else if (rejection.status == 404) {
                            $location.path("/not-found");
                        }
                        return $q.reject(rejection);
                    }
                };
            }]);

            // Добавляем перехватчик
            $httpProvider.interceptors.push('AppHttpInterceptor');

        }])

        .config(['$sceProvider', function ($sceProvider) {

            $sceProvider.enabled(false);

        }])

        .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise("/");

            // Главная страница

            $stateProvider.state('main', {
                url: '/',
                templateUrl: 'resources/views/index.html',
                controller: 'MainCtrl'
            });

            // Логин

            $stateProvider.state('login', {
                url: '/login',
                templateUrl: 'resources/views/login.html',
                controller: 'LoginCtrl'
            });

            // Страница не найдена

            $stateProvider.state('notFound', {
                url: '/not-found',
                templateUrl: 'resources/views/not-found.html',
                controller: 'NotFoundCtrl'
            });

            // Пользователи

            $stateProvider.state('users', {
                url: '/users',
                templateUrl: 'resources/views/users.html',
                controller: 'UserListCtrl'
            });



        }]);

})(angular);
/**
 * Сервис для хранения информации об аутентификации пользователя
 */
(function (angular) {

    angular.module('springMvcStarter')
        .service("authStorage", [AuthStorage]);

    function AuthStorage(){

        var STORAGE_KEY = "AUTH_STORAGE";

        this.setUser = function(u) {
            localStorage.setItem(STORAGE_KEY, angular.toJson(u));
        }

        this.getUser = function() {
            return angular.fromJson(localStorage.getItem(STORAGE_KEY));
        }

        this.reset = function() {
            localStorage.removeItem(STORAGE_KEY);
        }

    }

})(angular);

/**
 * Сервис пользователей
 */
(function (angular, $, _) {

    angular.module('springMvcStarter')
        .service("userService", [
            "$http",
            "$q",
            "authStorage",
            "$resource",
            "$cacheFactory",
            UserService
        ]);

    function UserService($http, $q, authStorage, $resource, $cacheFactory){

        this.login = function(model) {

            var deferred = $q.defer();

            $http.post('/login', model, {transformRequest: transformRequest, headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }})
                .success(loginSuccess)
                .error(loginError);


            function loginSuccess(data, status, headers) {

                if(status == 200) {
                    authStorage.setUser(data);
                    deferred.resolve();
                } else {
                    deferred.reject();
                }

            }

            function loginError() {
                deferred.reject();
            }

            function transformRequest(data, headersGetter) {

                return $.param(data);

            }

            return deferred.promise;

        }

        this.logout = function() {
            authStorage.reset();
            $cacheFactory.get('$http').removeAll();
        }

        this.getUser = function() {
            return authStorage.getUser();
        }

        this.isAuth = function() {
            return authStorage.getUser() != null;
        }

        var resource = null;

        this.getResource = function() {


            if(resource == null) {

                resource = $resource('/admin/user/:id', {id:'@id'}, {
                    page: {method: 'GET', isArray: false}
                });

            }

            return resource;

        }


    }
    
})(angular, jQuery, _);

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

/**
 * Контроллер главной страницы
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("MainCtrl", [
            "$scope",
            MainCtrl
        ]);

    function MainCtrl($scope){    }
    
})(angular, _);

/**
 * Контроллер страница не найдена
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("NotFoundCtrl", [
            "$scope",
            NotFoundCtrl
        ]);

    function NotFoundCtrl($scope){    }
    
})(angular, _);

/**
 * Контроллер отображающий сраницу об авторизованном пользователе
 */
(function (angular, _) {

    angular.module('springMvcStarter')
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
