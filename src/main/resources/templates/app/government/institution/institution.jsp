<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构职能</title>
<meta name="decorator" content="blank"/>

<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<style type="text/css">
	
</style>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>
</head>
<body>

	
	<div id="title">
		<div id="1">领导班子</div>
		<div id="2">职能部门</div>
		<div id="3">街镇领导</div>
	</div>
	
		<div id="show1">
			<dl>
			</dl>
		</div>
		<div id="show2">Hello world! two</div>
		<div id="show3">Hello world! three</div>
	

<script type="text/javascript">


	$(function(){
		$("#1").css("border-bottom","2px #eb413d solid").css("color","#eb413d");// 初始化样式
		$("div[id^='show']").hide();
		$("#show1").show(); 
		
		var url="${ct}/api/getjson?url=";
		var param="http://202.104.25.196/RXWSSTWeb/news.web?leaderList&fid=";
		url=url+escape(encodeURI(param));
		loadJson(url,"",true);
		
		$("#title div").click(function(){ // title 相应样式重置
			$("#title div").css("border-bottom","").css("color","#888");
			$(this).css("border-bottom","2px #eb413d solid").css("color","#eb413d");
			$("div[id^='show']").hide();
			$("#show"+$(this).attr("id")).show();
		}) 
		
	});
	
	function img2Click(data){
		//alert(data);
		if($("#"+data).next().is(":hidden")){
			$("#"+data).children(".img2").attr("src","${ctxStatic}/modules/app/img/menu/xiala.png");
		}else{
			$("#"+data).children(".img2").attr("src","${ctxStatic}/modules/app/img/menu/shangla.png");
		}
		
		$("#"+data).nextUntil("dt").toggle(300);
	}
	
	function getLabel(data){
		//alert(data[0].items[0].name);
		//$("#show").append("<div id='show1'><dl></dl></div>")
		$(data).each(function(index){
			//alert(data[index].title);
			var onclick="img2Click('a"+index+"')";
			$("#show1 dl").append(
				"<dt id='a"+index+"' class='dt'><img class='img1' src='${ctxStatic}/modules/app/img/menu/note.png' width='20px' height='25px'/>"+
				 data[index].title +
				"<img class='img2' onclick="+onclick+" width='23px' height='15px;' src='${ctxStatic}/modules/app/img/menu/shangla.png'/></dt>"		
			);
			
			var items=data[index].items;
			$(items).each(function(index){
				$("#show1 dl").append(
					"<dd class='dd'><img  src='${ctxStatic}/modules/app/img/menu/oye.png' width='15px' height='15px'/>"+
					 "<a href='${ct}/government/institution/detail?id="+items[index].id+"_"+items[index].fid+"&no="+items[index].no+"'>"+items[index].name+"</a>"+
					"</dd>"	
				);
			});
		})
		$("dl dd").hide();
	}
	
</script>
</body>
</html>