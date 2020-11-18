<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${headName }</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>
</head>
<body>
	<div id="show"></div>
	

<script type="text/javascript">
	$(function(){
		var url="${ct}/api/getjson?post=true&url=";
		var param="http://202.104.25.196/RXWSSTWeb/news.web?newsDetail*id=${id}_${fid}*jsonpcallback=jQuery2130817426473066914_1499848806348*_=1499848806349";
		url=url+param;
		var callName="jQuery2130817426473066914_1499848806348";
		loadJsonp(url,callName);	//初次加载数据
	});
	
	function getLabel(data){
		$(data).each(function(index){
			//alert(data[index].bt);
			$("#show").append(
				"<h3 style='text-align:center;'>"+data.bt+"</h1>"+
				"<p style='border-bottom:1px gray solid;text-align:center;'>发布时间:"+data.cjrq+" "+data.cjsj+"</p>"	
				+data.content
			);
		});
	}
</script>
</body>
</html>