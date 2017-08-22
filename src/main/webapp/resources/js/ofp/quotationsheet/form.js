//添加商品选择大类后，商品也需要变
$('#productTypeSelect').change(function() {
	$.post(sys.rootPath + "/quotationsheet/getProductByProductTypeId.html", {productTypeId : $('#productTypeSelect').val()
	}, function(resp) {
		var result = JSON.parse(resp);
		$("#productSelect").empty();
		if(result.success==true){
			$.each(result.data, function(index, value) {
				$("#productSelect").append('<option value=' + value.productId + '>'
						+ value.productCode + '</option>');
			});
		}
	});
})
// 绑定客户
$.post(sys.rootPath + "/quotationsheet/getCustomers.html", function(resp) {// 获取所有客户列表
	customers = JSON.parse(resp).data;
	if (customers != null) {
		$.each(customers, function(index, value) {
			$("#customerSelect").append(
					'<option value=' + value.customerId + '>'
							+ value.customerName + '</option>')
		});
		if ($('#customerId').val() != undefined) {// 反现
			$("#customerSelect option[value='" + $('#customerId').val() + "']")
					.attr("selected", "selected");
		}
		$("#customerSelect").chosen().change(function(option) {
			var selectCustomerId = $("#customerSelect").val();
			$.each(customers, function(index, v) {
				if (v.customerId == selectCustomerId) {
					$('#contacts').val(v.contacts);
					$('#telephone').val(v.telephone);
					$('#country').val(v.country);
					return false;
				}
			});
		});
	}

});
$.post(sys.rootPath + "/product/allList.html", function(resp) {
	var result = JSON.parse(resp);
	if (result.success == true) {
		$.each(result.data, function(index, value) {
			$("#productSelect").append(
					'<option value=' + value.productId + '>'
							+ value.productType.cnName + "-" + value.cnName
							+ '</option>');
			/* 样式会变？？？ */
			/*
			 * $("#productSelect").chosen().change(function(option) { });
			 */
		});
	}
});
// 打开模态框
$('#btnOpenModal').click(function() {
	$('#myModal').modal('show');

});
// 是否整数
function isInteger(obj) {
	return obj % 1 === 0
}
// 导出excel
function exportQuotationSheet(url, exportType) {
	if ($('#quotationSheetId').val() != undefined) {
		debugger;
		var path = sys.rootPath + url + '?quotationSheetId='
				+ $('#quotationSheetId').val() + '&exportType=' + exportType
				+ '&baseUri=' + $.url().attr('path');
		$('#exportExcelForm').attr("action", path).submit();
		// webside.common.loadPage(url+'?quotationSheetId='+$('#quotationSheetId').val()+'&exportType='+exportType);
	} else {
		layer.msg('新增的报价单不能导出', {
			icon : 0
		});
	}
}

$('#btnDelteRow').click(function() {
	var anSelected = oTable.$('tr.selected');
	if (anSelected.length !== 0) {
		oTable.fnDeleteRow(anSelected[0]);
	}
	// oTable.row('.selected').remove().draw( false );
});
// 添加商品明细
$('#btnAddRows').click(function() {
					if ($('#productSelect').val() == null||$('#productSelect').val() == "") {
						layer.msg('请选择商品', {
							icon : 0
						});
						return false;
					}
					$.post(sys.rootPath+ '/quotationsheet/findProductById.html',
									{
										productId : $('#productSelect').val()
									},
									function(resp) {
										var v = JSON.parse(resp);
										if (v.success == true) {
											$('#example').dataTable().fnAddData(
															[ {
																productId : $(
																		'#productSelect')
																		.val(),
																buyPrice : v.data.buyPrice,
																usdPrice : v.data.usdPrice,
																unit : v.data.unit,
																top : v.data.top,
																bottom : v.data.bottom,
																height : v.data.height,
																weight : v.data.weight,
																volume : v.data.volume,
																packing : v.data.packing,
																packingRate : v.data.packingRate,
																number : v.data.packingRate,
																packNum : 1,
																totalcbm : 1* v.data.cbm,
																totalGw : 1* v.data.gw,
																cbm : v.data.cbm,
																gw : v.data.gw,
																quotationSheetId : $('#quotationSheetId').val() == undefined ? '': $('#quotationSheetId').val()
															} ]);
											$('#myModal').modal('hide');

										} else {
											layer.msg(v.message, {
												icon : 0
											});
										}

									})

				});
