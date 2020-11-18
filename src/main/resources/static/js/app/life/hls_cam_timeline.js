/*
cam-timeline
 */

var camTimelineTpl = '<div class="timeline" id="timeline" style="background:url(./img/cam-timeaxis.png) no-repeat 0 37px;"></div>';
var camhourneedle = {
    hour : (new Date()).getHours(),
    half : false
}; // 当前的时间
var camtimestamp = parseInt((new Date()).getTime() / 1000) + 3600 * 8;
var camCurrentDay = 0; // 向前推的天数
var camCurrentTS = (new Date()).getTime(); // 当前时间坐标原点
var camPlayUnitArr = []; // cam-play-unit 位置数组
var timer = null;
// console.log(camtimestamp+' '+(new Date()).getHours()+':'+(new
// Date()).getMinutes());
var timemode = 24;

var arrows = function() {
    $("#timeline")
            .append(
                    '<div class="scroll_earlier" style="position:absolute;left:-30px;top:15px;width:36px;height:46px;background:url(./static/img/arrow-left.png) no-repeat;cursor:pointer;-moz-user-select:none;"></div>')
            .append(
                    '<div class="scroll_later" style="display: block;position:absolute;right:-25px;top:15px;width:36px;height:46px;background:url(./static/img/arrow-right.png) no-repeat;cursor:pointer;-moz-user-select:none;"></div>');

    $(".scroll_earlier").click( function(e) {
        if ((camCurrentDay >= 6 && timemode == 24)|| (((new Date((new Date()).getTime() - 7 * 86400000)).setHours(23, 59, 59, 999) > camCurrentTS) && timemode == 1)) {
            return false;
        }
        if ((camhourneedle.hour <= 0 && !camhourneedle.half)|| timemode == 24) {
            camCurrentDay++;
            camhourneedle.hour = 24;
            camhourneedle.hour--;
        
            if (timemode == 24) {
                camCurrentTS -= 86400000;
            } else {
                camCurrentTS -= 1800000;
                camhourneedle.half = !camhourneedle.half;
            }
            generateTimeline(camCurrentDay); // 插入播放列表块
            playlistmode(playlist.results, 24); // 插入时间轴
            $(".camvodSelectDay .btn").eq(6 - camCurrentDay).css("background-color", "#faa732").siblings().css("background-color","#1369C0");
            return false;
        } else {
            if (!camhourneedle.half) {
                camhourneedle.hour--;
            } else {
                // nothing to do
            }
            camCurrentTS -= 1800000;
            camhourneedle.half = !camhourneedle.half;
            generateTimeline(); // 插入播放列表块
            playlistByHour(camhourneedle.hour);
            e.stopPropagation();
            // 剪辑状态下跳过无内容片段
            if ($("#camEdit").html() == "提交" && camPlayUnitArr.length == 0) {
                $(".scroll_earlier").trigger("click");
                return false;
            }
            return false;
        }
    }).bind('selectstart', function() {
        return false;
    });

    $(".scroll_later").click(
            function(e) {
                // console.log(camCurrentDay);
                if (camCurrentDay <= 0 && timemode == 24) { //
                    return false;
                } else if (camCurrentDay == 0
                        && camhourneedle.hour > (new Date()).getHours() - 1) {
                    return false;
                } else if ($("#timespan").width() < 953) {
                    return false;
                }
                if ((camhourneedle.hour >= 23 && camhourneedle.half)
                        || timemode == 24) {
                    camCurrentDay--;
                    // console.log(camCurrentDay);
                    camhourneedle.hour = 0;
                    if (timemode == 24) {
                        camCurrentTS += 86400000;
                    } else {
                        camCurrentTS += 1800000;
                        camhourneedle.half = !camhourneedle.half;
                    }
                    generateTimeline(camCurrentDay); // 插入播放列表块
                    playlistmode(playlist.results, 24);
                    $(".camvodSelectDay .btn").eq(6 - camCurrentDay).css(
                            "background-color", "#faa732").siblings().css(
                            "background-color", "#1369C0");
                    return false;
                } else {
                    if (!camhourneedle.half) {
                        // nothing to do
                    } else {
                        camhourneedle.hour++;
                    }
                    camhourneedle.half = !camhourneedle.half;
                    camCurrentTS += 1800000;
                    generateTimeline();
                    playlistByHour(camhourneedle.hour);
                    e.stopPropagation();
                    // 剪辑状态下跳过无内容片段
                    if ($("#camEdit").html() == "提交"
                            && camPlayUnitArr.length == 0) {
                        $(".scroll_later").trigger("click");
                        return false;
                    }
                    return false;
                }
            }).bind('selectstart', function() {
        return false;
    });

}
function camInit() {
    camCurrentTS = (new Date()).getTime();
    sharee = 3;
    mousePos = 0;
    // camCurrentTS = 1399701600000;
    camdraging($("#camClipNeedle")[0]);
    camvodSelectDay();
    $("#timeline").click(function(e, mousePos) {
        timelineclick(e, mousePos);
    });
    generateTimeline(camCurrentTS); // 插入播放列表块
    playlistmode(playlist.results, 24);
    // 选择时间范围的事件响应
    $("#cam_time_1").click(function(e) {
        $("#selectTimespan button").css("background-color", "#bbb");
        $(this).css("background-color", "#1369C0");
        timemode = 1;
        var __time = new Date((camtimestamp - 8 * 3600) * 1000);
        if (camhourneedle.half) {
            // nothing to do;
        } else {
            camhourneedle.hour = __time.getHours();
        }
        generateTimeline(); // 插入播放列表块
        playlistByHour(camhourneedle.hour); // 按小时调整播放列表
        // 设置时间指针移动
        /*
        if (typeof (timer) != 'undefined') {
            clearInterval(timer);
        }
        var _timeinterval = timemode == 24 ? 86400 : 3600;
        timer = setInterval(function() {
            if (!timerCam(_timeinterval)) {
                return false;
            }
        }, parseInt(_timeinterval * 1000 / 953));
        */
    });
    $("#cam_time_24").click(function(e) {
        if ($("#camEdit").html() == "提交") {
            alert("在1小时模式下会更方便您选择时间范围");
            return false;
        } else {
            $("#selectTimespan button").css("background-color", "#bbb");
            $(this).css("background-color", "#1369C0");
            timemode = 24;
            camhourneedle.half = false;
            generateTimeline(); // 插入播放列表块
            playlistmode(playlist.results, 24); // 插入时间轴
            // 设置时间指针移动
            /* 时间轴移动由HLS完成
            if (typeof (timer) != 'undefined') {
                clearInterval(timer);
            }
            var _timeinterval = timemode == 24 ? 86400 : 3600;
            timer = setInterval(function() {
                if (!timerCam(_timeinterval)) {
                    return false;
                }
            }, parseInt(_timeinterval * 1000 / 953));
            */
        }
    });
}
/**
 * 时间轴点击事件
 * 
 * @param e
 * @param mousePos
 */
function timelineclick(e, mousePos) {

    if ($(e.target).attr("class") != 'cam-play-unit'
            && typeof (mousePos) == 'undefined') {
        // return false;
    } else if (typeof (sharee) != 'undefined' && sharee == 3) {
        var _offset_x = null;
        if (typeof (mousePos) != 'undefined') {
            _offset_x = mousePos;
        } else {
            _offset_x = e.pageX - $("#timeline").offset().left;
        }
        camtimestamp = getTimestamp(_offset_x).st;
        var __time = new Date((camtimestamp - 8 * 3600) * 1000);
        // id 目前作为唯一的标识符，统一都是放的是filepath的值
        // var id = e.target.attributes['tag'].value;
        // 该taskId属性中2.16存放的是taskId，3.3中存放的是filePath的值
        // var taskId = e.target.attributes['taskId'].value;

        // 开始时间
        // var startTime = e.target.attributes['startTime'].value;
        var clickTime = getDate(__time);

        // 结束时间
        // var endTime = e.target.attributes['endTime'].value;
        /*
         * try { VissCtrlImpl.StopVodFile(); } catch (err) { }
         */
        // alert(startTime+ "_" + endTime);
        // VissCtrlImpl.PlayVodFileEx(cameraId, taskId, startTime, endTime, 0,
        // 0);
        // currentId = id;
        var clickTime2 = new Date(clickTime.replace(/-/g, "/"));
        // var startTime2 = new Date(startTime.replace(/-/g, "/"));
        // var endTime2 = new Date(endTime.replace(/-/g, "/"));
        // var posTime = (clickTime2 - startTime2) / 1000;
        var posTime = (clickTime2) / 1000;
        /*
         * if (posTime < 0) { posTime = (endTime2 - startTime2) / 1000 / 2;
         * posTime += posTime / 2; }
         */
        try {
            // VissCtrlImpl.SetPlayPosVodFileEx(posTime);
            hlsVideoCtrl.recordPlay(__time.getTime());
            $("#camClipNeedle").css("left", (_offset_x - 6) + "px");
            // 以下定时任务暂时注释， 由hls的事件触发来实现
            /*
            if (timemode == 1) {
                if (typeof (timer) != 'undefined') {
                    clearInterval(timer);
                }
                var _timeinterval = timemode == 24 ? 86400 : 3600;
                timer = setInterval(function() {
                    if (!timerCam(_timeinterval)) {
                        return false;
                    }
                }, parseInt(_timeinterval * 1000 / 953));
            } else if (timemode == 24) {
                if (typeof (timer) != 'undefined') {
                    clearInterval(timer);
                }
                var _timeinterval = timemode == 24 ? 86400 : 3600;
                timer = setInterval(function() {
                    if (!timerCam(_timeinterval)) {
                        return false;
                    }
                    // console.log(player.getPosition());
                }, parseInt(_timeinterval * 1000 / 953));
            }
            */
        } catch (err) {
            alert(err);
        }
        $("#camClipNeedle").css("left", (_offset_x - 6) + "px");
    }
}

