// Jquery Document
/* $(document).ready(function(){
	 indexJsonStopFn()
    });*/
var ct,ctxStatic;
function initLujingFn(jct,jctx){
	ct=jct;
	ctxStatic=jctx;
};


//轮播对象
var Rotamove;//点击的位置
var Rotation;
function rotaTion(rota) {
    var name =rota;//获取轮播对象
	var pic=name+" .rotation-picture a";//获取轮播图片
	var picwidth=$(name+" .rotation-picture").width();//获取轮播图片宽度
	var picmin=-picwidth;
	var picmax=-picwidth*$(tab).length+picmin;
	
	var format=name+" .rotation-format";//过渡格式
	var tab=name+" .rotation-tab li";//获取轮播标签
	var tabindex=0;//获取轮播标签当前位置
	var distance=160;//左右切换移动距离
	var roationtime=10000;//轮播时间
	var moveLRdis=30;//左右移动时达到规定距离可以左右移动
	var moveupdown;//判断是否上下移动
	
	this.init=function(){
		//轮播图片
		var first=$(format).find("a:first").clone();
		var last=$(format).find("a:last").clone();
		$(format).prepend(last);
		$(format).append(first);
		$(name+" .rotation-picture .rotation-format").css("margin-left",-picwidth);
		
		
		//轮播标签绑定事件
		for(i=0;i<$(tab).length;i++){
			$(tab+":eq("+i+")").bind("click",function(){
				allTab(tab,$(this),"red-background");
				tabindex=$(tab).city_data($(this));//获取当前标签
				//图片轮播动画
				Rotation.playanimation();
			});
		}
		//触屏轮播标签绑定事件
		$("#touch_rotation").bind('touchstart',function(e){ 
			var touch = e.originalEvent.targetTouches[0];
			var length=$(tab).length;
			Rotamove= touch.pageX;
			moveupdown=$("#main_container").scrollTop();
			$(format).attr("class","rotation-format");
		});
		$("#touch_rotation").bind('touchmove',function(e){
			var picsize=$(name+" .rotation-picture").width();
			if(moveupdown-$("#main_container").scrollTop()==0){
				var touch = e.originalEvent.targetTouches[0];
				var x= parseInt(Rotamove-touch.pageX);
				if(touchLR(x)<-moveLRdis){//右
//					if(tabindex==($(tab).length-1))
//						return false;
					Rotation.touchmove(touchLR(x)+moveLRdis,e);
				}
				if(touchLR(x)>moveLRdis){//左
//					if(tabindex==0)
//						return false;
					Rotation.touchmove(touchLR(x)-moveLRdis,e);
				}
			}
		});
		$("#touch_rotation").bind('touchend',function(e){
			if(moveupdown-$("#main_container").scrollTop()==0){
				var touch = e.originalEvent.changedTouches[0];
				var x= parseInt(Rotamove-touch.pageX);
				if(x>0)
					Rotation.touchemd("left",x);
				else
					Rotation.touchemd("right",x);		
				$(format).attr("class","rotation-format rotation-move");
				$('#main_container').css('overflow-y','scroll');
			}
		});
		//设置时间播放
		setInterval("Rotation.timeplay()",roationtime);
	}
	this.playanimation=function(){
		var picsize=$(name+" .rotation-picture").width();
		$(name+" .rotation-picture .rotation-format").css("margin-left",(-picsize*tabindex+picmin));
	}
	this.touchanimation=function(num){
		var picsize=$(name+" .rotation-picture").width();
		$(name+" .rotation-picture .rotation-format").css("margin-left",(-picsize*num+picmin));
	}
	this.touchmove=function(x,e){//负：左
		var picsize=$(name+" .rotation-picture").width()*tabindex;
		$(name+" .rotation-picture .rotation-format").css("margin-left",-picsize+x+picmin);
		$('#main_container').css('overflow-y','hidden');
		e.preventDefault();
		e.stopPropagation();
	}
	this.touchemd=function(pos,dis){//正：左
		//alert(pos+" "+dis)
		var num=0;
		var length=$(tab).length;
		if(pos=="left"){
			if(dis>distance){
				//所有对象初始化
				for(i=0;i<$(tab).length;i++)
					$(tab+":eq("+i+")").attr("class","");
				num=tabindex+1;
				if((tabindex+1)<length)
					tabindex=tabindex+1
				else
					tabindex=0;
			}else{
				num=tabindex;
			}
		}
		if(pos=="right"){
			if(dis<(-distance)){
				//所有对象初始化
				for(i=0;i<$(tab).length;i++)
					$(tab+":eq("+i+")").attr("class","");
				num=tabindex-1;
				tabindex=((tabindex-1)<0)?length-1:tabindex-1;//获取当前标签
			}else{
				num=tabindex;
			}
		}
		this.touchanimation(num);
		$(tab+":eq("+tabindex+")").attr("class","red-background");
	}
	this.timeplay=function(){
		//所有对象初始化
		for(i=0;i<$(tab).length;i++)
			$(tab+":eq("+i+")").attr("class","");
		var length=$(tab).length;
		if((tabindex+1)<length)
			tabindex=tabindex+1
		else
			tabindex=0;	
		this.playanimation();
		$(tab+":eq("+tabindex+")").attr("class","red-background");
	}
}



