/**
 * 提示框函数
 * 说明：点击显示
 * @return {load ,config, close, open}
 * @author pjx 2018/03/29
 * @version 1.1.0
 * @example 看下面操作步骤
*/
var prompt = (function (index, pos, q) {
    //局部变量
    var id = index;
    var contant, title, close, font, button, a;

    //配置
    var configStyle = {//自己配置颜色
        background: "rgba(0, 0, 0, 0.3)",//背景颜色
        contant: "#2e3846",//内容框颜色
        close: "#fff",//交叉字体颜色
        title: "#fff",//标题字体颜色
        font: "#fff",//内容字体颜色
        button: "#795aac",//按钮默认颜色
        vis_close: true,//是否显示关闭按钮
        vis_font: true,//是否显示字体
        vis_button_are: true//是否显示按钮区域
    }
    function fnConfig() {
        var $sid = "#" + id + " .";
        $("#" + id).css("background-color", configStyle.background);
        $($sid + contant).css("background-color", configStyle.contant);
        $($sid + close + " close").css("background", configStyle.close);
        $($sid + title).css("color", configStyle.title);
        $($sid + font).css("color", configStyle.font);
        $($sid + a).css("background-color", configStyle.button);
        $($sid + close + " close").css("display",
            (configStyle.vis_close) ? "block" : "none"
        );
        $($sid + font).css("display",
            (configStyle.vis_font) ? "block" : "none"
        );
        $($sid + button).css("display",
            (configStyle.vis_button_are) ? "block" : "none"
        );
    }
    //通用函数
    function fnFormatModel(str, model) {//模板格式化（正则替换）
        for (var k in model) {
            var re = new RegExp("{{" + k + "}}", "g");
            str = str.replace(re, model[k]);
        }
        return str;
    }
    //创建html、css
    var html = '<div id="{{name}}">' +
        '<div class="{{name}}-contant">' +
        '<p class="{{name}}-close"><close></close></p>' +
        '<p class="{{name}}-title">123</p>' +
        '<p class="{{name}}-font">123123123123</p>' +
        '<div class="{{name}}-button"></div>' +
        '</div>' +
        '</div>';
    var css = "#{{name}}{visibility: hidden;opacity: 0;height: 100%;width: 100%;background: rgba(0, 0, 0, 0.3);position: fixed;z-index: 999;-webkit-transition: all 0.3s;-moz-transition: all 0.3s;transition: all 0.3s;}#{{name}}.ani{visibility: visible;opacity: 1;}#{{name}} .{{name}}-contant{background: #2e3846;overflow:hidden;width: 300px;border-radius: 5px;left: 0;right: 0;margin: 0 auto;position: absolute;top: 50%;-webkit-transform: translate(0, -50%);-ms-transform: translate(0, -50%);transform: translate(0, -50%);}#{{name}} .{{name}}-close{position: absolute;right: 7px;top: 5px;width: 20px;height: 20px;cursor: pointer;}#{{name}} .{{name}}-close close{background: #fff;} .{{name}}-close close:before,#{{name}} .{{name}}-close close:after{position: absolute;background:inherit;content: '';width: 20px;top: 8px;height: 4px;transform: rotate(-45deg);}#{{name}} .{{name}}-close close:after{transform: rotate(45deg);}#{{name}} .{{name}}-close:hover close:before,#{{name}} .{{name}}-close:hover close:after{background-color: #3e4b5d;}#{{name}} .{{name}}-title{color: #fff;padding: 22px 35px 10px;line-height: 25px;letter-spacing: 1px;word-break: break-all;text-align: center;font-size: 20px;font-weight: 600;}#{{name}} .{{name}}-font{color: #fff;color: #3e4b5d;padding: 12px 35px 10px 20px;line-height: 25px;letter-spacing: 1px;word-break: break-all;text-align: center;    box-sizing: border-box;}#{{name}} .{{name}}-button{padding: 0 30px 20px;text-align: center;}#{{name}} .{{name}}-button a{margin: 5px;display: inline-block;cursor: pointer;background-color: #795aac;color: #fff;border: none;padding: 5px 25px;border-radius: 5px;}#{{name}} .{{name}}-button a:hover{background-color: #3e4b5d !important;}";
    function createHC(h, c) {
        var name = { "name": id };
        this.createHTML(fnFormatModel(h, name));
        this.createCSS(fnFormatModel(c, name));
    }
    createHC.prototype = {
        createHTML: function (str) {
            $(pos).prepend(str);
        },
        createCSS: function (str) {
            var style = '<style type="text/css">' + str + '</style>'
            $("head").append(style);
        },
    }
    //关闭按钮时间
    function fnClose() {
        var $sid = "#" + id;
        $($sid).removeClass("ani");
    }
    //按钮显示提示框
    function fnPrint(obj, n) {
        obj.unbind().click(function () {
            fnBox(n);
        });
    }
    //不用按钮提示框
    function fnBox(n) {
        var $sid = "#" + id;
        $($sid).addClass("ani");
        //可以不填
        var cancel = (n.cancel == undefined || n.cancel) ? true : false;
        var shut = (n.close == undefined) ? configStyle.vis_close : n.close;
        var tip = (n.title == undefined) ? true : false;
        //必填
        var txt = n.text;
        var btn = n.button;
        $($sid + " ." + button).html("");//提示框初始化
        for (var b in btn) {
            var color = (btn[b].color == undefined) ? configStyle.button : btn[b].color;
            $($sid + " ." + button).append("<a style='background-color:" + color + "'>" + btn[b].name + "</a>");
            $($sid + " ." + button).find(" a:eq(" + b + ")").unbind().bind("click", btn[b].callback);
        }
        //添加取消按钮
        if (cancel) {
            $($sid + " ." + button).append("<a style='background:#d74a4a'>取消</a>");
            $($sid + " ." + button).find(" a:eq(" + ($($sid + " ." + button + " a").length - 1) + ")").unbind().bind("click", fnClose);
        }
        //是否显示关闭
        if (shut) {
            $($sid + " ." + close + " close").css("display", "block");
        } else {
            $($sid + " ." + close + " close").css("display", "none");
        }
        //是否添加标题
        if (tip) {
            $($sid + " ." + title).css("display", "none");
        } else {
            $($sid + " ." + title).css("display", "block");
            $($sid + " ." + title).text(n.title);
        }
        $($sid + " ." + font).text(txt);
        $($sid).addClass("ani");
        $($sid + font).text();
    }
    //创建对象
    var repeat = false;//是否重复init
    return {
        load: function (o) {
            if (!repeat) {
                console.log(o)
                if (o != undefined) {
                    id = (o.id != undefined) ? o.id : id;
                    pos = (o.pos != undefined) ? o.pos : pos;
                } else {
                    id = id;
                    pos = pos;
                }
                contant = id + "-contant";
                close = id + "-close";
                title = id + "-title";
                font = id + "-font";
                button = id + "-button";
                a = id + "-button" + " a";
                new createHC(html, css);
                //初始化配置
                fnConfig();
                //显示按钮
                q.fn.print = function (n) {
                    fnPrint(q(this), n);
                }
                //关闭按钮事件
                $("#" + id + " ." + close).unbind().click(function () {
                    fnClose();
                });
                repeat = !repeat;
            } else {
                console.log("不能重复加载！");
            }
        },
        config: function (l) {
            for (var c in l) {
                configStyle[c] = l[c];
            }
            fnConfig();
        },
        close: function () {
            fnClose();
        },
        open: function(n){
            fnBox(n);
        }
    }
})("prompt", "body", jQuery);




