<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/chosen/chosen.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/datepicker/css/bootstrap-datepicker3.standalone.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.bootstrap.js"></script>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.jeditable.js"></script> --%>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.editable.js"></script> --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ace/tree.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/quotationsheet/form.js"></script>
<script type="text/javascript">
	$(function() {
		validateForm();
	});
</script>
<style type="text/css">
/* input.has-error {
	border: 1px solid red !important;
}
 */
.help-block {
	padding-left: 16px;
	padding-bottom: 2px;
	font-weight: bold;
	color: red;
}
</style>
<div class="page-header">
	<h1>
		<c:if test="${empty quotationSheetEntity}">
		新增报价单
		</c:if>
		<c:if test="${!empty quotationSheetEntity}">
		保存报价单
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<form id="exportExcelForm" method="post"></form>
		<form id="quotationsheetForm" name="quotationsheetForm"
			class="form-horizontal" role="form" method="post">
			<c:if test="${!empty quotationSheetEntity}">
				<%-- <input type="hidden" id="pageNum" name="pageNum"
					value="${page.pageNum }">
				<input type="hidden" id="pageSize" name="pageSize"
					value="${page.pageSize }">
				<input type="hidden" id="orderByColumn" name="orderByColumn"
					value="${page.orderByColumn }">
				<input type="hidden" id="orderByType" name="orderByType"
					value="${page.orderByType }"> --%>
				<input type="hidden" name="createUser" id="createUser"
					value="${quotationSheetEntity.createUser }">
				<input type="hidden" name="quotationSheetId" id="quotationSheetId"
					value="${quotationSheetEntity.quotationSheetId }">
				<input type="hidden" id="customerId"
					value="${quotationSheetEntity.customer.customerId }">
			</c:if>
			<div class="form-group">
				<input type="hidden" name="quotationSubSheetEntities"
					id="quotationSubSheetEntities" />
				<input type="hidden" name="productSelectList" id="productSelectList"
					value=${productIds } />
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="customerName">客户名称:</label>
					<div class="col-sm-3">
						<div>
							<select class="chosen-select" style="width: 100%"
								name="customerId" id="customerSelect" data-placeholder="客户名称...">
								<option value="">&nbsp;</option>
							</select>
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="contacts">联系人:</label>
					<div class="col-sm-3">
						<div>
							<input readonly class="form-control" id="contacts" type="text"
								value="${quotationSheetEntity.customer.contacts }"
								placeholder="联系人..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="telephone">联系电话:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" readonly id="telephone" type="text"
								value="${quotationSheetEntity.customer.telephone }"
								placeholder="联系电话..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="quotationSheetCode">报价单号:</label>
					<div class="col-sm-3">
						<!-- 自动生成(时间戳) -->
						<input readonly class="form-control" name="quotationSheetCode"
							id="quotationSheetCode" type="text"
							value="${quotationSheetEntity.quotationSheetCode }"
							placeholder="报价单号..." />
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="quotationDate">报价日期:</label>
					<div class="col-sm-3">
						<input type="text" id="quotationDate" name="quotationDate"
							<c:if test="${!empty quotationSheetEntity}">
							value=<fmt:formatDate value="${quotationSheetEntity.quotationDate }" pattern="yyyy-MM-dd"/>
							</c:if>
							class="datepicker col-sm-12" placeholder="报价日期" />
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="priceTerms">价格术语:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="priceTerms" id="priceTerms"
								type="text" value="${quotationSheetEntity.priceTerms }"
								placeholder="价格术语..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="country">国家:</label>
					<div class="col-sm-3">
						<div>
							<input readonly class="form-control" id="country" type="text"
								value="${quotationSheetEntity.customer.country }"
								placeholder="国家..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="currency">币种:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="currency" id="currency"
								type="text" value="${quotationSheetEntity.currency }"
								placeholder="币种..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="exchangeRate">汇率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="exchangeRate" id="exchangeRate"
								type="number" value="${quotationSheetEntity.exchangeRate }"
								placeholder="汇率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="expirationDate">有效期限:</label>
					<div class="col-sm-3">
						<div>
							<input type="text" id="expirationDate" name="expirationDate"
								value="${quotationSheetEntity.expirationDate }"
								class="form-control" type="number" placeholder="有效期限" />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="payMode">付款方式:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="payMode" id="payMode"
								type="text" value="${quotationSheetEntity.payMode }"
								placeholder="付款方式..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="resource">起运地:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="resource" id="resource"
								type="text" value="${quotationSheetEntity.resource }"
								placeholder="起运地..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="dest">目的地:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="dest" id="dest" type="text"
								value="${quotationSheetEntity.dest }" placeholder="目的地..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="deliveryDate">交货期限:</label>
					<div class="col-sm-3">
						<div>
							<input type="number" id="deliveryDate" name="deliveryDate"
								value="${quotationSheetEntity.deliveryDate }"
								class="form-control" placeholder="交货期限..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="insuranceCost">保险费率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="insuranceCost"
								id="insuranceCost" type="number"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								${quotationSheetEntity.insuranceCost } placeholder="保险费率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="foreignGreight">国外运费:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="foreignGreight"
								id="foreignGreight" type="number"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								"${quotationSheetEntity.foreignGreight }"
								placeholder="国外运费..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="homeGreight">国内运费:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="homeGreight" id="homeGreight"
								type="number"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								"${quotationSheetEntity.homeGreight }"
								placeholder="国内运费..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="operationCost">管理费:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="operationCost"
								id="operationCost" type="number"
								value=<c:if test="${empty quotationSheetEntity}">1.5</c:if>
								"${quotationSheetEntity.operationCost }" 
								placeholder="管理费..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="commission">佣金率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="commission" id="commission"
								type="number"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								"${quotationSheetEntity.commission }" 
								placeholder="佣金率..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="rebate">折扣率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="rebate" id="rebate"
								type="number"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								"${quotationSheetEntity.rebate }" placeholder="折扣率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="totalCbm">CBM合计:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="totalCbm" id="totalCbm"
								readonly type="number" value="${quotationSheetEntity.totalCbm }"
								placeholder="CBM合计..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="profit">利润:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="profit" id="profit"
								value="${quotationSheetEntity.profit }" type="number" readonly
								placeholder="利润..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="swapRate">换汇率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="swapRate" id="swapRate"
								readonly type="number" value="${quotationSheetEntity.swapRate }"
								placeholder="换汇率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="interestMonth">计息月:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="interestMonth"
								id="interestMonth" type="number"
								value="${quotationSheetEntity.interestMonth }"
								placeholder="计息月..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="taxRebateRate">退税率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="taxRebateRate"
								id="taxRebateRate"
								value=<c:if test="${empty quotationSheetEntity}">0</c:if>
								"${quotationSheetEntity.taxRebateRate }"
								 type="number"
								placeholder="退税率..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="valueAddedTaxRate">增值税率:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="valueAddedTaxRate"
								id="valueAddedTaxRate" type="number"
								value=<c:if test="${empty quotationSheetEntity}">17</c:if>
								"${quotationSheetEntity.valueAddedTaxRate }"
								placeholder="换汇率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="rate">利率:</label>
					<div class="col-sm-3">
						<input class="form-control" name="rate" id="rate" type="number"
							value=<c:if test="${empty interestRateEntity}">${Rate }</c:if>
							"${interestRateEntity.rate }"
							placeholder="利率..." />
					</div>
				</div>
			</div>
		</form>
		<div class="hr hr-dotted"></div>
	</div>