function timerCam(_timeinterval) {
    if (parseInt($("#camClipNeedle").css("left")) <= 947) {
        $("#camClipNeedle").css("left",
                parseInt($("#camClipNeedle").css("left")) + 1);
    } else if (parseInt($("#camClipNeedle").css("left")) == 948) {
        $(".scroll_later").trigger("click");
        $("#camClipNeedle").css("left", "476px");
    } else {
        return false;
    }
    camtimestamp += parseInt(_timeinterval / 953);
    // console.log(player.getPosition());
    return true;
}
function jiazai() {
    $(".cam-container").append(camTimelineTpl);
    $("#timeline").css({
        "width" : "960px",
        "height" : "80px",
        "margin" : "10px auto 20px"
    });
    // 插入播放列表块
    generateTimeline();
    var play_set = typeof (playlist) == "undefined" ? [] : playlist.results;
    playlistmode(play_set, 24); // 时间轴插入
    $("#timeline")
            .click(
                    function(e, mousePos) {
                        if ($(e.target).attr("class") != 'cam-play-unit'
                                && typeof (mousePos) == 'undefined') {
                            // return false;
                        } else if (typeof (sharee) != 'undefined'
                                && sharee == 3) {
                            if (typeof (mousePos) != 'undefined') {
                                var _offset_x = mousePos;
                            } else {
                                var _offset_x = e.pageX
                                        - $("#timeline").offset().left;
                            }
                            // console.log(mousePos);
                            // console.log(e.pageX+'-'+(parseInt($("#timeline").css("margin-left"))+'-'+parseInt($(".cam-container").css("margin-left"))+1+210));
                            if (playurl
                                    && _offset_x <= parseInt($("#timespan")
                                            .css("width")) && 0 < _offset_x
                                    && _offset_x < 953) { // 处理点击录像，实现点播功能
                                // $("#clipspan").css("left",(_offset_x-6)+"px");
                                $("#camClipNeedle").css("left",
                                        (_offset_x - 6) + "px");
                                camtimestamp = getTimestamp(_offset_x).st;
                                var __time = new Date(
                                        (camtimestamp - 8 * 3600) * 1000);
                                // console.log(camtimestamp+'
                                // '+__time.getHours()+':'+__time.getMinutes());
                                if (camhourneedle.half) {
                                    // nothing to do;
                                } else {
                                    camhourneedle.hour = __time.getHours();
                                }
                                loadVod(playurl, getTimestamp(_offset_x));
                                $("#camShowVod").html("看直播");
                                $(".live").html("录像");
                                $(".camHint").remove();
                            } else {
                                return false;
                            }
                        }
                    });
    // 插入录像天数选择按钮
    if (typeof (sharee) != "undefined" && sharee == 3) {
        // 插入向前向后控制按钮
        $(".cam-container")
                .append(
                        '<div class="scroll_earlier" style="position:absolute;bottom:95px;left:12px;width:36px;height:46px;background:url(./img/video/arrow-left.png) no-repeat;cursor:pointer;-moz-user-select:none;"></div>')
                .append(
                        '<div class="scroll_later" style="display: block;position:absolute;bottom:95px;right:19px;width:36px;height:46px;background:url(./img/video/arrow-right.png) no-repeat;cursor:pointer;-moz-user-select:none;"></div>');
        // 插入录像天数选择按钮
        camvodSelectDay();
    }

    // 选择时间范围的事件响应
    $("#cam_time_1")
            .click(
                    function(e) {
                        $("#selectTimespan button").css("background-color",
                                "#bbb");
                        $(this).css("background-color", "#1369C0");
                        timemode = 1;
                        var __time = new Date((camtimestamp - 8 * 3600) * 1000);
                        if (camhourneedle.half) {
                            // nothing to do;
                        } else {
                            camhourneedle.hour = __time.getHours();
                        }
                        generateTimeline(); // 插入播放列表块
                        playlistByHour(camhourneedle.hour); // 按小时调整播放列表
                        // 设置时间指针移动
                        if (typeof (timer) != 'undefined') {
                            clearInterval(timer);
                        }
                        var _timeinterval = timemode == 24 ? 86400 : 3600;
                        timer = setInterval(
                                function() {
                                    if (parseInt($("#camClipNeedle")
                                            .css("left")) <= 947) {
                                        $("#camClipNeedle").css(
                                                "left",
                                                parseInt($("#camClipNeedle")
                                                        .css("left")) + 1);
                                    } else if (parseInt($("#camClipNeedle")
                                            .css("left")) == 948) {
                                        $(".scroll_later").trigger("click");
                                        $("#camClipNeedle")
                                                .css("left", "476px"); // 把时间轴指针移到中间
                                    } else {
                                        return false;
                                    }
                                    camtimestamp += parseInt(_timeinterval / 953);
                                    // console.log(player.getPosition());
                                }, parseInt(_timeinterval * 1000 / 953));
                    });
    $("#cam_time_24")
            .click(
                    function(e) {
                        if ($("#camEdit").html() == "提交") {
                            alert("在1小时模式下会更方便您选择时间范围");
                            return false;
                        } else {
                            $("#selectTimespan button").css("background-color",
                                    "#bbb");
                            $(this).css("background-color", "#1369C0");
                            timemode = 24;
                            camhourneedle.half = false;
                            generateTimeline(); // 插入播放列表块
                            playlistmode(play_set, 24); // 插入时间轴
                            // 设置时间指针移动
                            if (typeof (timer) != 'undefined') {
                                clearInterval(timer);
                            }
                            var _timeinterval = timemode == 24 ? 86400 : 3600;
                            timer = setInterval(
                                    function() {
                                        if (parseInt($("#camClipNeedle").css(
                                                "left")) <= 947) {
                                            $("#camClipNeedle")
                                                    .css(
                                                            "left",
                                                            parseInt($(
                                                                    "#camClipNeedle")
                                                                    .css("left")) + 1);
                                        } else if (parseInt($("#camClipNeedle")
                                                .css("left")) == 948) {
                                            $(".scroll_later").trigger("click");
                                            $("#camClipNeedle").css("left",
                                                    "476px");
                                        } else {
                                            return false;
                                        }
                                        camtimestamp += parseInt(_timeinterval / 953);
                                        // console.log(player.getPosition());
                                    }, parseInt(_timeinterval * 1000 / 953));
                        }
                    });
    $(".scroll_earlier")
            .click(
                    function(e) {
                        // console.log(camCurrentDay);
                        // console.log((new Date((new Date()).getTime() -
                        // 6*86400000)).setHours(23,59,59,999) > camCurrentTS);
                        if ((camCurrentDay >= 6 && timemode == 24)
                                || (((new Date(
                                        (new Date()).getTime() - 7 * 86400000))
                                        .setHours(23, 59, 59, 999) > camCurrentTS) && timemode == 1)) {
                            return false;
                        }
                        if ((camhourneedle.hour <= 0 && !camhourneedle.half)
                                || timemode == 24) {
                            camCurrentDay++;
                            camhourneedle.hour = 24;
                            camhourneedle.hour--;

                            if (timemode == 24) {
                                camCurrentTS -= 86400000;
                            } else {
                                camCurrentTS -= 1800000;
                                camhourneedle.half = !camhourneedle.half;
                            }
                            generateTimeline(camCurrentDay); // 插入播放列表块
                            playlistmode(play_set, 24); // 插入时间轴
                            $(".camvodSelectDay .btn").eq(6 - camCurrentDay)
                                    .css("background-color", "#faa732")
                                    .siblings().css("background-color",
                                            "#1369C0");
                            return false;
                        } else {
                            if (!camhourneedle.half) {
                                camhourneedle.hour--;
                            } else {
                                // nothing to do
                            }
                            camCurrentTS -= 1800000;
                            camhourneedle.half = !camhourneedle.half;
                            generateTimeline(); // 插入播放列表块
                            playlistByHour(camhourneedle.hour);
                            e.stopPropagation();
                            // 剪辑状态下跳过无内容片段
                            if ($("#camEdit").html() == "提交"
                                    && camPlayUnitArr.length == 0) {
                                $(".scroll_earlier").trigger("click");
                                return false;
                            }
                            return false;
                        }
                    }).bind('selectstart', function() {
                return false;
            });
    $(".scroll_later").click(
            function(e) {
                // console.log(camCurrentDay);
                if (camCurrentDay <= 0 && timemode == 24) { //
                    return false;
                } else if (camCurrentDay == 0
                        && camhourneedle.hour > (new Date()).getHours() - 1) {
                    return false;
                } else if ($("#timespan").width() < 953) {
                    return false;
                }
                if ((camhourneedle.hour >= 23 && camhourneedle.half)
                        || timemode == 24) {
                    camCurrentDay--;
                    // console.log(camCurrentDay);
                    camhourneedle.hour = 0;
                    if (timemode == 24) {
                        camCurrentTS += 86400000;
                    } else {
                        camCurrentTS += 1800000;
                        camhourneedle.half = !camhourneedle.half;
                    }
                    generateTimeline(camCurrentDay); // 插入播放列表块
                    playlistmode(play_set, 24);
                    $(".camvodSelectDay .btn").eq(6 - camCurrentDay).css(
                            "background-color", "#faa732").siblings().css(
                            "background-color", "#1369C0");
                    return false;
                } else {
                    if (!camhourneedle.half) {
                        // nothing to do
                    } else {
                        camhourneedle.hour++;
                    }
                    camhourneedle.half = !camhourneedle.half;
                    camCurrentTS += 1800000;
                    generateTimeline();
                    playlistByHour(camhourneedle.hour);
                    e.stopPropagation();
                    // 剪辑状态下跳过无内容片段
                    if ($("#camEdit").html() == "提交"
                            && camPlayUnitArr.length == 0) {
                        $(".scroll_later").trigger("click");
                        return false;
                    }
                    return false;
                }
            }).bind('selectstart', function() {
        return false;
    });
}

