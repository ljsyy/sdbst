<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择地区</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
html{background-color: white;}
.round{
    background-color: white;
    width: 15px;
    height: 15px;
    margin-top: 11px;
    position: absolute;
    left: 10px;
    border: solid 1px #ccc;
    border-radius: 50%;
}
.main{background-color: white;}
.main .area-title{
	height: 46px;
    line-height: 46px;
    color: #9B9B9B;
    font-size: 17px;
    padding-left: 33px;
}
.main .area-list{
	height: 40px;
    line-height: 40px;
    background-color: white;
}
.main .area-list>p,.main .area-list>a{
    border-top: 1px solid #E1DEDE;
	padding-left: 20px;
    font-size: 16px; 
}
.main .area-list>p:last-child{border-bottom: 1px solid #E1DEDE;}
.area-left{display: inline;}
.area-title{display: inline;}
.round{
	background-color: white;
    width: 15px;
    height: 15px;
    margin-top: 14px;
    position: absolute;
    left: 10px;
    border: solid 1px #eb3f29;
    border-radius: 50%;
}
.solid{
    background-color: #eb3f29;
    width: 9px;
    height: 9px;
    margin-top: 2px;
    position: absolute;
    left: 2px;
    border: solid 1px #eb3f29;
    border-radius: 50%;
}
.tips{height: 40px;background-color: #F6F7F7;}
.tips p{
    background-color: #F9F4D8;
    line-height: 25px;
    position: absolute;
    left: 15px;
    height: 25px;
    margin-top: 7px;
    width: 89%;
    font-size: 12px;
    white-space: nowrap;
}
</style>

<script type="text/javascript">
var appLabel = {
	main : ".main",
	arealeft : ".area-left",
    areatitle : ".area-title",
	arealist : ".area-list",
}
var area = {
	region:"${ct}/life/booking/getJsonAsHospital?data="+escape('{region:"all"}'),	
	urlCode:"${ct}/life/booking/getJsonAsHospital?data=",
};
area.ajax = function(url,data,callback){
	$.ajax({
		url:eval("this." + url) + data,
		async:true,
		dataType:"json",
		success:callback
	});
}
area.click = {	
	//选择医院
	
	district : function(data){
		var lab      = appLabel;
		var arealist = lab.arealist;
		//var arealeft = lab.arealeft;
		var areatitle= lab.areatitle;
		var obj      = data.data.obj;
		//------
		var areacode = $(this).attr("areacode");
		$(arealist).empty();
		//$(arealeft).text("");
		$(areatitle).text("请选择医院");
		
		
		obj.ajax("urlCode",escape('{areaCode:"'+areacode+'"}'),function(result){
			var json = result.resultInfo;
			console.log(json);
			for(var hos in json){
				$(arealist).append("<p hospitalCode="+json[hos].hospitalCode+" hospitalName="+json[hos].hospitalName+">"+json[hos].hospitalName+"</p>");
			}
			$(arealist + " p").unbind().bind("click",{obj : obj},obj.click.hospital);
		});
	},
	//选择门诊
	hospital :function(data){
		var lab      = appLabel;
		var arealist = lab.arealist;
		//var arealeft = lab.arealeft;
		var areatitle= lab.areatitle;
		var obj      = data.data.obj;
		//------
		var hospitalcode = $(this).attr("hospitalCode");
		var hospitalname = $(this).attr("hospitalName");
		$(arealist).empty();
		//$(arealeft).text(hospitalname);
		$(areatitle).text(hospitalname);
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		obj.ajax("urlCode",escape('{hospitalCode:"'+hospitalcode+'"}'),function(result){
			load_ani.remove();
			var json = result.resultInfo;
			console.log(json);
			if(result.resultStatus == "01"){
				for(var depar in json){
					$(arealist).append("<p hospitalCode=" + json[depar].hospitalCode + " departmentCode="+json[depar].departmentCode + ">" + json[depar].departmentName + "</p>");
				}
				$(arealist + " p").unbind().bind("click",{obj : obj},obj.click.href)
			}else if(result.resultStatus == "02"){
				$(arealist).append("<p>该医院暂时无法预约</p>");
			};
		});
	},
	href : function(data){
		var obj = data.data.obj;
		var hospitalcode = $(this).attr("hospitalCode");
		var departmentCode = $(this).attr("departmentCode");
		window.location.href="hospital3?departmentCode="+departmentCode+"&hospitalCode="+hospitalcode+"&type=area&noHead=${param.noHead}";
	}
}
//选择地区
area.init = function(){
	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
	var obj = this;
	this.ajax("region","",function(result){
		load_ani.remove();
		var json = result.resultInfo;
		console.log(json);
		var lab = appLabel;
		var arealist = lab.arealist;
		//var arealeft = lab.arealeft;
		var areatitle= lab.areatitle;
		//$(arealeft).text("");
		$(areatitle).text("请选择地区");
		for(var area in json){
			if(json[area].districtName != "顺德区")
				$(arealist).append("<p areaCode="+json[area].districtCode+">"+json[area].districtName+"</p>");
		}
		
		$(arealist + " p").unbind().bind("click",{obj : obj},obj.click.district);
	});
}

$(document).ready(function(){
	area.init();
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
	        <span id="title">选择地区-医院-科室</span>
	        <div class="headr"></div>
	    </header>
  	</c:otherwise>
</c:choose>

<!-- 地区、医院、科室列表 -->
<div class="main">
	<!-- <p class="area-left"></p> -->
	<div onclick="javascript:location.reload();">
		<div class="round"><div class="solid"></div></div>
		<p class="area-title""></p>
	</div>
	<div class="tips">
		<p>温馨提示：您可点击上面重新选择</p>
	</div>
	<div class="area-list">
		
		
	</div>
</div>

</body>
</html>