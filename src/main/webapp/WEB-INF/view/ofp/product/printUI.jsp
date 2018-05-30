<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
.noform {
	padding:7px 0px 0px 12px;
	font-size: 14px;
}
.logo{
	font-size:18px;
	font-weight:bold;
	font-family: Tahoma;
}
</style>
<script type="text/javascript">
	function preview(oper) { 
		if(oper<10){
		  var newWin=window.open('about:blank', '', '');   
		  bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
		  sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
		  eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
		  prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
		  prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html  
		  newWin.document.write(prnhtml);   
		  newWin.document.location.reload();
		  newWin.print();
		}
	}
</script>
<div class="page-header noprint">
	<h1>
		打印预览
	</h1>
</div>
<div class="row" id="printDiv" style="margin-top:0px;">
	<!--startprint1-->
	<style media="print">
	@page {
		size:40mm 30mm;
	}
	.printForm {
		padding:1px 0px 0px 12px;
		font-size: 10px;
	}
	.logo{
		font-size:16px;
		font-weight:bold;
		font-family: Tahoma;
	}
	.noprint{display:none;}
	.pageNext{page-break-after: always;}
	</style>
	<script text/javascript>
		$(function() {
			var loadLogourl = sys.rootPath
					+ "/product/loadLogo.html?baseUri="
					+ $.url().attr('path');
			$("img[name='smallLogo']").each(function(){
				$(this).attr("src",loadLogourl);
			});
		});
	</script>
	<c:forEach var="productTagBean" items="${productTagBeans }" varStatus="status">
		<div class="col-sm-2 <c:if test='${!status.last}'>pageNext</c:if>" style="padding-right:0px;padding-left:10px;padding-top:0px;;margin-right:0px;">
			<form class="form-horizontal" style="line-height:1;" role="form" method="post">
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
					  <div class="col-sm-12 noform printForm logo" style="text-align:center;">
				      		ALIC
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.artNo }
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.facNo }
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.tbh }
				      </div>
				   </div> 
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.weightAndVol }
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.meas }
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.gw }
				      </div>
				   </div>
				   <div class="form-group" style="padding:0;margin:0 0 0 0;">
				      <div class="col-sm-12 noform printForm">
				      	${productTagBean.qcAndCbm }
				      </div>
				   </div>
			</form>
		</div>
	</c:forEach>
	<!--endprint1-->
</div>
<div class="center noprint" style="padding-right:5px;padding-top:15px;">
	<button id="btnAdd" type="button" onclick="preview(1);" class="btn btn-success btn-sm">
		打印
	</button>
</div>