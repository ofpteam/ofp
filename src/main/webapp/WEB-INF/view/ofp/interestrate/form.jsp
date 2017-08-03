<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/interestrate/form.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<div class="page-header"></div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="interestRateForm" name="interestRateForm"
			class="form-horizontal" role="form" method="post">
			<c:if test="${!empty interestRateEntity}">
				<input type="hidden" name="interestRateId" id="interestRateId"
					value="${interestRateEntity.interestRateId }">
			</c:if>
			<div class="form-group">
				<label class="control-label col-sm-1 no-padding-right" for="rate">利率:</label>
				<div class="col-sm-10">
					<div class="clearfix">
						<input class="form-control" name="rate" id="rate" type="number"
							value="${interestRateEntity.rate}" placeholder="利率..." />
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="javascript:$('#interestRateForm').submit();"
		class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp;
		<c:if test="${empty interestRateEntity}">
		添加
		</c:if>
		<c:if test="${!empty interestRateEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/interestRate/listUI.html')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>