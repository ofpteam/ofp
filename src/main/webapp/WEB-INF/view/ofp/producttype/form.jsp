<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
	<script type="text/javascript"
	src="${ctx }/resources/js/opf/producttype/form.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<div class="page-header">
	<h1>
		<c:if test="${empty userEntity}">
		新增商品大类
		</c:if>
		<c:if test="${!empty userEntity}">
		编辑商品大类
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="productTypeForm" name="jobForm" class="form-horizontal" role="form"
			method="post">
			<input type="hidden" name="jobGroup" id="jobGroup" value="">

			<div class="form-group">
				<label class="control-label col-sm-1 no-padding-right"
					for="jobGroupSelect">大类分类</label>
				<div class="col-sm-10">
					<div class="clearfix">
						<select class="form-control" name="jobGroupSelect"
							id="jobGroupSelect" style="width: 100%">
							<option value="1">杯子</option>
						</select>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-1 no-padding-right" for="jobName">大类名称</label>
				<div class="col-sm-10">
					<div class="clearfix">
						<input class="form-control" name="jobName" id="jobName"
							type="text" value="" placeholder="大类名称..." />
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="submit();"
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
		onclick="webside.common.loadPage('/producttype/index.html')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>