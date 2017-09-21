var productSelectedList= new Array();
//更新选中记录
function onChecked(productId){
	if($.inArray(productId, productSelectedList)==-1 ){
		productSelectedList.push(productId);	
	}else{
		productSelectedList.splice($.inArray(productId,productSelectedList),1);
	}
}
//从商品列表勾选商品再创建报价单
function addFromProductList(){
	if(productSelectedList.length==0){
		layer.msg("你没有选择行", {
			icon : 0
		});
	}else{
		webside.common.loadPage('/quotationsheet/addUI.html' + '?productIds='+productSelectedList );
	}
}


$('#btnPrint').click(function(){
	var url = "/product/exportQrCodeBatch.html";
	if(productSelectedList.length==0){
		layer.msg("你没有选择行", {
			icon : 0
		});
		return;
	}
	var productIds="";
/*	$.each(MliSelected,function(i,v){
		//选中
		productIds += oTable.fnGetData(v).productId + ",";
		//var t=oTable.rows('.selected').data();
	});*/
	var path =sys.rootPath+url+'?productIds=' + productSelectedList + '&baseUri=' + $.url().attr('path');  
    $('#productForm').attr("action", path).submit();
});

$('#btnBatchExport').click(function(){
	var url = "/product/exportProductBatch.html";
	if(productSelectedList.length==0){
		layer.msg("你没有选择行", {
			icon : 0
		});
		return;
	}
	var productIds="";
/*	$.each(MliSelected,function(i,v){
		//选中
		productIds += oTable.fnGetData(v).productId + ",";
		//var t=oTable.rows('.selected').data();
	});*/
	var path =sys.rootPath+url+'?productIds=' + productSelectedList + '&baseUri=' + $.url().attr('path');  
    $('#productForm').attr("action", path).submit();
});

$('#btnPrintTag').click(function(){
	var url = "/product/printProductTag.html";
	if(productSelectedList.length==0){
		layer.msg("你没有选择行", {
			icon : 0
		});
		return;
	}
	var products="";
	$.each(productSelectedList,function(i,v){
		//选中
		products += productSelectedList[i] + ",";
		//var t=oTable.rows('.selected').data();
	});
//	var path =sys.rootPath+url+'?productIds=' + productIds + '&baseUri=' + $.url().attr('path');
	$.post(sys.rootPath+'/product/printProductTag.html',{productIds:products},function(resp){
		var result = JSON.parse(resp);
		if(result.success==false){
			layer.msg('打印失败', {icon : 0});
		}else{
			layer.msg('打印成功', {icon : 0});
		}
	});
	
//    $('#productForm').attr("action", path).submit();
});


var dtGridColumns = [{
	id : 'productId',
	title : '选择',
	type : 'text',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header',
	resolution : function(value, record, column, grid, dataNo, columnNo) {
		 //当前页码
		 var nowPage = grid.pager.nowPage;
		// 获取每页显示的记录数(即: select框中的10,20,30)
		var pageSize = grid.pager.pageSize;
		// 获取排序字段
		var columnId = grid.sortParameter.columnId;
		// 获取排序方式 [0-不排序，1-正序，2-倒序]
		var sortType = grid.sortParameter.sortType;
			return '<input type="checkbox" onclick="onChecked('+value+')" class="productCheckBox" id="'+value+'"/>';
	    }
},{
	id : 'productId',
	title : '图片预览',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header',
	hideType : 'sm|xs',
	resolution : function(value, record, column, grid, dataNo, columnNo) {
		var url= sys.rootPath
		+ "/product/loadThumbnail.html?productId="
		+ value + '&baseUri='
		+ $.url().attr('path');
		return '<img id="thumbnail" src='+url+' alt="缩略图" height="120px" />'
	}
}, {
    id : 'productCode',
    title : '商品编码',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'factoryCode',
    title : '工厂编码',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'top',
    title : '口',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'bottom',
    title : '底',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'height',
    title : '高',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'volume',
    title : '容量',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'length',
    title : '外箱长',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'width',
    title : '外箱宽',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'packHeight',
    title : '外箱高',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'packingRate',
    title : '装箱率',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'cbm',
    title : 'CBM',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'gw',
    title : 'GW',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
	id : 'productId',
	title : '操作',
	type : 'number',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header',
	resolution : function(value, record, column, grid, dataNo, columnNo) {
		 //当前页码
		 var nowPage = grid.pager.nowPage;
		// 获取每页显示的记录数(即: select框中的10,20,30)
		var pageSize = grid.pager.pageSize;
		// 获取排序字段
		var columnId = grid.sortParameter.columnId;
		// 获取排序方式 [0-不排序，1-正序，2-倒序]
		var sortType = grid.sortParameter.sortType;
			var nav="/product/editUI.html";
			return '<a onclick="editProductLink(\''+nav+'\',\''+value+'\');" href="javascript:void(0);"><span class="btn btn-sm btn-primary">编辑</span></a>';
	    }
}];
var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    /*check : true,*/
    extraWidth : '37px',
    loadURL : sys.rootPath + '/product/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print',
    exportFileName : '商品信息',
    pageSize : 10,
    pageSizeLimit : [10, 20, 30],
    onCellClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
    	 var log = '<p>单元格事件触发。事件类型：'+e.type+'；触发单元格坐标：('+columnNo+','+dataNo+')；单元格内容：'+value+'。</p>';
    	     },
    	     /*onCheck : function(isChecked, record, grid, dataNo, row, extraCell, e){
    	    	         var log = '<p>复选事件触发。是否复选：'+isChecked+'；触发行坐标：'+dataNo+'。</p>';
    	    },*/onGridComplete : function(grid){
    	    	if(productSelectedList.length>0){//之前选中的要重新勾选上
    	    		for(var i=0;i<productSelectedList.length;i++){
    	    			$('#'+productSelectedList[i]).attr('checked','checked');
    	    		}
    	    	}
    	    }
};
var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
    grid.load();
    $("#btnSearch").click(productSearch);
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
        	productSearch();
        }
    };
    
});

//编辑
function editProductLink(nav,id){
	 //当前页码
	 var nowPage = grid.pager.nowPage;
	// 获取每页显示的记录数(即: select框中的10,20,30)
	var pageSize = grid.pager.pageSize;
	// 获取排序字段
	var columnId = grid.sortParameter.columnId;
	// 获取排序方式 [0-不排序，1-正序，2-倒序]
	var sortType = grid.sortParameter.sortType;
	webside.common.loadPage(nav + '?id=' +id + "&page="
				+ nowPage + "&rows=" + pageSize + "&sidx=" + columnId
				+ "&sord=" + sortType);
}

//编辑
function editProduct(nav){
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
		webside.common.loadPage(nav + '?id=' + rows[0].productId + "&page="
				+ nowPage + "&rows=" + pageSize + "&sidx=" + columnId
				+ "&sord=" + sortType);
	} else {
		layer.msg("你没有选择行或选择了多行数据", {
			icon : 0
		});
	}
}
//自定义查询
function productSearch() {
    grid.parameters = new Object();
    grid.parameters['productCode'] = $("#searchKey").val();//商品中文名称
    grid.parameters['parentId'] = $("#parentId").val();//商品大类Id
    grid.refresh(true);
}


