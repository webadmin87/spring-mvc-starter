(function (angular, window) {

    window.MVC_STARTER_APP = 'springMvcStarter';

    angular.module(MVC_STARTER_APP, [
        'ngResource',
        'ui.router',
        'ui.bootstrap',
        'ui.grid',
        'ui.grid.pagination',
        'textAngular',
        'angularFileUpload',
        'pascalprecht.translate',
        'ngMessages'
    ])

        // Настройки url

        .constant('urlMapping', {

        })


        // Настройки загрузки файлов

        .constant('uploaderParams', {

            url:  "admin/upload/"

        })

        .constant('urlUploaderParams', {

            url:  "admin/upload-by-url/"

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

            $httpProvider.defaults.cache = false;

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
                templateUrl: 'resources/views/users/users.html',
                controller: 'UserListCtrl',
                data: {
                    roles: ['ROLE_ADMIN']
                }
            });

            $stateProvider.state('users.add', {
                url: '/add',
                templateUrl: 'resources/views/users/add.html',
                controller: 'UserAddCtrl',
                data: {
                    roles: ['ROLE_ADMIN']
                }
            });

            $stateProvider.state('users.update', {
                url: '/update/:id',
                templateUrl: 'resources/views/users/update.html',
                controller: 'UserUpdateCtrl',
                data: {
                    roles: ['ROLE_ADMIN']
                }
            });




        }]);

})(angular, window);