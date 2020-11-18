/*我的预约*/

//当前日期
var now = new Date();
var year = now.getFullYear();
var month = now.getMonth()+1;
if(month < 10){
	month = "0" + month;
};
var day = now.getDate();
if(day < 10){
	day = "0" + day;
};
var nowdate = year+""+month+""+day;

var oppo = "sdapp";
var pwd = oppo+""+nowdate;
var hash = hex_md5(pwd);

function getAppointDataFn(){
	var url = "http://19.200.92.241/appoint?action=getClass&oppo="+oppo+"&pwd="+hash+"&centerid=000003,000009,000001,000002";   
	console.log(url);
	var result;
	$.ajax({
		url:ct+"/app/menu/getJson",
		type:"get",
		data:{"url":url},
		dataType:"json",
		async:false,
		success:function(data){
			result=data;
		}
	});
	return result;
	
}

