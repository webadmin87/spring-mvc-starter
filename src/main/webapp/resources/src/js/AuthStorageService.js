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
