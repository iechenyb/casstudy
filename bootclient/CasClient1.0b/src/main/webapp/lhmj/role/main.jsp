<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<mytop></mytop>
<div class="am-g">
    <div class="am-u-sm-7">&nbsp;</div>
    <div class="am-u-sm-1">
    	<a ui-sref="role.list" href id="load" ><button type="button" class="am-btn am-btn-success">列表</button></a>
    </div>
    <div class="am-u-sm-1"><button
            type="button"
            class="am-btn am-btn-primary"
            data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 400, height: 225}">
        新增
    </button>
    </div>
    <div class="am-u-sm-3">&nbsp;</div>
</div>
<div ui-view>
    {{name}}-role
</div>
