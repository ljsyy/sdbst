// JavaScript Document
var ct,ctxStatic;
function initLujingFn(jct,jctx){
	ct=jct;
	ctxStatic=jctx;
};


//五级行政审批
$(function(){
	//获取焦点时清空输入框
	$("input[type='text']").focus(function(){
			$(this).val("");
	});	
	//查询按键的点击事件							   
	$("input[type='submit']").click(function(){
			$(this).css("background-color","#F00");
	});
	$("#head #left").hover(
			function(){
				$(this).css("background-color","#E00");
			},
			function(){
				$(this).css("background-color","");
			}
	);
});
//菜单对象
function windowFn(tabname) {
	var name =tabname;//获取菜单对象
	var namewidth=parseInt($(name).css("width"));
	var move="#window_move";//获取移动对象
	var movewidth=parseInt($(move).css("width"));
	var tab=move+" a";
	var distance=160;//左右切换移动距离
	var movedis;//点击时屏幕到点击距离
	var savemovedis=0;//保持距离
	this.init=function(){
		//轮播标签绑定事件
		for(i=0;i<$(tab).length;i++){
			$(tab+":eq("+i+")").bind("click",function(){
				allTab(tab,$(this),"red-color");
				tabindex=$(tab).index($(this));//获取当前标签
			});
		}
		//触屏轮播标签绑定事件
		$(name).bind('touchstart',function(e){ 
			var touch = e.originalEvent.targetTouches[0];
			movedis= touch.pageX;
			$(move).attr("class","");
		});
		$(name).bind('touchmove',function(e){
			var touch = e.originalEvent.targetTouches[0];
			var x= parseInt(movedis-touch.pageX);
			windowObject.touchmove(touchLR(x));
		});
		$(name).bind('touchend',function(e){ 
			var touch = e.originalEvent.changedTouches[0];
			var x= parseInt(movedis-touch.pageX);
			var margin=parseInt($(move).css("margin-left"));		
			$(move).attr("class","transition");
			if(margin>0 || movewidth<namewidth){
				savemovedis=0;
				$(move).css("margin-left",savemovedis);
			}
			if(margin<0 &&  movewidth>namewidth){
				savemovedis=margin;	
				if(margin < namewidth-movewidth){
					savemovedis=namewidth-movewidth;		
				}
				$(move).css("margin-left",savemovedis);
			}

		});
		return null;
	}
	this.touchmove=function(x,savedis){//负：左
		var movenum=savemovedis+x;
		$(move).css("margin-left",movenum);
	}
}



/*处理不同的页面
 * 
 * 
 * 
 * 
 * */
//-------------------------查看
function contantMenuCheckFn(ct, noHead){
	//return '<div id="inquire"><div class="progress-search" style="margin-bottom: 10px;"><div>进度查询</div><form action="#" method="post"><input class="text" type="text" name="number" placeholder="输入受理编号" size="20" maxlength="20" /><br/><br/><input class="submit" type="submit" value="查询" /></form></div><div class="event-search"><div>事项查询</div><form action="'+ct+'/app/menu/interface/itemQueryList?noHead='+noHead+'" method="post"><input class="text" type="text" name="keyWord" placeholder="输入事项名称" size="20" maxlength="20" /><br/><br/><input class="submit" type="submit" value="查询" /></form></div></div>';
	
	return '<div id="inquire"><div class="progress-search" style="margin-bottom: 10px;"><div>进度查询</div><form action="#" method="post" onsubmit="return progressCheckFn(this)"><input class="text" type="text" name="number" placeholder="输入受理编号" size="20" maxlength="20" /><br/><br/><input class="submit" type="submit" value="查询" /></form></div><div class="event-search"><div>事项查询</div><form action="#" method="post"><input class="text" type="text" name="keyWord" placeholder="输入事项名称" size="20" maxlength="20" /><br/><br/><input class="submit" type="submit" value="查询" /></form></div></div>';
}

