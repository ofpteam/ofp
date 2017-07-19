var dtGridColumns = [ {
	id : 'CUSTOMER_ID',
	title : '编号',
	type : 'number',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'CUSTOMER_NAME',
	title : '客户姓名',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'TELEPHONE',
	title : '电话',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'COUNTRY',
	title : '国家',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'DESCRIPTION',
	title : '描述',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
} ];
var dtGridOption = {
	lang : 'zh-cn',
	ajaxLoad : true,
	check : true,
	extraWidth : '37px',
	loadURL : sys.rootPath + '/ocustomer/list.html',
	columns : dtGridColumns,
	gridContainer : 'dtGridContainer',
	toolbarContainer : 'dtGridToolBarContainer',
	tools : 'refresh|print',
	exportFileName : '客户信息',
	pageSize : 10,
	pageSizeLimit : [ 10, 20, 30 ]
};
var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
	grid.load();
	$("#btnSearch").click(customSearch);

	// 注册回车键事件
	document.onkeypress = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			customSearch();
		}
	};

});

// 自定义查询
function customSearch() {
	grid.parameters = new Object();
	grid.parameters['CUSTOMER_NAME'] = $("#searchKey").val();
	grid.refresh(true);
}
