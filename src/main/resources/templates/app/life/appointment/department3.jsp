<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>获取号源</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
.doctor{
    position: relative;
    background: #fff;
    margin: 15px;
    padding: 15px;
}
.doctor img{
	position:absolute;
	width:55px;     
	top: 9px;
}
.word{padding: 0 63px;white-space: nowrap;}
.subject,.doctor-post{color:#999;font-size:13px;}
.doctor img.right-icon{
	width:10px;
	right:25px;
	top: calc(50% - 8px);
}
.doctor .date{border-bottom: solid 1px #D4D4D4;height: 40px;}
.list{position: relative;}
.list .time{
    border-bottom: solid 1px #D4D4D4;
    height: 97px;
    white-space: nowrap;
}
.list .time .top{
    background: #66BEA7;
    width: 60px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    color: white;
    position: relative;
    left: 2px;
    top: 14px;
}
.list .time .bottom{
	background: #66BEA7;
    width: 60px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    color: white;
    position: relative;
    left: 2px;
    top: 25px;
}
.list .time .money{
    color: #F7AE3B;
    font-size:22px;
    white-space: nowrap;
}
.right{
    right: 7px;
    position: absolute;
    top: calc(50% - 15px);
    white-space: nowrap;
}
.right span{
    position: relative;
    top: 6px;
    right: 2px;
    color: #ADAAAA;
}
.right img{position: relative;width: 10px;}
.error{
    background: white;
    margin: 10px;
    text-align: center;
    font-size: 20px;
    padding: 20px 0px;
}
</style>

<script>
var imgstr='<img src="${ctxStatic}/modules/app/img/appoint/choose.png" class="img-green" />';

$(document).ready(function(){
	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
	
	var myDate   = new Date();
	var year     = myDate.getFullYear();
	var month    = (myDate.getMonth() + 1);
	var date     = myDate.getDate();
	var hours    = myDate.getHours();
	var minutes  = myDate.getMinutes();
	var newtime  = hours*60 + minutes;
	var chooseT  = "${param.time}";
	var newdata  = year + "-" + month + "-" + date;
	$.ajax({
		url:"${ct}/life/booking/getJsonAsHospital?data="+escape('{hospitalCode:"${param.hospitalCode}",sourceId:"${param.sourceId}",sourceType:"${param.sourceType}",regDate:"${param.time}",firstOrReturn:"1"}'),
		async:true,
		dataType:"json",
		success:function(result){
			load_ani.remove();
			var json = result.resultInfo.rows;
			console.log(json)
			if(result.resultInfo.total != 0){
				for(var doc in json){
				
					var sum = json[doc].registerFee+json[doc].treatmentFee;
					var docname = 'name=${param.name}';
					var tr  = '&tr=' + sum;
					var sub = '&sub=${param.sub}';
					var time = '&time=${param.time} ' + json[doc].regStartTime + '-' + json[doc].regEndTime	;
					var hospitalCode = '&hospitalCode=${param.hospitalCode}';
					var departmentCode = '&departmentCode='+json[doc].departmentCode	;
					var diviFlag = '&diviFlag='+json[doc].diviFlag;
					var doctorId = '&doctorId='+json[doc].doctorId	;
					var href = "'${ct}/life/booking/department4?"+docname + sub + tr + time + hospitalCode + departmentCode + diviFlag + doctorId + "&noHead=${param.noHead}'";
					
					var yuyuetime = parseInt((json[doc].regEndTime).split(":")[0]) * 60 +  parseInt((json[doc].regEndTime).split(":")[1]);
					var sigs =json[doc].leaveSigs
					if(chooseT ==  newdata){
						if(sigs > 0 && yuyuetime > newtime){
							var guahao = "可挂号"
						}else{
							var guahao = "不可挂号"
								href = ""
						}
					}else{
						 if(sigs > 0){
							 var guahao = "可挂号"
						 }else{
							 var guahao = "不可挂号"
									href = ""
						 }	
					}
					$(".time-list").append(
						'<div class="list" onclick="window.location.href=' + href + '">'+
							'<div class="time">'+
								'<div class="top">' + json[doc].regStartTime + '</div>'+
								'<div class="bottom">' + json[doc].regEndTime + '</div>'+
								'<img src="${ctxStatic}/modules/app/img/appoint/2-1.png" style="position: relative;width: 11px;left: 67px;top: -32px;" />'+
								'<p style="position: relative;left: 83px;top: -74px;width: 50px;font-size: 18px;"><span class="money">'+ sum +'</span>元</p>'+
								'<div class="right">'+
									'<span>' + guahao + '</span>'+
									'<img src="${ctxStatic}/modules/app/img/appoint/right.png" />'+
								'</div>'+
							'</div>'+
						'</div>'
					)
				}
			}else{
				$(".time-list").append(
					'<div class="error">暂无可预约时间</div>'
				);
			};
		}
	});	
});


</script>

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
	        <span id="title">号源</span>
	        <div class="headr"></div>
	    </header>
  	</c:otherwise>
</c:choose>

<!-- 医生列表 -->
<div class="doctor">
	<img src="${ctxStatic}/modules/app/img/appoint/face.png" />
	<div class="word">
		<p class="doctor-name">${param.name}</p>
		<p class="doctor-post">${param.post}</p>
		<p class="subject">${param.sub}</p>
	</div>
	<img src="${ctxStatic}/modules/app/img/appoint/right.png" class="right-icon" />
</div>

<!-- 选择 -->
<div class="doctor">
	<div class="date">
		<%-- <img src="${ctxStatic}/modules/app/img/appoint/turnLeft.png" style="position: absolute;left: 15px;top: 11px;" /> --%>
		<p style="text-align:center;white-space: nowrap;">${param.time}</p>
		<%-- <img src="${ctxStatic}/modules/app/img/appoint/turnRight.png" style="position: absolute;right: 15px;top: 11px;" /> --%>
	</div>
	
	<div class="time-list">
		
	</div>
	
</div>
	
	



</body>
</html>