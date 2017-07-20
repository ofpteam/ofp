var tree = [ {
	text : "杯子",
	id : 1,
	// state : {
	// checked: true,
	// disabled: true,
	// expanded: false,
	// selected: true
	// },
	nodes : [ {
		id : 11,
		text : "压杯"
	}, {
		id : 12,
		text : "吹杯"
	}, {
		id : 13,
		text : "啤酒杯"
	}, {
		id : 14,
		text : "小把杯"
	} ]
}, {
	// state : {
	// checked: true,
	// disabled: true,
	// expanded: false,
	// selected: true
	// },
	text : "服装",
	id : 2,
	nodes : [ {
		id : 21,
		text : "压瓶"
	}, {
		id : 22,
		text : "吹瓶"
	}, {
		id : 23,
		text : "啤酒瓶"
	}, {
		id : 24,
		text : "小把瓶"
	} ]
} ];
var selectId = -1;
$('#tree').treeview({
	data : tree,
	levels : 1,// 只展开1级
	onNodeSelected : function(event, data) {
		selectId = data['id'];// 获取选中node的id
	}

});

$('#btnAdd').click(function() {
	if (selectId > 0) {
		webside.common.loadPage('/producttype/addUI.html?id=' + selectId);
	} else {
		layer.msg("你没有选择行", {
			icon : 0
		});
		/*
		 * layer.confirm("请选中一行", { icon : 3, title : '提示', btn: [] //按钮 });
		 */
	}
})
$('#btnEdit').click(function() {
	if (selectId > 0) {
		webside.common.loadPage('/producttype/editUI.html?id=' + selectId);
	} else {
		layer.msg("你没有选择行", {
			icon : 0
		});
		/*
		 * layer.confirm("请选中一行", { icon : 3, title : '提示', btn: [] //按钮 });
		 */
	}
})