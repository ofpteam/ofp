<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/datepicker/css/bootstrap-datepicker3.standalone.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/ofp/quotationsheet/list.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
<div class="page-header">
	<shiro:hasPermission name="quotationsheet:addUI">
		<button id="btnAdd" type="button" onclick="webside.common.addModel('/quotationsheet/addUI.html')" class="btn btn-primary btn-sm">
		  	<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="quotationsheet:editUI">
		<button id="btnEdit" type="button" onclick="webside.common.editModel('/quotationsheet/editUI.html')" class="btn btn-success btn-sm">
			 <i class="fa fa-pencil-square-o"></i>&nbsp;编辑
		</button>
	</shiro:hasPermission>
</div>
<div class="input-group col-xs-12">
	<label class="col-sm-1 control-label no-padding-right" for="times">报价单日期:</label>
	<div class="col-sm-5">
		<input type="text" id="starttime" class="datepicker"
			placeholder="报价单日开始日期" />---<input type="text" id="endtime"
			class="datepicker" placeholder="报价单日开始日期" />
	</div>
	<label class="col-sm-1 control-label no-padding-right" for="times">客户姓名:</label>
	<div class="col-sm-5">
		 <input id="searchKey" type="text" class="input form-control" placeholder="客户名称...">
	</div>
</div>
<div class="input-group col-xs-12">
	
</div>
<div class="input-group">
	<span class="input-group-btn">
		<button id="btnSearch" class="btn btn-primary btn-sm" type="button">
			<i class="fa fa-search"></i> 搜索
		</button>
	</span>
</div>
<div class="row" style="margin-top:5px;">
	<div class="col-xs-12 widget-container-col ui-sortable"
		style="min-height: 127px;">
		<!-- #section:custom/widget-box.options.transparent -->
		<div class="widget-box transparent ui-sortable-handle"
			style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				<h4 class="widget-title lighter">报价单列表</h4>
				<div class="widget-toolbar no-border">
					<a href="#" data-action="fullscreen" class="orange2"> 
						<i class="ace-icon fa fa-arrows-alt"></i>
					</a> 
					<a href="#" data-action="collapse" class="green"> 
						<i class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>

			<div class="widget-body" style="display: block;">
				<div class="widget-main padding-6 no-padding-left no-padding-right">
					<input id="pageNum" type="hidden" value="${page.pageNum }">
					<input id="pageSize" type="hidden" value="${page.pageSize }">
					<input id="orderByColumn" type="hidden" value="${page.orderByColumn }">
					<input id="orderByType" type="hidden" value="${page.orderByType }">
					<div id="dtGridContainer" class="dlshouwen-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
	</div>
	
	
</div>


