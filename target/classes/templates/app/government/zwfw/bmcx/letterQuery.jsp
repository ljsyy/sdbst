<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/base.css" />
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/letterQuery.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<script src="${ctxStatic}/modules/app/js/lib/vue.js"></script>
<title>信访查询</title>
</head>
<body>
    <div id="vue-change">
	    <!--  菜单 -->
	    <div id="menu"> 
	    	<li v-on:click="update('replied.content')" class="li-red" >已回复</li> 
	        <li v-on:click="update('dispose.content')" >处理中</li> 
	    </div><br/>
		<!-- 内容 -->
		<div class="content" v-html="msg"><div></div></div>
	</div>
</body>
</html>

<script type="text/javascript">
$(function(){
	//获取已回复的数据
	var replied={"url":"http://202.104.25.196/RXWSSTWeb/visit.web?list&visitTempIsreply=1&pageSize=10&page=",
				 "content":"",
				 "pageIndex":"1"};
	getContent(replied);
	
	//获取处理中的数据
	var dispose={"url":"http://202.104.25.196/RXWSSTWeb/visit.web?list&visitTempIsreply=0&pageSize=10&page=",
				 "content":"",
				 "pageIndex":"1"};
	getContent(dispose);
	
	//当前页面内容，默认加载已回复的内容
	var current="replied.content";
	
	//变更内容
	var app=new Vue({
		el:"#vue-change",
		data: { msg:replied.content },
		methods: {
			update: function(data){
				this.msg=eval('(' + data + ')');
				current=data;
			}
		}
	});
	
	//变更菜单下划线
	$("#vue-change #menu li").click(function(){
		$("#vue-change #menu li").removeClass("li-red");
		$(this).addClass("li-red");
		
	});
	
	//下拉到底,加载数据
	$(window).scroll(function() {
		
	    if ($(window).scrollTop() >= $(document).height() - $(window).height()) {
	    	
	    	if(current=="replied.content"){
				//已回复页面
	    		replied.pageIndex++;
		    	getContent(replied);
		    	$("#vue-change .content").html(replied.content);
		    	
			}else{
				//处理中页面
				dispose.pageIndex++;
		    	getContent(dispose);
		    	$("#vue-change .content").html(dispose.content);
			}
	    }
	});
	
});

//获取内容
function  getContent(obj){
	obj.url=obj.url.replace("&", "*");
	$.ajaxSetup({async : false});
	$.ajax({
		url:"${ct}/app/menu/getjson?url="+obj.url+obj.pageIndex,
		type:"post",
		dataType:"json",
		success:function(result){
			var arr=result.obj;
			$(arr).each(function(index){
				obj.content+="<div class='text'><table border='0' cellpadding='5' cellspacing='0'><tr> <td width='30%'>受理编号:</td> <td>"+ arr[index].servecode +"</td> </tr><tr> <td>日期：</td> <td>"+ arr[index].createdate +"</td> </tr><tr> <td>目的</td> <td>"+ arr[index].aim +"</td> </tr><tr> <td>内容摘要：</td> <td>"+ arr[index].remark +"</td> </tr></table></div>";
			});
		}
	});
	
}
</script>