<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
/* 放大 */
.productCheckBox {
	width: 20px;
	height: 20px;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ofp/product/list.js"></script>
<div class="page-header">
	<shiro:hasPermission name="product:addUI">
		<button id="btnAdd" type="button"
			onclick="webside.common.addModel('/product/addUI.html')"
			class="btn btn-primary btn-sm">
			<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
	<%-- <shiro:hasPermission name="product:editUI">
		<button id="btnEdit" type="button"
			onclick="editProduct('/product/editUI.html')"
			class="btn btn-success btn-sm">
			<i class="fa fa-pencil-square-o"></i>&nbsp;编辑
		</button>
	</shiro:hasPermission> --%>
	<button id="btnEdit" type="button" onclick="addFromProductList()"
		class="btn btn-success btn-sm">
		<i class="fa fa-pencil-square-o"></i>&nbsp;创建报价单
	</button>
	<button id="btnPrint" type="button" 	class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp; 导出excel
	</button>
	<button id="btnPrintTag" type="button" 	class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp; 打印标签
	</button>
</div>
<form id="productForm" name="productForm" method="post"></form>
<div class="input-group col-xs-12" style="margin-top: 5px;">
	<input id="searchKey" type="text" class="input form-control"
		placeholder="商品编码 ...">
</div>
<div class="input-group col-xs-12" style="margin-top: 5px;">
	<select class="form-control" id="parentId" name="parentId">
		<option></option>
		<c:forEach var="productType" items="${productTypeChildrenList }">
			<option value='${productType.productTypeId}'>
				${productType.cnName}</option>
		</c:forEach>
	</select>
</div>
<div class="input-group col-xs-12" style="margin-top: 5px;">
	<span class="input-group-btn">
		<button id="btnSearch" class="btn btn-primary btn-sm" type="button">
			<i class="fa fa-search"></i> 搜索
		</button>
	</span>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12 widget-container-col ui-sortable"
		style="min-height: 127px;">
		<!-- #section:custom/widget-box.options.transparent -->
		<div class="widget-box transparent ui-sortable-handle"
			style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				<h4 class="widget-title lighter">商品列表</h4>
				<div class="widget-toolbar no-border">
					<a href="#" data-action="fullscreen" class="orange2"> <i
						class="ace-icon fa fa-arrows-alt"></i>
					</a> <a href="#" data-action="collapse" class="green"> <i
						class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>

			<div class="widget-body" style="display: block;">
				<div class="widget-main padding-6 no-padding-left no-padding-right">
					<input id="pageNum" type="hidden" value="${page.pageNum }">
					<input id="pageSize" type="hidden" value="${page.pageSize }">
					<input id="orderByColumn" type="hidden"
						value="${page.orderByColumn }"> <input id="orderByType"
						type="hidden" value="${page.orderByType }">
					<div id="dtGridContainer" class="dlshouwen-grid-container"></div>
					<div id="dtGridToolBarContainer"
						class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
	</div>
</div>


