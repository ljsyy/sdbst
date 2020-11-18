<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!infoTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="decorator" content="blank"/>
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>

<title>我的咨询</title>

<style>
body{height: 100%;width: 100%;}
body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.container {padding: 0;max-width: 750px;}
 html,.container{height: 100%;}
 html{background:#fff;}
body{background: #F3F3F3;}
.container{text-align: center;}
.main{background: white;    height: 100%;}
.content{}

.time{text-align: center;}
.keyword{text-align: center;}
.digest{}
img{width: 100%;height: auto;padding: 13px 0px;}

.title{border-bottom: solid 1px #e5e5e5;}
.title span{color:#999;}
.title1 span{color:#999;}
.title p{padding: 10px;}
.title1 p{padding: 10px;}
.reply{border-bottom: solid 1px #e5e5e5;}
.reply p{padding: 10px;}
.reply span{color:#999;    line-height: 30px;    font-size: 15px;}
.none{height: 10px;background: #F3F3F3;}
</style>



<!-- 获取政策列表 -->
<script>
$(document).ready(function(){
	var login = '${qylogin}';
	if(login == ""){
		queren();
		return false;
	}else{
		detail();
	}
});
	
function queren(){
	var se=confirm("您好，请先登录!");
	if (se==true){
  		//alert("你按下的是【确认】");
		window.location.href ="${ct}/government/qy/login?&noHead=true";
  	}else{
  		//alert("你按下的是【取消】");
  		window.history.go(-1);
  	}
};
	
function detail(){
	var url = "${ct}/api/getjson";
	var data = "http://sdztc.shunde.gov.cn/QYZTCWS/faq.do?getMyFaqRunById*id=${param.id}*token=${qylogin.token}"
	//alert("id:${param.id},token=${qylogin.token}");
	$.ajax({
		url: url,
		data: "url=" + escape(data),
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			var data = result.data;
			var answer;
			if(data.answer == "" || data.answer == undefined || data.answer == null){
				answer = "暂未回复";
			}else{
				answer = data.answer;
			}
			//alert(answer)
			$(".main").append(
				'<div class="content">'+
					'<div class="title">'+
						'<p>咨询问题：'+
							'<span>'+data.title+'</span>'+
						'</p>'+
					'</div>'+
					'<div class="title1">'+
						'<p>问题描述：'+
							'<span>'+data.question+'</span>'+
						'</p>'+
					'</div>'+
					'<div class="none"></div>'+
					'<div class="reply">'+
						'<p>回复部门：'+
							'<span>'+data.typeName+'</span>'+
						'</p>'+
					'</div>'+
					'<div class="reply">'+
						'<p>回复内容：'+
							'<span>'+answer+'</span>'+
						'</p>'+
					'</div>'+
				'</div>' 
			)	
		}
	});
}
	


</script>


</head>
<body>
	<!--头部-->
<%-- 	<c:choose>
	  	<c:when test="${param.noHead}"></c:when>
	  		<c:otherwise>
		    <header id="head">
		        <div class="headl retrun-icon"> 
		   		    <svg class="icon" aria-hidden="true">
		            	<use xlink:href="#icon-toLeft-copy"></use>
		            </svg>
		        </div>
		        <span id="title">用户信息/修改会员信息</span>
		        <div class="headr"></div>
		    </header>
	  	</c:otherwise>
	</c:choose> --%>
	
	
	
	
	<div class="main">

	</div>

</body>
</html>