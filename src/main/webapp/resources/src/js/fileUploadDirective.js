/**
 * Директива для загрузки файлов. Использует компонент FileUploader.
 *
 * @link https://github.com/nervgh/angular-file-upload
 *
 * Пример использования:
 *
 * <div file-upload files-attr="filePaths" file-descriptions-attr="fileDescriptions" remove-files-attr="filesToRemove" uploader-params="uploaderParams" model="model"></div>
 *
 * files-attr - имя атрибута модели, хранящего карту имен загруженных файлов
 * file-descriptions-attr - имя атрибута модели хранящего описания файлов, не обязательный
 * remove-files-attr - имя атрибута модели, хранящего массив имен файлов подлежащих удалению
 * uploader-params - параметры компонента FileUploader
 * model - модель
 *
 */

(function (angular, _) {

   angular.module(MVC_STARTER_APP)
       .directive('fileUpload', ["FileUploader", "$http", "userService", FileUpload]);


   function FileUpload(FileUploader, $http, userService) {

       var templateUrl = 'resources/build/templates/fileUploadDirective.html';

       return {

           restrict: 'AE',

           scope: {
               model: "=",
               uploaderParams: "=",
               urlUploaderParams: "="

           },

           controllerAs: 'ctrl',

           controller: function($scope, $element, $attrs) {

               var required = ['filesAttr', 'removeFilesAttr', 'uploaderParams', 'model'];

               for( var attr in required) {

                   if(!$attrs[required[attr]])
                       throw new Error("Attribute "+attr+" can't be empty");

               }

               $scope.filesAttr = $attrs.filesAttr;

               if(!$scope.model[$attrs.filesAttr])
                   $scope.model[$attrs.filesAttr] = {};

               if(!$scope.model[$attrs.removeFilesAttr])
                   $scope.model[$attrs.removeFilesAttr] = [];


               $scope.$watch('model.'+$attrs.filesAttr, function(newVal){

                   if(newVal) {

                       var keys = _.keys(newVal);

                       keys =  _.reduce(keys, function(arr, num){
                           arr.push(parseInt(num));
                           return arr;
                       }, []);

                       keys.sort();

                       $scope.uploadedFileKeys = keys;

                   }

               }, true);

               // Если передан атрибут для хранения подписей к файлам

               if($attrs.fileDescriptionsAttr) {

                   $scope.descriptions = {};

                   $scope.$watch("model."+$attrs.fileDescriptionsAttr, function(val){

                       if(val === undefined)
                            return;

                       if(!val || val.length !=  $scope.model[$attrs.filesAttr].length) {

                           var prevValue =  $scope.model[$attrs.fileDescriptionsAttr];

                           $scope.model[$attrs.fileDescriptionsAttr] = {};

                           for(var i in $scope.model[$attrs.filesAttr]) {

                               $scope.model[$attrs.fileDescriptionsAttr][i] = (prevValue && prevValue[i])?prevValue[i]:"";
                           }


                       }

                       $scope.descriptions = $scope.model[$attrs.fileDescriptionsAttr];


                   });

               }

               this.uploader = new FileUploader($scope.uploaderParams);

               var self = this;

               this.uploader.onSuccessItem = function(item, response, status, headers){

                   completeUpload(response);

                   self.uploader.removeFromQueue(item);

               };

               this.uploader.onErrorItem = function(file, response, status, headers) {
                   console.info('onErrorItem', file, response, status, headers);
               };

               this.uploader.onBeforeUploadItem = function (fileItem) {

                   if(userService.isAuth()) {
                       fileItem.headers = {
                           'X-AUTH-TOKEN': userService.getUser().token
                       };
                   }

               };

               this.deleteFile = function(model, key) {

                   var name = model[$attrs.filesAttr][key];

                   // Если передан атрибут для хранения подписей к файлам
                   if($attrs.fileDescriptionsAttr) {
                       delete model[$attrs.fileDescriptionsAttr][key];
                   }

                   delete model[$attrs.filesAttr][key];

                   model[$attrs.removeFilesAttr].push(name);

               }

               this.baseName = function(path) {

                   return path.slice(path.lastIndexOf("/")+1);

               }

               this.getMaxKey = function(obj) {

                   var keys = _.keys(obj);

                   var max = keys.length>0?_.max(keys, function(num) {
                       return parseInt(num);
                   }):0;

                   return parseInt(max);

               }

               // Загрузка файлов по url

               $scope.urlUploaderProcess = false;

               this.uploadByUrl = function(url) {

                   if($scope.urlUploaderParams && url && !$scope.urlUploaderProcess) {

                       $scope.urlUploaderProcess = true;

                       var promise = $http.post($scope.urlUploaderParams.url, '', {
                           params:{url: encodeURI(url)},
                           headers: {
                               "Content-Type": "text/plain",
                               "Accept": "*/*"
                           }
                       });

                       promise.then(function(){
                           $scope.urlUploaderProcess = false;
                       });

                       promise.success(function(response){

                           completeUpload(response);

                           $scope.fileUrl = null;

                       });

                       promise.error(function(){
                           alert('Upload error!');
                       });

                   }

               }


               function completeUpload(response) {

                   if(response) {
                       var key = self.getMaxKey($scope.model[$attrs.filesAttr]) + 1;

                       // Если передан атрибут для хранения подписей к файлам
                       if ($attrs.fileDescriptionsAttr) {
                           $scope.model[$attrs.fileDescriptionsAttr][key] = "";
                       }

                       $scope.model[$attrs.filesAttr][key] = response;

                   }

               }


           },

           templateUrl: templateUrl

       }


   }

})(angular, _);