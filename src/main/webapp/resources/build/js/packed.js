(function (angular) {

    angular.module('springMvcStarter', [
        'ui.router',
        'ui.bootstrap'
    ])
        .constant('urlMapping', {

        })

        .config(['$provide', '$httpProvider', function ($provide, $httpProvider) {

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            $httpProvider.defaults.headers.common['Accept'] = '*';

            $httpProvider.defaults.cache = true;

            // Перехват http. Обработка ошибо и аутентификация

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
                url: '',
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
(function (angular, $, _) {

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
            UserService
        ]);

    function UserService($http, $q, authStorage){

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
        }

        this.getUser = function() {
            return authStorage.getUser();
        }

        this.isAuth = function() {
            return authStorage.getUser() != null;
        }

        this.getAll = function() {

            return $http.get('/admin/users/');

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

        userService.getAll().success(function(data){

            $scope.models = data;

        });

    }
    
})(angular, _);
