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
<script src="${ctxStatic}/modules/app/js/base64.js"></script>

<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>
<title>项目审批进度查询</title>

<style>

body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.container {padding: 0;max-width: 750px;}
 html,.container{height: 100%;}
 html{}
body{background: #F3F3F3;}
a{text-decoration:none;color: #000;}

.block{background: #fff;margin-bottom: 10px;}
.block .content{}
.title{
    padding: 10px;
    font-size: 17px;
    font-weight: 600;
}
.line{
	display: -webkit-inline-box;
    width: 100%;
    border-top: solid 1px #bcbcbc;
    box-sizing: border-box;
    padding: 10px;
}
.line .left{width: 50%;}
.line .right{width: 50%;color: #9c9a9a;}
.main{
    overflow: scroll;
    height: 100%;
    position: relative;
}
</style>

<script>
//var runEnviron="wx";	//初始化运行环境(wx测试)
var runEnviron='${runEnviron}';	//运行环境
$(document).ready(function(){
	choose();	//判断系统
});

var base = new Base64();	//加密解密
var page = 1;
var loginId; //登录返回值（统一）
var str1;	//IOS登录返回值
//查询微信登录返回值
function isLogin(){
	$.ajax({
		url:"${ct}/sys/userOthers/getuserid",
		data:"mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			loginId = result.data;
		}
	});
}
var loginHref;
//判断系统	获取登录返回值
function choose(){
	var u = navigator.userAgent; 
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端 
	//alert('${runEnviron}');
	if(typeof(runEnviron) != "undefined" && runEnviron == 'wx'){	
		//alert("wx");
		loginHref="${ct}/app/menu/project/login?client=wx";
		isLogin();	//检测微信userid
	}else{
		loginHref="${ct}/app/menu/project/login";
		if (isAndroid) {
			//alert("Android");
			loginId = myobj.useridshow();	//安卓返回值
		}else if (isiOS) {
			//alert("ios");
		  	aaa.call();		//调用IOS返回值
		  	loginId = str1;
		}
	}
	//alert("loginId="+loginId);
	if(loginId == "" || loginId == undefined || loginId == null){
		//alert("请先登录百事通系统");
		webToast("请先登录","middle",600);
		window.location.href=loginHref;
		/* popTipShow.alert('温馨提示','请先登录', ['OK'],
			function(e){
			  //callback 处理按钮事件		  
			  var button = $(e.target).attr('class');
			  if(button == 'ok'){
				window.location.href=loginHref;
				this.hide();
			  }	
			}
		); */
		
	}else{
		//alert("查询绑定信息");
		searchBind();
	}
}

//查询绑定信息
function searchBind(){
	$.ajax({
		url:"${ct}/sys/userOthers/getpaw",
		data:"type=2&userId="+loginId+"&mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result);
			if(result.msg == "false" && result.data == "请登录账号密码"){	//未登录
				//alert("请登录经科局系统");
				popTipShow.alert('温馨提示','请登录经科局系统', ['OK'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
						if(typeof(runEnviron) != "undefined" && runEnviron == 'wx'){
							window.location.href="${ct}/app/menu/project/login?client=wx";
						}else{
							window.location.href="${ct}/app/menu/project/login";
						}
						this.hide();
					  }	
					}
				);
				
			}else if(result.msg == "false" && result.data == "没有保存账号密码"){		//已登录，但没有绑定账号
				//alert("请登录经科局系统");
				popTipShow.alert('温馨提示','请登录经科局系统', ['OK'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
						if(typeof(runEnviron) != "undefined" && runEnviron == 'wx'){
							window.location.href="${ct}/app/menu/project/login?client=wx";
						}else{
							window.location.href="${ct}/app/menu/project/login";
						}
						this.hide();
					  }	
					}
				);
			}else{	//已登录并查询到绑定信息
				user = base.decode(result.data.username);
				pwd = base.decode(result.data.password);
				load();
			}
			
			
		}
	});
}

