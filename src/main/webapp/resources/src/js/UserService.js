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
