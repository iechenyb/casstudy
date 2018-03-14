<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<div class="am-g">
    <div class="am-u-sm-1">&nbsp;</div>
    <div class="am-u-sm-10">
        <table class="am-table">
            <tr><th>序号</th> <th>名称</th><td>操作</td></tr>
            <tr ng-repeat="x in items"><td> {{ x.id }}</td> <td> {{ x.name }}</td>
                <td>
                    <button type="button" class="am-btn am-btn-success" ng-click="delRole(x.id)">删除</button>
                    <button type="button" class="am-btn am-btn-success" ng-click="">修改</button>
                </td>
            </tr>
        </table>
    </div>
    <div class="am-u-sm-1">&nbsp;</div>
    <div class="am-u-sm-4">&nbsp;</div>
    <div class="am-u-sm-8"><div id="page"></div></div>
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <button type="button" class="am-btn am-btn-success" ng-click="addRole()">新增</button>
            </div>
        </div>
    </div>
</div>