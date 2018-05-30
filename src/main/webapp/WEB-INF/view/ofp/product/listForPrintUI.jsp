<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquerydatatables/jquery.dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/ofp/product/listForPrintUI.js"></script>
<div class="page-header">
	<h1>
	商品列表
	</h1>
</div>
<div class="row" style="margin-top: 5px;">
	<table id="example" class="display" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>商品编号</th>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>工厂编码</th>
			</tr>
		</thead>
	</table>
</div>
<form id="productForm" name="productForm" method="post"></form>
<div class="center">
	<button id="btnPrint" type="button"
		class="btn btn-success btn-sm" >
		<i class="fa fa-user-plus"></i>&nbsp; 导出excel
	</button>
	<button id="btnPrintTag" type="button"
		class="btn btn-success btn-sm" >
		<i class="fa fa-user-plus"></i>&nbsp; 打印标签
	</button>
	<button id="btnPrintTagJS" type="button"
		class="btn btn-success btn-sm" >
		<i class="fa fa-user-plus"></i>&nbsp; 客户端打印标签
	</button>
</div>