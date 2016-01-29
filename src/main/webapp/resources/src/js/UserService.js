/**
 * Сервис пользователей
 */
(function (angular, $, _) {

    angular.module(MVC_STARTER_APP)
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

        this.setUser = function(u) {
            return authStorage.setUser(u);
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