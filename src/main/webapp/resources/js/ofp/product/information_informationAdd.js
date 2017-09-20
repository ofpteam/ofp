
$(function() {
	/* 下拉多选 */
	$(".chosen-select").chosen();

	$('#chosen-multiple-style').on('click', function(e) {
		var target = $(e.target).find('input[type=radio]');
		
		var which = parseInt(target.val());
		if (which == 2)
			$('#role_code').addClass('tag-input-style');
		else
			$('#role_code').removeClass('tag-input-style');
	});

	/* 时间控件 */
	var d1 = new Date();
	var d2 = new Date(d1);
	d2.setDate(d1.getDate()+1);
	$(".datepicker").datepicker({
		language : "zh-CN",
		startDate: d2,//最小日期
		autoclose : true,// 选中之后自动隐藏日期选择框
		clearBtn : true,// 清除按钮
		format : "yyyy-mm-dd "// 日期格式，详见
								// http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});

	$('.form_datetime').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		autoclose : 1,
		todayBtn : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		clearTxt : '清空',
		clearBtn : true,
		format : "yyyy-mm-dd hh:ii"
	});

	/* 文本编辑器 */
	$('#content').ace_wysiwyg({
		toolbar : [ 'font', null, 'fontSize', null, {
			name : 'bold',
			className : 'btn-info'
		}, {
			name : 'italic',
			className : 'btn-info'
		}, {
			name : 'strikethrough',
			className : 'btn-info'
		}, {
			name : 'underline',
			className : 'btn-info'
		}, null, {
			name : 'insertunorderedlist',
			className : 'btn-success'
		}, {
			name : 'insertorderedlist',
			className : 'btn-success'
		}, {
			name : 'outdent',
			className : 'btn-purple'
		}, {
			name : 'indent',
			className : 'btn-purple'
		}, null, {
			name : 'justifyleft',
			className : 'btn-primary'
		}, {
			name : 'justifycenter',
			className : 'btn-primary'
		}, {
			name : 'justifyright',
			className : 'btn-primary'
		}, {
			name : 'justifyfull',
			className : 'btn-inverse'
		}, null, {
			name : 'createLink',
			className : 'btn-pink'
		}, {
			name : 'unlink',
			className : 'btn-pink'
		}, null, {
			name : 'insertImage',
			className : 'btn-success'
		}, null, 'foreColor', null, {
			name : 'undo',
			className : 'btn-grey'
		}, {
			name : 'redo',
			className : 'btn-grey'
		} ],
		'wysiwyg' : {
			fileUploadError : showErrorAlert
		}
	}).prev().addClass('wysiwyg-style2');

	function showErrorAlert(reason, detail) {
		var msg = '';
		if (reason === 'unsupported-file-type') {
			msg = "Unsupported format " + detail;
		} else {
			console.log("error uploading file", reason, detail);
		}
		$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'
						+ '<strong>File upload error</strong> '+ msg+ ' </div>').prependTo('#alerts');
	}

});

/* 附件与文本之间转换 */
$("#item_type").change(function() {
	if (this.value == 1) {
		$("#accessory").show();
		$("#text").hide();
		var path =root;
		var controller = '/mvc/informationUpload.do';
		var button = 'ctlBtn';// 上传附件按钮Id
		uploaderHelper.init(path, controller, button);
	
	} else if (this.value == 2) {
		$("#text").show();
		$("#accessory").hide();
	} else {
		$("#accessory").hide();
		$("#text").hide();
	}
});

// 选择目录
function selectOrg() {
	var url =root+ '/mvc/directoryList.do';
	$.post(url, function(content) {
		openInfoDialog("目录列表", content, 500, 400, function rankSave() {
			if ($('#tree3').treeview('getSelected', null).length > 0) {
				var id = $('#tree3').treeview('getSelected', null)[0].id;
				var text = $('#tree3').treeview('getSelected', null)[0].text;
				// var name = $("#directPath").val();
				$("#catalog_id").val(id);
				$("#catalogName").val(text);
				setHash('${pageContext.request.contextPath}');
			}
		});
	});
};

