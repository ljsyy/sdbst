var prompt={
	promptAll:"#prompt",
	promptContant:"#prompt .prompt-contant",
	promptFont:"#prompt .prompt-font",
	promptButton:"#prompt .prompt-button",
	config:false,//选择自己配置颜色
	hidden_button:false//是否隐藏按钮
}
//显示弹出框内容
/**
font:标题
hb:按钮是否隐藏
button：{
	name:按钮名,
	color:按钮颜色,
	callback:按钮事件
}
*/
prompt.print=function(font,hb,button){
	var buttonparent = $(this.promptButton);
	buttonparent.html("");
	//console.log(arguments)返回参数的数组
	$(this.promptFont).text(font);
	for(var b in button){
		var style = '';
		if(button[b].color != null){
				style = "background:" + button[b].color;
		}
		buttonparent.append("<a style='" + style + "'>" + button[b].name + "</a>");
		buttonparent.find(" a:eq(" + b + ")").bind("click",button[b].callback);
	}
	$(this.promptAll).attr("class","prompt ani");
	if(hb)
		buttonparent.css("display","none");
}
prompt.init=function(){
	var html =　'<div id="prompt" class="prompt">'+
					'<div class="prompt-contant">'+
					 	'<p class="tip">温馨提示</p>'+
						'<p class="prompt-font"></p>'+
						'<div class="prompt-button"></div>'+
					'</div>'+
				'</div>';
	$("#prompt").remove();
	$("body").prepend(html);
	//关闭、确定事件
	/* $(this.jiaocha).bind("click",{parent:this},function(my){var parent=my.data.parent;$(parent.promptAll).attr("class","prompt");}); */
	if(this.hidden_button)
		this.promptButton.css("display","none");
}