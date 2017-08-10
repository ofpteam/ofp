jQuery.validator.addMethod("positiveinteger", function(value, element) {
   var aint=parseInt(value);	
    return aint>0&& (aint+"")==value;   
  }, "Please enter a valid number.");   
//提交
function validateForm(){
$('#interestRateForm').validate({
    errorElement : 'div',
    errorClass : 'help-block',
 /*
	 * focusInvalid : false, ignore : "",
	 */
    rules : {
    	rate : {
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
        var interestRateId = $("#interestRateId").val();
        var url = "";
        if (interestRateId != undefined) {
            url = '/interestRate/edit.html';
        } else {
            url = '/interestRate/add.html';
        }
        webside.common.commit('interestRateForm', url, '/interestRate/listUI.html');
    }
});
}