//快捷对象
var Fastmove;//点击的位置
var Fast;
function fastFn(fast){
	var name =fast;//获取快捷对象
	var move=fast+" .fast-format";//获取移动对象
	var tab=fast+" .fast-tab li";//获取标签对象
	var tabindex=0;//获取轮播标签当前位置
	var distance=100;//左右切换移动距离
	var moveLRdis=30;//左右移动时达到规定距离可以左右移动
	var moveupdown;//判断是否上下移动
	this.init=function(){
		//快捷标签绑定事件
		for(i=0;i<$(tab).length;i++){
			$(tab+":eq("+i+")").bind("click",function(){
				allTab(tab,$(this),"red-background");
				tabindex=$(tab).city_data($(this));//获取当前标签
				//快捷动画
				Fast.playanimation();				
			});
		}
		//触屏标签绑定事件
		$("#touch_fast").bind('touchstart',function(e){ 
			var touch = e.originalEvent.targetTouches[0];
			Fastmove= touch.pageX;
			moveupdown=$("#main_container").scrollTop();
			$(move).attr("class","fast-format");
		});
		$("#touch_fast").bind('touchmove',function(e){ 
			
			if(moveupdown-$("#main_container").scrollTop()==0){
				var touch = e.originalEvent.targetTouches[0];
				var x= parseInt(Fastmove-touch.pageX);
				
				if(touchLR(x)<-moveLRdis){
					Fast.touchmove(touchLR(x)+moveLRdis,e);
				}
				if(touchLR(x)>moveLRdis){
					Fast.touchmove(touchLR(x)-moveLRdis,e);
				}
			}	
		});
		$("#touch_fast").bind('touchend',function(e){
			if(moveupdown-$("#main_container").scrollTop()==0){
				var touch = e.originalEvent.changedTouches[0];
				var x= parseInt(Fastmove-touch.pageX);
				if(x>0)
					Fast.touchemd("left",x);
				else
					Fast.touchemd("right",x);
				$(move).attr("class","fast-format fast-move");
				$('#main_container').css('overflow-y','scroll');
			}
		});
	}
	this.playanimation=function(){
		var width=$(name).width();
		$(move).css("left",(-width*tabindex));
	}
	this.touchmove=function(x,e){//负：左
		var picsize=$(name).width()*tabindex;
		$(name+" .fast-format").css("left",-picsize+x);
		$('#main_container').css('overflow-y','hidden');
		e.preventDefault();
		e.stopPropagation();
		//console.log(x)
	}
	this.touchemd=function(pos,dis){//正：左
		//console.log(pos+" "+dis)
		if(pos=="left"){
			if(dis>distance){
				//所有对象初始化
				for(i=0;i<$(tab).length;i++)
					$(tab+":eq("+i+")").attr("class","");
				var length=$(tab).length;
				if((tabindex+1)<length)
					tabindex=tabindex+1
				else
					tabindex=length-1;
				
			}
			var picsize=$(name).width();
			$(move).css("left",(-picsize*tabindex));
			$(tab+":eq("+tabindex+")").attr("class","red-background");
		}
		if(pos=="right"){
			if(dis<(-distance)){
				//所有对象初始化
				for(i=0;i<$(tab).length;i++)
					$(tab+":eq("+i+")").attr("class","");
				tabindex=((tabindex-1)<0)?0:tabindex-1;//获取当前标签
			}
			var picsize=$(name).width();
			$(move).css("left",(-picsize*tabindex));
			$(tab+":eq("+tabindex+")").attr("class","red-background");
		}
	}
}
//刷新对象
var Refresh;
function refreshFn(ref){
	var name =ref;//获取刷新对象
	var bodytab="#main_container";//触屏对象
	var bodyheight="#main_height";
	var format=name+" .refresh-format";
	var img=format+" img";
	var movedisY=0;//拉动距离
	var time1,time2;
	var shunxi=0;
	var aniend=true;//加载动画结束
	var aniendcome=true;//加载动画结束重新按过
	this.allheight;//文档高度
	this.initdata=function(){//初始化高度
		allheight=$(bodyheight).height();
	}
	this.init=function(){
		//触屏轮播标签绑定事件
		$(bodytab).bind('touchstart',function(e){
			if(aniend){
				var touch = e.originalEvent.targetTouches[0];
				allheight=$(bodyheight).height();
				movedisY=touch.clientY;
				//console.log(movedisY);
				if(time1!=undefined){
					clearTimeout(time1);
					clearTimeout(time2); 
				}
				aniendcome=true;
			}
				
			//e.preventDefault();
			//e.stopPropagation();
			//console.log(movedisY);
		});
		$(bodytab).bind('touchmove',function(e){
				var documentpos=$(bodytab).height()+$(bodytab).scrollTop();//文档位置从顶到可见区域	
				//console.log(documentpos)
				if(aniend && aniendcome){
					if(allheight<=documentpos){
						var touch = e.originalEvent.targetTouches[0];
						var y= parseInt((movedisY-touch.clientY)*0.58);
						if(y<=0){
							y=0;
						}
						$(name).css("height",y);
						$(bodytab).scrollTop($(bodyheight).height());
						$(img).css("transform","rotate("+y+"deg)");
					}
					if(allheight>documentpos){//动画小于100时，上下移动不能刷新
						allheight=$(bodyheight).height();
					}
					if(parseInt($(name).css("height"))>0){
						e.preventDefault();
						e.stopPropagation();
					}
				}	
		});
		$(bodytab).bind('touchend',function(e){
			if(aniend){
				if(parseInt($(name).css("height"))>=100){
					$(name).attr("class","refresh transtion start");
					$(img).attr("class","ani");
					$("#new-hidden").css("display","block");
					time1=setTimeout('Refresh.timeout(1);',1500);
					time2=setTimeout('Refresh.timeout(2);',2000);
					aniend=false;
					aniendcome=false;
				}
				
				if(parseInt($(name).css("height"))>0 && parseInt($(name).css("height"))<100){
					$(name).css("height",0);
					$(name).attr("class","refresh transtion");
					setTimeout('Refresh.timeout(2);',500);
				}
			}
			//$('#main_container').css('overflow-y','scroll');
			
		});
		this.timeout=function(num){
			switch(num){
			case 1:
				$(name).attr("class","refresh transtion");
				$(name).css("height",0);
				setTimeout('loadIndexJson()',200);
				aniend=true;
				
			  break;
			case 2://初始化
				$("#new-hidden").css("display","none");
				$(name).attr("class","refresh");
				$(img).attr("class","");
			  break;
			}
			
		}
	}
}