var dtGridColumns = [ {
	id : 'customerId',
	title : '编号',
	type : 'number',
	columnClass : 'text-center',
	hideType : 'xs',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'customerName',
	title : '客户姓名',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'telephone',
	title : '电话',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'country',
	title : '国家',
	type : 'string',
	columnClass : 'text-center',
	headerClass : 'dlshouwen-grid-header'
}, {
	id : 'description',
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
function delCustomer(nav, callback) {
	debugger;
	var rows = grid.getCheckedRecords();
	if (rows.length >= 1) {
		layer.confirm('确认删除吗？', {
			icon : 3,
			title : '删除提示'
		}, function(index, layero) {
			var delete_ids = [];
            $.each(rows, function(index, value) {
            	delete_ids.push(this.customerId);
            });
            
			$.ajax({
				type : "POST",
				url : sys.rootPath + nav,
				data : {
					"ids" : delete_ids.join(',')
				},
				dataType : "json",
				success : function(resultdata) {
					if (resultdata.success) {
						layer.msg(resultdata.message, {
							icon : 1
						});
						if (callback) {
							callback();
						}
					} else {
						layer.msg(resultdata.message, {
							icon : 5
						});
					}
				},
				error : function(errorMsg) {
					layer.msg('服务器未响应,请稍后再试', {
						icon : 3
					});
				}
			});
			layer.close(index);
		});
	} else {
		layer.msg("你没有选择行或选择了多行数据", {
			icon : 0
		});
	}
}
// 自定义查询
function customSearch() {
	grid.parameters = new Object();
	grid.parameters['CUSTOMER_NAME'] = $("#searchKey").val();
	grid.refresh(true);
}
