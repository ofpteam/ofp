(function() {
	if(!window.uploaderHelper){
		window.uploaderHelper={};
	}
	var fileNames=new Array();
	var uploader;
	
	window.uploaderHelper={
			uuid:function(){
				 var s = [];
				    var hexDigits = "0123456789abcdef";
				    for (var i = 0; i < 36; i++) {
				        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
				    }
				    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
				    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
				    s[8] = s[13] = s[18] = s[23] = "-";
				 
				    var uuid = s.join("");
				    return uuid;
			},
			loadFile:function(attach_path){//加载附件
				if(attach_path!=""){
					var fileurl=root+'/upload/'+attach_path;//上传成功后的文件路径
					$('#thelist').append('<a href='+fileurl+'>'+attach_path.substring(36,attach_path.length)+'</a><img src="'+root+'/img/clear.png" onclick= uploaderHelper.delfileFromClient('+"'"+attach_path+"'"+')  />');
					fileNames.push(attach_path);
				}
			},delfileFromClient:function(fileid){
				f_confirm("是否确认删除？",function isdelete(){
					$('#thelist').children().remove();
					fileNames.splice($.inArray(fileid,fileNames), 1);
				});
			},
			getFileIds:function(){
				return fileNames;
			},
			getFiles:function(){
				return uploader.getFiles();
			},
			init:function(path,controller,buttionId){
				/* 初始化文件上传控件 */
				if(uploader!=undefined){
					fileNames=new Array();
					$('#thelist').children().remove();
					uploader.destory();
				}
					 uploader = WebUploader.create({
					// swf文件路径
					swf : path+'/js/webuploader/Uploader.swf',
					// 文件接收服务端。
					server :path+controller,/*/mvc/informationUpload.do",
		*/			// 选择文件的按钮。可选。
					// 内部根据当前运行是创建，可能是input元素，也可能是flash.
					pick : '#picker',
					// 不压缩image, 默认如果是jpg，文件上传前会压缩一把再上传！
					resize : false,
					//fileNumLimit:1,//上传数量控制
					fileSingleSizeLimit:2*1024*1024,
					// 只允许选择附件文件。
					accept : {
						title : 'office',
						extensions : 'xlsx,docx',
						mimeTypes : '.xlsx,.docx'
					},
					duplicate:true//允许重复上传
				});
				// 显示用户选择
				// 由于webuploader不处理UI逻辑，所以需要去监听fileQueued事件来实现。
				// 当有文件被添加进队列的时候
				uploader.on('fileQueued', function(file) {
				if($('#thelist a').length>0){
						f_alert('超出上传数量!');
						return false;
					}
				if($('#thelist h5').length>=1){
					f_alert('超出上传数量!');
					return false;
				}
				var uuid=uploaderHelper.uuid();
				file.name=file.name.replace(/(^s+)|(\s+$)/g,"");
				file.name=file.name.replace(/\s/g,"");
				file.name =uuid+file.name;
				fileNames=new Array();
				fileNames.push(file.name);
			
					$('#thelist').append('<div id="' + file.id + '" class="item">' + '<h5 class="info">'
									+ file.name.substring(36,file.name.length) + '</h5>' + '<p class="state">等待上传...</p>'+ '</div>');
					});
				// 文件上传进度
				// 文件上传中，Web Uploader会对外派送uploadProgress事件，其中包含文件对象和该文件当前上传进度。
				// 文件上传过程中创建进度条实时显示。
				uploader.on('uploadProgress',
								function(file, percentage) {
									var $li = $('#' + file.id), $percent = $li.find('.progress .progress-bar');
									// 避免重复创建
									//if (!$percent.length) {
										$percent = $('<div class="progress progress-striped active">'
														+ '<div class="progress-bar" role="progressbar" style="width: 0%">'
														+ '</div>' + '</div>')
												.appendTo($li).find('.progress-bar');
									//}
									$li.find('p.state').text('上传中');
									$percent.css('width', percentage * 100 + '%');
								});
				// 文件成功、失败处理
				// 文件上传失败会派送uploadError事件，成功则派送uploadSuccess事件。不管成功或者失败，在文件上传完后都会触发uploadComplete事件。
		
				uploader.on('uploadSuccess', function(file,response) {
					});
				uploader.on('error', function(type) {
					if(type=="F_EXCEED_SIZE"){
						f_alert('文件大小不能超过2M!');
						return;
			        }

				});
				uploader.on('uploadComplete', function(file) {
					var fileurl=root+'/upload/'+file.name;//上传成功后的文件路径
					$('#' + file.id).children().remove();
					$('#' + file.id).append('<a href='+fileurl+'>'+file.name.substring(36,file.name.length)+'</a><img src="'+root+'/img/clear.png" onclick= uploaderHelper.delfileFromClient("'+file.name+'")  id='+file.name+' />');
				
				});
				$('#ctlBtn').on('click', function() {
					if ($(this).hasClass('disabled')) {
						return false;
					}
					var index=uploader.options.server.indexOf("?");
					if(index==-1){
						uploader.options.server=uploader.options.server+"?fileName="+encodeURI(fileNames[0]);
					}
					else{
						uploader.options.server=uploader.options.server.substring(0,index)+"?fileName="+encodeURI(fileNames[0]);
					}
					uploader.upload();
				});
			}
	}

})();