/**操作步骤
	window.onload = function () {
		//第一步：加载提示框
			//简洁版
			prompt.load();
			//自定版
			// prompt.load({
			// 	id: "sc",//不填：默认为prompt，填：提示框的id
			// 	pos: ".container"//不填：默认为body，填：提示框的位置
			// });
		//第二步：可选操作
			prompt.config({//不可填
				background: "rgba(0, 0, 0, 0.3)",//背景颜色
				contant: "#2e3846",//内容框颜色
				close: "#fff",//交叉字体颜色
				title: "#fff",//标题字体颜色
				font: "#fff",//内容字体颜色
				button: "#795aac",//按钮默认颜色
				vis_close: true,//是否显示关闭按钮
				vis_font: true,//是否显示字体
				vis_button_are: true//是否显示按钮区域
			})
		//第三步：按钮添加事件
			//简洁版
			$("#test").print({
				title:"问题",
				text: "你是谁",
				button: [
					{
						name: "确定1",
						callback: function () {
							alert("我是你爷爷");
						}
					}
				]
			});
			//自定版
			// a= 1;//test
			// $("#test").print({
			// 	cancel: false,//不填：默认为显示取消按钮，填：false - 不显示
			// 	close: false,//不填：默认为配置显示，填：true - 显示，false - 不显示
			//	title:"问题",//不填：不现实标题
			// 	text: "我是你爷爷",//文档
			// 	button: [//按钮
			// 		{
			// 			color: "#f0f",//不填：默认为配置显示，填：按钮颜色
			// 			name: "确定2",//按钮名字
			// 			callback: function () {//按钮事件
			// 				alert("我是你孙子");
			// 				a = 5;//test
			// 			}
			// 		}
			// 	]
			// });
	}
 */