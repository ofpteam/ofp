<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.jeditable.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.editable.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ace/tree.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/quotationsheet/form.js"></script>
<div class="page-header">
	<h1>
		<c:if test="${empty userEntity}">
		新增报价单
		</c:if>
		<c:if test="${!empty userEntity}">
		保存报价单
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
			</c:if>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="CUSTOMER_NAME">客户名称:</label>
					<div class="col-sm-3">
						<div>
							<input class="form-control" name="CUSTOMER_NAME"
								id="CUSTOMER_NAME" type="text"
								value="${userEntity.accountName }" placeholder="客户名称..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="CUSTOMER_NAME">联系人:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="CUSTOMER_NAME" id="CUSTOMER_NAME"
								type="text" value="${userEntity.accountName }"
								placeholder="工厂编码..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="TELEPHONE">联系电话:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="TELEPHONE" id="TELEPHONE" type="text"
								value="${userEntity.accountName }" placeholder="联系电话..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="QUOTATION_SHEET_CODE">报价单号:</label>
					<div class="col-sm-3">
						<!-- 自动生成 -->
						<input readonly class="form-control" name="QUOTATION_SHEET_CODE"
							id="QUOTATION_SHEET_CODE" type="text"
							value="${userEntity.accountName }" placeholder="报价单号..." />
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="QUOTATION_DATE">报价日期:</label>
					<div class="col-sm-3">
						<input type="text" id="QUOTATION_DATE"
							class="datepicker col-sm-12" placeholder="报价日期" />
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="PRICE_TERMS">价格术语:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PRICE_TERMS" id="PRICE_TERMS"
								type="text" value="${userEntity.accountName }"
								placeholder="价格术语..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="COUNTRY">国家:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="COUNTRY" id="COUNTRY" type="text"
								value="${userEntity.accountName }" placeholder="国家..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="CN_NAME">币种:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="CURRENCY" id="CURRENCY" type="text"
								value="${userEntity.accountName }" placeholder="币种..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="EXCHANGE_RATE">汇率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="EXCHANGE_RATE" id="EXCHANGE_RATE"
								type="number" value="${userEntity.accountName }"
								placeholder="汇率..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="VAT_RATE">有效期限:</label>
					<div class="col-sm-3">
						<div>
							<input type="text" id="VAT_RATE" class="datepicker col-sm-12"
								placeholder="有效期限" />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="PAY_MODE">付款方式:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PAY_MODE" id="PAY_MODE" type="text"
								value="${userEntity.accountName }" placeholder="付款方式..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="RESOURCE">起运地:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="RESOURCE" id="RESOURCE" type="text"
								value="${userEntity.accountName }" placeholder="起运地..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="DEST">目的地:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="DEST" id="DEST" type="text"
								value="${userEntity.accountName }" placeholder="目的地..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="DELIVERY_DATE">交货期限:</label>
					<div class="col-sm-3">
						<div>
							<input type="text" id="DELIVERY_DATE"
								class="datepicker col-sm-12" placeholder="交货期限..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="INSURANCE_COST">保险费:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="INSURANCE_COST" id="INSURANCE_COST"
								type="number" value="${userEntity.accountName }"
								placeholder="保险费..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="FOREIGN_GREIGHT">国外运费:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="FOREIGN_GREIGHT" id="FOREIGN_GREIGHT"
								type="number" value="${userEntity.accountName }"
								placeholder="国外运费..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="HOME_GREIGHT">国内运费:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="HOME_GREIGHT" id="HOME_GREIGHT"
								type="number" value="${userEntity.accountName }"
								placeholder="国内运费..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="OPERATION_COST">管理费:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="OPERATION_COST" id="OPERATION_COST"
								type="number" value="${userEntity.accountName }"
								placeholder="管理费..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right"
						for="COMMISSION">佣金:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="COMMISSION" id="COMMISSION"
								type="number" value="${userEntity.accountName }"
								placeholder="佣金..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right" for="REBATE">折扣:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="REBATE" id="REBATE" type="number"
								value="${userEntity.accountName }" placeholder="折扣..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="TOTAL_CBM">CBM合计:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="TOTAL_CBM" id="TOTAL_CBM"
								type="number" value="${userEntity.accountName }"
								placeholder="CBM合计..." />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label no-padding-right" for="PROFIT">利润:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="PROFIT" id="PROFIT" type="number"
								value="${userEntity.accountName }" placeholder="利润..." />
						</div>
					</div>

					<label class="col-sm-1 control-label no-padding-right"
						for="SWAP_RATE">换汇率:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="SWAP_RATE" id="SWAP_RATE"
								type="number" value="${userEntity.accountName }"
								placeholder="换汇率..." />
						</div>
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="INTEREST_MONTH">计息月:</label>
					<div class="col-sm-3">
						<div>
							<input <c:if test="${!empty userEntity}">readonly</c:if>
								class="form-control" name="INTEREST_MONTH" id="INTEREST_MONTH"
								type="number" value="${userEntity.accountName }"
								placeholder="计息月..." />
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="hr hr-dotted"></div>
	</div>
</div>
<!-- 按钮触发模态框 -->
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">添加商品
</button>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               选择商品
            </h4>
         </div>
         <div class="modal-body">
          <div id="searchTree"></div>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
            <button type="button" class="btn btn-primary" id="btn">
               确认
            </button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
<table id="example" class="display" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>产品编码</th>
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
		</tr>
	</thead>
</table>
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