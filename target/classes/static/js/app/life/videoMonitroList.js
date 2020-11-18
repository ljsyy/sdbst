//页面加载完后
window.onload=function () {
    loadList();
}
//加载视频监控列表
function loadList() {
    //获取视频列表标签对象
    var videoList=document.getElementById("video-list");

    $.ajax({
        url:basePath()+"/app/videoMonitor/list",
        type:"get",
        data:{},
        dataType:"json",
        success:function(result){
            console.log(result);
            if(result.code==200){
                //清空子元素
                videoList.innerHTML="";
                var str="";
                //遍历集合
                for(var i=4;i<result.data.length;i++){
                    console.log(result.data[i]);
                    str+="<li class=\"mui-table-view-cell mui-media\">\n" +
                            " <a class=\"mui-navigate-right\" href=\""+basePath()+"/app/videoMonitor/playPage?type=live&deviceId="+result.data[i].id+"\">"+
                                "<img src=\""+basePath()+"/img/life/vm-icon.jpg\" class=\"mui-media-object mui-pull-left\">"+
                                "<div>"+result.data[i].name+"</div>"+
                            "</a>\n" +
                         "</li>"
                }
                videoList.innerHTML=str;
            }else {
                alert(result.msg)
            }
        }
    });
}