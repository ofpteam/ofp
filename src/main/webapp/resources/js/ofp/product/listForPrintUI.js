$(document).ready(function(){
	var url=sys.rootPath+"/product/getAllProducts.html";
	oTable = $('#example').dataTable( {
	    			  	"sScrollY": "200px",
	    			    "sScrollX": "100%", //横向滚动条       
	    			    "bScrollCollapse": true,
	    			    "bProcessing": true, // 显示是否加载
	    			    "bStateSave": true,
	    			    "bSort": false,
	    			    "bServerSide": false,
	    			    "sAjaxSource": url , //后台地址
	    			    "bAutoWidth": true,
	    			    "bPaginate": false, //翻页功能  
	    			    "iDisplayLength":5,
	    			    "iDisplayStart":0,
	    			    "aLengthMenu": [20,50,100],
	    				"fnServerData": function (sSource, aoData, fnCallback, oSettings) {
	    			        oSettings.jqXHR = $.ajax({
	    			            "dataType": 'json',
	    			            "contentType": "application/json; charset=utf-8",
	    			            "url": sSource,
	    			            "success": function (modellist) {
	    			                fnCallback(modellist); //string to json
	    			                $('#example tbody').on( 'click', 'tr', function () {
	    			                	//多选
	    			                	  if ( $(this).hasClass('selected') ) {
	      			                        $(this).removeClass('selected');
	      			                    }
	      			                    else {
	      			                    	  $(this).addClass('selected'); 
	      			                    }
	    			                	
	    			                } );
	    			            },
	    			            "error": function (resp) {
	    			                alert("错误代码：" + resp.status + "," + "错误信息：" + resp.readyState);
	    			            }
	    			        });
	    			    },
	    		        "aoColumns": [
	    							{ "mDataProp": "productId" },
	    							{ "mDataProp": "productCode" },
	    							{ "mDataProp": "cnName" },
	    							{ "mDataProp": "factoryCode" }
	    		                ]
	    		} );
})


$('#btnPrint').click(function(){
var MliSelected=	oTable.$('tr.selected');
$.each(MliSelected,function(i,v){
	var productIds=new Array();
	//选中
	productIds.push(oTable.fnGetData(v));
	
	//var t=oTable.rows('.selected').data();
	debugger;
});
});
