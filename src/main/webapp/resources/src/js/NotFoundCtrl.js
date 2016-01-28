/**
 * Контроллер страница не найдена
 */
(function (angular, _) {

    angular.module(MVC_STARTER_APP)
        .controller("NotFoundCtrl", [
            "$scope",
            NotFoundCtrl
        ]);

    function NotFoundCtrl($scope){    }
    
})(angular, _);
