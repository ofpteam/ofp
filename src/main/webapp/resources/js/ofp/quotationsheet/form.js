
//提交
function validateForm(){
$('#quotationsheetForm').validate({
    errorElement : 'div',
    errorClass : 'help-block',
    focusInvalid : false,
    ignore : "",
    rules : {
    	PRODUCT_CODE : {
            required : true
        },FACTORY_CODE:{
        	  required : true
        },UNIT:{
        	  required : true
        }
    },
    messages : {
    	PRODUCT_CODE : {
            required : "请填写商品编码",
        },FACTORY_CODE:{
        	 required : "请填写工厂编码",
        },UNIT:{
        	 required : "请填写单位",
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
        var userId = $("#userId").val();
        var url = "";
        if (userId != undefined) {
            url = '/quotationsheet/edit.html';
        } else {
            url = '/quotationsheet/add.html';
        }
        webside.common.commit('quotationsheetForm', url, '/quotationsheet/listUI.html');
    }
});
}