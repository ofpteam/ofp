/*bootstrap.select用法*/
/*官网http://silviomoreto.github.io/bootstrap-select/examples/*/
//读取选中值
/*获取值， $("#XXX").val()即可 */
//读取展现
/*jQuery( '.selectpicker').selectpicker({
    liveSearch: true,
    size:8
});*/
//反现
/*var str='3,4,5,6';
var arr=str.split(',');
$('#usertype').selectpicker('val', arr);*/
//绑定客户
	//获取商品一级目录
debugger;
					$.post(sys.rootPath + "/roleProduct/queryAllProductTypeList.html", function(
							resp) {
						//绑定
						var result = JSON.parse(resp);
						if(result.success==true){
							$.each(result.data, function(index, value) {
								$(".selectpicker").append('<option value='+value.productTypeId+'>'
													+ value.cnName + '</option>')
							});
						 	jQuery( '.selectpicker').selectpicker({
					              liveSearch: true,
					              size:8
					          });
					}else{
						layer.msg('获取数据失败', {icon : 0});
					}
					});

		$.post(sys.rootPath + "/roleProduct/queryAllRoleList.html", function(
				resp) {//获取所有客户列表
			var roles = JSON.parse(resp).data;
			if (roles != null) {
				$.each(roles, function(index, value) {
					$("#roleId").append('<option value='+value.id+'>'
										+ value.name + '</option>')
				});
				$("#roleId").change(function(option) {
			
					var selectId = $("#roleId").val();
							//获取角色的权限进行反现
							$.post(sys.rootPath + "/roleProduct/getProductsByRoleId.html",{roldId:selectId}, function(
									resp) {
								var result = JSON.parse(resp);
								if(result.success==true){//获取到权限数据
									var array=new Array();
									$.each(result.data,function(i,value){
										array.push(value.product_Type_Id);
									});
									$('.selectpicker').selectpicker('val', array);//反现
								}else{
									$('.selectpicker').selectpicker('deselectAll');
								}
							});
						
						
				});
			}

		});
//添加商品权限明细
$('#btnSave').click(function(){
	var roleId = $("#roleId").val();
	var productTypes=$('.selectpicker').val();
	var productTypString=productTypes.toString();
	debugger;
	$.post(sys.rootPath+'/roleProduct/addRoleProductTypeBatch.html',{roleId:roleId,productTypString:productTypString},function(resp){
		var result = JSON.parse(resp);
		if(result.success==false){
			layer.msg('保存数据失败', {icon : 0});
		}
	});
});
