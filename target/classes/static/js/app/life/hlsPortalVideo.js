/**
 * HLS的控制对象
 * 包含 播放， 查询录像列表， 更新时间等
 */
(function(global){
	var hlsVideoCtrl = {
		hlsplayrecordOptions : {"autoplay": true},
        getPlayUrl :  basePath()+"/app/videoMonitor/playUrl",
        getPlaylisURL : basePath()+"/app/videoMonitor/playList",
        tmpPlaylistArr : [],
        deviceId : "",
	    playctrl : function(deviceId, recordType, targetId){
			hlsVideoCtrl.videoId = "hlsplayrecord-video";
            hlsVideoCtrl.recordType = recordType;
            hlsVideoCtrl.targetId = targetId;
            hlsVideoCtrl.deviceId = deviceId;
            // 初始化实时直播或者录像点播的页面
            pageAction.initPage(recordType, targetId);
            hlsVideoCtrl.camInitOk = false;
            hlsVideoCtrl.tmpPlaylistArr = [];
            hlsVideoCtrl.quality = "original";
            hlsVideoCtrl.RealBeginTime = null;

            // 先释放资源
            hlsVideoCtrl.releaseM3u8(false);
            hlsVideoCtrl.beginIndex = 1;
            // 清楚获取列表的定时任务
            if (hlsVideoCtrl.getPlayListfunc) {
                clearInterval(hlsVideoCtrl.getPlayListfunc);
            }
            if ( recordType == "live" ) {
                // 如果是实时直播，直接开始播放
                hlsVideoCtrl.recordPlay(new Date().getTime(), "", "");
            } else {
                hlsVideoCtrl.messageid = layer.load(1, {
                    shade: [0.1,'#000'], //0.1透明度的白色背景
                    content : "玩命加载中， 请稍后..."
                });
                // 如果是录像点播
                // 初始化时间轴， 等待用户点播
                camhourneedle = {
                    hour : (new Date()).getHours(),
                    half : false
                }; // 当前的时间
                camtimestamp = parseInt((new Date()).getTime() / 1000) + 3600 * 8;
                camCurrentDay = 0; // 向前推的天数
                camCurrentTS = (new Date()).getTime(); // 当前时间坐标原点
                camPlayUnitArr = []; // cam-play-unit 位置数组
                timer = null;
                timemode = 24;

                hlsVideoCtrl.getPlayList();   /* 获取视频录像记录列表 */
                if( "pu_vod" == recordType ){
                    //前端录像3秒更新一次
                    hlsVideoCtrl.getPlayListfunc = setInterval(function(){hlsVideoCtrl.getPlayList(); }, 3 * 1000);
                }else if( "cv_vod" == recordType ){
                    //中心录像300秒更新一次
                    hlsVideoCtrl.getPlayListfunc = setInterval(function(){hlsVideoCtrl.getPlayList(); }, 300 * 1000);
                }
            }
            if ( hlsVideoCtrl.isIE() ) {
                if ( hlsVideoCtrl.ieRefresh ) {
                    clearInterval(hlsVideoCtrl.ieRefresh);
                }
                // 对于IE的未响应， 建议20秒刷新一下视频
                hlsVideoCtrl.ieRefresh = setInterval(function(){
                    if (hlsVideoCtrl.hlsplayUrl && hlsVideoCtrl.hlsplayUrl.PlayUrl) {
                        // 销毁videojs，重新初始化
                        hlsVideoCtrl.videoReload(hlsVideoCtrl.hlsplayUrl.PlayUrl);
                    }
                }, 10 * 60 * 1000);
            }
		},
		getPlayList :function(){
            // 发送请求获取回放视频列表
            var deviceId = $("#deviceId").val();
            // 发送请求获取回放视频列表

            $.ajax({
                url : hlsVideoCtrl.getPlaylisURL,
                data :{
                    type : hlsVideoCtrl.recordType,
                    deviceId : deviceId,
                    beginIndex:hlsVideoCtrl.beginIndex
                },
                dataType : 'json',
                success : function( data ){
                    if ( data ) {
                        // console.log(data);
                        var playlistArr = hlsVideoCtrl.tmpPlaylistArr;
                        if ( data.data && data.data.playList ) {
                            var playlist = data.data.playList;
                            for ( var i = 0; i < playlist.length; i++ ) {
                                var rtPlayObj = playlist[i];
                                // 计算到秒
                                var beginTimeSec = parseInt(parseInt(rtPlayObj.beginDate) / 1000);
                                var endTimeSec = parseInt(parseInt(rtPlayObj.endDate) / 1000);
                                var playObj = [beginTimeSec, endTimeSec, 0, rtPlayObj.event, rtPlayObj.endDate, rtPlayObj.beginDate, rtPlayObj.contentId];
                                playlistArr.push(playObj);
                            }
                        }
                        for (var i = 0; i < playlistArr.length - 1; i++) {
                            for (var j = 0; j < playlistArr.length - 1 -i; j++) {
                                var elementi = playlistArr[j];
                                var nexte = playlistArr[j + 1];
                                if ( elementi[0] > nexte[0]) {
                                    var temp = playlistArr[j];
                                    playlistArr[j] = playlistArr[j + 1];
                                    playlistArr[j + 1] = temp;
                                }
                            }
                        }
                        if(data.data.endIndex){
                            hlsVideoCtrl.beginIndex = parseInt(data.data.endIndex) + 1;
//                            	console.log("********hlsVideoCtrl.beginIndex="+hlsVideoCtrl.beginIndex);
                            if( data.data.totalNum ){
                                if( parseInt(data.data.endIndex) >= parseInt(data.data.totalNum) ) {
                                    //停止查询
                                    clearInterval(hlsVideoCtrl.getPlayListfunc);
                                }
                            }
                        }

                        hlsVideoCtrl.tmpPlaylistArr = playlistArr;
                        global.playlist = { "results" : playlistArr};
                    }
                    if(!hlsVideoCtrl.camInitOk){
                        // 初始化时间轴
                        camInit();
                        hlsVideoCtrl.camInitOk = true;
                    }else {
                        drawPlayList();
                    }
                    if ( hlsVideoCtrl.messageid) {
                        layer.close(hlsVideoCtrl.messageid);
                    }
                }
            });
	    },
		initplayEvent : function(){
            // 监听开始时间
            hlsVideoCtrl.hlsplayrecordPlayer.on('playing', function(){
                if ( hlsVideoCtrl.recordType != "live" && hlsVideoCtrl.hlsplayUrl &&
                    hlsVideoCtrl.hlsplayUrl.ControlUrl && !hlsVideoCtrl.RealBeginTime) {
                    $.ajax({
                        url : hlsVideoCtrl.hlsplayUrl.ControlUrl + "&Action=GetRealBeginTime",
                        dataType : "json",
                        success : function(data){
                            if( data && data.RealBeginTime ){
                                var beginDate = new Date();
                                var beginTimeStr = data.RealBeginTime;
                                beginDate.setYear(parseInt(beginTimeStr.substring(0,4),10));
                                beginDate.setMonth(parseInt(beginTimeStr.substring(5,7)-1,10));
                                beginDate.setDate(parseInt(beginTimeStr.substring(8,10),10));
                                beginDate.setHours(parseInt(beginTimeStr.substring(11,13),10));
                                beginDate.setMinutes(parseInt(beginTimeStr.substring(14,16),10));
                                beginDate.setSeconds(parseInt(beginTimeStr.substring(17,19),10));
                                hlsVideoCtrl.RealBeginTime = beginDate.getTime();  // 获取的是毫秒
                            } else {
                                hlsVideoCtrl.RealBeginTime = hlsVideoCtrl.currentPlayStartTime;
                            }
                        }
                    });
                }
            });
            // 监听videojs时间更新， 更新进度条
		    hlsVideoCtrl.hlsplayrecordPlayer.on('timeupdate', function(){
                if ( hlsVideoCtrl.recordType != "live" && hlsVideoCtrl.RealBeginTime ) {
                    var playlistArr = playlist.results || [];
                    var playtime = parseInt(hlsVideoCtrl.hlsplayrecordPlayer.currentTime() * 1000);
                    if ( playlistArr.length > 0) {
                        var minEntimeIndex = -1;
                        var minEntime = Number.MAX_VALUE;
                        var isInPlayList = true;
                        for ( var i = 0; i < playlistArr.length ; i++) {
                            var playVideoEndTime = playlistArr[i][4];
                            var playVideoStartTime = playlistArr[i][5];
                            // 如果当前时间已经超过某个
                            var currentPlayTimeWithBt = hlsVideoCtrl.RealBeginTime + playtime;
                            /*if ( currentPlayTimeWithBt > playVideoEndTime && hlsVideoCtrl.RealBeginTime < playVideoEndTime) {
                                if ( playVideoEndTime < minEntime) {
                                    minEntimeIndex = i;
                                }
                            }*/
                            // 说明再当前i这个时间轴上
                            if ( currentPlayTimeWithBt >= playVideoStartTime && currentPlayTimeWithBt <= playVideoEndTime) {
                                isInPlayList = true;
                                break;
                            }

                            // 如果超出上一个时间段的结束时间并小于当前的开始时间， 说明当前时间不再时间轴上
                            if ( currentPlayTimeWithBt < playVideoStartTime && i > 0 && currentPlayTimeWithBt > playlistArr[ i -1 ][4]) {
                                isInPlayList = false;
                                minEntimeIndex = i;
                                break;
                            }
                        }
                        if ( !isInPlayList ) {
                            hlsVideoCtrl.RealBeginTime = Number(playlistArr[minEntimeIndex][5]);
                        }
                    }
                    var currentTime = parseInt((hlsVideoCtrl.RealBeginTime + playtime) / 1000 );
                    var _offset_x = parseInt($("#clipspan").css("left")) - 5;

                    var date = new Date(camCurrentTS);
                    date.setHours(0, 0, 0, 0);
                    var timestamp_start = date.getTime();
                    var _time_fix = camhourneedle.half ? 1800 : 0;
                    // 计算_offset_x
                    if (timemode == 1) {
                        // 按照_offset_x计算时间逆推出来
                        _offset_x = parseInt(((currentTime - _time_fix) * 1000 - timestamp_start  - camhourneedle.hour * 3600000) * 953 / (60 * 60 * 1000 ) ) - 6;
                    } else if ( timemode == 24) {
                        _offset_x = parseInt(((currentTime - _time_fix) * 1000 - timestamp_start) *953 / ( 24 * 60 * 60 * 1000));
                    }
                    // 更新移动的时间指针
                    if ( _offset_x >= 952 ) {
                        // 跳转到下一个时间轴
                        $(".scroll_later").trigger("click");
                    } else {
                        // 更新移动的时间指针
                        $("#camClipNeedle").css("left", (_offset_x - 7) + "px");
                    }
                }
            });
		},
        qualitySelect : function(quality){
            if (quality && hlsVideoCtrl.hlsplayUrl && hlsVideoCtrl.hlsplayUrl.PlayUrl) {
                if ( hlsVideoCtrl.recordType == "live" ) {
                    hlsVideoCtrl.recordPlay(new Date().getTime(), quality, "");
                } else {
                    var _offset_x = $("#camClipNeedle").offset().left - $("#timeline").offset().left;
                    if (_offset_x <= $("#timespan").width()) {
                        var timeObj = getTimestamp(_offset_x);
                        var timespot = new Date((timeObj.st - 8 * 3600) * 1000);
                        hlsVideoCtrl.recordPlay(timespot.getTime(), quality, "");
                    }
                }
            } else {
                layer.alert("系统异常");
            }
        },
        /**
         * HLS重新播放
         * @param startTime
         * @param quality
         * @param loadingMsg
         */
	    recordPlay : function(startTime, quality, loadingMsg){
            // 把当前点击的事件作为开始时间
            hlsVideoCtrl.RealBeginTime = null;
            hlsVideoCtrl.currentPlayStartTime = startTime;
            // HLS关闭m3u8请求， 并重新加载
            hlsVideoCtrl.m3u8stopAndReload(true, startTime, loadingMsg, quality);
        },
        /**
         * MTG网关关闭当前连接
         * ifneedreload : 会reload
         */
        m3u8stopAndReload : function(ifneedreload, startTime, loadingMsg, quality){
            var message = loadingMsg || "正在重新加载视频请稍候……"
            hlsVideoCtrl.messageid = layer.load(1, {
                shade: [0.1,'#000'], //0.1透明度的白色背景
                content : message
            });
            if ( hlsVideoCtrl.hlsplayUrl && hlsVideoCtrl.hlsplayUrl.ControlUrl ) {
                hlsVideoCtrl.releaseM3u8(function(){
                    if ( ifneedreload ) {
                        hlsVideoCtrl.getRecordUrl(startTime, loadingMsg, quality);
                    }
                });
            } else {
                if ( ifneedreload ) {
                    hlsVideoCtrl.getRecordUrl(startTime, loadingMsg, quality);
                }
            }
        },
        /**
         * 释放m3u8链接， 并清空videojs对象及页面元素
         * @param callback
         */
        releaseM3u8 : function(callback){
            if ( hlsVideoCtrl.hlsplayUrl && hlsVideoCtrl.hlsplayUrl.ControlUrl ) {
                $.ajax({
                    url : hlsVideoCtrl.hlsplayUrl.ControlUrl + "&Action=Stop",
                    dataType : "json",
                    success : function(data){
                    },
                    complete : function(){
                        hlsVideoCtrl.hlsplayUrl = {};
                        if ( typeof (callback) == 'function' ) {
                            callback();
                        }
                    }
                });
            }
            try{
                hlsVideoCtrl.hlsplayrecordPlayer.dispose();
            }catch(e){
                $(".hlsplayrecord-container").empty();
            }
        },
        /**
         * 获取PlayURL
         * startTime, 录像开始时间
         * loadingMsg， 获取URL期间Loading界面的消息体
         */
        getRecordUrl : function (startTime, loadingMsg, quality){
            var deviceId = hlsVideoCtrl.deviceId;
            var quality = quality || hlsVideoCtrl.quality;
            hlsVideoCtrl.hlsplayUrl = {};   // 此处做初始化， 为了避免多次调用时出现BUG
            var params = {
                videoQuality: quality,
                deviceId : deviceId,
                type : hlsVideoCtrl.recordType
            };
            if ( hlsVideoCtrl.recordType != "live" ) {
                params.beginTime = startTime;
            }
            $.ajax({
                url : hlsVideoCtrl.getPlayUrl,
                data : params,
                dataType : "json",
                success: function(data){
                    if ( data ) {
                        if ( data.data && data.data.ResponseStatus ) {
                            var responseStatus = data.data.ResponseStatus;
                            if ( responseStatus.StatusCode == "115003" ) {
                                // cancle loading
                                if ( hlsVideoCtrl.messageid ) {
                                    layer.close(hlsVideoCtrl.messageid);
                                }
                                layer.alert("没有合适MTG使用", "提醒");
                            } else if ( responseStatus.StatusCode == "990106" ) {
                                // cancle loading
                                if ( hlsVideoCtrl.messageid ) {
                                    layer.close(hlsVideoCtrl.messageid);
                                }
                                layer.alert("请求视频参数的值不合法", "提醒");
                            } else if ( responseStatus.StatusCode == "990202" ) {
                                // cancle loading
                                if ( hlsVideoCtrl.messageid ) {
                                    layer.close(hlsVideoCtrl.messageid);
                                }
                                layer.alert("无此操作权限", "提醒");
                            } else if ( data.data.PlayUrl) {
                                hlsVideoCtrl.videoReload(data.data.PlayUrl);
                            }
                        }
                        hlsVideoCtrl.hlsplayUrl = data.data || {};
                    }
                },
                complete: function(xhr, ts){
                    // 当前请求结束都会执行该方法
                    // 取消loading
                    if ( hlsVideoCtrl.messageid ) {
                        layer.close(hlsVideoCtrl.messageid);
                    }
                }
            });
        },
        /**
         * 重新绘制Videojs页面元素并重新初始化Videojs
         * @param srcUrl
         */
		videoReload : function (srcUrl){
            if ( srcUrl ) {
				try{
                    hlsVideoCtrl.hlsplayrecordPlayer.dispose();
                }catch(e){
                    $(".hlsplayrecord-container").empty();
                }
                $(".hlsplayrecord-container").append("<video id='" + hlsVideoCtrl.videoId + "' width='100%' height='100%' class='video-js vjs-default-skin' controls>" +
                            "<source src='" + srcUrl + "' type='application/x-mpegURL'> </source>" +
                         "</video>");
                var options = hlsVideoCtrl.hlsplayrecordOptions;
                if (hlsVideoCtrl.isIE()) {
                    // options.techOrder = ['flash'];
                }
		        hlsVideoCtrl.hlsplayrecordPlayer = videojs(hlsVideoCtrl.videoId, options, function onPlayerReady() {
                    this.play();
                    if ( hlsVideoCtrl.recordType != "live" ) {
                        hlsVideoCtrl.initplayEvent();
                    }
                });
				// hlsVideoCtrl.hlsplayrecordPlayer.errors();
            }
        },
        isIE : function () {
            if (!!global.ActiveXObject || "ActiveXObject" in global) {
                return true;
            } else {
                return false;
            }
        }
	};
	var pageAction = {
	    initPage : function(type, targetElementId){
            pageAction.targetId = targetElementId;
            if ( type == "live" ) {
                pageAction.initHlsLivePage();
            } else if ( type == "cv_vod" || type == "pu_vod" ) {
                pageAction.initHlsRecordPage();
            }
        },
        /**
         * 初始化实时直播页面
         */
        initHlsLivePage : function(){
            var target = $("#" + pageAction.targetId);
            target.empty(); // 先清空页面元素
            var html =
                "<div class=\"hlsplayrecord-container\">\n" +
                "    </div>";
            target.append(html);
        },
        /**
         * 初始化录像点播页面
         */
        initHlsRecordPage : function(){
            var target = $("#" + pageAction.targetId);
            target.empty(); // 先清空页面元素
            var html =
                "<div class='hlsVideoBody'>\n" +
                "<div class=\"hlsplayrecord-container\" style=\"background-color:#000;\">\n" +
                "</div>\n" +
                "<div id=\"hlsplayrecord-cam\">\n" +
                "    <div class=\"cam-container\">\n" +
                "        <div class=\"timeline\" id=\"timeline\">\n" +
                "            <div id=\"timespan\" title=\"18:14\">\n" +
                "               <div id=\"camplaylistcon\"  class=\"cam-play-unit\"></div>\n" +
                "                  <img id=\"camClipNeedle\" src=\"" + ctx + "/static/img/cam-timeneedle-red.png\" />\n" +
                "               </div>\n" +
                "            </div>\n" +
                "        <div id='infoTxt'></div>\n" +
                "        <div id=\"selectTimespan\" style=\"\">\n" +
                "            <div class=\"time_select_div\">\n" +
                "                 <button id=\"cam_time_1\" class=\"btn btn-info\" style=\"\">1小时</button>\n" +
                "                 <button id=\"cam_time_24\" class=\"btn btn-info\" style=\"\">24小时</button>\n" +
                "           </div>\n" +
                "        </div>\n" +
                "  </div>\n" +
                "</div></div>";
            target.append(html);
        },
    };
	global.hlsVideoCtrl = hlsVideoCtrl;
})(this);