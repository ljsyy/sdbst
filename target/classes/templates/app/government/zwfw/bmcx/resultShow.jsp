<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>
<style type="text/css">
	body { background-color:#FFF; }
	.resultShow-content { max-width:720px; padding:0px; margin:0px auto; }
	.resultShow-content div { font-size:16px; font-weight:500; line-height:26px; }
</style>
<title>结果显示</title>
</head>

<body>
    <div class="resultShow-content"></div>
</body>

</html>
<script>


$(function(){
	//企业办照进度查询
	//获取acceptid
	var acceptid="${param.acceptid}";
	
	if(typeof(acceptid) != "undefined" && acceptid!=""){
		//url地址
		var url="http://sdbst.shunde.gov.cn:8082/shundemap/a/test/testYxb/list?mobileLogin=true*acceptid="+acceptid;
		
		$.ajax({
			url:"${ct}/app/menu/getjson?url="+url,
			type:"post",
			dataType:"json",
			success:function(result){
				var data=result.data;
				$(data).each(function(i){
					$(".resultShow-content").append("<div>企业名称："+data[i].entityname+"</div><div>业务号："+data[i].acceptid+"</div><div>业务类型："+data[i].accepttype+"</div><div>业务状态："+data[i].acttype+"</div><div>受理日期："+data[i].taskstartdate+"</div><hr/>");
				});
			}
		});
	}
	

	//企业资料查询
	//获取registerno
	var registerno="${param.registerno}";
	
	if(typeof(registerno) != "undefined" && registerno!=""){
		//url地址
		var url="http://sdbst.shunde.gov.cn:8082/shundemap/a/qiye/vEnterpriseMaterial/list?mobileLogin=true";
		
		$.ajax({
			url:"${ct}/app/menu/getjson?url="+url,
			type:"post",
			dataType:"json",
			success:function(result){
				var data=result.data;
				$(data).each(function(i){
					if( data[i].entityno==registerno || data[i].corpname==registerno || data[i].registerno==registerno){
						$(".resultShow-content").append("<div>企业名称："+data[i].corpname+"</div><div>企业状态："+data[i].enterprisestatusname+"</div><div>负责人："+data[i].principal+"</div><div>成立日期："+data[i].foundingdate+"</div><div>核准日期："+data[i].approvedate+"</div><div>登记机关："+data[i].aicname+"</div><div>地址："+data[i].address+"</div><div>经营范围："+data[i].businessscope+"</div><hr/>");
						
					}
				});
				
			}
		});
		
	}
	
});


</script>