function progressCheckFn(form){
	var number = form.number.value;
	//去掉空格
	number=number.replace(/(^\s*)|(\s*$)/g, "");
	alert(number);
	if(number==''){
		alert('受理编号不能为空');
		return false;
	}
	return true;
}

//-------------------------公交出行

//-------------------------交通资讯
var gjzxStr;
function gjzxMenuFn(index){
	var url=ct+"/app/menu/getjson?url=";
	gjzxStr='<div id="gjzx">';
	if(index==0){
		url=url+"http://202.104.25.196/RXWSSTWeb/m_affairs.web?clientAffairsList&infoType=xw&pageIndex=0";
	}
	if(index==1){
		url=url+"http://202.104.25.196/RXWSSTWeb/m_affairs.web?clientAffairsList&infoType=gg&pageIndex=0";
	}
	if(index==2){
		url=url+"http://202.104.25.196/RXWSSTWeb/m_affairs.web?clientAffairsList&infoType=tz&pageIndex=0";
	}
	url=url.replace(/&/g,'*');
	$.ajaxSettings.async = false;
	$.getJSON(url,function(result){
		var json=$.parseJSON( result.obj );
		for(x in json)
			gjzxStr+='<div class="list-img"><a href=""> <div class="list-font"><nobr><b>'+json[x].title.text+'</b></nobr><p class="text">'+json[x].introduction.text+'</p><p class="time">'+json[x].publishTime.text+'</p></div><div class="list-into"></div></a></div>';
		gjzxStr+='</div>';
	});
	$.ajaxSettings.async = true;
}

//-------------------------人事信息
var rsxxStr;
function rsxxMenuFn(index,noHead){
	if(index==0){
		inPageFn("/api/noheadtimeList?titleName=人事任免&noHead="+noHead);
	}
	if(index==1){
		inPageFn("/api/noheadtimeList?titleName=人员招录&noHead="+noHead);
	}
//	rsxxStr="";
//	var url=ct+"/app/menu/getjson?url=";
//	var titleName="";
//	if(index==0){
//		url=url+"http://202.104.25.196/RXWSSTWeb/news.web?newList2&fid=10171,30009,110013&needFoldId=true&whereSql=status=%271%27&start=0&limit=10";
//		titleName="人事任免";
//	}
//	if(index==1){
//		url=url+"http://202.104.25.196/RXWSSTWeb/news.web?newList2&fid=10418&needFoldId=false&whereSql=substring(zhbz,2,1)=%271%27&start=0&limit=10";
//		titleName="人员招录";
//	}
//	url=url.replace(/&/g,'*');
//	$.ajaxSettings.async = false;
//    $.getJSON(url,function(result){
//    	console.log(result.obj)
//		for(x in result.obj){
//			var href=ct+"/app/menu/interface/getData?url=/api/detail?id="+result.obj[x].id+"*foldId="+result.obj[x].fold_id+"&title="+titleName;
//			rsxxStr+='<div id="tongyonglist"><a href="'+href+'"> <div class="list-font"><b>'+result.obj[x].bt+'</b><p>'+result.obj[x].cjrq+'</p></div><div class="list-into"></div></a></div>';
//		}
//    });	
//    $.ajaxSettings.async = true;
}


