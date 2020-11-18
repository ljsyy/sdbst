<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/app/css/appoint.css" />
<style type="text/css">
	body { background-color:#FFF; }
	.content input { text-indent:10px; line-height:28px; border:0px; width:100%;
					 font-size:18px; font-weight:500; }
</style>
<title>电费查询</title>
</head>

<body>
    <form action="#" method="post">
    <div class="content">
    	<table border="0" cellpadding="10" cellspacing="0" width="100%" align="center">
            <tr>
                <td width="35%">用户编号：</td>
                <td> 
                	<input type="text" name="userNo" placeholder="请输入用户编号" maxlength="20" size="20" />
                </td>
            </tr>
            <tr>
                <td>用户名称：</td>
                <td> 
                	<input type="text" name="number" placeholder="请输入用户名称" size="20" maxlength="20" />
                </td>
            </tr>
    	</table>
    </div>
    <div id="container">
        <div class="btn">
        	<input type="submit" value="查询" />
        </div>
    </div>
    </form>
</body>

</html>