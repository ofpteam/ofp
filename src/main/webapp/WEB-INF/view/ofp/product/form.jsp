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
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="PRODUCT_CODE">商品编码:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="PRODUCT_CODE" id="PRODUCT_CODE"
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
					<label class="col-sm-1 control-label no-padding-right" for="UNIT">单位:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="UNIT" id="UNIT" type="email"
								value="${userEntity.accountName }" placeholder="单位..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="PRODUCT_TYPE_ID_1">商品大类(一级):</label>
					<div class="col-sm-3">
						<select class="form-control" id="PRODUCT_TYPE_ID_1">
							<option value="1">杯子</option>
						</select>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="PRODUCT_TYPE_ID_2">商品大类(二级):</label>
					<div class="col-sm-3">
						<select class="form-control" id="PRODUCT_TYPE_ID_2">
						</select>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="CUSTOMS_CODE">海关编码:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="CUSTOMS_CODE" id="CUSTOMS_CODE"
								type="text" value="${userEntity.accountName }"
								placeholder="海关编码..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="USD_PRICE">美金单价:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="USD_PRICE" id="USD_PRICE"
								type="number" value="${userEntity.accountName }"
								placeholder="美金单价..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="CN_NAME">中文名称:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="CN_NAME" id="CN_NAME" type="text"
								value="${userEntity.accountName }" placeholder="中文名称..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="EN_NAME">英文名称:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="EN_NAME" id="EN_NAME" type="text"
								value="${userEntity.accountName }" placeholder="英文名称..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="VAT_RATE">增值税率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="VAT_RATE" id="VAT_RATE" type="number"
								value="${userEntity.accountName }" placeholder="增值税率..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="BUY_PRICE">工厂价:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="BUY_PRICE" id="BUY_PRICE"
								type="number" value="${userEntity.accountName }"
								placeholder="工厂价..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="WEIGHT">重量:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="WEIGHT" id="WEIGHT" type="number"
								value="${userEntity.accountName }" placeholder="重量（单位：g）..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="VOLUME">容量:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="VOLUME" id="VOLUME" type="number"
								value="${userEntity.accountName }" placeholder="容量（单位：ml）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="TOP">TOP:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="TOP" id="TOP" type="number"
								value="${userEntity.accountName }" placeholder="TOP（单位：mm）..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="BOTTOM">BOTTOM:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="BOTTOM" id="BOTTOM" type="number"
								value="${userEntity.accountName }"
								placeholder="BOTTOM（单位：mm）..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="LENGTH">外包装长度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="LENGTH" id="LENGTH" type="number"
								value="${userEntity.accountName }" placeholder="外包装长度（单位:cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="WIDTH">外包装宽度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="WIDTH" id="WIDTH" type="number"
								value="${userEntity.accountName }" placeholder="外包装宽度（单位：cm）..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="PACK_HEIGHT">外包装高度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PACK_HEIGHT" id="PACK_HEIGHT"
								type="number" value="${userEntity.accountName }"
								placeholder="外包装高度（单位：cm）..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="GW">G.W:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="GW" id="GW" type="number"
								value="${userEntity.accountName }" placeholder="G.W..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="PACKING_RATE">装箱率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PACKING_RATE" id="PACKING_RATE"
								type="number" value="${userEntity.accountName }"
								placeholder="装箱率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="TAX_REBATE_RATE">退税率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="TAX_REBATE_RATE" id="TAX_REBATE_RATE"
								type="number" value="${userEntity.accountName }"
								placeholder="退税率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="CBM">CBM:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="CBM" id="CBM" type="number"
								value="${userEntity.accountName }" placeholder="CBM..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="PACKING">包装描述:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PACKING" id="PACKING"
								type="text" value="${userEntity.accountName }"
								placeholder="包装描述..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="TAX_REBATE_RATE"></label>
					<div class="col-sm-3">
						<div>
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