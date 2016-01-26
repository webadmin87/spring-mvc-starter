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

            var error = function() {

                $scope.result = false;

            }

            $scope.model.$save(success, error);

        }

        $scope.update = false;

    }
    
})(angular, _);
