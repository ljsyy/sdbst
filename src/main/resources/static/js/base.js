//获取项目的绝对路径
function basePath() {
    var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
    return result;
    // return "";
}

//json基本路径
var jsonurl="/app/menu/findChildsMenu?id=";
//模板格式化（正则替换）
var FormatModel = function (str, model) {
    for (var k in model) {
        var re = new RegExp("{" + k + "}", "g");
        str = str.replace(re, model[k]);
    }
    return str;
}
//删除重复的数组
function removeDuplicates(arr) {
    var temp = {}, r = [];
    for (var i in arr)
        temp[arr[i]] = true;
    for (var k in temp)
        r.push(k);
    return r;
}
function setCookie(cname,cvalue,exdays){
	var d = new Date();
	d.setTime(d.getTime()+(exdays*24*60*60*1000));
	var expires = "expires="+d.toGMTString();
	document.cookie = cname+"="+cvalue+"; "+expires;
}
function getCookie(cname){
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name)==0) return c.substring(name.length,c.length);
	}
	return "";
}
function checkCookie(){
	var user=getCookie("username");
	if (user!=""){
		alert("Welcome again " + user);
	}
	else {
		user = prompt("Please enter your name:","");
  		if (user!="" && user!=null){
    		setCookie("username",user,30);
    	}
	}
}
function delCookie(name) { 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval = getCookie(name); 
    if(cval!=null) 
        document.cookie= name + "="+cval+";expires=" + exp.toGMTString(); 
}

//判断左右滑动结果：正（左滑动）
var touchLR=function(x){
	if(x<0)
		return Math.abs(x);
	else
		return (-x);	
}
var touchTB=function(y){
	if(y<0)
		return Math.abs(y);
	else
		return (-y);	
}
//标签对象初始化
function allTab(tab,my,classname){
	//所有对象初始化
	for(i=0;i<$(tab).length;i++){
		$(tab+":eq("+i+")").attr("class","");
	}
	my.attr("class",classname);
}
//返回按钮
$(function() {
	$(".retrun-icon").click(function() {
		window.history.back();
	});
});

//标签对象tool
function jsonToolFn(){
	//json排序,   json:json对象       vis:是否获取隐藏
    this._jsonsort=function(json,vis){
        var aparentid=new Array();
         for(var i=0;i<json.length;i++){
        	 if(json[i].wxShow==1 || vis==true)
                aparentid.push(json[i]);
         }
        //排序
        for(var i=0;i<json.length;i++)
           for(var j=0;j<json.length-i-1;j++)
               if(aparentid[j].sort>aparentid[j+1].sort){
                   var swap =  aparentid[j];
                   aparentid[j]=aparentid[j+1];
                   aparentid[j+1]=swap;
               }
        //console.log(aparentid)
        return aparentid;
      }     
	
}
 //获取到JSON数据对象
function jsonObjectFn(url,createobject,createobjectFn,endobjectFn){
    var _json;
    var _jsonlength;
    //构造函数
    (function(){
        $.getJSON(url, function(json){
            eval(createobject)._json=json.data;
            eval(createobject)._jsonlength=json.data.length;  
            eval(createobjectFn);
        });
        $(document).ajaxStop(function(){
        	if(endobjectFn!=null)
        		eval(endobjectFn);
    	});
    })()
    //获取JSON数据：//0:json对象，1:json对象长度
    this.getJsonData=function(){return new Array(this._json,this._jsonlength);}
    //查找json数据id,id是唯一
    this.getjsonId=function(id){
         for(i=0;i<this._jsonlength;i++){
            if(this._json[i].id==id){
                return this._json[i];
            }
         }
    }
    //查找json数据parentId。true：排序，false：不排序
    this.getjsonparentId=function(parentid,judgment){
        var aparentid=new Array();
         for(i=0;i<this._jsonlength;i++){
            if(this._json[i].parentId==parentid){
                aparentid.push(this._json[i]);
            }
         }
        //排序
        if(aparentid!="" && judgment==true){
            for(i=0;i<aparentid.length;i++)
                for(j=0;j<aparentid.length-i-1;j++)
                    if(aparentid[j].sort>aparentid[j+1].sort){
                        var swap =  aparentid[j];
                        aparentid[j]=aparentid[j+1];
                        aparentid[j+1]=swap;
                    }
            return aparentid;
            //for(x in aparentid){console.log(aparentid[x].sort);}
        }     
    }
    //查找json数据name
    this.getjsonName=function(namedata){
        var aparentid=new Array();
         for(i=0;i<this._jsonlength;i++){
            if(this._json[i].name==name){
                 return this._json[i];
            }
         }
    }
}
/*	样式表格
 * 
 * */
