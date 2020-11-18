<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!infoTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/iconfont.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/lib/md5.min.js"></script>
<title>项目审批信息推送测试</title>

<style>

body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.container {padding: 0;max-width: 750px;}
 html,.container{height: 100%;}
 html{}
body{background: #F3F3F3;}
a{text-decoration:none;color: #000;}

.main{}
.block{margin-bottom: 10px;}
.font{}
.font span{}
</style>

<script>
$(document).ready(function(){
	load();
});


function load(){

	$.ajax({ 
		url:"${ct}/interface/querySTP",
		type:"post",
		dataType:"json",
		data:"appid=${param.appid}&secret=${param.secret}",
		success:function(result){
			console.log(result);
			if(result.code == 0){
				var list = result.list;
				for(var i in list){
					$(".main").append(
						'<div class="block">'+
							'<p class="font">编号:<span>'+i+'</span></p>'+
							'<p class="font">用户账号:<span>'+list[i].user+'</span></p>'+
							'<p class="font">业务类别:<span>'+list[i].kind+'</span></p>'+
							'<p class="font">项目名称:<span>'+list[i].projectName+'</span></p>'+
							'<p class="font">申报单位:<span>'+list[i].company+'</span></p>'+
							'<p class="font">项目阶段:<span>'+list[i].projectStage+'</span></p>'+
							'<p class="font">审批环节:<span>'+list[i].checkStage+'</span></p>'+
							'<p class="font">处理日期:<span>'+list[i].handleDate+'</span></p>'+
							'<p class="font">处理人:<span>'+list[i].handler+'</span></p>'+
							'<p class="font">处理方式/意见:<span>'+list[i].handing+'</span></p>'+
						'</div>'
					);
				}
			}else{
				alert(result.code)
			}
		}
	});
}

</script>

</head>
<body>

<div class="main">
<!-- 	<div class="block">
		<p class="font">编号:<span>1</span></p>
		<p class="font">用户账号:<span>1</span></p>
		<p class="font">业务类别:<span>1</span></p>
		<p class="font">项目名称:<span>1</span></p>
		<p class="font">申报单位:<span>1</span></p>
		<p class="font">项目阶段:<span>1</span></p>
		<p class="font">审批环节:<span>1</span></p>
		<p class="font">处理日期:<span>1</span></p>
		<p class="font">处理人:<span>1</span></p>
		<p class="font">处理方式/意见:<span>1</span></p>
	</div> -->
</div>

</body>
</html>