//-------------------------环境质量
var hjzlStr;
var hjzlMap = true;
var hjzlArray=new Array();
//接口获取数据
//function hjzlPush(num,model){
//			  var url=ct+"/life/getjsonHJZL?url=";
//				var weatermode=new Array("综合","PM2.5","PM10","O3_1h","SO2","NO2");
//				var watermode=new Array("综合","PH值","溶解氧","高猛酸盐","氨氮");
//				var weaterArray=new Array(),waterArray=new Array();
//				$.ajaxSettings.async = false;
//				for(var x in weatermode){
//					var newurl=url+escape(encodeURI("http://oa.shunde.gov.cn/negp/pages/Weater/getAirMsg.do?checkType="+weatermode[x]));
//					$.ajax({
//				 		url:newurl,
//						dataType:"json",
//						success:function(result){
//							console.log(result)
//							weaterArray.push(result);
//						}
//				 	});
//				}
//				for(var y in watermode){
//					var newurl1=url+escape(encodeURI("http://oa.shunde.gov.cn/negp/pages/Weater/getWaterMsg.do?checkType="+watermode[y]));
//					 $.ajax({
//					 		url:newurl1,
//							dataType:"json",
//							success:function(result){
//								waterArray.push(result);
//							}
//					 });
//				}
//				$.ajaxSettings.async = true;
//				hjzlArray.push(weaterArray);
//				hjzlArray.push(waterArray);
//				hjzlMenuFn(num,model);
//}
//备份获取数据
function hjzlPush(num,model){
	  var url=ct+"/life/HJZL?type=";
		var weatermode=new Array("空气综合","PM2.5","PM10","O3_1h","SO2","NO2");
		var watermode=new Array("水综合","PH值","溶解氧","高猛酸盐","氨氮");
		var weaterArray=new Array(),waterArray=new Array();
		for(var x in weatermode){
			var newurl=url+weatermode[x];
			$.ajax({
		 		url:newurl,
				dataType:"json",
				async:false,
				success:function(result){
					var j = JSON.parse(result.list.result);
					weaterArray.push(j);
				}
		 	});
		}
		for(var y in watermode){
			var newurl1=url+watermode[y];
			 $.ajax({
			 		url:newurl1,
					dataType:"json",
					async:false,
					success:function(result){
						var j = JSON.parse(result.list.result);
						waterArray.push(j);
					}
			 });
		}
		hjzlArray.push(weaterArray);
		hjzlArray.push(waterArray);
		hjzlMenuFn(num,model);
}
function hjzlMenuFn(index,model){
	
	$("#contant_page").empty();
	hjzlStr="";
	if(index==0){
		var jsonurl=hjzlArray[index];
		var mode=new Array("'综合'","'PM2.5'","'PM10'","'O3_1h'","'SO2'","'NO2'");
		var checktype = 0;
		var nullval={
				siteName : ["东康","新松","金桔咀","勒流","陈村","容桂","北滘","龙江","伦教","乐从","均安","杏坛","苏岗","全区综合"],
		}
		switch(model){
			case "综合":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')" class="red-border">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 0;
				break;
			case "PM2.5":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  class="red-border" onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 1;
				break;
			case "PM10":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  class="red-border" onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 2;
				break;
			case "O3_1h":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  class="red-border" onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 3;
				break;
			case "SO2":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  class="red-border" onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 4;
				break;
			case "NO2":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PM2.5</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >PM10</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >O<sub>3</sub></p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >SO<sub>2</sub></p><p  class="red-border" onclick="hjzlMenuFn('+index+','+mode[5]+')" >NO<sub>2</sub></p></div>'
				checktype = 5;
				break;
		}
			windowSizeFn();
			var contentH=$(window).height()-78.8+"px";
			//地图
			if(hjzlMap){
				hjzlStr += "<div  style='height:"+contentH+";' class='content-hj'>";
				if(jsonurl[checktype][0] != undefined){
					var con = new Array();
					var maptown = new Array();
					var i = checktype;
					var json=jsonurl[i][0];
					var timer = "<div class='hj-head'  style='position: absolute;bottom: 0;width: 100%;'><span>空气质量实况</span><span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新：" + json.checkTime + "</span></div></div>"
					hjzlStr += "<div style='width: 100%;height: 100%;overflow: hidden;margin:0;position: relative;'>"+
							      "<div id='allmap' style='width: 100%;height: 100%;overflow: hidden;margin:0;'></div>" +
							       "<div style='position: absolute;font-size: 15px;padding: 5px;z-index: 1000;right: 5px;top: 5px;background: rgba(255,255,255,.8);-webkit-box-shadow: 1px 1px 2px rgba(0,0,0,.4);border-radius: 3px;' onclick='hjzlListFn(" + index + ",\"" + model + "\")'>列表</div>" + timer + "</div>"
					for(var x = 0; x < json.weaterDate.length-1;x++){
						//console.log(json.weaterDate[x].indexNum)
							maptown.push(json.weaterDate[x].siteName);
							var style = '';
							if(json.weaterDate[x].indexNum<=50)
								con.push("<b style='background: #B8FF76;"+style+"'>"+json.weaterDate[x].siteName+":"+json.weaterDate[x].indexNum+"</b>");
							if(json.weaterDate[x].indexNum>=51 && json.weaterDate[x].indexNum<=100)
								con.push("<b style='background: #87F522;"+style+"'>"+json.weaterDate[x].siteName+":"+json.weaterDate[x].indexNum+"</b>");
							if(json.weaterDate[x].indexNum>=101 && json.weaterDate[x].indexNum<=150)
								con.push("<b style='background: #67CE08;"+style+"'>"+json.weaterDate[x].siteName+":"+json.weaterDate[x].indexNum+"</b>");
							if(json.weaterDate[x].indexNum == "---"){
								con.push("<b style='background: #B8FF76;"+style+"'>"+json.weaterDate[x].siteName+":--" + "</b>");
							}
					}				    
				}
			//列表
			}else{
				hjzlStr+="<div  style='height:"+contentH+";' class='content-hj'>" +
						"<div style='position: fixed;height: 20px;width: 20px;padding: 5px;z-index: 1000;right: 5px;top: 90px;background: rgba(255,255,255,.8);-webkit-box-shadow: 1px 1px 2px rgba(0,0,0,.4);border-radius: 3px;' onclick='hjzlMapFn(" + index + ",\"" + model + "\")'><img src='img/map/weizhi.png' width='100%' height='100%'/></div>" +
						"<div class='hj-title'><span>站点</span><span>指数</span><span>级别</span></div>";
				if(jsonurl[checktype][0] != undefined){
					var i = checktype;
					var json=jsonurl[i][0];
					for(var x in json.weaterDate){
						hjzlStr+="<div class='hj-neirong'><span>"+json.weaterDate[x].siteName+"</span><span>";
						if(json.weaterDate[x].indexNum<=50)
							hjzlStr+="<b style='background: #B8FF76;' class='zhishu'>"+json.weaterDate[x].indexNum+"</b>";
						if(json.weaterDate[x].indexNum>=51 && json.weaterDate[x].indexNum<=100)
							hjzlStr+="<b style='background: #87F522;' class='zhishu'>"+json.weaterDate[x].indexNum+"</b>";
						if(json.weaterDate[x].indexNum>=101 && json.weaterDate[x].indexNum<=150)
							hjzlStr+="<b style='background: #67CE08;' class='zhishu'>"+json.weaterDate[x].indexNum+"</b>";
						hjzlStr+="</span><span>"+json.weaterDate[x].level+"</span></div>";
					}
					hjzlStr+="<div class='hj-head'><span>空气质量实况</span><span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新：" + json.checkTime + "</span></div></div>";
				}else{
					for(var x = 0; x < nullval.siteName.length;x++){
						hjzlStr+="<div class='hj-neirong'><span>"+nullval.siteName[x]+"</span><span>";
						hjzlStr+="<b style='background: #B8FF76;' class='zhishu'>--</b>";
						hjzlStr+="</span><span>--</span></div> ";
					}
					hjzlStr+="<div class='hj-head'><span>空气质量实况</span><span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新：" + nullval.data + "</span></div></div>";
				}
			}
	}
	
	if(index==1){
		var jsonurl=hjzlArray[index];
		var mode=new Array("'综合'","'PH值'","'溶解氧'","'高猛酸盐'","'氨氮'");
		var checktype = 0;
		var nullval={
				siteName : ["顺德水道","顺德水道","容桂水道","东海水道","潭洲水道","东平水道 （吉利涌）"],
				checkPlace : ["羊额水厂","龙江水厂","容桂水厂","均安水厂","陈村水厂","乐从水厂"]
		}
		switch(model){
			case "综合":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')" class="red-border">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PH值</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >溶解氧</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >高猛酸盐</p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >氨氮</p></div>'
				checktype = 0;
				break;
			case "PH值":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" class="red-border">PH值</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >溶解氧</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >高猛酸盐</p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >氨氮</p></div>'
				checktype = 1;
				break;
			case "溶解氧":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PH值</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" class="red-border">溶解氧</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >高猛酸盐</p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >氨氮</p></div>'
				checktype = 2;
				break;
			case "高猛酸盐":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PH值</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >溶解氧</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" class="red-border">高猛酸盐</p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" >氨氮</p></div>'
				checktype = 3;
				break;
			case "氨氮":
				hjzlStr+='<div id="hjzl"><div class="xz-hj"><p onclick="hjzlMenuFn('+index+','+mode[0]+')">综合</p><p  onclick="hjzlMenuFn('+index+','+mode[1]+')" >PH值</p><p  onclick="hjzlMenuFn('+index+','+mode[2]+')" >溶解氧</p><p  onclick="hjzlMenuFn('+index+','+mode[3]+')" >高猛酸盐</p><p  onclick="hjzlMenuFn('+index+','+mode[4]+')" class="red-border">氨氮</p></div>'
				checktype = 4;
				break;
		}
		windowSizeFn();
		var contentH=$(window).height()-78.8+"px";
		if(hjzlMap){
			hjzlStr += "<div  style='height:"+contentH+";' class='content-hj'>";
			var con = new Array();
			var maptown = new Array();
			var i = checktype;
			var json=jsonurl[i][0];
			var timer = "<div class='hj-head' style='position: absolute;bottom: 0;'><span>水质质量实况</span><span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新："+json.checkTime+"</span></div></div>"
			hjzlStr += "<div style='width: 100%;height: 100%;overflow: hidden;margin:0;position: relative;'>"+
					      "<div id='allmap' style='width: 100%;height: 100%;overflow: hidden;margin:0;'></div>" +
					       "<div style='position: absolute;font-size: 15px;padding: 5px;z-index: 1000;right: 5px;top: 5px;background: rgba(255,255,255,.8);-webkit-box-shadow: 1px 1px 2px rgba(0,0,0,.4);border-radius: 3px;' onclick='hjzlListFn(" + index + ",\"" + model + "\")'>列表</div>" + timer + "</div>"
			if(jsonurl[checktype][0] != undefined){	       
				for(var x in json.weaterDate){
						maptown.push(json.weaterDate[x].checkPlace);
						if(json.weaterDate[x].level==null)
							con.push("<b style='background: #5BD9F6;'>"+json.weaterDate[x].checkPlace+":"+"无</b>");
						if(json.weaterDate[x].level==1)
							con.push("<b style='background: #22CEF5;'>"+json.weaterDate[x].checkPlace+":"+"Ⅰ</b>");
						if(json.weaterDate[x].level==2)
							con.push("<b style='background: #0BBFE7;'>"+json.weaterDate[x].checkPlace+":"+"Ⅱ</b>");
						if(json.weaterDate[x].level==3)
							con.push("<b style='background: #13AED1;'>"+json.weaterDate[x].checkPlace+":"+"Ⅲ</b>");
						if(json.weaterDate[x].level==4)
							con.push("<b style='background: #4196FF;'>"+json.weaterDate[x].checkPlace+":"+"Ⅳ</b>");
						if(json.weaterDate[x].level==5)
							con.push("<b style='background: #0072FE;'>"+json.weaterDate[x].checkPlace+":"+"Ⅴ</b>");
				}				    
			}else{
				for(var x = 0; x < nullval.siteName.length;x++){
					maptown.push(nullval.checkPlace[x]);
					con.push("<b style='background: #5BD9F6;'>"+nullval.checkPlace[x]+":"+"无</b>");
				}		
			}
			
		}else{
			hjzlStr+="<div  style='height:"+contentH+";' class='content-hj'>" +
					"<div style='position: fixed;height: 20px;width: 20px;padding: 5px;z-index: 1000;right: 5px;top: 90px;background: rgba(255,255,255,.8);-webkit-box-shadow: 1px 1px 2px rgba(0,0,0,.4);border-radius: 3px;' onclick='hjzlMapFn(" + index + ",\"" + model + "\")'><img src='img/map/weizhi.png' width='100%' height='100%'/></div>" +
					"<div class='hj-title'><span>河段</span><span>检测位置</span><span>水质类别</span><span>超标项目</span></div>";
			if(jsonurl[checktype][0] != undefined){
						var i = checktype;
						var json=jsonurl[i][0];
						for(var x in json.weaterDate){
							hjzlStr+="<div class='hj-neirong'><span>"+json.weaterDate[x].siteName+"</span><span>"+json.weaterDate[x].checkPlace+"</span><span>";
							if(json.weaterDate[x].level==null)
								hjzlStr+="<b style='background: #5BD9F6;' class='zhishu'>无</b>";
							if(json.weaterDate[x].level==1)
								hjzlStr+="<b style='background: #22CEF5;' class='zhishu'>Ⅰ</b>";
							if(json.weaterDate[x].level==2)
								hjzlStr+="<b style='background: #0BBFE7;' class='zhishu'>Ⅱ</b>";
							if(json.weaterDate[x].level==3)
								hjzlStr+="<b style='background: #13AED1;' class='zhishu'>Ⅲ</b>";
							if(json.weaterDate[x].level==4)
								hjzlStr+="<b style='background: #4196FF;' class='zhishu'>Ⅳ</b>";
							if(json.weaterDate[x].level==5)
								hjzlStr+="<b style='background: #0072FE;' class='zhishu'>Ⅴ</b>";
							hjzlStr+="</span><span>"+json.weaterDate[x].remarks+"</span></div> ";
						}
						hjzlStr+="<div class='hj-head'><span>水质质量实况</span><span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新："+json.checkTime+"</span></div></div>";
			}else{
						for(var x = 0; x < nullval.siteName.length;x++){
							hjzlStr+="<div class='hj-neirong'><span>"+nullval.siteName[x]+"</span><span>"+nullval.checkPlace[x]+"</span><span>";
							hjzlStr+="<b style='background: #5BD9F6;' class='zhishu'>--</b>";
							hjzlStr+="</span><span>--</span></div> ";
						}
						hjzlStr+="<div class='hj-head'><span>水质质量实况</span><span<span style='display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;flex:1;'>更新：--</span></div></div>";
			}
			
		}
	}
	if(index==2){
		hjzlStr+="<div id='hjzl'> <div style='padding: 5px;text-align: center;background: #fff;border-bottom: solid 1px #B4B4B4'>空气质量指标说明</div> <div class='content-hj'> <div style='background:#B8FF76;' class='hj-title'><span>空气质量指数</span><span>空气质量级别</span><span>空气质量状况</span></div> <div class='hj-neirong'><span>0-50</span><span>一级</span><span>优</span></div> <div class='hj-neirong'><span>51-100</span><span>二级</span><span>良</span></div> <div class='hj-neirong'><span>101-150</span><span>三级</span><span>轻度污染</span></div> <div class='hj-neirong'><span>151-200</span><span>四级</span><span>中度污染</span></div> <div class='hj-neirong'><span>201-300</span><span>五级</span><span>重度污染</span></div> </div>";
		hjzlStr+="<div style='margin-top:10px;padding: 5px;text-align: center;background: #fff;border-bottom: solid 1px #B4B4B4'>水质质量指标说明</div> <div class='content-hj'> <div style='background:#5BD9F6;' class='hj-title'><span>水质级别</span><span>水质状况</span></div> <div class='hj-neirong'><span>Ⅰ</span><span>良好</span></div> <div class='hj-neirong'><span>Ⅱ</span><span>较清洁，过滤后可成为饮用水</span></div> <div class='hj-neirong'><span>Ⅲ</span><span>过滤清洁后可用作普通工业用水</span></div> <div class='hj-neirong'><span>Ⅳ</span><span>普通农业用水，灌溉用</span></div> <div class='hj-neirong'><span>Ⅴ</span><span>普通景观用水</span></div> </div>"
	} 
	$("#contant_page").append(hjzlStr);
	if(hjzlMap){
		baiduMap(maptown,con);
	}
}

