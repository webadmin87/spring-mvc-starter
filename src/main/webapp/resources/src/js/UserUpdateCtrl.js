/**
 * Контроллер изменения пользователя
 */
(function (angular, _) {

    angular.module(MVC_STARTER_APP)
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

        $scope.roles = Resource.roles();

        $scope.model = Resource.get({id: $stateParams.id});

        $scope.uploaderParams = uploaderParams;

        $scope.save = function(form) {

            var success = function() {
                $state.go('users');
                $scope.gridWrapper.loadData();
            }

            var error = function(res) {

                $scope.result = false;

                $scope.errors = res.data;

            }

            $scope.model.$save(success, error);

        }

        $scope.update = true;

    }
    
})(angular, _);
