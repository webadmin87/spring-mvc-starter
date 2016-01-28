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