function hjzlMapFn(index,model){
	hjzlMap = true;
	hjzlMenuFn(index,model)
}
function hjzlListFn(index,model){
	hjzlMap = false;
	hjzlMenuFn(index,model)
}
function baiduMap(tn,content){
	//创建和初始化地图函数：
	 function initMap(){
		createMap();//创建地图
		setMapEvent();//设置地图事件
		addMapControl();//向地图添加控件
	}
	//创建地图函数：
	function createMap(){
		var city = "顺德区";
		var town = tn;
		var con = content;
		var myGeo = new BMap.Geocoder();
		var map = new BMap.Map("allmap");//在百度地图容器中创建一个地图
		map.centerAndZoom("顺德区新松",11);//设定地图的中心点和坐标并将地图显示在地图容器中
		window.map = map;//将map变量存储在全局
		for(var t=0; t<town.length;t++){
			var setcity = city + town[t];
			createPoint(setcity,con[t]);
		}
	}
	function createPoint(city,con){
		var myGeo = new BMap.Geocoder();
		myGeo.getPoint(city, function(point){
			if (point) {
				var address = new BMap.Point(point.lng, point.lat);
				var marker = new BMap.Marker(address);  // 创建标注
				var label = new BMap.Label(con,{position:address,offset:new BMap.Size(-22,-43)});
				map.addOverlay(marker);               // 将标注添加到地图中
				map.addOverlay(label);
				label.setStyle({borderColor:"#999"});
			}
		});
	}
	//地图事件设置函数：
	function setMapEvent(){
		map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
		map.disableScrollWheelZoom();//禁用地图滚轮放大缩小，默认禁用(可不写)
		map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
		map.disableKeyboard();//禁用键盘上下左右键移动地图，默认禁用(可不写)
	}
	//地图控件添加函数：
	function addMapControl(){
		//向地图中添加缩放控件
		var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		map.addControl(ctrl_nav);
	}
	initMap();//创建和初始化地图
}

