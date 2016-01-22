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

        // Проверка прав доступа

        .run(['$rootScope', 'userService', '$state', function($rootScope, userService, $state){

            $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {

                if($rootScope.stateChangeBypass) {

                    $rootScope.stateChangeBypass = false;

                    return;

                }

                // Есть ограничение по ролям

                if(toState.data && toState.data.roles) {

                    event.preventDefault();

                    if(userService.isAuth() && userService.hasRole(toState.data.roles)) {

                        $rootScope.stateChangeBypass = true;

                        $state.go(toState, toStateParams);

                    } else {

                        $rootScope.stateChangeBypass = false;

                        alert('Forbidden 403');

                    }

                }

            });

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
                controller: 'UserListCtrl',
                data: {
                    roles: ['ROLE_ADMIN']
                }
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
 * Сервис фабрика для обертки грида
 */
(function (angular, $, _) {

    angular.module('springMvcStarter')
        .service("gridService", [
            GridService
        ]);

    function GridService() {

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

                resource = $resource('/admin/user/:id', {id:'@id'});

            }

            return resource;

        }

        this.hasRole = function(roles) {

            var user = this.getUser();

            if(!user)
                return false;

            return roles.indexOf(user.role) > -1

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
