<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="${ctxStatic}/modules/app/css/guideDetail.css" />
<script src="${ctxStatic}/modules/app/js/iconfont.js"></script>
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<style type="text/css">
	.container { background-color:#EEE; }
</style>
<title>详情</title>
</head>
<body>
<div class="container">
    <!--内容-->
    <div id="content">
    	<dl id="name">
        	<dt>办理事件</dt>
            <dd></dd>
        </dl>
    	<dl id="serviceObject">
        	<dt>办理对象</dt>
            <dd></dd>
        </dl>
        <dl id="applyCondition">
        	<dt>办理条件</dt>
        </dl>
        <dl id="stuffs">
        	<dt>所需材料</dt>
        </dl>
        <dl id="process">
        	<dt>窗口办理流程</dt>
        </dl>
        <dl id="onlineWorkflow">
        	<dt>网上办理流程</dt>
        </dl>
        <dl id="isCharge">
        	<dt>收费标准</dt>
            <dd></dd>
        </dl>
        <dl>
        	<dt>办理时限</dt>
            <dd id="ptLegalLimit">法定期限：<span></span></dd>
            <dd id="ptLimit">承诺期限：<span></span></dd>
        </dl>
        <dl>
        	<dt>办理窗口</dt>
            <dd id="workTime"><span></span></dd>
            <dd id="address">地址：<span></span><a></a></dd>
            <dd id="telePhone">联系电话：<span></span><a></a></dd>
            <dd id="transguide">公交指引：<span></span></dd>
        </dl>
        <dl id="legalBasis">
        	<dt>办理依据</dt>
            <dd><span></span><a></a></dd>
        </dl>
        <dl id="descripiton">
        	<dt>备注</dt>
            <dd><span></span><a></a></dd>
        </dl>
        <dl>
        	<dt>发布单位</dt>
            <dd id="acceptUnit"><span></span></dd>
            <dd id="createDate"><span></span></dd>
        </dl>
    </div>
</div> 
</body>
</html>

<script>
	//获取详情页ItemId和地区areaId
	var itemId="${param.id}";
	var areaId="${param.areaId}";
	
	//要访问的url地址，需要把url地址中的&换成*
	var url="http://202.104.25.196/RXWSSTWeb/xzsp.web?guideDetail*approveItemId="+itemId+"*areaId="+areaId;
			
	var leng=60;
	var applyCondition=new Array();	//申请条件
	var stuffs=new Array();			//所需材料
	var index=0;
	var process=new Array();		//处理流程
	var onlineWorkflow=new Array();	//网上办理流程
	var address='';					//地址
	var telePhone='';				//联系电话
	var legalBasis='';				//法律依据
	var descripiton='';				//备注
			
	$(function(){
		
		$.ajax({
			url:"${ct}/app/menu/getjson?url="+url,
			type:"post",
			dataType:"xml",
			success:function(result){
				//总数据
				var data=$(result).find("Response").find("Body").find("itemDetail");
				
				//标题
				var titleName=$(data).children("name").text();
				if(titleName.length>10){
					titleName=titleName.substring(0,10)+"...";
				}
				$("title").text( titleName );
				
				//办理事件
				$("#content #name dd").text( $(data).children("name").text() );
				//办理对象
				$("#content #serviceObject dd").text( $(data).children("serviceObject").text() );
				
				//申请条件
				applyCondition=splitFn( $(data).children("applyCondition").text() );
				for(var i=0; i<applyCondition.length && i<2; i++){
					$("#content #applyCondition").append( "<dd>"+applyCondition[i]+"</dd>" );
				}
				if(applyCondition.length>2){
					$("#content #applyCondition").append( "<dd><a onclick='applyConditionFn()'>&gt;&gt;&gt;</a></dd>" );
				}
				
				//所需材料
				$(data).find("stuffs").find("stuffDetail").each(function(i, detail){
					var name= $(detail).attr("name");
					if( typeof(name) != "undefined" ){
						stuffs[index]="<dd>"+(i+1)+"."+ name +"</dd>";
						index++;
					}
					$(detail).find("stuff").each(function(j, stuff){
						stuffs[index]="<dd>"+(i+1)+"-"+(j+1)+ $(stuff).text() +"</dd>";
						index++;
					});
				});
				for(var i=0; i<stuffs.length && i<2; i++){
					$("#content #stuffs").append( stuffs[i] );
				}
				if(stuffs.length>2){
					$("#content #stuffs").append( "<dd><a onclick='stuffsFn()'>&gt;&gt;&gt;</a></dd>" );
				}
				
				//处理流程
				process=splitFn( $(data).children("process").text() );
				for(var i=0; i<process.length && i<2; i++){
					$("#content #process").append( "<dd>"+process[i]+"</dd>" );
				}
				if(process.length>2){
					$("#content #process").append( "<dd><a onclick='processFn()'>&gt;&gt;&gt;</a></dd>" );
				}
				
				//网上办理流程
				onlineWorkflow=splitFn( $(data).children("onlineWorkflow").children("name").text() );
				for(var i=0; i<onlineWorkflow.length && i<2; i++){
					$("#content #onlineWorkflow").append( "<dd>"+onlineWorkflow[i]+"</dd>" );
				}
				if(onlineWorkflow.length>2){
					$("#content #onlineWorkflow").append( "<dd><a onclick='onlineWorkflowFn()'>&gt;&gt;&gt;</a></dd>" );
				}
				
				//收费标准
				$("#content #isCharge dd").text( $(data).children("isCharge").text() );
				
				//办理时限
				$("#content #ptLegalLimit span").text( $(data).children("ptLegalLimit").text() );
				$("#content #ptLimit span").text( $(data).children("ptLimit").text() );
				
				//办理窗口
				$("#content #workTime span").text( $(data).children("workTime").text() );
				
				address=$(data).children("address").text();
				if(address.length > leng){
					$("#content #address a").text('>>>');
				}
				$("#content #address span").text( address.substring(0, leng) );
				
				telePhone=$(data).children("telePhone").text();
				if(telePhone.length > leng){
					$("#content #telePhone a").text('>>>');
				}
				$("#content #telePhone span").text( telePhone.substring(0, leng) );
				
				$("#content #transguide span").text( $(data).children("transguide").text() );
				
				//法律依据
				legalBasis=$(data).children("legalBasis").text();
				if(legalBasis.length > leng){
					$("#content #legalBasis dd a").text('>>>');
				}
				$("#content #legalBasis dd span").text( legalBasis.substring(0, leng) );
				
				//备注
				descripiton=$(data).children("descripiton").text();
				if(descripiton.length > leng){
					$("#content #descripiton dd a").text('>>>');
				}
				$("#content #descripiton dd span").text( descripiton.substring(0, leng) );
				
				//发布单位
				$("#content #acceptUnit span").text( $(data).children("acceptUnit").text() );
				$("#content #createDate span").text( $(data).children("createDate").text() );
			}
		});
		
		
		//办理窗口
		$("#content #address a").click(function(){
			if( $(this).text()=='>>>' ){
				$(this).text('<<<');
				$("#content #address span").text( address );
			}else if( $(this).text()=='<<<' ){
				$(this).text('>>>');
				$("#content #address span").text( address.substring(0, leng) );
			}
		});
		$("#content #telePhone a").click(function(){
			if( $(this).text()=='>>>' ){
				$(this).text('<<<');
				$("#content #telePhone span").text( telePhone );
			}else if( $(this).text()=='<<<' ){
				$(this).text('>>>');
				$("#content #telePhone span").text( telePhone.substring(0, leng) );
			}
		});
		
		//法律依据
		$("#content #legalBasis dd a").click(function(){
			if( $(this).text()=='>>>' ){
				$(this).text('<<<');
				$("#content #legalBasis dd span").text( legalBasis );
			}else if( $(this).text()=='<<<' ){
				$(this).text('>>>');
				$("#content #legalBasis dd span").text( legalBasis.substring(0, leng) );
			}
		});
		
		//备注
		$("#content #descripiton dd a").click(function(){
			if( $(this).text()=='>>>' ){
				$(this).text('<<<');
				$("#content #descripiton dd span").text( descripiton );
			}else if( $(this).text()=='<<<' ){
				$(this).text('>>>');
				$("#content #descripiton dd span").text( descripiton.substring(0, leng) );
			}
		});
	
	});
	
	//申请条件
	function applyConditionFn(){
		var da=$("#content #applyCondition dd a");
		var str="<dt>申请条件</dt>";
		if( da.text()=='>>>' ){
			for(var i=0; i<applyCondition.length; i++){
				str=str+"<dd>"+applyCondition[i]+"</dd>";
			}
			str=str+"<dd><a onclick='applyConditionFn()'>&lt;&lt;&lt;</a></dd>";
		}else if( da.text()=='<<<' ){
			for(var i=0; i<applyCondition.length && i<2; i++){
				str=str+"<dd>"+applyCondition[i]+"</dd>";
			}
			str=str+"<dd><a onclick='applyConditionFn()'>&gt;&gt;&gt;</a></dd>";
		} 
		$("#content #applyCondition").html(str);
	}
	
	//所需材料
	function stuffsFn(){
		var da=$("#content #stuffs dd a");
		var str="<dt>所需材料</dt>";
		if( da.text()=='>>>' ){
			for(var i=0; i<stuffs.length; i++){
				str=str+stuffs[i];
			}
			str=str+"<dd><a onclick='stuffsFn()'>&lt;&lt;&lt;</a></dd>";
		}else if( da.text()=='<<<' ){
			for(var i=0; i<stuffs.length && i<2; i++){
				str=str+stuffs[i];
			}
			str=str+"<dd><a onclick='stuffsFn()'>&gt;&gt;&gt;</a></dd>";
		} 
		$("#content #stuffs").html(str);
	}
	
	//处理流程
	function processFn(){
		var da=$("#content #process dd a");
		var str="<dt>处理流程</dt>";
		if( da.text()=='>>>' ){
			for(var i=0; i<process.length; i++){
				str=str+"<dd>"+process[i]+"</dd>";
			}
			str=str+"<dd><a onclick='processFn()'>&lt;&lt;&lt;</a></dd>";
		}else if( da.text()=='<<<' ){
			for(var i=0; i<process.length && i<2; i++){
				str=str+"<dd>"+process[i]+"</dd>";
			}
			str=str+"<dd><a onclick='processFn()'>&gt;&gt;&gt;</a></dd>";
		} 
		$("#content #process").html(str);
	}
	
	//网上办理流程
	function onlineWorkflowFn(){
		var da=$("#content #onlineWorkflow dd a");
		var str="<dt>网上办理流程</dt>";
		if( da.text()=='>>>' ){
			for(var i=0; i<onlineWorkflow.length; i++){
				str=str+"<dd>"+onlineWorkflow[i]+"</dd>";
			}
			str=str+"<dd><a onclick='onlineWorkflowFn()'>&lt;&lt;&lt;</a></dd>";
		}else if( da.text()=='<<<' ){
			for(var i=0; i<onlineWorkflow.length && i<2; i++){
				str=str+"<dd>"+onlineWorkflow[i]+"</dd>";
			}
			str=str+"<dd><a onclick='onlineWorkflowFn()'>&gt;&gt;&gt;</a></dd>";
		} 
		$("#content #onlineWorkflow").html(str);
	}
	
	//拆分字符串
	function splitFn(str){
		var nn=new Array('（一）','（二）','（三）','（四）','（五）','（六）','（七）','（八）','（九）','（十）');
		var arr=new Array();
		var i=0;
		var num=2;
		if(str.indexOf(nn[1])>=0){
			for(var j in nn){
				if(str.indexOf(nn[j])>=0){
					arr[i]=str.substring(0,str.indexOf(nn[j]));
					str=str.substring(str.indexOf(nn[j]));
					i++;
				}else{
					break;
				}
			}
		}else if(str.indexOf(num+'.')>=0){
			while(str.indexOf(num+'.')>=0){
				arr[i]=str.substring(0,str.indexOf(num+'.'));
				str=str.substring(str.indexOf(num+'.'));
				i++;
				num++;
			}
		}else if(str.indexOf('应'+num)>=0){
			while(str.indexOf('应'+num)>=0){
				arr[i]=str.substring(0,str.indexOf('应'+num));
				str=str.substring(str.indexOf('应'+num));
				i++;
				num++;
			}
		}else if(str.indexOf(num+'：')>=0){
			while(str.indexOf(num+'：')>=0){
				arr[i]=str.substring(0,str.indexOf(num+'：'));
				str=str.substring(str.indexOf(num+'：'));
				i++;
				num++;
			}
		}else if(str.indexOf(num+'、')>=0){
			while(str.indexOf(num+'、')>=0){
				arr[i]=str.substring(0,str.indexOf(num+'、'));
				str=str.substring(str.indexOf(num+'、'));
				i++;
				num++;
			}
		}
		arr[i]=str;
		return arr;
	}
	
</script>

