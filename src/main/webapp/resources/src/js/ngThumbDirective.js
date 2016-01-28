/**
 * Директива отображения превью изображений
 * 
 * Пример использования:
 * 
 * <div ng-thumb="{ file: file, width: 100 }"></div>
 *  
 *  где  file - File|string, объект файл, или url адрес
 */

(function (angular) {

    angular.module(MVC_STARTER_APP)
        .directive('ngThumb', ['$window', NgThumb]);


    function NgThumb($window) {
        
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function (item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function(type) {
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            },
            isImageFile: function (file) {
                var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return this.isImage(type);
            },
            isImageUrl: function (file) {
                var type = file.split('.').pop().toLowerCase() + '|';
                return this.isImage(type);
            }
        };
        
        var draw = function(canvas, img, params) {

            var width = params.width || img.width / img.height * params.height;
            var height = params.height || img.height / img.width * params.width;
            canvas.attr({ width: width, height: height });
            canvas[0].getContext('2d').drawImage(img, 0, 0, width, height);
            
        }

        return {
            restrict: 'A',
            template: '<canvas ng-show="visible" />',
            link: function (scope, element, attributes) {
                
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                var canvas = element.find('canvas');

                if(typeof(params.file) == 'string') {

                    if (!helper.isImageUrl(params.file)) return;

                    scope.visible = true;
                    
                    var img = new Image();
                    img.src = params.file;
                    img.onload = onLoadImage;
                    
                } else {

                    if (!helper.isFile(params.file)) return;
                    if (!helper.isImageFile(params.file)) return;

                    scope.visible = true;
                    
                    var reader = new FileReader();
                    reader.onload = onLoadFile;
                    reader.readAsDataURL(params.file);

                }

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    draw(canvas, this, params)
                }
                
            }
        };
    }

})(angular);