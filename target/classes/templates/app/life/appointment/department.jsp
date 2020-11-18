<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择科室</title>
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
    padding-left: 10px;
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
</style>

<script type="text/javascript">
var appLabel = {
	main : ".main",
    areatitle : ".area-title",
	arealist : ".area-list",
}
var area = {
	departmentName:"${ct}/life/booking/getJsonAsDept?data="+escape('{departmentName:""}'),	
	urlCode:"${ct}/life/booking/getJsonAsDept?data=",
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
	//选择科室2
	hospital :function(data){
		var lab      = appLabel;
		var arealist = lab.arealist;
		var areatitle= lab.areatitle;
		var obj      = data.data.obj;
		//------
		var departmentCode = $(this).attr("departmentCode");
		var departmentName = $(this).attr("departmentName");
		
		
		$(arealist).empty();
		$(areatitle).text("重新选择"); 
		
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		
		obj.ajax("urlCode",encodeURI('{supCode:"'+departmentCode+'",departmentName:"'+departmentName+'"}'),function(result){
			load_ani.remove();
			
			var json = result.resultInfo.rows;
			console.log(json);
			for(var depar in json){
				$(arealist).append("<p supDeptName=" + json[depar].remark + " subDeptName=" + json[depar].departmentName + ">" + json[depar].departmentName + "</p>");
			}
			$(arealist + " p").unbind().bind("click",{obj : obj},obj.click.href);
		});
	},
	href : function(data){
		var obj = data.data.obj;
		var supDeptName = $(this).attr("supDeptName");//一级
		var subDeptName = $(this).attr("subDeptName");//二级
		window.location.href="hospital3?supDeptName="+supDeptName+"&subDeptName="+subDeptName+"&type=department&noHead=${param.noHead}";
	}
}
//选择科室1
area.init = function(){
	load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
	var obj = this
	this.ajax("departmentName","",function(result){
		load_ani.remove();
		
		var json = result.resultInfo.rows;
		console.log(json);
		var lab = appLabel;
		var arealist = lab.arealist;
		var areatitle= lab.areatitle;
		$(areatitle).text("请选择科室");
		for(var area in json){
				$(arealist).append("<p departmentCode="+json[area].departmentCode+" departmentName="+json[area].departmentName+">"+json[area].departmentName+"</p>");
		}
		
		$(arealist + " p").unbind().bind("click",{obj : obj},obj.click.hospital);
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
	        <span id="title">选择科室</span>
	        <div class="headr"></div>
	    </header>
 	</c:otherwise>
</c:choose>

<!-- 科室列表 -->
<div class="main">
	<p class="area-title" onclick="javascript:location.reload();"></p>
	<div class="area-list">
		
		
	</div>
</div>

</body>
</html>