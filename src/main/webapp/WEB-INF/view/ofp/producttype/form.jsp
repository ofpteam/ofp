<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/producttype/form.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty productTypeEntity}">
		新增商品大类
		</c:if>
		<c:if test="${!empty productTypeEntity}">
		编辑商品大类
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="productTypeForm" name="productTypeForm"
			class="form-horizontal" role="form" method="post">
			<c:if test="${!empty productTypeEntity}">
				<input type="hidden" name="productTypeId" id="hdfproductTypeId"
					value="${productTypeEntity.productTypeId }">
				<input type="hidden" name="parentId" id="parentId"
					value="${productTypeEntity.parentId }">
				<input type="hidden" name="level" id="level"
					value="${productTypeEntity.level }">
				<input type="hidden" name="orderby" id="orderby"
					value="${productTypeEntity.orderby }">
			</c:if>
			<c:if test="${!empty productTypeList}">
				<div class="form-group">
					<label class="control-label col-sm-1 no-padding-right"
						for="productTypeId">大类分类</label>
					<div class="col-sm-10">
						<div class="clearfix">
							<select class="form-control" name="productTypeId"
								id="productTypeId" style="width: 100%">
								<c:forEach var="productTypefather" items="${productTypeList }">
									<option value='${productTypefather.productTypeId}'>
										${productTypefather.cnName}</option>
								</c:forEach>
							</select>

						</div>
					</div>
				</div>
			</c:if>
			<div class="form-group">
				<label class="control-label col-sm-1 no-padding-right" for="cnName">大类名称</label>
				<div class="col-sm-10">
					<div class="clearfix">
						<input class="form-control" name="cnName" id="cnName" type="text"
							value="${productTypeEntity.cnName}" placeholder="大类名称..." />
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="javascript:$('#productTypeForm').submit();"
		class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp;
		<c:if test="${empty productTypeEntity}">
		添加
		</c:if>
		<c:if test="${!empty productTypeEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/producttype/index.html')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>