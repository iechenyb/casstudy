(function myheader() {
  var headerController=function($scope,$rootScope,$state,$http,$location){
    $http.get(basePath+"data/menu/fixMenu.json").success(
        function(data){
          $scope.menus= data;
        }
    );
  };
  var url = basePath+'component/header/index.html';
  var header = {
    restrict: 'E',
    templateUrl: url,
    bindings:{links:'='},
    controllerAs:"myheader",
    controller:headerController
  };
  headerModule = angular.module('myheader', ['ui.router'])
    .component('myheader', header);
})();