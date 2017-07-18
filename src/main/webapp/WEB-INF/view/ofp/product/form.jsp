<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		webside.form.user.validateUserForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty userEntity}">
		新增用户
		</c:if>
		<c:if test="${!empty userEntity}">
		编辑用户
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="productForm" name="userForm" class="form-horizontal"
			role="form" method="post">
			<c:if test="${!empty userEntity}">
				<input type="hidden" id="pageNum" name="pageNum"
					value="${page.pageNum }">
				<input type="hidden" id="pageSize" name="pageSize"
					value="${page.pageSize }">
				<input type="hidden" id="orderByColumn" name="orderByColumn"
					value="${page.orderByColumn }">
				<input type="hidden" id="orderByType" name="orderByType"
					value="${page.orderByType }">
				<input type="hidden" name="id" id="userId" value="${userEntity.id }">
				<input type="hidden" name="userInfo.id" value="${userEntity.id }">
			</c:if>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="PRODUCT_CODE">商品编码:</label>
					<div class="col-sm-3">
						<div>
							<input
								class="form-control" name="PRODUCT_CODE" id="PRODUCT_CODE"
								type="text" value="${userEntity.accountName }"
								placeholder="商品编码..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="FACTORY_CODE">工厂编码:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="FACTORY_CODE" id="FACTORY_CODE"
								type="text" value="${userEntity.accountName }"
								placeholder="工厂编码..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="UNIT">单位:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="UNIT" id="UNIT"
								type="email" value="${userEntity.accountName }"
								placeholder="单位..." />
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="hr hr-dotted"></div>
	</div>
</div>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="javascript:$('#productForm').submit();"
		class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp;
		<c:if test="${empty userEntity}">
		添加
		</c:if>
		<c:if test="${!empty userEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/product/listUI.html<c:if test="${!empty userEntity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>