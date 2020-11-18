<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提交预约信息</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
.message{background: white;position: relative;padding: 10px;margin: 20px 20px 12px 20px;border-radius: 8px;}
.message span{width: 30%;}
.message .message-list{border-bottom: solid 1px #DCD9D9;height: 50px;width: 100%;display: flex;align-items: center;}
.message .top-list.noborder{border-bottom: solid 0px #DCD9D9;height: auto;}
.message .message-list .left{float: left;margin-left: 10px;font-size: 18px;}
.message .message-list .left img{width: 23px;position: relative;top: 4px;}
.message .message-list .right{float: right;margin-right: 10px;color: #B1ADAD;font-size: 17px;}
.message .message-list .right span{color: #FFA700;font-size: 25px;position: absolute;right: -10px;top: 8px;}
.choose{font-size: 23px;position: relative;padding-left: 23px;top: 6px;background: #F6F6F6;color: #827E7E;}
.submit{background: #3EC6A3;width: 90%;height: 45px;line-height: 45px;border: 0px;border-radius: 8px;color: white;font-size: 23px;margin-bottom: 20px;}
.message-list input{outline: none;border: none;flex: 1;text-align: right;color: darkgrey;margin: 0 10px 0 0;}
.message-list select{outline: none;border: none;flex: 1;text-align: right;direction: rtl;right: 15px;color: darkgrey;margin: 0 10px 0 0;}
.left-info{float: left;margin-left: 10px;font-size: 18px;width: 100%;}
.top-list{border-bottom: solid 1px #DCD9D9;height: 50px;width: 100%;align-items: center;line-height: 50px;}
.top-list .left{float: left;margin-left: 10px;font-size: 18px;}
.top-list .right{float: right;margin-right: 10px;color: #B1ADAD;font-size: 17px;}
.top-list .right span{color: #FFA700;font-size: 25px;position: absolute;right: -10px;top: 8px;}
</style>

</head>
<body>
<!--头部-->
<c:choose>
  	<c:when test="${param.noHead}"></c:when>
  	<c:otherwise>
	    <header id="head">
	        <div class="headl retrun-icon"> 
	   		    <svg class="icon" aria-hidden="true">
	                   <use xlink:href="#icon-toLeft-copy"></use>
	               </svg>
	        </div>
	        <span id="title">提交预约信息</span>
	        <div class="headr"></div>
	    </header>
  	</c:otherwise>
</c:choose>

<!-- 挂号信息 -->
<div class="message">
	<div class="top-list">
		<p class="left">挂号诊疗费</p>
		<p class="right"><span>${param.tr}</span>元</p>
	</div>
	<div class="top-list">
		<p class="left">科室</p>
		<p class="right">${param.sub}</p>
	</div>
	<div class="top-list">
		<p class="left">医生</p>
		<p class="right">${param.name}</p>
	</div>
	<div class="top-list noborder">
		<p class="left" style="float:none;">就诊时间</p>
		<p class="right" style="float:none;margin-left: 10px;height: 20px;line-height: 20px;">${param.time}</p>
		
	</div>
</div>

<!-- 选择就诊人 -->
<%-- <p class="choose">选择就诊人</p>
<div class="message">
	<div class="message-list">
		<p class="left">
		<img src="${ctxStatic}/modules/app/img/appoint/warning.png">
		您还没有添加就诊人
		</p>
	</div>
	<div class="message-list noborder">
		<p class="left">
		<img src="${ctxStatic}/modules/app/img/appoint/add.png">
		添加就诊人
		</p>
	</div>
</div>  --%>

<!-- 填写信息 -->
<p class="choose">请填写信息</p>
<div class="message">
	<div class="message-list">
		
			<span>姓名</span>
			<input type="text" id="name" placeholder="请输入姓名" />
		
	</div>
	<div class="message-list">
		<span>性别</span>
			<select id="gender">
				<option value="">请选择</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
		
	</div>
	<div class="message-list">
		
			<span>证件类型</span>
			<select id="cardType">
				<option value="">请选择</option>
				<option value="身份证">身份证</option>
				<option value="市民卡">市民卡</option>
				<option value="社保卡">社保卡</option>
			</select>
		
	</div>
	<div class="message-list">
		<span>证件号码</span><input type="text" id="cardNum" placeholder="请输入证件号码" />
	</div>
	<div class="message-list">
		<span>手机号码</span><input type="text" id="tel" placeholder="请输入手机号" />
	</div>
	<div class="message-list">
		<span>出生日期</span><input type="text" id="bir" onclick="yearClick($(this))" onblur="yearClick($(this))" value=""/>
	</div>
</div>

<center>
	<div class="submit" onclick="submitClick()" type="submit">确认</div>
</center>

</body>
</html>

<script type="text/javascript">

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
	
	var d=new Date();
	var day=d.getDate();
	var month=d.getMonth() + 1;
	var year=d.getFullYear();
	$("#bir").val(year + "/" + month + "/" + day);
	
	//根据身份证获取生日日期
	$("#cardNum").change(function(){
		var ic = $("#cardNum").val();	//修改生日日期
		checkId(ic);
		ic = ic.substring(6, 10) + "-" + ic.substring(10, 12) + "-" + ic.substring(12, 14);
		//alert(ic);
		$("#bir").val(ic);
	});
});
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
</script>

<script>
function submitClick(){
	var name = $("#name").val();
	var gender = $("#gender").val();
	var cardType = $("#cardType").val();
	var cardNum = $("#cardNum").val();
	var tel = $("#tel").val();
	var bir = $("#bir").val();
	if(name == "" || gender == ""  || cardType == "" || cardNum == "" || tel == "" || bir == ""){
		//alert("请填入内容")
		webToast("请填入内容","middle",600);
		return ;
	}
	
	var regEx =/^1[3|4|5|7|8|9][0-9]\d{4,8}$/;
	if(!(regEx.test($("#tel").val()))){
		//alert("手机格式不正确")
		webToast("手机格式不正确","middle",600);
		return ;
	}

	var data = {
    	empi: "${appoinctlogin.empi}",
        hospitalCode: "${param.hospitalCode}",
      	departmentCode: "${param.departmentCode}",
     	doctorId: "${param.doctorId}",
     	regDate: ("${param.time}").split(" ")[0],//
     	firstOrReturn: "1",
     	diviFlag: "1",
     	timeDiviBegin: ("${param.time}").split(" ")[1].split("-")[0],//
     	timeDiviEnd: ("${param.time}").split(" ")[1].split("-")[1],//
     	hzName: name,
     	hzGender: gender,
     	hzCardType: cardType,
     	hzCard: cardNum,
     	regName: name,
     	regGender: gender,
     	regCardType: cardType,
     	regCard: cardNum,
     	regType: "1",
     	telephone: tel,
     	birthday: bir,
     	staffNo: "",
     	regWays: "0"
	};
 	console.log(JSON.stringify(data))
	$.ajax({
		url:"${ct}/life/booking/getJsonAsHospital?data="+encodeURIComponent(JSON.stringify(data)),
		async:true,
		dataType:"json",
		success:function(result){
			if(result.resultStatus == "01"){
				//alert("提交成功");
				//window.location.href ="${ct}/life/appoint/mybooking?id=&noHead=${param.noHead}";
				popTipShow.alert('温馨提示','提交成功', ['OK'],
					function(e){
					  //callback 处理按钮事件		  
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
						window.location.href ="${ct}/life/appoint/mybooking?id=&noHead=${param.noHead}";
						this.hide();
					  }	
					}
				);
			}else{
				//alert(result.errorMsg);
				webToast(result.errorMsg,"middle",600);
				return;
			}
			
		}
	});
}
</script>
