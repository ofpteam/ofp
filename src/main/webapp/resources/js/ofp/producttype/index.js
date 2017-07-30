var selectId = -1;
//绑定数据到tree
var url = sys.rootPath + "/producttype/list.html";
$.ajax({
	type : "post",
	url : url,
	async : false,
	dataType : "json",
	success : function(data) {
		$('#tree').treeview({
			data : data,
			levels : 1,// 只展开1级
			onNodeSelected : function(event, data) {
				selectId = data['id'];// 获取选中node的id
			}

		});
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		alert("请求失败！");
	}
});



$('#btnAdd').click(function() {
	webside.common.loadPage('/producttype/addUI.html?id=' + selectId);
})
$('#btnEdit').click(function() {
	debugger;
	if (selectId > 0) {
		webside.common.loadPage('/producttype/editUI.html?id=' + selectId);
	} else {
		layer.msg("你没有选择行", {
			icon : 0
		});
	}
})


