/*升序*/
function sort(arr){
	var temp;
	for(var i=0; i<arr.length-1; i++){
		for(var j=i+1; j<arr.length; j++){
			if (arr[i].sort > arr[j].sort ){
				temp=arr[i];
				arr[i]=arr[j];
				arr[j]=temp;
			}
		}
	}
}


/*固定头部*/
function windowSizeFn(){
	var h=$(window).height();
	var mH=$("#menu").outerHeight(true);
	$("#content").height(h-mH);
}


/*菜单滑动功能*/
function sliderFn(){
	/*固定头部*/
	windowSizeFn();
	
	//菜单对象，点击的位置
	var menuObj=new windowFn("#menu");
	menuObj.init();
	
	function windowFn(tabname) {
		var name =tabname;//获取菜单对象
		var namewidth=parseInt($(name).css("width"));
		var move="#menu_move";//获取移动对象
		var movewidth=parseInt($(move).css("width"));
		var tab=move+" li";
		var distance=160;//左右切换移动距离
		var movedis;//点击时屏幕到点击距离
		var savemovedis=0;//保持距离
		this.init=function(){
			
			//轮播标签绑定事件
			for(i=0;i<$(tab).length;i++){
				$(tab+":eq("+i+")").bind("click",function(){
					allTab(tab,$(this));
					tabindex=$(tab).city_data($(this));//获取当前标签
				});
			}
			return;///删除return和base.css(60和61行 flex:1)就可以滑轮
			//触屏轮播标签绑定事件
			$(name).bind('touchstart',function(e){ 
				var touch = e.originalEvent.targetTouches[0];
				movedis= touch.pageX;
				
				$(move).attr("class","");
			});
			$(name).bind('touchmove',function(e){
				var touch = e.originalEvent.targetTouches[0];
				var x= parseInt(movedis-touch.pageX);
				
				menuObj.touchmove(touchLR(x));
			});
			$(name).bind('touchend',function(e){ 
				var touch = e.originalEvent.changedTouches[0];
				var x= parseInt(movedis-touch.pageX);
				movewidth=$(move).width();
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
		}
		this.touchmove=function(x,savedis){//负：左
			var movenum=savemovedis+x;
			$(move).css("margin-left",movenum);
		}
	};
	
	//标签对象初始化
	function allTab(tab,my){
		//所有对象初始化
		for(i=0;i<$(tab).length;i++){
			$(tab+":eq("+i+")").attr("class","");
		}
		my.attr("class","red-color");
	}
	
	//判断左右滑动结果：正（左滑动）
	var touchLR=function(x){
		if(x<0)
			return Math.abs(x);
		else
			return (-x);	
	}	
	
	/*变换下划线*/
	$("#menu #menu_move li").click(function(){
		$("#menu #menu_move li").removeClass("li-red");
		$(this).addClass("li-red");
	});

}


/*16宫格*/
function five16Fn(sort){
	var str="";
    var index=0;
    var row;	//行
    var col=4;	//列
	if(sort!=null && sort.length>0){
		row=Math.ceil(sort.length/col);
	}else{
		return str;
	}
    str=str+'<div class="five16">';
    for(i=0;i<row;i++){
        str=str+'<div class="ps">';
        for(j=0;j<col;j++){
            if(i==row-1 && (col-j)<=(row*col-sort.length)){
                str=str+'<div class="ps-format"></div>';
            }else{
            	//对name中的特殊字符&和"进行转义
        		var name=sort[index].name;
        		name=name.replace(/\&/g,'%26');
        		name=name.replace(/\"/g,'%5C"');
            	
            	var url="";
            	if( typeof(sort[index].href) == "string" && sort[index].href.indexOf("http")==0 ){
            		//外链
            		url=sort[index].href;
            	}else if( typeof(sort[index].href) == "string" && sort[index].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(sort[index].disclaimer) == 'string' && sort[index].disclaimer != '' && sort[index].disclaimer != '0' ){
            		//免责声明
            		url=ct+sort[index].href+"&noHead="+noHead+"&disclaimer="+sort[index].disclaimer;
            	}else{
            		url=ct+sort[index].href+"&noHead="+noHead;
            		
            	}
            	
            	str=str+'<a  onclick="countFn(\''+sort[index].modelid+'\',\''+sort[index].id+'\',\''+sort[index].parentIds+'\',\''+sort[index].href+'\',\''+sort[index].name+'\',\''+sort[index].disclaimer+'\',\''+url+'\')"  class="ps-format"><div class="ps-fi"><img src="'+ct+'/img?url='+sort[index].mobileIcon+'"/><p>'+sort[index].name+'</p></div></a>';
                index++; 
            }
        }
        str=str+"</div>";
    }
    str=str+'</div>';
    return str;
}


/*四级列表*/
function fourListFn(sort){
	var str='';
	if(sort==null || sort.length==0){
		return str;
	}
	for(var i=0; i<sort.length; i++){
		//对name中的特殊字符&和"进行转义
		var name=sort[i].name;
		name=name.replace(/\&/g,'%26');
		name=name.replace(/\"/g,'%5C"');
		
		//链接地址
		var url="";
		if( typeof(sort[i].href) == "string" && sort[i].href.indexOf("http")==0 ){
			//外链
			url=sort[i].href;
		}else if( typeof(sort[i].href) == "string" && sort[i].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(sort[i].disclaimer) == 'string' && sort[i].disclaimer != '' && sort[i].disclaimer != '0' ){
			//免责声明
			url=ct+sort[i].href+"&noHead="+noHead+"&disclaimer="+sort[i].disclaimer;
		}else{
			url=ct+sort[i].href+"&noHead="+noHead;
			
		}
		str=str+'<dl class="fourList"><dt><a  onclick="countFn(\''+sort[i].modelid+'\',\''+sort[i].id+'\',\''+sort[i].parentIds+'\',\''+sort[i].href+'\',\''+sort[i].name+'\',\''+sort[i].disclaimer+'\',\''+url+'\')"  class="dt-a"><div class="left"><img src="'+ct+'/static/modules/app/img/menu/note.png" height="30"/></div><div class="center">'+ sort[i].name +'</div><div class="right"><img class="xiala right" src="'+ct+'/static/modules/app/img/menu/xiala.png"  height="10"/></div></a></dt></dl>';
	} 
	return str;
}


/*五级树*/
function fiveTreeFn(data, five){
	var str='';
	for(var i=0; i<five.length; i++){
		str=str+'<dl class="fiveTree">';
		str=str+'<dt onclick="ddHideFn(\''+i+'\')" index="'+i+'" status="hide"><div class="left"><img src="'+ct+'/static/modules/app/img/menu/note.png" height="30"/></div><div class="center">'+ five[i].name +'</div><div class="right"><img class="xiala down" src="'+ct+'/static/modules/app/img/menu/xiala.png" height="10"/></div></dt>';
		
		//获取六级菜单数据
		var six=new Array();
		index=0;
		for(var j=0; j<data.length; j++){
			if(data[j].parentId == five[i].id){
				six[index]=data[j];
				index++;
			}
		}
		//对六级菜单数据排序
		sort(six);
		for(var j=0; j<six.length; j++){
			//链接地址
			var url="";
			//对name中的特殊字符&和"进行转义
			var name=six[j].name;
			name=name.replace(/\&/g,'%26');
			name=name.replace(/\"/g,'%5C"');
			
			if( typeof(six[j].href) == "string" && six[j].href.indexOf("http")==0 ){
				//外链
				url=six[j].href;
			}else if( typeof(six[j].href) == "string" && six[j].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(six[j].disclaimer) == 'string' && six[j].disclaimer != '' && six[j].disclaimer != '0' ){
				//免责声明
				url=ct+six[j].href+"&noHead="+noHead+"&disclaimer="+six[j].disclaimer;
			}else{
				url=ct+six[j].href+"&noHead="+noHead;
				
			}
				
			str=str+'<dd><a onclick="countFn(\''+six[j].modelid+'\',\''+six[j].id+'\',\''+six[j].parentIds+'\',\''+six[j].href+'\',\''+six[j].name+'\',\''+six[j].disclaimer+'\',\''+url+'\')" ><div class="left"><div></div></div><div class="right">'+ six[j].name +'</div></a></dd>'; 
		}
		str=str+'</dl>';
	} 
	return str;
}


/*五级树显示或隐藏子菜单*/
function ddHideFn(i){
	var that=$("[index="+i+"]");
	if(that.attr("status") == "hide"){
		that.find(".xiala").attr("class","xiala");
		that.attr("status","show");
		that.nextAll("dd").show(200);
	}else{
		that.find(".xiala").attr("class","xiala"+" down");
		that.attr("status","hide");
		that.nextAll("dd").hide(200);
	}
	
	//缓存处于显示的菜单
	var sixTree='';
	var shows=$("[status='show']");
	if(shows != null){
		$(shows).each(function(){
			sixTree+=$(this).attr("static.framework7.js.city_data.js")+',';
		})
	}
	sessionStorage.setItem('sixTree', sixTree);
	
}


