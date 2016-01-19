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

            // Перехват ошибок http

            $provide.factory('AppHttpInterceptor', ["$q", "$location", function ($q, $location) {
                return {

                    // Ошибка ответа
                    responseError: function (rejection) {
                        if (rejection.status == 401) {
                            $location.path("/login");;
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



        }]);

})(angular);
/**
 * Контроллер страницы логина
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("LoginCtrl", [
            "$scope",
            LoginCtrl
        ]);

    function LoginCtrl($scope){    }
    
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
