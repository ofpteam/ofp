<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/chosen/bootstrap-select.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/chosen/bootstrap-select.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/roleproduct/form.js"></script>
<div class="page-header"></div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right" for="roleId">利率:</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<select class="chosen-select" style="width: 100%" name="roleId"
						id="roleId">
						<option value="">&nbsp;</option>
					</select>
				</div>
			</div>
		</div>

	</div>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<div class="form-group">
			<label class="control-label col-sm-1 no-padding-right"
				for="productId">商品:</label>
			<div class="col-sm-10">
				<div class="clearfix">
					<select class="form-control selectpicker" multiple>
					<option value="">&nbsp;</option>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="center" style="margin-top: 10px;">
	<button id="btnSave" type="button" class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp; 保存
	</button>
</div>