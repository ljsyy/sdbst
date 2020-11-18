/**
 * 加载动画
 * 说明：点击显示
 * @return {}
 * @author pjx 2018/04/02
 * @version 1.0.0
 * @example 看下面操作步骤
*/
var load_ani = (function (w,p,i) {
    //局部变量
    var main = "<div style='position: fixed;z-index:9999999;height: 100%;width: 100%;background: rgba(0,0,0,.3);' id={{id}}></div>";
    //配置属性
    var config = {
        aniname : "LOADANI",//默认动画名
        background : "#fff"//默认动画颜色
    }
    //通用函数
    function fnFormatModel(str, model) {//模板格式化（正则替换）
        for (var k in model) {
            var re = new RegExp("{{" + k + "}}", "g");
            str = str.replace(re, model[k]);
        }
        return str;
    }
    function fnCSS(s){
        return fnFormatModel(s,{
            id :i,
            aniname :config.aniname,
            background : config.background
        });
    }
    //矩形
    rect = function () {
        var style = fnCSS('<style type="text/css">#{{id}} .spinner-rect{margin: 0 auto;top: 50%;margin-top: -30px;position:relative;width:75px;height:60px;text-align:center;font-size:10px}'+
        '#{{id}} .spinner-rect>div{margin: 0 3px;background-color:{{background}};height:100%;width:6px;display:inline-block;-webkit-animation:{{aniname}} 1.2s infinite ease-in-out;animation:{{aniname}} 1.2s infinite ease-in-out}'+
        '#{{id}} .spinner-rect>.rect2{-webkit-animation-delay:-1.1s;animation-delay:-1.1s}'+
        '#{{id}} .spinner-rect>.rect3{-webkit-animation-delay:-1s;animation-delay:-1s}'+
        '#{{id}} .spinner-rect>.rect4{-webkit-animation-delay:-.9s;animation-delay:-.9s}'+
        '#{{id}} .spinner-rect>.rect5{-webkit-animation-delay:-.8s;animation-delay:-.8s} '+
        '@-webkit-keyframes {{aniname}}{0%,100%,40%{-webkit-transform:scaleY(.4)}20%{-webkit-transform:scaleY(1)}} '+
        '@keyframes {{aniname}}{0%,100%,40%{transform:scaleY(.4);-webkit-transform:scaleY(.4)}20%{transform:scaleY(1);-webkit-transform:scaleY(1)}}</style>');
        var str = '<div class="spinner-rect">' +
            '<div class="rect1"></div>' +
            '<div class="rect2"></div>' +
            '<div class="rect3"></div>' +
            '<div class="rect4"></div>' +
            '<div class="rect5"></div>' +
            '</div>';
        return style + str;
    }
    //正方形
    square = function () {
        var style = fnCSS('<style type="text/css">#{{id}} .spinner-square {width: 60px;height: 60px;background-color: {{background}};position:absolute;margin: -30px auto;top: 50%;left: 0;right: 0;-webkit-animation: {{aniname}} 1.2s infinite ease-in-out;animation: {{aniname}} 1.2s infinite ease-in-out;}'+
        '@-webkit-keyframes {{aniname}} {0% { -webkit-transform: perspective(120px) }50% { -webkit-transform: perspective(120px) rotateY(180deg) }100% { -webkit-transform: perspective(120px) rotateY(180deg)  rotateX(180deg) }}'+
        '@keyframes {{aniname}} {0% {transform: perspective(120px) rotateX(0deg) rotateY(0deg);-webkit-transform: perspective(120px) rotateX(0deg) rotateY(0deg)} 50% {transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg);-webkit-transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg)} 100% {transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);-webkit-transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);}}</style>');
        var str = '<div class="spinner-square"></div>';
        return style + str;
    }
    //点变大变少
    dot = function () {
        var style = fnCSS('<style type="text/css">#{{id}} .spinner-dot{width: 60px;height: 60px;position: relative;top: 50%;margin: -30px auto;}'+
        '#{{id}} .spinner-dot>.double-bounce1,'+
        '#{{id}} .spinner-dot>.double-bounce2{width: 100%;height: 100%;border-radius: 50%;background-color: {{background}};opacity: 0.6;position: absolute;top: 0;left: 0;-webkit-animation: {{aniname}} 2.0s infinite ease-in-out;animation:{{aniname}} 2.0s infinite ease-in-out;}'+
        '#{{id}} .spinner-dot>.double-bounce2{-webkit-animation-delay: -1.0s;animation-delay: -1.0s;}'+
        '@-webkit-keyframes {{aniname}}{0%, 100%{-webkit-transform: scale(0.0)}50%{-webkit-transform: scale(1.0)}}'+
        '@keyframes {{aniname}}{0%, 100%{transform: scale(0.0);-webkit-transform: scale(0.0);}50%{transform: scale(1.0);-webkit-transform: scale(1.0);}}</style>');
        var str = '<div class="spinner-dot">' +
            '<div class="double-bounce1"></div>' +
            '<div class="double-bounce2"></div>' +
            '</div>';
        return style + str;
    }
    //双正方形旋转 
    cube = function () {
        var style = fnCSS('<style type="text/css">' +
        '#{{id}} .spinner-cube {margin: -37.5px auto;top:50%;width: 75px;height: 75px;position: relative;}' +
        '#{{id}} .spinner-cube>.cube1,' +
        '#{{id}} .spinner-cube>.cube2 {background-color: {{background}};width: 30px;height: 30px;position: absolute;top: 0;left: 0;-webkit-animation: {{aniname}} 1.8s infinite ease-in-out;animation: {{aniname}} 1.8s infinite ease-in-out;}' +
        '#{{id}} .spinner-cube>.cube2 {-webkit-animation-delay: -0.9s;animation-delay: -0.9s;}' +
        '@-webkit-keyframes {{aniname}}{25% { -webkit-transform: translateX(42px) rotate(-90deg) scale(0.5) }50% { -webkit-transform: translateX(42px) translateY(42px) rotate(-180deg) }75% { -webkit-transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5) }100% { -webkit-transform: rotate(-360deg) }}' +
        '@keyframes {{aniname}}{25%{transform: translateX(42px) rotate(-90deg) scale(0.5); -webkit-transform: translateX(42px) rotate(-90deg) scale(0.5);} 50%{transform: translateX(42px) translateY(42px) rotate(-179deg); -webkit-transform: translateX(42px) translateY(42px) rotate(-179deg);} 50.1%{transform: translateX(42px) translateY(42px) rotate(-180deg); -webkit-transform: translateX(42px) translateY(42px) rotate(-180deg);} 75%{transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5); -webkit-transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5);} 100%{transform: rotate(-360deg); -webkit-transform: rotate(-360deg);}}</style>');
        var str = '<div class="spinner-cube">' +
            '<div class="cube1"></div>' +
            '<div class="cube2"></div>' +
            '</div>';
            return style + str;
    }
    //点从左到右 
    dotGo = function () {
        var style = fnCSS('<style type="text/css">' +
        '#{{id}} .spinner-dotGo{ margin: auto; height: 30px; position: absolute; top: 0; left: 0; right: 0; bottom: 0; text-align: center;}' +
        '#{{id}} .spinner-dotGo>div{width:30px;height:30px;background-color:{{background}};border-radius:100%;display:inline-block;-webkit-animation:{{aniname}} 1.4s infinite ease-in-out;animation:{{aniname}} 1.4s infinite ease-in-out;-webkit-animation-fill-mode:both;animation-fill-mode:both}' +
        '#{{id}} .spinner-dotGo .bounce1{-webkit-animation-delay:-.32s;animation-delay:-.32s}' +
        '#{{id}} .spinner-dotGo .bounce2{-webkit-animation-delay:-.16s;animation-delay:-.16s}' +
        '@-webkit-keyframes {{aniname}}{0%, 80%, 100%{-webkit-transform: scale(0.0)} 40%{-webkit-transform: scale(1.0)} } ' +
        '@keyframes {{aniname}}{0%, 80%, 100%{transform: scale(0.0); -webkit-transform: scale(0.0);} 40%{transform: scale(1.0); -webkit-transform: scale(1.0);} }' +
        '</style>');
        var str = '<div class="spinner-dotGo">' +
            '<div class="bounce1"></div>' +
            '<div class="bounce2"></div>' +
            '<div class="bounce3"></div>' +
            '</div>';
        return style + str;
    }
    //一点显示 
    scaleout = function () {
        var style = fnCSS('<style type="text/css">' +
        '#{{id}} .spinner-scaleout{position:absolute;right:0;left:0;margin:-20px auto;top:50%;width:40px;height:40px;border-radius:100%;background-color:{{background}};-webkit-animation: {{aniname}} 1s infinite ease-in-out;animation: {{aniname}} 1s infinite ease-in-out}' +
        '@-webkit-keyframes  {{aniname}}{0%{-webkit-transform: scale(0.0)}100%{-webkit-transform: scale(1.0);opacity: 0;}}' +
        '@keyframes  {{aniname}}{0%{transform: scale(0.0);-webkit-transform: scale(0.0);} 100%{transform: scale(1.0);-webkit-transform: scale(1.0);opacity: 0;}}' +
        '</style>');
        var str = '<div class="spinner-scaleout"></div>';
        return style + str;
    }
    //旋转加载 
    pointloading = function () {
        var style = fnCSS('<style type="text/css">' +
        '#{{id}} .spinner-pointloading{margin:-30px auto;top:50%;width:60px;height:60px;position:relative}' +
        '#{{id}} .spinner-pointloading .container1>div,' +
        '#{{id}} .spinner-pointloading .container2>div,' +
        '#{{id}} .spinner-pointloading .container3>div{width:15px;height:15px;background-color:{{background}};border-radius:100%;position:absolute;-webkit-animation:{{aniname}} 1.2s infinite ease-in-out;animation:{{aniname}} 1.2s infinite ease-in-out;-webkit-animation-fill-mode:both;animation-fill-mode:both} ' +
        '#{{id}} .spinner-pointloading .spinner-container {position: absolute;width: 100%;height: 100%;}' +
        '#{{id}} .spinner-pointloading .container2 {-webkit-transform: rotateZ(45deg);transform: rotateZ(45deg);}' +
        '#{{id}} .spinner-pointloading .container3 {-webkit-transform: rotateZ(90deg);transform: rotateZ(90deg);}' +
        '#{{id}} .spinner-pointloading .circle1 { top: 0; left: 0; }' +
        '#{{id}} .spinner-pointloading .circle2 { top: 0; right: 0; }' +
        '#{{id}} .spinner-pointloading .circle3 { right: 0; bottom: 0; }' +
        '#{{id}} .spinner-pointloading .circle4 { left: 0; bottom: 0; }   ' +
        '#{{id}} .spinner-pointloading .container2 .circle1{-webkit-animation-delay:-1.1s;animation-delay:-1.1s}' +
        '#{{id}} .spinner-pointloading .container3 .circle1{-webkit-animation-delay:-1s;animation-delay:-1s}' +
        '#{{id}} .spinner-pointloading .container1 .circle2{-webkit-animation-delay:-.9s;animation-delay:-.9s}' +
        '#{{id}} .spinner-pointloading .container2 .circle2{-webkit-animation-delay:-.8s;animation-delay:-.8s}' +
        '#{{id}} .spinner-pointloading .container3 .circle2{-webkit-animation-delay:-.7s;animation-delay:-.7s}' +
        '#{{id}} .spinner-pointloading .container1 .circle3{-webkit-animation-delay:-.6s;animation-delay:-.6s}' +
        '#{{id}} .spinner-pointloading .container2 .circle3{-webkit-animation-delay:-.5s;animation-delay:-.5s}' +
        '#{{id}} .spinner-pointloading .container3 .circle3{-webkit-animation-delay:-.4s;animation-delay:-.4s}' +
        '#{{id}} .spinner-pointloading .container1 .circle4{-webkit-animation-delay:-.3s;animation-delay:-.3s}' +
        '#{{id}} .spinner-pointloading .container2 .circle4{-webkit-animation-delay:-.2s;animation-delay:-.2s}' +
        '#{{id}} .spinner-pointloading .container3 .circle4{-webkit-animation-delay:-.1s;animation-delay:-.1s}' +
        '@-webkit-keyframes {{aniname}}{0%, 80%, 100%{-webkit-transform: scale(0.0)}40%{-webkit-transform: scale(1.0)}}' +
        '@keyframes {{aniname}}{0%, 80%, 100%{transform: scale(0.0); -webkit-transform: scale(0.0);} 40%{transform: scale(1.0); -webkit-transform: scale(1.0);}}' +
        '</style>');
        var str = '<div class="spinner-pointloading">' +
            '<div class="spinner-container container1">' +
            '<div class="circle1"></div>' +
            '<div class="circle2"></div>' +
            '<div class="circle3"></div>' +
            '<div class="circle4"></div>' +
            '</div>' +
            '<div class="spinner-container container2">' +
            '<div class="circle1"></div>' +
            '<div class="circle2"></div>' +
            '<div class="circle3"></div>' +
            '<div class="circle4"></div>' +
            '</div>' +
            '<div class="spinner-container container3">' +
            '<div class="circle1"></div>' +
            '<div class="circle2"></div>' +
            '<div class="circle3"></div>' +
            '<div class="circle4"></div>' +
            '</div>' +
            '</div>';
        return style + str;
    }
    return {
        open : function(mode,o){
            if($("#" + i).length == 0){
                var append = $(p);
                append.prepend(fnFormatModel(
                    main,
                    {
                        id : i
                    }
                ));
            }else{
                $("#" + i).css("display","block");
            }
            if(o != undefined){
                for(var c in o){
                    config[c] = o[c];
                }
            }
            if(mode == undefined || mode == ""){
                mode = "dotGo";
            }
            $("#" + i).html(eval(mode));//rect square dot cube dotGo scaleout pointloading
        },
        hidden : function(o){
            $("#" + i).css("display","none");
        },
        remove : function(o){
            $("#" + i).remove();
        }
    }
})(window, "body", "LoadAni");




/**操作步骤
	window.onload = function () {
        //简洁版
        load_ani.open();
        //详细版
		load_ani.open("rect",//rect square dot cube dotGo scaleout pointloading
		{
            background:"#000"//可选，背景颜色
            aniname : "ani"//可选，动画名
        })
        //隐藏不删除
        load_ani.hidden()
        //删除
        load_ani.remove()
	}
 */