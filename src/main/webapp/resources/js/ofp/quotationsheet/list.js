var dtGridColumns = [{
	id : 'QUOTATION_SHEET_ID',
	title : '编号',
	type : 'number',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header'
}, {
    id : 'CUSTOMER_Name',
    title : '客户名称',
    type : 'string',
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