function drawPlayList(){
    playlistmode(playlist.results, timemode);
}

// 插入时间轴模板
function generateTimeline(currentDay) {
    // console.log(camhourneedle);
    camCurrentTS = (new Date(camCurrentTS)).setHours(camhourneedle.hour,
            camhourneedle.half ? 30 : 0, 0, 0);
    // console.log((new Date(camCurrentTS)).toString());
    // console.log((new Date(camCurrentTS)).getHours());
    $("#timeline").empty();
    var date = new Date(camCurrentTS);
    var timestamp_now = date.getTime();
    date.setHours(0, 0, 0, 0);
    var timestamp_start = date.getTime();
    var time_ratio = (timestamp_now - timestamp_start) / (24 * 60 * 60 * 1000);
    var time_width = parseInt(time_ratio * 953);
    currentDay = typeof (currentDay) == 'undefined' ? currentDay = 0
            : currentDay;
    // date.setTime(timestamp_now - currentDay*86400000);
    var datetoday = toDoubleTime((date.getMonth() + 1)) + '-'
            + toDoubleTime(date.getDate());
    var nextdate = new Date(date.getTime() + 86400000);
    var dateafter = toDoubleTime((nextdate.getMonth() + 1)) + '-'
            + toDoubleTime(nextdate.getDate());
    if (timemode == 24) {
        var _time1 = '00:00';
        var _time2 = '06:00';
        var _time3 = '12:00';
        var _time4 = '18:00';
        var _time5 = '00:00';
        if (((new Date()).getDate() > (new Date(camCurrentTS)).getDate())) {
            time_width = 953;
        } else {
            timestamp_now = new Date();
            time_ratio = (timestamp_now - timestamp_start)
                    / (24 * 60 * 60 * 1000);
            time_width = parseInt(time_ratio * 953);
        }
    } else if (timemode == 1) {
        if (camhourneedle.half) {
            var _needleTwoDigit = toDoubleTime(camhourneedle.hour);
            var _time1 = _needleTwoDigit + ':30';
            var _time2 = _needleTwoDigit + ':45';
            if (camhourneedle.hour == 23) {
                nextdate = new Date(date.getTime() + 86400000);
                dateafter = toDoubleTime((nextdate.getMonth() + 1)) + '-'
                        + toDoubleTime(nextdate.getDate());
                var _time3 = '00:00' + '</br>' + dateafter;
                var _time4 = '00:15';
                var _time5 = '00:30';
            } else {
                var _time3 = toDoubleTime((camhourneedle.hour + 1)) + ':00';
                var _time4 = toDoubleTime((camhourneedle.hour + 1)) + ':15';
                var _time5 = toDoubleTime((camhourneedle.hour + 1)) + ':30';
            }

        } else {
            var _needleTwoDigit = toDoubleTime(camhourneedle.hour);
            var _time1 = _needleTwoDigit + ':00';
            var _time2 = _needleTwoDigit + ':15';
            var _time3 = _needleTwoDigit + ':30';
            var _time4 = _needleTwoDigit + ':45';
            var _time5 = toDoubleTime((camhourneedle.hour + 1)) + ':00';
        }
        if ((camhourneedle.hour < ((new Date()).getHours() - 1))
                || date.getTime() < (new Date()).setHours(0, 0, 0, 0)
                || ((camhourneedle.hour == (new Date()).getHours() - 1) && !camhourneedle.half)) {
            time_width = 953;
        } else if (camhourneedle.hour == (new Date()).getHours()
                || ((camhourneedle.hour == (new Date()).getHours() - 1) && camhourneedle.half)) {
            var _thisdate = new Date();
            timestamp_now = _thisdate.getTime();
            _thisdate.setHours(camhourneedle.hour, camhourneedle.half ? 30 : 0,
                    0, 0);
            time_ratio = (timestamp_now - _thisdate.getTime())
                    / (60 * 60 * 1000);
            time_width = parseInt(time_ratio * 953) > 953 ? 953
                    : parseInt(time_ratio * 953);
        }
        dateafter = datetoday;
        if (camhourneedle.hour == 23 && !camhourneedle.half) {
            _time5 = '00:00';
            nextdate = new Date(date.getTime() + 86400000);
            dateafter = toDoubleTime((nextdate.getMonth() + 1)) + '-'
                    + toDoubleTime(nextdate.getDate());
        } else if (camhourneedle.hour == 0) {
            var yesterday = new Date(date.getTime());
            datetoday = toDoubleTime((yesterday.getMonth() + 1)) + '-'
                    + toDoubleTime(yesterday.getDate());
        } else if (camhourneedle.hour == 23 && camhourneedle.half) {
            dateafter = toDoubleTime((nextdate.getMonth() + 1)) + '-'
                    + toDoubleTime(nextdate.getDate());
        }
    }
    time_width = time_width > 953 ? 953 : time_width;
    $(".timeline")
            .css("position", "relative")
            .append(
                    '<div id="timespan" title="" style="position:absolute;top:37px;left:0px;width:'
                            + time_width
                            + 'px;height:6px;background:url(./static/img/cam-timeline.png) repeat-x;border-radius:5px;">\
    <p id="camDate" style="position:absolute;top:12px;left:-30px;text-align:center;line-height:16px;">'
                            + _time1
                            + '</br>'
                            + datetoday
                            + '</p>\
    <p style="position:absolute;top:12px;left:210px;text-align:center;line-height:16px;">'
                            + _time2
                            + '</p>\
    <p style="position:absolute;top:12px;left:451px;text-align:center;line-height:16px;">'
                            + _time3
                            + '</p>\
    <p style="position:absolute;top:12px;left:691px;text-align:center;line-height:16px;">'
                            + _time4
                            + '</p>\
    <p id="camAfterDate" style="position:absolute;top:12px;left:907px;width:92px;text-align:center;line-height:16px;">'
                            + _time5
                            + '</br>'
                            + dateafter
                            + '</p>\
    <div id="camVodThumb" style="position:absolute;width:256px;height:144px;top:-185px;left:20px;border:1px solid #acacac;background:url(./static/img/loading.gif) no-repeat center #fff;z-index:20;display:none;"></div>\
    <div id="camplaylistcon" style="position:absolute;top:-24px;width:953px;height:30px;overflow:hidden;"></div>\
    </div>');
    // 如果处于剪辑模式保存指针位置
    if ($("#camEdit").html() == "提交") {
        for (k in camClipSpan) {
            $("#timespan").append(camClipSpan[k]);
        }
        // 移出clipspan hide掉camVodThumb
        $("#clipspan").mouseout(function() {
            $("#camVodThumb").hide();
        })
        // 设置时间片段可拖拽
        camClipDrag($("#clipspan")[0]);
        // 设置时间轴指针可拖拽
        $("#timespan .camClip").each(function(index, element) {
            camClipDrag(element);
        });
        // timeneedle;
        $("#timespan")
                .append(
                        '<img id="camClipNeedle" src="./static/img/cam-timeneedle-red.png" style="position:absolute;top:-27px;left:'
                                + $("#clipspan").css("left") + ';z-index:2"/>');
        // 调整clipneedle位置
        var _time_obj = getClipValidSpan();
        if (_time_obj.minute >= 10) {
            $("#clipspan").width(158);
            $("#camClipEnd").css("left",
                    parseInt($("#camClipStart").css("left")) + 158);
        }
        var timeObj_st = getTimestamp(parseInt($("#camClipStart").css("left")));
        var timespot_st = new Date((timeObj_st.st - 8 * 3600) * 1000);
        var clip_time_st = toDoubleTime(timespot_st.getHours()) + ':'
                + toDoubleTime(timespot_st.getMinutes()) + ':'
                + toDoubleTime(timespot_st.getSeconds());
        var timeObj_end = getTimestamp(parseInt($("#camClipEnd").css("left")));
        var timespot_end = new Date((timeObj_end.st - 8 * 3600) * 1000);
        var clip_time_end = toDoubleTime(timespot_end.getHours()) + ':'
                + toDoubleTime(timespot_end.getMinutes()) + ':'
                + toDoubleTime(timespot_end.getSeconds());
        $("#clipLabelSpan").css({
            "left" : $("#clipspan").css("left"),
            "width" : $("#clipspan").css("width")
        }).find("span").html(_time_obj.minute + "分钟" + _time_obj.second + "秒");
        $("#clipLabelStart").css("left",
                (parseInt($("#camClipStart").css("left")) - 25)).html(
                clip_time_st);
        $("#clipLabelEnd").css("left", $("#camClipEnd").css("left")).html(
                clip_time_end);
        $("#clipTooltip").css("left",
                parseInt($("#camClipStart").css("left")) - 40);
        // 播放内容随时间指针变动
        var _offset_x = parseInt($("#clipspan").css("left")) - 5;
        camtimestamp = getTimestamp(_offset_x).st;
        loadVod(playurl, getTimestamp(_offset_x));
    } else {
        $("#timespan")
                .append(
                        '<img id="camClipNeedle" src="./static/img/cam-timeneedle-red.png" style="position:absolute;top:-27px;left:'
                                + (getTimeneedlePos() - 6) + 'px;z-index:2"/>');
        // 设置时间轴指针可拖动;
        camdraging($("#camClipNeedle")[0]);
    }
    // 设置时间指针移动
    if (typeof (timer) != 'undefined') {
        clearInterval(timer);
    }
    var _timeinterval = timemode == 24 ? 86400 : 3600;
    timer = setInterval(function() {
        if ($("#timespan").width() <= 953) {
            $("#timespan").css("width",
                    parseInt($("#timespan").css("width")) + 1);
        } else {
            clearInterval(timer);
        }
        camtimestamp += parseInt(_timeinterval / 953);
        // console.log(camtimestamp);
    }, parseInt(_timeinterval * 1000 / 953));
    // 设置时间轴指针cursor
    if (typeof (sharee) != 'undefined' && sharee == 3) {
        $("#camClipNeedle").css({
            "cursor" : "pointer"
        });
    }
    arrows();
}
/**
 * 计算时间偏移
 * 
 * @returns {Number}
 */
