(function (angular) {

    angular.module('springMvcStarter', [
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

            url:  "/admin/upload/"

        })

        .constant('urlUploaderParams', {

            url:  "/admin/upload-by-url/"

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

                resource = $resource('/admin/user/:id/', {id:'@id'}, {
                    roles: {url: '/admin/user/roles/', method:'GET', isArray: true}
                });

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
 * Контроллер добавления пользователя
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("UserAddCtrl", [
            "$scope",
            "userService",
            "uploaderParams",
            "$state",
            UserAddCtrl
        ]);

    function UserAddCtrl($scope, userService, uploaderParams, $state){

        var Resource = userService.getResource();

        $scope.roles = Resource.roles();

        $scope.model = new Resource({
            active: true,
            filePaths: {},
            filesToRemove: [],
            role: 'ROLE_ADMIN'
        });

        $scope.uploaderParams = uploaderParams;

        $scope.save = function(form) {

            var success = function() {
                $state.go('users');
                $scope.gridWrapper.loadData();
            }

            var error = function(res) {

                $scope.result = false;

                $scope.errors = res.data;

            }

            $scope.model.$save(success, error);

        }

        $scope.update = false;

    }
    
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

/**
 * Контроллер изменения пользователя
 */
(function (angular, _) {

    angular.module('springMvcStarter')
        .controller("UserUpdateCtrl", [
            "$scope",
            "userService",
            "uploaderParams",
            "$state",
            "$stateParams",
            UserUpdateCtrl
        ]);

    function UserUpdateCtrl($scope, userService, uploaderParams, $state, $stateParams){

        var Resource = userService.getResource();

        $scope.roles = Resource.roles();

        $scope.model = Resource.get({id: $stateParams.id});

        $scope.uploaderParams = uploaderParams;

        $scope.save = function(form) {

            var success = function() {
                $state.go('users');
                $scope.gridWrapper.loadData();
            }

            var error = function(res) {

                $scope.result = false;

                $scope.errors = res.data;

            }

            $scope.model.$save(success, error);

        }

        $scope.update = true;

    }
    
})(angular, _);

(function(angular){

    /**
     * Преобразует пустую строку и undefined в null при биндинге к свойству модели
     */
    angular.module('springMvcStarter')
        .directive('emptyToNull', function () {
            return {
                restrict: 'A',
                require: 'ngModel',
                priority: 10,
                link: function (scope, elem, attrs, ctrl) {
                    ctrl.$parsers.push(function(viewValue) {
                        if(viewValue === "" || viewValue === undefined) {
                            return null;
                        }
                        return viewValue;
                    });
                }
            };
        });

})(angular);
/**
 * Директива для загрузки файлов. Использует компонент FileUploader.
 *
 * @link https://github.com/nervgh/angular-file-upload
 *
 * Пример использования:
 *
 * <div file-upload files-attr="filePaths" file-descriptions-attr="fileDescriptions" remove-files-attr="filesToRemove" uploader-params="uploaderParams" model="model"></div>
 *
 * files-attr - имя атрибута модели, хранящего карту имен загруженных файлов
 * file-descriptions-attr - имя атрибута модели хранящего описания файлов, не обязательный
 * remove-files-attr - имя атрибута модели, хранящего массив имен файлов подлежащих удалению
 * uploader-params - параметры компонента FileUploader
 * model - модель
 *
 */

(function (angular, _) {

   angular.module('springMvcStarter')
       .directive('fileUpload', ["FileUploader", "$http", "userService", FileUpload]);


   function FileUpload(FileUploader, $http, userService) {

       var templateUrl = '/resources/build/templates/fileUploadDirective.html';

       return {

           restrict: 'AE',

           scope: {
               model: "=",
               uploaderParams: "=",
               urlUploaderParams: "="

           },

           controllerAs: 'ctrl',

           controller: function($scope, $element, $attrs) {

               var required = ['filesAttr', 'removeFilesAttr', 'uploaderParams', 'model'];

               for( var attr in required) {

                   if(!$attrs[required[attr]])
                       throw new Error("Attribute "+attr+" can't be empty");

               }

               $scope.filesAttr = $attrs.filesAttr;

               if(!$scope.model[$attrs.filesAttr])
                   $scope.model[$attrs.filesAttr] = {};

               if(!$scope.model[$attrs.removeFilesAttr])
                   $scope.model[$attrs.removeFilesAttr] = [];


               $scope.$watch('model.'+$attrs.filesAttr, function(newVal){

                   if(newVal) {

                       var keys = _.keys(newVal);

                       keys =  _.reduce(keys, function(arr, num){
                           arr.push(parseInt(num));
                           return arr;
                       }, []);

                       keys.sort();

                       $scope.uploadedFileKeys = keys;

                   }

               }, true);

               // Если передан атрибут для хранения подписей к файлам

               if($attrs.fileDescriptionsAttr) {

                   $scope.descriptions = {};

                   $scope.$watch("model."+$attrs.fileDescriptionsAttr, function(val){

                       if(val === undefined)
                            return;

                       if(!val || val.length !=  $scope.model[$attrs.filesAttr].length) {

                           var prevValue =  $scope.model[$attrs.fileDescriptionsAttr];

                           $scope.model[$attrs.fileDescriptionsAttr] = {};

                           for(var i in $scope.model[$attrs.filesAttr]) {

                               $scope.model[$attrs.fileDescriptionsAttr][i] = (prevValue && prevValue[i])?prevValue[i]:"";
                           }


                       }

                       $scope.descriptions = $scope.model[$attrs.fileDescriptionsAttr];


                   });

               }

               this.uploader = new FileUploader($scope.uploaderParams);

               var self = this;

               this.uploader.onSuccessItem = function(item, response, status, headers){

                   completeUpload(response);

                   self.uploader.removeFromQueue(item);

               };

               this.uploader.onErrorItem = function(file, response, status, headers) {
                   console.info('onErrorItem', file, response, status, headers);
               };

               this.uploader.onBeforeUploadItem = function (fileItem) {

                   if(userService.isAuth()) {
                       fileItem.headers = {
                           'X-AUTH-TOKEN': userService.getUser().token
                       };
                   }

               };

               this.deleteFile = function(model, key) {

                   var name = model[$attrs.filesAttr][key];

                   // Если передан атрибут для хранения подписей к файлам
                   if($attrs.fileDescriptionsAttr) {
                       delete model[$attrs.fileDescriptionsAttr][key];
                   }

                   delete model[$attrs.filesAttr][key];

                   model[$attrs.removeFilesAttr].push(name);

               }

               this.baseName = function(path) {

                   return path.slice(path.lastIndexOf("/")+1);

               }

               this.getMaxKey = function(obj) {

                   var keys = _.keys(obj);

                   var max = keys.length>0?_.max(keys, function(num) {
                       return parseInt(num);
                   }):0;

                   return parseInt(max);

               }

               // Загрузка файлов по url

               $scope.urlUploaderProcess = false;

               this.uploadByUrl = function(url) {

                   if($scope.urlUploaderParams && url && !$scope.urlUploaderProcess) {

                       $scope.urlUploaderProcess = true;

                       var promise = $http.post($scope.urlUploaderParams.url, '', {
                           params:{url: encodeURI(url)},
                           headers: {
                               "Content-Type": "text/plain",
                               "Accept": "*/*"
                           }
                       });

                       promise.then(function(){
                           $scope.urlUploaderProcess = false;
                       });

                       promise.success(function(response){

                           completeUpload(response);

                           $scope.fileUrl = null;

                       });

                       promise.error(function(){
                           alert('Upload error!');
                       });

                   }

               }


               function completeUpload(response) {

                   if(response) {
                       var key = self.getMaxKey($scope.model[$attrs.filesAttr]) + 1;

                       // Если передан атрибут для хранения подписей к файлам
                       if ($attrs.fileDescriptionsAttr) {
                           $scope.model[$attrs.fileDescriptionsAttr][key] = "";
                       }

                       $scope.model[$attrs.filesAttr][key] = response;

                   }

               }


           },

           templateUrl: templateUrl

       }


   }

})(angular, _);
/*!
 * angular-input-match
 * Checks if one input matches another
 * @version v1.3.0
 * @link https://github.com/TheSharpieOne/angular-input-match
 * @license MIT License, http://www.opensource.org/licenses/MIT
 */
(function(window, angular, undefined){'use strict';

angular.module('springMvcStarter').directive('match', match);

function match ($parse) {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function(scope, elem, attrs, ctrl) {
            if(!ctrl) {
                if(console && console.warn){
                    console.warn('Match validation requires ngModel to be on the element');
                }
                return;
            }

            var matchGetter = $parse(attrs.match);
            var modelSetter = $parse(attrs.ngModel).assign;

            scope.$watch(getMatchValue, function(){
                modelSetter(scope, parser(ctrl.$viewValue));
            });

            ctrl.$parsers.unshift(parser);
            ctrl.$formatters.unshift(formatter);

            function parser(viewValue){

                var matchValue = getMatchValue();

                if(isEmpty(matchValue) && isEmpty(viewValue) || viewValue === matchValue){
                    ctrl.$setValidity('match', true);
                    return (viewValue === "" || viewValue === undefined)?null:viewValue;
                } else {
                    ctrl.$setValidity('match', false);
                    return null;
                }
            }

            function formatter(modelValue){
                return modelValue === null? ctrl.$isEmpty(ctrl.$viewValue)? null : ctrl.$viewValue : modelValue;
            }

            function getMatchValue(){
                var match = matchGetter(scope);
                if(angular.isObject(match) && match.hasOwnProperty('$viewValue')){
                    match = match.$viewValue;
                }
                return match;
            }

            function isEmpty(value) {

                return value===null || value === undefined || value === "" || value === false;

            }

        }
    };
}
match.$inject = ["$parse"];
})(window, window.angular);
/**
 * Директива отображения превью изображений
 * 
 * Пример использования:
 * 
 * <div ng-thumb="{ file: file, width: 100 }"></div>
 *  
 *  где  file - File|string, объект файл, или url адрес
 */

(function (angular) {

    angular.module('springMvcStarter')
        .directive('ngThumb', ['$window', NgThumb]);


    function NgThumb($window) {
        
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function (item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function(type) {
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            },
            isImageFile: function (file) {
                var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return this.isImage(type);
            },
            isImageUrl: function (file) {
                var type = file.split('.').pop().toLowerCase() + '|';
                return this.isImage(type);
            }
        };
        
        var draw = function(canvas, img, params) {

            var width = params.width || img.width / img.height * params.height;
            var height = params.height || img.height / img.width * params.width;
            canvas.attr({ width: width, height: height });
            canvas[0].getContext('2d').drawImage(img, 0, 0, width, height);
            
        }

        return {
            restrict: 'A',
            template: '<canvas ng-show="visible" />',
            link: function (scope, element, attributes) {
                
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                var canvas = element.find('canvas');

                if(typeof(params.file) == 'string') {

                    if (!helper.isImageUrl(params.file)) return;

                    scope.visible = true;
                    
                    var img = new Image();
                    img.src = params.file;
                    img.onload = onLoadImage;
                    
                } else {

                    if (!helper.isFile(params.file)) return;
                    if (!helper.isImageFile(params.file)) return;

                    scope.visible = true;
                    
                    var reader = new FileReader();
                    reader.onload = onLoadFile;
                    reader.readAsDataURL(params.file);

                }

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    draw(canvas, this, params)
                }
                
            }
        };
    }

})(angular);
(function(angular, _){

	/**
	 * Директива для отображения превью
	 */
	angular.module('springMvcStarter')
		.directive('previewSrc', [function () {
			return {
				restrict: 'A',
				scope: {
					'previewSrc': '='
				},

				link: function ($scope, elem, attrs) {


					$scope.$watch('previewSrc', function(newVal) {

						if(!newVal) {
							elem.hide();
							return;
						}

						var keys = _.keys(newVal);

						if(keys.length > 0) {

							keys =  _.reduce(keys, function(arr, num){
								arr.push(parseInt(num));
								return arr;
							}, []);

							keys.sort();

							var src = $scope.previewSrc[keys[0]];

							elem.attr('src', src);

							elem.show();

						} else {

							elem.attr('src', '');

							elem.hide();

						}


					}, true);

				}
			};
		}]);

})(angular, _);
(function(angular){

    /**
     * Преобразует строку в объект даты
     */
    angular.module('springMvcStarter')
        .directive("stringToDate", StringToDate);

    function StringToDate() {

        return {
            restrict: 'A',
            require: 'ngModel',
            priority: 0,
            link: function ($scope, element, attrs, ngModel) {

                ngModel.$formatters.unshift(function (value) {
                    return angular.isString(value) ? new Date(value) : value;
                });

            }
        };

    }

})(angular);
(function(angular){

    angular.module('springMvcStarter')
        .config(["$translateProvider", function ($translateProvider) {

            $translateProvider.translations('ru', {

                'Users': 'Пользователи',
                'Login error': 'Ошибка входа',
                'Username': 'Имя пользователя',
                'Password': 'Пароль',
                'Login': 'Вход',
                'Not found': '404 страница не найдена',
                'Add': 'Создать',
                'Save': 'Сохранить',
                'Cancel': 'Отменить',
                'Error saving': 'Ошибка сохранения',
                'Active': 'Активность',
                'Fio': 'ФИО',
                'Confirm password': 'Подтверждение пароля',
                'Avatar': 'Аватар',
                'Phone': 'Телефон',
                'Text': 'Текс',
                'Email': 'Электронный адрес',
                'Role': 'Роль',
                'Actions': 'Действия',
                'Field is required': 'Поле обязательно к заполнению',
                'Invalid field value': 'Неверное значение поля',
                'Filed to short': 'Значение слишком короткое',
                'Field to long': 'Значение слишком длинное',
                'Passwords does not match': 'Пароли не совпадают',
                'Email incorrect': 'Элекстронный адрес введен неверно',
                'Uploaded files': 'Загруженные файла',
                'Upload queue': 'Очередь загрузки',
                'Upload by url': 'Загрузить по URL',
                'Upload': 'Загрузить',
                'Name': 'Имя',
                'Status': 'Статус',
                'Info': 'Инфо',
                'Progress': 'Прогресс',
                'Remove': 'Удалить',
                'Confirm remove?': 'Подтвердить удаление?'

            });

            $translateProvider.preferredLanguage('ru');

            $translateProvider.useSanitizeValueStrategy(null);

        }]);

})(angular);