<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>政策订阅</title>
<meta name="decorator" content="blank"/>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/mui/mui.min.css">
<script src="${ctxStatic}/modules/app/js/mui/mui.min.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style>
.mui-control-content {background-color: #efeff4;min-height: 215px;}
.mui-control-content .mui-loading {margin-top: 50px;}
body,html,.mui-content,.mui-slider{height: 100%;background: #edeef0;}
.mui-slider-group{height: 94%;}

.mui-table-view-cell > a:not(.mui-btn){display: flex !important;align-items: center;}
.mui-table-view-cell:after{left: 0px;height: 2px;}
.mui-table-view-cell p{font-size: 13px;}
.mui-slider .mui-slider-group .mui-slider-item img{height: auto;}
.title{overflow: hidden;white-space: nowrap;text-overflow: ellipsis;font-size: 15px !important;color: #000;}

#touch {width: 55px;height: 55px;position: absolute;right: 3%;bottom: 8%;margin-left: -30px;margin-bottom: -30px;z-index: 99999;line-height: 55px;background: #000000;text-align: center;color: #fff;border-radius: 50%;opacity: 0.4;}

.error{text-align: center;}
.error img{width: 60% !important;padding-top: 20%;}
.error p{padding: 15px;font-size: 16px;}
</style>

<script>
var selectId;
var loginId;

$(document).ready(function(){
	
	chooseSys();
	
	/* 订阅设置 */
	$("#touch").click(function(){
		window.location.href = "${ct}/QY/train/subscribeSet?id="+loginId;
	});
});

/* 判断客户端 */
function chooseSys(){
	if(mui.os.wechat){
		//alert("wechat");
		isLoginWX();
	}else if(mui.os.ios){
		//alert("ios");
		aaa.call();		//调用IOS返回值
	  	loginId = str1;
		//alert(str1);
	}else if(mui.os.android){
		//alert("android");
		loginId = myobj.useridshow();	//安卓返回值
	}else{
		//alert("other");
		isLoginWX();
	}
	if(loginId == "" || loginId == undefined || loginId == null){	//未登录百事通
		//alert("未登录");
		popTipShow.alert('温馨提示','请先登录百事通', ['知道了'],
				function(e){
				  //callback 处理按钮事件		  
				  var button = $(e.target).attr('class');
				  if(button == 'ok'){
					//按下确定按钮执行的操作
					//todo ....
					this.hide();

					if(mui.os.wechat){
						//alert("wechat");
						window.location.href ="${ct}/person/login?type=subscribe&noHead=true";
					}else if(mui.os.ios){
						//alert("ios");
						aaa.pushViewControllerTitle('SecondViewController','secondPushedFromJS');	//跳到IOS登录页，登陆后刷新页面
					}else if(mui.os.android){
						//alert("android");
						myobj.updateshow();	//跳到安卓登录页，登陆后刷新页面
					}else{
						//alert("other");
						window.location.href ="${ct}/person/login?type=subscribe&noHead=true";
					}
					
				  }	
				}
			);
		
	}else{	//已登录百事通
		//alert("已登录");
		have();
	}
}

/* 查询已订阅 */
var code;
function have(){
	load_ani.open("pointloading");//开始动画
	$.ajax({
		url:"${ct}/policy/policy/findId?id="+loginId,
		type: "GET",
		dataType:"json",
		async: true,
		success:function(result){
			console.log(result)
			if(JSON.stringify(result) == "{}" || result.success == "不存在该数据！"){
				//alert("error");
				$("#ZJ").append(
					'<div class="error">'+
						'<img alt="" src="${ctxStatic}/modules/app/img/QyTrain/error.png" />'+
						'<p>暂时没有数据，请前往订阅设置</p>'+
					'</div>'
				)
				$("#HY").append(
					'<div class="error">'+
						'<img alt="" src="${ctxStatic}/modules/app/img/QyTrain/error.png" />'+
						'<p>暂时没有数据，请前往订阅设置</p>'+
					'</div>'
				)
			}else{
				console.log(result.districtcodes)
				console.log(result.industryids)
				if(result.districtcodes == "" || result.districtcodes == null || result.districtcodes == undefined){
					$("#ZJ").append(
						'<div class="error">'+
							'<img alt="" src="${ctxStatic}/modules/app/img/QyTrain/error.png" />'+
							'<p>暂时没有数据，请前往订阅设置</p>'+
						'</div>'
					)
				}else{
					var districtcodes = result.districtcodes;
					for(var i in districtcodes){
						code = districtcodes[i];
						showListZJ();
					}
				}
				if(result.industryids == "" || result.industryids == null || result.industryids == undefined){
					$("#HY").append(
						'<div class="error">'+
							'<img alt="" src="${ctxStatic}/modules/app/img/QyTrain/error.png" />'+
							'<p>暂时没有数据，请前往订阅设置</p>'+
						'</div>'
					)
				}else{
					var industryids = result.industryids;
					for(var i in industryids){
						code = industryids[i];
						showListHY();
					}
				}
			}
			load_ani.remove();//结束动画
		}
	});
}

/* 镇街列表 */
function showListZJ(){
	$.ajax({
		url: "${ct}/QY/train/getList?&typeCodes=&districtCode="+code+"&scaleCode=&industryCode=&gradeCode=&keyword=&start=0&limit=1000",
		type: "GET",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			var data = result.data;
			$("#ZJ").append('<ul class="mui-table-view">');
			for(var i in data){
				var imgUrl;
				if(data[i].imgUrl == "" || data[i].imgUrl == undefined || data[i].imgUrl == null){
					imgUrl = "${ctxStatic}/modules/app/img/QyTrain/icon1.png";
				}else{
					imgUrl = data[i].imgUrl;
				}
				var pubUnit;
				if(data[i].pubUnit == "" || data[i].pubUnit == undefined || data[i].pubUnit == null){
					pubUnit = "暂无部门记录";
				}else{
					pubUnit = data[i].pubUnit;
				}
				var publicDate;
				if(data[i].publicDate == "" || data[i].publicDate == undefined || data[i].publicDate == null){
					publicDate = "暂无时间记录";
				}else{
					publicDate = data[i].publicDate;
				}
				$("#ZJ ul").append(
					'<li class="mui-table-view-cell mui-media">'+
				        '<a href="${ct}/QY/train/newDetail?id='+data[i].id+'">'+
				            '<img class="mui-media-object mui-pull-left" src="'+imgUrl+'">'+
				            '<div class="mui-media-body">'+
				                '<p class="title">'+data[i].title+'</p>'+
				                '<p class="mui-ellipsis">'+pubUnit+'</p>'+
				                '<p class="mui-ellipsis">'+publicDate+'</p>'+
				            '</div>'+
				        '</a>'+
				    '</li>'
				)
			}
			$("#ZJ").append('</ul>');
		}
	});
}

/* 行业列表 */
function showListHY(){
	$.ajax({
		url: "${ct}/QY/train/getList?&typeCodes=&districtCode=&scaleCode=&industryCode="+code+"&gradeCode=&keyword=&start=0&limit=1000",
		type: "GET",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			var data = result.data;
			$("#HY").append('<ul class="mui-table-view">');
			for(var i in data){
				var imgUrl;
				if(data[i].imgUrl == "" || data[i].imgUrl == undefined || data[i].imgUrl == null){
					imgUrl = "${ctxStatic}/modules/app/img/QyTrain/icon1.png";
				}else{
					imgUrl = data[i].imgUrl;
				}
				var pubUnit;
				if(data[i].pubUnit == "" || data[i].pubUnit == undefined || data[i].pubUnit == null){
					pubUnit = "暂无部门记录";
				}else{
					pubUnit = data[i].pubUnit;
				}
				var publicDate;
				if(data[i].publicDate == "" || data[i].publicDate == undefined || data[i].publicDate == null){
					publicDate = "暂无时间记录";
				}else{
					publicDate = data[i].publicDate;
				}
				$("#HY ul").append(
					'<li class="mui-table-view-cell mui-media">'+
				        '<a href="${ct}/QY/train/newDetail?id='+data[i].id+'">'+
				            '<img class="mui-media-object mui-pull-left" src="'+imgUrl+'">'+
				            '<div class="mui-media-body">'+
				            	'<p class="title">'+data[i].title+'</p>'+
				                '<p class="mui-ellipsis">'+pubUnit+'</p>'+
				                '<p class="mui-ellipsis">'+publicDate+'</p>'+
				            '</div>'+
				        '</a>'+
				    '</li>'
				)
			}
			$("#HY").append('</ul>');
		}
	});
}

/* 查询微信登录返回值 */
function isLoginWX(){
	$.ajax({
		url:"${ct}/sys/userOthers/getuserid",
		data:"mobileLogin=true",
		type: "POST",
		dataType:"json",
		async:false,
		success:function(result){
			console.log(result)
			loginId = result.data;
		}
	});
}
</script>

<!-- IOS专用 -->
<script type="text/javascript">
var call = function(){
    var callInfo = JSON.stringify({"jianshu": "http://www.jianshu.com/users/55c8fdc3c6e7/latest_articles"});
        tianbai.getCall(callInfo);
}
var Callback = function(str){
    str1 = str;	
    return str1;
}
var alerCallback = function(){
    alert('成功');
}
</script>

</head>
<body>


<div class="mui-content">
	<div id="slider" class="mui-slider">
		<div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
			<a class="mui-control-item" href="#item1mobile">镇街区域</a>
			<a class="mui-control-item" href="#item2mobile">行业分类</a>
		</div>
		
		<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-6"></div>
		
		<div class="mui-slider-group">
		
			<!-- 镇街区域 -->
			<div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
				<div id="scroll1" class="mui-scroll-wrapper">
					<div class="mui-scroll" id="ZJ">	
				
			
					</div>
				</div>
			</div>
			
			<!-- 行业分类 -->
			<div id="item2mobile" class="mui-slider-item mui-control-content">
				<div id="scroll2" class="mui-scroll-wrapper">
					<div class="mui-scroll" id="HY">
				
			
					</div>
				</div>
			</div>
			
			
		</div>
		
	</div>
	
	<!-- 浮动（设置） -->
	<div id="touch" style="width: 55px; height: 55px; position: absolute;">设置</div>

</div>

<script>
mui.init({
	swipeBack: false
});
(function($) {
	$('.mui-scroll-wrapper').scroll({
		indicators: true //是否显示滚动条
	});
})(mui);
</script>

<!-- <script>

// 最好放在DIV下方，避免DIV没有加载完，获取不到
$('#touch').on('touchmove', function(e) {

    // 阻止其他事件
    //e.preventDefault();

    // 判断手指数量
    if (e.originalEvent.targetTouches.length == 1) {

        // 将元素放在滑动位置
        var touch = e.originalEvent.targetTouches[0];  

        $("#touch").css({'left': touch.pageX + 'px',
            'top': touch.pageY + 'px'});
    }
});
</script> -->


</body>
</html>