// 绑定数据到tree
/*
 * var url = sys.rootPath + "/producttype/list.html"; $.ajax({ type : "post",
 * url : url, async : false, dataType : "json", success : function(data) {
 * debugger; $('#searchTree').treeview({ data :JSON.parse(data), levels : 1,//
 * 只展开1级 onNodeSelected : function(event, data) { selectId = data['id'];//
 * 获取选中node的id }, showCheckbox:true, onNodeChecked:nodeChecked ,
 * onNodeUnchecked:nodeUnchecked, showTags: true }); }, error :
 * function(XMLHttpRequest, textStatus, errorThrown) { alert("请求失败！"); } }); var
 * nodeCheckedSilent = false; function nodeChecked (event, node){
 * if(nodeCheckedSilent){ return; } nodeCheckedSilent = true;
 * checkAllParent(node); checkAllSon(node); nodeCheckedSilent = false; }
 * 
 * var nodeUncheckedSilent = false; function nodeUnchecked (event, node){
 * if(nodeUncheckedSilent) return; nodeUncheckedSilent = true;
 * uncheckAllParent(node); uncheckAllSon(node); nodeUncheckedSilent = false; }
 * 
 * //选中全部父节点 function checkAllParent(node){
 * $('#searchTree').treeview('checkNode',node.nodeId,{silent:true}); var
 * parentNode = $('#searchTree').treeview('getParent',node.nodeId);
 * if(!("nodeId" in parentNode)){ return; }else{ checkAllParent(parentNode); } }
 * //取消全部父节点 function uncheckAllParent(node){
 * $('#searchTree').treeview('uncheckNode',node.nodeId,{silent:true}); var
 * siblings = $('#searchTree').treeview('getSiblings', node.nodeId); var
 * parentNode = $('#searchTree').treeview('getParent',node.nodeId);
 * if(!("nodeId" in parentNode)) { return; } var isAllUnchecked = true;
 * //是否全部没选中 for(var i in siblings){ if(siblings[i].state.checked){
 * isAllUnchecked=false; break; } } if(isAllUnchecked){
 * uncheckAllParent(parentNode); }
 *  }
 * 
 * //级联选中所有子节点 function checkAllSon(node){
 * $('#searchTree').treeview('checkNode',node.nodeId,{silent:true});
 * if(node.nodes!=null&&node.nodes.length>0){ for(var i in node.nodes){
 * checkAllSon(node.nodes[i]); } } } //级联取消所有子节点 function uncheckAllSon(node){
 * $('#searchTree').treeview('uncheckNode',node.nodeId,{silent:true});
 * if(node.nodes!=null&&node.nodes.length>0){ for(var i in node.nodes){
 * uncheckAllSon(node.nodes[i]); } } }
 */

