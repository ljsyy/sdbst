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
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<title>我的预约</title>

<style>
.line{    
	background: white;
    margin: 12px;
    position: relative;
    padding: 15px;
}
.submit{    
    background: #6BB39D;
    width: 80px;
    text-align: center;
    border-radius: 5px;
    position: absolute;
    right: 10px;
    top: 10px;
    color: white;
    height: 30px;
    line-height: 30px;
}
.c_submit{    
    background: #9E9E9E;
    width: 80px;
    text-align: center;
    border-radius: 5px;
    position: absolute;
    right: 10px;
    top: 10px;
    color: white;
    height: 30px;
    line-height: 30px;
}
.error{background: #fff;padding: 17px;}
.line img{height: 67px;float: left;margin: 11px 11px 0 0;}
.right{display: inline-block;}
.right span{}
.right p{
	font-size: 15px;
    margin: 3px 0 0 0;
    color: #a1a1a1;
}
</style>
<script type="text/javascript">

//查询预约
$(document).ready(function(){

	var data = {
    	empi: "${appoinctlogin.empi}",
    	hospitalCode: "",
	    infotorName:"",
	    regDate:"",
	    regWays:"0"
	};
 	console.log(JSON.stringify(data))
 	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
 	$.ajax({
		url:"${ct}/life/booking/getJsonAsAppointment?data="+encodeURIComponent(JSON.stringify(data)),
		async:true,
		dataType:"json",
		success:function(result){
			load_ani.remove();
			var json = result.resultInfo.rows;
			console.log(json)
			if(result.resultInfo.total != 0){	//成功
				for(var info in json){
					if(json[info].orderStatus == "1"){ //未取消
						$(".list").append(
							'<div class="line" regSerialNumber=' + json[info].regSerialNumber + ' hospitalCode='+json[info].hospitalCode + '>'+
							'<img src="${ctxStatic}/modules/app/img/appoint/face.png" />'+
								'<div class="right">'+
									'<span>' + json[info].doctorName + '</span>'+
									'<p>' + json[info].departmentName + '</p>'+
									'<p>' + json[info].hospitalName + '</p>'+
									'<p>' + json[info].regDate + ' ' + json[info].timeDiviBegin + '-' + json[info].timeDiviEnd + '</p>'+
									'<div class="submit" onclick="submitClick($(this))" type="submit">取消预约</div>'+
								'</div>'+
							'</div>'
						)
					}else{	//已取消
						 $(".list").append(
							 '<div class="line" regSerialNumber=' + json[info].regSerialNumber + ' hospitalCode='+json[info].hospitalCode + '>'+
								'<img src="${ctxStatic}/modules/app/img/appoint/face.png" />'+
									'<div class="right">'+
										'<span>' + json[info].doctorName + '</span>'+
										'<p>' + json[info].departmentName + '</p>'+
										'<p>' + json[info].hospitalName + '</p>'+
										'<p>' + json[info].regDate + ' ' + json[info].timeDiviBegin + '-' + json[info].timeDiviEnd + '</p>'+
										'<div class="c_submit" type="submit">已取消</div>'+
									'</div>'+
								'</div>'
						)
					}
				
				}
			}else{	//失败
				$(".list").append(
						'<div class="error">暂无预约信息</div>'
				)
			};
		}
	})
});


//取消预约
function submitClick(my){
	var regSerialNumber = my.parent().parent().attr("regSerialNumber");//平台订单号
	var hospitalCode = my.parent().parent().attr("hospitalCode");//医院机构代码
	var data = {
		regSerialNumber: regSerialNumber, //平台订单号
		hospitalCode: hospitalCode	 //医院机构代码
	};
 	console.log(JSON.stringify(data))
 	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
 	$.ajax({
		url:"${ct}/life/booking/getJsonAsAppointment?data="+encodeURIComponent(JSON.stringify(data)),
		async:true,
		dataType:"json",
		success:function(result){
			load_ani.remove();
			if(result.resultStatus == "01"){
				//alert("取消成功");
				webToast("取消成功","middle",600);
				location.reload();
			}else{
				//alert(result.errorMsg);
				webToast(result.errorMsg,"middle",600);
			}
		
		}
	});
};

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
	        <span id="title">我的预约</span>
	        <div class="headr"></div>
	    </header>
  	</c:otherwise>
</c:choose>

<!-- 预约列表 -->
<div class="list">
	
	
</div>


</body>
</html>