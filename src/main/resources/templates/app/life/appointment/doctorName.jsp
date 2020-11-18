<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按医生名</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>

<style>
.date-list{}
.date{
	display:flex;
	align-items: center;
    text-align: center;
}
.date .format{
    position: relative;
	flex:1;    
	padding: 10px 0;
	border: solid 1px transparent;
	border-bottom: solid 1px #ccc;
	  
}
.date .format.border-green{ border: solid 1px #36B494;    color: #37B494; }
.date .format.border-green p{border-right:solid 1px transparent;}
.date .format.border-green .img-green{position: absolute;top: 0;height: 38px;right: 0;}
.date .format p{ border-right:solid 1px #ccc;}
.date .format p span.top{}
.date .format p span.bottom{
    font-weight: bold;
    line-height: 30px;
}
.doctor-list{}
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
.word{padding: 0 63px;white-space: nowrap;    height: 60px;}
.doctor-name{}
.number,.doctor-post{color:#999;font-size:13px;}
.num{color:#1CB13E;}
.doctor img.right-icon{
	width:10px;
	right:5px;
	top: calc(50% - 8px);	
}
.submit{
    color: white;
    font-size: 23px;
    text-align: center;
}
.submit input{
    vertical-align: middle;
    line-height: 30px;
    height: 30px;
    border: 0;
    width: 80%;
    margin: 12px 6px;
    font-size: 16px;
    padding: 0 0 0 5px;
}
.submit input:hover{outline:none;}
.submit img{vertical-align: middle;}
.search{height: 40px;}
#name{}
</style>

</head>

<script>
$(document).ready(function(){

});

function submitClick(){
	var name = $("#name").val();
	if(name == ""){
		//alert("请填入内容")
		webToast("请填入内容","middle",600);
		return ;
	}
	//按医生名预约
	
	var data={
		doctorName : name,
		regDate : ""
	};
	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
	$.ajax({
		url:"${ct}/life/booking/getJsonAsAppointment?data="+encodeURIComponent(JSON.stringify(data)),
		//url:"${ct}/life/booking/getJsonAsDept?data="+encodeURIComponent('{supDeptName:"${param.supDeptName}",subDeptName:"${param.subDeptName}",regDate:'+date+'}'),//按科室预约
		async:true,
		dataType:"json",
		success:function(result){
			load_ani.remove();
			$(".doctor-list").empty();
			var json = result.resultInfo.rows;
			console.log(json)
			//alert();
			if(result.errorMsg == ""){
				for(var doc in json){
					var href = "'${ct}/life/booking/regSrcInfo?hospitalCode="+json[doc].hospitalCode+"&sourceType="+json[doc].sourceType+"&sourceId="+json[doc].sourceId+"&departmentName="+json[doc].departmentName+"&noHead=${param.noHead}'";
					$(".doctor-list").append(
						'<div class="doctor" onclick="window.location.href='+href+'">'+
							'<img src="${ctxStatic}/modules/app/img/appoint/face.png" />'+
							'<div class="word">'+
								'<p class="doctor-name">' + json[doc].sourceName +'（'+ json[doc].doctorLevel +'）</p>'+
								'<p class="doctor-post">' + json[doc].departmentName +'</p>'+
								 '<p class="number">' + json[doc].hospitalName + '</p>'+ 
							'</div>'+
							'<img src="${ctxStatic}/modules/app/img/appoint/right.png" class="right-icon" />'+
						'</div>'
					)
				};
			}else{
				$(".doctor-list").append(
					'<div>'+result.errorMsg+'</div>'
				)
			}
		}
	});
	
}
//});

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
	        <span id="title">按医生名预约</span>
	        <div class="headr"></div>
	    </header>
  	</c:otherwise>
</c:choose>

<!-- 搜索框 -->
<div class="date-list">
	<div class="search">
		<div class="submit" type="submit"><input type="text" id="name" placeholder="请输入医生名" />
			<img src="${ctxStatic}/modules/app/img/map/sousuo.jpg" onclick="submitClick()" height="35"/>
		</div>
	</div>
</div>

<!-- 医生列表 -->
<div class="doctor-list">

	
</div>



</body>
</html>