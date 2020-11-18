<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上信访/局长信箱</title>
<meta name="decorator" content="blank"/><base>
	<link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.7-dist/css/bootstrap.css">
<style type="text/css">
	td{padding:8px;}
	#button{height:50px;background-color:#eb413d; color:white;font-size: 23px;border:none;border-radius:10px;}
	#head{position:fixed; top:0;width:100%;background-color:#eb413d;line-height: 50px;font-size: 22px;text-align: center;}
	#h50{height:50px;}
	#head span{background-color: #eb413d;color:white; padding-right:50px;}
	#head img{width:50px;height:49px;float:left;}
	option{
	font-size:14px; 
    font-family:"宋体";
    font-weight:normal;
	}
	select{    
	text-indent: 0.01px;
    text-overflow: '';
    border: 0px solid #CCC;
    color: #888;
    height: 30px;
    line-height: 15px;
    margin-top: 2px;
    outline: 0 none;
    padding: 5px 0px 5px 5px;
    width: 220px;
    -webkit-border-radius: 4px;
    font-size:14px; 
    font-family:"宋体";
    font-weight:normal;
    }
    input{
    border: 0px solid #CCC;
    color: #888;
    height: 30px;
    line-height: 15px;
    margin-right: 6px;
    margin-top: 2px;
    outline: 0 none;
    padding: 5px 0px 5px 5px;
    width: 220px;
    -webkit-border-radius: 4px;
    font-size:14px; 
    font-family:"宋体";
    font-weight:normal;
    }
    textarea{
    border: 0px solid #CCC;
    color: #888;
    height: 86px;
    line-height: 15px;
    margin-right: 6px;
    margin-top: 2px;
    outline: 0 none;
    padding: 5px 0px 5px 5px;
    width: 220px;
    font-size:14px; 
    font-family:"宋体";
    font-weight:normal;
    -webkit-border-radius: 4px
    }
    *{
	font: 14px "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-weight:bold;
    color: #5F5C5C;	
	}
	
</style>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
	

	<center id="show" style="background-color: #F4F4F4;">
		<table>
			<tr>
				<td>目&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp的：</td>
				<td>
					<select id="purpose">
						<option value="-1">== 请选择 ==</option>
						<option value="意见建议">意见建议</option>
						<option value="申诉">申诉</option>
						<option value="求决">求决</option>
						<option value="揭发控告">揭发控告</option>
						<option value="咨询">咨询</option>
						<option value="行政服务">行政服务</option>
						<option value="纪委">纪委</option>
						<option value="监察">监察</option>
						<option value="领导">领导</option>
						<option value="其他">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>所属镇街：</td>
				<td>
					<select id="street">
						<option value="-1">== 请选择 ==</option>
						<option value="大良">大良</option>
						<option value="容桂">容桂</option>
						<option value="伦教">伦教</option>
						<option value="勒流">勒流</option>
						<option value="陈村">陈村</option>
						<option value="北滘">北滘</option>
						<option value="乐从">乐从</option>
						<option value="龙江">龙江</option>
						<option value="杏坛">杏坛</option>
						<option value="均安">均安</option>
						<option value="其他">其他</option>
						<option value="全区">全区</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>反映地址：</td>
				<td><input id="address" placeholder="请输入反映地址"/></td>
			</tr>
			<tr>
				<td>反映主题：</td>
				<td><input id="subject" placeholder="请输入反映主题"/></td>
			</tr>
			<tr>
				<td valign="top" style="padding-top: 12px;">反映内容：</td>
				<td><textarea cols="30" rows="4" id="content" placeholder="请输入反映内容" ></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="padding: 0px;"><hr/></td>
			</tr>
			
			<tr>
				<td colspan="2">请告诉我们如何与您取得联系</td>
			</tr>
			
			<tr>
				<td>真实姓名：</td>
				<td><input id="name" placeholder="请输入真实姓名"/></td>
			</tr>
			<tr>
				<td>性别/组别：</td>
				<td>
					<select id="sex">
						<option value="-1">==请选择==</option>
						<option value="女">女</option>
						<option value="男">男</option>
						<option value="公司">公司</option>
						<option value="集体">集体</option>
						<option value="家庭">家庭</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td><input id="num" placeholder="请输入身份证号"/></td>
			</tr>
			<tr>
				<td>联系电话：</td>
				<td><input id="phone" placeholder="请输入联系电话"/></td>
			</tr>
			<tr>
				<td>电子邮箱：</td>
				<td><input id="email" placeholder="请输入电子邮箱"/></td>
			</tr>
			<tr>
				<td>联系地址：</td>
				<td><input id="addContact" placeholder="请输入联系地址"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="button" type="button" onclick="check()" value="开始发送邮件"/>
				</td>
			</tr>
		</table>
	</center>
	
	
	
	
	
	
	

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					提示
				</h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
	
	
	
	
	
	
	
	

