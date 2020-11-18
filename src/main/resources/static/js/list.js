$(function(){
	
	//返回按钮
	$(".retrun-icon").click(function() {
		window.history.back();
	});
	
	//弹框确定按钮
	$("#simpleWD .sure").click(function(){
		$("#simpleWD .content").text('');
		$("#simpleWD").hide(300);
	});
	
});