</div>
<!-- 按钮触发模态框 -->
<button class="btn btn-primary btn-xm" data-toggle="modal"
	id="btnOpenModal">添加商品</button>
<button class="btn btn-primary btn-xm" id="btnDelteRow">删除商品</button>
<button class="btn btn-primary btn-xm" id="btnCalculation">计算利润</button>
<c:if test="${!empty quotationSheetEntity}">
	<button class="btn btn-primary btn-xm" id="btnExcel"
		onclick="exportQuotationSheet('/quotationsheet/exportQuotationSheet.html', 'EXCEL')">导出Excel</button>
	<button class="btn btn-primary btn-xm" id="btnExcelOld"
		onclick="exportQuotationSheet('/quotationsheet/exportQuotationSheet.html', 'OLDEXCEL')">导出Excel(Old)</button>
	<button class="btn btn-primary btn-xm" id="btnExcelOld"
		onclick="exportQuotationSheet('/quotationsheet/exportQuotationSheet.html', 'PDF')">导出PDF</button>
</c:if>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择商品</h4>
			</div>
			<div class="modal-body">
				<!-- <div id="searchTree"></div> -->
				<div class="form-group">
					<div class="row">
						<label class="col-sm-2 control-label no-padding-right"
							for="productTypeSelect">产品大类:</label>
						<div class="col-sm-10">
							<div>
								<select class="chosen-select" id="productTypeSelect"
									style="width: 100%;" data-placeholder="产品大类...">
									<c:forEach var="productType" items="${productTypeList }">
										<option value='${productType.productTypeId}'>
											${productType.cnName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="row">
						<label class="col-sm-2 control-label no-padding-right"
							for="productCode">商品编码:</label>
						<div class="col-sm-10">
							<div>
								<select class="chosen-select" id="productSelect"
									style="width: 100%;" data-placeholder="商品编码...">
									<c:forEach var="product" items="${productList }">
										<option value='${product.productId}'>
											${product.productCode}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<!-- 	<div class="form-group">
					<div class="row">
						<label class="col-sm-2 control-label no-padding-right"
							for="number">数量:</label>
						<div class="col-sm-10">
							<div>
								<input class="form-control" name="number" id="number"
									type="number" placeholder="数量..." />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="row">
						<label class="col-sm-2 control-label no-padding-right"
							for="packNum">箱数:</label>
						<div class="col-sm-10">
							<div>
								<input class="form-control" name="packNum" id="packNum"
									type="number" placeholder="箱数..." />
							</div>
						</div>
					</div>
				</div> -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" id="btnAddRows">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<table id="example" class="display" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>商品编码</th>
			<th>收购单价</th>
			<th>美金单价</th>
			<th>单位</th>
			<th>Top(cm)</th>
			<th>Bottom(cm)</th>
			<th>Height(cm)</th>
			<th>Weight(g)</th>
			<th>Volume(ml)</th>
			<th>Packing(cm)</th>
			<th>装箱率</th>
			<th>数量</th>
			<th>箱数</th>
			<th>TOTALCBM</th>
			<th>总毛重</th>
			<th>CBM</th>
			<th>GW</th>
			<th>操作</th>
		</tr>
	</thead>
</table>
<div class="center">
	<button id="btnAdd" type="button"
		onclick="javascript:$('#quotationsheetForm').submit();"
		class="btn btn-success btn-sm">
		<i class="fa fa-user-plus"></i>&nbsp;
		<c:if test="${empty quotationSheetEntity}">
		添加
		</c:if>
		<c:if test="${!empty quotationSheetEntity}">
		保存
		</c:if>
	</button>
	<button id="btn" type="button"
		onclick="webside.common.loadPage('/quotationsheet/listUI.html')"
		class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>