//请选择：默认 
function defaultFormatFn(ct,noHead,runEnviron,sort,img){
	//console.log(sort)
	var str='';
	for(x in sort) {
		if(typeof(runEnviron) != "undefined" && runEnviron == 'wx' ){
			//微信控制
			if( sort[x].wxShow!=1 ){continue;}
		}else{
			//APP控制
			if( sort[x].mobileShow!=1 ){continue;}
		}
		
	    str=str+'<dl id="defaultformat"><dt>';
	    
	    //对name中的特殊字符&和"进行转义
		var name=sort[x].name;
		name=name.replace(/\&/g,'%26');
		name=name.replace(/\"/g,'%5C"');
	    
	    //链接地址
	    var url="";
	    if(sort[x].type=="fourList" || sort[x].type=="fourSlider" || sort[x].type=="five16" || sort[x].type=="fiveTree" || sort[x].type=="fiveSliderList" || sort[x].type=="sixTree"){
		    url=sort[x].type+"?id="+sort[x].id+"&name="+name+"&noHead="+noHead;
		    
	    }else if( typeof(sort[x].href) == "string" && sort[x].href.indexOf("http")==0 ){
		    //跳转到外部href
		    url=sort[x].href;
		    
	    }else if( typeof(sort[x].href) == "string" && sort[x].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(sort[x].disclaimer) == 'string' && sort[x].disclaimer != '' && sort[x].disclaimer != '0' ){
	    	url=ct+sort[x].href+"&noHead="+noHead+"&disclaimer="+sort[x].disclaimer;
	    	
	    }else{
		    //跳转到内部部href
		    url=ct+sort[x].href+"&noHead="+noHead;
	   
	    }
	    str=str+'<a  onclick="countFn(\''+sort[x].modelid+'\',\''+sort[x].id+'\',\''+sort[x].parentIds+'\',\''+sort[x].href+'\',\''+sort[x].name+'\',\''+sort[x].disclaimer+'\',\''+url+'\')"  class="dt-a"><div class="center">'+sort[x].name+'</div><div class="right"><img class="xiala right" src='+img+' height="10"/></div></a>';
	    str=str+'</dt></dl>';
	}
	return str;
}
//四级列表
function menuFourList(ct,noHead,runEnviron,sort,img1,img2){
	var str='';
	if(sort==undefined){
		return str;
	}
	for(var i=0; i<sort.length; i++){
		//判断菜单是否显示
		if(typeof(runEnviron) != "undefined" && runEnviron == 'wx' ){
			//微信控制
			if( sort[i].wxShow!=1 ){continue;}
		}else{
			//APP控制
			if( sort[i].mobileShow!=1 ){continue;}
		}
		
		//对name中的特殊字符&和"进行转义
		var name=sort[i].name;
		name=name.replace(/\&/g,'%26');
		name=name.replace(/\"/g,'%5C"');
		
		//链接地址
		var url="";
		if(sort[i].type=="fourList" || sort[i].type=="fourSlider" || sort[i].type=="five16" || sort[i].type=="fiveTree" || sort[i].type=="fiveSliderList" || sort[i].type=="sixTree"){
			url=sort[i].type+"?id="+sort[i].id+"&name="+name+"&noHead="+noHead;
			
		}else if( typeof(sort[i].href) == "string" && sort[i].href.indexOf("http")==0 ){
			url=sort[i].href;
			
		}else if( typeof(sort[i].href) == "string" && sort[i].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(sort[i].disclaimer) == 'string' && sort[i].disclaimer != '' && sort[i].disclaimer != '0' ){
			url=ct+sort[i].href+"&noHead="+noHead+"&disclaimer="+sort[i].disclaimer;
			
		}else{
			url=ct+sort[i].href+"&noHead="+noHead;
			
		}
		str=str+'<dl class="fourList"><dt><a  onclick="countFn(\''+sort[i].modelid+'\',\''+sort[i].id+'\',\''+sort[i].parentIds+'\',\''+sort[i].href+'\',\''+sort[i].name+'\',\''+sort[i].disclaimer+'\'\''+url+'\')"  class="dt-a"><div class="left"><img src='+img1+' height="30"/></div><div class="center">'+ sort[i].name +'</div><div class="right"><img class="xiala right" src='+img2+' height="10"/></div></a></dt></dl>';
	} 
	return str;
}
//树形结构
function menuFiveTree(ct,resuld,id,img1,img2){
    // console.log("menuFiveTree:_____"+resuld)

    var str='';
		var oneArr=JSON.parse(resuld);
		// console.log("menuFiveTree:"+oneArr)
		var twoArr=new Array();
		//获取二级菜单集合 
		var index=0;
		for(var i=0; i<oneArr.length; i++){
            console.log("33:"+oneArr.length)
			if(oneArr[i].parentId == id){
				twoArr[index]=oneArr[i];
				index++;
			}
		}
	// console.log("3:"+oneArr)
	// console.log("4:"+twoArr)
		//对二级菜单数据排序
		sortArr(twoArr);
		for(var i=0; i<twoArr.length; i++){
			//判断菜单是否显示
			// if(typeof(runEnviron) != "undefined" && runEnviron == 'wx' ){
			// 	//微信控制
			// 	if( twoArr[i].wxShow!=1 ){continue;}
			// }else{
				//APP控制
				if( twoArr[i].mobileShow!=1 ){continue;}
			// }
			
			str=str+'<dl class="fiveTree">';
			str=str+'<dt><div class="left"><img src='+img1+' height="30"/></div><div class="center">'+ twoArr[i].name +'</div><div class="right"><img class="xiala down" src='+img2+' height="10"/></div></dt>';
			
			//获取三级菜单数据
			var threeArr=new Array();
			index=0;
			for(var j=0; j<oneArr.length; j++){
				if(oneArr[j].parentId == twoArr[i].id){
					threeArr[index]=oneArr[j];
					index++;
				}
			}
			//对三级菜单数据排序
			sortArr(threeArr);
			for(var j=0; j<threeArr.length; j++){
				//判断菜单是否显示
				// if(typeof(runEnviron) != "undefined" && runEnviron == 'wx' ){
				// 	//微信控制
				// 	if( threeArr[j].wxShow!=1 ){continue;}
				// }else{
					//APP控制
					if( threeArr[j].mobileShow!=1 ){continue;}
				// }
				//链接地址
				var url="";
				
				//对name中的特殊字符&和"进行转义
				var name=threeArr[j].name;
				name=name.replace(/\&/g,'%26');
				name=name.replace(/\"/g,'%5C"');
				
				// if( threeArr[j].type=="fourList" || threeArr[j].type=="fourSlider" || threeArr[j].type=="five16" || threeArr[j].type=="fiveTree" || threeArr[j].type=="fiveSliderList" || threeArr[j].type=="sixTree"){
				// 	url=threeArr[j].type+"?id="+threeArr[j].id+"&name="+name;
				//
				// }else{
					if( typeof(threeArr[j].href) == "string" && threeArr[j].href.indexOf("http")==0 ){
						url=threeArr[j].href;
					
					}else if( typeof(threeArr[j].href) == "string" && threeArr[j].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(threeArr[j].disclaimer) == 'string' && threeArr[j].disclaimer != '' && threeArr[j].disclaimer != '0' ){
						url=ct+threeArr[j].href+"&disclaimer="+threeArr[j].disclaimer;
						
					}else{
						url=ct+threeArr[j].href;
						
					}
					// //遍历判断是否还有下一级菜单
					// for(var x=0; x<oneArr.length; x++){
					// 	if( oneArr[x].parentId == threeArr[j].id ){
					// 		url=basePath()+"/app/menu/"+"nextList?id="+threeArr[j].id+"&name="+name;
					// 		break;
					// 	}
					// }
				// }
				str=str+'<dd><a onclick="countFn(\''+threeArr[j].modelid+'\',\''+threeArr[j].id+'\',\''+threeArr[j].parentIds+'\',\''+threeArr[j].href+'\',\''+threeArr[j].name+'\',\''+threeArr[j].disclaimer+'\',\''+url+'\')" ><div class="left"><div></div></div><div class="right">'+ threeArr[j].name +'</div></a></dd>'; 
			}
			str=str+'</dl>';
		} 
		return str;
}
//对数组排序
function sortArr(arr){
	var temp;
	for(var i=0; i<arr.length-1; i++){
		for(var j=i+1; j<arr.length; j++){
			if( arr[i].sort>arr[j].sort ){
				temp=arr[i];
				arr[i]=arr[j];
				arr[j]=temp;
			}
		}
	}
}
//显示或隐藏子菜单
function childsMenuHide(){
	$(".fiveTree  dd").hide();
	$(".fiveTree  dt").click(function(){
		if($(this).next().is(":hidden")){
			$(this).find(".xiala").attr("class","xiala");
		}else{
			$(this).find(".xiala").attr("class","xiala"+" down");
		}
		$(this).nextUntil("dt").toggle(200);
		
	});
}

//足迹和点击量统计
function countFn(modelId,mId,mParentIds,mUrl,mName,disclaimer,url){
	if(typeof(phoneCode)=='string' && phoneCode != ''){
		$.getJSON(ct+"/a/browse/sdbstBrowse/save?modelId="+modelId+"&types="+types+"&brand="+brand+"&versions="+versions+"&phoneCode="+phoneCode+"&operator="+operator+"&area="+area+"&mId="+mId+"&mParentIds="+mParentIds+"&mUrl="+mUrl+"&mName="+mName, function(){});
	}
	
	//微信免责声明
	if(typeof(disclaimer) == 'string' && disclaimer != '' && disclaimer != '0' && typeof(runEnviron) == "string" && runEnviron == 'wx'){
		$("#disclaimer .d-btn .d-btn-r a").attr('href', url);
		$.getJSON(ct+"/app/menu/getDisclaimer?type=disclaimer&value="+disclaimer, 
			function(dict){
				$("#disclaimer .d-content span").text(dict.remarks);
			}
		);
		$("#disclaimer").show();
	}else{
		window.location.href=url;
	}
}

//16宫格格式   
function menu16Fn(ct,noHead,runEnviron,sort,column,img){
    var str="";
    var num=0;
	if(sort!=undefined){
		var floor=Math.ceil(sort.length/column);
	}else{
		return str;
	}
    str=str+'<div id="menu16Grids">';
    for(i=0;i<floor;i++){
    	if(typeof(runEnviron) != "undefined" && runEnviron == 'wx' ){
			//微信控制
			if( sort[i].wxShow!=1 ){continue;}
		}else{
			//APP控制
			if( sort[i].mobileShow!=1 ){continue;}
		}
        str=str+'<div class="ps">';
        for(j=0;j<column;j++){
            if(i==floor-1 && (column-j)<=(floor*column-sort.length)){
                str=str+'<div class="ps-format"></div>';
            }else{
            	
            	//对name中的特殊字符&和"进行转义
        		var name=sort[num].name;
        		name=name.replace(/\&/g,'%26');
        		name=name.replace(/\"/g,'%5C"');
            	
            	var url="";
            	if( sort[num].type=="fourList" || sort[num].type=="fourSlider" || sort[num].type=="five16" || sort[num].type=="fiveTree" || sort[num].type=="fiveSliderList" || sort[num].type=="sixTree"){
            		url=sort[num].type+'?id='+sort[num].id+'&name='+name+"&noHead="+noHead;
            		
            	}else if( typeof(sort[num].href) == "string" && sort[num].href.indexOf("http")==0 ){
            		url=sort[num].href;
            		
            	}else if( typeof(sort[num].href) == "string" && sort[num].href.indexOf("/app/menu/interface/getData") >= 0 && typeof(sort[num].disclaimer) == 'string' && sort[num].disclaimer != '' && sort[num].disclaimer != '0' ){
            		url=ct+sort[num].href+"&noHead="+noHead+"&disclaimer="+sort[num].disclaimer;
            		
            	}else{
            		url=ct+sort[num].href+"&noHead="+noHead;
            		
            	}
            	
            	str=str+'<a  onclick="countFn(\''+sort[num].modelid+'\',\''+sort[num].id+'\',\''+sort[num].parentIds+'\',\''+sort[num].href+'\',\''+sort[num].name+'\',\''+sort[num].disclaimer+'\',\''+url+'\')"  class="ps-format"><div class="ps-fi"><img src='+img+sort[num].mobileIcon+'/><p>'+sort[num].name+'</p></div></a>';
                num++; 
            }
        }
        str=str+"</div>";
    }
    str=str+'</div>';
    return str;
}

    
