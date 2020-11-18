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
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script type="text/javascript">
	$(function(){
		if(${param.noHead}){
			$(".container #search").css({"position":"fixed","left":"0px","top":"0px","right":"0px"});
			$(".container #content").css("margin-top","50px");
		}
	})
</script>

<style>
.container #search .search-r{padding: 0;color: #676565;font-weight: 600;font-size: 16px;}
</style>

<title>${param.name}街镇服务</title>
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
		        <span id="title">${param.name}街镇服务</span>
		        <div class="headr">&nbsp;</div>
		    </header>
   		</c:otherwise>
   	</c:choose>
    <!--查询输入框-->
    <div id="search">
        <span>
        	<input type="text" maxlength="20" size="20" name="keyword" id="keyword" placeholder="请输入关键字"  />
        </span>
        <div class="search-r">查询
            <!-- <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-search"></use>
            </svg> -->
        </div>
    </div>
    <!--内容-->
    <div id="content"></div>
    <!-- 弹框 -->
</div> 

</body>
</html>

<script>
	//获取地区名称areaName
	var areaName="${param.name}";
	//地区areaId
	var areaId="";
	//要访问的url地址
	var url="";
	
	$(function(){
		//获取地区 areaId
		getAreaId( areaName );
		
		//获取要访问的地址，&转换为*
		url="http://202.104.25.196/RXWSSTWeb/xzsp.web?itemUnitType*unitId=*areaId="+areaId;
		
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
			url:"${ct}/app/menu/getjson?url="+url,
			type:"post",
			dataType:"xml",
			success:function(result){
				arr=$(result).find("Response").find("Body").find("items").find("item");
				$(arr).each(function(){
					var id= $(this).find("id").text();
					var name= $(this).find("name").text();
					//链接地址
					var tempUrl="getData?url=/app/menu/interface/areaDetail?id="+id+"*areaId="+areaId+"&title=详情&noHead=${param.noHead}";
					
					$(".container #content").append("<dl><dt><a href='"+ tempUrl +"'><div class='dl-l'>"+ name +"</div><div class='dl-r'><img src='${ctxStatic}/modules/app/img/interface/right.png' /></div></a></dt></dl>");
				});
			}
		});
	}
	
	//查询显示数据
	function searchAndShow(){
		//获取关键字
		var keyword=$(".container #search #keyword").val();
		//去掉空格
		keyword=keyword.replace(/(^\s*)|(\s*$)/g, "");
		if(keyword==''){
			/* prompt.init();	//初始化弹框
			prompt.print("请输入您要查询的关键字！",false,[
				{
					name : "确定",
					color : "#eb413d",
					callback: function(){
						$("#prompt").attr("class","prompt");
					}
				}
			]); */
			webToast("请输入您要查询的关键字！","middle",1000);
			//$(".container #search #keyword").focus();
			return;
		}
		var str="";
		var count=0;
		$(arr).each(function(){
			var id= $(this).find("id").text();
			var name= $(this).find("name").text();
			//链接地址
			//var tempUrl="getData?url=/app/menu/interface/areaDetail?id="+id+"*areaId="+areaId+"&title=详情&noHead=${param.noHead}";
			var tempUrl="${ct}/app/menu/interface/areaDetail?id="+id+"*areaId="+areaId+"&title=详情&noHead=${param.noHead}";
			if(  name.indexOf(keyword)>=0 ){
				//筛选查询部分数据显示
				str+="<dl><dt><a href='"+ tempUrl +"'><div class='dl-l'>"+ name +"</div><div class='dl-r'><img src='${ctxStatic}/modules/app/img/interface/right.png' /></div></a></dt></dl>";
				count++;
			}
			
		});
		if(count==0){
			/* prompt.init();	//初始化弹框
			prompt.print("对不起，没有找到您要查询的结果！",false,[
				{
					name : "确定",
					color : "#eb413d",
					callback: function(){
						$("#prompt").attr("class","prompt");
					}
				}
			]); */
			webToast("对不起，没有找到您要查询的结果！","middle",1200);
		}else{
			//显示查询结果
			$(".container #content").html(str);
		}
	}
	
	//根据地区名称 areaName 获取地区 areaId
	function getAreaId( name ){
		switch( name ){
			case "大良":
				areaId="440606002";
				break;
			case "容桂":
				areaId="440606001";
				break;
			case "伦教":
				areaId="440606004";
				break;
			case "勒流":
				areaId="440606003";
				break;
			case "北滘":
				areaId="440606009";
				break;
			case "陈村":
				areaId="440606005";
				break;
			case "乐从":
				areaId="440606010";
				break;
			case "龙江":
				areaId="440606007";
				break;
			case "杏坛":
				areaId="440606008";
				break;
			case "均安":
				areaId="440606006";
				break;
			default:
				areaId="440606002";		//默认大良
		
		}
	}
	
</script>