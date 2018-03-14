/**
 * 对所有的请求的返回的数据格式做统一处理！
 * @param data
 * @returns {boolean}
 */
var jsonPD = function (data) {
  if (data.code == 0)return true;
  else alert(data.msg);
  return false;
};
var reEscapedHtml = /(&amp;|&lt;|&gt;|&quot;|&#39;)/g;
var htmlUnescapes = {
  '&amp;': '&',
  '&lt;': '<',
  '&gt;': '>',
  '&quot;': '"',
  '&#39;': "'"
};
function unescapeHtmlChar(match) {
  return htmlUnescapes[match];
}
function html_decode(string) {
  return string == null ? '' : String(string).replace(reEscapedHtml, unescapeHtmlChar);
}
//将hmtl内容直接转换成模板内容加载
angular.module('html', [], function($compileProvider) {
    $compileProvider.directive('compile', function($compile) {
        return function(scope, element, attrs) {
            scope.$watch(
                function(scope) {
                    return scope.$eval(attrs.compile);
                },
                function(value) {
                    element.html(value);
                    $compile(element.contents())(scope);
                }
            );
        };
    });
});
angular.module('myInterceptor', [])
    .factory('myInterceptor', ["$q", function ($q) {
      var ModalLoading = '<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="http-modal-loading">' +
          '<div class="am-modal-dialog">' +
          '<div class="am-modal-hd">正在载入...</div>' +
          '<div class="am-modal-bd">' +
          '<span class="am-icon-spinner am-icon-spin"></span>' +
          '</div>' +
          '</div>' +
          '</div>';
      $("body").append(ModalLoading);
      var timestampMarker = {
        request: function (config) {
          config.st = setTimeout("$('#http-modal-loading').modal('open')", 1);
          return config;
        },
        response: function (res) {
          clearTimeout(res.config.st);
          $('#http-modal-loading').modal('close');
          return res;
        },
        responseError: function (err) {
          clearTimeout(err.config.st);
          $('#http-modal-loading').modal('close');
            if(err.status==404){
                alert('页面没有发现或者已经转移到其他服务器！');
            }else if(err.status==500){
                alert('服务器故障，请联系管理员！');
            }else{
                alert("请求失败，错误原因["+err.statusText+"]");
            }
          return $q.reject(err);
        }
      };
      return timestampMarker;
    }]);
angular.module('repeatFinish', [])
    .directive('onFinishRender', function ($timeout) {
      return {
        restrict: 'A',
        link: function (scope, element, attr) {
          if (scope.$last === true) {
            $timeout(function () {
              scope.$emit(attr.onFinishRender);
            });
          }
        }
      }
    });
var linksMAP={
    yggl:{
        title:"员工管理",
        url:"p1/yggl",
        admin:{
            title:"管理员管理",
            url:"p1/yggl",
        },
        emp:{
            title:"普通员工管理",
            url:"p1/yggl",
        },
        boss:{
            title:"领导管理",
            url:"p1/yggl",
        }
    }
};
angular.module('getTitle', [])
    .directive('getTitle', function () {
      return {
        restrict: 'A',
        template: function (element, attr) {
          var names = attr.getTitle.split(".");
          var value = linksMAP;
          for (var i = 0; i < names.length; i++) {
            value = value[names[i]]
          }
          value = value.title;
          return value;
        }
      }
    });

function goto(url) {
  window.location.href = url;
}