//-------------------------服务电话
var contactStr;
function contactMenuFn(index){
	contactStr="";
	//var url=ct+"/api/getjsonlist?function=contact/";
	var url=ct+"/data/sjDepAddressbook/getDepAddList";
	$.ajaxSettings.async = false;
  $.getJSON(url,function(result){
		var data=result.obj;
		for(i in data){
			var j=-1;
			if(index==0 && data[i].depType=="党政机关"){
				j=i;
			}else if(index==1 && data[i].depType=="区属事业单位"){
				j=i;
			}else if(index==2 && data[i].depType=="省或佛山市直管单位"){
				j=i;
			}else if(index==3 && data[i].depType=="各镇街道"){
				j=i;
			}
			if(j!=-1){
				contactStr+="<p style='padding:7px;border-bottom:2px #f4f6f8 solid;'>"+data[j].depName+"<br/><span style='color:#888888;'>电话: "+data[j].contact+"</span><br/><span style='color:#888888;'>邮箱: "+data[j].email+"</span></p>";
			}
		}
		
  });	
  $.ajaxSettings.async = true;
}

//-------------------------四级滑块跳转外部链接
var allHrefStr;
function allHrefFn(url){
	var parentH=$("#change").height();
	allHrefStr="<div class='scroll-wrapper'><iframe   frameborder='0' allowtransparency='true' src="+url+"></iframe></div>";
}
//内部页面
function inPageFn(url){
	allHrefStr="";
	var url=ct+url;
	allHrefFn(url);
}
//外部页面
function outPageFn(url){
	allHrefStr="";
	var url="http://"+url;
	allHrefFn(url);
}





