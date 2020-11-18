<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${ri.headName} </title>
<meta name="decorator" content="blank"/>
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/detail.css"/>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/app/js/detail.js"></script>
</head>
<body>
	
	<div id="show"></div>

<script type="text/javascript">
		var wait=false;		//下拉加载等待
		var pageIndex=0;	//请求页数
	$(function(){
		var result=eval(${ri.data});
		if(${ri.returntype=="pagey"}){
			getLabel(result);
		}else{
			getLabel(result.obj);
		}
		
		$(window).scroll(function() {//下拉到底,加载数据
			//postScroll(wait,url,pageIndex);
			if(wait==true){
				return false;
			}
		    if ($(window).scrollTop() +2>= $(document).height() - $(window).height()) {
		    	//alert("pageIndex:"+pageIndex);
			  wait=true;	//下拉触发等待
			  if(${ri.returntype==null||ri.returntype=="json"}){
			      pageIndex=pageIndex+10;
				  url=url+"&post=true";
			  }else if(${ri.returntype=="page" || ri.returntype=="pagey"}){
				  pageIndex=pageIndex+1;
			  }
			  var url="${ct}/api/aftergetjson?function=${ri.function}&pageIndex="+pageIndex;
			  getJson(url,false);
		    } 
		}); 
	});
		

	function getJson(url,flag){
		$.ajax({
			url:url,
			dataType: "json",
			success:function(data){
				if(data!=null && data!=""){
					if(${ri.returntype=="pagey"}){
						showData(data);
					}else{
						showData(data.obj,flag);
					}
				}else{
					alert("没有加载到数据！");
				}
			},
			error:function(){
				alert("出错啦...");
			}
			
		});
	}
	
	//处理展示数据
	function showData(data,flag){
		if(data!=null && data!=""){
			if(flag){	//判断是否初次加载数据
				$("#show").children().remove();	//重新加载前清空 <div id="list">
			}
			getLabel(data);	
			wait=false;
		}else{
			alert("没有加载到数据！");
		}
	}

	
	function getLabel(data){
		$(data).each(function(index){
			var word="";
			if(${ri.returntype=="pagey"}){
				word=data[index].title;
			}else{
				word=data[index].bt;
			}
			word=getWord(word);
			
			if(${ri.returntype==null || ri.returntype=="json"}){
				var hr="onclick=\"window.location.href=\'${ct}/api/getdetail?fid="+data[index].fold_id+"&id="+data[index].id+"&titleName=${ri.titleName}&headName=${ri.headName}/详情&jsp=/modules/government/trade/detail\'\"";
				$("#show").append(
					"<div class='list' " +hr+ ">"+
							word+"<span>"+data[index].cjrq+"</span>"+
					"<img src='${ctxStatic}/modules/app/img/menu/right.png'></div>"
				);
			}else if(${ri.returntype=="page"}){
						// "<a href='http://sdcin.shunde.gov.cn/viewtownzbgg.php?id="+data[index].id+"'>"+
				var hr="onclick=\"window.location.href=\'http://sdcin.shunde.gov.cn/viewtownzbgg.php?id="+data[index].id+"\'\"";
				$("#show").append(
					"<div class='list' "+hr+" style='background:url(${ctxStatic}/modules/app/img/menu/right.png) no-repeat right center;background-size:20px 20px;'>"+
							word+"<span>"+data[index].cjrq+"</span>"+
					"</div>"
				);
			}else if(${ri.returntype=="pagey"}){
						// "<a href='"+data[index].url+"'>"+
				var hr="onclick=\"window.location.href=\'"+data[index].url+"\'\"";
				$("#show").append(
					"<div class='list' "+hr+" style='background:url(${ctxStatic}/modules/app/img/menu/right.png) no-repeat right center;background-size:20px 20px;'>"+
							word+"<span>"+data[index].date+"</span>"+
					"</div>"
				);
			}
		});
			data=null;
	}
	
</script>
</body>
</html>