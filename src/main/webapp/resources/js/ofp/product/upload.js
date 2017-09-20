jQuery(function() { 
var $ = jQuery,
        $list = $('#fileList'),
        // 优化retina, 在retina下这个值是2
        //ratio = window.devicePixelRatio || 1,
        // 缩略图大小
       // thumbnailWidth = 100 * ratio,
        //thumbnailHeight = 100 * ratio,
        // Web Uploader实例
        // 初始化Web Uploader
        uploader = WebUploader.create({
        	// swf文件路径
            swf: sys.rootPath + '/resources/js/Uploader.swf',
			// 文件接收服务端。
			server :sys.rootPath+'/product/uploadPicture.html',/*/mvc/informationUpload.do",
*/			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#filePicker',
			// 不压缩
			compress:false,
			//fileNumLimit:1,//上传数量控制
			//fileSingleSizeLimit:2*1024*1024,
			// 只允许选择附件文件。
			accept : {
				title: 'Images',
	            extensions: 'gif,jpg,jpeg,bmp,png',
	            mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'   //修改这行
	          //  mimeTypes: 'image/*'
			},
			duplicate:false//允许重复上传
    });


 // 当有文件添加进来的时候，创建img显示缩略图使用
    uploader.on( 'fileQueued', function( file ) {
        var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                '</div>'
                ),
            $img = $li.find('img');

        // $list为容器jQuery实例
        $list.append( $li );

        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
      /*  uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );*/
    });

 // 文件上传过程中创建进度条实时显示。    uploadProgress事件：上传过程中触发，携带上传进度。 file文件对象 percentage传输进度 Nuber类型
    uploader.on( 'uploadProgress', function( file, percentage ) {
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
    uploader.on( 'uploadSuccess', function( file,response) {
    	if(response.success==true){
    		$('#hdMapUrl').val(response.data);
    		  $( '#'+file.id ).addClass('upload-state-done');
    	        //console.info(response);
    	      $("#upInfo").html("<font color='red'>上传成功！</font>");
    	}else{
    		  $error.text(response.data);
    	}
      
    });

    // 文件上传失败                                file:文件对象 ， code：出错代码
    uploader.on( 'uploadError', function(file,code) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败!');
    });

    // 不管成功或者失败，文件上传完成时触发。 file： 文件对象
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });
  //绑定提交事件
    $("#btn").click(function() {
        uploader.upload();   //执行手动提交
      });
  
});
