  fileNames=new Array();//已经上传的附件名称集合	  
//客户端删除附件
  function delfileFromClient(fileName,fileUrlId){
    	layer.confirm('是否删除该附件', {
            icon : 3,
            title : '提示'
            }, function() {
            	$('#'+fileUrlId).next().next().remove();//删除br
             	$('#'+fileUrlId).next().remove();//删除img
            	$('#'+fileUrlId).remove();//删除附件
            	fileNames.splice($.inArray(fileName,fileNames),1);
            	$('#attachmentNames').val(fileNames);
            	layer.closeAll('dialog');
            });
    }
jQuery(function() { 
var $ = jQuery,
        $list = $('#fileList'),
        // 优化retina, 在retina下这个值是2
        //ratio = window.devicePixelRatio || 1,
        // 缩略图大小
       // thumbnailWidth = 100 * ratio,
        //thumbnailHeight = 100 * ratio,
        // Web Uploader实例
        // 初始化Web auploader
      
        auploader = WebUploader.create({
        	// swf文件路径
            swf: sys.rootPath + '/resources/js/auploader.swf',
			// 文件接收服务端。
			server :sys.rootPath+'/product/uploadPicture.html',/*/mvc/informationUpload.do",
*/			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#Picker',
			// 不压缩
			compress:false,
			//fileNumLimit:1,//上传数量控制
			//fileSingleSizeLimit:2*1024*1024,
			// 只允许选择附件文件。
	/*		accept : {
				title: 'Images',
	            extensions: 'gif,jpg,jpeg,bmp,png',
	            mimeTypes: 'image/*'
			},*/
			duplicate:false//允许重复上传
    });
        if($('#attachmentNames').val()!=""){//返显附件
        	fileNames=$('#attachmentNames').val().split(',');
        	for(var i=0;i<fileNames.length;i++){
        		var url = sys.rootPath + "/product/downloadAttachmentByName.html?fileName="
    			+ fileNames[i]+ '&baseUri='+ $.url().attr('path');
    			$('#thelist').append('<a href='+url+' id='+fileNames[i].substring(0,36)+'>'+fileNames[i].substring(36,fileNames[i].length)+'</a><img src="'+sys.rootPath+'/resources/images/clear.png" onclick= delfileFromClient("'+fileNames[i]+'","'+fileNames[i].substring(0,36)+'")  id='+fileNames[i]+' /><br/>');
    			//document.getElementById(timestamp).href = url;
        	}
        	// 自定义参数
			auploader.options.formData.uid = fileNames;  
		}

 // 当有文件添加进来的时候，创建img显示缩略图使用
    auploader.on( 'fileQueued', function( file ) {
    	$('#thelist').append('<div id="' + file.id + '" class="item">' + '<h5 class="info">'
				+ file.name + '</h5>' + '<p class="state">等待上传...</p>'+ '</div>');
    });

 // 文件上传过程中创建进度条实时显示。    uploadProgress事件：上传过程中触发，携带上传进度。 file文件对象 percentage传输进度 Nuber类型
    auploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
        }
        $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功时候触发，给item添加成功class, 用样式标记上传成功。 file：文件对象，    response：服务器返回数据
    auploader.on( 'uploadSuccess', function( file,response) {
    	if(response.success==true){
    		fileNames.push(response.fileName);
    		var url = sys.rootPath + "/product/downloadAttachmentByName.html?fileName="
			+ response.fileName+ '&baseUri='+ $.url().attr('path');
			$('#' + file.id).children().remove();
			$('#' + file.id).append('<a id='+response.fileName.substring(0,36)+'>'+file.name+'</a><img src="'+sys.rootPath+'/resources/images/clear.png" onclick= delfileFromClient("'+fileNames[i]+'","'+response.fileName.substring(0,36)+'")  id='+file.name+' /><br/>');
			document.getElementById(response.fileName.substring(0,36)).href = url;
			// 自定义参数
			$('#attachmentNames').val(fileNames);
    	}else{
    		  $error.text(response.data);
    	}
      
    });

    // 文件上传失败                                file:文件对象 ， code：出错代码
    auploader.on( 'uploadError', function(file,code) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败!');
    });

    // 不管成功或者失败，文件上传完成时触发。 file： 文件对象
    auploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });
  //绑定提交事件
    $("#ctlBtn").click(function() {
        auploader.upload();   //执行手动提交
      
      });
  
});
