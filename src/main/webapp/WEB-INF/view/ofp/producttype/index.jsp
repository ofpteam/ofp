<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ace/tree.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ofp/producttype/index.js"></script>
<div class="page-header">
	<shiro:hasPermission name="producttype:addUI">
		<button id="btnAdd" type="button"
			class="btn btn-primary btn-sm">
			<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="producttype:editUI">
		<button id="btnEdit" type="button"
			class="btn btn-success btn-sm">
			<i class="fa fa-pencil-square-o"></i>&nbsp;编辑
		</button>
	</shiro:hasPermission>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12 widget-container-col ui-sortable"
		style="min-height: 127px;">
		<!-- #section:custom/widget-box.options.transparent -->
		<div id="tree" class="tree"></div>

	</div>
</div>