/* Init the table */
// 编辑时反现子表
var url = sys.rootPath + "/quotationsheet/getSubSheet.html";
if ($('#quotationSheetId').val() != undefined) {// 编辑时
	url += "?id=" + $('#quotationSheetId').val();
}
oTable = $('#example').dataTable({
	"sScrollY" : "400px",
	"sScrollX" : "150%", // 横向滚动条
	"bScrollCollapse" : true,
	"bProcessing" : true, // 显示是否加载
	"bStateSave" : true,
	"bSort" : false,
	"bServerSide" : false,
	"sAjaxSource" : url, // 后台地址
	"bAutoWidth" : true,
	"bPaginate" : true, // 翻页功能
	"iDisplayLength" : 5,
	"iDisplayStart" : 0,
	"aLengthMenu" : [ 5, 20 ],
	"fnServerData" : function(sSource, aoData, fnCallback, oSettings) {
		oSettings.jqXHR = $.ajax({
			"dataType" : 'json',
			"contentType" : "application/json; charset=utf-8",
			"url" : sSource,
			"success" : function(modellist) {
				fnCallback(modellist); // string to json
				 //当数量被修改
				$('#example tbody').on('click', 'tr', function() {
					// 多选
					/*
					 * if ( $(this).hasClass('selected') ) {
					 * $(this).removeClass('selected'); } else {
					 * $(this).addClass('selected');
					 * oTable.$('tr.selected').removeClass('selected');
					 * $(this).addClass('selected'); }
					 */
					// 单选
					if ($(this).hasClass('selected')) {
						$(this).removeClass('selected');
					} else {
						oTable.$('tr.selected').removeClass('selected');
						$(this).addClass('selected');
					}
				});
			},
			"error" : function(resp) {
				alert("错误代码：" + resp.status + "," + "错误信息：" + resp.readyState);
			}
		});
	} ,
	"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
		//数量
		 $('td:eq(11)', nRow).html('<input onchange="updateRow(this,'+iDisplayIndex+')" class="txtnumber"  type="number" value="'+aData.number+'"></input>');
		//箱数 
		 $('td:eq(12)', nRow).html('<input onchange="updateRow(this,'+iDisplayIndex+')" class="txtpackNum" type="number" value="'+aData.packNum+'"></input>');
		
		 /* Append the grade to the default row class name */
		/* if (aData[4] == "A") { */

		// $('td:eq(15)', nRow).html('<button name="btndeleteRow" class="btn
		// btn-grey btn-xs"><i class="icon-trash icon-2x
		// icon-only">删除</i></button>');
		// $('td:eq(10)', nRow).html('<input type="number"
		// value="'+aData.number+'"></input>');
		// $('td:eq(11)', nRow).html('<input type="number"
		// value="'+aData.packNum+'"></input>');
		/* } */
	},
	"aoColumns" : [ {
		"mDataProp" : "productId"
	}, {
		"mDataProp" : "buyPrice"
	}, {
		"mDataProp" : "usdPrice"
	}, {
		"mDataProp" : "unit"
	}, {
		"mDataProp" : "top"
	}, {
		"mDataProp" : "bottom"
	}, {
		"mDataProp" : "height"
	}, {
		"mDataProp" : "weight"
	}, {
		"mDataProp" : "volume"
	}, {
		"mDataProp" : "packing"
	}, {
		"mDataProp" : "packingRate"
	}, {
		"mDataProp" : "number"
	}, {
		"mDataProp" : "packNum"
	}, {
		"mDataProp" : "totalcbm"
	}, {
		"mDataProp" : "totalGw"
	}, {
		"mDataProp" : "cbm"
	}, {
		"mDataProp" : "gw"
	}, {
		"mDataProp" : "quotationSheetId"
	} ], "aoColumnDefs": [
	{ "bVisible": false, "aTargets": [ 15,16 ] }]
});

//自动计算装箱数量、箱数
function updateRow(txtbox,iDisplayIndex){
	var sData = oTable.fnGetData(iDisplayIndex);
	//判断是修改数量还是修改箱数
	//1）当改变数量：箱数=数量/装箱率
	//2）当改变箱数：数量=箱数*装箱率
	var className=$(txtbox)[0].className;
	if(className=="txtnumber"){//数量
		var number=Number($(txtbox).val());
		var packNum=number/sData.packingRate;
		
		oTable.fnUpdate(number, iDisplayIndex,11);
		oTable.fnUpdate(packNum, iDisplayIndex,12);
		oTable.fnUpdate(sData.cbm*packNum, iDisplayIndex,13);//总体积=单个cbm*箱数
		oTable.fnUpdate(sData.gw*packNum, iDisplayIndex,14);//总毛重=Gw*箱数
	}else{//箱数
		debugger;
		var packNum=Number($(txtbox).val());
		var number=packNum*sData.packingRate;
		oTable.fnUpdate(number, iDisplayIndex,11);
		oTable.fnUpdate(packNum, iDisplayIndex,12);
		oTable.fnUpdate(sData.cbm*packNum, iDisplayIndex,13);//总体积=单个cbm*箱数
		oTable.fnUpdate(sData.gw*packNum, iDisplayIndex,14);//总毛重=Gw*箱数
	}
}

