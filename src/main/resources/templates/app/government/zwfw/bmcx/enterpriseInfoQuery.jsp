<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/app/css/appoint.css" />
<script src="${ctxStatic}/modules/app/js/lib/jquery.min.js"></script>

<link rel="stylesheet" href="${ctxStatic}/modules/app/js/plug/alertPopShow.css">
<script src="${ctxStatic}/modules/app/js/plug/alertPopShow.js"></script>
<script src="${ctxStatic}/modules/app/js/load-ani.js"></script>

<style type="text/css">
	.doAs-content { max-width:720px; padding:0px; margin:0px auto; }
	.doAs-content table { margin-bottom: 10px; }
	.doAs-content table tr td { font-size:16px; font-weight:500; line-height:26px; }
	.doAs-content table tr td input { text-indent:10px; line-height:26px; border:0px; width:100%;
						 	  		  font-size:16px; font-weight:500; }
	.doAs-content .line { border-bottom:1px solid #EEE; }
	.resultShow-content { max-width:720px; padding:0px; margin:0px auto; }
	.resultShow-content div { font-size:16px; font-weight:500; line-height:26px; }
	
	button{
	    width: 90%;
	    font-size: 16px;
	    border: 0;
	    background: #eb413d;
	    padding: 5px;
	    border-radius: 5px;
	    color: #fff;
	}
</style>
<title>企业资料查询</title>
</head>

<body>
    <div class="doAs-content">
    	<table border="0" cellpadding="5" cellspacing="0" width="100%" align="left">
            <tr><td>企业名称：</td></tr>
            <tr>
                <td class="line"> 
                	<input type="text" id="corpname" placeholder="请输入企业名称" maxlength="30" size="30" />
                </td>
            </tr>
            <tr><td>社会统一信用代码：</td></tr>
            <tr>
                <td class="line"> 
                	<input type="text" id="registerno" placeholder="请输入注册号" size="30" maxlength="30" />
                </td>
            </tr>
    	</table>
    </div>
    <div id="container">
        <div class="btn">
        	<center><button type="submit">查询</button></center>
        </div>
    </div>
    <div class="resultShow-content"></div>
</body>

</html>
<script>



$(function(){
	
	$("#container .btn button").click(function(){
		
		var corpname=$("#corpname").val();
		var registerno=$("#registerno").val();
		//去掉空格
		corpname=corpname.replace(/(^\s*)|(\s*$)/g, "");
		registerno=registerno.replace(/(^\s*)|(\s*$)/g, "");
		//console.log("转码前："+corpname);
		corpname=encodeURI(corpname);
		//console.log("转码后："+corpname);
		
		var url="";
		var param="/jeesite/qiye/qiyeziliao/list";
		url=url+param;
		
		if(corpname == ""&&registerno == ""){
			//alert("请输入查询信息");
			webToast("请输入查询信息","middle",3000);
			return;
		}
		
		load_ani.open("pointloading");//rect square dot cube dotGo scaleout pointloading
		$.ajax({
			//url:"${ct}/app/menu/getData?url=http://sdbst.shunde.gov.cn:8082/shundemap/a/qiye/vEnterpriseMaterial/list",
			url:url,
			type:"post",
			//data:{"param": "qyname="+corpname+"&num="+xynum },
			data:"qyname="+corpname+"&xynum="+registerno,
			dataType:"json",
			success:function(result){
				load_ani.remove();
				//console.log(corpname)
				if(result == ""){
					//alert("查询无数据！");
					webToast("查询无数据！","middle",3000);
					return;
				}else{
					$(".doAs-content").html("");
					$("#container").html("").css("padding","0");
					var data = result;
					for(var i in data){
						var uniscid;
						if(data[i].uniscid == "" || data[i].uniscid == undefined || data[i].uniscid == null){
							uniscid = "暂无记录";
						}else{
							uniscid = data[i].uniscid;
						}
						var foundingdate;
						if(data[i].foundingdate == "" || data[i].foundingdate == undefined || data[i].foundingdate == null){
							foundingdate = "暂无记录";
						}else{
							foundingdate = data[i].foundingdate;
						}
						$(".resultShow-content").append(
								"<div>企业名称："+data[i].corpname+"</div>"+
								"<div>社会统一信用代码："+uniscid+"</div>"+
								"<div>状态："+data[i].enterprisestatusname+"</div>"+
								"<div>注册号："+data[i].registerno+"</div>"+
								"<div>地址："+data[i].address+"</div>"+
								"<div>负责人："+data[i].principal+"</div>"+
								"<div>登记机关："+data[i].aicname+"</div>"+
								"<div>成立日期："+foundingdate+"</div>"+
								"<div>核准日期："+data[i].approvedate+"</div>"+
								"<div>经营范围："+data[i].businessscope+"</div>"+
								"<hr/>"
						);
						
					}
				}
				
	
			}
			
		});
		
	
	});
});
	


</script>