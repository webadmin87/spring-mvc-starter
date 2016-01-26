(function(angular){

    angular.module('springMvcStarter')
        .config(["$translateProvider", function ($translateProvider) {

            $translateProvider.translations('ru', {

                'Users': 'Пользователи'

            });

            $translateProvider.preferredLanguage('ru');

            $translateProvider.useSanitizeValueStrategy(null);

        }]);

})(angular);