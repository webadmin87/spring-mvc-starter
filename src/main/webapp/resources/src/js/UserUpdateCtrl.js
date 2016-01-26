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

        $scope.model = Resource.get({id: $stateParams.id});

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

        $scope.update = true;

    }
    
})(angular, _);
