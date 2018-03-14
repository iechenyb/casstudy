(function mynav() {
  var navController=function($scope,$rootScope,$state,$http,$location){
   /* $http.get(basePath+"data/menu/fixMenu.json").success(
        function(data){
          $scope.menus= data;
        }
    );*/
  };
  var url = basePath+'component/nav/index.html';
  var nav = {
    restrict: 'E',
    templateUrl: url,
    bindings:{links:'='},
    controllerAs:"mynav",
    controller:navController
  };
  headerModule = angular.module('mynav', ['ui.router'])
    .component('mynav', nav);
})();