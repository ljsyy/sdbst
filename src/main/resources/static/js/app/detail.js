//  shira  二○一七年八月七日  
//  second develope


/*$(function(){
	var wait=false;		//下拉加载等待
	$(window).scroll(function() {//下拉到底,加载数据
		scroll(wait,url,pageIndex);
	});
});*/

//从接口获取数据

function getWord(word) {
    var width = $(window).width();
    var size = width * 2 / 18 - 6;

    if (word.length > size) {
        word = word.substring(0, size) + "...";
    } else if (word.length < 10) {
        word = word + "&nbsp;&nbsp;";
    }
    return word;
}

function ioopen() {	//查询窗口  输入框  显示/隐藏
    $("#search").toggle(300);
}

//加载数据 jsonp
function loadJsonp(url, callName) {
    //mui.toast(url);
    $.ajax({
        type: "get",
        async: true,
        url: url,
        dataType: "jsonp",
        jsonp: "callback",//服务端用于接收callback调用的function名的参数
        jsonpCallback: callName,
        success: function (data) {
            mui.hideLoading(function () {
                if (data != null && data.success) {
                    showData(data.obj, true);
                } else {
                    mui.toast("没有加载到数据!");
                }
            });
        },
        error: function () {
            mui.hideLoading(function () {
                mui.toast("出错啦...");
            });
        }
    });
}

function loadJsonp2(url, callName, ct) {
    /*$.ajax({
        type : "get",
        async : true,
        url : url,
        dataType : "jsonp",
        jsonp: "callback",	//服务端用于接收callback调用的function名的参数
        jsonpCallback: callName,
        success : function(data){
            if(data!=null && data.success){
                if(typeof(data.obj.content) != "undefined" && data.obj.content=="内容加载失败！"){
                    var id=url.substring(url.indexOf('*id=')+4, url.indexOf('*jsonpcallback='));
                    var uri="http://sdbst.shunde.gov.cn:8082/SDBST/news.web?newsDetail*id="+id+"*mobileLogin=true";

                    $.ajax({
                        url:ct+"/app/menu/getjson?url="+uri,
                        type:"post",
                        dataType:"json",
                        success:function(result){
                            showData(result.data,true);
                        }
                    });
                }else{
                    showData(data.obj,true);
                }
            }else{
                mui.toast("没有加载到数据!");
            }
        },
        error:function(){
            mui.toast("出错啦...");
        }
    });*/

    var id = url.substring(url.indexOf('*id=') + 4, url.indexOf('*jsonpcallback='));
    var uri = "http://sdbst.shunde.gov.cn:8082/SDBST/news.web?newsDetail*id=" + id + "*mobileLogin=true";

    $.ajax({
        url: ct + "/app/menu/getjson?url=" + uri,
        type: "post",
        dataType: "json",
        success: function (result) {
            if (typeof(result.data.content) != "undefined" && result.data.content == "加载中失败！") {
                window.location.href = "http://www.sdggzy.cn/data/main.php?id=" + id.replace("_", "-");
            } else {
                showData(result.data, true);
            }

        }
    });

}

function loadJsonp3(id, titleName, ct, noHead) {

    // var id=url.substring(url.indexOf('*id=')+4, url.indexOf('*jsonpcallback='));
    // var uri="http://sdbst2.shunde.gov.cn:8090/sdbst/data/newsDetail?id="+id+"*mobileLogin=true";

    $.ajax({
        // url:"http://sdbst2.shunde.gov.cn:8090/sdbst/data/newsDetail?id="+id+"&mobileLogin=true",
        url: ct + "/data/newsDetail?id=" + id + "&mobileLogin=true",
        type: "post",
        dataType: "json",
        success: function (result) {
            mui.hideLoading(function () {
                console.log(result);
                var data = result.obj;
                if (typeof (data.content) != "undefined" && data.content == "加载中失败！") {
                    top.location = "http://www.sdggzy.cn/data/main.php?id=" + id.replace("_", "-") + "&title=" + titleName + "&adapt=1&noHead=" + noHead;
                } else {
                    showData(data, true);
                }
            });
        }
    });

}

//加载数据 json     路径 要 处理               get 请求
function loadJson(url, pageIndex, flag) {
    url = url.replace(/&/g, '%26');
    $.ajax({
        url: url + pageIndex,
        dataType: "json",
        success: function (data) {
            if (data != null && data != "" && data.success) {
                showData(data.obj, flag);
            } else {
                mui.toast("没有加载到数据！");
            }
        },
        error: function () {
            mui.toast("出错啦...");
        }

    });
}

//加载数据 json    路径 不用 处理          get 请求
function getJson(url, pageIndex, flag) {
    $.ajax({
        url: url + pageIndex,
        dataType: "json",
        success: function (data) {
            if (data != null && data != "" && data.success) {
                showData(data.obj, flag);
            } else {
                mui.toast("没有加载到数据！");
            }
        },
        error: function () {
            mui.toast("出错啦...");
        }

    });
}

//加载数据 json    路径不用处理           post 请求
function loadPostJson(url, pageIndex, flag) {
    $.ajax({
        url: url + pageIndex,
        dataType: "json",
        success: function (data) {
            if (data != null && data != "" && data.success) {
                showData(data.obj, flag);
            } else {
                mui.toast("没有加载到数据！");
            }
        },
        error: function () {
            mui.toast("出错啦...");
        }

    });
}

//加载数据  XML
function loadXml(url, pageIndex, flag) {
    // var url="http://202.104.25.196/RXWSSTWeb/xzsp.web?itemThemeType&areaId=440606&typeId=1&keyWord=";
    $.ajax({
        url: url + pageIndex,
        dataType: "xml",
        success: function (data) {
            mui.toast("Success");
            mui.toast($(data).find("Response").find("Head").find("message").text());
            return data;
        },
        error: function () {
            mui.toast("出错啦...");
        }

    });
}

//处理展示数据
function showData(data, flag) {
    if (data != null && data != "") {
        if (flag) {	//判断是否初次加载数据
            $("#show").children().remove();	//重新加载前清空 <div id="list">
        }
        getLabel(data);
        wait = false;
    } else {
        mui.toast("没有加载到数据！");
    }
}

//下拉到底   加载数据      请求的路径不用处理      get 请求
function getScroll(wait, url, pageIndex) {
    if (wait) {
        return false;
    }
    if ($(window).scrollTop() + 2 >= $(document).height() - $(window).height()) {
        wait = true;	//下拉触发等待
        pageIndex = pageIndex + 1;
        getJson(url, pageIndex, false);
    }
}

//下拉到底   加载数据     请求的路径要处理      get 请求
function scroll(wait, url, pageIndex) {
    if (wait) {
        return false;
    }
    if ($(window).scrollTop() + 2 >= $(document).height() - $(window).height()) {
        wait = true;	//下拉触发等待
        pageIndex = pageIndex + 1;
        loadJson(url, pageIndex, false);
    }
}

//下拉到底   加载数据     请求的路径不用处理      post 请求
function postScroll(wait, url, pageIndex) {
    if (wait) {
        return false;
    }
    if ($(window).scrollTop() + 2 >= $(document).height() - $(window).height()) {
        wait = true;	//下拉触发等待
        pageIndex = pageIndex + 1;
        loadPostJson(url, pageIndex, false);
    }
}

//获得根目录
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
}