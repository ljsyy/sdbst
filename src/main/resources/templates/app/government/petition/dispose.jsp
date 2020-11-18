<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上信访/信访查询/处理中</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<style type="text/css">
	#show{background-color: #f4f6f8;}
	table{margin:10px 0px;background-color: white;}
</style>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>
</head>
<body>
	
	<div id="show"></div>
	

<script type="text/javascript">
$(function(){
	var wait=false;		//下拉加载等待
	var pageIndex=1;	//请求页数
	var url="${ct}/api/getjson?url=";
	var param="http://202.104.25.196/RXWSSTWeb/visit.web?list*visitTempIsreply=0*pageSize=10*page=";
	url=url+param;
	loadJson(url,pageIndex,true);	//初次加载数据 ==>第三个参数是判断页面数据是否首次加载,是则为true,否则为false
	$(window).scroll(function() {//下拉到底,加载数据
		//scroll(wait,url,pageIndex);
		if(wait){
			return false;
		}
	    if ($(window).scrollTop() +2>= $(document).height() - $(window).height()) {
		  wait=true;	//下拉触发等待
	      pageIndex=pageIndex+1;
	      getJson(url,pageIndex,false);
	    }
	});
});

function getLabel(data){	//设置标签内容
	$(data).each(function(index){
		$("#show").append(
			"<table>"+
				"<tr>"+
					"<td width='90px' style='color:#888;'>受理编号:</td>"+
					"<td>"+data[index].servecode+"</td>"+
					"<td style='text-align:right;'>"+data[index].createdate+"</td>"+
				"</tr>"+
				"<tr>"+
					"<td style='color:#888;'>目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;的:</td>"+
					"<td colspan='2'>"+data[index].aim+"</td>"+
				"</tr>"+
				"<tr>"+
					"<td style='vertical-align: top;color:#888;'>内容摘要:</td>"+
					"<td colspan='2'>"+data[index].remark+"</td>"+
				"</tr>"+
			"</table>"
		);
	})
}	
	
</script>
</body>
</html>