$(".datepicker").datepicker({
	language : "zh-CN",
	autoclose : true,// 选中之后自动隐藏日期选择框
	clearBtn : true,// 清除按钮
	todayBtn: 'linked',//今日按钮
	format : "yyyy-mm-dd"// 日期格式，详见
							// http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
});

// 提交
function validateForm() {
	$('#quotationsheetForm').validate(
					{
						errorElement : 'div',
						errorClass : 'help-block',
						focusInvalid : false,
						ignore : "",
						rules : {
							quotationDate : {
								required : true
							},
							currency : {
								required : true,
								maxlength : 20
							},
							expirationDate : {
								required : true,
								digits : true
							},
							payMode : {
								required : true,
								maxlength : 20
							},
							resource : {
								required : true,
								maxlength : 50
							},
							dest : {
								required : true,
								maxlength : 50
							},
							deliveryDate : {
								required : true,
								digits : true
							},
							insuranceCost : {
								required : true,
								number : true
							},
							foreignGreight : {
								required : true,
								number : true
							},
							homeGreight : {
								required : true,
								number : true
							},
							operationCost : {
								required : true,
								number : true
							},
							commission : {
								required : true,
								number : true
							},
							rebate : {
								required : true,
								number : true
							}/*
								 * ,totalCbm:{计算的出来 required : true, digits:true }
								 */,
							interestMonth : {
								required : true,
								digits : true,
								range : [ 0, 99 ]
							},
							valueAddedTaxRate : {
								required : true,
								number : true
							},
							taxRebateRate : {
								required : true,
								number : true
							},
							exchangeRate : {
								required : true,
								number : true
							},
							rate : {
								required : true,
								number : true
							}
						},
						messages : {},
						highlight : function(e) {
							$(e).removeClass('has-info').addClass('has-error');
						},
						success : function(e) {
							$(e).removeClass('has-error').addClass(
									'has-success');
							$(e).remove();
						},
						errorPlacement : function(error, element) {
							if (element.is('input[type=checkbox]')
									|| element.is('input[type=radio]')) {
								var controls = element
										.closest('div[class*="col-"]');
								if (controls.find(':checkbox,:radio').length > 1)
									controls.append(error);
								else
									error.insertAfter(element.nextAll(
											'.lbl:eq(0)').eq(0));
							} else if (element.is('.select2')) {
								error
										.insertAfter(element
												.siblings('[class*="select2-container"]:eq(0)'));
							} else if (element.is('.chosen-select')) {
								error
										.insertAfter(element
												.siblings('[class*="chosen-container"]:eq(0)'));
							} else
								error.insertAfter(element.parent());
						},
						submitHandler : function(form) {
							var quotationSheetId = $("#quotationSheetId").val();
							var items = $('#example').dataTable().fnGetData();
							if (items.length > 0) {
								$('#quotationSubSheetEntities').val(
										JSON.stringify(items));
								$('#quotationsheetForm').serializeArray();
								var url = "";
								if (quotationSheetId != undefined) {
									url = '/quotationsheet/edit.html';
								} else {
									url = '/quotationsheet/add.html';
								}
								webside.common.commit('quotationsheetForm',
										url, '/quotationsheet/listUI.html');
							} else {
								layer.msg('请添加至少一种商品', {
									icon : 0
								});
								return false;
							}

						}
					});
}