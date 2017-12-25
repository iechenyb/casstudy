var expander={
  restrict : 'EA',
  replace : true,
  transclude : true,
  scope : {
    title : '=expanderTitle'
  },
  template : '<div>'
  + '<div class="title" ng-click="toggle()">{{title}}</div>'
  + '<div class="body" ng-show="showMe" ng-transclude></div>'
  + '</div>',
  link : function(scope, element, attrs) {
    scope.showMe = false;
    scope.toggle = function toggle() {
      scope.showMe = !scope.showMe;
    }
  }
};
var accordion={
    restrict : 'EA',
    replace : true,
    transclude : true,
    template : '<div ng-transclude></div>',
    controller : function() {
  var expanders = [];
  this.gotOpened = function(selectedExpander) {
    angular.forEach(expanders, function(expander) {
      if (selectedExpander != expander) {
        expander.showMe = false;
      }
    });
  }
  this.addExpander = function(expander) {
    expanders.push(expander);
  }
}
};
var expander1 = {
    restrict : 'EA',
    replace : true,
    transclude : true,
    require : '^?accordion',
    scope : {
        title : '=expanderTitle'
    },
    template : '<div>'
+ '<div class="title" ng-click="toggle()">{{title}}</div>'
+ '<div class="body" ng-show="showMe" ng-transclude></div>'
+ '</div>',
    link : function(scope, element, attrs, accordionController) {
  scope.showMe = false;
  accordionController.addExpander(scope);
  scope.toggle = function toggle() {
    scope.showMe = !scope.showMe;
    accordionController.gotOpened(scope);
  }
}
};