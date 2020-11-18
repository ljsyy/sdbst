<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/base64.js"></script>

<title>登录/注册</title>

<style>

body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.container {padding: 0;max-width: 750px;}
 html,.container{height: 100%;}
 html{background:#fff;}
body{background: #F3F3F3;}
a{text-decoration:none;color: #000;}
.container{text-align: center;}

.title{width: 100%;height: 40px;background: white;}
.title ul{display: -webkit-box;}
.title li{width: 50%;}
.title p{text-align: center;line-height: 40px;}
.main{margin-top: 10px;}
.main #left{}
.main #right{display: none;}
.star{display: inline;color: red;}

.list{
	background: white;    
	display: flex;    
	padding: 15px;    
	border-bottom: solid 1px #e4e4e4;    
}
.list input{    
	outline: none;
    border: none;
        flex: 1;
    text-align: right;color: darkgrey;
        font-size: 17px;
}
.list select{    
	outline: none;
    border: none;
    flex: 1;
    text-align: right;
    direction: rtl;
    position: absolute;
    right: 15px;
   	color: darkgrey;
}
.submit{    
	background: #77BDA8;
    width: 90%;
    height: 40px;
    line-height: 40px;
    font-size: 20px;
    border-radius: 8px;
    color: white;
}
.main{background: white;}
center{background: white;    margin-top: 25px;}
</style>

<script>
//var runEnviron='wx';	//运行环境
var runEnviron='${runEnviron}';	//运行环境
var base = new Base64();	//加密解密
var str1;	//IOS登录返回值(已登录返回useId，未登录返回空)
var str2;	//安卓登录返回值(已登录返回useId，未登录返回空)
var str3;	//微信登录返回值
var loginId;	//通用返回值
var username;
var psssword;

var state;

//检查身份证号码
function checkId(pId) {
    //检查身份证号码
    var arrVerifyCode = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2];
    var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
    var Checker = [1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1];
    if(pId.length != 15 && pId.length != 18) {
        //alert("身份证号共有15位或18位")
        webToast("身份证号共有15位或18位","middle",600);
        return;
    }
    var Ai = pId.length == 18 ? pId.substring(0, 17) : pId.slice(0, 6) + "19" + pId.slice(6, 16);
    if(!/^\d+$/.test(Ai)) {
        //return "身份证除最后一位外，必须为数字！"
        webToast("身份证除最后一位外，必须为数字！","middle",600);
        return;
    }
    var yyyy = Ai.slice(6, 10), mm = Ai.slice(10, 12) - 1, dd = Ai.slice(12, 14);
    var d = new Date(yyyy, mm, dd), now = new Date();
    var year = d.getFullYear(), mon = d.getMonth(), day = d.getDate();
    if(year != yyyy || mon != mm || day != dd || d > now || year < 1800) {
        //return "身份证输入错误！"
        webToast("身份证格式错误！","middle",600);
        return;
    }
    for(var i = 0, ret = 0; i < 17; i++) {
        ret += Ai.charAt(i) * Wi[i]
    }
    Ai += arrVerifyCode[ret %= 11];
    //return pId.length == 18 && pId != Ai ? alert("身份证格式错误！") : Ai;
    return pId.length == 18 && pId != Ai ? webToast("身份证格式错误！","middle",600) : Ai;
}

$(document).ready(function(){

	//选择日期
	var d=new Date();
	var day=d.getDate();
	var month=d.getMonth() + 1;
	var year=d.getFullYear();
	$("#bir").val(year + "/" + month + "/" + day);
	
	//根据身份证获取生日日期
	$("#regId").change(function(){
		var ic = $("#regId").val();	//修改生日日期
		checkId(ic);
		ic = ic.substring(6, 10) + "-" + ic.substring(10, 12) + "-" + ic.substring(12, 14);
		//alert(ic);
		$("#bir").val(ic);
	});
	
	start();

});
</script>
<script>
/* 滑块 */
function click_l(){
	$("#left").css("display","block");//显示div
	$("#right").css("display","none");//隐藏div
	$("#left_p").css("color","#6BB39D");//左字体变绿
	$("#left_p").css("border-bottom","solid 2px #6BB39D");//左下划线变绿
	$("#right_p").css("color","black");//右字体变黑
	$("#right_p").css("border-bottom","solid 0px #6BB39D");//右下划线消失
}
function click_r(){
	$("#left").css("display","none");//隐藏div
	$("#right").css("display","block");//显示div
	$("#left_p").css("color","black");//左字体变黑
	$("#left_p").css("border-bottom","solid 0px #6BB39D");//左下划线消失
	$("#right_p").css("color","#6BB39D");//右字体变绿
	$("#right_p").css("border-bottom","solid 2px #6BB39D");//右下划线变绿
}
</script>

<!-- 注册并激活 -->
<script>


function regClick(){
	var name = $("#name").val();//姓名
	var regpassword = $("#regPassword").val();//密码
	var regid = $("#regId").val();//身份号
	var gender = $("#gender").val();//性别
	var bir = $("#bir").val();//生出日期
	var mobile = $("#mobile").val();//手机号
	var email = $("#email").val();//电子邮箱
	if(name == "" || regpassword == ""  || regid == "" || gender == "" || bir == "" || mobile == ""){
		//alert("请填入内容");
		webToast("请填入内容","middle",600);
		return ;
	}
	var regEx =/^1[3|4|5|7|8|9][0-9]\d{4,8}$/;
	if(!(regEx.test($("#mobile").val()))){
		//alert("手机格式不正确");
		webToast("手机格式不正确","middle",600);
		return ;
	}
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(email != ""){
		if(!(reg.test($("#email").val()))){
			//alert("邮箱格式不正确");
			webToast("邮箱格式不正确","middle",600);
			return ;
		}	
	}
	var regdata = {
        personName: name,  //姓名
        gender: gender,    //性别
        birthday: bir,     //出生年月
        mobile: mobile,	   //手机号
        password: regpassword,//密码
        email: email,      //电子邮箱
    	idCard: regid,     //身份证号
    	idCardCity: "",    //市民卡
    	idCardMed: "",     //健康卡
    	idCardSoc: "",     //社保卡
    	idCardOld: ""      //老人卡
	};
	var live = {
		idCard: regid,     //身份证号
		personName: name,  //姓名
		idCardCity: "",    //市民卡
    	idCardMed: "",     //健康卡
    	idCardSoc: "",     //社保卡
    	idCardOld: ""      //老人卡
	}
	$.ajax({
		url:"${ct}/life/booking/getJsonIdentityRegister",
		data:"data=" + encodeURIComponent(JSON.stringify(regdata)),
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			if(result.resultStatus == "01"){
				$.ajax({
					url:"${ct}/life/booking/getJsonIdentityRegister",
					data:"data=" + encodeURIComponent(JSON.stringify(live)),
					type: "POST",
					dataType:"json",
					async:false,
					success:function(result){
						if(result.resultStatus == "01"){
							//alert("注册并激活成功");
							webToast("注册并激活成功","middle",1000);
							location.reload();
						}else{
							//alert(result.errorMsg);
							webToast(result.errorMsg,"middle",600);
						}
					}
				})
			}else{
				//alert(result.errorMsg);
				webToast(result.errorMsg,"middle",600);
			};
		}
	});
}

/* 日期选择 */
function yearClick(my){
	var type = my.attr("type");
	if(my.val() != ""){
		if(type == "text"){
			my.val("")
			my.attr("type","date");
		}else{
			my.attr("type","text");
		}
	}else{
		return;
	}
}

/* new */
/* 微信端是否登录百事通,获取userid */
function isLoginWX(){	
	//alert("getwxid");
	$.ajax({
		url:"${ct}/sys/userOthers/getuserid",
		data:"mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			//alert("got");
			console.log(result)
			loginId = result.data;
		}
	});
}