function getTimeneedlePos() {
    // console.log(camtimestamp);
    if ((new Date(camCurrentTS)).getDate() != (new Date(
            (camtimestamp - 8 * 3600) * 1000)).getDate()) {
        return 10000;
    }
    var needleLeft = 0;
    if (timemode == 24) {
        var _time = new Date(camCurrentTS);
        _time.setHours(0, 0, 0, 0);
        var timestamp_start = _time.getTime();
        needleLeft = parseInt((camtimestamp - 8 * 3600 - parseInt(timestamp_start / 1000)) * 953 / 86400);
    } else if (timemode == 1) {
        var _time = new Date(camCurrentTS);
        _time.setHours(camhourneedle.hour, camhourneedle.half ? 30 : 0, 0, 0);
        var timestamp_start = _time.getTime();
        needleLeft = parseInt((camtimestamp - 8 * 3600 - parseInt(timestamp_start / 1000)) * 953 / 3600);
    }
    if (needleLeft < 0 || needleLeft > 953) {
        return 10000;
    } else {
        return needleLeft;
    }
}
function loadVod(url, timestamp, tovod) { // 加载播放器
    var time_day_start = timestamp.st;
    var time_day_end = timestamp.et;
    url += '&access_token=' + access + '&deviceid=' + deviceid + '&st='
            + time_day_start + '&et=' + time_day_end;
    // console.log(url);
    if (typeof (tovod) != 'undefined' && tovod == 1) {
        // to do thing
    } else {
        // player.remove();
    }
    createPlayer(url, false);
    player.onBuffer(function(event) { // 设置播放器缓存事件
        // console.log("onBuffer");
        if (typeof (timer) != 'undefined') {
            clearInterval(timer);
        }
    });
    player.onPlay(function(e) {
        // 设置时间指针移动
        if (typeof (timer) != 'undefined') {
            clearInterval(timer);
        }
        var _timeinterval = timemode == 24 ? 86400 : 3600;
        timer = setInterval(function() {
            if (parseInt($("#camClipNeedle").css("left")) <= 943) {
                var _cur_camneedle = parseInt($("#camClipNeedle").css("left"));
                // console.log(_cur_camneedle);
                for (var i = 0; i < camPlayUnitArr.length; i++) {
                    if (_cur_camneedle + 8 == camPlayUnitArr[i][1]) {
                        if (i == camPlayUnitArr.length - 1) {
                            $(".scroll_later").trigger("click");
                            _cur_camneedle = parseInt($("#camClipNeedle").css(
                                    "left")) + 2;
                        } else {
                            _cur_camneedle = camPlayUnitArr[i + 1][0];
                        }
                        $("#timeline").trigger("click", [ _cur_camneedle ]);
                        return false;
                        break;
                    }
                }
                $("#camClipNeedle").css("left",
                        parseInt($("#camClipNeedle").css("left")) + 1);
            } else if (parseInt($("#camClipNeedle").css("left")) == 944) {
                $(".scroll_later").trigger("click");
                $("#camClipNeedle").css("left", "476px");
            } else {
                return false;
            }
            camtimestamp += parseInt(_timeinterval / 953);
        }, parseInt(_timeinterval * 1000 / 953));
    })
    player.onIdle(function(e) {
        if (typeof (timer) != 'undefined') {
            clearInterval(timer);
        }
    })
    player.onBuffer(function(event) {
        log("onBuffer");
    });
    player.onReady(function(event) {
        log("onReady");
    });
    player.onBufferChange(function(event) {
        log("buffer percent : " + event.bufferPercent + "; position : "
                + event.position);
    });
    player.onComplete(function(e) {
        log("onComplete");
    });
    player.onError(function(e) {
        if (typeof (timer) != 'undefined') {
            clearInterval(timer);
        }
        log("onErr");
    });
}
function getTimestamp(offsetx) {
    var timestamp = {};
    var date = new Date(camCurrentTS);
    date.setHours(0, 0, 0, 0);
    var timestamp_start = date.getTime();
    var timeoffset = timemode == 1 ? (camhourneedle.hour * 3600000 + parseInt((60 * 60 * 1000 * (offsetx + 6) / 953)))
            : parseInt((24 * 60 * 60 * 1000 * offsetx / 953)); // 判断条件值得商榷
    var _time_fix = camhourneedle.half ? 1800 : 0;
    timestamp.st = parseInt((timestamp_start + timeoffset) / 1000) + 8 * 3600
            + _time_fix; // 8*3600 意指时差
    timestamp.et = timestamp.st + 1800;
    return timestamp;
    // $(this).css("background-color","");
}
// 播放列表展示模式(mode为1小时/24小时)
function playlistmode(play_set, mode) {
    camPlayUnitArr = [];
    for (var i = 0; i < play_set.length; i++) {
        setplayunit(play_set[i], mode);
    }
    // 播放unit悬停效果
    var timer_tn = null;
    var mousePos = 0; // 用于判断鼠标是否在timespan里 0 移出 1 在其上
    $("#timespan .cam-play-unit").hover(function() {
        $(this).css("cursor", "pointer");
        $(this).animate({
            "height" : "30px"
        }, "fast");
        mousePos = 1;
    }, function() {
        $(this).animate({
            "height" : "24px"
        }, "fast");
        mousePos = 0;
    }).mouseout(function() {
        if (typeof (timer_tn) != 'undefined') {
            clearTimeout(timer_tn);
        }
        $("#camVodThumb").css("display", "none");
    });
    // title时刻提示缩略图
    $("#timespan").mousemove(
            function(e) {
                var _offset_x = e.pageX - $("#timeline").offset().left;
                if (_offset_x <= $("#timespan").width()) {
                    var timeObj = getTimestamp(_offset_x);
                    var timespot = new Date((timeObj.st - 8 * 3600) * 1000);
                    $(this).attr(
                            "title",
                            toDoubleTime(timespot.getHours()) + ':'
                                    + toDoubleTime(timespot.getMinutes()));
                    if (typeof (timer_tn) != 'undefined') {
                        clearTimeout(timer_tn);
                    }
                    // 需要判断
                    if (typeof (sharee) != 'undefined' && sharee == 3) {
                        timer_tn = setTimeout(function() {
                            setThumbnail(timeObj.st, _offset_x, mousePos); // mousePos用于判断鼠标是否在timespan里
                                                                            // 0 移出
                                                                            // 1
                                                                            // 在其上
                        }, 50)
                    }
                } else {
                    $("#timespan").css("cursor", "default");
                }
            });

}
// 设置播放单元位置
function setplayunit(unitArr, mode) {
    // console.log(unitArr[0] + ' '+ (new
    // Date((unitArr[0]-8*3600)*1000)).toString());
    // console.log(unitArr[1] + ' '+ (new
    // Date((unitArr[1]-8*3600)*1000)).toString());
    var _st = unitArr[0];
    var date = new Date(camCurrentTS);
    var time_mode = 0;
    if (mode == 24) {
        date.setHours(0, 0, 0, 0);
        time_mode = 86400;
    } else if (mode == 1) {
        var _tmpdate = new Date(camCurrentTS);
        date.setHours(_tmpdate.getHours(), camhourneedle.half ? 30 : 0, 0, 0);
        time_mode = 3600
    }
    var timestamp_start = parseInt(date.getTime() / 1000);
    var unitPos = Math.ceil((_st - timestamp_start) * 953 / time_mode);

    if (mode == 1) {
        // unitPos = unitPos - 5;
    }

    var unitWidth = Math.ceil((unitArr[1] - unitArr[0]) * 953 / time_mode);
    camPlayUnitArr.push([ unitPos, unitPos + unitWidth ]);
    $("#camplaylistcon")
            .append(
                    '<div class="cam-play-unit" startTime="'
                            + unitArr[5]
                            + '" endTime="'
                            + unitArr[4]
                            + '" taskId="'
                            + unitArr[6]
                            + '" tag="'
                            + unitArr[3]
                            + '" style="position:absolute;left:'
                            + unitPos
                            + 'px;top:0px;width:'
                            + unitWidth
                            + 'px;height:24px;background:#D1D1D1";cursor:pointer;"></div>');
}
// 按小时调整播放列表
function playlistByHour(hour) {
    $(".cam-play-unit").remove();
    var new_play_set = [];
    var _date = new Date(camCurrentTS);
    _date.setHours(typeof (hour) != 'undefined' ? hour : _date.getHours(),
            camhourneedle.half ? 30 : 0, 0, 0);
    // console.log("_date"+_date.toString());
    var play_set = typeof (playlist) == "undefined" ? [] : playlist.results;
    for (var i = 0; i < play_set.length; i++) {

        if (playlist.results[i][0] >= parseInt(_date.getTime() / 1000)
                && playlist.results[i][0] <= parseInt(_date.getTime() / 1000 + 3600)) {
            if (playlist.results[i][1] >= parseInt(_date.getTime() / 1000)
                    && playlist.results[i][1] <= parseInt(_date.getTime() / 1000 + 3600)) {
                new_play_set.push(playlist.results[i]);
            } else {
                new_play_set.push([ playlist.results[i][0],
                        (parseInt(_date.getTime() / 1000 + 3600)), 0,
                        playlist.results[i][3], playlist.results[i][4],
                        playlist.results[i][5] ]);
            }
        } else if (playlist.results[i][1] >= parseInt(_date.getTime() / 1000)
                && playlist.results[i][1] <= parseInt(_date.getTime() / 1000 + 3600)) {
            new_play_set.push([ (parseInt(_date.getTime() / 1000)),
                    playlist.results[i][1], 0, playlist.results[i][3],
                    playlist.results[i][4], playlist.results[i][5] ]);
        } else if (playlist.results[i][0] <= parseInt(_date.getTime() / 1000)
                && playlist.results[i][1] >= parseInt(_date.getTime() / 1000 + 3600)) {
            new_play_set.push([ parseInt(_date.getTime() / 1000),
                    parseInt(_date.getTime() / 1000), 0,
                    playlist.results[i][3], playlist.results[i][4],
                    playlist.results[i][5] ]);
        }
    }
    playlistmode(new_play_set, 1);
    // 如果处于剪辑模式设置timespan
    if ($("#camEdit").html() == "提交") {
        if (getClipValidSpan().minute >= 10) {
            $("#clipspan").width(158);
            $("#camClipEnd").css("left",
                    parseInt($("#camClipStart").css("left")) + 158);
        }
        $("#camClipEnd").css("left",
                parseInt($("#camClipStart").css("left")) + 100);
        setclipspan();
        var _time_obj = getClipValidSpan();
        var timeObj_st = getTimestamp(parseInt($("#camClipStart").css("left")));
        var timespot_st = new Date((timeObj_st.st - 8 * 3600) * 1000);
        var clip_time_st = toDoubleTime(timespot_st.getHours()) + ':'
                + toDoubleTime(timespot_st.getMinutes()) + ':'
                + toDoubleTime(timespot_st.getSeconds());
        var timeObj_end = getTimestamp(parseInt($("#camClipEnd").css("left")));
        var timespot_end = new Date((timeObj_end.st - 8 * 3600) * 1000);
        var clip_time_end = toDoubleTime(timespot_end.getHours()) + ':'
                + toDoubleTime(timespot_end.getMinutes()) + ':'
                + toDoubleTime(timespot_end.getSeconds());
        $("#clipLabelSpan").css({
            "left" : $("#clipspan").css("left"),
            "width" : $("#clipspan").css("width")
        }).find("span").html(_time_obj.minute + "分钟" + _time_obj.second + "秒");
        $("#clipLabelStart").css("left",
                (parseInt($("#camClipStart").css("left")) - 42)).html(
                clip_time_st);
        $("#clipLabelEnd").css("left", $("#camClipEnd").css("left")).html(
                clip_time_end);
        $("#clipTooltip").css("left",
                parseInt($("#camClipStart").css("left")) - 40);
    }
}
// 单位时间日期补双
function toDoubleTime(timeStr) {
    if (timeStr.toString().length == 1) {
        timeStr = '0' + timeStr;
    }
    return timeStr;
}

