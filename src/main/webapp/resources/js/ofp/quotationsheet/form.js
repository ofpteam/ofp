
//绑定客户
		$.post(sys.rootPath + "/quotationsheet/getCustomers.html", function(
				resp) {//获取所有客户列表
			customers = JSON.parse(resp).data;
			if (customers != null) {
				$.each(customers, function(index, value) {
					$("#customerSelect").append(
							'<option value='+value.customerId+'>'
									+ value.customerName + '</option>')
				});
				if($('#customerId').val()!=undefined){//反现
					$("#customerSelect option[value='"+$('#customerId').val()+"']").attr("selected","selected");  
				}
				$("#customerSelect").chosen().change(function(option) {
					var selectCustomerId = $("#customerSelect").val();
					 $.each(customers, function(index,v) {
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
$.post(sys.rootPath + "/product/allList.html", function(
		resp) {
	var result=JSON.parse(resp);
	if(result.success==true){
		$.each(result.data,function(index,value){
			$("#productSelect").append('<option value='+value.productId+'>'
					+value.productType.cnName+"-"+ value.cnName + '</option>');
			/*样式会变？？？*/
			/*$("#productSelect").chosen().change(function(option) {
			});*/
		});
	}
});
//打开模态框
$('#btnOpenModal').click(function(){
	$('#myModal').modal('show');
	
});
// 是否整数
function isInteger(obj) {
	 return obj%1 === 0
}
//导出excel
function exportQuotationSheet(url,exportType){
	if($('#quotationSheetId').val()!=undefined){
		webside.common.loadPage(url+'?quotationSheetId='+$('#quotationSheetId').val()+'&exportType='+exportType);
	}else{
		layer.msg('新增的报价单不能导出', {icon : 0});
	}
}
	
$('#btnDelteRow').click(function(){
	debugger;
var anSelected=	oTable.$('tr.selected');
if ( anSelected.length !== 0 ) {
    oTable.fnDeleteRow( anSelected[0] );
}
	//oTable.row('.selected').remove().draw( false );
});
//添加商品明细
$('#btnAddRows').click(function(){
	if($('#productSelect').val()==""){
		layer.msg('请选择商品', {icon : 0});
		return false;
	}
	if($('#packingRate').val()==""||isNaN($('#packingRate').val())||Number($('#packingRate').val())<=0){
		layer.msg('装箱率必须是数字', {icon : 0});
		return false;
	}
	if($('#number').val()==""||!isInteger($('#number').val())||Number($('#number').val())<=0){
		layer.msg('数量必须是正整数', {icon : 0});
		return false;
	}if($('#packNum').val()==""||!isInteger($('#packNum').val())||Number($('#packNum').val())<=0){
		layer.msg('箱数必须是正整数', {icon : 0});
		return false;
	}
	$.post(sys.rootPath+'/quotationsheet/findProductById.html',{productId:$('#productSelect').val()},function(resp){
		var v=JSON.parse(resp);
		if(v.success==true){
			$('#example').dataTable().fnAddData(
							[ {productId:$('#productSelect').val(),buyPrice:v.data.buyPrice,
								usdPrice:v.data.usdPrice, unit:v.data.unit, top:v.data.top,
								bottom:v.data.bottom, height:v.data.height,
								weight:v.data.weight, volume:v.data.volume,
								packing:v.data.packing,packingRate:$('#packingRate').val(),
								number:$('#number').val(), packNum:$('#packNum').val(), totalcbm:Number($('#number').val())*v.data.cbm,
								totalGw:Number($('#number').val())*v.data.gw,quotationSheetId:$('#quotationSheetId').val()==undefined?'':$('#quotationSheetId').val() }]);
			$('#myModal').modal('hide');
		
		}else{
			layer.msg(v.message, {icon : 0});
		}
	
	
	})
	

	
});
// 绑定数据到tree
/*var url = sys.rootPath + "/producttype/list.html";
$.ajax({
	type : "post",
	url : url,
	async : false,
	dataType : "json",
	success : function(data) {
		debugger;
		$('#searchTree').treeview({
			data :JSON.parse(data),
			levels : 1,// 只展开1级
			onNodeSelected : function(event, data) {
				selectId = data['id'];// 获取选中node的id
			},
			showCheckbox:true,  
			onNodeChecked:nodeChecked ,  
		    onNodeUnchecked:nodeUnchecked,
		    showTags: true
		});
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		alert("请求失败！");
	}
});
var nodeCheckedSilent = false;
function nodeChecked (event, node){
    if(nodeCheckedSilent){
        return;
    }
    nodeCheckedSilent = true;
    checkAllParent(node);
    checkAllSon(node);
    nodeCheckedSilent = false;
}

var nodeUncheckedSilent = false;
function nodeUnchecked  (event, node){
    if(nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    uncheckAllParent(node);
    uncheckAllSon(node);
    nodeUncheckedSilent = false;
}

//选中全部父节点
function checkAllParent(node){
    $('#searchTree').treeview('checkNode',node.nodeId,{silent:true});
    var parentNode = $('#searchTree').treeview('getParent',node.nodeId);
    if(!("nodeId" in parentNode)){
        return;
    }else{
        checkAllParent(parentNode);
    }
}
//取消全部父节点
function uncheckAllParent(node){
    $('#searchTree').treeview('uncheckNode',node.nodeId,{silent:true});
    var siblings = $('#searchTree').treeview('getSiblings', node.nodeId);
    var parentNode = $('#searchTree').treeview('getParent',node.nodeId);
    if(!("nodeId" in parentNode)) {
        return;
    }
    var isAllUnchecked = true;  //是否全部没选中
    for(var i in siblings){
        if(siblings[i].state.checked){
            isAllUnchecked=false;
            break;
        }
    }
    if(isAllUnchecked){
        uncheckAllParent(parentNode);
    }

}

//级联选中所有子节点
function checkAllSon(node){
    $('#searchTree').treeview('checkNode',node.nodeId,{silent:true});
    if(node.nodes!=null&&node.nodes.length>0){
        for(var i in node.nodes){
            checkAllSon(node.nodes[i]);
        }
    }
}
//级联取消所有子节点
function uncheckAllSon(node){
    $('#searchTree').treeview('uncheckNode',node.nodeId,{silent:true});
    if(node.nodes!=null&&node.nodes.length>0){
        for(var i in node.nodes){
            uncheckAllSon(node.nodes[i]);
        }
    }
}
*/

    /* Init the table */
 // 编辑时反现子表
    var url= sys.rootPath+"/quotationsheet/getSubSheet.html";
    if($('#quotationSheetId').val()!=undefined){//编辑时
    	url+="?id="+$('#quotationSheetId').val();
    }
    		 oTable = $('#example').dataTable( {
    			  	"sScrollY": "400px",
    			    "sScrollX": "150%", //横向滚动条       
    			    "bScrollCollapse": true,
    			    "bProcessing": true, // 显示是否加载
    			    "bStateSave": true,
    			    "bSort": false,
    			    "bServerSide": false,
    			    "sAjaxSource": url , //后台地址
    			    "bAutoWidth": true,
    			    "bPaginate": true, //翻页功能  
    			    "iDisplayLength":5,
    			    "iDisplayStart":0,
    			    "aLengthMenu": [5,20],
    				"fnServerData": function (sSource, aoData, fnCallback, oSettings) {
    			        oSettings.jqXHR = $.ajax({
    			            "dataType": 'json',
    			            "contentType": "application/json; charset=utf-8",
    			            "url": sSource,
    			            "success": function (modellist) {
    			                fnCallback(modellist); //string to json
    			                $('#example tbody').on( 'click', 'tr', function () {
    			                    if ( $(this).hasClass('selected') ) {
    			                        $(this).removeClass('selected');
    			                    }
    			                    else {
    			                    	oTable.$('tr.selected').removeClass('selected');
    			                        $(this).addClass('selected');
    			                    }
    			                } );
    			            },
    			            "error": function (resp) {
    			                alert("错误代码：" + resp.status + "," + "错误信息：" + resp.readyState);
    			            }
    			        });
    			    }/*, "aoColumnDefs" :[
    				                         { "bVisible": false, "aTargets": [0] }第一列隐藏
    			                               ]*/,
    			      "fnRowCallback" : function(nRow, aData, iDisplayIndex) {
    					/* Append the grade to the default row class name */
    					/*if (aData[4] == "A") {*/
    			    	
    						//$('td:eq(15)', nRow).html('<button name="btndeleteRow" class="btn btn-grey btn-xs"><i class="icon-trash icon-2x icon-only">删除</i></button>');
    						//$('td:eq(10)', nRow).html('<input type="number" value="'+aData.number+'"></input>');
    						//$('td:eq(11)', nRow).html('<input type="number" value="'+aData.packNum+'"></input>');
    					/*}*/
    				},                            
    		        "aoColumns": [
    							{ "mDataProp": "productId" },
    							{ "mDataProp": "buyPrice" },
    							{ "mDataProp": "usdPrice" },
    							{ "mDataProp": "unit" },
    							{ "mDataProp": "top" },
    							{ "mDataProp": "bottom" },
    							{ "mDataProp": "height" },
    							{ "mDataProp": "weight" },
    							{ "mDataProp": "volume" },
    							{ "mDataProp": "packing" },
    							{ "mDataProp": "packingRate" },
    							{ "mDataProp": "number" },
    							{ "mDataProp": "packNum" },
    							{ "mDataProp": "totalcbm" },
    							{ "mDataProp": "totalGw" },
    							{ "mDataProp": "quotationSheetId" }
    		                ]
    		} );
    		
    			
               
    		     

		$(".datepicker").datepicker({
			language : "zh-CN",
			autoclose : true,//选中之后自动隐藏日期选择框
			clearBtn : true,//清除按钮
			todayBtn : true,//今日按钮
			format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});

//提交
function validateForm(){
$('#quotationsheetForm').validate({
    errorElement : 'div',
    errorClass : 'help-block',
    focusInvalid : false,
    ignore : "",
    rules : {
    	quotationDate:{
    		required : true
        },currency:{
        	required : true,
        	maxlength:20
        },expirationDate:{
        	required : true,
        	digits:true
        },payMode:{
        	required : true,
        	maxlength:20
        },resource:{
        	required : true,
        	maxlength:50
        },dest:{
        	required : true,
        	maxlength:50
        },deliveryDate:{
        	required : true,
        	digits:true
        },insuranceCost:{
        	required : true,
        	number:true
        },foreignGreight:{
        	required : true,
        	number:true
        },homeGreight:{
        	required : true,
        	number:true
        },operationCost:{
        	required : true,
        	number:true
        },commission:{
        	required : true,
        	number:true
        },rebate:{
        	required : true,
        	number:true
        }/*,totalCbm:{计算的出来
        	required : true,
        	digits:true
        }*/,interestMonth:{
        	required : true,
        	digits:true,
        	range:[0,99]
        },valueAddedTaxRate:{
        	required : true,
        	number:true
        },taxRebateRate:{
        	required : true,
        	number:true
        },exchangeRate:{
        	required : true,
        	number:true
        },rate:{
        	required : true,
        	number:true
        }
    },
    messages : {
    },
    highlight : function(e) {
        $(e).removeClass('has-info').addClass('has-error');
    },
    success : function(e) {
        $(e).removeClass('has-error').addClass('has-success');
        $(e).remove();
    },
    errorPlacement : function(error, element) {
        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
            var controls = element.closest('div[class*="col-"]');
            if (controls.find(':checkbox,:radio').length > 1)
                controls.append(error);
            else
                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
        } else if (element.is('.select2')) {
            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
        } else if (element.is('.chosen-select')) {
            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
        } else
            error.insertAfter(element.parent());
    },
    submitHandler : function(form) {
        var quotationSheetId = $("#quotationSheetId").val();
    	var items=$('#example').dataTable().fnGetData();
    	if(items.length>0){
    		$('#quotationSubSheetEntities').val(JSON.stringify(items));
    		$('#quotationsheetForm').serializeArray();
    	     var url = "";
    	        if (quotationSheetId != undefined) {
    	        	url ='/quotationsheet/edit.html';
    	        } else {
    	        	url = '/quotationsheet/add.html';
    	        }
    	       webside.common.commit('quotationsheetForm', url, '/quotationsheet/listUI.html');
    	}else{
    		layer.msg('请添加至少一种商品', {icon : 0});
    		return false;
    	}
   
    }
});
}