<script type="text/javascript">
	var isSend=true;	/// 邮件发送期间 标记位
	
	$(function(){
		$("tr td").eq(2).css("align","");
	});
	function check(){
		
		var purpose=$("#purpose").val();
		var street=$("#street").val();
		var address=$("#address").val();
		var subject=$("#subject").val();
		var content=$("#content").val();
		
		var name=$("#name").val();
		var sex=$("#sex").val();
		var num=$("#num").val();
		var phone=$("#phone").val();
		var email=$("#email").val();
		var addContact=$("#addContact").val();

		if(checkNull(purpose,"目的"))
			return false;
		if(checkNull(street,"所属镇街"))
			return false;
		if(checkNull(address,"反映地址"))
			return false;
		if(checkNull(subject,"反映主题"))
			return false;
		if(checkNull(content,"反映内容"))
			return false;
		
		if(checkNull(name,"姓名"))
			return false;
		if(checkNull(sex,"性别/组别"))
			return false;
		if(checkNull(num,"身份证号"))
			return false;
		if(checkNull(phone,"联系电话"))
			return false;
		if(checkNull(email,"电子邮箱"))
			return false;
		if(checkNull(addContact,"联系地址"))
			return false;
		
		if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phone) || !(/^1[34578]\d{9}$/.test(phone))){
			$(".modal-body").text('联系电话格式错误,请重填!');
			$('#myModal').modal("show");
			return false;
		}
		if(!/^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/.test(email)){
			$(".modal-body").text("请输入正确的邮箱格式!");
			$('#myModal').modal("show");
			return false;
		}
		if(!/^(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)$/.test(num)){
			$(".modal-body").text("请输入正确的身份证号格式!");
			$('#myModal').modal("show");
			return false;
		}
		
		var contentx=content;	//字符处理,超过20个字符则换行
		content="";
		var lineSize=20;
		for(var i=0;contentx.length>0;i++){
			if(contentx.length>lineSize){
				content=content+contentx.substring(0,lineSize)+"<br/>";
				contentx=contentx.substring(lineSize,contentx.length);
			}else{
				content=content+contentx;
				contentx="";
			}
		}
		
		var data={
			"purpose":purpose,
			"street":street,
			"address":address,
			"subject":subject,
			"content":content,
			
			"name":name,
			"sex":sex,
			"num":num,
			"phone":phone,
			"email":email,
			"addContact":addContact,
		}
		
		if(isSend){	//判断是否可以发送邮件
			isSend=false;
			$.ajax({
				url:"${ct}/government/petition/chiefemail/submit",
				data:data,
				success:function(data){
					$(".modal-body").text(data);
					$('#myModal').modal("show");
					isSend=true;
				},
				error:function(data){
					$(".modal-body").text("邮件发送失败...");
					$('#myModal').modal("show");
					isSend=true;
				}
			})
		}else{
			$(".modal-body").text("邮件正在发送,请稍后再操作...");
			$('#myModal').modal("show");
		}
		
	}
	
	function checkNull(data,message){
		if(data==""){
			$(".modal-body").text(message+" 不能为空!");
			$('#myModal').modal("show");
			return true;
		}else if(data=="-1"){
			$(".modal-body").text("请选择 "+message);
			$('#myModal').modal("show");
			return true
		}else{
			return false;
		}
	}
	
</script>
</body>
</html>