var user = "${param.username}";
var pwd = "${param.password}";
function load(){
	var args={"appAcc":user,"appPwd":hex_md5(pwd+"zjcmd5key&*"),"pageIndex":page,"pageSize":5};
	
	$.ajax({ 
		url:"${ct}/app/menu/getDemo?url=http://19.200.90.199/webapi.do?unimethod*funccode=queryStatus*access_token=${access_token}",
		type:"post",
		dataType:"json",
		data:{"param": JSON.stringify(args)},
		success:function(result){
			console.log(result);
			if(result.code == 0){
				var list = result.data.list;
				for(var i in list){
					var href = "${ct}/app/menu/project/queryOpinion?username="+user+"&password="+pwd+"&id="+list[i].id;
					var prevHandleNote;
					if(list[i].prevHandleNote == "" || list[i].prevHandleNote == null || list[i].prevHandleNote == undefined){
						prevHandleNote = "暂无数据";
					}else{
						prevHandleNote = list[i].prevHandleNote;
					}
					var checkStage;
					if(list[i].checkStage == "" || list[i].checkStage == null || list[i].checkStage == undefined){
						checkStage = "暂无数据";
					}else{
						checkStage = list[i].checkStage;
					}
					$(".main").append(
						'<div class="block">'+
							'<div class="title">'+list[i].projectName+'</div>'+
							'<div class="content">'+
								'<div class="line">'+
									'<p class="left">业务类别</p>'+
									'<p class="right">'+list[i].businessName+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">单位名称</p>'+
									'<p class="right">'+list[i].companyName+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">申请日期</p>'+
									'<p class="right">'+list[i].applyTime+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">项目阶段</p>'+
									'<p class="right">'+list[i].projectStage+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">审批环节</p>'+
									'<p class="right">'+checkStage+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">处理日期</p>'+
									'<p class="right">'+list[i].prevHandleTime+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">处理人</p>'+
									'<p class="right">'+list[i].prevHandler+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">处理方式、意见</p>'+
									'<p class="right">'+prevHandleNote+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">初审结果</p>'+
									'<p class="right">'+list[i].isAuditPassKsgzry+'</p>'+
								'</div>'+
								/* '<div class="line">'+
									'<p class="left">流程实例ID</p>'+
									'<p class="right">'+list[i].processInstanceId+'</p>'+
								'</div>'+
								'<div class="line">'+
									'<p class="left">申报项目主键ID</p>'+
									'<p class="right">'+list[i].id+'</p>'+
								'</div>'+ */
								'<a class="line" href= "'+href+'" style="text-align: -webkit-right;color: cornflowerblue;">'+
									'<p>项目审批流转意见查询</p>'+
								'</a>'+
							'</div>	'+
						'</div>'
					);
				}
			}else{
				//alert(result.msg);
				webToast(result.msg,"middle",600);
			}
			$(".main").bind("scroll",function(){
				var bottom=$(this).scrollTop() + $(this).height();
				  if(bottom==$(this)[0].scrollHeight){
					  page = page + 1;
					  load();
				  }	
			});
		}
	});
}

</script>
<!-- IOS专用 -->
<script type="text/javascript">
var call = function(){
    var callInfo = JSON.stringify({"jianshu": "http://www.jianshu.com/users/55c8fdc3c6e7/latest_articles"});
    tianbai.getCall(callInfo);
}
var Callback = function(str){
    str1 = str;	
    return str1;
}
var alerCallback = function(){
    alert('成功');
}
</script>

</head>
<body>
<%-- <div>access_token:${access_token}</div> --%>
<div class="main">
	<!-- <div class="block">
		<div class="title">xxx项目</div>
		<div class="content">
			<div class="line">
				<p class="left">业务类别</p>
				<p class="right">2017年顺德区采购本地装备产品财政扶持资金项目</p>
			</div>
			<div class="line">
				<p class="left">单位名称</p>
				<p class="right">广州竞速公司</p>
			</div>
			<div class="line">
				<p class="left">申请日期</p>
				<p class="right">2017-11-30 13:31:04</p>
			</div>
			<div class="line">
				<p class="left">项目阶段</p>
				<p class="right">立项评审</p>
			</div>
			<div class="line">
				<p class="left">审批环节</p>
				<p class="right">申报人填写申报书</p>
			</div>
			<div class="line">
				<p class="left">处理日期</p>
				<p class="right">2017-12-01 16:51:30</p>
			</div>
			<div class="line">
				<p class="left">处理人</p>
				<p class="right">张三</p>
			</div>
			<div class="line">
				<p class="left">处理方式、意见</p>
				<p class="right">[退回]请补充附件</p>
			</div>
			<div class="line">
				<p class="left">初审结果</p>
				<p class="right">不通过</p>
			</div>
			<div class="line">
				<p class="left">流程实例ID</p>
				<p class="right">366934</p>
			</div>
			<div class="line">
				<p class="left">申报项目主键ID</p>
				<p class="right">f4d572f6-eb8a-4737-8be1-8bb9ccb56150</p>
			</div>
		</div>
	</div> -->
</div>
</body>
</html>
