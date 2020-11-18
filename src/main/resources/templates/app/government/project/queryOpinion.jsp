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
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/lib/md5.min.js"></script>

<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<title>项目审批流转意见查询</title>

<style>

body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.container {padding: 0;max-width: 750px;}
 html,.container{height: 100%;}
 html{}
body{background: #F3F3F3;}
a{text-decoration:none;color: #000;}

.block{background: #fff;margin-bottom: 10px;}
.block .content{}
.line{
	display: -webkit-inline-box;
    width: 100%;
    border-bottom: solid 1px #f1f1f1;
    box-sizing: border-box;
    padding: 10px;
}
.line .left{width: 50%;}
.line .right{width: 50%;color: #9c9a9a;}

</style>

<script>
$(document).ready(function(){
	load();
});

var user = "${param.username}";
var pwd = "${param.password}";
var id = "${param.id}";
function load(){
	var args={"appAcc": user,"appPwd": hex_md5(pwd + "zjcmd5key&*"),"procInstId": id}
	$.ajax({ 
		url:"${ct}/app/menu/getDemo?url=http://19.200.90.199/webapi.do?unimethod*funccode=queryOpinion*access_token=${access_token}",
		type:"post",
		dataType:"json",
		data:{"param": JSON.stringify(args)},
		success:function(result){
			console.log(result);
			if(result.code == 0){
				//alert();
				var list = result.data.list;
				for(var i in list){
					$(".main").append(
						'<div class="block">'+
							'<div class="line">'+
								'<p class="left">流转方式</p>'+
								'<p class="right">'+list[i].transmitWay+'</p>'+
							'</div>'+
							'<div class="line">'+
								'<p class="left">审核意见</p>'+
								'<p class="right">'+list[i].comment+'</p>'+
							'</div>'+
							'<div class="line">'+
								'<p class="left">审核人</p>'+
								'<p class="right">'+list[i].dealUser+'</p>'+
							'</div>'+
							'<div class="line">'+
								'<p class="left">审核时间</p>'+
								'<p class="right">'+list[i].dealTime+'</p>'+
							'</div>'+
						'</div>'
					);
				}
				if(result.data.total_count == 0){
					//alert("查询无数据");
					//webToast("查询无数据","middle",3000);
					popTipShow.alert('温馨提示','查询无数据', ['知道了'],
						function(e){
						  //callback 处理按钮事件		  
						  var button = $(e.target).attr('class');
						  if(button == 'ok'){
							//按下确定按钮执行的操作
							//todo ....
							window.history.go(-1);
							this.hide();
						  }	
						}
					);
					
				}
			}else{
				//alert(result.msg);
				popTipShow.alert('温馨提示',result.msg, ['知道了'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
						//按下确定按钮执行的操作
						//todo ....
						window.history.go(-1);
						this.hide();
					  }	
					}
				);
				//webToast(result.msg,"middle",3000);
			}
		}
	});
}

</script>

</head>
<body>
<%-- <div>access_token:${access_token}</div> --%>
<div class="main">
	<!-- <div class="block">
		<div class="line">
			<p class="left">流转方式</p>
			<p class="right">转交</p>
		</div>
		<div class="line">
			<p class="left">审核意见</p>
			<p class="right">同意申报</p>
		</div>
		<div class="line">
			<p class="left">审核人</p>
			<p class="right">高志伟</p>
		</div>
		<div class="line">
			<p class="left">审核时间</p>
			<p class="right">2017-11-30 13:31:04</p>
		</div>
	</div>
	<div class="block">
		<div class="line">
			<p class="left">流转方式</p>
			<p class="right">转交</p>
		</div>
		<div class="line">
			<p class="left">审核意见</p>
			<p class="right">同意申报</p>
		</div>
		<div class="line">
			<p class="left">审核人</p>
			<p class="right">高志伟</p>
		</div>
		<div class="line">
			<p class="left">审核时间</p>
			<p class="right">2017-11-30 13:31:04</p>
		</div>
	</div> -->
</div>
</body>
</html>