//检验
function formValidate(){
	$.validator.addMethod("isCatalogSelected",function(value,element,arg){//检测是否选择类型，如果是附件则判断至少一个附件，如果是内容则判断是否为空
		var msg_catalog_id = $('#catalogName').val();
		if (msg_catalog_id == ""||msg_catalog_id == null) {
			return false;
		}else{
			return true;
		}
	},"*目录路径必选");
	$.validator.addMethod("isOperExist",function(value,element,arg){//检测是否选择类型，如果是附件则判断至少一个附件，如果是内容则判断是否为空
		var msg_role_code = $('#role_code').val();
		if (msg_role_code == null||msg_role_code=="") {
			return false;
		}else{
			return true;
		}
	},"*查看权限必填");
	$.validator.addMethod("isSelected",function(value,element,arg){//是否选中
		var msg_item_type = $('#item_type').find("option:selected")[0].index;
		if (msg_item_type == 0) {
			return false;
		}else{
			return true;
		}
	},"*采编类型必填");
	$.validator.addMethod("isUploaded",function(value,element){//检测是否选择类型，如果是附件则判断至少一个附件，如果是内容则判断是否为空
		var msg_item_type = $('#item_type').find("option:selected")[0].index;
		 if (msg_item_type == 1) {// 附件
			var filesCount =uploaderHelper.getFileIds().length;
			if (filesCount == 0) {
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		} 
	},"*请先添加附件");
	$.validator.addMethod("isContentEmpty",function(value,element){//检测是否选择类型，如果是附件则判断至少一个附件，如果是内容则判断是否为空
		var msg_item_type = $('#item_type').find("option:selected")[0].index;
		 if (msg_item_type == 2) {// 文档
			var filesCount =uploaderHelper.getFileIds().length;
			if ($("#content")[0].innerHTML =="") {
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	},"*文档不能为空");
	return $('#form_informationAdd').validate({
		rules:{
			catalog_path:{
				isCatalogSelected:true
			},
			role_code:{
				isOperExist:true
			},
			title:{
				required:true,
				maxlength:20
			},item_type:{
				isSelected:true,
				isUploaded:true,
				isContentEmpty:true
			},key_words:{
				required:true
			},use_date:{
				required:true
			},publish_time:{
				required:true
			}
		},messages:{
			title:{
				required:"*必填",
				maxlength:"长度超过20"
			},key_words:{
				required:"*必填"
			},catalog_path:{
				required:"*必填"
			},use_date:{
				required:"*必填(失效日期不小于当前日期)"
			},publish_time:{
				required:"*必填"
			},role_code:{
				required:"*必填"
			}
		}
	});
}

// 发布知识点
function release() {
	debugger
	if(!formValidate().form())
		return false;
	var title=$("#title").val();
	var reg = /^[0-9a-zA-Z_\u4e00-\u9fa5]+$/;
	var use_date = $.trim($("#use_date").val());
	var publish_time = $("#publish_time").val();
	if(!reg.test(title)){
		$("#name_msg").text("*标题只能输入汉字，字母，数字和下划线！");
		return false;
	}
	
	
	if(use_date != "" && publish_time != ""){
		var arr = publish_time.split(" ");
		if(use_date <= arr[0]){
			f_alert('失效日期必须大于发布时间');
			return false;
		}
	}
	var obj = new Object();
	obj = $('.operId').val();
	var testData = $("#form_informationAdd").serialize();
	if($('#item_type').find("option:selected")[0].index==1){//附件
	//var files = uploaderHelper.getFiles();// 上传附件
	var fileIds = uploaderHelper.getFileIds();// 上传附件Id
	if($('#thelist a').length == 0){
		f_alert('请先上传附件!');
		return false;
	}
	var testData = $("#form_informationAdd").serialize();
	testData += "&catalog_id="+$('#catalog_id').val()+"&attach_path="+uploaderHelper.getFileIds()+"&content=" + encodeURIComponent($("#content").html()) + "&operId=" + obj;
	}
	else{
		testData +=  "&catalog_id="+$('#catalog_id').val()+"&content=" + encodeURIComponent($("#content").html()) + "&operId=" + obj;
	}
	$.ajax({
		type : "POST",
		url : root+"/mvc/linkAddInformationSave.do?value=1&role_code="+obj,
		data : testData,
		async : false,
		dataType : 'json',
		success : function(data) {
			if (data.msg == null) {
				window.location = root+"/mvc/queryInformationList.do";
			} else {
				f_alert("保存失败:" + data.msg);
			}
		},
		error : function(msg) {
			var exp = "分配出错 " + msg;
			f_alert("保存失败:" + exp);
		}
	});
}

// 保存知识点
function gosave() {
	if(!formValidate().form())
		return false;
	var title=$("#title").val();
	var reg = /^[0-9a-zA-Z_\u4e00-\u9fa5]+$/;
	var use_date = $("#use_date").val();
	var publish_time = $("#publish_time").val();
	if(!reg.test(title)){
		$("#name_msg").text("*标题只能输入汉字，字母，数字和下划线！");
		return false;
	}
	
	if(use_date != "" && publish_time != ""){
		var arr = publish_time.split(" ");
		if(use_date <= arr[0]){
			f_alert('失效日期必须大于发布时间');
			return false;
		}
	}
	var obj = new Object();
	obj = $('.operId').val();
	var testData = $("#form_informationAdd").serialize();
   
	if($('#item_type').find("option:selected")[0].index==1){//附件
	//var files = uploaderHelper.getFiles();// 上传附件
	var fileIds = uploaderHelper.getFileIds();// 上传附件Id
	if($('#thelist a').length == 0){
		f_alert('请先上传附件!');
		return false;
	}
	var testData = $("#form_informationAdd").serialize();
	testData += "&catalog_id="+$('#catalog_id').val()+"&attach_path="+uploaderHelper.getFileIds()+"&content=" + encodeURIComponent($("#content").html()) + "&operId=" + obj;
	}
	else{
		testData += "&catalog_id="+$('#catalog_id').val()+"&content=" + encodeURIComponent($("#content").html()) + "&operId=" + obj;
	}
	$.ajax({
		type : "POST",
		url : root+"/mvc/linkAddInformationSave.do?value=2&role_code="+obj,
		data : testData,
		async : false,
		dataType : 'json',
		success : function(data) {
			if (data.msg == null) {
				window.location =root+ "/mvc/queryInformationList.do";
			} else {
				f_alert("保存失败:" + data.msg);
			}
		},
		error : function(msg) {
			var exp = "分配出错 " + msg;
			f_alert("保存失败:" + exp);
		}
	});
}


/*开始上传选择附件
$("#ctlBtn").click(function(){
	debugger
	var fileIds = $("#picker").val();;// 上传附件Id
	if(fileIds == ""){
		f_alert("请先上传附件！")
	}
});*/


/* 返回 */
function goback() {
	window.location = root+"/mvc/queryInformationList.do";
}