	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择日期获取号源</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
.date-list{background-color:white;}
.date{
	display:flex;
	align-items: center;
    text-align: center;
	
}
.date .format{
    position: relative;
	flex:1;    
	border-bottom: solid 1px #ccc;
}
.date .format.border-green{ border: solid 1px #36B494;    color: #37B494; }
.date .format.border-green p{border-right:solid 1px transparent;}
.date .format.border-green .img-green{position: absolute;top: 0;height: 38px;right: 0;}
.date .format p{}
.date .format p span.top{}
.date .format p span.bottom{font-weight: bold;}
.doctor-list{	}
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
.doctor{
    position: relative;
  	background: #fff;    
  	margin: 10px;
    padding: 5px;
}
.doctor img{
	position:absolute;
	width:55px;     
	top: 9px;
}
.word{
	padding: 0 63px;
	white-space: nowrap;    
	height: 60px;
}
.doctor-name{}
.number,.doctor-post{color:#999;font-size:13px;}
.num{color:#1CB13E;}
.doctor img.right-icon{
	width:10px;
	right:5px;
	top: calc(50% - 8px);
	
}
.doctor-list{
    position: relative;
    background: #fff;
    margin: 15px;
    padding: 15px;
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
.right img{
    position: relative;
    width: 10px;
    top: 7px;
}
.error{
    background: white;
    margin: 10px;
    text-align: center;
    font-size: 20px;
    padding: 20px 0px;
}
</style>

</head>

<script>
var imgstr='<img src="${ctxStatic}/modules/app/img/appoint/choose.png" class="img-green" />';
var today = "";
var chooseday = "";
$(document).ready(function(){
	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
	//日期
	$.ajax({
		url:"${ct}/life/booking/getJsonAsHospital?data="+escape('{systemDate:"all"}'),
		async:false,
		dataType:"json",
		success:function(result){
			load_ani.remove();
			var json = result.resultInfo.rows;
			console.log(json)
			var nowmonth = (json[0].sysDate == 1) ? (json[0].sysMonth + 1) - 1 : (json[0].sysMonth + 1);
			nowmonth = (nowmonth == 0) ? 12 : nowmonth;
			var str   = '';
			str += '<div class="date">';
	/* 		for(var y = 0 ; y < 2; y++){
				str += '<div class="date"><div class="format border-green"><p><span class="top">' + nowmonth + '月</span><br><span class="bottom">今日</span></p></div>'; 
			/* 	for(var date = 4*y; date < (4*(y+1)); date++){	 */
				for(var date =0; date < 7; date++){		
					var defualt = "";
					var month = json[date].sysMonth + 1 ;
					var sdate = json[date].sysDate;
					if(month < 10){month = "0" + month;};
					if(sdate < 10){sdate = "0" + sdate;};
					if(date == 0){
						defualt = "border-green"
					}
					str +='<div date="'+json[date].sysYear + '-' + month + '-' + sdate	+'" class="format ' + defualt + '">'+
							'<p><span class="top">' + month + '月</span><br>'+
							   '<span class="bottom">' + json[date].sysDate + '日</span>'+
							'</p>'+
						  '</div>';	  
				}
				str += "</div>";	
			/* } */
			//var str = '<div class="format"><p><span class="top">' + nowmonth + '月</span><br><span class="bottom">今日</span></p></div>'
			$("#date").append(str);
			$("#date .date .format").bind("click",function(){
				$(".doctor-list").empty();
				var parent = $(this).parent().parent();
				var date   = $(this).attr("date");
				//if(date == undefined) date = today;
				parent.find(".date .format").attr("class","format");
				$(this).attr("class","format border-green");
				chooseday = date;
				ajax(date)
			});
		}
	});
	var myDate=new Date();
	today = chooseday = $(".border-green").attr("date");
	ajax(today);
	function ajax(date){
		
		//console.log('{hospitalCode:"${param.hospitalCode}",departmentCode:"${param.departmentCode}",doctorName:"",regDate:'+date+'}')
		//获取号源
			
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		$.ajax({
			//url:"${ct}/life/booking/getJsonAsHospital?data="+escape('{hospitalCode:"${param.hospitalCode}",sourceType:"${param.sourceType}",sourceId:"${param.sourceId}",regDate:"'data'",firstOrReturn:"1"}'),
			url:"${ct}/life/booking/getJsonAsHospital?data="+escape('{hospitalCode:"${param.hospitalCode}",sourceType:"${param.sourceType}",sourceId:"${param.sourceId}",firstOrReturn:"1",regDate:'+date+'}'),
			async:true,
			dataType:"json",
			success:function(result){
				//alert(date);
				load_ani.remove();
				var json = result.resultInfo.rows;
				console.log(json)
				if(result.resultInfo.total != 0){
				for(var doc in json){
				
				var sum = json[doc].registerFee+json[doc].treatmentFee;
				var docname = 'name='+ json[doc].sourceName;
				var tr  = '&tr=' + sum;
				var sub = '&sub=${param.departmentName}';
				var time = '&time='+ json[doc].visitDate + " " + json[doc].regStartTime + '-' + json[doc].regEndTime
				var hospitalCode = '&hospitalCode=${param.hospitalCode}'
				var departmentCode = '&departmentCode='+json[doc].departmentCode
				var diviFlag = '&diviFlag='+json[doc].diviFlag
				var doctorId = '&doctorId='+json[doc].doctorId
				//var departmentName = '&departmentName=${param.departmentName}';
				var href = "'${ct}/life/booking/department4?"+docname + sub + tr + time + hospitalCode + departmentCode + diviFlag + doctorId + "&noHead=${param.noHead}'";
				
				var sigs =json[doc].leaveSigs
				if(sigs > 0){
					var guahao = "可挂号"
				}else{
					var guahao = "不可挂号"
						href = ""
				}
				$(".doctor-list").append(
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
				)};
			}
			else{
				$(".doctor-list").append(
						'<div class="error">暂无可预约时间</div>'
				);	
			}
			}
		});
	}
	
});




</script>




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

<!-- 日期 -->
<div class="date-list">
	<div id="date">
	
	</div>
	
</div>

<!-- 医生列表 -->
<div class="doctor-list">

	
</div>



</body>
</html>