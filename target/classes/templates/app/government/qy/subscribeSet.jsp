<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!infoTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="decorator" content="blank"/>
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<link rel="stylesheet" href="${ctxStatic}/modules/app/js/mui/mui.min.css">
<script src="${ctxStatic}/modules/app/js/mui/mui.min.js"></script>

<title>政策订阅</title>

<style>
.mui-control-content {background-color: #efeff4;min-height: 215px;}
.mui-control-content .mui-loading {margin-top: 50px;}
body,html,.mui-content,.mui-slider{height: 100%;background: #edeef0;}
.mui-slider-group{height: 94%;}

.mui-checkbox input[type=checkbox]:checked:before, .mui-radio input[type=radio]:checked:before{color: #eb413d;}

button{background: #eb413d;width: 90%;color: #fff;border-radius: 5px;font-size: 16px;border: solid 1px #dd524d;margin-top: 10px;margin-bottom: 15px;}
</style>



<script>

$(document).ready(function(){
	getAllZJ();
	getAllHY();
	selectCheck();
});

/* 镇街 */
function getAllZJ(){
	$.ajax({
		url: "${ct}/QY/train/getAllZJ",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(result){
			console.log(result)
			if(result.errorCode == 0){
				var data = result.data;
				for(var i in data){
					$("#ZJ").append(
						'<div class="mui-input-row mui-checkbox">'+
							'<label>'+data[i].name+'</label>'+
							'<input name="ZJ" id="'+data[i].code+'" value="'+data[i].code+'" type="checkbox">'+
						'</div>'
					)
				}
				$("#ZJ").append('<center><button onclick="save()">保 存</button></center>');
			}else{
				webToast(result.errorMsg,"middle",1000);
				return;
			}
		}
	});
}

/* 行业 */
function getAllHY(){
	$.ajax({
		url: "${ct}/QY/train/getAllHY",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(result){
			console.log(result)
			if(result.errorCode == 0){
				var data = result.data;
				for(var i in data){
					$("#HY").append(
						'<div class="mui-input-row mui-checkbox">'+
							'<label>'+data[i].name+'</label>'+
							'<input name="HY" id="'+data[i].code+'" value="'+data[i].code+'" type="checkbox">'+
						'</div>'
					)
				}
				$("#HY").append('<center><button onclick="save()">保 存</button></center>');
			}else{
				webToast(result.errorMsg,"middle",1000);
				return;
			}
		}
	});
}

/* 标记已选择 */
function selectCheck(){
	$.ajax({
		url: "${ct}/policy/policy/findId?id=${param.id}",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(result){
			console.log(result)
			if(JSON.stringify(result) == "{}"){
				//alert("error");
				webToast("没有订阅记录！","middle",1000);
				return;
			}else if(result.success == "不存在该数据！"){
				webToast("没有订阅记录！","middle",1000);
				return;
			}else{
				var districtcodes = result.districtcodes;
				for(var i in districtcodes){
					var idd = "#" + districtcodes[i];
					$(idd).attr('checked', true)
				}
				var industryids = result.industryids;
				for(var i in industryids){
					var idd = "#" + industryids[i];
					//console.log(idd)
					$(idd).attr('checked', true)
				}
			}
		}
	});
}

/* 遍历保存 */
function save(){
	//alert("${param.loginId}")
    ZJ = document.getElementsByName("ZJ");
    var districtCodes = "";
    for(k in ZJ){
        if(ZJ[k].checked){
            districtCodes += ZJ[k].value + ","
        }
    }
    console.log(districtCodes)
    HY = document.getElementsByName("HY");
    var industryIds = "";
    for(k in HY){
        if(HY[k].checked){
        	industryIds += HY[k].value + ","
        }
    }
    console.log(industryIds)
    
    $.ajax({
		url: "${ct}/policy/policy/insert?id=${param.id}&industryIds="+industryIds+"&districtCodes="+districtCodes,
		type: "GET",
		dataType: "json",
		async: false,
		success: function(result){
			console.log(result)
			if(result.success == true){
				//alert("保存成功！");
				webToast("保存成功！","middle",1000);
				window.location.href = "${ct}/QY/train/subscribe";
			}else{
				//alert("保存失败！");
				webToast("保存失败！","middle",1000);
				return;
			}
		}
	});
}
</script>


</head>
<body>

<!-- <div id="content"></div>

<button onclick="save()">获取</button> -->

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

</body>
</html>