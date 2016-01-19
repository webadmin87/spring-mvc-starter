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
