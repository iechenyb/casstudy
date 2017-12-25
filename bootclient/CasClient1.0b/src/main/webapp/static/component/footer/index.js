(function myfooter() {
  var footer = {
    restrict: 'E',
    templateUrl: basePath+'component/footer/index.html',
  };
  footerModule = angular.module('myfooter', ['ui.router'])
    .component('myfooter', footer);
})();