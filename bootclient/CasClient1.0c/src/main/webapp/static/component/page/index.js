(function mypage() {
  var pager = {
    restrict: 'E',
    templateUrl: basePath+'component/page/index.html',
  };
  footerModule = angular.module('mypage', ['ui.router'])
    .component('mypage', pager);
})();