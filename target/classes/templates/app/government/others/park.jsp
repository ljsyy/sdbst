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
.icon{vertical-align: middle;}
.info{background: #F6F6F6;padding: 0px 1em 0px 3em;}
.info p{color: #eb413d;padding: 3px 0px;}
.info div{
    display: flex;
    padding: 3px;
}
.info div text-col{   display:block
}
.info div text-col.l{color: #eb413d;    white-space: nowrap;
}
.info div text-col.r{    padding-left: 8px;
}
.info span{color: #6e6e6e;padding-left: 10px;}
</style>
<title>顺德区各镇街公园</title>
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
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>均安镇文化广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">均安镇华安路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>均安镇翠湖公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">均安镇翠湖路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>李小龙乐园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">均安镇沙浦村</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">9：00~5：30</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">40</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">军人、65岁以上（佛山户口）的老人、70岁以上（非佛山本地户口）老人，及1.1米以下的儿童免费；60岁以上半票；佛山工会会员有优惠；记者凭记者卡免费</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">生态公园</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">200万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">200盏亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">5个治安岗亭</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>伦教文化广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">伦教南苑路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">广场内有大型计生浮雕；各项康体、儿童娱乐设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">43000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>伦教一景公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">伦教荔村荔景路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">25000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>伦教滨江公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">伦教滨江路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">56000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有（街区驻点）</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>人民公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇人民南路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">49100</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>牛岗公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇丰华南路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">13300</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>天湖公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇丰华南路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">128000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>金紫公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇龙峰北路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5:00-22:00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">42400</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>紫云公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇人民北路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">74580</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>里海公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇里海路东头街</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">2028</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>文化广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">龙江镇丰华北路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天候</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">39500</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>花溪公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道容桂大道</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休闲、游乐；各项康体、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">6.9万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、庭院灯、太阳能灯等</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>文塔公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道桂洲大道中</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休闲、游乐；各项康体、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约6万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、庭院灯、太阳能灯等</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>狮山公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道狮山环路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：00－23：30</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐；老干活动中心</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约4万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、庭院灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>大凤山公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道德胜路60号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：00－22：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休闲、游乐；各项康体、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约16.7万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>凤岭公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道环山路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：00－22：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐、标准泳池及儿童池、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约13.3万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>榕园公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道体育路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：00－22：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">4.3万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>海旁公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道乐安北路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：00－22：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐、健身设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">1.3万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>千禧广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道振华路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">6500</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>白莲池公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道白莲路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">8：00－17：30</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">2元（18岁以下半票，凭退休证免票）</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">观音堂</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约4000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">射灯、路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>眉蕉头公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">容桂街道容桂大道东</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">6：00—22：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休闲、游乐设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">45000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯共约46支</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>乐从文化公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从镇乐从大道B136号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">150000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>乐从河滨公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从镇怡乐路A87号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">8000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>乐从儿童公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从镇荔中路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">5000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>乐从美德公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从镇莲塘二路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">6000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>乐从红棉东公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从镇红棉东路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">5000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>乐从东区体育公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从德富材料城内</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">部分场地收费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">网球场、足球场收费，其余免费</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">篮球场、足球场、网球场、羽毛球场、门球场、乒乓球台</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">30000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>乐从南片区中心公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">乐从南区</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">篮球场、门球场、休憩</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">45000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>北滘公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘新城区东一路与林上北路交汇处</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集娱乐、优闲、水乡特色为一体的新型主题公园</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">130000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>北滘市民广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北窖镇新城区</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集娱乐、优闲、游乐、水乡特色为一体的新型广场</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">60000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>北滘百福公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘济虹路与林上南路交汇处</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集健身、文化、娱乐为一体</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">30000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>北滘蓬莱公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北窖镇蓬莱路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集娱乐、优闲、文化为一体</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">22000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>北滘中山公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘济虹路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集健身、文化、娱乐为一体</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">7000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>西窖文化公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘镇西滘村委会</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集健身、文化、娱乐为一体综合性公园</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">18000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>龙涌公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘镇龙涌村委会</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集健身、文化、娱乐为一体综合性公园</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">30000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>碧江民乐公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘碧江泰宁东路16号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">5：30-11：30 2：00-20：30</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">部分健身、娱乐设施免费</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">集健身、文化、娱乐为一体</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>金楼公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">北滘碧江泰宁路6号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">8：30-17：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">个人：15元  团体：10元</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">本村60岁以上老人免费参观一次</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">以明清古建筑为主</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>锦岩公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良锦岩路45号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">6:00--24:00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">2</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">45岁以上人员免费入园</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">内设烧烤场</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">28300</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>钟楼公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良凤山东路1号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">内设健身广场和儿童游乐场</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">68990</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">亭园灯、路灯、高灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>顺峰山公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良南国东路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">内设自行车径、步行径、登山径、德懿艺术馆等休闲、文化设施</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">8209亩</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、亭园灯、高灯、景观灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>桂畔公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良桂畔路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">91000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、亭园灯、岸灯、景观灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>凤岭公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良凤山中路97号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">4:30--23:00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">139028</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">亭园灯、路灯、景观灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>西山庙</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良文秀路西山庙</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">8：00－17：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">5元</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">旅行团8折，60岁佛山户口免票，学生集体进场免票</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>清晖园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良清晖路23号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">8：00－18：00</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">15元</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">20人以上8折优惠，符合《佛山市老人优惠办法》给予老人优惠</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>德胜广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">大良德民路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">35.4万</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、亭园灯、高灯、景观灯、射灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">有</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>杏坛文化广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">杏坛镇建设路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>杏坛北河门诊公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">杏坛镇建设六路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">800</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>杏坛新涌公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">杏坛新涌大桥脚</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">1500</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>麦庄公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">杏坛逢简村委边</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">4000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>高翔公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">杏坛村高翔组</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">全天开放</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">8000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">路灯、照明灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>树林公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">勒流龙升北路路口</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">人工湖</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约20000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>勒流政府广场</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">勒流政和中路1号</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">喷泉、优闲、健身广场</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约20000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>勒流江义怡情公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">勒流江义大道</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">优闲、健身</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约20000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>勒流黄连人民公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">勒流黄连萧地大道</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">开放式</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">免费</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">优闲、健身</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约13000</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">有</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>西广场公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">陈村镇政和路镇政府旁</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约22043</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>沙洲公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">陈村镇旧圩沙洲路
    		</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约14304</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-g.png" height="30">
	    	<span>钊林公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">陈村镇安宁路</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约41929</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">一个</text-col></div>
    	</div>
    </div>
     <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-r.png" height="30">
	    	<span>中山公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">陈村镇安宁路中山桥旁</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约3108</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、亭园灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
    <div class="list">
    	<div class="title">
	    	<img class="icon" src="${ctxStatic}/modules/app/img/inshunde/park-b.png" height="30">
	    	<span>四季公园</span>
	    	<img class="xiala" src="${ctxStatic}/modules/app/img/menu/shangla.png" height="10">
    	</div>
    	<div class="info close">
    		<div><text-col class="l">地址:</text-col><text-col class="r">陈村镇白陈路南涌桥旁</text-col></div>
    		<div><text-col class="l">开放时间:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">门票（元）:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">优惠政策:</text-col><text-col class="r">无</text-col></div>
    		<div><text-col class="l">公园主要设施:</text-col><text-col class="r">休憩、游乐</text-col></div>
    		<div><text-col class="l">公园面积（平方米）:</text-col><text-col class="r">约30901</text-col></div>
    		<div><text-col class="l">照明设备配置情况:</text-col><text-col class="r">高灯、亭园灯、太阳能灯</text-col></div>
    		<div><text-col class="l">治安岗亭配置情况:</text-col><text-col class="r">无</text-col></div>
    	</div>
    </div>
    
</div> 

</body>
</html>