// 获取缩略图
var lastThumbnailTimestamp = 0;
var lastThumbnailUrl = '';

function setThumbnail(timestamp, pos, mousePos) {
    if (mousePos == 1) {
        // $("#camVodThumb").empty().css({"left":(pos-128),"background":"url(js/playRecord/loading.gif)
        // no-repeat center #fff","display":"block"});
    }
    var _playthumbnail_new = lastThumbnailUrl;
    if (Math.abs(lastThumbnailTimestamp - timestamp) < 40) {
        // var _playthumbnail_new = typeof(playthumbnail) ==
        // 'undefined'?'':playthumbnail +
        // '&st='+(timestamp-60)+'&et='+(timestamp+60);
    } else {
        lastThumbnailTimestamp = timestamp;
        _playthumbnail_new = typeof (playthumbnail) == 'undefined' ? ''
                : playthumbnail + '&access_token=' + access + '&deviceid='
                        + deviceid + '&st=' + (timestamp - 60) + '&et='
                        + (timestamp + 60);
        lastThumbnailUrl = _playthumbnail_new;
    }

}
// 插入录像天数选择按钮
function camvodSelectDay() {
    $("#selectTimespan")
            .append(
                    '<div class="camvodSelectDay btn-group" style="visibility:-hidden;left:20%;margin:auto;"></div>');
    for (var i = 6; i >= 0; i--) {
        var _cam_selectDay_date = new Date((new Date()).getTime() - i
                * 86400000);
        $(".camvodSelectDay").append(
                '<button style="background-color: #1369C0" class="btn">'
                        + _cam_selectDay_date.getDate() + '</button>');
    }
    // setDayButton();
    $(".camvodSelectDay .btn").eq(6).css("background-color", "#faa732");
    $(".camvodSelectDay .btn")
            .each(
                    function(index, element) {
                        $(element)
                                .click(
                                        function(e) {
                                            if ($(this).attr("camlist") == 'blank') {
                                                return false;
                                            }
                                            $(".camvodSelectDay .btn")
                                                    .each(
                                                            function(index,
                                                                    element) {
                                                                if ($(element)
                                                                        .attr(
                                                                                "camlist") != 'blank') {
                                                                    $(element)
                                                                            .css(
                                                                                    "background-color",
                                                                                    "#1369C0");
                                                                }
                                                            });
                                            if ($(this).attr("camlist") != 'blank') {
                                                $(this).css("background-color",
                                                        "#faa732");
                                            }
                                            var __new_date = new Date(
                                                    camCurrentTS);
                                            var _new_day = new Date(
                                                    (new Date()).getTime()
                                                            - 86400000
                                                            * (6 - index));
                                            var play_set = typeof (playlist) == "undefined" ? []
                                                    : playlist.results;
                                            camCurrentTS = __new_date.setMonth(
                                                    _new_day.getMonth(),
                                                    _new_day.getDate());
                                            if (camCurrentTS > (new Date())
                                                    .getTime()) {
                                                camCurrentTS = (new Date())
                                                        .getTime();
                                                camhourneedle.hour = (new Date())
                                                        .getHours();
                                            }
                                            // console.log(camhourneedle.hour);
                                            generateTimeline(6 - index);
                                            if (timemode == 24) {
                                                playlistmode(play_set, 24);
                                            } else {
                                                playlistByHour(camhourneedle.hour);
                                            }
                                            camCurrentDay = 6 - index;
                                        });
                    });
}
// 看录像拖拽指针及事件处理
function camdraging(obj) {
    obj.onmousedown = function(ev) {
        if (typeof (sharee) != 'undefined' && sharee == 3) {
            var oEvent = ev || window.event;
            // oEvent.stopPropagation();
            var _offset_x = oEvent.pageX - $("#timeline").offset().left;
            disX = oEvent.pageX - obj.offsetLeft;
            // disY=oEvent.clientY-obj.offsetTop;
            document.onmousemove = function(ev) {
                if (!timelineMousemove(ev, obj)) {
                    return false;
                }
            }
            document.onmouseup = function(ev) {
                document.onmousemove = null;
                document.onmouseup = null;
            }
            return false;
        } else {
            return false;
        }
    }
}
function timelineMousemove(ev, obj) {

    // 播放unit悬停效果
    var timer_tn = null;
    var mousePos = 1; // 用于判断鼠标是否在timespan里 0 移出 1 在其上
    $("#timespan .cam-play-unit").hover(function() {
        $(this).css("cursor", "pointer");
        $(this).animate({
            "height" : "30px"
        }, "fast");
        mousePos = 1;
    }, function() {
        $(this).animate({
            "height" : "24px"
        }, "fast");
        mousePos = 0;
    }).mouseout(function() {
        if (typeof (timer_tn) != 'undefined') {
            clearTimeout(timer_tn);
        }
        $("#camVodThumb").css("display", "none");
    });
    $("#camClipNeedle").mouseout(function(e) {
        if (typeof (timer_tn) != 'undefined') {
            clearTimeout(timer_tn);
        }
        $("#camVodThumb").css("display", "none");
    });
    // title时刻提示
    var oEvent = ev || window.event;
    _offset_x = oEvent.pageX - $("#timeline").offset().left;

    if (_offset_x > 0 && _offset_x <= $("#timespan").width()) {
        obj.style.left = oEvent.pageX - disX + 'px';
        var timeObj = getTimestamp(_offset_x);
        var timespot = new Date((timeObj.st - 8 * 3600) * 1000);
        $("#timespan").attr(
                "title",
                toDoubleTime(timespot.getHours()) + ':'
                        + toDoubleTime(timespot.getMinutes()));
        if (typeof (timer_tn) != 'undefined') {
            clearTimeout(timer_tn);
        }
        // 需要判断
        timer_tn = setTimeout(function() {
            // setThumbnail(timeObj.st,_offset_x,mousePos);
        }, 300)
    } else {
        $("#timespan").css("cursor", "default");
        return false;
    }
    return true;

}
// 剪辑拖拽指针及事件处理
function camClipDrag(obj) {
    obj.onmousedown = function(ev) {
        // if(typeof(share) != 'undefined' && share == 3){
        if (($(obj).attr("id") == 'camClipStart' || $(obj).attr("id") == 'camClipEnd')) {
            $(obj).css("z-index", parseInt($(obj).css("z-index")) + 1);
        }
        if (getClipValidSpan().minute > 10) {
            // $("#clipspan").width(150);
            // $(obj).trigger("mouseup");
            // return false;
        } else {
            var oEvent = ev || window.event;
            // oEvent.stopPropagation();
            var _offset_x = oEvent.pageX - $("#timeline").offset().left;
            disX = oEvent.pageX - obj.offsetLeft;
            // disY=oEvent.clientY-obj.offsetTop;
            document.onmousemove = function(ev) {
                if (($(obj).attr("id") == 'camClipStart' || $(obj).attr("id") == 'camClipEnd')
                        && (parseInt($("#camClipStart").css("left")) >= parseInt($(
                                "#camClipEnd").css("left")))) {
                    $("#camClipStart").trigger("mouseup").css("left",
                            parseInt($("#clipspan").css("left")) - 6);
                    $("#camClipEnd").trigger("mouseup").css("left",
                            parseInt($("#camClipStart").css("left")) + 11);
                    $("#clipspan")
                            .css(
                                    {
                                        "left" : parseInt($("#camClipStart")
                                                .css("left")) + 6,
                                        "width" : parseInt($("#camClipEnd")
                                                .css("left"))
                                                - parseInt($("#camClipStart")
                                                        .css("left"))
                                    });
                    // 时刻提示信息,我应该把它封成一个函数
                    var timeObj_st = getTimestamp(parseInt($("#camClipStart")
                            .css("left")));
                    var timespot_st = new Date(
                            (timeObj_st.st - 8 * 3600) * 1000);
                    var clip_time_st = toDoubleTime(timespot_st.getHours())
                            + ':' + toDoubleTime(timespot_st.getMinutes())
                            + ':' + toDoubleTime(timespot_st.getSeconds());
                    var timeObj_end = getTimestamp(parseInt($("#camClipEnd")
                            .css("left")));
                    var timespot_end = new Date(
                            (timeObj_end.st - 8 * 3600) * 1000);
                    var clip_time_end = toDoubleTime(timespot_end.getHours())
                            + ':' + toDoubleTime(timespot_end.getMinutes())
                            + ':' + toDoubleTime(timespot_end.getSeconds());
                    var _time_obj = getClipValidSpan();
                    $("#clipLabelSpan").css({
                        "left" : $("#clipspan").css("left"),
                        "width" : $("#clipspan").css("width")
                    }).find("span").html(
                            _time_obj.minute + "分钟" + _time_obj.second + "秒");
                    $("#clipLabelStart").css("left",
                            (parseInt($("#camClipStart").css("left")) - 42))
                            .html(clip_time_st);
                    $("#clipLabelEnd")
                            .css("left", $("#camClipEnd").css("left")).html(
                                    clip_time_end);
                    $("#clipTooltip").css("left",
                            parseInt($("#camClipStart").css("left")) - 40);
                    $("#clipspan")
                            .css(
                                    {
                                        "left" : parseInt($("#camClipStart")
                                                .css("left")) + 6,
                                        "width" : parseInt($("#camClipEnd")
                                                .css("left"))
                                                - parseInt($("#camClipStart")
                                                        .css("left"))
                                    });
                    $("#camClipNeedle").css("left", _offset_x);
                    return false;
                } else {
                    if (getClipValidSpan().minute >= 10) {
                        $("#camClipStart").css("left",
                                parseInt($("#clipspan").css("left")) - 5);
                        $("#camClipEnd").css("left",
                                parseInt($("#camClipEnd").css("left")) - 5);
                        $(obj).trigger("mouseup");
                        return false;
                    } else {
                        if ($(".alert .alert-heading").css("display") != 'none') {
                            // obj.style.top=oEvent.clientY-disY+'px';
                            var needle_1_x = $("#camClipStart").offset().left;
                            var needle_2_x = $("#camClipEnd").offset().left;
                            if ($(obj).attr("id") == 'camClipStart') {
                                $("#clipspan")
                                        .css(
                                                {
                                                    "left" : parseInt($(
                                                            "#camClipStart")
                                                            .css("left")) + 6,
                                                    "width" : needle_2_x
                                                            - needle_1_x
                                                });
                            } else if ($(obj).attr("id") == 'camClipEnd') {
                                $("#clipspan").css({
                                    "width" : needle_2_x - needle_1_x
                                });
                            } else if ($(obj).attr("id") == 'clipspan') {
                                if (parseInt($("#clipspan").css("left")) < 0) {
                                    $("#clipspan").css("left", 0);
                                    $("#clipspan").trigger("mouseup");
                                    return false;
                                } else if (parseInt($("#clipspan").css("left")) > parseInt($(
                                        "#timespan").css("width"))
                                        - parseInt($("#clipspan").css("width"))) {
                                    $("#clipspan").css(
                                            "left",
                                            parseInt($("#timespan")
                                                    .css("width"))
                                                    - parseInt($("#clipspan")
                                                            .css("width")));
                                    $("#clipspan").trigger("mouseup");
                                    return false;
                                } else {
                                    $("#camClipStart")
                                            .css(
                                                    "left",
                                                    parseInt($("#clipspan")
                                                            .css("left")) - 6);
                                    $("#camClipEnd")
                                            .css(
                                                    "left",
                                                    parseInt($("#clipspan")
                                                            .css("left"))
                                                            + parseInt($(
                                                                    "#clipspan")
                                                                    .css(
                                                                            "width"))
                                                            - 6);
                                }
                            }
                            $("#clipspan")
                                    .css("width", needle_2_x - needle_1_x);
                            // 播放unit悬停效果
                            var timer_tn = null;
                            var mousePos = 1; // 用于判断鼠标是否在timespan里 0 移出 1 在其上
                            $("#timespan .cam-play-unit").hover(function() {
                                $(this).css("cursor", "pointer");
                                $(this).animate({
                                    "height" : "30px"
                                }, "fast");
                                mousePos = 1;
                            }, function() {
                                $(this).animate({
                                    "height" : "24px"
                                }, "fast");
                                mousePos = 0;
                            }).mouseout(function() {
                                if (typeof (timer_tn) != 'undefined') {
                                    clearTimeout(timer_tn);
                                }
                                $("#camVodThumb").css("display", "none");
                            });
                            $("#camClipNeedle").mouseout(function(e) {
                                if (typeof (timer_tn) != 'undefined') {
                                    clearTimeout(timer_tn);
                                }
                                $("#camVodThumb").css("display", "none");
                            });
                            // title时刻提示
                            var oEvent = ev || window.event;
                            _offset_x = oEvent.pageX
                                    - $("#timeline").offset().left;

                            if (_offset_x > 0
                                    && _offset_x <= $("#timespan").width()
                                    && parseInt($("#camClipStart").css("left")) >= -6
                                    && parseInt($("#camClipStart").css("left")) <= parseInt($(
                                            "#timespan").css("width"))
                                            - parseInt($("#clipspan").css(
                                                    "width")) - 6) {
                                obj.style.left = oEvent.pageX - disX + 'px';
                                var timeObj = getTimestamp(_offset_x);
                                var timespot = new Date(
                                        (timeObj.st - 8 * 3600) * 1000);
                                $("#timespan").attr(
                                        "title",
                                        toDoubleTime(timespot.getHours())
                                                + ':'
                                                + toDoubleTime(timespot
                                                        .getMinutes()));
                                if (typeof (timer_tn) != 'undefined') {
                                    clearTimeout(timer_tn);
                                }
                                // 需要判断
                                timer_tn = setTimeout(function() {
                                    // setThumbnail(timeObj.st,_offset_x,mousePos);
                                }, 300)
                            } else if (parseInt($("#camClipStart").css("left")) < -6) {
                                $("#camClipStart").css("left", "-6px");
                            } else if (parseInt($("#camClipStart").css("left")) > parseInt($(
                                    "#timespan").css("width"))
                                    - parseInt($("#clipspan").css("width"))) {
                                $("#camClipStart").css(
                                        "left",
                                        parseInt($("#timespan").css("width"))
                                                - parseInt($("#clipspan").css(
                                                        "width")) - 6 + "px");
                            } else {
                                $("#timespan").css("cursor", "default");
                                return false;
                            }
                        }

                        var timeObj_st = getTimestamp(parseInt($(
                                "#camClipStart").css("left")));
                        var timespot_st = new Date(
                                (timeObj_st.st - 8 * 3600) * 1000);
                        var clip_time_st = toDoubleTime(timespot_st.getHours())
                                + ':' + toDoubleTime(timespot_st.getMinutes())
                                + ':' + toDoubleTime(timespot_st.getSeconds());
                        var timeObj_end = getTimestamp(parseInt($("#camClipEnd")
                                .css("left")));
                        var timespot_end = new Date(
                                (timeObj_end.st - 8 * 3600) * 1000);
                        var clip_time_end = toDoubleTime(timespot_end
                                .getHours())
                                + ':'
                                + toDoubleTime(timespot_end.getMinutes())
                                + ':' + toDoubleTime(timespot_end.getSeconds());
                        var _time_obj = getClipValidSpan();
                        $("#clipLabelSpan").css({
                            "left" : $("#clipspan").css("left"),
                            "width" : $("#clipspan").css("width")
                        }).find("span").html(
                                _time_obj.minute + "分钟" + _time_obj.second
                                        + "秒");
                        $("#clipLabelStart")
                                .css(
                                        "left",
                                        (parseInt($("#camClipStart")
                                                .css("left")) - 42)).html(
                                        clip_time_st);
                        $("#clipLabelEnd").css("left",
                                $("#camClipEnd").css("left")).html(
                                clip_time_end);
                        $("#clipTooltip").css("left",
                                parseInt($("#camClipStart").css("left")) - 40);
                        // console.log((new
                        // Date((camtimestamp-8*3600)*1000)).toString()+'down');
                    }
                }
            }
            document.onmouseup = function() {
                document.onmousemove = null;
                document.onmouseup = null;
                var _offset_x = 0;
                setclipspan();
                if ($(obj).attr("id") == 'camClipStart') {
                    _offset_x = parseInt($(obj).css("left"));
                } else if ($(obj).attr("id") == 'camClipEnd') {
                    _offset_x = parseInt($(obj).css("left")) - 16;
                    for (var i = 0; i < camPlayUnitArr.length; i++) {
                        if (_offset_x > camPlayUnitArr[i][1]
                                && _offset_x < camPlayUnitArr[i + 1][0]) {
                            _offset_x = camPlayUnitArr[i + 1][0];
                            break;
                        } else if (_offset_x < camPlayUnitArr[0][0]) {
                            _offset_x = camPlayUnitArr[0][0];
                            break;
                        }
                    }
                } else if ($(obj).attr("id") == 'clipspan') {
                    _offset_x = parseInt($(obj).css("left")) - 6;
                }
                $("#camClipNeedle").css("left", _offset_x);
                camtimestamp = getTimestamp(_offset_x).st;
                loadVod(playurl, getTimestamp(_offset_x));
                $("#camShowVod").html("看直播");
                $(".live").html("录像");
                $(".camHint").remove();
                if (typeof (timer_tn) != 'undefined') {
                    clearTimeout(timer_tn);
                }
                $("#camVodThumb").css("display", "none");
            }
            return false;
            /*
             * }else{ return false; }
             */
        }
    }
}
// 剪辑范围判定
function setclipspan() {
    var _clipStart = parseInt($("#camClipStart").css("left"));
    var _clipEnd = parseInt($("#camClipEnd").css("left"));
    for (var i = 0; i < camPlayUnitArr.length; i++) {
        if (_clipStart > camPlayUnitArr[i][1] && i < camPlayUnitArr.length - 1
                && _clipEnd < camPlayUnitArr[i + 1][0]) {
            $("#camClipStart").css("left", camPlayUnitArr[i + 1][0] - 6);
            $("#camClipEnd").css("left", camPlayUnitArr[i + 1][1] - 6);
            break;
        }
        if (_clipEnd < camPlayUnitArr[0][0]) {
            $("#camClipStart").css("left", camPlayUnitArr[0][0] - 6);
            $("#camClipEnd").css("left", camPlayUnitArr[0][1] - 6);
            break;
        }
        if (_clipStart > camPlayUnitArr[i][1] && i < camPlayUnitArr.length - 1
                && _clipStart < camPlayUnitArr[i + 1][0]) {
            $("#camClipStart").css("left", camPlayUnitArr[i + 1][0] - 6);
        } else if (_clipStart < camPlayUnitArr[0][0]) {
            $("#camClipStart").css("left", camPlayUnitArr[0][0] - 6);
        } else if (_clipStart > camPlayUnitArr[camPlayUnitArr.length - 1][1]) {
            $("#camClipStart").css("left",
                    camPlayUnitArr[camPlayUnitArr.length - 1][0] - 6);
            $("#camClipEnd").css("left",
                    camPlayUnitArr[camPlayUnitArr.length - 1][1] - 6);
        }
        if (_clipEnd > camPlayUnitArr[i][1] && i < camPlayUnitArr.length - 1
                && _clipEnd < camPlayUnitArr[i + 1][0]) {
            $("#camClipEnd").css("left", camPlayUnitArr[i][1] - 6);
        } else if (_clipEnd > camPlayUnitArr[camPlayUnitArr.length - 1][1]) {
            $("#camClipEnd").css("left",
                    camPlayUnitArr[camPlayUnitArr.length - 1][1] - 6);
            // 下面这段跟后面的代码重复了，但是只有在这里加上才能起作用。
            $("#clipspan").css(
                    {
                        "left" : parseInt($("#camClipStart").css("left")) + 6,
                        "width" : parseInt($("#camClipEnd").css("left"))
                                - parseInt($("#camClipStart").css("left"))
                    });
            // 时刻提示信息
            var timeObj_st = getTimestamp(parseInt($("#camClipStart").css(
                    "left")) - 6);
            var timespot_st = new Date((timeObj_st.st - 8 * 3600) * 1000);
            var clip_time_st = toDoubleTime(timespot_st.getHours()) + ':'
                    + toDoubleTime(timespot_st.getMinutes()) + ':'
                    + toDoubleTime(timespot_st.getSeconds());
            var timeObj_end = getTimestamp(parseInt($("#camClipEnd")
                    .css("left")));
            var timespot_end = new Date((timeObj_end.st - 8 * 3600) * 1000);
            var clip_time_end = toDoubleTime(timespot_end.getHours()) + ':'
                    + toDoubleTime(timespot_end.getMinutes()) + ':'
                    + toDoubleTime(timespot_end.getSeconds());
            var _time_obj = getClipValidSpan();
            $("#clipLabelSpan").css({
                "left" : $("#clipspan").css("left"),
                "width" : $("#clipspan").css("width")
            }).find("span").html(
                    _time_obj.minute + "分钟" + _time_obj.second + "秒");
            $("#clipLabelStart").css("left",
                    (parseInt($("#camClipStart").css("left")) - 42)).html(
                    clip_time_st);
            $("#clipLabelEnd").css("left", $("#camClipEnd").css("left")).html(
                    clip_time_end);
            $("#clipTooltip").css("left",
                    parseInt($("#camClipStart").css("left")) - 40);
            $("#clipspan").css(
                    {
                        "left" : parseInt($("#camClipStart").css("left")) + 6,
                        "width" : parseInt($("#camClipEnd").css("left"))
                                - parseInt($("#camClipStart").css("left"))
                    });
        }
    }
    $("#camClipNeedle").css("left", parseInt($("#camClipStart").css("left")));
    camtimestamp = getTimestamp(parseInt($("#camClipStart").css("left"))).st;
    // 超过范围后刻度校正
    if (getClipValidSpan().minute >= 10) {
        $("#camClipEnd").css("left",
                parseInt($("#camClipStart").css("left")) + 150);
    }
    // 时刻提示信息
    var timeObj_st = getTimestamp(parseInt($("#camClipStart").css("left")));
    var timespot_st = new Date((timeObj_st.st - 8 * 3600) * 1000);
    var clip_time_st = toDoubleTime(timespot_st.getHours()) + ':'
            + toDoubleTime(timespot_st.getMinutes()) + ':'
            + toDoubleTime(timespot_st.getSeconds());
    var timeObj_end = getTimestamp(parseInt($("#camClipEnd").css("left")));
    var timespot_end = new Date((timeObj_end.st - 8 * 3600) * 1000);
    var clip_time_end = toDoubleTime(timespot_end.getHours()) + ':'
            + toDoubleTime(timespot_end.getMinutes()) + ':'
            + toDoubleTime(timespot_end.getSeconds());
    var _time_obj = getClipValidSpan();
    $("#clipLabelSpan").css({
        "left" : $("#clipspan").css("left"),
        "width" : $("#clipspan").css("width")
    }).find("span").html(_time_obj.minute + "分钟" + _time_obj.second + "秒");
    $("#clipLabelStart").css("left",
            (parseInt($("#camClipStart").css("left")) - 42)).html(clip_time_st);
    $("#clipLabelEnd").css("left", $("#camClipEnd").css("left")).html(
            clip_time_end);
    $("#clipTooltip")
            .css("left", parseInt($("#camClipStart").css("left")) - 40);
    $("#clipspan").css(
            {
                "left" : parseInt($("#camClipStart").css("left")) + 6,
                "width" : parseInt($("#camClipEnd").css("left"))
                        - parseInt($("#camClipStart").css("left"))
            });
}
// 计算剪辑范围有效覆盖时间
function getClipValidSpan() {
    var _clip_span_arr = [];
    var _clip_start = $("#camClipStart").offset().left + 6
            - $("#timeline").offset().left;
    var _clip_width = $("#camClipEnd").offset().left
            - $("#camClipStart").offset().left;
    var _time_obj = {};
    for (var i = 0; i < camPlayUnitArr.length; i++) {
        if (camPlayUnitArr[i][0] >= _clip_start
                && camPlayUnitArr[i][0] <= _clip_start + _clip_width) {
            if (camPlayUnitArr[i][1] >= _clip_start
                    && camPlayUnitArr[i][1] <= _clip_start + _clip_width) {
                _clip_span_arr.push(camPlayUnitArr[i]);
            } else {
                _clip_span_arr.push([ camPlayUnitArr[i][0],
                        _clip_start + _clip_width ]);
            }
        } else if (camPlayUnitArr[i][1] >= _clip_start
                && camPlayUnitArr[i][1] <= _clip_start + _clip_width) {
            _clip_span_arr.push([ _clip_start, camPlayUnitArr[i][1] ]);
        } else if (camPlayUnitArr[i][0] <= _clip_start
                && camPlayUnitArr[i][1] >= _clip_start + _clip_width) {
            _clip_span_arr.push([ _clip_start, _clip_start + _clip_width ]);
        }
    }
    var _clip_time_total = 0;
    for (var i = 0; i < _clip_span_arr.length; i++) {
        _clip_time_total += (_clip_span_arr[i][1] - _clip_span_arr[i][0]);
    }
    var _time_offset = _clip_width - _clip_time_total;
    var timeObj_st = getTimestamp(parseInt($("#camClipStart").css("left")));
    var timeObj_end = getTimestamp(parseInt($("#camClipEnd").css("left")));
    var _total_time = timeObj_end.st - timeObj_st.st
            - parseInt(_time_offset * 3600 / 953);
    // console.log((new Date((timeObj_end.st-8*3600)*1000)).toString() + ' ' +
    // (new Date((timeObj_st.st-8*3600)*1000)).toString() +' ' +
    // parseInt(_time_offset*60/953));
    _time_obj.minute = parseInt(_total_time / 60);
    _time_obj.second = _total_time % 60;
    // console.log(_time_obj.second);
    return _time_obj;
}
// 若某日无录像内容将对应天的按钮置灰
function setDayButton() {
    var play_set = typeof (playlist) == "undefined" ? [] : playlist.results;
    var _start_ts = parseInt((new Date((new Date()).getTime() - 6 * 86400000))
            .setHours(0, 0, 0, 0) / 1000) + 8 * 3600;
    if (play_set.length == 0) {
        $(".camvodSelectDay .btn").css("background-color", "#ccc").attr(
                "camlist", "blank");
    } else {
        for (var i = 0; i < 6; i++) {
            var _temp_ts = _start_ts + i * 86400;
            for (var j = 0; j < play_set.length; j++) {
                if (play_set[j][0] > _temp_ts
                        && play_set[j][1] < _temp_ts + 86400) {
                    $(".camvodSelectDay .btn").eq(i).css("background-color",
                            "#1369C0").attr("camlist", "has");
                    break;
                } else {
                    $(".camvodSelectDay .btn").eq(i).css("background-color",
                            "#ccc").attr("camlist", "blank");
                }
            }
        }
    }
}

