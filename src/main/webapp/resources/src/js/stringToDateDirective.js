(function(angular){

    /**
     * Преобразует строку в объект даты
     */
    angular.module(MVC_STARTER_APP)
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