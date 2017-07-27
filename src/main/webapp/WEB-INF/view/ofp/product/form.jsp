<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<link rel="stylesheet"
	href="${ctx }/resources/js/webuploader/webuploader.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/product/form.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/webuploader/webuploader.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/product/upload.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty productEntity}">
		新增商品
		</c:if>
		<c:if test="${!empty productEntity}">
		编辑商品
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="productForm" name="productForm" class="form-horizontal"
			role="form" method="post">
			<c:if test="${!empty productEntity}">
				<input type="hidden" id="pageNum" name="pageNum"
					value="${page.pageNum }">
				<input type="hidden" id="pageSize" name="pageSize"
					value="${page.pageSize }">
				<input type="hidden" id="orderByColumn" name="orderByColumn"
					value="${page.orderByColumn }">
				<input type="hidden" id="orderByType" name="orderByType"
					value="${page.orderByType }">
				<input type="hidden" name="id" id="userId" value="${productEntity.id }">
			</c:if>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="productCode">商品编码:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="productCode" id="productCode"
								type="text" value="${productEntity.productCode }"
								placeholder="商品编码..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="factoryCode">工厂编码:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="factoryCode" id="factoryCode"
								type="text" value="${productEntity.factoryCode }"
								placeholder="工厂编码..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="unit">单位:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="unit" id="unit" type="text"
								value="${productEntity.unit }" placeholder="单位..." />
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
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="CUSTOMS_CODE" id="CUSTOMS_CODE"
								type="text" value="${productEntity.accountName }"
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
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="USD_PRICE" id="USD_PRICE"
								type="number" value="${productEntity.accountName }"
								placeholder="美金单价..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="CN_NAME">中文名称:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="CN_NAME" id="CN_NAME" type="text"
								value="${productEntity.accountName }" placeholder="中文名称..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="EN_NAME">英文名称:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="EN_NAME" id="EN_NAME" type="text"
								value="${productEntity.accountName }" placeholder="英文名称..." />
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
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="VAT_RATE" id="VAT_RATE" type="number"
								value="${productEntity.accountName }" placeholder="增值税率..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="BUY_PRICE">收购单价:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="BUY_PRICE" id="BUY_PRICE"
								type="number" value="${productEntity.accountName }"
								placeholder="收购单价..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="WEIGHT">单品重量:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="WEIGHT" id="WEIGHT" type="number"
								value="${productEntity.accountName }" placeholder="单品重量（g）..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="VOLUME">容量:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="VOLUME" id="VOLUME" type="number"
								value="${productEntity.accountName }" placeholder="容量（ml）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="TOP">Top:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="TOP" id="TOP" type="number"
								value="${productEntity.accountName }" placeholder="口TopΦ(mm)..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="BOTTOM">底:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="BOTTOM" id="BOTTOM" type="number"
								value="${productEntity.accountName }" placeholder="底BottomΦ(mm)..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="LENGTH">高:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="HEIGHT" id="HEIGHT" type="number"
								value="${productEntity.accountName }" placeholder="高Height(mm)..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="LENGTH">外包装长度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="LENGTH" id="LENGTH" type="number"
								value="${productEntity.accountName }" placeholder="外包装长度（单位:cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="WIDTH">外包装宽度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="WIDTH" id="WIDTH" type="number"
								value="${productEntity.accountName }" placeholder="外包装宽度（单位：cm）..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="PACK_HEIGHT">外包装高度:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="PACK_HEIGHT" id="PACK_HEIGHT"
								type="number" value="${productEntity.accountName }"
								placeholder="外包装高度（单位：cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="GW">G.W:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="GW" id="GW" type="number"
								value="${productEntity.accountName }" placeholder="G.W..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="PACKING_RATE">装箱率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="PACKING_RATE" id="PACKING_RATE"
								type="text" value="${productEntity.accountName }"
								placeholder="装箱率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="TAX_REBATE_RATE">退税率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="TAX_REBATE_RATE" id="TAX_REBATE_RATE"
								type="number" value="${productEntity.accountName }"
								placeholder="退税率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="CBM">CBM:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="CBM" id="CBM" type="number"
								value="${productEntity.accountName }" placeholder="CBM..." />
						</div>
					</div>


				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="PACKING">PACKING:</label>
					<div class="col-sm-7">
						<div>
							<textarea <c:if test="${!empty productEntity}">readonly</c:if>
								class="form-control" name="PACKING_RATE" id="PACKING_RATE"
								placeholder="Description and Packing...">
						</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="file">上传附件:</label>
					<div class="col-sm-5">
						<div>
							<div id="thelist" class="uploader-list"></div>
							<div id="picker">选择文件</div>
							<button id="ctlBtn" class="btn btn-default">开始上传</button>
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
		<c:if test="${empty productEntity}">
		添加
		</c:if>
		<c:if test="${!empty productEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/product/listUI.html<c:if test="${!empty productEntity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>