<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/list.css" />
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/list.js"></script>
<script src="${ctxStatic}/modules/app/js/iconfont.js"></script>
<script type="text/javascript">
	$(function(){
		if(${param.noHead}){
			$(".container #search").css({"position":"fixed","left":"0px","top":"0px","right":"0px"});
			$(".container #content").css("margin-top","50px");
		}
	})
</script>
<style type="text/css">
	.container #content .result{background-color:#fff; padding:10px;}
</style>
<title>行政审批</title>
</head>
<body>
<div class="container">
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
		        <span id="title">行政审批</span>
		        <div class="headr">&nbsp;</div>
		    </header>
   		</c:otherwise>
   	</c:choose>
    <!--查询输入框-->
    <div id="search">
        <span>
        	<input type="text" maxlength="20" size="20" name="keyword" id="keyword" placeholder="请输入关键字"  />
        </span>
        <div class="search-r">
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-search"></use>
            </svg>
        </div>
    </div>
    <!--内容-->
    <div id="content"></div>
</div> 

</body>
</html>

<script>
	//获取事项查询名称keyWord
	var keyWord="${keyWord}";
	
	//要访问的url地址，需要把url地址中的&换成*
	var url="http://202.104.25.196/RXWSSTWeb/xzsp.web?itemThemeType";
	
	$(function(){
		//初始化数据
		init();
		
		//点击查询
		$(".container #search .search-r").click(function(){
			searchAndShow();
		});
	});
	
	//初始化数据
	var arr;
	function init(){
		$.ajax({
			url:"${ct}/app/menu/getData?url="+url,
			type:"post",
			data:{"param":"keyWord="+keyWord},
			dataType:"xml",
			success:function(result){
				arr=$(result).find("Response").find("Body").find("items").find("item");
				
				if(arr.length==0){
					$(".container #content").append("<div class='result'>对不起，没有找到您要查询的结果！</div>");
				}else{
					$(arr).each(function(){
						var id= $(this).children("id").text();
						var name= $(this).children("name").text();
						//链接地址					
						var tempUrl="getData?url=/app/menu/interface/guideDetail?id="+id+"&title=详情&noHead=${param.noHead}";
						
						$(".container #content").append("<dl><dt><a href='"+ tempUrl +"'><div class='dl-l'>"+ name +"</div><div class='dl-r'><img src='${ctxStatic}/modules/app/img/interface/right.png' /></div></a></dt></dl>");
					});
				}
			}
		});
	}
	
	//查询显示数据
	function searchAndShow(){
		//清空数据
		$(".container #content").html("");
		//获取关键字
		var keyword=$(".container #search #keyword").val();
		//去掉空格
		keyword=keyword.replace(/(^\s*)|(\s*$)/g, "");
		
		$(arr).each(function(){
			var id= $(this).children("id").text();
			var name= $(this).children("name").text();
			//链接地址
			var tempUrl="getData?url=/app/menu/interface/guideDetail?id="+id+"&title=详情&noHead=${param.noHead}";
			
			if(  name.indexOf(keyword)>=0 ){
				//筛选查询部分数据显示
				$(".container #content").append("<dl><dt><a href='"+ tempUrl +"'><div class='dl-l'>"+ name +"</div><div class='dl-r'><img src='${ctxStatic}/modules/app/img/interface/right.png' /></div></a></dt></dl>");
			}
			
		});
		
	}
	
</script>