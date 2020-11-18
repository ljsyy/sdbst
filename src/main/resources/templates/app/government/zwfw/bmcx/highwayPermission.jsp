<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>路政许可</title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>

<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
/*搜索*/
#search,#search-details{background: #fff;position: relative;padding: 10px 15px;    box-sizing: border-box;}
#search-details p{margin-bottom: 10px;color:#3A3A3A;padding:0 15px;} 
#search-details div{color:#3A3A3A;padding:0 15px;}
#search-details div li{display: inline-block;border: solid 2px #999999;margin: 0 10px 10px 0; padding:2px 10px;    border-radius: 5px;}
#search-details .details-botton{padding: 8px 0;margin: 0 20px 0 0;flex: 1;background: #EB413D;color: white;border-radius: 5px;}
#search-details .choose{background: #999;color: #fff;}
.line{
	border-bottom: #f4f6f8 2px solid;
    line-height: 50px;
    padding-left: 15px;
}
#xkXmmc,#xkFr,#xkXdr,#xkJdrq{
	height: 30px;
    line-height: 30px;
    text-align: right;
    float: right;
    width: 40%;
    margin-right: 28px;
    margin-top: 11px;
    border: none;
}
.unit{border-bottom: 1px solid #bbbbbb !important;}
</style>
</head>
<body>
   	

	<div id="search-choose">
		<div id="search" onclick="searchshow()" >
			<div style="background: #F4F6F8;border-radius: 5px;">
				<img id="searchimg" src="${ctxStatic}/modules/app/img/map/sousuo.jpg" height="30" style="vertical-align: middle;margin: 0 5px;padding: 5px;"/>
				<span id="searchtext" style="color: #ABA9AB;background: transparent;font-size: 17px;vertical-align: middle;">请输入搜索信息</span>
			</div>
		</div>
		<div id="search-details" style="padding:0;display:none;">
			<div class="line">项目名称<input id="xkXmmc" placeholder="请输入"/></div>
			<div class="line">法定代表人姓名<input id="xkFr" placeholder="请输入"/></div>
			<div class="line">行政相对人名称<input id="xkXdr" placeholder="请输入"/></div>
			<div class="line">许可决定日期<input id="xkJdrq" placeholder="请输入"/></div>
			<p id="button" style="padding-top: 8px;">
			<span id="exit" onclick="exit_no()">取消</span>
			<span id="request_no" onclick="searchrequest()">查询</span>
			</p>
		</div>
	</div>
	
	<div id="show"></div>


<script type="text/javascript">
function exit_no(){	//取消按钮点击事件
	$("#xkXmmc").val("");
	$("#xkFr").val("");
	$("#xkXdr").val("");
	$("#xkJdrq").val("");
	$("#search-details").hide(300);
	$("#searchtext").text("请输入搜索信息");
	$("#searchtext").css("color","#ABA9AB");
	$("#searchimg").css("opacity","1");
}
function searchshow(){	//查询按钮显示点击事件
	$("#search-details").show(300);
	$("#searchtext").text("请输入搜索条件");
	$("#searchtext").css("color","#000");
	$("#searchimg").css("opacity","0");
}
function searchrequest(){	//搜索按钮隐藏点击事件
	$("#search-details").hide(300);
	$("#searchtext").text("请输入搜索信息");
	$("#searchtext").css("color","#ABA9AB");
	$("#searchimg").css("opacity","1");
	pageIndex=1;
	load($("#xkXmmc").val(),$("#xkFr").val(),$("#xkXdr").val(),$("#xkJdrq").val(),pageIndex,true);
}




	$(function(){
		var wait=false;		//下拉加载等待
		var pageIndex=1;
		load("","","","",pageIndex,true);	//初次加载数据
		$("#request").click(function(){//查询事件
			$("#search").hide(300);
			pageIndex=1;
			load($("#xkXmmc").val(),$("#xkFr").val(),$("#xkXdr").val(),$("#xkJdrq").val(),pageIndex,true);
		});
		$(window).scroll(function(){//下拉到底,加载数据
			if(wait){
				return false;
			}
			if($(window).scrollTop()>=$(document).height()-$(window).height()){
				wait=true;
				pageIndex=pageIndex+1;
				load($("#xkXmmc").val(),$("#xkFr").val(),$("#xkXdr").val(),$("#xkJdrq").val(),pageIndex,false);
			}
		});
		
	});
	
	//函数  将读取的数据拼接 显示
	function show(data,flag){
		console.log(data.length);
		if(data!=null && data.length>0){
			if(flag){
				$("#show").children().remove();	//重新加载前清空 <div id="show">
			}
			
			$(data).each(function(index){
				$("#show").append(
					"<div class='unit'>"+
						/* "<p class='title'><img class='img' src='${ctxStatic }/modules/app/img/menu/redpoint.png' />"+data[index].jyjname+"</p>"+ */
						"<p class='content'>"+
						"<b>项目名称:"+data[index].xkXmmc+"</b></br>"+
						"审批类别:"+data[index].xkSplb+"</br>"+
						"行政相对人名称:"+data[index].xkXdr+"</br>"+
						"法定代表人姓名:"+data[index].xkFr+"</br>"+
						"许可决定日期:"+data[index].xkJdrq+"</br>"+
						"许可截止期:"+data[index].xkJzq+"</br>"+
						"许可机关:"+data[index].xkXzjg+"</br>"+
						"许可内容:"+data[index].xkNr+"</p>"+
					"</div>"	
				);
			});
			wait=false;
		}else{
			webToast("没有加载到数据!","middle",3000);
		}
	}
	
	
	//从接口获取数据
	function load(xkXmmc,xkFr,xkXdr,xkJdrq,pageIndex,flag){
		//var url="${ct}/api/getjson?url=";
		var param="/jeesite/dlxk/dlxk/list";
		
		//url=url+param;//
		//url=url+escape(encodeURI(param));
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		$.ajax({
			url:param,
			type:"post",
			data:"pageNo="+pageIndex+"&pageSize=10&xmname="+xkXmmc+"&xzname="+xkXdr+"&fdname="+xkFr+"&xkrq="+xkJdrq,
		    dataType:"json",
			success:function (data) {
				load_ani.remove();
		        if(data!=null){
		        	show(data,flag);
		        }else{
		        	webToast("没有加载到数据!","middle",3000);
		        }
		    }
		});
	}
</script>
</body>
</html>