/* 绑定百事通账号 */
function binding(){
	//alert();
	$.ajax({
		url:"${ct}/sys/userOthers/saveuserOthers",
		//data:"username="+"${appointBind.username}"+"&password="+"${appointBind.pwd}"+"&type=0&userId="+loginId+"&mobileLogin=true",
		data:"username="+username+"&password="+password+"&type=0&userId="+loginId+"&mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			if(result.data == "保存成功"){
				//alert("绑定成功！");
				webToast("绑定成功！","middle",600);
				if("${param.type}" == "book"){
					window.location.href ="${ct}/life/booking?id=&noHead=${param.noHead}";
				}else if("${param.type}" == "health"){
					window.location.href ="${ct}/life/health/index?id=&noHead=${param.noHead}";
				}
				
			}else{
				//alert("绑定失败！");
				webToast("绑定失败！","middle",600);
			}
		}
	});
}

/* 判断系统获取userId */
function chooseGetId(){
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
			//alert("str1="+str1);
			//alert("loginId="+loginId);
		}
	}
}

/* 查询已绑定信息 */
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
			}else{
				//alert("error4");
				username = "";
				password = "";
				//return;
			}
		}
	});
}

<!-- 登录 -->
function login(){
	//alert(222);
	var data = {
        password: password,//密码
        cardNumber: username,     //身份证号
    	cardType: "sf"	   //证件类型(sf:身份证，sb:社保卡，sm：市民卡，jk:健康卡)
	};
	/* var data = {
        password: "123456",//密码
        cardNumber: "440681199506170212",     //身份证号
    	cardType: "sf"	   //证件类型(sf:身份证，sb:社保卡，sm：市民卡，jk:健康卡)
	}; */
	
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
				//alert("登陆成功");
				state = "pass";
			  	//alert(state);
			  	//alert("登陆成功1");
			  	$.ajax({//保存empi值
					url:"${ct}/life/appoint/savelogin",
					data:"data=" + encodeURIComponent(JSON.stringify(result.resultInfo)),
					type: "POST",
					async:true,
					success:function(result){
						//alert("保存empi值")
						console.log(result)
					}
				});
				
			}else{
				//alert(result.errorMsg);
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

function saveEmpi(){	//保存empi值
	$.ajax({
		url:"${ct}/life/appoint/savelogin",
		data:"data=" + encodeURIComponent(JSON.stringify(result.resultInfo)),
		type: "POST",
		async:true,
		success:function(result){
			console.log(result)
		}
	});
}

function loginClick(){	//点击登录按钮
	username = $("#idNum").val();
	password = $("#password").val();
	login(); //登录
	//alert(state+"///");
	if(state == "pass"){	//登陆成功
		popTipShow.confirm('温馨提示','是否绑定百事通账号',['绑定','不绑定'],
			function(e){
			  //callback 处理按钮事件
			  var button = $(e.target).attr('class');
			  if(button == 'ok'){
				this.hide();
				chooseGetId();
				//alert("111222"+loginId);
				if(loginId == "" || loginId == null || loginId == undefined){	//未登录
					saveBindDate();
					chooseLogin();	//判断系统，登录百事通
				}else{	//已登录
					binding();	//绑定百事通
				}
				

			  }

			  if(button == 'cancel') {
				this.hide();
			    if("${param.type}" == "book"){
					window.location.href ="${ct}/life/booking?user="+username+"&pwd="+password+"&noHead=${param.noHead}";
				}else if("${param.type}" == "health"){
					window.location.href ="${ct}/life/health/index?user="+username+"&pwd="+password+"&noHead=${param.noHead}";
				}else{
					//alert("error1");
					return;
				}
				

			  }
			}
		);
	}else{
		//alert("error2");
		return;
	}
}

function chooseLogin(){	//判断系统，跳转百事通登录页面
	var u = navigator.userAgent; 
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端 
	if((typeof(runEnviron) != "undefined" && runEnviron == 'wx') || "${param.client}" == "wx"){
		//alert("wx登录");
		if("${param.type}" == "book"){
			window.location.href ="${ct}/person/login?type=book&noHead=${param.noHead}";
		}else if("${param.type}" == "health"){
			window.location.href ="${ct}/person/login?type=health&noHead=${param.noHead}";
		}
	}else{
		if (isAndroid) {
			//alert("Android登录");
			myobj.updateshow();	//跳到安卓登录页，登陆后刷新页面
		}else if (isiOS) {
			//alert("ios登录");
			aaa.pushViewControllerTitle('SecondViewController','secondPushedFromJS');	//跳到IOS登录页，登陆后刷新页面
		}
	}
}

function start(){
	chooseGetId();
	//alert("loginId="+loginId);
	//if("${appointBind.username}" != "" && "${appointBind.pwd}" != "" && loginId != ""){//查询缓存是否存有信息，若有则绑定
	if(loginId != ""){//已登录
		//alert("1");
		searchBind();
		if(username != "" && password != ""){	//查询到绑定信息
			//alert(username);
			//alert(password);
			//alert("登录");
			login();	//登录
			if(state == "pass"){
				//alert("跳转");
				webToast("已为您自动登录","middle",600);
				if("${param.type}" == "book"){
					window.location.href ="${ct}/life/booking?id=&noHead=${param.noHead}";
				}else if("${param.type}" == "health"){
					window.location.href ="${ct}/life/health/index?id=&noHead=${param.noHead}";
				}
			}else{
				//alert("error3");
				return;
			}
		}else if("${appointBind.username}" != "" && "${appointBind.pwd}" != ""){
			username = "${appointBind.username}";
			password = "${appointBind.pwd}";
			//alert("有缓存，绑定百事通账号");
			binding();	//绑定百事通账号
		}else{	//未查询到绑定信息
			//alert("未查询到绑定信息");
			return;
		}
		
	}else if(loginId != ""){	//已登录百事通
		//alert("2");
		searchBind();
		
	}else{//未登录百事通
		//alert("若已绑定账号，则自行登录百事通账号后进入");
		webToast("若已绑定账号，则自行登录百事通账号后进入","middle",2000);
		/* popTipShow.alert('温馨提示','若已绑定账号，则自行登录百事通账号后进入', ['OK'],
			function(e){
			  //callback 处理按钮事件		  
			  var button = $(e.target).attr('class');
			  if(button == 'ok'){
			  	
				this.hide();
			  }	
			}
		); */
		return;
	}
}

function saveBindDate(){	//缓存保存绑定信息
	var bindingdata = {
		username: username,	//身份证号码
        pwd  : password,//密码
	};
	$.ajax({
		url:"${ct}/life/appoint/binding",
		data:"data=" + encodeURIComponent(JSON.stringify(bindingdata)),
		type: "POST",
		async:false,
		success:function(result){
			console.log(result);
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

<div class="title">
	<ul>
		<li id="left_click" onClick="click_l()"><p id="left_p" style="color: #6BB39D; border-bottom: solid 2px #6BB39D;">登录</p></li>
		<li id="right_click" onClick="click_r()"><p id="right_p">注册</p></li>
	</ul>
</div>

<div class="main">
	<!-- 登录 -->
	<div id="left">
		<div class="list">
			<span>身份证号：</span>
			<input type="text" id="idNum" placeholder="请输入身份证号" maxlength="18" />
		</div>
		<div class="list">
			<span>密码：</span>
			<input type="password" id="password" placeholder="请输入6-20位密码" />
		</div>
		
		<center>
			<div class="submit" onclick="loginClick()" type="submit">登录</div>
		</center>
	</div>
	
	<!-- 注册 -->
	<div id="right">
		<div class="list">
			<span>姓名<p class="star">*</p></span>
			<input type="text" id="name" placeholder="请输入" maxlength="10" />
		</div>	
		<div class="list">
			<span>密码<p class="star">*</p></span>
			<input type="password" id="regPassword" placeholder="请输入6-20位密码" />
		</div>
		<div class="list">
			<span>身份证号<p class="star">*</p></span>
			<input type="text" id="regId" placeholder="请输入身份证号" maxlength="18" />
		</div>
		<div class="list">
			<span>性别<p class="star">*</p></span>
			<select id="gender">
				<option value="">请选择</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
		</div>
		<div class="list">
			<span>手机号<p class="star">*</p></span>
			<input type="text" id="mobile" placeholder="请输入手机号" />
		</div>
		<div class="list">
			<span>出生年月<p class="star">*</p></span>
			<input type="text" id="bir" onclick="yearClick($(this))" onblur="yearClick($(this))" value=""/>
		</div>
		<div class="list">
			<span>电子邮箱：</span>
			<input type="text" id="email" placeholder="请输入电子邮箱" />
		</div>
		
		<center>
			<div class="submit" onclick="regClick()" type="submit">注册</div>
		</center>
		
	</div>
</div>

</body>
</html>