<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择日期获取医生列表</title>
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
.word{padding: 0 63px;white-space: nowrap;height: 60px;}
.doctor-name{}
.number,.doctor-post{color:#999;font-size:13px;}
.num{color:#1CB13E;}
.doctor img.right-icon{
	width:10px;
	right:5px;
	top: calc(50% - 8px);
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
				//alert(chooseday)
				ajax(date)
			});
		}
	});
	var myDate=new Date();
	today = chooseday = $(".border-green").attr("date");
	ajax(today);
	function ajax(date){
		//console.log('{hospitalCode:"${param.hospitalCode}",departmentCode:"${param.departmentCode}",doctorName:"",regDate:'+date+'}')
		//获取医生列表
		if("${param.type}" == "area"){
			var	url = "${ct}/life/booking/getJsonAsHospital?data="+escape('{hospitalCode:"${param.hospitalCode}",departmentCode:"${param.departmentCode}",doctorName:"",regDate:'+date+'}')//按医院医院
			//alert(date);
		}
		if("${param.type}" == "department"){
			var	url = "${ct}/life/booking/getJsonAsDept?data="+encodeURIComponent('{supDeptName:"${param.supDeptName}",subDeptName:"${param.subDeptName}",regDate:'+date+'}')//按医院医院
		}
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		$.ajax({
			url:url,//按医院医院
			//url:"),//按科室预约
			async:true,
			dataType:"json",
			success:function(result){
				load_ani.remove();
				var json = result.resultInfo.rows;
				console.log(json)
				if(result.resultInfo.total != 0){
				for(var doc in json){
					var href = "'${ct}/life/booking/department3?hospitalCode="+json[doc].hospitalCode+"&departmentCode="+json[doc].departmentCode+"&sourceId="+json[doc].sourceId+"&sourceType="+json[doc].sourceType+"&name="+json[doc].sourceName+"&post="+json[doc].doctorLevel+"&sub="+json[doc].departmentName+"&time="+chooseday+"&noHead=${param.noHead}'";
					$(".doctor-list").append(
							'<div class="doctor" onclick="window.location.href='+href+'">'+
								'<img src="${ctxStatic}/modules/app/img/appoint/face.png" />'+
								'<div class="word">'+
									'<p class="doctor-name">' + json[doc].sourceName + '</p>'+
									'<p class="doctor-post">' + json[doc].doctorLevel + '</p>'+
									/* '<p class="number">剩余<span class="num">20</span>个号</p>'+ */
								'</div>'+
								'<img src="${ctxStatic}/modules/app/img/appoint/right.png" class="right-icon" />'+
							'</div>')
					};
				}else{
					$(".doctor-list").append(
							'<div class="error">暂无可预约医生</div>'
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
	        <span id="title">医生列表</span>
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