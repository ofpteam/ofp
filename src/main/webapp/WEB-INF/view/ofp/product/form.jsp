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
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/product/uploadAttachment.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
		if ($('#hdMapUrl').val() != undefined && $('#hdMapUrl').val() != "") {//没有上传图片不显示
			var url = sys.rootPath + "/product/loadQRCode.html?productId="
					+ $('#productId').val() + '&baseUri='
					+ $.url().attr('path');
			document.getElementById("qrCode").src = url;

			var loadThumbnailurl = sys.rootPath
					+ "/product/loadThumbnail.html?productId="
					+ $('#productId').val() + '&baseUri='
					+ $.url().attr('path');
			document.getElementById("thumbnail").src = loadThumbnailurl;

		} else {
			$('#qrCode').hide();
		}

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
				<input type="hidden" name="productId" id="productId"
					value="${productEntity.productId }">

				<input type="hidden" id="productTypeId"
					value="${productEntity.productType.productTypeId }">
			</c:if>
			<input type="hidden" id="attachmentNames"
				value="${productEntity.attachmentNames }">
			<div class="form-group">
				<input type="hidden" name="hdMapUrl" id="hdMapUrl"
					value="${productEntity.hdMapUrl }">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="productCode">商品编码:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="productCode" id="productCode"
								type="text" value="${productEntity.productCode }"
								placeholder="商品编码..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="factoryCode">工厂编码:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="factoryCode" id="factoryCode"
								type="text" value="${productEntity.factoryCode }"
								placeholder="工厂编码..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="PRODUCT_TYPE_ID_1">商品大类(一级):</label>
					<div class="col-sm-2">
						<select class="form-control" id="productTypefather"
							name="productTypefather">
							<c:forEach var="productTypefather" items="${productTypeList }">
								<c:choose>
									<c:when test="${empty productEntity}">
										<option value='${productTypefather.productTypeId}'>
											${productTypefather.cnName}</option>
									</c:when>
									<c:when
										test="${ productEntity!=null and
									productTypefather.productTypeId==productEntity.productType.parentId}">
										<option value='${productTypefather.productTypeId}'
											selected="selected">${productTypefather.cnName}</option>
									</c:when>
									<c:otherwise>
										<option value='${productTypefather.productTypeId}'>
											${productTypefather.cnName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="productType">商品大类(二级):</label>
					<div class="col-sm-2">
						<select class="form-control" id="productType" name="productTypeId">
							<c:forEach var="productType" items="${productTypeChildrenList }">
								<option value='${productType.productTypeId}'>
									${productType.cnName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="enName">英文名称:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="enName" id="enName" type="text"
								value="${productEntity.enName }" placeholder="英文名称..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="cnName">中文名称:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="cnName" id="cnName" type="text"
								value="${productEntity.cnName }" placeholder="中文名称..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="customsCode">海关编码:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="customsCode" id="customsCode"
								type="text" value="${productEntity.customsCode }"
								placeholder="海关编码..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="unit">单位:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="unit" id="unit" type="text"
								value="${productEntity.unit }" placeholder="单位..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="usdPrice">美金单价:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="usdPrice" id="usdPrice"
								type="number" value="${productEntity.usdPrice }"
								placeholder="美金单价..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="buyPrice">收购单价:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="buyPrice" id="buyPrice"
								type="number" value="${productEntity.buyPrice }"
								placeholder="收购单价..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="vatRate">增值税率:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="vatRate" id="vatRate"
								type="number" value="${productEntity.vatRate }"
								placeholder="增值税率..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="taxRebateRate">退税率:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="taxRebateRate"
								id="taxRebateRate" type="number"
								value="${productEntity.taxRebateRate }" placeholder="退税率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">

					<label class="col-sm-1 control-label no-padding-right" for="top">Top:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="top" id="top" type="number"
								value="${productEntity.top }" placeholder="口TopΦ(mm)..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="bottom">Bottom:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="bottom" id="bottom"
								type="number" value="${productEntity.bottom }"
								placeholder="底BottomΦ(mm)..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="height">Height:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="height" id="height"
								type="number" value="${productEntity.height }"
								placeholder="高Height(mm)..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="gw">G.W:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="gw" id="gw" type="number"
								value="${productEntity.gw }" placeholder="G.W..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="length">外箱长:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="length" id="length"
								type="number" value="${productEntity.length }"
								placeholder="外箱长（单位:cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="width">外箱宽:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="width" id="width" type="number"
								value="${productEntity.width }" placeholder="外箱宽（单位：cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">外箱高:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="packHeight" id="packHeight"
								type="number" value="${productEntity.packHeight }"
								placeholder="外箱高（单位：cm）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="cbm">CBM:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="cbm" id="cbm" readonly
								value="${productEntity.cbm }" placeholder="CBM..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="volume">容量:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="volume" id="volume"
								type="number" value="${productEntity.volume }"
								placeholder="容量（ml）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="weight">单品重量:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="weight" id="weight"
								type="number" value="${productEntity.weight }"
								placeholder="单品重量（g）..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="packingRate">装箱率:</label>
					<div class="col-sm-2">
						<div>
							<input class="form-control" name="packingRate" id="packingRate"
								type="number" value="${productEntity.packingRate }"
								placeholder="装箱率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="packing">Description:</label>
					<div class="col-sm-11">
						<div>
							<textarea class="form-control" name="packing" id="packing"
								placeholder="Description and Packing...">${productEntity.packing }</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">二维码:</label>
					<div class="col-sm-3">
						<div>
							<img id="qrCode" alt="二维码" height="200px" />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">缩略图(点击下载):</label>
					<div class="col-sm-3">
						<div>
							<a id="downloadfile"><img id="thumbnail" alt="缩略图"
								height="200px" /></a>
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">上传图片:</label>
					<div class="col-sm-3" id="uploader-demo">
						<div>
							<div id="fileList" class="uploader-list"></div>
							<div id="upInfo"></div>
							<div id="filePicker">选择图片</div>
							<input type="button" id="btn" value="开始上传"
								class="btn btn-default"
								style="width: 83px; height: 40px; float: left; margin-top: 10px;" />
						</div>
					</div>
					<!-- <div id="uploader-demo" class="col-sm-3">
						
					</div> -->
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">附件列表:</label>
					<div class="col-sm-3">
						<div id="thelist" class="uploader-list"></div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight"></label>
					<div class="col-sm-3 no-padding-left">
						<div>
							
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="packHeight">上传附件:</label>
					<div class="col-sm-3" id="uploader">
						<div>
							<div>
								<div class="btns">
									<div id="Picker">选择附件</div>
									<input type="button" id="ctlBtn" value="开始上传"
										class="btn btn-default"
										style="width: 83px; height: 40px; float: left; margin-top: 10px;" />
								</div>
							</div>
						</div>
					</div>

					<!-- <label class="col-sm-2 control-label no-padding-right"
					for="hdMapUrl">上传附件:</label>
				<div class="col-sm-2" id="uploader">
					
				</div> -->
				</div>
			</div>
		</form>

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