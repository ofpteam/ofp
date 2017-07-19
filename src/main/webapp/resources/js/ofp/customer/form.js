//提交
function validateForm(){
$('#customerForm').validate({
    errorElement : 'div',
    errorClass : 'help-block',
    focusInvalid : false,
    ignore : "",
    rules : {
    	CUSTOMER_NAME : {
            required : true
        },TELEPHONE:{
        	  required : true
        }
    },
    messages : {
    	CUSTOMER_NAME : {
            required : "请填写客户名称",
        },TELEPHONE:{
        	 required : "请填写电话",
        }
    },
    highlight : function(e) {
        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
    },
    success : function(e) {
        $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
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
        var userId = $("#CUSTOMER_ID").val();
        var url = "";
        if (userId != undefined) {
            url = '/ocustomer/edit.html';
        } else {
            url = '/ocustomer/add.html';
        }
        webside.common.commit('ocustomerForm', url, '/ocustomer/listUI.html');
    }
});
}