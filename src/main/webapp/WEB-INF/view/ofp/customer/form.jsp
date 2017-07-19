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
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/customer/form.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty userEntity}">
		新增商品
		</c:if>
		<c:if test="${!empty userEntity}">
		编辑商品
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
				<label class="control-label col-sm-1 no-padding-right"
					for="accountName">邮箱</label>
				<div class="col-sm-10">
					<div class="clearfix">
						<input <c:if test="${!empty userEntity}">readonly</c:if>
							class="form-control" name="accountName" id="accountName"
							type="email" value="${userEntity.accountName }"
							placeholder="邮箱,将做为用户登录系统的账户..." />
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