var dtGridColumns = [{
	id : 'QUOTATION_SHEET_ID',
	title : '编号',
	type : 'number',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header'
}, {
    id : 'CUSTOMER_NAME',
    title : '客户名称',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
	   id : 'QUOTATION_DATE',
	    title : '报价日期',
	    type : 'date',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	
}];
var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    extraWidth : '37px',
    loadURL : sys.rootPath + '/quotationsheet/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print',
    exportFileName : '报价单信息',
    pageSize : 10,
    pageSizeLimit : [10, 20, 30]
};
var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
    grid.load();
    $("#btnSearch").click(customSearch);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch();
        }
    };
    
});

//自定义查询
function customSearch() {
    grid.parameters = new Object();
    grid.parameters['CUSTOMER_NAME'] = $("#searchKey").val();
    grid.parameters['TIMES'] = $("#id-date-range-picker-1").val();
    grid.refresh(true);
}


//编辑
function editQuotationSheet(nav){
	 //当前页码
	 var nowPage = grid.pager.nowPage;
	// 获取每页显示的记录数(即: select框中的10,20,30)
	var pageSize = grid.pager.pageSize;
	// 获取排序字段
	var columnId = grid.sortParameter.columnId;
	// 获取排序方式 [0-不排序，1-正序，2-倒序]
	var sortType = grid.sortParameter.sortType;
	// 获取选择的行
	var rows = grid.getCheckedRecords();
	if (rows.length == 1) {
		webside.common.loadPage(nav + '?id=' + rows[0].QUOTATION_SHEET_ID + "&page="
				+ nowPage + "&rows=" + pageSize + "&sidx=" + columnId
				+ "&sord=" + sortType);
	} else {
		layer.msg("你没有选择行或选择了多行数据", {
			icon : 0
		});
	}
}

