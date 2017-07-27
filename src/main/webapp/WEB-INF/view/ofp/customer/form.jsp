<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/customer/form.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty customerEntity}">
		新增客户
		</c:if>
		<c:if test="${!empty customerEntity}">
		编辑客户
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<form id="customerForm" name="customerForm" class="form-horizontal"
		role="form" method="post">
		<c:if test="${!empty customerEntity}">
			<input type="hidden" id="pageNum" name="pageNum"
				value="${page.pageNum }">
			<input type="hidden" id="pageSize" name="pageSize"
				value="${page.pageSize }">
			<input type="hidden" id="orderByColumn" name="orderByColumn"
				value="${page.orderByColumn }">
			<input type="hidden" id="orderByType" name="orderByType"
				value="${page.orderByType }">
			<input type="hidden" name="customerId" id="customerId" value="${customerEntity.customerId}">
		</c:if>
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right"
				for="customerName">客户名称</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<input 
						class="form-control" name="customerName" id="customerName"
						type="text" value="${customerEntity.customerName}"
						placeholder="客户名称..." />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right"
				for="telephone">电话</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<input 
						class="form-control" name="telephone" id="telephone" type="text"
						value="${customerEntity.telephone }" placeholder="电话..." />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right" for="country">国家</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<input 
						class="form-control" name="country" id="country" type="text"
						value="${customerEntity.country }" placeholder="国家..." />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right" for="description">描述</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<input 
						class="form-control" name="description" id="description" type="text"
						value="${customerEntity.description }" placeholder="描述..." />
				</div>
			</div>
		</div>
	</form>
</div>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="javascript:$('#customerForm').submit();"
		class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp;
		<c:if test="${empty customerEntity}">
		添加
		</c:if>
		<c:if test="${!empty customerEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/ocustomer/listUI.html<c:if test="${!empty customerEntity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>