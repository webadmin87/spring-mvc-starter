(function(angular, _){

	/**
	 * Директива для отображения превью
	 */
	angular.module(MVC_STARTER_APP)
		.directive('previewSrc', [function () {
			return {
				restrict: 'A',
				scope: {
					'previewSrc': '='
				},

				link: function ($scope, elem, attrs) {


					$scope.$watch('previewSrc', function(newVal) {

						if(!newVal) {
							elem.hide();
							return;
						}

						var keys = _.keys(newVal);

						if(keys.length > 0) {

							keys =  _.reduce(keys, function(arr, num){
								arr.push(parseInt(num));
								return arr;
							}, []);

							keys.sort();

							var src = $scope.previewSrc[keys[0]];

							elem.attr('src', src);

							elem.show();

						} else {

							elem.attr('src', '');

							elem.hide();

						}


					}, true);

				}
			};
		}]);

})(angular, _);