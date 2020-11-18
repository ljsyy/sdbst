<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0, user-scalable=yes">

<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<style type="text/css">
body, dd, dl, html, ol, p, ul {margin: 0;padding: 0;list-style-type: none;font-family: -apple-system,BlinkMacSystemFont,"PingFang SC","Hiragino Sans GB","Microsoft Yahei",\5b8b\4f53,Tahoma,Arial,Helvetica,STHeiti;}
.content{
	    overflow: scroll;
}
.list .title{
	border-bottom: solid 1px #e5e5e5;
    height: 40px;
    line-height: 40px;
    padding: 5px 10px;
}
.list .title .xiala{
    float: right;
    margin-top: 16px;
}
.list .title .xiala.up{
    transform: rotate(180deg);
    -ms-transform: rotate(180deg);
    -moz-transform: rotate(180deg);
    -webkit-transform: rotate(180deg);
    -o-transform: rotate(180deg);
}
.list .info.close{
	display:none;
	
}
.title span{font-size: 20px;    margin-left: 8px;}
.icon{    vertical-align: middle;
    height: 23px;
    position: relative;
    margin-top: -7px;}
.info{background: #fff;}
.out{
    margin: 10px;
    border: solid 1px #eb413d;
    border-radius: 5px;
    background: #f9f2f2;
}
.out .up{    position: relative;border-bottom: solid 1px #eb413d;    padding: 5px 0px;}
.name{padding: 0px 30px 0 10px;width: 100%;box-sizing: border-box;display: block;}
.down{    padding: 5px 10px;color: #888888;}
.location{}
.location-img{height: 20px;width: 20px;position: absolute;top: 0;right: 5px;bottom: 0;margin: auto;}
.location-img img{height:100%;width:100%;}
.area{}
.number{    float: right;}
.localtion{}

</style>
<title>顺德区主要避难场所</title>
<script>
$(document).ready(function(){
	$(".list .title").click(function(){
		if($(this).parent().find(".info").hasClass("open")){
			$(this).parent().find(".info").removeClass("open").addClass("close");
			$(this).find(".xiala").removeClass("up");
		}else{
			$(this).parent().find(".info").removeClass("close").addClass("open");
			$(this).find(".xiala").addClass("up");
			
		}

	});
});

</script>
</head>
<body>

<div class="container">
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/shelter.png" />
	    	<span>大良</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div class="out">
    			<div class="up">
    				<span class="name">南江社区南霞星光之家</span>
					<div class="location-img"><img src="${ctxStatic}/modules/app/img/inshunde/location.png"></div>		
    			</div>
    			<div class="down">
    				<p class="location">位置：大良南霞小市场边</p>
    				<span class="area">面积（㎡）：	300</span>
    				<span class="number">容纳人数（人）：150</span>
    			</div>
    		</div>
    		
    		<div class="out">
    			<div class="up">
    				<span class="name">南江社区南霞星光之家</span>
					<div class="location-img"><img src="${ctxStatic}/modules/app/img/inshunde/location.png"></div>  			
    			</div>
    			<div class="down">
    				<p class="location">位置：大良南霞小市场边</p>
    				<span class="area">面积（㎡）：	300</span>
    				<span class="number">容纳人数（人）：150</span>
    			</div>
    		</div>
    	</div>
    </div>
     
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/shelter.png" />
	    	<span>大良</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div class="out">
    			<div class="up">
    				<span class="name">南江社区南霞星光之家</span>
					<div class="location-img"><img src="${ctxStatic}/modules/app/img/inshunde/location.png"></div>		
    			</div>
    			<div class="down">
    				<p class="location">位置：大良南霞小市场边</p>
    				<span class="area">面积（㎡）：	300</span>
    				<span class="number">容纳人数（人）：150</span>
    			</div>
    		</div>
    		
    		<div class="out">
    			<div class="up">
    				<span class="name">南江社区南霞星光之家</span>
					<div class="location-img"><img src="${ctxStatic}/modules/app/img/inshunde/location.png"></div>  			
    			</div>
    			<div class="down">
    				<p class="location">位置：大良南霞小市场边</p>
    				<span class="area">面积（㎡）：	300</span>
    				<span class="number">容纳人数（人）：150</span>
    			</div>
    		</div>
    	</div>
    </div>
   
    
</div> 

</body>
</html>
