<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预约挂号</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/base64.js"></script>

<style>
html{background-color:white;}
.booking-list{width:100%;}
.icon-list{width:50%;margin-top: 18px;}
.icon-list img{width:60px;}
li{text-align: center;float: left;height: 150px;}
p{text-align: center;margin-top: 10px;}
.welcome{position: relative;background: white;padding: 10px;}
.welcome img{width:30px;}
#welcome{display: -webkit-inline-box;font-size: 15px;padding-left: 10px;vertical-align: super;}

.c-float-popWrap .c-float-modePop button.ok{color: #6bb39d;}
</style>

</head>
<body onhashchange="func();">

<!-- banner -->
<div class="banner">
	<img src="${ctxStatic}/modules/app/img/appoint/banner.png" style="width:100%;" />
</div>

<!-- 图标列表 -->
<div class="booking-list">
	<div class="welcome" onclick="window.location.href='${ct}/life/appoint/info?id=&type=book&noHead=${param.noHead}'">
		<div>
			<img src="${ctxStatic}/modules/app/img/appoint/person.png" />
			<p id="welcome">请登录</p>
		</div>
		<%-- ${appoinctlogin.personName}<span>，您好！</span> --%>
	</div>	
	<li class="icon-list">
		<a href="${ct}/life/booking/area?id=&noHead=${param.noHead}">
			<img src="${ctxStatic}/modules/app/img/appoint/hospital.png" />
		</a>
		<p>按医院挂号</p>
	</li>
	
	<li class="icon-list">
		<a href="${ct}/life/booking/department?id=&noHead=${param.noHead}">
			<img src="${ctxStatic}/modules/app/img/appoint/specialist.png" />
		</a>
		<p>按专科挂号</p>
	</li>
	
	<li class="icon-list">
		<a href="${ct}/life/booking/doctorName?id=&noHead=${param.noHead}">
			<img src="${ctxStatic}/modules/app/img/appoint/doctor.png" />
		</a>
		<p>按医生挂号</p>
	</li>
	
	<li class="icon-list">
		<a href="${ct}/life/appoint/mybooking?id=&noHead=${param.noHead}">
			<img src="${ctxStatic}/modules/app/img/appoint/myAppoint.png" />
		</a>
		<p>我的预约</p>
	</li>
	
	
</div>
	
</body>
</html>

<script>
var runEnviron='${runEnviron}';	//运行环境
var base = new Base64();	//base64加密解密
var str1;	//IOS登录返回值
var loginId;	//通用返回值
var username;	//初始化预约挂号系统账号
var password;	//初始化预约挂号系统账号

$(document).ready(function(){
	chooseSys();
});

//判断客户端
function chooseSys(){
	var u = navigator.userAgent; 
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端 
	if((typeof(runEnviron) != "undefined" && runEnviron == 'wx') || "${param.client}" == "wx"){
		//alert("WX");
		isLoginWX();
	}else{
		if(isAndroid) {
			//alert("Android");
			loginId = myobj.useridshow();	//安卓返回值
		}else if (isiOS) {
			//alert("IOS");
			aaa.call();		//调用IOS返回值
		  	loginId = str1;
		}
	}
	//alert("loginId="+loginId);
	if("${param.user}" != "" && "${param.pwd}" != ""){
		username = "${param.user}";
		password = "${param.pwd}";
		login();
	}else if(loginId == "" || loginId == null || loginId == undefined){
		window.location.href = "${ct}/life/appoint/login?type=book&noHead=true";
	}else{
		searchBind();
	}
}

//获取微信端userid
function isLoginWX(){	
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

//查询已绑定信息 
function searchBind(){
	$.ajax({
		url:"${ct}/sys/userOthers/getpaw",
		data:"type=0&userId="+loginId+"&mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result);
			if(result.msg == "true"){
				username = base.decode(result.data.username);
				password = base.decode(result.data.password);
				login();
			}else{
				//alert("没有查询到绑定信息，请登录预约挂号系统");
				/* webToast("没有查询到绑定信息，请登录预约挂号系统","middle",600);
				username = "";
				password = "";
				window.location.href = "${ct}/life/appoint/login?type=book&noHead=true"; */
				popTipShow.alert('温馨提示','没有查询到绑定信息，请登录预约挂号系统', ['OK'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
					  	username = "";
						password = "";
						window.location.href = "${ct}/life/appoint/login?type=book&noHead=true";
						this.hide();
					  }	
					}
				);
			}
		}
	});
}

function login(){
	var data = {
        password: password,//密码
        cardNumber: username,     //身份证号
    	cardType: "sf"	   //证件类型(sf:身份证，sb:社保卡，sm：市民卡，jk:健康卡)
	};
 	console.log(JSON.stringify(data))
	$.ajax({
		url:"${ct}/life/booking/getJsonIdentityRegister",
		data:"data=" + encodeURIComponent(JSON.stringify(data)),
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result);
			if(result.resultStatus == "01"){	//登陆成功
			  	$.ajax({//保存empi值
					url:"${ct}/life/appoint/savelogin",
					data:"data=" + encodeURIComponent(JSON.stringify(result.resultInfo)),
					type: "POST",
					async:true,
					success:function(result){
						console.log(result)
					}
				});
			  	$("#welcome").text(result.resultInfo.personName+"，您好！");
			}else{
				popTipShow.alert('温馨提示',result.errorMsg, ['OK'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
					  	
						this.hide();
					  }	
					}
				);
				return;
			}
		}
	});
}

function pushHistory() {
	var state = {
		title : "",
		url : "#"
	};
	window.history.pushState(state, "title", "#");
}
$(function(){
	window.addEventListener("popstate", function(e) {
	//alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能
		if((typeof(runEnviron) != "undefined" && runEnviron == 'wx') || "${param.client}" == "wx"){
			window.location.href = "${ct}/app/menu/menu?index=4";
		}
	}, false);
	pushHistory();
	
});
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
