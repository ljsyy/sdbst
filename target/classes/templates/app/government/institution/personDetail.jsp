<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构职能/人物简介</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>
</head>
<body>
	<div id="show" class=" pad10"></div>

<script type="text/javascript">
	$(function(){
		var url="${ct}/api/getjson?post=true&url=";
		var param="http://202.104.25.196/RXWSSTWeb/news.web?leader2*id=${id}*no=${no}";
		url=url+param;
		loadPostJson(url,"",true);
	});
	
	function getLabel(data){
		if(data!=null){
			var introduct = data.introduct;
			introduct = introduct.replace(/&#xfffd;/g, "滘");
			$("#show").append(
				"<p style='text-align:center;'><img height='120px' width='96px' src='"+data.face+"'/></p>"+
				"<p style='text-indent:2em;line-height:25px;font-size:16px;'>"+introduct+"</p>"
			);
		}
	}
	
</script>
</body>
</html>