// 弹框模板
function Elframe(idname, title, lb, rb) {
    if (idname == 'webox2') {
        var blidname = 'lookCam';
        var bridname = 'setCamparams';
    } else if (idname == 'webox3') {
        blidname = 'setCam_one';
        bridname = 'setCam_all';
    } else {
        blidname = 'setopenshare';
        bridname = 'setPrivateshare';
    }
    if (rb) {
        var _Tpl = '<div id="'
                + idname
                + '" class="alert alert-info camHint" style="position:absolute;top:200px;left:50%;width:400px;margin-left:-200px;padding:5px 8px 20px;text-align:center;z-index:100;height: 150px;">\
      <h1 onselectstart="return false;" style="-moz-user-select: none; background: none repeat scroll 0 0 ;display: block; font-size: 22px; font-weight: normal; height: 30px; line-height: 30px; margin: 0; padding: 0 0 0 5px; position: relative;">\
      <a class="span" href="javascript:void(0);" style=" cursor: pointer; display: inline-block; font-size: 22px; height: 30px; position: absolute; right: 6px; top: 0; width: 17px;">X</a>\
      </h1><h4 class="alert-heading" style="font-size:20px;margin: auto 20px;text-align:center;">'
                + title
                + '</h4>\
      <button id="'
                + blidname
                + '" class="btn btn-info" style="outline:none;width:86px;height:34px;margin-top: 22px;line-height:0px; margin-right: 12px;">'
                + lb
                + '</button>\
      <button id="'
                + bridname
                + '" class="btn btn-info" style="outline:none;width:86px;height:34px;margin-top: 22px;line-height:0px;">'
                + rb + '</button>\
      </div>';
    } else {
        _Tpl = '<div id="'
                + idname
                + '" class="alert alert-info camHint" style="position:absolute;top:200px;left:50%;width:400px;margin-left:-200px;padding:5px 8px 20px;text-align:center;z-index:100;height: 150px;">\
      <h1 onselectstart="return false;" style="-moz-user-select: none; background: none repeat scroll 0 0 ;display: block; font-size: 22px; font-weight: normal; height: 30px; line-height: 30px; margin: 0; padding: 0 0 0 5px; position: relative;">\
      <a class="span" href="javascript:void(0);" style=" cursor: pointer; display: inline-block; font-size: 22px; height: 30px; position: absolute; right: 6px; top: 0; width: 17px;">X</a>\
      </h1><h4 class="alert-heading" style="font-size:20px;margin: auto 20px;text-align:center;">'
                + title
                + '</h4>\
      <button id="'
                + blidname
                + '" class="btn btn-info" style="outline:none;width:86px;height:34px;margin-top: 22px;line-height:0px; margin-right: 12px;">'
                + lb + '</button></div>';
    }
    return _Tpl;
}
function getDate(o) {
    return o.getFullYear() + "-" + (o.getMonth() + 1) + "-" + o.getDate() + " "
            + o.getHours() + ":" + o.getMinutes() + ":" + o.getSeconds();
}