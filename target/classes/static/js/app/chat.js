(function (global, factory) {
    window.onload = function () {
        factory(global)
    }
})(typeof window !== "undefined" ? window : this, function (window) {
    if (!window.console) {
        function noop() {
        }

        window.console = {log: noop, error: noop, warn: noop}
    }
    if (!window.JSON) {
        window.JSON = {
            parse: function (sJSON) {
                return eval("(" + sJSON + ")")
            }, stringify: function () {
                var toString = Object.prototype.toString;
                var isArray = Array.isArray || function (a) {
                    return toString.call(a) === "[object Array]"
                };
                var escMap = {
                    '"': '\\"',
                    "\\": "\\\\",
                    "\b": "\\b",
                    "\f": "\\f",
                    "\n": "\\n",
                    "\r": "\\r",
                    "\t": "\\t"
                };
                var escFunc = function (m) {
                    return escMap[m] || "\\u" + (m.charCodeAt(0) + 65536).toString(16).substr(1)
                };
                var escRE = /[\\"\u0000-\u001F\u2028\u2029]/g;
                return function stringify(value) {
                    if (value == null) {
                        return "null"
                    } else if (typeof value === "number") {
                        return isFinite(value) ? value.toString() : "null"
                    } else if (typeof value === "boolean") {
                        return value.toString()
                    } else if (typeof value === "object") {
                        if (typeof value.toJSON === "function") {
                            return stringify(value.toJSON())
                        } else if (isArray(value)) {
                            var res = "[";
                            for (var i = 0; i < value.length; i++) res += (i ? ", " : "") + stringify(value[i]);
                            return res + "]"
                        } else if (toString.call(value) === "[object Object]") {
                            var tmp = [];
                            for (var k in value) {
                                if (value.hasOwnProperty(k)) tmp.push(stringify(k) + ": " + stringify(value[k]))
                            }
                            return "{" + tmp.join(", ") + "}"
                        }
                    }
                    return '"' + value.toString().replace(escRE, escFunc) + '"'
                }
            }()
        }
    }
    if (typeof Object.assign != "function") {
        Object.assign = function (target) {
            "use strict";
            if (target == null) {
                throw new TypeError("Cannot convert undefined or null to object")
            }
            target = Object(target);
            for (var index = 1; index < arguments.length; index++) {
                var source = arguments[index];
                if (source != null) {
                    for (var key in source) {
                        if (Object.prototype.hasOwnProperty.call(source, key)) {
                            target[key] = source[key]
                        }
                    }
                }
            }
            return target
        }
    }
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function (obj) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == obj) {
                    return i
                }
            }
            return -1
        }
    }
    if (!Number.parseInt) {
        Number.parseInt = function (txt) {
            return txt && txt.replace(/[^0-9]/gi, "") * 1
        }
    }
    if (typeof String.prototype.startsWith != "function") {
        String.prototype.startsWith = function (prefix) {
            return this.slice(0, prefix.length) === prefix
        }
    }
    if (typeof String.prototype.endsWith != "function") {
        String.prototype.endsWith = function (suffix) {
            return this.indexOf(suffix, this.length - suffix.length) !== -1
        }
    }
    var i18n = function () {
        var lang;
        setLang({"zh-TW": "tw", en: "en"}[window.navigator.language]);
        var defs = {
            "客服系统": {en: "Live Chat System", tw: "客服系統"},
            "连接...": {en: "Connecting...", tw: "連接..."},
            "男": {en: "Male", tw: "男"},
            "女": {en: "Female", tw: "女"},
            "机器人": {en: "Robot", tw: "機器人"},
            "机器人客服": {en: "Robot Service", tw: "機器人客服"},
            "在线咨询": {en: "Online Service", tw: "線上諮詢"},
            " 为您服务": {en: " serve for you", tw: " 為您服務"},
            "回车或点击选择": {en: "Enter or click to select", tw: "回車或點擊選擇"},
            "没有更多了": {en: "No more", tw: "沒有更多了"},
            "人工客服": {en: "Human Service", tw: "人工客服"},
            "游客": {en: "Tourists", tw: "遊客"},
            "满意度评价": {en: "Satisfaction evaluation", tw: "滿意度評估"},
            "您对刚才的服务沟通满意度如何？": {en: "How satisfied are you with the service communication? ", tw: "您對剛才的服務溝通滿意度如何？"},
            "很好": {en: "Very good", tw: "很好"},
            "好": {en: "Good", tw: "好"},
            "一般": {en: "Commonly", tw: "一般"},
            "差": {en: "Bad", tw: "差"},
            "已提交": {en: "Submitted", tw: "已提交"},
            "提交": {en: "Submit", tw: "提交"},
            "提交中...": {en: "Submitting...", tw: "提交中..."},
            "查看全文": {en: "View more", tw: "查看全文"},
            "留言反馈": {en: "Feedback", tw: "留言回饋"},
            "请填写您的留言内容": {en: "Please fill in the content of your message", tw: "請填寫您的留言內容"},
            "姓名": {en: "Name", tw: "姓名"},
            "电话": {en: "Phone", tw: "電話"},
            "邮箱": {en: "E-mail", tw: "電郵"},
            "Q Q": {en: "Q Q", tw: "Q Q"},
            "地址": {en: "Address", tw: "地址"},
            "留言": {en: "Content", tw: "留言"},
            "性别": {en: "Gender", tw: "性别"},
            "保密": {en: "Secrecy", tw: "保密"},
            " 150 字以内": {en: " Within 150 words", tw: " 150 字以內"},
            "您的输入不完整，*号为必填项": {en: "Your input is not complete, * is required", tw: "您的輸入不完整，*號為必填項"},
            "电话格式不正确，请重新输入！": {en: "Phone format is incorrect, please re-enter it! ", tw: "電話格式不正確，請重新輸入！"},
            "邮件格式不正确，请重新输入！": {en: "The e-mail format is incorrect. Please re-enter it!", tw: "郵件格式不正確，請重新輸入！"},
            "QQ格式不正确，请重新输入！": {en: "The QQ format is incorrect. Please re-enter it!", tw: "QQ格式不正確，請重新輸入！"},
            "留言内容不能超出150个字符": {en: "Message no more than 150 characters", tw: "留言內容不能超出150個字元"},
            "点击发送问题": {en: "Click to send question", tw: "點擊發送問題"},
            "有用": {en: "Yes", tw: "有用"},
            "没用": {en: "No", tw: "没用"},
            "解决": {en: "Yes", tw: "解決"},
            "未解决": {en: "No", tw: "未解決"},
            "满意": {en: "Yes", tw: "滿意"},
            "不满意": {en: "No", tw: "不滿意"},
            "正在输入...": {en: "Typing...", tw: "正在輸入..."},
            "加载更多历史消息": {en: "Load more", tw: "加載更多歷史消息"},
            "人工客服正在为您服务": {en: "Customer service is serving you", tw: "人工客服正在為您服務"},
            "智能机器人客服正在为您服务": {en: "Robot customer service is for your service", tw: "智慧型機器人客服正在為您服務"},
            "请联系客服人员到客服管理后台填写并完善网页接入": {
                en: "Please contact your administrator to complete web interface configuration",
                tw: "請聯系客服人員到客服管理後臺填寫並完善網頁接入"
            },
            "温馨提示，无须重复性提问，问题发送一次即可。": {
                en: "Warm tips, no need to send or ask questions twice",
                tw: "溫馨提示，無須重複性提問，問題發送一次即可。"
            },
            "连接错误，请检查您的网络连接": {en: "Connection error, please check your network", tw: "連接錯誤，請檢查您的網絡連接"},
            "新消息": {en: "New message", tw: "新消息"},
            "点击发送问题": {en: "Click to send", tw: "點擊發送問題"},
            "会话_": {en: "Chat - ", tw: "會話_"},
            "智能客服系统": {en: "Intelligent customer service system", tw: "智慧客服系統"},
            "智能客服向前一小步、客服工作向前一大步。": {
                en: "Smart customer service, a small step forward, customer service work a big step forward.",
                tw: "智慧客服向前一小步、客服工作向前一大步。"
            },
            "提示音": {en: "Ring", tw: "提示音"},
            "已静音": {en: "Muted", tw: "已靜音"},
            "因长时间未活动会话已关闭": {en: "Session closed due to inactivity for a long time", tw: "因長時間未活動會話已關閉"},
            "对话已在新窗口打开": {en: "The dialog has been opened in new window", tw: "對話已在新窗口打開"},
            "当前会话已中断": {en: "The current session has been interrupted", tw: "當前會話已中斷"},
            "收到": {en: "Receive ", tw: "收到"},
            "条未读消息": {en: " unread messages", tw: "條未讀消息"},
            "图片太大：": {en: "Picture size limited: ", tw: "圖片太大："},
            "不支持的图片格式：": {en: "Unsupport image type: ", tw: "不支持的圖片格式："},
            "获取上传权限失败": {en: "Access deny", tw: "獲取上傳許可權失敗"},
            "对话连接已中断，即将为您重新连接": {
                en: "Connection has been interrupted and is trying to reconnect",
                tw: "對話連接已中斷，即將為您重新連接"
            }
        }, keys = {}, fTimeOld;
        i18n.setLang = setLang;
        i18n.getLang = function () {
            return lang
        };
        return i18n;

        function setLang(l) {
            lang = l;
            if (lang) {
                lang = lang.substring(0, 2);
                $.attr(document.body, "lang", lang);
                document.title = {zh: "客服系统", tw: "客服系統", en: "Customer Service System"}[lang];
                fTimeOld || (fTimeOld = $.fTime);
                $.fTime = function () {
                    return chgTime(fTimeOld.apply(this, Array.prototype.slice.call(arguments)))
                }
            } else {
                $.attr(document.body, "lang", "");
                document.title = "客服系统";
                fTimeOld && ($.fTime = fTimeOld)
            }
        }

        function i18n(key) {
            var txt;
            if (!lang) {
                txt = key
            } else {
                txt = defs[key];
                if (txt) {
                    if ($.type(txt) === "object") {
                        txt = txt[lang]
                    } else if (txt && lang === "tw") {
                        txt = key
                    }
                }
            }
            if (!txt) {
                key in keys || (keys[key] = true, console.log("i18n 未定义：" + key));
                txt = key
            }
            var len;
            if (len = arguments.length) {
                for (var i = 1; i < len; i++) {
                    txt = txt.replace("%s", arguments[i])
                }
            }
            return txt
        }

        function chgTime(timestr) {
            if (!timestr) {
                return timestr
            }
            if (lang === "en") {
                timestr = timestr.replace(/年|月/, "-").replace(/日/, "").replace(/时/, "h").replace(/分/, "m").replace(/秒/, "s").replace(/昨天/, "Yesterday").replace(/今天/, "Today")
            } else if (lang === "tw") {
                timestr = timestr.replace(/时/, "時")
            }
            return timestr
        }
    }();
    var conf = function () {
        $.i18n = i18n;
        var href = resolveHref(), ua = $.ua, sid = getSid(), oid = getOid(), cached = getCached(), diatpl = getDiatpl(),
            aid = getAid(), wid = href.worker || href.wid || href.worker_id,
            gid = href.group || href.gid || href.groupid || href.group_id || href.sched_id,
            human = href.human !== "0" && href.human, reconn = cached.lastout + 48 * 36e5 > (new Date).getTime();
        (wid === "null" || wid === "0") && (wid = 0);
        (gid === "null" || gid === "0") && (gid = 0);
        (wid && ~wid || gid && ~gid) && (human = {human: human, wid: wid, gid: gid});
        (function () {
            $.public = /(chat|desk|cs).v5kf.com\/desk/.test(window.location.href);
            $.deploy = /(chat|desk|cs)(\.\w+){2,}/.test(window.location.hostname);
            $.debug = !(/desk/i.test(window.location.pathname) && !/debug/i.test(window.location.pathname)) && /localhost|27|yearn|10\.0\.0\.\d{1,3}|192\.168\.\d{1,3}\.\d{1,3}/i.test(window.location.hostname) || /\bdebug\b/i.test(window.location.href);
            try {
                $.SLD = $.deploy && window.location.hostname.split(".")[0] || "chat"
            } catch (e) {
            }
            $.maxPhoneW = href.maxPhoneWidth || 720;
            $.autoColor = function (color) {
                if ($.ua.ie < 9) {
                    if (/rgba|hsla/g.test(color)) {
                        var newC = color.replace(/\(/, ",").replace(/\)/, "");
                        var arr = newC.split(",");
                        return arr[0].replace(/a$/i, "") + "(" + arr[1] + "," + arr[2] + "," + arr[3] + ")"
                    } else {
                        return color
                    }
                } else {
                    return color
                }
            };
            $.autoHost = function (i) {
                if ($.debug) {
                    switch (i) {
                        case"www":
                            return "www.v5kf.com";
                        case"apis":
                            return "apis.v5kf.com";
                        case"static":
                            return "static.v5kf.com";
                        case"rs":
                            return "rs.v5kf.com";
                        case"vos":
                            return "vos.v5kf.com";
                        case"chat":
                        default:
                            return "chat.v5kf.com"
                    }
                } else {
                    if (/\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/i.test(window.location.hostname)) {
                        return location.host
                    } else {
                        if (!i || !$.deploy) {
                            return location.host
                        } else {
                            var arr = location.host.split(".");
                            arr[0] = i;
                            return arr.join(".")
                        }
                    }
                }
            };
            $.mediaPhone = function () {
                return !/ifr/.test(document.body.className) && document.body.clientWidth <= ($.maxPhoneW || 720) && document.body.clientWidth > 0
            };
            $.isPhone = window.phone || $.mediaPhone();
            $.isPhone && !/phone/i.test(document.body.className) && (document.body.className += " phone");
            $.isAndroid = navigator.userAgent.indexOf("Android") > -1 || navigator.userAgent.indexOf("Adr") > -1;
            $.isiOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
            $.isWeChat = navigator.userAgent.indexOf("MicroMessenger") > -1
        })();
        var host = window.location.host, arr = host.split("."), nArr = arr.slice(1);
        ~window.location.host.indexOf($.debug ? "v5kf.com" : arr[0] - 0 ? host : nArr.join(".")) && (document.domain = $.debug ? "v5kf.com" : arr[0] - 0 ? host : nArr.join("."));
        var lang = document.documentElement.lang;
        console.log("lang:", lang);
        if (!lang) {
            lang = "zh"
        }
        i18n.setLang(lang);
        return {
            system: {
                compatible: !ua.isIe || ua.ie >= 9,
                embed: (!window.parent || window.parent !== window) && (!ua.isIe || ua.ie > 8),
                diatpl: diatpl,
                voice: !($.cache(oid + "_voice") === "false"),
                ssl: /^https/i.test(window.location.protocol),
                debug: $.debug,
                env: getEnv(),
                magic: getMagic().magic,
                real: getMagic().real,
                fake: href.fake,
                human: human,
                reconn: reconn,
                lang: lang || (navigator.language || navigator.browserLanguage).toLowerCase(),
                url: {
                    ws: "ws://" + $.autoHost("chat") + "/sitews",
                    poll: "//" + $.autoHost("chat") + "/public/webauth/v9",
                    upload: "//" + $.autoHost("chat") + "/public/wxyt/web",
                    upurl: "//" + $.autoHost("vos") + "/public/upload/",
                    site: "//" + $.autoHost("apis") + "/public/api_dkf/get_chat_siteinfo",
                    histMode: "//" + $.autoHost("apis") + "/public/api_stat/get_visitor_chat",
                    hist: "//" + $.autoHost("apis") + "/public/api_wkby/get_messages",
                    hotQues: "//" + $.autoHost("apis") + "/public/api_dkf/get_hot_ques",
                    autoQues: "//" + $.autoHost("apis") + "/public/api_dkf/get_site_ques"
                }
            },
            site: Object.assign(cached.site || {}, {
                id: sid,
                aid: aid,
                autoQues: cached.autoQues,
                p_id: href.p_id,
                f_id: href.f_id,
                delay: {loadHist: false, stamp: null, json: getDelay(), timer: 0, time: 0},
                defHot: href.defhot && JSON.parse(decodeURIComponent(href.defhot)),
                theme: {color: getThemeColor()}
            }),
            robot: cached.robot,
            workers: cached.workers,
            guest: {id: oid, vip: getVip(), nickname: getNickName(), avatar: getAvatar(), auth: href.auth}
        };

        function resolveHref() {
            var search = window.location.search.substr(1).split("&"), ret = {}, it;
            for (var i = 0, len = search.length; i < len; i++) {
                it = search[i];
                ret[it.replace(/=[\w\W]*$/, "")] = it.replace(/^[\w\W]*?=/, "")
            }
            return ret
        }

        function getThemeColor() {
            var color = href.theme || href.color;
            color && (color = decodeURIComponent(color));
            if (color && /^#?[0-9a-z]{3,6}$/i.test(color)) {
                return color[0] === "#" ? color : "#" + color
            } else if (color && /^rgb\([0-9]{1,3}, ?[0-9]{1,3}, ?[0-9]{1,3}\)/i.test(color)) {
                return color
            }
            return null
        }

        function getVip() {
            var vip = href.vip;
            return vip && decodeURIComponent(vip)
        }

        function getAvatar() {
            var avatar = href.photo || href.avatar;
            return avatar && decodeURIComponent(avatar)
        }

        function getNickName() {
            var name = href.name || href.nickname || href.nick_name;
            return name && decodeURIComponent(name)
        }

        function getSid() {
            return parseInt(href.sid || href.s_id || href.site || href.siteid || href.site_id)
        }

        function getAid() {
            var aid = href.accountid || href.account || href.aid || href.a_id;
            return aid
        }

        function getDelay() {
            var sDelay = href.delay || "0", aDelay, iDelay, maxNum, minNum;
            sDelay = decodeURIComponent(sDelay).replace(/[\s|,|-|，|－]+/gi, "-");
            aDelay = sDelay.split("-");
            aDelay[0] = !aDelay[0] ? "0" : aDelay[0];
            aDelay[1] = !aDelay[1] ? aDelay[0] : aDelay[1];
            minNum = Math.min(aDelay[0], aDelay[1]);
            maxNum = Math.max(aDelay[0], aDelay[1]);
            minNum = minNum <= 10 ? minNum : "10";
            maxNum = maxNum <= 10 ? maxNum : "10";
            iDelay = Math.floor(Math.random() * (maxNum - minNum + 1) + minNum);
            return {text: sDelay, arr: aDelay, timers: [minNum, maxNum]}
        }

        function getOid() {
            var oid = href.oid || href.o_id || href.open_id || href.open || href.openid;
            if (!oid) {
                oid = $.cache(sid + "_oid")
            }
            if (!oid) {
                oid = (new Date).getTime().toString(36) + Math.round(Math.random() * 4294967295).toString(36);
                $.cache(sid + "_oid", oid || "")
            }
            return decodeURIComponent(oid)
        }

        function getEnv() {
            var browserName = ua.browser.name ? ua.browser.name + " " + ua.browser.version : ua.isIe ? "IE " + ua.ie : "";
            var env = [{key: "设备", val: ua.isDesktop ? "桌面电脑" : ua.isMobile ? "手机" : "平板"}, {
                key: "浏览器",
                val: browserName + (ua.isWechat ? " [微信]" : "")
            }, {key: "操作系统", val: ua.platform.fullname || ua.platform.name + " " + ua.platform.version}, {
                key: "屏幕",
                val: window.screen.width + "x" + window.screen.height
            }];
            href.ip && env.push({key: "IP", val: decodeURIComponent(href.ip)});
            if (href.location) {
                var where = decodeURIComponent(href.location);
                try {
                    $.type(where) === "string" && (where = JSON.parse(where));
                    var arr = [];
                    $.each(where, function (i, item) {
                        arr.push(item)
                    });
                    where = arr.join(" ")
                } catch (e) {
                    console.warn("conf=>href.location", e)
                }
                env.push({key: "位置", val: where})
            }
            if (!href.url && window.parent && window.parent !== window) {
                var refer;
                try {
                    refer = window.parent.location.href
                } catch (e) {
                    refer = document.referrer
                }
                refer && env.push({key: "来访", val: refer})
            } else {
                href.url && env.push({key: "来访", val: decodeURIComponent(href.url)})
            }
            href.wd && env.push({key: "来源关键词", val: decodeURIComponent(decodeURIComponent(href.wd))});
            return env
        }

        function getMagic() {
            var arr = href.magic || href.magic_content;
            var json = href.real;
            if (!arr) {
                arr = []
            } else {
                try {
                    arr = eval("(" + decodeURIComponent(arr) + ")");
                    if (arr.length && !arr[0].key) {
                        throw'magic解析错误,URL参数请遵照格式：&magic_content=[{"key":"参数名","val":"参数值"},...]'
                    }
                } catch (e) {
                    console.error(e, '\nmagic解析错误,URL参数请遵照格式：&magic_content=[{"key":"参数名","val":"参数值"},...]');
                    arr = []
                }
            }
            if (!json) {
                json = {}
            } else {
                json = eval("(" + decodeURIComponent(json) + ")")
            }
            return {magic: arr, real: json}
        }

        function getCached() {
            var ret = {};
            ret.site = $.cache(sid + "_site");
            ret.site = eval("(" + ret.site + ")");
            ret.robot = $.cache(sid + "_robot");
            ret.robot = eval("(" + ret.robot + ")");
            ret.autoQues = $.cache(sid + "_autoQues");
            ret.autoQues = eval("(" + ret.autoQues + ")");
            ret.workers = $.cache(sid + "_workers");
            ret.workers = eval("(" + ret.workers + ")");
            ret.lastout = $.cache(sid + "_lastout");
            ret.lastout = eval("(" + ret.lastout + ")");
            return ret
        }

        function getDiatpl() {
            var diatpl = href.diatpl || href.dia;
            return diatpl
        }
    }();
    var io = function () {
        function Socket(url, options) {
            if (!(this instanceof Socket)) {
                return new Socket(url, options)
            }
            options || (options = {});
            if (url && typeof url == "object") {
                options = url;
                url = options.url
            } else {
                options.url = url
            }
            if (!url) {
                throw new Error("您指定的URL为空！")
            }
            this.options = options;
            this.url = url;
            this.ws_url = options.ws_url || url;
            this.route = options.route || "ws";
            this.wid = 0;
            this.timely = options.timely || false;
            this.msgs = [];
            options.onopen && (this.onopen = options.onopen);
            options.onclose && (this.onclose = options.onclose);
            options.onerror && (this.onerror = options.onerror);
            options.onmessage && (this.onmessage = options.onmessage);
            this.timely || this.open()
        }

        var heartCheck = {
            active: true,
            timeout: 28e3,
            timeoutObj: null,
            checkoutObj: null,
            timeoutCb: null,
            checkCb: null,
            reset: function () {
                this.active = true
            },
            check: function () {
                var that = this;
                clearTimeout(this.checkoutObj);
                this.checkoutObj = setTimeout(function () {
                    if (!that.active) {
                        $.disconType = 1;
                        that.timeoutCb && that.timeoutCb();
                        return
                    }
                    that.checkCb && that.checkCb();
                    that.active = false;
                    that.check()
                }, that.timeout)
            },
            start: function (_timeoutCb, _checkCb) {
                this.active = true;
                $.disconType = 0;
                if (_timeoutCb) {
                    this.timeoutCb = _timeoutCb
                }
                if (_checkCb) {
                    this.checkCb = _checkCb
                }
                var that = this;
                that.check()
            }
        };
        Socket.CONNECTING = 0;
        Socket.OPEN = 1;
        Socket.CLOSING = 2;
        Socket.CLOSED = 3;
        Socket.prototype = {
            type: 0, open: function () {
                if (this.readyState && this.readyState === Socket.OPEN) {
                    console.warn("当前对象已经处于开启状态")
                } else if (this.readyState !== Socket.CONNECTING) {
                    this.readyState = Socket.CONNECTING;
                    var that = this;
                    setTimeout(function () {
                        if (that.readyState != Socket.OPEN) {
                            that.readyState = Socket.CLOSED
                        }
                    }, 3e4);
                    var WebSocket = window.WebSocket || window.MozWebSocket;
                    if (WebSocket && (this.route === "ws" || !this.route)) {
                        var url = this.options.getWsUrl() || this.url;
                        /^ws/i.test(url) || (url = (this.options.ssl ? "wss://" : "ws://") + window.location.host + url);
                        this._ws = new WebSocket(url);
                        this.type = 0
                    } else {
                        this._ws = new Polling(this.url, this.options);
                        this.type = 1
                    }
                    this.onopen && (this._ws.onopen = function () {
                        if (0 == that.type) {
                            heartCheck.start(function () {
                                that && that.close()
                            }, function () {
                                that && that._ws && that._ws.send("beat")
                            })
                        }
                        that.readyState = Socket.OPEN;
                        that.onopen.apply(that, arguments);
                        var msg = that.msgs.shift();
                        while (msg) {
                            that.send(msg);
                            msg = that.msgs.shift()
                        }
                    });
                    this.onclose && (this._ws.onclose = function () {
                        that.readyState = Socket.CLOSED;
                        that.onclose.apply(that, arguments);
                        that._ws = null
                    });
                    this.onerror && (this._ws.onerror = function () {
                        that.readyState = Socket.CLOSED;
                        that.onerror.apply(that, arguments)
                    });
                    this.onmessage && (this._ws.onmessage = function (e) {
                        if (0 == that.type) {
                            heartCheck.reset()
                        }
                        var json = e.data;
                        if (json == "beat") {
                            return
                        }
                        that.onmessage.apply(that, arguments)
                    })
                }
            }, close: function () {
                this.readyState = Socket.CLOSING;
                this._ws && this._ws.close()
            }, send: function (msg) {
                if (this.readyState !== Socket.OPEN) {
                    if (this.timely) {
                        if (this.readyState) {
                            console.log($.stamp(), "当前没有开启连接，重启连接");
                            $.showinfo("对话连接已中断，即将为您重新连接")
                        } else {
                            console.log($.stamp(), "开启连接")
                        }
                        this.msgs || (this.msgs = []);
                        if (msg && /\"o_type\":\"message\"/.test(msg)) {
                            this.msgs.push(msg)
                        }
                        this.open()
                    } else {
                        console.error($.stamp(), "当前没有开启连接，且没有开启适时重连")
                    }
                } else {
                    msg && this._ws.send(msg)
                }
            }
        };

        function Polling(url, options) {
            this.url = url;
            this.options = options;
            this.method = options.method || "GET";
            this.route = options.route || "ajax";
            this.engine = null;
            this.sender = null;
            this.msgs = [];
            this.open()
        }

        Polling.CONNECTING = 0;
        Polling.OPEN = 1;
        Polling.CLOSING = 2;
        Polling.CLOSED = 3;
        Polling.prototype = {
            open: function () {
                if (this.readyState && (this.readyState === Polling.OPEN || this.readyState === Polling.CLOSING)) {
                    console.warn("当前轮询对象已处于开启状态")
                } else if (this.readyState !== Polling.CONNECTING) {
                    this.readyState = Polling.CONNECTING;
                    var that = this;
                    var jsonp = /jsonp?/i.test(this.route || "");
                    var xhr = this.engine = $.ajax(this.url, {
                        method: jsonp ? "GET" : "POST" || this.method,
                        data: this.options.data,
                        jsonp: jsonp,
                        timeout: 5e3,
                        headers: {},
                        success: function (text) {
                            var json = eval("(" + text + ")");
                            if (json.o_error && json.o_error > 0) {
                                try {
                                    if (that.onerror) {
                                        that.onerror(json)
                                    } else {
                                        throw json.o_errmsg
                                    }
                                } finally {
                                    that.close()
                                }
                            } else {
                                "id" in json && (that.id = json.id, that.url += "?_query=" + that.id);
                                json.url && (that.url = json.url);
                                that.readyState = Polling.OPEN;
                                that.options.onauth && that.options.onauth.call(that, text)
                            }
                        },
                        error: function (err) {
                            try {
                                if (that.onerror) {
                                    that.onerror(err)
                                } else {
                                    throw err
                                }
                            } finally {
                                that.close()
                            }
                        },
                        done: function () {
                            if (that.readyState === Polling.OPEN) {
                                that.engine = null;
                                that.poll();
                                that.onopen && that.onopen({
                                    type: "open",
                                    currentTarget: that,
                                    target: that,
                                    timeStamp: (new Date).getTime()
                                })
                            } else if (that.readyState === Polling.CLOSING) {
                                that.readyState = Polling.CLOSED;
                                that.onclose && that.onclose("创建AJAX轮询失败")
                            } else if (that.readyState === Polling.CONNECTING) {
                                that.onerror && that.onerror("创建JSONP轮询失败");
                                that.close();
                                that.readyState = Polling.CLOSED;
                                that.onclose && that.onclose("创建JSONP轮询失败")
                            }
                        }
                    })
                }
            }, poll: function () {
                if (this.engine) {
                    console.warn("已经存在轮询连接");
                    return
                }
                if (this.readyState === Polling.OPEN) {
                    var that = this;
                    console.log($.stamp(), "poll");
                    var jsonp = /jsonp?/i.test(this.route || "");
                    this.engine = $.ajax(this.url, {
                        method: jsonp ? "GET" : this.method,
                        data: this.options.pollData,
                        jsonp: jsonp,
                        jqr: this.engine,
                        success: function (text) {
                            console.log($.stamp(), "onpoll:" + text);
                            that.onsuccess(text)
                        },
                        error: function (err) {
                            console.log($.stamp(), "onpollerr:" + err);
                            that.readyState === Polling.OPEN && that.onerror && that.onerror(err);
                            that.close()
                        },
                        done: function () {
                            that.engine = null;
                            that.poll()
                        }
                    })
                } else {
                    this.readyState = Polling.CLOSED;
                    this.onclose && this.onclose()
                }
            }, close: function () {
                if (this.readyState < Polling.CLOSING) {
                    this.readyState = Polling.CLOSING;
                    this.engine && this.engine.abort();
                    this.sender && this.sender.abort();
                    this.engine = this.sender = null
                }
            }, send: function (msg) {
                if (this.readyState !== Polling.OPEN) {
                    return
                }
                if (msg && (this.sender || this.msgs.length)) {
                    this.msgs.push(msg)
                } else if (msg || (msg = this.msgs.shift())) {
                    if (typeof msg !== "string") {
                        console.error("待发送的消息必须是一个字符串");
                        this.send();
                        return
                    }
                    var that = this;
                    var jsonp = /jsonp?/i.test(this.route || "");
                    this.sender = $.ajax(this.url, {
                        method: "post",
                        jsonp: jsonp,
                        data: msg,
                        jpr: jsonp ? this.sender : null,
                        success: function (text) {
                            if (!/^ok$/i.test(text)) {
                                that.onsuccess(text)
                            }
                        },
                        error: function (err) {
                            that.onerror && that.onerror(err);
                            that.close()
                        },
                        done: function () {
                            that.sender = null;
                            that.send()
                        }
                    })
                }
            }, onsuccess: function (text) {
                var that = this;
                if (that.onmessage) {
                    var texts;
                    if (!that.prep && /^\[.*\]$/.test(text)) {
                        texts = eval("(" + text + ")")
                    } else if (typeof that.prep === "function") {
                        texts = that.prep(text)
                    } else {
                        texts = [text]
                    }
                    for (var i = 0; i < texts.length; i++) {
                        text = texts[i];
                        typeof text === "object" && JSON && (text = JSON.stringify(text));
                        that.onmessage({data: text})
                    }
                }
            }
        };
        return Socket
    }();
    var consts = function () {
        var consts = {
            cTypes: {
                nulls: 0,
                wechat: 1,
                yixin: 2,
                web: 3,
                open: 4,
                wxqy: 5,
                sinamb: 6,
                alipay: 7,
                app: 8,
                xmpp: 9,
                ticket: 10,
                magic: 11
            },
            cTypeTexts: ["nulls", "wechat", "yixin", "web", "open", "wxqy", "sinamb", "alipay", "app", "xmpp", "ticket", "magic"],
            cTypeTextZhs: ["", "微信", "易信", "网页", "开放平台", "微信企业号", "新浪微博", "支付宝", "APP", "XMPP", "工单", "MAGIC"],
            wkStatus: {OFFLINE: 0, ONLINE: 1, HIDE: 2, LEAVE: 3, BUSY: 4},
            msgTypes: {
                NULLS: 0,
                TEXT: 1,
                IMAGE: 2,
                LOCATION: 3,
                LINK: 4,
                EVENT: 5,
                VOICE: 6,
                VIDEO: 7,
                SHORT_VIDEO: 8,
                NEWS: 9,
                MUSIC: 10,
                WXCS: 11,
                TEL: 14,
                RES: 20,
                VS: 21,
                APP_URL: 22,
                COMMENT: 23,
                NOTE: 24,
                CONTROL: 25,
                MJSON: 26
            },
            msgTypesZh: {
                NULLS: "空",
                TEXT: "文字",
                IMAGE: "图片",
                LOCATION: "地址",
                LINK: "链接",
                EVENT: "事件",
                VOICE: "语音",
                VIDEO: "视频",
                SHORT_VIDEO: "短视频",
                SVIDEO: void 0,
                SHORT_VIDEO: void 0,
                NEWS: "图文",
                MUSIC: "音乐",
                WXCS: 11,
                RES: 20,
                VS: 21,
                APP_URL: 22,
                COMMENT: 23,
                NOTE: 24,
                CONTROL: 25,
                MJSON: 26
            },
            msgDirs: {W2C: 0, C2W: 1, R2C: 2, R2CW: 3, R2QC: 4, R2VC: 5, W2R: 6, R2W: 7, R2CR: 8, R2CQ: 9, R2WM: 10},
            cType: cType,
            cTypeText: cTypeText,
            cTypeTextZh: cTypeTextZh,
            msgType: msgType,
            msgTypeZh: msgTypeZh
        };
        return consts;

        function cType(type) {
            if (/^\d+$/.test(type)) {
                return type >= 256 ? type / 256 : type
            } else {
                type === "weixin" && (type = "wechat");
                return consts.cTypes[type]
            }
        }

        function cTypeText(type) {
            return consts.cTypeTexts[type]
        }

        function cTypeTextZh(type) {
            return consts.cTypeTextZhs[type]
        }

        function msgType(type) {
            if (!/^\d+$/.test(type)) {
                return consts.msgTypes[type]
            } else {
                for (var key in consts.msgTypes) {
                    if (type == consts.msgTypes[key]) {
                        return key
                    }
                }
            }
        }

        function msgTypeZh(type) {
            if (!/^\d+$/.test(type)) {
                return consts.msgTypesZh[type]
            } else {
                return consts.msgTypesZh[msgType(type)]
            }
        }
    }();
    var audio = function () {
        var audio;
        return {
            playAudio: function (src) {
                if (!audio) {
                    audio = document.createElement("audio")
                }
                !audio.paused && audio.pause && audio.pause();
                src && (audio.src = $.setProtocol(src));
                audio.play && audio.play();
                return audio
            }, pauseAudio: function () {
                audio && audio.pause && audio.pause();
                return audio
            }
        }
    }();
    var adapt = function () {
        var msgTypes = consts.msgTypes;
        return {fMsg: fMsg, pMsg: pMsg};

        function fMsg(json, hold, valid) {
            try {
                if (valid && json.hasOwnProperty("valid")) {
                    return json.valid
                }
                var ret = {
                    id: json.message_id || json.id,
                    wid: json.w_id || json.wid,
                    type: parseInt(json.message_type || json.type),
                    stamp: $.stamp(json.create_time || json.stamp || void 0),
                    dir: parseInt(json.hasOwnProperty("direction") ? json.direction : json.dir),
                    transparent: json.transparent,
                    json: json.json || {}
                };
                json.w_id && (ret.wid = json.w_id);
                if (!hold && ret.dir === 4) {
                    ret.dir = 1
                } else if (hold && ret.dir === 2) {
                    ret.dir = 4
                }
                if (json.candidate) {
                    ret.robot = [];
                    for (var i = 0, len = json.candidate.length; i < len; i++) {
                        var rMsg = fMsg(json.candidate[i], true);
                        rMsg && ret.robot.push(rMsg)
                    }
                }
                switch (parseInt(json.message_type || json.type)) {
                    case msgTypes.NULLS:
                        return null;
                    case msgTypes.TEXT:
                        ret.json = {text: json.content || json.text_content};
                        break;
                    case msgTypes.IMAGE:
                        var d = json.json_content;
                        if (d) {
                            $.type(d) === "string" && (d = JSON.parse(d));
                            ret.json = {mid: d.media_id, src: d.src || d.pic_url}
                        } else {
                            ret.json = {src: json.src || json.pic_url, mid: json.media_id}
                        }
                        break;
                    case msgTypes.LOCATION:
                        var d = json.json_content;
                        if (d) {
                            $.type(d) === "string" && (d = JSON.parse(d));
                            ret.json = {lat: d.x, lng: d.y, scale: d.scale, label: d.label}
                        } else {
                            ret.json = {lat: json.x, lng: json.y, scale: json.scale, label: json.label}
                        }
                        break;
                    case msgTypes.LINK:
                        var d = json.json_content;
                        if (d) {
                            $.type(d) === "string" && (d = JSON.parse(d));
                            ret.json = {desc: d.description, link: d.url, name: d.title}
                        } else {
                            ret.json = {desc: json.description, link: json.url, name: json.title}
                        }
                        break;
                    case msgTypes.EVENT:
                        store.getState().current = Object.assign({}, store.getState().current, {typing: json.cancel || false});
                        return null;
                    case msgTypes.VOICE:
                        var d = json.json_content;
                        if (d && $.type(d) === "string") {
                            d = JSON.parse(d) || {};
                            if (d.format === "amr" && !window.AMR) {
                                $.loadAmr()
                            }
                            ret.json = {
                                src: json.src || d.url,
                                mirror: d.url || null,
                                mid: d.media_id,
                                format: d.format,
                                text: json.recognition
                            }
                        } else {
                            ret.json = {
                                src: json.src || json.url,
                                mirror: json.url || null,
                                mid: json.media_id,
                                format: json.format,
                                text: json.recognition
                            }
                        }
                        break;
                    case msgTypes.VIDEO:
                        var d = json.json_content;
                        if (d && $.type(d) === "string") {
                            d = JSON.parse(d) || {};
                            ret.json = {
                                src: json.src || d.url,
                                mirror: d.url || null,
                                mid: d.media_id,
                                format: d.format,
                                text: json.recognition
                            }
                        } else {
                            ret.json = {
                                src: json.src || json.url,
                                mirror: json.url || null,
                                mid: json.media_id,
                                format: json.format,
                                text: json.recognition
                            }
                        }
                        break;
                    case msgTypes.SHORT_VIDEO:
                        var d = json.json_content;
                        if (d && $.type(d) === "string") {
                            d = JSON.parse(d) || {};
                            ret.json = {
                                src: json.src || d.url,
                                mirror: d.url || null,
                                mid: d.media_id,
                                format: d.format,
                                text: json.recognition
                            }
                        } else {
                            ret.json = {
                                src: json.src || json.url,
                                mirror: json.url || null,
                                mid: json.media_id,
                                format: json.format,
                                text: json.recognition
                            }
                        }
                        break;
                    case msgTypes.NEWS:
                        var d = json.json_content, articles;
                        if (d) {
                            $.type(d) === "string" && (d = JSON.parse(d));
                            articles = d.articles
                        } else if (json.articles) {
                            articles = json.articles
                        }
                        if (articles && articles.length) {
                            if (articles.length > 1) {
                                ret.json = [];
                                for (var i = 0; i < articles.length; i++) {
                                    var it = articles[i];
                                    ret.json.push({link: it.url, name: it.title, img: it.pic_url})
                                }
                            } else {
                                var it = articles[0];
                                ret.json = {name: it.title, desc: it.description, img: it.pic_url, link: it.url}
                            }
                        } else {
                            ret = null
                        }
                        break;
                    case msgTypes.MUSIC:
                        ret.json = {
                            src: json.music_url,
                            hqsrc: json.hq_music_url,
                            name: json.title,
                            desc: json.description
                        };
                        break;
                    case msgTypes.WXCS:
                        return null;
                    case msgTypes.TEL:
                        var d = json.json_content;
                        if (d && $.type(d) === "string") {
                            d = JSON.parse(d);
                            ret.json = {num: d.called}
                        } else {
                            ret.json = {num: json.called}
                        }
                        break;
                    case msgTypes.RES:
                        return null;
                    case msgTypes.VS:
                        return null;
                    case msgTypes.APP_URL:
                        return null;
                    case msgTypes.COMMENT:
                        ret.json = json.unsend ? void 0 : {code: json.code};
                        break;
                    case msgTypes.NOTE:
                        ret.json = json.unsend ? void 0 : {
                            name: json.name || json.username,
                            phone: json.phone,
                            email: json.email,
                            qq: json.qq,
                            text: json.text,
                            address: json.address,
                            gender: json.gender
                        };
                        break;
                    case msgTypes.CONTROL:
                        return null;
                    case msgTypes.MJSON:
                        return null;
                    default:
                        console.warn("无法处理的消息：message::" + json.message_type)
                }
                if (ret) {
                    json.valid = true
                } else {
                    json.valid = false;
                    $.report({func: "fMsg", err: "err unvalid", data: json});
                    console.error("err unvalid")
                }
                if (valid) {
                    return json.valid
                }
                return ret
            } catch (err) {
                $.report({func: "fMsg", err: err, data: json});
                console.error(err)
            }
        }

        function pMsg(json) {
            var msg = {o_type: "message", direction: 1, message_type: json.type}, type = json.type;
            json = json.json;
            switch (type) {
                case msgTypes.TEXT:
                    msg.content = json.text;
                    break;
                case msgTypes.IMAGE:
                    msg.pic_url = json.src;
                    msg.title = json.name;
                    json.mid && (msg.media_id = json.mid);
                    break;
                case msgTypes.NEWS:
                    if (!("length" in json)) {
                        json = [json]
                    }
                    msg.articles = $.each(json, function (i, it) {
                        return {title: it.name, pic_url: it.img, description: it.desc, url: it.link}
                    });
                    break;
                case msgTypes.VOICE:
                    msg.url = json.src;
                    json.format && (msg.format = json.format);
                    json.mid && (msg.media_id = json.mid);
                    msg.title = json.name;
                    break;
                case msgTypes.MUSIC:
                    msg.music_url = json.src;
                    msg.hq_music_url = json.src;
                    msg.title = json.name;
                    msg.description = json.desc;
                    break;
                case msgTypes.LOCATION:
                    msg.x = parseFloat(json.lat);
                    msg.y = parseFloat(json.lng);
                    msg.label = parseInt(json.scale);
                    break;
                case msgTypes.COMMENT:
                    msg.code = json.code;
                    break;
                case msgTypes.EVENT:
                    msg.event = json.event;
                    msg.cancel = json.cancel;
                    msg.content = json.content;
                    break;
                case msgTypes.NOTE:
                    msg = Object.assign({}, msg, json);
                    break;
                case msgTypes.CONTROL:
                    msg.code = json.code;
                    json.argc && (msg.argc = json.argc);
                    json.argv && (msg.argv = json.argv);
                    break;
                default:
                    console.warn("无法处理的消息类型：" + msg.message_type);
                    return null
            }
            return msg
        }
    }();
    var stamp = function () {
        function stamp(date) {
            /undefined|null/.test($.type(date)) && (date = (new Date).getTime());
            if (/^\d+$/.test(date)) {
                if (date < 1e10) {
                    return date * 1e3
                } else {
                    return parseInt(date)
                }
            } else if (typeof date === "string" && (date = new Date(date.replace(/\-/g, "/"))) && date.toJSON()) {
                return date.getTime()
            } else {
                console.warn("无法处理的时间戳：" + date);
                return date
            }
        }

        return stamp
    }();
    var report = function () {
        var pending = window.failedReports || [];
        window.failedReports = [];
        return function (json) {
            json && pending.push(json);
            if (!$.reporturl || !$.auth) {
                return
            }
            if (pending.length) {
                while (json = pending.shift()) {
                    commit(json)
                }
            }
        };

        function commit(json) {
        }
    }();
    var sendid = function () {
        var idCursorTime = (new Date).getTime(), idCursor = 0;
        return function () {
            return idCursorTime + "-" + idCursor++
        }
    }();
    var upload = function () {
        var form = document.createElement("form"), input = document.createElement("input"), maxsize = 6 * 1024 * 1024,
            sid, aid, oid, auth, authurl, upurl;
        input.type = "file";
        input.name = "FileContent";
        input.multiple = true;
        if ($.ua.isMobile) {
            input.accept = "image/*"
        } else {
            input.accept = "image/jpg,image/jpeg,image/png,image/gif"
        }
        form.appendChild(input);

        function _upload(cb, camera, inFile) {
            if ($.ua.isIe && $.ua.ie < 10) {
                $.showinfo("浏览器旧，请使用Google Chrome或360急速浏览器等上传图片！", 3e3);
                return void 0
            }
            openDlg(function (file) {
                getImgAuth(function (uplink) {
                    var imgId = $.sendid();
                    var fd = null;
                    var lrzEnable = false;
                    try {
                        if ((!$.ua.isIe || $.ua.ie > 10) && lrz) {
                            lrzEnable = true
                        }
                    } catch (e) {
                        console.warn("lrzEnable =>", e)
                    }
                    if (lrzEnable && lrz && (!inFile || !/Blob|blob/i.test(inFile.toString()))) {
                        console.log("lrzEnable =>", true);
                        lrz(file, {width: 1280, height: 1280, quality: 1}).then(function (rst) {
                            console.log((new Date).getTime(), "lrz =>", file.size, rst);
                            if (rst.fileLen > maxsize) {
                                $.showinfo($.i18n("图片太大：") + file.size, 2e3);
                                return
                            }
                            fd = rst.formData;
                            cb({imgId: imgId, file: rst.base64, name: file.name});
                            doUpload(file, uplink, fd, function (json) {
                                cb({imgId: imgId, src: json.url, name: file.name})
                            }, function (error) {
                                cb({imgId: imgId, error: error, name: file.name})
                            })
                        }).catchs(function (err) {
                            console.warn((new Date).getTime(), "lrz =>", err);
                            doUpload(file, uplink, fd, function (json) {
                                cb({imgId: imgId, src: json.url, name: file.name})
                            }, function (error) {
                                cb({imgId: imgId, error: error, name: file.name})
                            })
                        }).always(function () {
                            console.log("always => finish")
                        })
                    } else {
                        console.log("lrzEnable =>", false);
                        /Blob|blob/i.test(file.toString()) && cb({
                            imgId: imgId,
                            file: createBlobUrl(file),
                            name: file.name
                        });
                        doUpload(file, uplink, null, function (json) {
                            cb({imgId: imgId, src: json.url, name: file.name})
                        }, function (error) {
                            cb({imgId: imgId, error: error, name: file.name})
                        })
                    }
                })
            }, camera, inFile)
        }

        function createBlobUrl(file) {
            window.URL = window.URL || window.webkitURL;
            return window.URL.createObjectURL(file)
        }

        function doUpload(file, uplink, fd, success, error) {
            if (!fd) {
                if (file.size > maxsize) {
                    $.showinfo($.i18n("图片太大：") + file.size, 2e3);
                    return
                }
                fd = new FormData;
                fd.append("FileContent", file)
            }
            console.log("doUpload ajax", {fd: fd, uplink: uplink, auth: auth});
            $.ajax(uplink, {
                method: "post",
                headers: {Accept: "application/json, text/javascript, */*; q=0.01", Authorization: auth},
                data: fd,
                success: function (text) {
                    if (text) {
                        var json = JSON.parse(text);
                        json.data && (json = json.data);
                        success && success(json)
                    }
                },
                error: function (text) {
                    console.error((new Date).getTime(), "upload =>", text);
                    error && error(text)
                }
            })
        }

        function openDlg(uploadCb, camera, inFile) {
            if (inFile) {
                if (/bmp|gif|png|jpeg|jpg/i.test(inFile.type)) {
                    uploadCb(inFile)
                } else {
                    $.showinfo($.i18n("不支持的图片格式：") + inFile.type, 2e3)
                }
                return
            }
            if (!input) {
                console.warn("文件输入框不存在");
                return
            }
            if (camera == "camera") {
                input.setAttribute("capture", "camera")
            } else {
                input.removeAttribute("capture")
            }
            input.onchange = function () {
                var file = this.files[0];
                if (!file) {
                    console.log("没有打开图片!");
                    return
                }
                if (!file.type || /bmp|gif|png|jpeg|jpg/i.test(file.type)) {
                    uploadCb(file)
                } else {
                    $.showinfo($.i18n("不支持的图片格式：") + file.type, 2e3)
                }
                form.reset()
            };
            input.click()
        }

        function getImgAuth(cb) {
            var state = store.getState();
            auth = state.guest.auth;
            if (!auth) {
                if (!sid || !aid || !oid || !authurl || !upurl) {
                    sid = state.site.id;
                    aid = state.site.aid || state.site.p_id;
                    oid = state.guest.id;
                    authurl = state.system.url.poll
                }
                $.ajax(authurl, {
                    method: "post",
                    data: JSON.stringify({
                        site: sid,
                        account: aid,
                        visitor: oid,
                        avatar: state.guest.avatar,
                        nickname: state.guest.nickname || ""
                    }),
                    success: function (text) {
                        var json = JSON.parse(text);
                        if (!json.o_error) {
                            auth = state.guest.auth = json.authorization;
                            getImgAuth(cb)
                        } else {
                            console.error(json.o_errmsg)
                        }
                    }
                });
                return
            }
            cb(state.system.url.upurl);
            return;
            $.ajax(upurl, {
                data: {auth: auth}, success: function (text) {
                    try {
                        var ret = JSON.parse(text);
                        if (ret.o_error) {
                            throw new Error(text)
                        }
                        cb(ret.url.replace(/^http:/i, "") + "?sign=" + (ret.sign || ret.authorization))
                    } catch (e) {
                        console.error(text);
                        $.showinfo($.i18n("获取上传权限失败"), 2e3)
                    }
                }, error: function (text) {
                    console.error(text);
                    $.showinfo($.i18n("获取上传权限失败"), 2e3)
                }
            })
        }

        return _upload
    }();
    var emoji = function () {
        var emotions = {
            "/::)": "2009426204649332.gif",
            "/::~": "2009426204432159.gif",
            "/::B": "2009426203942933.gif",
            "/::|": "2009426203927717.gif",
            "/:8-)": "2009426204332318.gif",
            "/::<": "2009426204655335.gif",
            "/::$": "2009426204421181.gif",
            "/::X": "2009426203947115.gif",
            "/::Z": "200942620473854.gif",
            "/::'(": "2009426204336398.gif",
            "/::-|": "200942620363243.gif",
            "/::@": "2009426204453127.gif",
            "/::P": "2009426203918413.gif",
            "/::D": "2009426203834799.gif",
            "/::O": "2009426204438161.gif",
            "/::(": "2009426204457207.gif",
            "/::+": "2009426203631924.gif",
            "/:--b": "2009426203826955.gif",
            "/::Q": "2009426204343825.gif",
            "/::T": "2009426204710696.gif",
            "/:,@P": "2009426203535123.gif",
            "/:,@-D": "2009426203845213.gif",
            "/::d": "2009426204559431.gif",
            "/:,@o": "2009426203626168.gif",
            "/::g": "200942620382405.gif",
            "/:|-)": "2009426204511756.gif",
            "/::!": "200942620476841.gif",
            "/::L": "2009426203612485.gif",
            "/::>": "2009426204554209.gif",
            "/::,@": "2009426204725241.gif",
            "/:,@f": "2009426203552327.gif",
            "/::-S": "2009426203643426.gif",
            "/:?": "2009426204716110.gif",
            "/:,@x": "2009426204121698.gif",
            "/:,@@": "2009426203543362.gif",
            "/::8": "200942620387292.gif",
            "/:,@!": "200942620463342.gif",
            "/:!!!": "2009426204614878.gif",
            "/:xx": "2009426204353991.gif",
            "/:bye": "2009426203520915.gif",
            "/:wipe": "200942620400917.gif",
            "/:dig": "2009426203524599.gif",
            "/:handclap": "2009426203744593.gif",
            "/:&-(": "2009426204347115.gif",
            "/:B-)": "2009426203640891.gif",
            "/:<@": "2009426204611216.gif",
            "/:@>": "2009426203823435.gif",
            "/::-O": "2009426204626462.gif",
            "/:>-|": "2009426204413198.gif",
            "/:P-(": "2009426204519320.gif",
            "/::'|": "2009426204634617.gif",
            "/:X-)": "200942620370827.gif",
            "/::*": "2009426204719604.gif",
            "/:@x": "2009426204623279.gif",
            "/:8*": "200942620415695.gif",
            "/:pd": "2009426203922939.gif",
            "/:<W>": "2009426203856297.gif",
            "/:beer": "2009426204630345.gif",
            "/:basketb": "2009426204326116.gif",
            "/:oo": "2009426203723341.gif",
            "/:coffee": "2009426204523765.gif",
            "/:eat": "2009426203556965.gif",
            "/:pig": "2009426203837308.gif",
            "/:rose": "2009426203656691.gif",
            "/:fade": "2009426204619707.gif",
            "/:showlove": "200942620410615.gif",
            "/:heart": "2009426203954405.gif",
            "/:break": "2009426203619609.gif",
            "/:cake": "200942620408116.gif",
            "/:li": "2009426203647674.gif",
            "/:bome": "2009426204329724.gif",
            "/:kn": "2009426204551293.gif",
            "/:footb": "2009426203714788.gif",
            "/:ladybug": "2009426203740796.gif",
            "/:shit": "2009426204731691.gif",
            "/:moon": "2009426204640960.gif",
            "/:sun": "2009426203720577.gif",
            "/:gift": "2009426203930309.gif",
            "/:hug": "2009426204417246.gif",
            "/:strong": "200942620450530.gif",
            "/:weak": "2009426204112537.gif",
            "/:share": "2009426203539512.gif",
            "/:v": "2009426204646794.gif",
            "/:@)": "2009426204728451.gif",
            "/:jj": "2009426203737612.gif",
            "/:@@": "2009426203910315.gif",
            "/:bad": "200942620367378.gif",
            "/:lvu": "2009426203528732.gif",
            "/:no": "2009426204721695.gif",
            "/:ok": "200942620396799.gif",
            "/:love": "2009426205315887.gif",
            "/:<L>": "2009426205314448.gif",
            "/:jump": "2009426203559585.gif",
            "/:shake": "2009426205315218.gif",
            "/:<O>": "2009426205315310.gif",
            "/:circle": "2009426205315736.gif",
            "/:kotow": "2009426204734202.gif",
            "/:turn": "2009426205315546.gif",
            "/:skip": "2009426205315951.gif",
            "/:oY": "2009426205315383.gif",
            "/:#-0": "2009426205315324.gif",
            "/:hiphot": "2009426205315991.gif",
            "/:kiss": "2009426205315888.gif",
            "/:<&": "2009426205315937.gif",
            "/:&>": "2009426205315143.gif"
        };
        return function (key, classNames) {
            var baseUrl = "//" + $.autoHost("static") + "/images/emoji/qq/";
            if (!key) {
                return {emotions: emotions, baseUrl: baseUrl}
            } else if (!classNames) {
                return key in emotions && baseUrl + emotions[key]
            } else {
                return key.replace(/\/:[^\/]{0,8}/g, function (key) {
                    for (var i = key.length; i > 2; i--) {
                        if (key.substr(0, i) in emotions) {
                            return '<img class="' + classNames + '" src="' + baseUrl + emotions[key.substr(0, i)] + '"/>' + key.substr(i)
                        }
                    }
                    return key
                })
            }
        }
    }();
    var emojiZh = function () {
        var emotions = {
            "[微笑]": "2009426204649332.gif",
            "[撇嘴]": "2009426204432159.gif",
            "[色]": "2009426203942933.gif",
            "[发呆]": "2009426203927717.gif",
            "[得意]": "2009426204332318.gif",
            "[流泪]": "2009426204655335.gif",
            "[害羞]": "2009426204421181.gif",
            "[闭嘴]": "2009426203947115.gif",
            "[睡]": "200942620473854.gif",
            "[大哭]": "2009426204336398.gif",
            "[尴尬]": "200942620363243.gif",
            "[发怒]": "2009426204453127.gif",
            "[调皮]": "2009426203918413.gif",
            "[呲牙]": "2009426203834799.gif",
            "[惊讶]": "2009426204438161.gif",
            "[难过]": "2009426204457207.gif",
            "[酷]": "2009426203631924.gif",
            "[冷汗]": "2009426203826955.gif",
            "[抓狂]": "2009426204343825.gif",
            "[吐]": "2009426204710696.gif",
            "[偷笑]": "2009426203535123.gif",
            "[愉快]": "2009426203845213.gif",
            "[白眼]": "2009426204559431.gif",
            "[傲慢]": "2009426203626168.gif",
            "[饥饿]": "200942620382405.gif",
            "[困]": "2009426204511756.gif",
            "[惊恐]": "200942620476841.gif",
            "[流汗]": "2009426203612485.gif",
            "[憨笑]": "2009426204554209.gif",
            "[悠闲]": "2009426204725241.gif",
            "[奋斗]": "2009426203552327.gif",
            "[咒骂]": "2009426203643426.gif",
            "[疑问]": "2009426204716110.gif",
            "[嘘]": "2009426204121698.gif",
            "[晕]": "2009426203543362.gif",
            "[疯了]": "200942620387292.gif",
            "[衰]": "200942620463342.gif",
            "[骷髅]": "2009426204614878.gif",
            "[敲打]": "2009426204353991.gif",
            "[再见]": "2009426203520915.gif",
            "[擦汗]": "200942620400917.gif",
            "[抠鼻]": "2009426203524599.gif",
            "[鼓掌]": "2009426203744593.gif",
            "[糗大了]": "2009426204347115.gif",
            "[坏笑]": "2009426203640891.gif",
            "[左哼哼]": "2009426204611216.gif",
            "[右哼哼]": "2009426203823435.gif",
            "[哈欠]": "2009426204626462.gif",
            "[鄙视]": "2009426204413198.gif",
            "[委屈]": "2009426204519320.gif",
            "[快哭了]": "2009426204634617.gif",
            "[阴险]": "200942620370827.gif",
            "[亲亲]": "2009426204719604.gif",
            "[吓]": "2009426204623279.gif",
            "[可怜]": "200942620415695.gif",
            "[菜刀]": "2009426203922939.gif",
            "[西瓜]": "2009426203856297.gif",
            "[啤酒]": "2009426204630345.gif",
            "[篮球]": "2009426204326116.gif",
            "[乒乓]": "2009426203723341.gif",
            "[咖啡]": "2009426204523765.gif",
            "[饭]": "2009426203556965.gif",
            "[猪头]": "2009426203837308.gif",
            "[玫瑰]": "2009426203656691.gif",
            "[凋谢]": "2009426204619707.gif",
            "[嘴唇]": "200942620410615.gif",
            "[爱心]": "2009426203954405.gif",
            "[心碎]": "2009426203619609.gif",
            "[蛋糕]": "200942620408116.gif",
            "[闪电]": "2009426203647674.gif",
            "[炸弹]": "2009426204329724.gif",
            "[刀]": "2009426204551293.gif",
            "[足球]": "2009426203714788.gif",
            "[瓢虫]": "2009426203740796.gif",
            "[便便]": "2009426204731691.gif",
            "[月亮]": "2009426204640960.gif",
            "[太阳]": "2009426203720577.gif",
            "[礼物]": "2009426203930309.gif",
            "[拥抱]": "2009426204417246.gif",
            "[强]": "200942620450530.gif",
            "[弱]": "2009426204112537.gif",
            "[握手]": "2009426203539512.gif",
            "[胜利]": "2009426204646794.gif",
            "[抱拳]": "2009426204728451.gif",
            "[勾引]": "2009426203737612.gif",
            "[拳头]": "2009426203910315.gif",
            "[差劲]": "200942620367378.gif",
            "[爱你]": "2009426203528732.gif",
            "[NO]": "2009426204721695.gif",
            "[OK]": "200942620396799.gif",
            "[爱情]": "2009426205315887.gif",
            "[飞吻]": "2009426205314448.gif",
            "[跳跳]": "2009426203559585.gif",
            "[发抖]": "2009426205315218.gif",
            "[怄火]": "2009426205315310.gif",
            "[转圈]": "2009426205315736.gif",
            "[磕头]": "2009426204734202.gif",
            "[回头]": "2009426205315546.gif",
            "[跳绳]": "2009426205315951.gif",
            "[投降]": "2009426205315383.gif",
            "[激动]": "2009426205315324.gif",
            "[乱舞]": "2009426205315991.gif",
            "[献吻]": "2009426205315888.gif",
            "[左太极]": "2009426205315937.gif",
            "[右太极]": "2009426205315143.gif"
        };
        return function (key, classNames) {
            var baseUrl = "//" + $.autoHost("static") + "/images/emoji/qq/";
            if (!key) {
                return {emotions: emotions, baseUrl: baseUrl}
            } else if (!classNames) {
                return key in emotions && baseUrl + emotions[key]
            } else {
                return key.replace(/\[[NOK\u4E00-\u9FA5\uF900-\uFA2D]{1,3}\]/g, function (key) {
                    for (var i = key.length; i > 2; i--) {
                        if (key.substr(0, i) in emotions) {
                            return '<img class="' + classNames + '" src="' + baseUrl + emotions[key.substr(0, i)] + '"/>' + key.substr(i)
                        }
                    }
                    return key
                })
            }
        }
    }();
    var showinfo = function () {
        var infoBox, infoTimer;
        return function showInfo(text, quick) {
            if (!text) {
                return
            }
            if (!infoBox) {
                infoBox = $("#alert")[0];
                if (!infoBox) {
                    return
                }
                infoBox.onclick = hideInfo
            }
            infoBox.innerHTML = $.i18n(text);
            infoBox.style.display = "block";
            infoTimer && (infoTimer = clearTimeout(infoTimer));
            infoTimer = setTimeout(function () {
                hideInfo.call(infoBox)
            }, quick || 2e3)
        };

        function hideInfo() {
            this.style.display = ""
        }
    }();
    var emojiOutChange = function () {
        return function (text) {
            var emotionsZh = emojiZh().emotions, emojisZhArr = $.keys(emojiZh().emotions),
                emojisArr = $.keys(emoji().emotions);
            return text.replace(/\[[NOK\u4E00-\u9FA5\uF900-\uFA2D]{1,3}\]/g, function (key) {
                var idx = emojisZhArr.indexOf(key);
                return ~idx ? emojisArr[idx] : key
            })
        }
    }();
    var emojiInChange = function () {
        return function (text) {
            var emotions = emoji().emotions, emojisZhArr = $.keys(emojiZh().emotions),
                emojisArr = $.keys(emoji().emotions);
            return text.replace(/\/:[^\/]{0,8}/g, function (key) {
                for (var i = key.length; i > 2; i--) {
                    if (key.substr(0, i) in emotions) {
                        var it = key.substr(0, i);
                        var idx = emojisArr.indexOf(it);
                        return (~idx ? emojisZhArr[idx] : it) + key.substr(i)
                    }
                }
                return key
            })
        }
    }();
    $.extend(consts);
    $.extend(adapt);
    $.extend(audio);
    $.extend({
        emoji: emoji,
        emojiZh: emojiZh,
        emojiOutChange: emojiOutChange,
        emojiInChange: emojiInChange,
        io: io,
        stamp: stamp,
        report: report,
        sendid: sendid,
        upload: upload,
        showinfo: showinfo,
        outBarHeight: window.screen.height - document.body.clientHeight
    });
    $.extend({
        setProtocol: function (url) {
            if (url && /^https?:/i.test(url)) {
                url = url.replace(/^https?:/, "")
            }
            return url
        }, httpsEnable: function (url) {
            if (url && (!/https/i.test(window.location.protocol) || /^(https:)?\/\//i.test(url) || /(https?:)?\/\/[a-z0-9A-Z\.]*v5kf\.com/i.test(url) || /\/?lib\/files\/images/i.test(url))) {
                return true
            }
            return false
        }
    });
    $.addEvent = function () {
        if (document.addEventListener) {
            return function (el, type, fn) {
                el.addEventListener(type, fn, false)
            }
        } else {
            return function (el, type, fn) {
                el.attachEvent("on" + type, function () {
                    return fn.call(el, window.event)
                })
            }
        }
    }();
    $.removeEvent = function () {
        if (document.removeEventListener) {
            return function (el, type, fn) {
                el.removeEventListener(type, fn, false)
            }
        } else {
            return function (el, type, fn) {
                el.detachEvent("on" + type, function () {
                    return fn.call(el, window.event)
                })
            }
        }
    }();
    var store = function () {
        var state = {}, reducers = {}, actions = {}, listeners = {};
        return {handle: handle, dispatch: dispatch, connect: connect, getState: getState, action: action};

        function getState() {
            return state
        }

        function handle(action, dft) {
            if ($.type(action) === "object") {
                for (var key in action) {
                    if (action.hasOwnProperty(key)) {
                        var reducer = action[key];
                        if ($.type(reducer) === "function") {
                            if (!(key in reducers)) {
                                reducers[key] = []
                            }
                            $.cull(reducers, reducer);
                            reducers[key].push(reducer)
                        }
                    }
                }
            }
            if (dft && $.type(dft) === "object") {
                var oldstate = state;
                state = $.extend({}, state, dft)
            }
        }

        function action(action, cb) {
            if (!(action in actions)) {
                actions[action] = []
            }
            actions[action].push(cb)
        }

        function dispatch(action, payload) {
            if (action in reducers) {
                var cbs = reducers[action], oldstate = state;
                for (var i = 0, len = cbs.length; i < len; i++) {
                    state = cbs[i](state, payload)
                }
                subscribe(state, oldstate)
            }
            if (action in actions) {
                var cbs = actions[action];
                for (var i = 0, len = cbs.length; i < len; i++) {
                    cbs[i](payload)(dispatch, getState)
                }
            }
        }

        function subscribe(st, old, node) {
            node || (node = listeners);
            var cbs;
            if ((cbs = node.__cb__) && st !== old) {
                for (var i = 0, len = cbs.length; i < len; i++) {
                    cbs[i](st, old, state)
                }
            }
            for (var key in node) {
                if (key !== "__cb__") {
                    subscribe((st || {})[key], (old || {})[key], node[key])
                }
            }
        }

        function connect(path, cb) {
            $.type(path) === "array" || (path = path.split("."));
            var dir, node = listeners;
            for (var i = 0, len = path.length; i < len; i++) {
                dir = path[i];
                if (!(dir in node)) {
                    node[dir] = {}
                }
                node = node[dir]
            }
            node.__cb__ || (node.__cb__ = []);
            node.__cb__.push(cb)
        }
    }();
    store.connect("system.compatible", function (st, old, state) {
        if (!st) {
            $("warn").html("您的浏览器较旧，请使用更新的浏览器打开此页面。如果您使用的是双核浏览器中的IE7模式，请切换到急速内核后，刷新此页面。");
            $("warn").tabClass("hide", true)
        }
    });
    store.connect("system.exit", function (st, old, state) {
        state.system.ws && state.system.wsstate && state.system.ws.close()
    });
    store.connect("system.voice", function (voice, old, state) {
        $("#voice").html(voice ? "&#xE801;" : "&#xE804;");
        if ($.isPhone) $("#voiceMbBtn").html(voice ? "&#xE81C;" : "&#xE81D;"); else $("#voiceMbBtn").html(voice ? "&#xE801;" : "&#xE804;");
        $("#voiceMbBtn").attr("title", voice ? $.i18n("提示音") : $.i18n("已静音"));
        $("#muteBtn").html(voice ? "&#xE801;" : "&#xE804;");
        state.guest && state.guest.id && $.cache(state.guest.id + "_voice", voice.toString())
    });
    store.connect("system.env", function (magic) {
        $.magic = ($.magic || []).concat(magic)
    });
    store.connect("system.magic", function (magic) {
        $.magic = ($.magic || []).concat(magic)
    });
    store.connect("system.real", function (real) {
        $.real = Object.assign($.real || {}, real)
    });
    store.connect("system.human", function (json) {
        if (json == 1 || json && json.human == 1) {
            store.dispatch("GET_HUMAN", json)
        }
    });
    store.connect("system.reconn", function (flag, old, state) {
        if (flag) {
            setTimeout(function () {
                store.dispatch("OPEN_WS")
            }, 200)
        } else {
        }
    });
    store.connect("system.wsstate", function (st) {
        if (st) {
            $("#recon").addClass("hide")
        } else {
            $("#recon").rmClass("hide");
            $("#text")[0].blur();
            $(".pnl-left").css("bottom", "0em");
            if ($.disconType == 1) {
                $("#closeTip")[0].innerHTML = $.i18n("因长时间未活动会话已关闭")
            } else if ($.disconType == 2) {
                $("#closeTip")[0].innerHTML = $.i18n("对话已在新窗口打开")
            } else {
                $("#closeTip")[0].innerHTML = $.i18n("当前会话已中断")
            }
        }
    });
    var _instance;

    function Slider(parentNode, imgArr, clickCb, options) {
        this.parent = parentNode;
        this.data = imgArr;
        this.clickListener = clickCb;
        this.index = 0;
        this.options = Object.assign({automove: true, duration: 3500}, options);
        if (!parentNode) {
            return
        }
        if (!imgArr || imgArr.length < 1) {
            parentNode.className += " hide";
            return
        }
        if (_instance) {
            _instance.destory()
        }
        _instance = this;
        var slider = document.createElement("ul");
        slider.className = "slider anim";
        var indicator = document.createElement("ul");
        indicator.className = "indicator anim";
        parentNode.className = parentNode.className.replace(/\s*hide/g, "");
        if (imgArr.length > 1) {
            $.each(imgArr, function (i, item) {
                var sli = document.createElement("li");
                sli.setAttribute("index", i);
                sli.className = "slider-item";
                sli.style.backgroundImage = "url(" + $.setProtocol(item.img) + ")";
                sli.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(item.img)) + ',sizingMethod="scale")';
                sli.style.width = (parentNode.clientWidth || 208) + "px";
                var a = document.createElement("a");
                a.target = "_blank";
                a.href = item.link;
                a.appendChild(sli);
                slider.appendChild(a);
                var fli = document.createElement("li");
                fli.setAttribute("index", i);
                fli.className = "focus-item";
                indicator.appendChild(fli)
            })
        }
        var sli = document.createElement("li");
        sli.setAttribute("index", 0);
        sli.className = "slider-item";
        sli.style.backgroundImage = "url(" + $.setProtocol(imgArr[0].img) + ")";
        sli.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(imgArr[0].img)) + ',sizingMethod="scale")';
        sli.style.width = (parentNode.clientWidth || 208) + "px";
        slider.appendChild(sli);
        console.log("parentNode.clientWidth:", parentNode.clientWidth, parentNode.clientHeight);
        slider.style.width = (imgArr.length + 1) * (parentNode.clientWidth || 208) + "px";
        slider.style.left = "0px";
        parentNode.appendChild(slider);
        if (imgArr.length > 1) {
            indicator.children[0].className += " active";
            parentNode.appendChild(indicator)
        }
        this.indicator = indicator;
        this.slider = slider;
        $.addEvent(slider, "click", clickDelegate);
        $.addEvent(indicator, "click", clickDelegate);
        $.addEvent(this.parent, "mouseover", this.stop);
        $.addEvent(this.parent, "mouseout", this.start);
        setTimeout(function () {
            parentNode.className
        }, 50)
    }

    function clickDelegate(e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (/slider-item/.test(target.className)) {
            _instance && _instance.clickListener(parseInt(target.getAttribute("index")))
        } else if (/focus-item/.test(target.className)) {
            _instance && _instance.goto.call(_instance, parseInt(target.getAttribute("index")))
        }
    }

    function indicatorUpdate(indicator, index) {
        $.each(indicator.children, function (i, item) {
            item.className = "focus-item"
        });
        indicator.children[index].className = "focus-item active"
    }

    function animate(slider, newIdx) {
        var width = _instance.parent.clientWidth, len = _instance.data.length, allW = width * len;
        if (newIdx === 0) {
            slider.style.left = -allW + "px";
            setTimeout(function () {
                slider.className = slider.className.replace(/\s*anim/g, "");
                slider.style.left = "0px";
                setTimeout(function () {
                    slider.className += " anim"
                }, 350)
            }, 350)
        } else {
            slider.style.left = -width * newIdx + "px"
        }
    }

    Slider.prototype = {
        constructor: Slider, start: function () {
            var _this = _instance;
            if (_this.data.length > 1 && _this.options.automove) {
                if (_this.timer) {
                    clearInterval(_this.timer)
                }
                _this.timer = setInterval(function () {
                    _this.next()
                }, _this.options.duration || 4e3)
            }
            return _this
        }, stop: function () {
            _instance && clearInterval(_instance.timer)
        }, next: function () {
            this.index += 1;
            if (this.index >= this.data.length) {
                this.index = 0
            }
            animate(this.slider, this.index);
            indicatorUpdate(this.indicator, this.index)
        }, prev: function () {
            this.index -= 1;
            if (this.index < 0) {
                this.index = this.data.length - 1
            }
            animate(this.slider, this.index);
            indicatorUpdate(this.indicator, this.index)
        }, goto: function (clickIndex) {
            var offset = this.index - clickIndex;
            this.index = clickIndex;
            animate(this.slider, this.index);
            indicatorUpdate(this.indicator, this.index)
        }, destory: function () {
            if (this.parent) {
                while (this.parent.hasChildNodes()) {
                    $.removeEvent(this.parent.firstChild, "click", clickDelegate);
                    this.parent.removeChild(this.parent.firstChild)
                }
            }
            $.removeEvent(this.parent, "mouseover", this.stop);
            $.removeEvent(this.parent, "mouseout", this.start);
            clearInterval(this.timer);
            this.slider = null;
            this.indicator = null;
            _instance = null
        }, hide: function () {
            this.destory()
        }
    };

    function SliderBuilder(parentNode, imgArr, clickCb) {
        if (_instance && _instance.parent) {
            return _instance
        }
        return new Slider(parentNode, imgArr, clickCb)
    }

    store.connect("site.banner", function (st, old, state) {
        if (!st) {
            return
        }
        $(".banner").html(st);
        $(".msg-banner").rmClass("hide")
    });
    store.connect("site.sliders", function (st, old, state) {
        if (!st || st.length < 1) {
            return
        }
        if (state.system.embed && state.system.diatpl < 3 || $.isPhone) {
            return
        }
        SliderBuilder($(".slider-container")[0], st, function (index) {
        }).start();
        $(".slider-container + div").css("top", $(".slider-container")[0].clientHeight + "px")
    });
    store.connect("site.copyright", function (st, old, state) {
        if (!st) {
            return
        }
        var copyright = $("#copyright");
        var copyrightSm = $("#copyrightSm");
        if (!st.display) {
            copyright.addClass("hide");
            copyrightSm.addClass("hide")
        } else if (st.content) {
            copyright[0].innerHTML = "<a>" + st.content + "</a>";
            copyrightSm[0].innerHTML = "<a>" + st.content + "</a>"
        }
    });
    store.connect("site.hotQues", function (st, old, state) {
        var quesBtn = $("#quesBtn");
        if (!st || st.length === 0) {
            quesBtn.addClass("hide");
            return
        }
        quesBtn.rmClass("hide");
        var quesDiv = $("#ques");
        quesDiv.html("");
        for (var i = 0; i < st.length; i++) {
            var li = document.createElement("li");
            li.className = "q-item";
            li.innerHTML = st[i];
            quesDiv.append(li)
        }
        var relsCon = $("#hots");
        relsCon.html("");
        for (var i = 0; i < st.length; i++) {
            var li = document.createElement("li");
            li.className = "rel-item";
            li.title = $.i18n("点击发送问题");
            li.innerHTML = $.emojiInChange(st[i] + "");
            relsCon.append(li)
        }
        $(".q-item").on("touchstart", function () {
            $(this).css("opacity", ".6")
        });
        $(".q-item").on("touchend", function () {
            $(this).css("opacity", "1")
        })
    });
    store.connect("site", function (st, old, state) {
        if (!state.system || !st) {
            return
        }
        if (!state.system.embed || state.system.diatpl == 4) {
            if (st.name) {
                $("#siteName").html(st.name);
                document.title = $.i18n("会话_") + st.name;
                $(".chat-title")[0].innerHTML = st.name
            }
            var homeUrl = st.home && (/^(https?:)?\/\//.test(st.home) ? st.home : "//" + st.home);
            $("#about").html("<h4>" + (st.name || $.i18n("智能客服系统")) + "</h4><p>" + (st.about || $.i18n("智能客服向前一小步、客服工作向前一大步。")) + '</p><ul class="about-links">' + (st.tel ? '<li class="about-link"><i class="kh">&#xe805;</i><span>' + st.tel + "</span></li>" : "") + (homeUrl ? '<li class="about-link"><i class="kh">&#xe80f;</i><a href="' + homeUrl + '" target="_blank">' + st.home.replace(/(https?:)?\/\//i, "") + "</a></li>" : "") + "</ul>")
        } else {
            $("#workerSite").html(st.name || st.id);
            $(".pnl-worker-text").addClass(st.name ? "has-site" : "unsite")
        }
    });
    store.connect("site.webChatBtns", function (st, old, state) {
        var middleW, btnsW = 0;
        if (Object.toString(st.btns) === "[]" || Object.toString(st.btns) === "{}") return;
        var len = st.btns.length, str = "";
        for (var i = 0; i < len; i++) {
            str += '<span class="diy-btn" data-time="0" data-click="2" title="' + st.btns[i].title + '" value="' + st.btns[i].value + '" index="' + i + '" type="' + st.btns[i].type + '">' + st.btns[i].title + "</span>"
        }
        $("#show").addClass("diy-chat-btn");
        $(".diy-middle").html(str);
        $(".pnl-diy").rmClass("hide");
        setTimeout(function () {
            middleW = $(".diy-middle")[0].clientWidth;
            for (var i = 0; i < len; i++) {
                btnsW += $(".diy-btn")[i].clientWidth + Math.round(12 * .1 + middleW * .01);
                if (btnsW > middleW) {
                    btnsW = $(".diy-btn")[i].clientWidth + Math.round(12 * .1 + middleW * .01);
                    st.index.push(i - 1)
                } else if (btnsW === middleW) {
                    btnsW = 0;
                    st.index.push(i)
                }
            }
            if (st.index.length > 0) {
                $(".diy-right").rmClass("hide")
            }
        }, 100)
    });
    store.connect("site.defHot", function (st, old, state) {
        $("#defHot").html('<div class="defhot-box">' + '<div class="defhot-main">' + '<div class="defhot-img' + (st.price ? " info-price" : "") + '">' + '<img class="defhot-img-url" src="' + st.img + '">' + "</div>" + '<div class="defhot-info">' + '<div class="defhot-info-title" title="' + st.name + '">' + st.name + "</div>" + '<div class="defhot-info-desc' + (st.price ? " info-price" : "") + '" title="' + st.desc + '">' + st.desc + "</div>" + (st.price ? '<div class="defhot-info-price">' + st.price + "</div>" : "") + "</div>" + '<div class="defhot-btn">' + '<div class="defhot-btn-send" id="defHotBtn" href="' + st.link + '">' + st.btn + "</div>" + "</div>" + "</div>" + "</div>");
        !sessionStorage[store.getState().site.id + "_defHot"] && $("#defHot").rmClass("hide");
        if (store.getState().current.wid && sessionStorage[store.getState().site.id + "_defHot"]) {
            $("#defHot").addClass("hide")
        }
        $("#defHotBtn").click(defHotClick)
    });
    store.connect("site.header", function (st, old, state) {
        if (!state.system.embed || state.system.diatpl == 4) {
            st.img && !$.ua.isMobile && $("#pnlHead").css({
                "background-image": "url(" + $.setProtocol(st.img) + ")",
                filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : st.img) + ",sizingMethod=scale)"
            });
            st.smimg && $("#pnlHeadSm").css("background-image", "url(" + $.setProtocol(st.smimg) + ")");
            if (st.color && !(state.site.theme && state.site.theme.color)) {
                var isWhite = st.color.toLowerCase() == "#fff" || st.color.toLowerCase() == "#ffffff" || st.color.toLowerCase() == "rgb(255, 255, 255)" || st.color.toLowerCase() == "rgba(255, 255, 255, 1)";
                $("#pnlHead").css("background-color", $.autoColor(st.color));
                $("#pnlHeadSm").css("background-color", $.autoColor(st.smcolor || st.color));
                $("#submit").css("background-color", $.autoColor(st.color));
                isWhite && $("#submit").css("border", "1px solid");
                $("#sendBtn").css("color", $.autoColor(st.smcolor || st.color));
                if ($.isDarkColor(st.color)) {
                    $(".pnl-opt").css("color", "#ececec");
                    $(".pnl-site").css("color", "#ececec");
                    $("#submit").css("color", "#fff")
                } else {
                    $(".pnl-about").css("color", "#536171");
                    $(".pnl-opt").css("color", "#536171");
                    $(".pnl-site").css("color", "#536171");
                    $(".pnl-worker-name").css("color", "#536171");
                    $("#submit").css("color", "#536171");
                    $(".pnl-worker-name").css("color", "#536171");
                    $(".pnl-worker-site").css("color", "#858a90")
                }
            }
            if (!$.ua.isMobile && !($.ua.isIe && $.ua.ie < 9)) {
                st.bodybg && ($(".body-bg")[0].src = $.setProtocol(st.bodybg))
            }
        }
        if ((!state.system.embed || state.system.diatpl == 4) && !$.ua.isMobile && st.logo) {
            st.logo && ($("#siteLogo")[0].src = $.setProtocol(st.logo));
            $("#siteLogo").rmClass("hide");
            $("#siteName").addClass("hide")
        } else {
            $("#siteLogo").addClass("hide");
            $("#siteName").rmClass("hide")
        }
    });
    store.connect("site.control", function (st, old, state) {
        if (st) {
            var show_faq = st.show_faq === undefined || st.show_faq,
                show_related = st.show_related === undefined || st.show_related,
                show_worker = st.show_worker === undefined || st.show_worker;
            if (!show_faq) {
                $("#quesBtn").css("display", "none");
                $(".pnl-hot").css("display", "none")
            }
            if (!show_related) {
                $("#quesBtn").css("display", "none");
                $(".pnl-rel").css("display", "none");
                $("#rel-tab").addClass("hide")
            }
            if (!show_worker) {
                $("#humanBtn").css("display", "none")
            }
        }
    });
    store.connect("site.theme", function (st, old, state) {
        if (st && st.color) {
            var isWhite = st.color.toLowerCase() == "#fff" || st.color.toLowerCase() == "#ffffff" || st.color.toLowerCase() == "rgb(255, 255, 255)" || st.color.toLowerCase() == "rgba(255, 255, 255, 1)";
            $("#pnlHead").css("background-color", $.autoColor(st.color));
            $("#pnlHeadSm").css("background-color", $.autoColor(st.color));
            $("#submit").css("background-color", $.autoColor(st.color));
            isWhite && $("#submit").css("border", "1px solid");
            $("#sendBtn").css("color", $.autoColor(st.color));
            if ($.isDarkColor(st.color)) {
                $(".pnl-opt").css("color", "#ececec");
                $(".pnl-site").css("color", "#ececec")
            } else {
                $(".pnl-about").css("color", "#536171");
                $(".pnl-opt").css("color", "#536171");
                $(".pnl-site").css("color", "#536171");
                $(".pnl-worker-name").css("color", "#536171");
                $(".pnl-worker-site").css("color", "#858a90");
                $("#submit").css("color", "#536171")
            }
        }
    });
    $.getColorRBG = function (color) {
        var $R, $G, $B, $A;
        if (color && /^#?[0-9a-f]{3,6}$/i.test(color)) {
            var RgbValue = color.replace("#", "");
            if (RgbValue.length === 3) {
                $R = parseInt(RgbValue[0] + RgbValue[0], 16);
                $G = parseInt(RgbValue[1] + RgbValue[1], 16);
                $B = parseInt(RgbValue[2] + RgbValue[2], 16)
            } else if (RgbValue.length === 6) {
                $R = parseInt(RgbValue[0] + RgbValue[1], 16);
                $G = parseInt(RgbValue[2] + RgbValue[3], 16);
                $B = parseInt(RgbValue[4] + RgbValue[5], 16)
            } else {
            }
        } else if (color && /^rgb\([0-9]{1,3}, ?[0-9]{1,3}, ?[0-9]{1,3}\)/i.test(color)) {
            var RgbValue = color.replace("rgb(", "").replace(")", "");
            var RgbValueArry = RgbValue.split(",");
            if (RgbValueArry.length === 3 || RgbValueArry.length === 4) {
                $R = parseInt(RgbValueArry[0]);
                $G = parseInt(RgbValueArry[1]);
                $B = parseInt(RgbValueArry[2]);
                if (RgbValueArry.length === 4) {
                    $A = RgbValueArry[3]
                }
            }
        } else {
        }
        return [$R, $G, $B]
    };
    $.isDarkColor = function (color) {
        var RGB = $.getColorRBG(color);
        var grayLevel = RGB[0] * .299 + RGB[1] * .587 + RGB[2] * .114;
        if (grayLevel < 192) {
            return true
        }
        return false
    };
    $.getFontColor = function (color) {
        var RGB = $.getColorRBG(color);
        var grayLevel = RGB[0] * .299 + RGB[1] * .587 + RGB[2] * .114;
        if (grayLevel < 192) {
            return "#ececec"
        }
        return "#536171"
    };
    $.getDiffColor = function (color) {
        var RGB = $.getColorRBG(color);
        var r, g, b;
        r = RGB[0] * 1 + 10 > 255 ? 255 : RGB[0] * 1 + 10;
        g = RGB[1] * 1 + 10 > 255 ? 255 : RGB[1] * 1 + 10;
        b = RGB[2] * 1 + 10 > 255 ? 255 : RGB[2] * 1 + 10;
        return "rgb(" + r + "," + g + "," + b + ")"
    };

    function defHotClick() {
        var state = store.getState(), json = state.site.defHot, msg = {
            id: $.sendid(),
            dir: 1,
            type: 9,
            stamp: $.stamp(),
            json: {name: json.name, img: json.img, link: json.link, desc: json.desc}
        };
        !state.current.wid && store.dispatch("GET_HUMAN", Object.assign({}, state.system.human, {human: 1}));
        setTimeout(function () {
            store.dispatch("SEND_MSG", msg)
        }, 200);
        sessionStorage.setItem(store.getState().site.id + "_defHot", true);
        $("#defHot").addClass("hide")
    }

    var Msg = function () {
        function baseinfo(msg) {
            var state = store.getState(), site = state.site || {}, robot = state.robot || {}, guest = state.guest || {},
                worker = state.workers[msg.wid] || state.workers[state.current.wid] || {};
            return {
                robotname: robot.name,
                robotphoto: robot.logo,
                workername: worker.name,
                workerphoto: worker.photo,
                cstmname: guest.nickname,
                cstmphoto: guest.avatar
            }
        }

        function Msg(msg) {
            this.msg = msg;
            this.json = (msg || {}).json;
            this.err = ""
        }

        Msg.prototype.valid = function () {
            if (!this.msg) {
                this.err = "无法渲染不存在的消息";
                return false
            }
            if (!this.json) {
                this.err = "无法渲染不存在的内容";
                return false
            }
            return true
        };
        Msg.prototype.render = function (nowrap) {
            if (!this.valid()) {
                console.warn("消息数据不完整：" + this.err);
                return false
            }
            if (nowrap) {
                return this.build(nowrap)
            } else {
                return this.wrap()
            }
        };
        Msg.prototype.wrap = function () {
            var that = this, msg = this.msg, json = this.json, info = baseinfo(this.msg), photo, name;
            var dom = this.build();
            if (!dom) {
                return false
            }
            var div = document.createElement("div");
            div.className = "msg" + (msg.dir === 0 ? " worker" : msg.dir === 1 ? " guest" : " robot");
            div.id = this.msg.id;
            var dir = document.createElement("div");
            if (msg.type === $.msgTypes.NEWS) {
                dir.className = "msg-center post"
            } else {
                dir.className = "msg-" + (msg.type === 9 ? "center post" : msg.dir === 1 ? "right" : "left");
                if (msg.dir === 0) {
                    dir.setAttribute("worker", info.workername || $.i18n("人工客服"));
                    name = info.workername;
                    photo = info.workerphoto
                } else if (msg.dir === 1) {
                    dir.setAttribute("customer", info.cstmname || $.i18n("游客"));
                    name = info.cstmname;
                    photo = info.cstmphoto
                } else if (msg.dir === 2 || msg.dir === 14 || msg.dir === 9) {
                    dir.setAttribute("worker", info.robotname || $.i18n("机器人客服"));
                    name = info.robotname;
                    photo = info.robotphoto
                }
            }
            div.appendChild(dir);
            if (msg.type !== $.msgTypes.NEWS) {
                var ico = document.createElement("div");
                ico.className = "msg-host";
                ico.innerHTML = '<i class="ic kh">' + (msg.dir === 0 ? "&#xe80C;" : msg.dir === 2 || msg.dir === 14 ? "&#xe803;" : "&#xe80C;") + "</i>";
                dir.appendChild(ico);
                if (photo) {
                    var img = new Image;
                    img.onload = function () {
                        if (ico) {
                            ico.style.backgroundImage = "url(" + $.setProtocol(img.src) + ")";
                            ico.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(img.src)) + ',sizingMethod="scale")';
                            ico.className = "msg-host photo";
                            ico.innerHTML = ""
                        }
                    };
                    img.src = $.setProtocol(photo)
                }
                if (name) {
                    ico.title = name
                }
            }
            dir.appendChild(dom);
            return this.wrapper = div
        };
        return Msg
    }();
    var preview = function () {
        var imgdom, deg, imgbox = imgdom && imgdom.parentElement, left, top, drag, mapJsLoading, qqMaps;
        return function (opt) {
            switch (opt.type) {
                case"img":
                    prevImg(opt.src);
                    break;
                case"link":
                    prevLink(opt.link);
                    break;
                case"map":
                    loadMapJs();
                    setTimeout(function () {
                        prevMap(opt)
                    }, 300);
                    break
            }
        };

        function loadMapJs() {
            if (!mapJsLoading) {
                mapJsLoading = true;
                $.ajax("//map.qq.com/api/js?v=2.exp&key=6LLBZ-QMLCX-HNA4L-T3ADN-4O3V5-BFFLB&callback=initMapJs", {
                    jsonp: true,
                    success: function (res) {
                        console.log("qq map ajax success:", res)
                    },
                    error: function (e) {
                        console.log("qq map ajax error:", e)
                    }
                });
                window.initMapJs = function () {
                    qqMaps = window.qq && window.qq.maps;
                    if (qqMaps) {
                        window.initMapJs = null
                    }
                }
            }
        }

        function prevMap(json) {
            var st = store.getState();
            $("#mask").rmClass("hide");
            $("#prevMap").tabClass("hide", true);
            $(".prev-map-box").css({top: 0, left: 0, bottom: 0, right: 0});
            var center = new qq.maps.LatLng(json.json.lat, json.json.lng);
            var options = {center: center, zoom: json.json.scale};
            var map = new qq.maps.Map($(".prev-map-box")[0], options);
            var marker = new qq.maps.Marker({position: center, map: map});
            marker.setAnimation(qq.maps.MarkerAnimation.BOUNCE)
        }

        function prevImg(src) {
            var st = store.getState();
            if (/scrshot/i.test(src)) {
                $("#mask").rmClass("hide");
                $("#prev").tabClass("hide", true);
                $(".img-num").addClass("hide");
                $(".img-mid").addClass("hide");
                imgInit(src);
                imgdom && (imgdom.src = src)
            } else if (/data:/.test(src)) {
                if ($.isPhone) {
                    $("#mask").rmClass("hide");
                    $("#prev").tabClass("hide", true);
                    imgInit(src);
                    imgdom && (imgdom.src = src)
                } else if (st.system.embed) {
                    window.open(src, "_blank")
                } else {
                    $("#mask").rmClass("hide");
                    $("#prev").tabClass("hide", true);
                    imgInit(src);
                    imgdom && (imgdom.src = src)
                }
            } else if ($.httpsEnable(src)) {
                src = location.protocol + $.setProtocol(src);
                if ($.isPhone) {
                    $("#mask").rmClass("hide");
                    $("#prev").tabClass("hide", true);
                    imgInit(src);
                    imgdom && (imgdom.src = src)
                } else if (st.system.embed) {
                    window.open(src, "_blank")
                } else {
                    $("#mask").rmClass("hide");
                    $("#prev").tabClass("hide", true);
                    imgInit(src);
                    imgdom && (imgdom.src = src)
                }
            } else {
                window.open(src, "_blank")
            }
        }

        function prevLink(link) {
            var st = store.getState();
            if ($.httpsEnable(link) && !($.ua.isIe && $.ua.ie < 9)) {
                link = location.protocol + $.setProtocol(link);
                if (st.system.embed) {
                    window.open(link, "_blank")
                } else if ($.isPhone) {
                    if ($.isAndroid || $.isWeChat) {
                        $("#mask")[0].style.backgroundColor = "rgba(0,0,0,0)";
                        $("#iframe").tabClass("hide", true);
                        $("#mask").rmClass("hide");
                        $("#iframe").src(link)
                    }
                    if ($.isiOS && !$.isWeChat) {
                        window.open(link, "_self")
                    }
                } else {
                    $("#iframe").tabClass("hide", true);
                    $("#mask").rmClass("hide");
                    $("#iframe").src(link)
                }
            } else {
                window.open(link, "_blank")
            }
        }

        function imgInit(src, cb) {
            if (!imgdom) {
                imgdom = $("#previmg")[0];
                if (!imgdom) {
                    console.warn("未找到dom元素：#previmg");
                    return
                }
                imgbox = imgdom.parentElement;
                imgbox.parentElement.onmousewheel = imgbox.parentElement.onwheel = imgWheel;
                imgbox.parentElement.onmousedown = imgMousedown;
                imgbox.parentElement.onmousemove = imgMousemove;
                imgbox.parentElement.onmouseup = imgbox.parentElement.onmouseleave = imgMouseup;
                if ($.isPhone) {
                    imgdom.touchstart = imgMousedown;
                    imgdom.touchmove = imgMousemove;
                    imgdom.touchend = imgMouseup;
                    $(".img-zoom").rmClass("hide");
                    $("#zoomSmall").click(imgZoom);
                    $("#zoomLarge").click(imgZoom)
                }
                $("#rotWise").click(imgRotate);
                $("#rotAnti").click(imgRotate);
                $("#midPrev").click(imgToggle);
                $("#midNext").click(imgToggle);
                $("#prev").click(function (e) {
                    if (e.target && /^prev$/.test(e.target.id)) {
                        $.addClass($("#mask")[0], "hide")
                    }
                })
            }
            var img = new Image;
            img.onload = function () {
                deg = 0;
                left = window.innerWidth / 2;
                top = window.innerHeight / 2;
                if (imgdom) {
                    if (img.width > window.innerWidth || img.height > window.innerHeight) {
                        if (img.width / window.innerWidth > img.height / window.innerHeight) {
                            img.height *= window.innerWidth / img.width;
                            img.width = window.innerWidth
                        } else {
                            img.width *= window.innerHeight / img.height;
                            img.height = window.innerHeight
                        }
                    }
                    imgdom.width = img.width;
                    imgdom.style.margin = -(img.height / 2) + "px 0 0 -" + img.width / 2 + "px";
                    imgbox.style.left = left + "px";
                    imgbox.style.top = top + "px"
                }
                cb && cb(img.src)
            };
            img.src = src
        }

        function imgWheel(e) {
            e || (e = window.event);
            var img = imgdom, dom, dir, iw, r, inimg, px, py;
            if (img) {
                dir = e.deltaY > 0 || e.wheelDelta < 0 ? -1 : 1;
                iw = img.width;
                dom = img.parentElement;
                if (/img/i.test(e.target.tagName)) {
                    px = e.offsetX + img.offsetLeft;
                    py = e.offsetY + img.offsetTop;
                    var tm;
                    switch (deg) {
                        case 3:
                            tm = px;
                            px = py;
                            py = -tm;
                            break;
                        case 2:
                            px = -px;
                            py = -py;
                            break;
                        case 1:
                            tm = px;
                            px = -py;
                            py = tm;
                            break;
                        case 0:
                    }
                } else {
                    px = e.offsetX - left;
                    py = e.offsetY - top
                }
                iw = iw + dir * 50;
                iw < 10 && (iw = 10);
                r = iw / img.width - 1;
                img.width = iw;
                img.style.margin = -(img.height / 2) + "px 0 0 " + -(img.width / 2) + "px";
                left -= r * px;
                top -= r * py;
                dom.style.left = left + "px";
                dom.style.top = top + "px"
            }
        }

        function imgRotate(e) {
            e || (e = window.event);
            var dir = !/anti/i.test(this.id);
            var img = imgdom;
            if (img) {
                deg || (deg = 0);
                deg += !!dir ? 1 : -1;
                deg += 8;
                deg %= 4;
                if ($.ua.browser.isSafari) {
                    img.style.webkitTransform = "rotate(" + deg * 90 + "deg)"
                } else {
                    img.style.transform = "rotate(" + deg * 90 + "deg)"
                }
            }
        }

        function imgZoom(e) {
            e || (e = window.event);
            var img = imgdom, dom, dir, iw, r, inimg, px, py;
            iw = img.width;
            dom = img.parentElement;
            if (e.target.id == "zoomLarge") {
                dir = 1
            } else if (e.target.id == "zoomSmall") {
                dir = -1
            } else {
                return
            }
            iw = iw + dir * 50;
            iw < 10 && (iw = 10);
            r = iw / img.width - 1;
            img.width = iw;
            img.style.margin = -(img.height / 2) + "px 0 0 " + -(img.width / 2) + "px";
            left -= r * px;
            top -= r * py;
            dom.style.left = left + "px";
            dom.style.top = top + "px"
        }

        function imgToggle(e) {
            e || (e = window.event);
            var st = store.getState(), img = st.preview.img, src, number = img.number, total = img.total,
                links = img.links;
            if (e.target.id == "midPrev") {
                number--
            } else if (e.target.id == "midNext") {
                number++
            } else {
                return
            }
            if (number <= 0) {
                number = 0;
                $("#midPrev").addClass("hide");
                $("#midNext").rmClass("hide")
            } else if (total - number <= 1) {
                number = total - 1;
                $("#midPrev").rmClass("hide");
                $("#midNext").addClass("hide")
            } else {
                $("#midPrev").rmClass("hide");
                $("#midNext").rmClass("hide")
            }
            $("#imgNum")[0].innerText = parseInt(number + 1) + " / " + total;
            Object.assign(store.getState().preview.img, {number: number});
            src = links[number];
            imgInit(src);
            imgdom.src = src
        }

        function imgMousedown(e) {
            e || (e = window.event);
            imgdom && (drag = {x: e.clientX, y: e.clientY})
        }

        function imgMousemove(e) {
            e || (e = window.event);
            var dom, img;
            if (drag && (img = imgdom)) {
                dom = img.parentElement;
                var pt = {x: e.clientX, y: e.clientY};
                left += pt.x - drag.x;
                top += pt.y - drag.y;
                drag = pt;
                dom.style.left = left + "px";
                dom.style.top = top + "px"
            }
        }

        function imgMouseup() {
            drag && (drag = null)
        }
    }();
    var MsgText = function () {
        function MsgText() {
            return Msg.apply(this, arguments)
        }

        MsgText.prototype = new Msg;
        MsgText.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            if (!this.json.text) {
                this.err = "数据中没有text内容";
                return false
            }
            return true
        };
        MsgText.prototype.build = function (nowrap) {
            var feedback = "(麻烦亲对我的回复做出评价：满意请发送 “Y” ，不满意发送 “N” )", text = aLinkBlank(this.json.text), showFeb,
                state = store.getState();
            if (text.endsWith(feedback)) {
                text = text.slice(0, text.length - feedback.length - 1);
                showFeb = true
            } else if (this.msg.transparent) {
                feedback = this.msg.transparent.feedback;
                feedback && (text = text.slice(0, text.length - feedback - 1), showFeb = true)
            }
            if (this.msg && this.msg.dir != 1) {
                text = text.replace(/\(请发送数字\)(:\r?\n\[1\])/g, "(" + $.i18n("点击发送问题") + ")$1")
            }
            text = text && text.replace(/([\r?\n]([\[|\【])(\d+)([\]|\】])\s?)([^\r?\n]+)/g, '<div class="msg-relques child$3"><span class="msg-anchor' + (this.json.text === state.robot.intro ? " msg-greeting" : "") + '" title="' + $.i18n("点击发送问题") + '" mode="$2$4" txt="$5" index="$3">$3. $5</span></div>');
            if (this.msg && this.msg.dir != 1) {
                text = text && $.fLink(text, '<a class="msg-anchor" href="$" target="_blank">$</a>').replace(/\r?\n/g, "<br/>")
            } else {
                text = text && fHtml(text)
            }
            text = text && $.emoji(text, "emoji");
            text = text && $.emojiZh(text, "emoji");
            text = text && text.replace(/\[URL=([^\]]+)\]([^\[]+)\[\/URL\]/g, '<a href=$1 target="_blank">$2</a>');
            if (text.length > 5 && text.substring(text.length - 5) == "<br/>") {
                text = text.substring(0, text.length - 5)
            }
            text = text && text.replace(/\[(\d+?)\]/g, '<span class="msg-anchor" txt="$1">[$1]</span>');
            var that = this;
            if (nowrap) {
                var span = document.createElement("span");
                span.innerHTML = text;
                span.onclick = function (e) {
                    that.clicked(e)
                };
                return span
            } else {
                var div = document.createElement("div"), state = store.getState(), delayT = state.site.delay.time;
                div.className = "msg-ball";
                this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
                if (!state.site.delay.loadHist && (this.msg.dir == 2 || this.msg.dir == 9)) {
                    var img = document.createElement("img");
                    img.src = "lib/files/images/btns/typing_bd106d9673.gif";
                    img.width = 24;
                    img.height = 9;
                    div.appendChild(img);
                    setTimeout(function () {
                        div.innerHTML = text;
                        state.site.delay.timer = 0;
                        if (showFeb) {
                            appendFeb(div)
                        }
                        setTimeout(function () {
                            var showPnl = $("#show")[0];
                            showPnl.scrollTop = showPnl.scrollHeight
                        }, 10)
                    }, delayT / 2 * 1e3)
                } else {
                    div.innerHTML = text;
                    if (showFeb) {
                        appendFeb(div)
                    }
                }
                div.onclick = function (e) {
                    that.clicked(e)
                };
                return div
            }
        };
        MsgText.prototype.clicked = function (e) {
            e || (e = window.event);
            var target = e.target || e.srcElement, txt;
            var state = store.getState();
            if (/msg-anchor/i.test(target.className) && target.getAttribute("index") && target.getAttribute("index")) {
                txt = /msg-greeting/i.test(target.className) || target.getAttribute("mode") == "【】" ? target.getAttribute("txt") : target.getAttribute("index");
                store.dispatch("SEND_MSG", {id: $.sendid(), stamp: $.stamp(), type: 1, dir: 1, json: {text: txt}});
                e.preventDefault && e.preventDefault()
            } else if (/msg-anchor/i.test(target.className) && $.isPhone && (txt = target.getAttribute("href"))) {
                if (/.*?\.(jpg|gif|png|jpeg)/gi.test(txt)) {
                    preview({type: "img", src: txt});
                    e.preventDefault && e.preventDefault()
                } else if (/^(https?:)?\/\/[^\s\.\\]+\.v5kf\.(com|net|cn)/.test(txt)) {
                    preview({type: "link", link: txt});
                    e.preventDefault && e.preventDefault()
                } else {
                    return false
                }
            } else if (/feb-/i.test(target.className) && (txt = target.getAttribute("txt"))) {
                store.dispatch("SEND_MSG", {id: $.sendid(), stamp: $.stamp(), type: 1, dir: 1, json: {text: txt}});
                $.each($(".msg-feb div"), function (index, ele) {
                    $(ele).attr("txt", "")
                })
            }
        };

        function fHtml(str) {
            if (/<[^<>]+>/g.test(str)) {
                str = str.replace(/</gi, "&lt;");
                str = str.replace(/[\r|\n]/g, "<br>");
                str = str.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
                str = str.replace(/[\s]/g, "&nbsp;")
            }
            return str
        }

        function aLinkBlank(txt) {
            return txt.replace(/<a ([^>]*?)>/gi, '<a $1 target="_blank" class="msg-anchor">')
        }

        function appendFeb(parent) {
            var state = store.getState();
            parent.innerHTML += '<div class="msg-feb"><div class="feb-yes" txt="Y"><i class="kh kh-yes"></i>' + $.i18n(state.site.id === 155488 ? "解决" : "有用") + '(Y)</div><div class="feb-no" txt="N"><i class="kh kh-no"></i>' + $.i18n(state.site.id === 155488 ? "未解决" : "没用") + "(N)</div></div>"
        }

        return MsgText
    }();
    var MsgImg = function () {
        function MsgImg() {
            return Msg.apply(this, arguments)
        }

        MsgImg.prototype = new Msg;
        MsgImg.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            if (!this.json.src && !this.json.file) {
                this.err = "数据中没有src内容";
                return false
            }
            return true
        };
        MsgImg.prototype.build = function (nowrap) {
            var that = this;
            var imgSrc = $.setProtocol(this.json.src || this.json.file);
            var div = document.createElement("div"), state = store.getState(), delayT = state.site.delay.time;
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            var img = document.createElement("img");
            if (!state.site.delay.loadHist && (this.msg.dir == 2 || this.msg.dir == 9)) {
                img.src = "lib/files/images/btns/typing_bd106d9673.gif";
                img.width = 24;
                img.height = 9;
                setTimeout(function () {
                    img.src = imgSrc;
                    div.className = "msg-ball img";
                    img.removeAttribute("width");
                    img.removeAttribute("height");
                    state.site.delay.timer = 0;
                    setTimeout(function () {
                        var showPnl = $("#show")[0];
                        showPnl.scrollTop = showPnl.scrollHeight
                    }, 10)
                }, delayT / 2 * 1e3)
            } else {
                div.className = "msg-ball img";
                img.src = imgSrc
            }
            div.appendChild(img);
            if (this.msg.status === 1 && this.json.imgId) {
                var mask = document.createElement("div");
                mask.id = "img" + this.json.imgId.substring(0, 13);
                mask.className = "abs cover img-loading";
                mask.onClick = function () {
                };
                div.appendChild(mask)
            } else if (this.msg.status === -1 && this.json.imgId) {
                var mask = document.createElement("div");
                mask.id = "img" + this.json.imgId.substring(0, 13);
                mask.className = "abs cover img-loading refresh";
                mask.onClick = function () {
                };
                div.appendChild(mask)
            }
            img.onclick = function () {
                imgSrc = $.setProtocol(that.msg.json.src);
                that.preview && that.preview(imgSrc)
            };
            return div
        };
        MsgImg.prototype.preview = function (src) {
            var state = store.getState(), number, total, links = [], hists = state.hists, messages = state.messages;
            preview({type: "img", src: src});
            if (!hists.length && !messages.length) return;
            for (var i = 0; i < hists.length; i++) {
                if (hists[i].type == 2) {
                    links.push($.setProtocol(hists[i].json.src))
                }
            }
            if (messages.length) {
                for (var i = 0; i < messages.length; i++) {
                    if (messages[i].type == 2) {
                        links.push($.setProtocol(messages[i].json.src))
                    }
                }
            }
            number = links.indexOf(src);
            total = links.length;
            $(".img-num").rmClass("hide");
            $(".img-mid").rmClass("hide");
            $("#imgNum")[0].innerText = parseInt(number + 1) + " / " + total;
            $("#midPrev").rmClass("hide");
            $("#midNext").rmClass("hide");
            if (total < 2) {
                $("#midPrev").addClass("hide");
                $("#midNext").addClass("hide")
            } else {
                if (number <= 0) {
                    $("#midPrev").addClass("hide")
                } else if (total - number <= 1) {
                    $("#midNext").addClass("hide")
                }
            }
            Object.assign(store.getState().preview.img, {number: number, total: total, links: links})
        };
        return MsgImg
    }();
    var MsgLoc = function () {
        function MsgLoc() {
            return Msg.apply(this, arguments)
        }

        MsgLoc.prototype = new Msg;
        MsgLoc.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            if (!this.json.lat) {
                this.err = "数据中没有纬度内容";
                return false
            }
            if (!this.json.lng) {
                this.err = "数据中没有经度内容";
                return false
            }
            return true
        };
        MsgLoc.prototype.build = function (nowrap) {
            var that = this;
            json = this.json;
            this.loc = "http://apis.map.qq.com/ws/staticmap/v2/?center=" + json.lat + "," + json.lng + "&zoom=" + (json.scale < 19 && json.scale > 3 ? json.scale : 15) + "&size=300*200&maptype=roadmap&markers=size:small|color:0x207CC4|label:V|" + json.lat + "," + json.lng + "&key=COMBZ-T5NWF-N2KJF-JTLXC-62JKK-QKFQG";
            var div = document.createElement("div"), state = store.getState(), loc = this.loc,
                delayT = state.site.delay.time;
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            if (!state.site.delay.loadHist && (this.msg.dir == 2 || this.msg.dir == 9)) {
                div.innerHTML = '<img src="lib/files/images/btns/typing_bd106d9673.gif" width="24" height="9" />';
                setTimeout(function () {
                    div.className = "msg-ball img";
                    div.innerHTML = '<img src="' + (this.loc || loc) + '"/>';
                    state.site.delay.timer = 0;
                    setTimeout(function () {
                        var showPnl = $("#show")[0];
                        showPnl.scrollTop = showPnl.scrollHeight
                    }, 10)
                }, delayT / 2 * 1e3)
            } else {
                div.className = "msg-ball img";
                div.innerHTML = '<img src="' + this.loc + '"/>'
            }
            div.onclick = function () {
                that.preview && that.preview(that.json || json)
            };
            return div
        };
        MsgLoc.prototype.preview = function (res) {
            $("#mask").addClass("center");
            preview({type: "map", json: {lat: res.lat, lng: res.lng, scale: res.scale, label: res.label}})
        };
        return MsgLoc
    }();
    var MsgNews = function () {
        function MsgNews() {
            return Msg.apply(this, arguments)
        }

        MsgNews.prototype = new Msg;
        MsgNews.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            var json = this.json;
            if ($.isArray(json)) {
                if (!json.length) {
                    this.err = "多图文数量为零";
                    return false
                }
                for (var i = 0, len = json.length; i < len; i++) {
                    var it = json[i];
                    if (!it.name) {
                        this.err = "数据中没有name内容";
                        return false
                    }
                }
            } else {
                if (!json.name) {
                    this.err = "数据中没有name内容";
                    return false
                }
                if (!json.link) {
                    this.err = "数据中没有link内容"
                }
            }
            return true
        };
        MsgNews.prototype.build = function (nowrap) {
            var that = this, json = this.json;
            if (json.length) {
                var div = document.createElement("div"), it;
                div.className = "post-multi";
                $.each(json, function (i, item) {
                    var itemDiv = document.createElement("div");
                    itemDiv.className = "post-multi-item";
                    if (i === 0) {
                        itemDiv.innerHTML = '<div class="post-multi-img" style="background-image:url(' + ($.setProtocol(item.img) || "") + "); filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(item.img)) + ',sizingMethod=scale)"><div class="post-multi-label">' + item.name + "</div></div>"
                    } else {
                        itemDiv.innerHTML = '<div class="post-multi-left"><div class="post-multi-title">' + item.name + '</div></div><div class="post-multi-pic" style="background-image:url(' + ($.setProtocol(item.img) || "") + "); filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(item.img)) + ',sizingMethod=scale)"></div>'
                    }
                    div.appendChild(itemDiv);
                    itemDiv.onclick = function () {
                        that.preview && that.preview(item)
                    }
                })
            } else {
                var div = document.createElement("div");
                div.className = "post-single";
                div.innerHTML = "";
                json.name && (div.innerHTML += '<div class="post-single-title">' + json.name + "</div>");
                this.msg.stamp && (div.innerHTML += '<div class="post-single-time">' + $.fTime(this.msg.stamp, "smart") + "</div>");
                json.img && (div.innerHTML += '<div class="post-single-img" style="background-image: url(' + ($.setProtocol(json.img) || "") + "); filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(json.img)) + ',sizingMethod=scale)"></div>');
                json.desc && (div.innerHTML += '<div class="post-single-desc">' + json.desc + "</div>");
                div.innerHTML && (div.innerHTML += '<div class="post-single-link">' + $.i18n("查看全文") + "</div>");
                div.onclick = function () {
                    that.preview && that.preview(json)
                }
            }
            return div
        };
        MsgNews.prototype.preview = function (json) {
            if (json.link) {
                preview({type: "link", link: json.link})
            }
        };
        return MsgNews
    }();
    var MsgNote = function () {
        function MsgNote() {
            return Msg.apply(this, arguments)
        }

        MsgNote.prototype = new Msg;
        MsgNote.prototype.valid = function () {
            return true
        };
        MsgNote.prototype.build = function (nowrap) {
            var that = this, json = this.json;
            var div = document.createElement("div"), state = store.getState(), delayT = state.site.delay.time;
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            var html = '<div class="msg-title">' + $.i18n("留言反馈") + '</div><div class="msg-text">' + $.i18n("请填写您的留言内容") + "</div>";
            if (true || typeof custom.float != "undefined" && typeof custom.float.msglist != "undefined") {
                var feilds = {
                    username: $.i18n("姓名"),
                    phone: $.i18n("电话"),
                    email: $.i18n("邮箱"),
                    qq: $.i18n("Q Q"),
                    gender: $.i18n("性别"),
                    address: $.i18n("地址"),
                    content: $.i18n("留言")
                }, cfg, val, state = store.getState();
                if (!(cfg = state.site.note)) {
                    cfg = {username: "2", phone: "1", email: "2", qq: "0", gender: "0", address: "1", content: "2"}
                }
                for (var key in feilds) {
                    if (key in cfg && (val = cfg[key]) != 0) {
                        if (key == "gender") {
                            html += '<div class="msg-wrap">' + $.i18n("性别") + '<label><input name="gender" ' + (val == 2 ? "valid=true" : "") + (json ? ' disabled="true"' + (json.gender ? ' checked="true"' : "") : "") + ' value="0" type="radio" class="msg-input" checked="true">' + $.i18n("保密") + '</label><label><input name="gender" ' + (val == 2 ? "valid=true" : "") + (json ? ' disabled="true"' + (json.gender ? ' checked="true"' : "") : "") + ' value="1" type="radio" class="msg-input">' + $.i18n("男") + '</label><label><input name="gender" ' + (val == 2 ? "valid=true" : "") + (json ? ' disabled="true"' + (json.gender ? ' checked="true"' : "") : "") + ' value="2" type="radio" class="msg-input">' + $.i18n("女") + "</label>" + (val == 2 ? ' <span class="msg-alert">*</span>' : "") + "</div>"
                        } else if (key == "content") {
                            html += '<div class="msg-wrap">' + feilds[key] + $.i18n(" 150 字以内") + (val == 2 ? ' <span class="msg-alert">*</span>' : "") + '<br/><textarea class="msg-content" name="' + key + '" ' + (val == 2 ? "valid=true" : "") + (json ? ' disabled="true"' + (json[key] ? ' value="' + json[key] + '"' : "") : "") + " ></textarea>" + "</div>"
                        } else {
                            html += '<div class="msg-wrap">' + feilds[key] + ' <input type="text" class="msg-input" name="' + key + '" ' + (val == 2 ? "valid=true" : "") + (json ? ' disabled="true"' + (json[key] ? ' value="' + json[key] + '"' : "") : "") + " />" + (val == 2 ? ' <span class="msg-alert">*</span>' : "") + "</div>"
                        }
                    }
                }
            }
            html += '<div class="msg-btn note-btn"' + (json ? ' style="background-color: grey"' : "") + ">" + (json ? $.i18n("已提交") : $.i18n("提交")) + "</div>";
            div.innerHTML = html;
            div.onclick = function (e) {
                that.clicked(e, that.msg)
            };
            return div
        };
        MsgNote.prototype.clicked = function (e, msg) {
            e || (e = window.event);
            var target = e.target || e.srcElement;
            if (/note-btn/.test(target.className)) {
                var json = {}, inputs = target.parentElement.getElementsByTagName("input"),
                    textarea = (textarea = target.parentElement.getElementsByTagName("textarea")) && textarea[0];
                if (textarea) {
                    json["content"] = textarea.value
                }
                $.each(inputs, function (i, input) {
                    if (input.name) {
                        if (/radio/.test(input.type)) {
                            if (input.checked) {
                                json[input.name] = input.value
                            }
                        } else {
                            json[input.name] = input.value
                        }
                    }
                });
                for (var i = 0; i < inputs.length; i++) {
                    var input = inputs[i];
                    if (input.getAttribute("valid")) {
                        if (!json[input.name]) {
                            $.showinfo("您的输入不完整，*号为必填项");
                            return
                        }
                    }
                }
                if (textarea && textarea.getAttribute("valid")) {
                    if (!json["content"]) {
                        $.showinfo("您的输入不完整，*号为必填项");
                        return
                    }
                }
                if (json["phone"] && !validPhone(json["phone"])) {
                    $.showinfo("电话格式不正确，请重新输入！");
                    return
                }
                if (json["email"] && !validEmail(json["email"])) {
                    $.showinfo("邮件格式不正确，请重新输入！");
                    return
                }
                if (json["qq"] && !/[0-9]{5,13}/.test(json["qq"])) {
                    $.showinfo("QQ格式不正确，请重新输入！");
                    return
                }
                if (json["content"]) {
                    if (json["content"].length > 150) {
                        $.showinfo("留言内容不能超出150个字符");
                        return
                    }
                }
                msg.json = json;
                store.dispatch("SEND_MSG", msg);
                target.innerHTML = $.i18n("提交中...");
                target.style.backgroundColor = "gray";
                setTimeout(function () {
                    target.innerHTML = $.i18n("已提交");
                    $.each(inputs, function (i, input) {
                        input.setAttribute("disabled", true)
                    })
                }, 600)
            }
        };

        function validEmail(email) {
            if (email.length > 0) {
                var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{1,10}$/;
                if (!reg.test(email)) {
                    return false
                }
            } else {
                return false
            }
            return true
        }

        function validPhone(phone) {
            var isPhone = /^([0-9]{3,4}-?)?[0-9]{7,8}$/;
            var isMob = /^((\+?[0-9]{2})|(\(\+?[0-9]{2}\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
            if (isMob.test(phone) || isPhone.test(phone)) {
                return true
            } else {
                return false
            }
        }

        return MsgNote
    }();
    var MsgEval = function () {
        function MsgEval() {
            return Msg.apply(this, arguments)
        }

        MsgEval.prototype = new Msg;
        MsgEval.prototype.valid = function () {
            return true
        };
        MsgEval.prototype.build = function (nowrap) {
            var that = this, json = this.json, code = (json || {}).code;
            var div = document.createElement("div"), state = store.getState(), delayT = state.site.delay.time;
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            var text = '<div class="msg-title">' + $.i18n("满意度评价") + '</div><div class="msg-text">' + $.i18n("您对刚才的服务沟通满意度如何？") + '</div><div class="msg-wrap"><label><input name="eval" value="0" type="radio"' + (json ? ' disabled="true"' + (code === 0 ? ' checked="true"' : "") : ' checked="true"') + ">" + $.i18n("很好") + '</label><label><input name="eval" value="1" type="radio"' + (json ? ' disabled="true"' + (code === 1 ? ' checked="true"' : "") : "") + ">" + $.i18n("好") + '</label><label><input name="eval" value="2" type="radio"' + (json ? ' disabled="true"' + (code === 2 ? ' checked="true"' : "") : "") + ">" + $.i18n("一般") + '</label><label><input name="eval" value="3" type="radio"' + (json ? ' disabled="true"' + (code === 3 ? ' checked="true"' : "") : "") + ">" + $.i18n("差") + '</label></div><div class="msg-btn eval-btn"' + (json ? ' style="background-color: grey"' : "") + ">" + (json ? $.i18n("已提交") : $.i18n("提交")) + "</div>";
            div.innerHTML = text;
            json || (div.onclick = function (e) {
                that.clicked(e, that.msg)
            });
            return div
        };
        MsgEval.prototype.clicked = function (e, msg) {
            e || (e = window.event);
            var target = e.target || e.srcElement;
            if (/eval-btn/.test(target.className)) {
                var radios = target.parentElement.getElementsByTagName("input");
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked) {
                        msg.json = {code: parseInt(radios[i].value)};
                        store.dispatch("SEND_MSG", msg);
                        break
                    }
                }
                target.innerHTML = $.i18n("提交中...");
                target.style.backgroundColor = "gray";
                $.rmClass(target, "eval-btn");
                setTimeout(function () {
                    target.innerHTML = $.i18n("已提交");
                    $.each(radios, function (i, radio) {
                        radio.setAttribute("disabled", true)
                    })
                }, 1e3)
            }
        };
        return MsgEval
    }();
    var MsgTel = function () {
        function MsgTel() {
            return Msg.apply(this, arguments)
        }

        MsgTel.prototype = new Msg;
        MsgTel.prototype.valid = function () {
            return true
        };
        MsgTel.prototype.build = function (nowrap) {
            var div = document.createElement("div");
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            div.innerHTML = '<div class="msg-title">' + (data.site.info && data.site.info.phone ? "热线：" + data.site.info.phone + "<br/>" : "期待与您的电话交流") + '</div><div class="msg-text">请输入或修改您的联系方式</div><div class="msg-form"><input class="msg-tel" type="text" placeholder="联系方式"' + (hist ? " disabled" : "") + '/></div><div class="msg-drag tel-drag' + (hist ? ' gray">重发请点击 "邀请致电"' : '">向右拖动发出邀请<div class="drag-thumb"></div>') + "</div>";
            return div
        };
        return MsgTel
    }();
    var MsgAudio = function () {
        function MsgAudio() {
            return Msg.apply(this, arguments)
        }

        MsgAudio.prototype = new Msg;
        MsgAudio.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            if (!this.json.src) {
                this.err = "数据中没有src内容";
                return false
            }
            return true
        };
        MsgAudio.prototype.build = function (nowrap) {
            var that = this;
            var div = document.createElement("div"), btn = document.createElement("div"), state = store.getState(),
                delayT = state.site.delay.time;
            div.className = "msg-ball";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            btn.className = "msg-audio-btn";
            if (!state.site.delay.loadHist && (this.msg.dir == 2 || this.msg.dir == 9)) {
                var img = document.createElement("img");
                img.src = "lib/files/images/btns/typing_bd106d9673.gif";
                img.width = 24;
                img.height = 9;
                div.appendChild(img);
                setTimeout(function () {
                    div.className = "msg-ball audio";
                    div.removeChild(img);
                    div.appendChild(this.btn = btn);
                    state.site.delay.timer = 0;
                    setTimeout(function () {
                        var showPnl = $("#show")[0];
                        showPnl.scrollTop = showPnl.scrollHeight
                    }, 10)
                }, delayT / 2 * 1e3)
            } else {
                div.className = "msg-ball audio";
                div.appendChild(this.btn = btn)
            }
            div.onclick = function () {
                that.play($.setProtocol(that.json.src))
            };
            return div
        };
        MsgAudio.prototype.play = function (src) {
            if (/amr/i.test(this.json.format) && $.ua.isMobile) {
                window.open(src, "_blank");
                return
            }
            var that = this;
            if (!this.playing) {
                this.playing = true;
                this.audio = $.playAudio(src);
                this.audio.onerror = this.audio.onpause = paused;
                this.audio.onended = ended;
                this.btn && $.addClass(this.btn, "playing")
            } else {
                $.pauseAudio()
            }

            function paused() {
                that.audio.paused || $.pauseAudio();
                that.playing = false;
                that.btn && $.rmClass(that.btn, "playing")
            }

            function ended() {
                that.audio.paused || $.pauseAudio();
                that.playing = false;
                that.btn && $.rmClass(that.btn, "playing")
            }
        };
        return MsgAudio
    }();
    var MsgVideo = function () {
        function MsgVideo() {
            return Msg.apply(this, arguments)
        }

        MsgVideo.prototype = new Msg;
        MsgVideo.prototype.valid = function () {
            if (!Msg.prototype.valid.call(this)) {
                return false
            }
            if (!this.json.src && !this.json.file) {
                this.err = "数据中没有src内容";
                return false
            }
            return true
        };
        MsgVideo.prototype.build = function (nowrap) {
            var that = this;
            var videoSrc = $.setProtocol(this.json.src || this.json.file);
            var div = document.createElement("div");
            div.className = "msg-ball video";
            this.msg.stamp && div.setAttribute("title", $.fTime(this.msg.stamp, "smart hh:mm:ss"));
            var video = document.createElement("video");
            video.src = videoSrc;
            video.controls = "controls";
            video.id = "video" + this.msg.id;
            video.width = 400;
            video.poster = "";
            video.style = "object-fit: fill;";
            video.setAttribute("x5-video-player-type", "h5");
            video.setAttribute("x5-video-player-fullscreen", true);
            video.setAttribute("webkit-playsinline", "playsinline");
            video.setAttribute("x-webkit-airplay", true);
            video.setAttribute("x5-video-orientation", "portraint");
            video.setAttribute("preload", "auto");
            video.setAttribute("playsinline", "playsinline");
            video.setAttribute("x5-playsinline", "playsinline");
            div.appendChild(video);
            var source = document.createElement("source");
            source.type = "video/mp4";
            video.appendChild(source);
            if (this.msg.status === 1 && this.json.imgId) {
                var mask = document.createElement("div");
                mask.id = "img" + this.json.imgId.substring(0, 13);
                mask.className = "abs cover img-loading";
                mask.onClick = function () {
                };
                div.appendChild(mask)
            } else if (this.msg.status === -1 && this.json.imgId) {
                var mask = document.createElement("div");
                mask.id = "img" + this.json.imgId.substring(0, 13);
                mask.className = "abs cover img-loading refresh";
                mask.onClick = function () {
                };
                div.appendChild(mask)
            }
            return div
        };
        return MsgVideo
    }();
    var buildMsg = function () {
        var cursor = {};
        return function (msg, nowrap) {
            var state = store.getState(), guest = state.guest, frag = [];
            if (msg.stamp) {
                var dom = timeLine(msg.stamp);
                dom && frag.push(dom)
            }
            var div;
            switch (msg.type) {
                case $.msgTypes.TEXT:
                    div = new MsgText(msg).render(nowrap);
                    break;
                case $.msgTypes.IMAGE:
                    div = new MsgImg(msg).render(nowrap);
                    break;
                case $.msgTypes.LOCATION:
                    div = new MsgLoc(msg).render(nowrap);
                    break;
                case $.msgTypes.VOICE:
                    div = new MsgAudio(msg).render(nowrap);
                    break;
                case $.msgTypes.VIDEO:
                    div = new MsgVideo(msg).render(nowrap);
                    break;
                case $.msgTypes.NEWS:
                    div = new MsgNews(msg).render(nowrap);
                    break;
                case $.msgTypes.MUSIC:
                    div = new MsgAudio(msg).render(nowrap);
                    break;
                case $.msgTypes.COMMENT:
                    div = new MsgEval(msg).render(nowrap);
                    break;
                case $.msgTypes.NOTE:
                    div = new MsgNote(msg).render(nowrap);
                    break;
                case $.msgTypes.TEL:
                    div = new MsgTel(msg).render(nowrap);
                    break;
                default:
                    console.log("无需显示的消息：类型" + msg.type)
            }
            if (div) {
                frag.push(div)
            }
            return frag
        };

        function timeLine(stamp) {
            stamp = getCursor(stamp);
            if (stamp) {
                var dom = document.createElement("div");
                dom.className = "msg min time";
                dom.innerHTML = $.fTime(stamp, "smart hh:mm:ss");
                return dom
            }
        }

        function getCursor(time) {
            var sc = $.stamp(time);
            time = void 0;
            if (!cursor.prev || sc < cursor.prev + 18e4) {
                if (sc > cursor.prev) {
                    cursor.prevLast = sc
                } else {
                    cursor.prev && (time = cursor.prevLast);
                    cursor.prev = Math.floor((sc - 1) / 18e4) * 18e4;
                    cursor.prevLast = sc
                }
            }
            if (!cursor.next || sc > cursor.next - 18e4) {
                if (sc < cursor.next) {
                } else {
                    cursor.next && (time = sc);
                    cursor.next = Math.ceil((sc + 1) / 18e4) * 18e4
                }
            }
            return time
        }
    }();

    function buildWelcomeMsg(txt) {
        var fg, frag = buildMsg({type: 1, dir: 2, json: {text: txt}}, "nowrap");
        fg || (fg = document.createDocumentFragment());
        for (var j = 0; j < frag.length; j++) {
            fg.appendChild(frag[j])
        }
        return fg
    }

    store.connect("robot", function (st, old, state) {
        if (st && st.logo) {
            $("#workerIcon").addClass("kh-bg");
            $("#workerPhoto").css({
                "background-image": "url(" + $.setProtocol(st.logo) + ")",
                filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(st.logo)) + ",sizingMethod=scale)"
            })
        } else {
            $("#workerIcon").rmClass("kh-bg");
            $("#workerPhoto").css("background-image", "", true);
            $("#workerPhoto").css("background-color", $.autoColor("rgba(255, 255, 255, 0.3)"))
        }
        $("#workerIcon").html("&#xe803;");
        $("#workerName").html(st && st.name || $.i18n("机器人"));
        if (!st) {
            return
        }
        if (st.update && !state.system.reconn || $.firstBackStatus === 1) {
            if (st.intro && !state.current.wid) {
                st.intro && $("#welcomeBall").html("").append(buildWelcomeMsg(st.intro));
                $("#welcome").rmClass("hide")
            } else {
                $("#welcome").addClass("hide")
            }
        }
        if (state.robot.logo) {
            var img = new Image;
            img.onload = function () {
                $("#welcomeIcon").html("").css({
                    "background-image": "url(" + $.setProtocol(img.src) + ")",
                    filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : $.setProtocol(img.src)) + ",sizingMethod=scale)"
                });
                $("#welcomeIcon").addClass("photo")
            };
            img.src = $.setProtocol(state.robot.logo)
        }
        $("#welcomeLeft").attr("worker", state.robot.name || $.i18n("机器人客服"))
    });
    store.connect("current.wid", function (st, old, state) {
        var worker = state.workers[st];
        if (worker) {
            if (state.messages.length <= 0) {
                if ($.firstBackStatus === 1) {
                    $(".msg-banner").addClass("hide");
                    store.dispatch("LOAD_HIST", true);
                    setTimeout(function () {
                        $("#show")[0].scrollTop = $("#show")[0].scrollHeight
                    }, 10);
                    setTimeout(function () {
                        if (!state.site.banner) {
                            $("#show").css("padding-top", "6px")
                        } else {
                            $(".msg-banner").rmClass("hide")
                        }
                        $("#show")[0].scrollTop = $("#show")[0].scrollHeight
                    }, 200)
                } else {
                    $("#welcome").addClass("hide");
                    $("#defHot").addClass("hide")
                }
                !sessionStorage[store.getState().site.id + "_defHot"] && $("#defHot").rmClass("hide")
            } else {
            }
            if (state.site.control.show_facetime || $.debug) {
                $("#videoBtn").css("display", "inline-block");
                $("#voiceBtn").css("display", "inline-block");
                var name = st + "@" + state.site.id
            }
        } else {
            if (state.site.control.show_facetime && !$.debug) {
                $("#videoBtn").css("display", "none");
                $("#voiceBtn").css("display", "none")
            }
        }
    });
    store.connect("robot.name", function (st, old, state) {
        $("#workerName").html(st)
    });
    store.connect("current.status", function (st, old, state) {
        switch (st) {
            case 1:
                $(".chat-title")[0].innerHTML = state.site.name || $.i18n("在线咨询");
                break;
            case 2:
                $(".chat-title")[0].innerHTML = state.workers[state.current.wid].name + $.i18n(" 为您服务");
                break;
            case 3:
                $(".chat-title")[0].innerHTML = state.site.name || $.i18n("在线咨询");
                break;
            case 0:
                break;
            default:
                $(".chat-title")[0].innerHTML = state.site.name || $.i18n("在线咨询");
                break
        }
    });
    store.connect("workers", function (st, old, state) {
        if (!st || $.isEmptyObject(st)) {
            return
        }
        var sid = state.site.id;
        $.cache(sid + "_workers", JSON.stringify(st));
        var worker;
        if (state.current && state.current.wid && (worker = st[state.current.wid])) {
            if (worker.photo) {
                $("#workerPhoto")[0].style.backgroundColor = "transparent";
                $("#workerIcon").addClass("kh-bg");
                $("#workerPhoto").css({
                    "background-image": "url(" + $.setProtocol(worker.photo) + ")",
                    filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : worker.photo) + ",sizingMethod=scale)"
                })
            } else {
                $("#workerPhoto")[0].style.backgroundColor = $.autoColor("rgba(255, 255, 255, 0.3)");
                $("#workerIcon").rmClass("kh-bg");
                $("#workerPhoto").css("background-image", "", true)
            }
            $("#workerIcon").html("&#xe80c;");
            $("#workerName").html(worker.name || $.i18n("人工客服"))
        } else {
            $("#workerIcon").html("&#xe803;");
            $("#workerName").html(state.robot.name || $.i18n("机器人"))
        }
    });
    store.connect("current.wid", function (st, old, state) {
        var worker;
        if (st && (worker = state.workers[st])) {
            if (worker.photo) {
                $("#workerPhoto")[0].style.backgroundColor = "transparent";
                $("#workerIcon").addClass("kh-bg");
                $("#workerPhoto").css({
                    "background-image": "url(" + $.setProtocol(worker.photo) + ")",
                    filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : worker.photo) + ",sizingMethod=scale)"
                })
            } else {
                $("#workerPhoto")[0].style.backgroundColor = $.autoColor("rgba(255, 255, 255, 0.3)");
                $("#workerIcon").rmClass("kh-bg");
                $("#workerPhoto").css("background-image", "", true)
            }
            $("#workerIcon").html("&#xe80c;");
            $("#workerName").html(worker.name || $.i18n("人工客服"))
        } else {
            var robot = state.robot;
            if (robot.logo) {
                $("#workerPhoto")[0].style.backgroundColor = "transparent";
                $("#workerIcon").addClass("kh-bg");
                $("#workerPhoto").css({
                    "background-image": "url(" + $.setProtocol(robot.logo) + ")",
                    filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + (!$.ua.isIe || $.ua.ie > 8 ? "" : robot.logo) + ",sizingMethod=scale)"
                })
            } else {
                $("#workerPhoto")[0].style.backgroundColor = $.autoColor("rgba(255, 255, 255, 0.3)");
                $("#workerIcon").rmClass("kh-bg");
                $("#workerPhoto").css("background-image", "", true)
            }
            $("#workerIcon").html("&#xe803;");
            $("#workerName").html(robot.name || $.i18n("机器人"))
        }
    });
    (function () {
        var doms = {}, showPnl = $("#show")[0], msgsCon = $("#msgs")[0], histCon = $("#hists"), newest = {}, nextnew;
        store.connect("messages", function (st, old, state) {
            var msg, dom, frag, fg, atbottom = showPnl.scrollHeight - showPnl.scrollTop < 10 + showPnl.clientHeight;
            for (var i = 0; i < st.length; i++) {
                msg = st[i];
                if (!(msg.id in doms) || doms[msg.id].msg !== msg || !doms[msg.id].dom.length) {
                    if (!(msg.id in doms && doms[msg.id].msg === msg)) {
                        frag = buildMsg(msg);
                        fg || (fg = document.createDocumentFragment());
                        for (var j = 0; j < frag.length; j++) {
                            fg.appendChild(frag[j])
                        }
                        doms[msg.id] = {msg: msg, dom: frag}
                    }
                } else {
                    if (fg) {
                        msgsCon.insertBefore(fg, doms[msg.id].dom[0]);
                        fg = void 0
                    }
                }
                if (msg.id in doms && doms[msg.id].dom.length) {
                    nextnew = msg
                }
            }
            if (fg) {
                msgsCon.appendChild(fg);
                fg = void 0
            }
            if (atbottom) {
                showPnl.scrollTop = showPnl.scrollHeight
            }
            if (nextnew && nextnew.id !== newest.id) {
                if (nextnew.dir !== 1) {
                    var text;
                    if (nextnew.type === $.msgTypes.TEXT) {
                        text = $.emojiInChange(nextnew.json.text || $.i18n("新消息"))
                    } else if (msg.type) {
                        text = $.i18n("[新消息：") + $.msgTypeZh(nextnew.type) + "]"
                    }
                    store.dispatch("BUBBLE_MSG", text)
                }
                newest = nextnew
            }
        });
        store.connect("hists", function (st, old, state) {
            if (state.system.need) {
                histCon.prepend(showHist(st, state.system.need))
            }
        });
        store.connect("system.need", function (st, old, state) {
            if (st) {
                histCon.prepend(showHist(state.hists, st))
            }
        });

        function showHist(hists, need) {
            var len = hists.length, msg, frag, fg = document.createDocumentFragment(), ret = [];
            for (var i = len - 1; i >= Math.max(len - need, 0); i--) {
                msg = hists[i];
                if (!(msg.id in doms)) {
                    frag = buildMsg(msg);
                    dom = frag[frag.length - 1];
                    doms[msg.id] = {msg: msg, dom: dom};
                    if (frag.length) {
                        ret = ret.concat(frag)
                    }
                }
            }
            for (var i = ret.length - 1; i >= 0; i--) {
                fg.appendChild(ret[i])
            }
            return fg
        }

        store.connect("workers", function (st, old, state) {
        });
        store.connect("rels", function (st, old, state) {
            st || (st = []);
            var relsCon = $("#rels");
            relsCon.html("");
            for (var i = 0; i < st.length; i++) {
                if (st[i].dir === 8) {
                    var li = document.createElement("li");
                    li.className = "rel-item";
                    li.title = $.i18n("点击发送问题");
                    li.innerHTML = $.emojiInChange(st[i].json.text);
                    relsCon.append(li);
                    var show_related = state.site.control.show_related;
                    if (show_related && !/active/i.test($("#rel-tab")[0].className)) {
                        $("#rel-tab").addClass("active");
                        $("#hot-tab").rmClass("active");
                        $(".pnl-hot").css("display", "none");
                        $(".pnl-rel").css("display", "block")
                    }
                }
            }
        })
    })();
    store.handle({
        INIT_STATE: function (state, payload) {
            var json = payload.system;
            if (json) {
                state = Object.assign({}, state);
                state.system = Object.assign({}, state.system, json);
                var debug = json.debug, ssl = json.ssl, url = state.system.url;
                url.ws = "ws" + (ssl ? "s" : "") + "://" + $.autoHost("chat") + "/" + (debug ? "debug/" : "") + "sitews";
                if (debug) {
                    url.poll = url.poll.replace(/public/, "debug");
                    url.upload = url.upload.replace(/public/, "debug");
                    url.upurl = url.upurl.replace(/public/, "debug")
                }
            }
            return state
        }, TOGGLE_VOICE: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.voice = $.type(payload) !== "undefined" ? payload : !state.system.voice;
            return state
        }, SYSTEM_EXIT: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.exit = true;
            return state
        }, BEGIN_ANALYSIS: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.analysis = true;
            return state
        }, HIST_NOMORE: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.nomore = true;
            return state
        }, WS_OPENED: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.ws = payload;
            state.system.wsstate = true;
            return state
        }, WS_CLOSED: function (state, payload) {
            state = Object.assign({}, state);
            state.system = Object.assign({}, state.system);
            state.system.ws = payload;
            state.system.wsstate = false;
            return state
        }, LOAD_HIST: function (state, payload) {
            if (payload) {
                state = Object.assign({}, state);
                state.system = Object.assign({}, state.system);
                state.system.need = (state.system.need || 0) + 5;
                state.system.nomore && state.system.need > state.hists.length && (state.system.need = state.hists.length)
            }
            return state
        }, LOG_UNREAD: function (state, payload) {
            var num = state.messages.length;
            if (num >= 0) {
                state = Object.assign({}, state);
                state.system = Object.assign({}, state.system);
                state.system.unread = num
            }
            return state
        }
    }, {
        system: {
            voice: true,
            debug: false,
            ssl: false,
            url: {
                ws: "ws://" + autoHost("chat") + "/sitews",
                poll: "//" + autoHost("chat") + "/public/webauth/v9",
                upload: "//" + autoHost("chat") + "/public/wxyt/web",
                upurl: "//" + autoHost("vos") + "/public/upload/",
                site: "//" + autoHost("apis") + "/public/api_dkf/get_chat_siteinfo",
                histMode: "//" + autoHost("apis") + "/public/api_stat/get_visitor_chat",
                hist: "//" + autoHost("apis") + "/public/api_wkby/get_messages",
                hotQues: "//" + autoHost("apis") + "/public/api_dkf/get_hot_ques",
                autoQues: "//" + autoHost("apis") + "/public/api_dkf/get_site_ques"
            }
        }
    });

    function autoHost(i) {
        if ($.debug) {
            switch (i) {
                case"www":
                    return "www.v5kf.com";
                case"apis":
                    return "apis.v5kf.com";
                case"static":
                    return "static.v5kf.com";
                case"rs":
                    return "rs.v5kf.com";
                case"vos":
                    return "vos.v5kf.com";
                case"chat":
                default:
                    return "chat.v5kf.com"
            }
        } else {
            if (/\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/i.test(window.location.hostname)) {
                return location.host
            } else {
                if (!i || !$.deploy) {
                    return location.host
                } else {
                    var arr = location.host.split(".");
                    arr[0] = i;
                    return arr.join(".")
                }
            }
        }
    }

    store.handle({
        INIT_STATE: function (state, payload) {
            var json = payload.site;
            if (json) {
                state = Object.assign({}, state);
                state.site = Object.assign({}, state.site, json, {links: [].concat(state.site.links)})
            }
            return state
        }
    }, {
        site: {
            id: null,
            name: "在线咨询",
            logo: "",
            home: "www.v5kf.com",
            aid: "",
            about: "v5智能客服向前一小步、客服工作效率向前一大步，产品改善和用户满意是我们的不懈追求。",
            busy: "当前客服繁忙，如果不能及时回复，请耐心稍后！",
            hcolor: "",
            himg: "",
            hlogo: "",
            tel: "",
            wait: "很抱歉，目前在线人工坐席忙，您可以先描述遇到的问题，谢谢！",
            note: {address: "0", content: "2", email: "2", gender: "0", phone: "1", qq: "1", username: "2"},
            control: {show_facetime: true},
            theme: {color: "#1881d3"}
        }
    });
    store.handle({
        INIT_STATE: function (state, payload) {
            var json = payload.robot;
            if (json) {
                state = Object.assign({}, state);
                state.robot = Object.assign({}, state.robot, json)
            }
            return state
        }
    }, {robot: {name: "机器人", logo: "", desc: "智能客服"}});
    store.handle({
        INIT_STATE: function (state, payload) {
            var json = payload.guest;
            if (json) {
                state = Object.assign({}, state);
                state.guest = Object.assign({}, state.guest, json)
            }
            return state
        }, AUTH_RESET: function (state, payload) {
            state = Object.assign({}, state);
            state.guest = Object.assign({}, state.guest, {auth: null});
            return state
        }
    }, {guest: {id: ""}});
    store.handle({
        INIT_STATE: function (state, payload) {
            var json = payload.workers;
            if (json) {
                state = Object.assign({}, state);
                state.workers = Object.assign({}, state.workers, json)
            }
            return state
        }, BACK_STATUS: function (state, payload) {
            var json = payload;
            if (json && json.w_id) {
                state = Object.assign({}, state);
                state.workers = Object.assign({}, state.workers);
                state.workers[json.w_id] = Object.assign({}, state.workers[json.w_id], {
                    id: json.w_id,
                    name: json.nickname,
                    photo: $.setProtocol(json.photo)
                })
            }
            return state
        }
    }, {workers: {}});
    var unreads = [];
    store.handle({
        SEND_MSG: function (state, payload) {
            var json = payload;
            if (json) {
                state = Object.assign({}, state);
                state.messages = [].concat(state.messages || []);
                if (/23|24|27/.test(json.type) || json.type === 5) {
                    var id = json.id, msg;
                    for (var i = state.messages.length - 1; i >= 0; i--) {
                        msg = state.messages[i];
                        if (msg.id === id) {
                            msg.json = json.json;
                            break
                        }
                    }
                } else {
                    if (json.type === 2 && json.json && json.json.imgId) {
                        var img = json.json;
                        if (img.file && !img.error && !img.src) {
                            json.status = 1;
                            state.messages.push(json)
                        } else {
                            var imgId = img.imgId, msg;
                            for (var i = state.messages.length - 1; i >= 0; i--) {
                                msg = state.messages[i];
                                if (msg.json && msg.id === imgId) {
                                    msg.json = Object.assign({}, msg.json, img);
                                    msg.status = img.src ? 0 : -1;
                                    break
                                }
                            }
                            if (i < 0) {
                                json.status = img.src ? 0 : -1;
                                state.messages.push(json)
                            }
                            var imgMask = null;
                            try {
                                imgMask = document.getElementById("img" + imgId.substring(0, 13))
                            } catch (e) {
                                console.warn("imgMask exception", e)
                            }
                            if (imgMask) {
                                img.src ? imgMask.parentNode.removeChild(imgMask) : $(imgMask).addClass("refresh")
                            }
                        }
                    } else {
                        state.messages.push(json)
                    }
                }
            }
            return state
        }, BACK_MSG: function (state, payload) {
            var json = payload;
            if (json && json.message_type === 25 && json.code === 5) {
                json.message_type = 24;
                json.unsend = true
            }
            if (json && (json = $.fMsg(json))) {
                state = Object.assign({}, state);
                if (json.dir !== 8) {
                    state.messages = [].concat(state.messages || []);
                    state.messages.push(json)
                } else {
                    state.rels = json.robot
                }
            }
            return state
        }, BACK_UNREAD: function (state, payload) {
            var json = payload, finish = payload.finish;
            if (json && (json = json.messages) && json.length) {
                for (var i = 0; i < json.length; i++) {
                    var msg = $.fMsg(json[i]);
                    msg && unreads.unshift(msg)
                }
            }
            if (finish) {
                unreads.sort(function (a, b) {
                    if (a.id > b.id) {
                        return 1
                    } else if (a.id < b.id) {
                        return -1
                    } else {
                        return 0
                    }
                });
                unreads.length && $.showinfo($.i18n("收到") + unreads.length + $.i18n("条未读消息"));
                state = Object.assign({}, state);
                state.messages = [].concat(state.messages || []);
                var num = state.system.unread || 0;
                state.messages = state.messages.slice(0, num).concat(unreads).concat(state.messages.slice(num));
                unreads = []
            }
            return state
        }
    }, {messages: [], rels: []});
    store.handle({
        HIST_LOADED: function (state, payload) {
            var arr = payload;
            if (arr) {
                state = Object.assign({}, state);
                state.hists = arr.concat(state.hists || [])
            }
            return state
        }
    }, {
        historys: {
            sidVip: [105723, 155488],
            codeVip: ["W6IINA", "qetiB4"],
            code: "",
            sid: "",
            interface: "",
            openid: "",
            num: "",
            message_id: "",
            message_time: ""
        }, hists: [], preview: {img: {number: null, total: null, links: []}}
    });
    store.handle({
        BACK_STATUS: function (state, payload) {
            var json = payload;
            if (json) {
                state = Object.assign({}, state);
                if (json.service === "waiter" && json.w_id) {
                    state.current = Object.assign({}, state.current, {wid: json.w_id, status: json.status})
                } else if (json.service === "close" || json.service === "none") {
                    state.current = Object.assign({}, state.current, {wid: void 0, status: json.status})
                } else {
                    state.current = Object.assign({}, state.current, {status: json.status})
                }
            }
            return state
        }
    }, {current: {}});
    store.action("BEGIN_ANALYSIS", function () {
        return function (dispatch, getState) {
            var state = getState();
            if (state.system.debug || !$.deploy) {
                return
            }
            var img = new Image;
            img.src = "//img.users.51.la/17248473.asp";
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?15cc4c01359300c19f4c238145dc8c10";
            document.body.appendChild(hm)
        }
    });
    store.action("IFR_MAX", function () {
        return function (dispatch, getState) {
            var state = getState();
            if (state.system.embed) {
                window.parent && window.parent.postMessage('{v5: "max"}', "*");
                store.dispatch("CLOSE_WS")
            }
        }
    });
    store.action("IFR_MIN", function () {
        return function (dispatch, getState) {
            var state = getState();
            if (state.system.embed) {
                window.parent && window.parent.postMessage('{v5: "min"}', "*")
            }
        }
    });
    store.action("WS_OPENED", function () {
        return function (dispatch, getState) {
            var state = getState();
            if (state.system.embed) {
                console.log("WS_OPENED postMessage");
                window.parent && window.parent.postMessage('{v5: "open"}', "*")
            }
        }
    });
    store.action("SYSTEM_EXIT", function () {
        return function (dispatch, getState) {
            var state = getState(), sid = state.site.id, oid = state.guest.id, stamp = $.stamp(),
                wsstate = state.system.wsstate && (state.current.wid || state.current.status > 0);
            if (!sid || !oid) {
                return
            }
            if (state.site.nocache) return;
            $.cache(sid + "_lastout", wsstate ? stamp : 0);
            if (state.messages.length) {
                stamp = state.messages[0].stamp || $.stamp();
                $.cache("ht_" + oid + "_" + stamp, JSON.stringify(state.messages) || "");
                var htidx = JSON.parse($.cache("ht_" + oid) || "[]");
                $.cull(htidx, stamp);
                htidx.push(stamp);
                $.cache("ht_" + oid, JSON.stringify(htidx) || "");
                state.messages = []
            }
        }
    });
    store.action("CACHE_MSGS", function () {
        return function (dispatch, getState) {
            var state = getState(), sid = state.site.id, oid = state.guest.id, stamp = $.stamp(),
                wsstate = state.system.wsstate && state.current.wid;
            if (!sid || !oid) {
                return
            }
            if (state.site.nocache) return;
            $.cache(sid + "_lastout", wsstate ? stamp : 0);
            if (state.messages.length) {
                stamp = state.messages[0].stamp || $.stamp();
                var msgs = [];
                for (var i = 0; i < state.messages.length; i++) {
                    var msg = state.messages[i];
                    if (msg.type == 2 && msg.dir == 1) {
                        msgs[i] = {
                            dir: msg.dir,
                            id: msg.id,
                            json: {file: "", imgId: msg.json.imgId, name: msg.json.name, src: msg.json.src},
                            stamp: msg.stamp,
                            status: msg.status,
                            type: msg.type
                        }
                    } else {
                        msgs[i] = msg
                    }
                }
                $.cache("ht_" + oid + "_" + stamp, JSON.stringify(msgs) || "");
                var htidx = JSON.parse($.cache("ht_" + oid) || "[]");
                $.cull(htidx, stamp);
                htidx.push(stamp);
                $.cache("ht_" + oid, JSON.stringify(htidx) || "")
            }
        }
    });
    store.action("BUBBLE_MSG", function (text) {
        return function (dispatch, getState) {
            var state = getState();
            if (!$.ua.isIe || $.ua.ie > 9) {
            }
            if (state.system.voice) {
                var audios = ["ad_c48ea069ba.mp3", "Agile_0997e67d9b.ogg", "Arrival_fa449f6806.ogg", "Chocolate_b22cc761dc.ogg", "Cleverer_13116af784.ogg", "CodeWord_e7e84b01c8.ogg", "Doorbell_e11282f024.ogg", "Drop_834e4aecc8.ogg", "Gift_8975656e1d.ogg", "Jump_204ab79b03.ogg", "Knock_7943492084.wav", "Reminder_7516cde317.ogg", "Ripples_6cd80b0f36.ogg", "Tapping_72e1baeef3.ogg", "Triumph_7dc6ed660f.ogg", "Win8_3acbe8b221.mp3"];
                $.playAudio("lib/files/audios/" + audios[6])
            }
            if (state.system.embed && text) {
                var name = state.workers[state.current.wid] && state.workers[state.current.wid].name || state.robot.name;
                var avatar = state.workers[state.current.wid] && state.workers[state.current.wid].photo || state.robot.logo;
                window.parent && window.parent.postMessage(JSON.stringify({
                    v5: "msg",
                    msg: text,
                    name: name,
                    avatar: avatar
                }), "*")
            }
        }
    });
    store.action("GET_SITE_INFO", function () {
        return function (dispatch, getState) {
            var state = getState(), url = state.system.url.site, aid = state.site.aid || "", sid = state.site.id;
            $.ajax(url, {
                data: {sid: sid, aid: aid, channel: "web"}, success: function (text) {
                    var json = JSON.parse(text), site = json.info, robot = json.robot;
                    json = {
                        site: {
                            update: true,
                            aid: state.site.aid || site.account_id,
                            about: site.about,
                            busy: site.busyalert,
                            name: site.company,
                            intro: site.intro,
                            header: {
                                color: site.header_bg_color,
                                img: $.setProtocol(site.header_bg_image),
                                smcolor: site.mobile_theme_color,
                                smimg: $.setProtocol(site.sm_header_bg_image),
                                logo: $.setProtocol(site.header_logo),
                                bodybg: $.setProtocol(site.body_bg_image)
                            },
                            logo: $.setProtocol(site.logo),
                            tel: site.phone,
                            wait: site.waitalert,
                            home: site.weburl,
                            note: site.msgcfg,
                            control: json.session && json.session.control,
                            copyright: json.copyright || {},
                            banner: site.newshot && site.newshot.showmode == 1 && site.newshot.value,
                            sliders: site.swiper && site.swiper.showmode == 1 && site.swiper.sliders,
                            histMode: json.session && json.session.history_mode,
                            nocache: false,
                            chathist: {
                                code: state.historys.code || state.site.nocache && state.historys.codeVip[state.historys.sidVip.indexOf(state.site.id)],
                                sid: state.historys.sid || state.site.id,
                                interFace: state.historys.interface || 768,
                                openid: state.historys.openid || state.guest.id,
                                num: state.historys.num || 100,
                                message_id: state.historys.message_id,
                                message_time: state.historys.message_time
                            }
                        },
                        robot: {
                            update: true,
                            name: robot && robot.name,
                            logo: $.setProtocol(robot && robot.logo),
                            intro: robot && robot.intro,
                            desc: robot && robot.desc,
                            web_btns_url: $.setProtocol(robot && robot.web_btns_url)
                        }
                    };
                    dispatch("INIT_STATE", json);
                    store.dispatch("GET_WEB_CHAT_BTNS");
                    var lastUpdate = $.cache(sid + "_lastUpdateQues") * 1 || 0;
                    if (json.site.control && json.site.control.learn_search && $.stamp() - lastUpdate > 7 * 24 * 3600 * 1e3) {
                        store.dispatch("GET_AUTO_QUES")
                    }
                    setTimeout(function () {
                        window.parent && window.parent.postMessage('{v5: "load"}', "*")
                    }, 0);
                    $.cache(sid + "_site", JSON.stringify(json.site));
                    $.cache(sid + "_robot", JSON.stringify(json.robot));
                    $("#mask").addClass("hide");
                    $("#mask").rmClass("unclose")
                }, error: function (err) {
                    console.log("site:" + err);
                    $("#loading").addClass("refresh")
                }
            })
        }
    });
    store.action("GET_WEB_CHAT_BTNS", function () {
        return function (dispatch, getState) {
            var state = getState(), url = state.robot.web_btns_url, account = state.site.aid || "",
                openid = state.guest.id;
            url && $.ajax({
                url: url + (/\?/g.test(url) ? "&" : "?") + "account=" + account + "&openid=" + openid + "&channel=web",
                success: function (text) {
                    var json = JSON.parse(text);
                    dispatch("INIT_STATE", {site: {webChatBtns: {btns: json, index: []}}})
                }
            })
        }
    });
    store.action("GET_SITE_QUES", function () {
        return function (dispatch, getState) {
            var state = getState(), url = state.system.url.hotQues, aid = state.site.aid || "", sid = state.site.id;
            $.ajax(url, {
                data: {sid: sid, aid: aid, channel: "web"}, success: function (text) {
                    var json = JSON.parse(text);
                    if (json && json.state == "ok") {
                        if (getState().site.hotQues && getState().site.hotQues.length > 0) {
                            return
                        }
                        dispatch("INIT_STATE", {site: {hotQues: json.items || []}});
                        if (json.items) {
                            $.cache(sid + "_ques", JSON.stringify(json.items) || "")
                        }
                    }
                }
            })
        }
    });
    store.action("GET_AUTO_QUES", function () {
        return function (dispatch, getState) {
            var state = getState(), url = state.system.url.autoQues, aid = state.site.aid, sid = state.site.id;
            $.ajax(url, {
                data: {sid: sid, aid: aid, channel: "web"}, success: function (text) {
                    var json = JSON.parse(text);
                    $.cache(sid + "_lastUpdateQues", $.stamp());
                    if (json && json.state == "ok") {
                        dispatch("INIT_STATE", {site: {autoQues: json.items || []}});
                        if (json.items) {
                            $.cache(sid + "_autoQues", JSON.stringify(json.items) || "")
                        }
                    }
                }
            })
        }
    });
    (function () {
        var ws_retry = 0;
        var human_got = 0;
        var ws = {
            send: function (d) {
                reConnect(function () {
                    ws.send(d)
                })
            }
        };
        store.action("OPEN_WS", function () {
            return function (dispatch, getState) {
                reConnect(function () {
                    ws.send("beat")
                })
            }
        });
        store.action("CLOSE_WS", function () {
            return function (dispatch, getState) {
                if (getState().system.wsstate) {
                    ws.close();
                    ws = {
                        send: function (d) {
                            reConnect(function () {
                                ws.send(d)
                            })
                        }
                    }
                }
            }
        });
        store.action("SEND_MAGIC", function (magic) {
            return function (dispatch, getState) {
                magic && magic.magic && ($.magic = ($.magic || []).concat(magic.magic));
                magic && magic.real && ($.real = Object.assign($.real || {}, magic.real));
                if (getState().system.wsstate) {
                    if ($.magic && $.magic.length) {
                        var json = {
                            mjson: {magic_arr: $.magic, real_json: $.real},
                            code: 2,
                            message_type: 26,
                            o_type: "message"
                        };
                        ws.send(json);
                        $.magic = [];
                        $.real = {}
                    }
                }
            }
        });
        store.action("SEND_MSG", function (json) {
            return function (dispatch, getState) {
                setTimeout(function () {
                    var showPnl = $("#show")[0];
                    showPnl.scrollTop = showPnl.scrollHeight
                }, 10);
                if (json.type === 2 && json.status) {
                    return
                }
                json = $.pMsg(json);
                if (getState().system.wsstate) {
                    $.magic && $.magic.length && (json.custom_content = [].concat($.magic), $.magic = [])
                }
                ws.send(json);
                store.dispatch("CACHE_MSGS")
            }
        });
        store.action("GET_STATUS", function () {
            return function (dispatch, getState) {
                ws.send({o_type: "session", o_method: "get_status"})
            }
        });
        store.action("GET_UNREAD", function () {
            return function (dispatch, getState) {
                ws.send({o_type: "session", o_method: "get_messages", size: 30})
            }
        });
        store.action("BACK_UNREAD", function (json) {
            return function (dispatch, getState) {
                if (!json.finish) {
                    dispatch("GET_UNREAD")
                }
            }
        });
        store.action("GET_HUMAN", function (payload) {
            return function (dispatch, getState) {
                var state = getState(), json = {code: 1};
                if (state.current.wid) {
                    $.showinfo("人工客服正在为您服务");
                    return
                }
                payload || (payload = getState().system.human);
                if (payload && (payload.wid || payload.gid)) {
                    json.argc = 2;
                    json.argv = (payload.gid || "0") + " " + (payload.wid || "0")
                }
                if (payload.human == 3 || payload == 3) {
                    json.code = 4
                }
                if (state.system.wsstate || payload.human == 1 || payload == 1) {
                    ws.send($.pMsg({dir: 1, type: 25, json: json}));
                    human_got = 1
                } else {
                    state.system.human = payload
                }
            }
        });
        store.action("GET_ROBOT", function () {
            return function (dispatch, getState) {
                var state = getState();
                if (!state.current.wid) {
                    $.showinfo("智能机器人客服正在为您服务");
                    return
                }
                ws.send($.pMsg({dir: 1, type: 25, json: {code: 2}}))
            }
        });
        var reConnecting = false;

        function reConnect(cb) {
            if (reConnecting) {
                return
            }
            reConnecting = true;
            var state = store.getState();
            if (state.system.wsstate) {
                store.dispatch("CLOSE_WS")
            }
            if (!state.guest.auth) {
                if (!state.site.aid) {
                    setTimeout(function () {
                        webAuth(function () {
                            startTrans();
                            cb && cb();
                            reConnecting = false
                        })
                    }, 300)
                } else {
                    webAuth(function () {
                        startTrans();
                        cb && cb();
                        reConnecting = false
                    })
                }
                return
            } else {
                startTrans();
                cb && cb();
                reConnecting = false
            }
        }

        function webAuth(cb) {
            var state = store.getState();
            var sid, aid, oid, authurl;
            if (!sid || !aid || !oid || !authurl) {
                sid = state.site.id;
                aid = state.site.aid || state.site.p_id;
                oid = state.guest.id;
                authurl = state.system.url.poll
            }
            if (!aid) {
                $.showinfo("请联系客服人员到客服管理后台填写并完善网页接入");
                console.warn("请联系客服人员到客服管理后台填写并完善网页接入");
                cb && cb();
                return
            }
            $.ajax(authurl, {
                method: "post",
                data: JSON.stringify({
                    site: sid,
                    account: aid,
                    visitor: oid,
                    vip: parseInt(state.guest.vip),
                    avatar: state.guest.avatar,
                    nickname: state.guest.nickname || ""
                }),
                success: function (text) {
                    var json = JSON.parse(text);
                    if (!json.o_error) {
                        state.guest.auth = json.authorization;
                        if (json.websocket) {
                            var ws_url = json.websocket;
                            if (state.system.ssl) {
                                if (!/^wss/i.test(ws_url)) {
                                    /^ws/i.test(ws_url) && (ws_url = ws_url.replace(/ws/i, "wss"))
                                }
                            }
                            state.system.url.ws = ws_url
                        }
                        cb && cb()
                    } else {
                        console.error(authurl, json.o_errmsg);
                        cb && cb()
                    }
                }
            })
        }

        function startTrans() {
            var state = store.getState();
            var sk = $.io({
                url: state.system.url.poll,
                ws_url: getWsUrl(),
                getWsUrl: getWsUrl,
                timely: true,
                ssl: state.system.ssl,
                method: "get",
                data: JSON.stringify({
                    site: state.site.id,
                    account: state.site.aid,
                    nickname: state.guest.nickname,
                    visitor: state.guest.id,
                    avatar: state.guest.avatar
                }),
                onauth: function (text) {
                    var json = JSON.parse(text);
                    if (!json.authorization) {
                        return
                    }
                    store.dispatch("BACK_AUTH", json.authorization)
                },
                onopen: function (e) {
                    console.log($.stamp(), "ws opened");
                    ws_retry = 0;
                    store.dispatch("WS_OPENED", sk);
                    if (state.system.human && !human_got) {
                        store.dispatch("GET_HUMAN")
                    }
                    state.system.analysis || store.dispatch("BEGIN_ANALYSIS");
                    store.dispatch("GET_STATUS");
                    store.dispatch("LOG_UNREAD");
                    store.dispatch("GET_UNREAD");
                    store.dispatch("SEND_MAGIC")
                },
                onmessage: function (e) {
                    var json = e.data, state = store.getState(), delay = state.site.delay.json.timers, stamp, newDelay,
                        oldDelay;
                    if (json == "beat") {
                        return
                    }
                    $.isPlainObject(json) || (json = JSON.parse(json));
                    if ("o_error" in json) {
                        store.dispatch("BACK_ERR", json);
                        return
                    }
                    if (json.o_type === "message") {
                        state.site.delay.loadHist = false;
                        if (json.direction == 2) {
                            newDelay = Math.floor(Math.random() * (delay[1] - delay[0] + 1) + delay[0]);
                            console.warn("oldDelay:" + state.site.delay.time + "  newDelayime:" + newDelay + "  stamp:" + json.create_time);
                            if (state.site.delay.timer) {
                                oldDelay = state.site.delay.time;
                                setTimeout(function () {
                                    state.site.delay.time = newDelay;
                                    state.site.delay.stamp = json.create_time;
                                    state.site.delay.timer = setTimeout(function () {
                                        store.dispatch("BACK_MSG", json)
                                    }, json.direction / 2 * 1e3)
                                }, (oldDelay + json.direction / 2) * 1e3)
                            } else {
                                state.site.delay.time = newDelay;
                                state.site.delay.stamp = json.create_time;
                                state.site.delay.timer = setTimeout(function () {
                                    store.dispatch("BACK_MSG", json)
                                }, (json.direction == 0 ? 1 : newDelay / 2) * 1e3)
                            }
                        } else {
                            store.dispatch("BACK_MSG", json)
                        }
                        store.dispatch("CACHE_MSGS")
                    } else if (json.o_type === "session") {
                        if (json.o_method === "get_messages") {
                            store.dispatch("BACK_UNREAD", json)
                        } else if (json.o_method === "get_status") {
                            $.firstBackStatus === 1 && ($.firstBackStatus = 2);
                            $.firstBackStatus || ($.firstBackStatus = 1);
                            store.dispatch("BACK_STATUS", json);
                            window.parent && window.parent.postMessage('{v5:"status",status:' + json.status + "}", "*")
                        }
                    }
                },
                onerror: function (e) {
                    console.error(e);
                    if (state.system.wsstate) {
                        ws.close()
                    }
                    store.dispatch("AUTH_RESET");
                    if (ws_retry <= 3) {
                        ws_retry++;
                        reConnect(function () {
                            ws.send()
                        })
                    } else {
                        $.showinfo("连接错误，请检查您的网络连接")
                    }
                },
                onclose: function (e) {
                    console.warn("ws closed");
                    store.dispatch("SYSTEM_EXIT");
                    store.dispatch("WS_CLOSED", sk)
                }
            });
            ws = {
                send: function (json) {
                    if (typeof json == "string") {
                        sk.send(json)
                    } else {
                        sk.send(json ? JSON.stringify(json) : void 0)
                    }
                }, close: function () {
                    sk.close()
                }
            };

            function getWsUrl() {
                var state = store.getState();
                return state.system.url.ws + (state.guest.auth ? "?auth=" + encodeURIComponent(state.guest.auth) : "?site=" + state.site.id + "&o_id=" + state.guest.id + "&aid=" + state.site.aid) + (state.system.fake ? "&fake=" + state.system.fake + "&f_id=" + (state.site.f_id || 16777216) + "&p_id=" + state.site.p_id : "")
            }
        }
    })();
    (function () {
        var ht, oid, repet = {}, nomShow, ind = 0;
        store.action("LOAD_HIST", function () {
            return function (dispatch, getState) {
                var state = getState();
                state.site.delay.loadHist = true;
                if (ht && !ht.length && !state.site.nocache) {
                    if (state.system.nomore) {
                        if (!nomShow) {
                            nomShow = true;
                            $.loading = false;
                            showNomore()
                        }
                        return
                    }
                }
                if (state.hists.length >= (state.system.need || 0)) {
                    $.loading = false;
                    $("#histStart")[0].innerHTML = $.i18n("加载更多历史消息");
                    return
                }
                if (!ht) {
                    oid = state.guest.id;
                    ht = JSON.parse($.cache("ht_" + oid) || "[]")
                }
                if (state.current.wid) {
                    $("#welcome").addClass("hide")
                }
                if (sessionStorage[store.getState().site.id + "_defHot"]) {
                    $("#defHot").addClass("hide")
                }
                var hid = ht.pop(), msgs, json = {};
                if (state.site.histMode) {
                    var hasMore = true, max = 6, url = state.system.url.histMode, dateNow = nowMonth();
                    date = ind == 0 ? dateNow : prevMonths(dateNow, ind);
                    hasMore && $.ajax(url, {
                        data: {
                            site_id: state.site.id,
                            open_id: state.guest.id,
                            account_id: state.site.aid,
                            start_date: date
                        }, success: function (res) {
                            ind++;
                            json = JSON.parse(res);
                            var data = json.data, messages = [], arrs = [], histArr = [], nomore = state.system.nomore;
                            if (JSON.stringify(json.data) === "[]" || JSON.stringify(json.data) === "{}") {
                            } else {
                                for (var i in data) {
                                    var msgs = data[i].messages;
                                    for (var j = 0; j < msgs.length; j++) {
                                        if (msgs[j].direction == 0) {
                                            msgs[j].worker_id = data[i].worker_id
                                        }
                                        messages.push(msgs[j])
                                    }
                                }
                                arrs = messages.concat(arrs)
                            }
                            console.log("get成功", json, json.data, state);
                            hasMore = ind < max ? true : false;
                            if (ind < max) {
                                nomore = false
                            } else {
                                nomore = true
                            }
                            if (nomore) {
                                dispatch("HIST_NOMORE");
                                $.loading = false;
                                showNomore()
                            } else {
                                $("#histStart")[0].innerHTML = $.i18n("加载更多历史消息")
                            }
                            for (var i = 0; i < arrs.length; i++) {
                                var item = {};
                                item.wid = arrs[i].worker_id;
                                item.dir = arrs[i].direction * 1;
                                item.id = arrs[i].message_id;
                                item.type = arrs[i].message_type * 1;
                                item.stamp = new Date(arrs[i].create_time.replace(/\-/g, "/")).getTime();
                                if (item.type === 1 && arrs[i].text_content != "-") {
                                    item.json = Object.assign({}, {text: arrs[i].text_content})
                                } else if (item.type === 2) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].text_content.media_id,
                                        src: arrs[i].text_content.pic_url
                                    })
                                } else if (item.type === 3) {
                                    item.json = Object.assign({}, {
                                        lat: arrs[i].text_content.x,
                                        lng: arrs[i].text_content.y,
                                        scale: arrs[i].text_content.scale,
                                        title: arrs[i].text_content.title,
                                        label: arrs[i].text_content.label
                                    })
                                } else if (item.type === 6) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].text_content.media_id,
                                        mirror: arrs[i].text_content.url || null,
                                        format: arrs[i].text_content.format,
                                        text: arrs[i].text_content.recognition,
                                        src: arrs[i].text_content.url
                                    })
                                } else if (item.type === 7) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].text_content.media_id,
                                        mirror: arrs[i].text_content.url || null,
                                        format: arrs[i].text_content.format,
                                        text: arrs[i].text_content.recognition,
                                        src: arrs[i].text_content
                                    })
                                } else if (item.type === 9) {
                                    var Articles = arrs[i].text_content.articles, iLen = Articles.length, articles = [];
                                    if (iLen === 1) {
                                        item.json = Object.assign({}, {
                                            link: Articles[0].url,
                                            img: Articles[0].pic_url,
                                            name: Articles[0].title,
                                            desc: Articles[0].description
                                        })
                                    } else {
                                        for (var j = 0; j < iLen; j++) {
                                            var article = {};
                                            if (Articles[j].url) {
                                                article.link = Articles[j].url
                                            }
                                            if (Articles[j].pic_url) {
                                                article.img = Articles[j].pic_url
                                            }
                                            if (Articles[j].title) {
                                                article.name = Articles[j].title
                                            }
                                            if (Articles[j].description) {
                                                article.desc = Articles[j].description
                                            }
                                            articles.push(article)
                                        }
                                        item.json = articles
                                    }
                                } else if (item.type === 10) {
                                    item.json = Object.assign({}, {
                                        name: arrs[i].text_content.title,
                                        hqsrc: arrs[i].text_content.hq_music_url,
                                        desc: arrs[i].text_content.description,
                                        src: arrs[i].text_content.music_url
                                    })
                                }
                                if (item.type != 25 || item.type != 5) {
                                    histArr.push(item)
                                }
                            }
                            json.hists = histArr;
                            msgs = histArr;
                            if (!hasMore) {
                                showNomore();
                                return
                            }
                            if (msgs) {
                                dispatch("HIST_LOADED", compatOld(msgs, oid, hid))
                            } else {
                                dispatch("LOAD_HIST")
                            }
                        }, error: function (request, status, error) {
                            console.log("get失败", request, status, error)
                        }
                    })
                } else if (state.site.nocache) {
                    var hasMore = true, url = state.system.url.hist;
                    hasMore && $.ajax(url, {
                        data: {
                            code: state.site.chathist.code,
                            sid: state.site.chathist.sid,
                            interface: state.site.chathist.interFace,
                            openid: state.site.chathist.openid,
                            num: state.site.chathist.num,
                            message_id: state.site.chathist.message_id,
                            message_time: state.site.chathist.message_time,
                            t_: Date.now()
                        }, success: function (text) {
                            json = JSON.parse(text);
                            state.site.chathist.message_id = json.lastMsgId;
                            state.site.chathist.message_time = json.lastMsgTime;
                            hasMore = json.state === 1 ? true : false;
                            var arrs = json.items, histArr = [], nomore = state.system.nomore;
                            if (json.state === 1) {
                                nomore = false
                            } else if (json.state === 2) {
                                nomore = true
                            }
                            if (nomore) {
                                dispatch("HIST_NOMORE");
                                $.loading = false;
                                showNomore()
                            } else {
                                $("#histStart")[0].innerHTML = $.i18n("加载更多历史消息")
                            }
                            for (var i = 0; i < arrs.length; i++) {
                                var item = {};
                                item.wid = arrs[i].w_id;
                                item.dir = arrs[i].direction * 1;
                                item.o_type = arrs[i].o_type;
                                item.id = arrs[i].message_id;
                                item.msg_id = arrs[i].msg_id;
                                item.type = arrs[i].message_type * 1;
                                item.stamp = arrs[i].create_time * 1e3;
                                if (item.type === 1) {
                                    item.json = Object.assign({}, {text: arrs[i].content || arrs[i].text || arrs[i].json.content || arrs[i].json.text})
                                } else if (item.type === 2) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].media_id || arrs[i].json.media_id,
                                        src: arrs[i].pic_url || arrs[i].json.pic_url
                                    })
                                } else if (item.type === 3) {
                                    item.json = Object.assign({}, {
                                        lat: arrs[i].x || arrs[i].json.x,
                                        lng: arrs[i].y || arrs[i].json.y,
                                        scale: arrs[i].scale || arrs[i].json.scale,
                                        title: arrs[i].title || arrs[i].json.title,
                                        label: arrs[i].label || arrs[i].json.label
                                    })
                                } else if (item.type === 6) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].media_id || arrs[i].json.media_id,
                                        mirror: arrs[i].url || arrs[i].json.url || null,
                                        format: arrs[i].format || arrs[i].json.format,
                                        text: arrs[i].recognition || arrs[i].json.recognition,
                                        src: arrs[i].url || arrs[i].json.url
                                    })
                                } else if (item.type === 7) {
                                    item.json = Object.assign({}, {
                                        mid: arrs[i].media_id || arrs[i].json.media_id,
                                        mirror: arrs[i].url || arrs[i].json.url || null,
                                        format: arrs[i].format || arrs[i].json.format,
                                        text: arrs[i].recognition || arrs[i].json.recognition,
                                        src: arrs[i].url || arrs[i].json.url
                                    })
                                } else if (item.type === 9) {
                                    var Articles = arrs[i].articles || arrs[i].json.articles, iLen = Articles.length,
                                        articles = [];
                                    if (iLen === 1) {
                                        item.json = Object.assign({}, {
                                            link: Articles[0].url,
                                            img: Articles[0].pic_url,
                                            name: Articles[0].title,
                                            desc: Articles[0].description
                                        })
                                    } else {
                                        for (var j = 0; j < iLen; j++) {
                                            var article = {};
                                            if (Articles[j].url) {
                                                article.link = Articles[j].url
                                            }
                                            if (Articles[j].pic_url) {
                                                article.img = Articles[j].pic_url
                                            }
                                            if (Articles[j].title) {
                                                article.name = Articles[j].title
                                            }
                                            if (Articles[j].description) {
                                                article.desc = Articles[j].description
                                            }
                                            articles.push(article)
                                        }
                                        item.json = articles
                                    }
                                } else if (item.type === 10) {
                                    item.json = Object.assign({}, {
                                        name: arrs[i].title || arrs[i].json.title,
                                        hqsrc: arrs[i].hq_music_url || arrs[i].json.hq_music_url,
                                        desc: arrs[i].description || arrs[i].json.description,
                                        src: arrs[i].music_url || arrs[i].json.music_url
                                    })
                                }
                                item && histArr.unshift(item)
                            }
                            json.hists = histArr;
                            msgs = histArr;
                            console.log(msgs, "success--------------------", json, state, state.system.nomore);
                            if (!hasMore) {
                                showNomore();
                                return
                            }
                            if (msgs) {
                                dispatch("HIST_LOADED", compatOld(msgs, oid, hid))
                            } else {
                                dispatch("LOAD_HIST")
                            }
                        }
                    })
                } else if (hid) {
                    msgs = JSON.parse($.cache("ht_" + oid + "_" + hid) || "null");
                    if (msgs) {
                        dispatch("HIST_LOADED", compatOld(msgs, oid, hid))
                    } else {
                        dispatch("LOAD_HIST")
                    }
                } else {
                    dispatch("HIST_NOMORE");
                    $.loading = false;
                    showNomore()
                }
            }
        });
        store.action("HIST_LOADED", function (arr) {
            return function (dispatch, getState) {
                var state = getState(), need = state.system.need || 0, nomore = state.system.nomore;
                if (state.hists.length < need && !nomore) {
                    dispatch("LOAD_HIST")
                } else {
                    $.loading = false
                }
                if (nomore) {
                    showNomore()
                } else {
                    $("#histStart")[0].innerHTML = $.i18n("加载更多历史消息")
                }
            }
        });

        function getHistory(url, data) {
        }

        function nowMonth() {
            var now = new Date, y, mon, d, date;
            y = now.getFullYear();
            mon = now.getMonth() + 1;
            d = now.getDate();
            return y + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (d < 10 ? "0" + d : d)
        }

        function prevMonth(date, index) {
            var aDate = date.split("-"), y = parseInt(aDate[0]), mon = parseInt(aDate[1]), d = parseInt(aDate[2]);
            if (mon - index > 0) {
                mon = mon - index
            } else {
                y = y - 1;
                mon = mon - index + 12
            }
            return y + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (d < 10 ? "0" + d : d)
        }

        function prevMonths(date, index) {
            var aDate = date.split("-"), y = parseInt(aDate[0]), mon = parseInt(aDate[1]), d = parseInt(aDate[2]);
            var mons = index % 12, ys = Math.floor(index / 12);
            if (ys === 0) {
                return prevMonth(date, index)
            } else {
                y = y - ys;
                var newDate = y + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (d < 10 ? "0" + d : d);
                return prevMonth(newDate, mons)
            }
        }

        function showNomore() {
            $("#histStart")[0].innerHTML = $.i18n("没有更多了");
            setTimeout(function () {
                var stamp = store.getState().hists.length && store.getState().hists[0].stamp || store.getState().messages.length && store.getState().messages[0].stamp || $.stamp();
                $("#histStart")[0].innerHTML = $.fTime(stamp, "smart hh:mm:ss")
            }, 1e3)
        }

        function compatOld(msgs, oid, hid) {
            if (msgs.length && !(msgs[0].stamp && msgs[0].stamp > 2542896e3)) {
                var msg, nostamp;
                for (var i = msgs.length - 1; i >= 0; i--) {
                    msg = msgs[i];
                    nostamp = !msg.stamp;
                    msg = $.fMsg(msg);
                    if (msg) {
                        msg.id || (msg.id = hid + "-" + i);
                        nostamp && delete msg.stamp;
                        msgs[i] = msg
                    } else {
                        msgs.splice(i, 1)
                    }
                }
                $.cache("ht_" + oid + "_" + hid, JSON.stringify(msgs));
                console.log("更新缓存：ht_" + oid + "_" + hid)
            }
            return msgs
        }
    })();
    $(".tab-btn").click(function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (target) {
            switch (target.id) {
                case"hot-tab":
                    if (!/active/i.test(target.className)) {
                        $("#hot-tab").addClass("active");
                        $("#rel-tab").rmClass("active");
                        $(".pnl-hot").css("display", "block");
                        $(".pnl-rel").css("display", "none")
                    }
                    break;
                case"rel-tab":
                    if (!/active/i.test(target.className)) {
                        $("#rel-tab").addClass("active");
                        $("#hot-tab").rmClass("active");
                        $(".pnl-hot").css("display", "none");
                        $(".pnl-rel").css("display", "block")
                    }
                    break
            }
        }
    });
    $(".slider-item").click(function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (target) {
            console.log("click slider:" + target.className)
        }
    });
    var relDblClick = function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement, link;
        if (target && /rel-item/i.test(target.className)) {
            store.dispatch("SEND_MSG", {
                id: $.sendid(),
                dir: 1,
                type: 1,
                stamp: $.stamp(),
                json: {text: target.innerHTML}
            })
        }
    };
    $("#rels").click(relDblClick);
    $("#hots").click(relDblClick);
    $("#ques").click(function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (target && /q-item/i.test(target.className)) {
            store.dispatch("SEND_MSG", {
                id: $.sendid(),
                dir: 1,
                type: 1,
                stamp: (new Date).getTime(),
                json: {text: target.innerHTML}
            });
            if ($.isPhone) {
            } else {
                $("#quesPnl").addClass("hide");
                $("#pnlExt").addClass("hide")
            }
        }
    });
    var loadtimer;
    $("#show")[0].onwheel = $("#show")[0].onmousewheel = function (e) {
        e || (e = window.event);
        if (this.scrollTop <= 0) {
            if (e && !e.deltaY) {
                e.deltaY = -e.wheelDelta / 40
            }
            if (!$.loading && e.deltaY < 0) {
                loadHist(true)
            }
            if (e.deltaY < 0) {
                if (e.preventDefault) {
                    e.preventDefault()
                } else {
                    e.returnValue = false
                }
            }
        } else if (this.scrollTop + this.clientHeight >= this.scrollHeight) {
            if (e.deltaY > 0) {
                if (e.preventDefault) {
                    e.preventDefault()
                } else {
                    e.returnValue = false
                }
            }
        }
        if (e.stopPropagation) {
            e.stopPropagation()
        } else {
            e.cancelBubble = true
        }
    };
    var touchY, touchYN;
    $("#show")[0].ontouchstart = function (e) {
        $.isPhone && $("#text")[0].blur();
        if (this.scrollTop <= 0) {
            e || (e = window.event);
            touchY = e.touches[0].clientY
        }
    };
    $("#show")[0].ontouchmove = function (e) {
        if (this.scrollTop <= 0) {
            e || (e = window.event);
            touchYN = e.touches[0].clientY
        }
    };
    $("#show")[0].ontouchend = $("#show")[0].ontouchcancel = function () {
        if (!$.loading && this.scrollTop <= 0 && touchYN > touchY + 5) {
            loadHist()
        }
    };
    $("#histStart")[0].onclick = function () {
        if (store.getState().system.nomore) {
            if (!nomShow) {
                showNoMore()
            }
        } else {
            loadHist()
        }
    };
    var nomShow;

    function loadHist(isMouse) {
        var state = store.getState();
        if (state.system.nomore) {
            if (!nomShow) {
                showNoMore()
            }
            return
        }
        $.loading = true;
        $("#histStart")[0].innerHTML = '<div class="abs cover loading"></div>';
        var timer = Math.round(Math.random() * 200);
        setTimeout(function () {
            store.dispatch("LOAD_HIST", true)
        }, timer > 50 ? timer : 50);
        loadtimer && (loadtimer = clearTimeout(loadtimer));
        if (!isMouse) {
            loadtimer = setTimeout(function () {
                loadtimer = void 0;
                $.loading = false
            }, 1e3)
        }
    }

    function showNoMore() {
        $("#histStart")[0].innerHTML = $.i18n("没有更多了");
        setTimeout(function () {
            var stamp = store.getState().hists.length && store.getState().hists[0].stamp || store.getState().messages.length && store.getState().messages[0].stamp || $.stamp();
            $("#histStart")[0].innerHTML = $.fTime(stamp, "smart hh:mm:ss")
        }, 1500)
    }

    $("#close")[0].onclick = function () {
        $(".msg-banner").addClass("hide");
        $("#show").css("padding-top", "6px")
    };
    var simAtoB = function () {
        function isStartWith(src, des) {
            for (var i = 0; i < src.length && i < des.length; i++) {
                if (src[i] !== des[i]) {
                    return false
                }
            }
            return true
        }

        function isContains(src, des) {
            var re = new RegExp(src, "im");
            return re.test(des)
        }

        function ld(s1, s2) {
            var d = [];
            var i, j, tmp, ch1, ch2, n = s1.length, m = s2.length;
            if (n === 0) {
                return m
            }
            if (m === 0) {
                return n
            }
            for (var x = 0; x < n + 1; x++) {
                d[x] = []
            }
            for (i = 0; i <= n; i++) {
                d[i][0] = i
            }
            for (j = 0; j <= m; j++) {
                d[0][j] = j
            }
            for (i = 1; i <= n; i++) {
                ch1 = s1[i - 1];
                for (j = 1; j <= m; j++) {
                    ch2 = s2[j - 1];
                    if (ch1 == ch2) {
                        tmp = 0
                    } else {
                        tmp = 1
                    }
                    d[i][j] = Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + tmp)
                }
            }
            return d[n][m]
        }

        function commonSubstrLen(s1, s2) {
            var d = [];
            var i, j, tmp, ch1, ch2, n = s1.length, m = s2.length;
            if (n === 0 || m === 0) {
                return 0
            }
            for (var x = 0; x < n + 1; x++) {
                d[x] = []
            }
            for (i = 0; i <= n; i++) {
                d[i][0] = 0
            }
            for (j = 0; j <= m; j++) {
                d[0][j] = 0
            }
            for (i = 1; i <= n; i++) {
                ch1 = s1[i - 1];
                for (j = 1; j <= m; j++) {
                    ch2 = s2[j - 1];
                    if (ch1 == ch2) {
                        d[i][j] = d[i - 1][j - 1] + 1
                    } else {
                        d[i][j] = Math.max(d[i - 1][j], d[i][j - 1])
                    }
                }
            }
            return d[n][m]
        }

        function simAtoB(s1, s2) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
            var d = commonSubstrLen(s1, s2), un = .5 * d / s1.length + .5 * d / s2.length;
            return un
        }

        return simAtoB
    }();

    function typing(bool) {
        var content = $("#text")[0].value;
        var msg = {
            id: $.sendid(),
            type: 5,
            dir: 1,
            stamp: $.stamp(),
            json: {event: "typing", cancel: bool, content: $.emojiOutChange(content)}
        };
        store.dispatch("SEND_MSG", msg)
    }

    function submit() {
        var text = $("#text")[0];
        if (!text.value) {
            return
        }
        var content = text.value;
        if (content == "[vs=oid]") {
            content = "oid=" + store.getState().guest.id
        } else if (content == "[vs=auth]") {
            content = "auth=" + store.getState().guest.auth
        } else if (content == "[vs=session]") {
            var key = "ht_" + store.getState().guest.id;
            var hts = JSON.parse($.cache(key) || "[]");
            content = key + ": " + (hts && JSON.stringify(hts)) + "\n";
            hts && $.each(hts, function (i, it) {
                key = "ht_" + store.getState().guest.id + "_" + it;
                content += "\n" + key + ": " + JSON.stringify($.cache(key))
            })
        }
        if ($.isPhone) {
            $("#sendBtn").css("display", "none");
            $("#menuBtn").css("display", "block");
            $("#emojiPnl").addClass("hide");
            $("#pnlExt").addClass("hide")
        }
        var msg = {id: $.sendid(), type: 1, dir: 1, stamp: $.stamp(), json: {text: $.emojiOutChange(content)}};
        store.dispatch("SEND_MSG", msg);
        text.value = "";
        $("#val-left")[0].style.display = "none";
        $("#valleft").html(450);
        $("#atcomPnl").addClass("hide")
    }

    $("#submit").click(submit);
    $("#sendBtn").click(submit);
    $("#sendBtn")[0].ontouchstart = function (e) {
        if ($.isPhone) {
            submit();
            e.returnValue = false;
            e.preventDefault();
            return false
        }
    };
    $("#text").keydown(function (e) {
        e || (e = window.event);
        var that = this === window ? $("#text")[0] : this;
        var atcomShow = !~$("#atcomPnl")[0].className.indexOf("hide");
        if (e && atcomShow && ((e.which || e.keyCode) === 38 || (e.which || e.keyCode) === 40)) {
            var rst = autoCompleteSelected(e.which || e.keyCode);
            e.returnValue = false;
            e.preventDefault && e.preventDefault();
            return false
        } else if (e && (e.which || e.keyCode) === 13 && selectIndex >= 0 && selectIndex < atcomArray.length) {
            var atcomCon = $("#atcom"), itemArr = atcomCon.children();
            var target = itemArr[itemArr.length - 1 - selectIndex];
            $("#atcomPnl").addClass("hide");
            $("#text").val(target.innerHTML);
            $("#text")[0].oninput();
            $("#text")[0].focus();
            $("#text")[0].selectionStart = $("#text")[0].selectionEnd = $("#text")[0].value.length;
            e.returnValue = false;
            e.preventDefault && e.preventDefault();
            return false
        } else if (e && (e.which || e.keyCode) === 13 && !e.shiftKey && (!$.ua.isMobile || $.ua.isMobile && e.ctrlKey)) {
            that.value = that.value.replace(/[\s\n]+$/g, "");
            submit();
            e.returnValue = false;
            e.preventDefault && e.preventDefault();
            return false
        }
    });
    $("#text").keyup(function (e) {
        e || (e = window.event);
        var that = this === window ? $("#text")[0] : this;
        if (e && (e.which || e.keyCode) === 13 && !e.shiftKey && !$.isPhone) {
            e.preventDefault && e.preventDefault();
            return
        } else {
            if (that.value.length > 450) {
                that.value = that.value.substr(0, 449)
            }
            if (that.value.length > 300) {
                $("#val-left")[0].style.display = "block"
            } else {
                $("#val-left")[0].style.display = "none"
            }
            $("#valleft").html(450 - that.value.length)
        }
    });
    var winHeight = window.innerHeight || document.body && document.body.clientHeight;
    window.winHeight = winHeight;
    var interval;

    function scrollToEnd() {
        if ($.ua.platform.isIphone && $.ua.platform.version > "11") {
            return
        }
        document.body.scrollTop = document.body.scrollHeight;
        $("#text")[0].scrollIntoView(true)
    }

    function pageRepair() {
        var currentPosition, timer;
        var speed = 1;
        timer = setInterval(function () {
            currentPosition = document.documentElement.scrollTop || document.body.scrollTop;
            currentPosition -= speed;
            window.scrollTo(0, currentPosition);
            currentPosition += speed;
            window.scrollTo(0, currentPosition);
            clearInterval(timer)
        }, 1)
    }

    var scrTop = document.body.scrollTop;
    var u = navigator.userAgent;
    $("#text").focus(function (e) {
        if ($.isPhone) {
            if (~$("#pnlBtns")[0].className.indexOf("show") || !~$("#pnlExt")[0].className.indexOf("hide") || !~$("#quesPnl")[0].className.indexOf("hide")) {
                $(".pnl-msgs").css("height", "");
                $("#emojiPnl").addClass("hide");
                $("#quesPnl").addClass("hide");
                $("#pnlExt").addClass("hide");
                $("#pnlBtns").rmClass("show");
                $(".pnl-text").css("height", "3.2em")
            }
            $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
            if ($.ua.platform.isIphone && $.ua.platform.version >= "12") {
                if (/MicroMessenger/gi.test(u)) {
                    setTimeout(function () {
                        if (window.scrollY < 270) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        } else if (window.scrollY < 300) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        } else if (window.scrollY < 320) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        }
                    }, 200)
                }
            } else if ($.ua.platform.isIphone && $.ua.platform.version > "11") {
                setTimeout(function () {
                    if (window.scrollY < 350) {
                        window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                    }
                }, 100)
            }
            interval = setInterval(function () {
                if ($.ua.platform.isIphone && $.ua.platform.version >= "12") {
                    return
                } else if ($.ua.platform.isIphone && $.ua.platform.version > "11") {
                    setTimeout(function () {
                        if (window.scrollY < 270) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        } else if (window.scrollY < 300) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        } else if (window.scrollY < 320) {
                            window.scrollTo(0, document.body.scrollHeight - window.scrollY)
                        }
                    }, 200);
                    if (/UCBrowser/gi.test(u)) {
                        setTimeout(function () {
                            if (!/hide/gi.test($(".pnl-diy")[0].className)) {
                                $("#show")[0].style.marginBottom = "60px"
                            }
                        }, 100)
                    }
                    return
                } else if ($.ua.platform.isIphone && $.ua.platform.version > "10") {
                    $(".pnl-text")[0].style.position = "absolute !important";
                    if (/UCBrowser/gi.test(u)) {
                        $(".pnl-text")[0].scrollIntoView(true);
                        var paddingBottom = 0;
                        if (/hide/gi.test($(".pnl-diy")[0].className)) {
                            paddingBottom = "36px";
                            $(".pnl-text")[0].style.bottom = "18.5em !important"
                        } else {
                            paddingBottom = "36px"
                        }
                    } else if (/Baidu/gi.test(u)) {
                        $("#show")[0].style.marginBottom = window.scrollY + "px";
                        $("#show")[0].scrollTop = $("#show")[0].scrollHeight + window.scrollY
                    } else {
                        document.body.scrollTop = document.body.scrollHeight
                    }
                    return
                } else if (/MZBrowser/gi.test(u) && !/ifr/gi.test(document.body.className)) {
                    $("#show")[0].style.margin = "0 0 62% 0";
                    $(".pnl-text")[0].style.bottom = "40%";
                    return
                } else if ((/MIX/gi.test(u) || /MiuiBrowser\/11/gi.test(u)) && !/MicroMessenger/gi.test(u) && !/ifr/gi.test(document.body.className)) {
                    if (document.body.scrollHeight === winHeight) {
                        $(".pnl-text")[0].style.bottom = 0
                    } else {
                        $(".pnl-text")[0].style.bottom = "48px"
                    }
                }
                document.body.scrollTop = document.body.scrollHeight;
                $("#text")[0].scrollIntoView(true)
            }, 200)
        } else {
            $("#emojiPnl").addClass("hide");
            $("#quesPnl").addClass("hide");
            $("#pnlExt").addClass("hide");
            $("#pnlBtns").rmClass("show")
        }
    }).blur(function () {
        if ($.ua.platform.isIphone && $.ua.platform.version > "12") {
            pageRepair()
        } else if ($.ua.platform.isIphone && $.ua.platform.version > "11") {
            if (/UCBrowser/gi.test(u)) {
                $("#show")[0].style.margin = 0
            }
        } else if ($.ua.platform.isIphone && $.ua.platform.version > "10") {
            if (!/hide/gi.test($("#atcomPnl")[0].className)) {
                return
            }
            if (/UCBrowser/gi.test(u)) {
                $(".pnl-text")[0].style.bottom = "3.3em !important"
            } else if (/Baidu/gi.test(u)) {
                $("#show")[0].style.margin = 0
            }
        } else if (/MZBrowser/gi.test(u)) {
            $("#show")[0].style.margin = 0;
            $(".pnl-text")[0].style.bottom = 0
        } else if (/MiuiBrowser/gi.test(u) || /MI/gi.test(u)) {
            $(".pnl-text")[0].style.bottom = 0
        }
        clearInterval(interval);
        setTimeout(function () {
            $("#atcomPnl").addClass("hide")
        }, 350);
        typing(true)
    });
    $("#text01").focus(function () {
        if ($.isPhone) {
            if (~$("#pnlBtns")[0].className.indexOf("show") || !~$("#pnlExt")[0].className.indexOf("hide") || !~$("#quesPnl")[0].className.indexOf("hide")) {
                $(".pnl-msgs").css("height", "");
                $("#emojiPnl").addClass("hide");
                $("#quesPnl").addClass("hide");
                $("#pnlExt").addClass("hide");
                $("#pnlBtns").rmClass("show");
                $(".pnl-text").css("height", "3.2em")
            }
            $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
            if ($.ua.platform.isIphone) {
                window.scrollTo(0, winHeight);
                if ($.ua.platform.version > "10") {
                    setTimeout(function () {
                        window.scrollTo(0, window.innerHeight - 97)
                    }, 100)
                }
            }
            interval = setInterval(scrollToEnd, 500)
        } else {
            $("#emojiPnl").addClass("hide");
            $("#quesPnl").addClass("hide");
            $("#pnlExt").addClass("hide");
            $("#pnlBtns").rmClass("show")
        }
    }).blur(function () {
        if ($.ua.platform.isIphone && $.ua.platform.version > "11") {
            pageRepair()
        }
        clearInterval(interval);
        setTimeout(function () {
            $("#atcomPnl").addClass("hide")
        }, 350)
    });
    $("#text0").focus(function () {
        if ($.isPhone) {
            if (~$("#pnlBtns")[0].className.indexOf("show") || !~$("#pnlExt")[0].className.indexOf("hide") || !~$("#quesPnl")[0].className.indexOf("hide")) {
                $(".pnl-msgs").css("height", "");
                $("#emojiPnl").addClass("hide");
                $("#quesPnl").addClass("hide");
                $("#pnlExt").addClass("hide");
                $("#pnlBtns").rmClass("show");
                $(".pnl-text").css("height", "3.2em")
            }
            var _this = this;
            timer = setTimeout(function () {
                _this.scrollIntoView(true)
            }, 400);
            interval = setInterval(scrollToEnd, 500)
        } else {
            $("#emojiPnl").addClass("hide");
            $("#quesPnl").addClass("hide");
            $("#pnlExt").addClass("hide");
            $("#pnlBtns").rmClass("show")
        }
    }).blur(function () {
        document.body.scrollTop = scrTop;
        clearInterval(interval);
        setTimeout(function () {
            $("#atcomPnl").addClass("hide")
        }, 350)
    });
    $("#text00").focus(function (e) {
        if ($.isPhone) {
            if (~$("#pnlBtns")[0].className.indexOf("show") || !~$("#pnlExt")[0].className.indexOf("hide") || !~$("#quesPnl")[0].className.indexOf("hide")) {
                $(".pnl-msgs").css("height", "");
                $("#emojiPnl").addClass("hide");
                $("#quesPnl").addClass("hide");
                $("#pnlExt").addClass("hide");
                $("#pnlBtns").rmClass("show");
                $(".pnl-text").css("height", "3.2em")
            }
            $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
            setTimeout(function () {
                if ($.ua.platform.isIphone) {
                    if (window.scrollY < 270) {
                        window.scrollTo(0, winHeight)
                    }
                }
                $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
                setTimeout(function () {
                    if (window.scrollY < 300) {
                        window.scrollTo(0, winHeight)
                    }
                    $("#show")[0].scrollTop = $("#show")[0].scrollHeight
                }, 450)
            }, 200);
            interval = setInterval(scrollToEnd, 500)
        } else {
            $("#emojiPnl").addClass("hide");
            $("#quesPnl").addClass("hide");
            $("#pnlExt").addClass("hide");
            $("#pnlBtns").rmClass("show")
        }
    }).blur(function (e) {
        if ($.ua.platform.isIphone && $.ua.platform.version > "12") {
            pageRepair()
        }
        clearInterval(interval);
        setTimeout(function () {
            $("#atcomPnl").addClass("hide")
        }, 350)
    });
    $("#text")[0].onpropertychange = $("#text")[0].oninput = function (e) {
        var text = $("#text")[0], state = store.getState();
        setTimeout(function () {
            typing(false)
        }, 20);
        if (text.value.length === 0) {
            if ($.isPhone) {
                $("#sendBtn").css("display", "none");
                $("#menuBtn").css("display", "block");
                $("#atcomPnl").addClass("hide")
            }
        } else {
            if ($.isPhone) {
                $("#sendBtn").css("display", "block");
                $("#menuBtn").css("display", "none")
            }
        }
        if (state.site.control && state.site.control.learn_search) {
            atcomArray = renderAtcomQues(text.value) || [];
            if (atcomArray.length > 0) {
                $("#atcomPnl").rmClass("hide");
                $("#atcomPnl")[0].scrollTop = $("#atcomPnl")[0].scrollHeight
            } else {
                $("#atcomPnl").addClass("hide")
            }
        }
    };
    $("#text")[0].onpaste = function (e) {
        $.clipboardData = e.clipboardData;
        var items = e.clipboardData && (e.clipboardData.items || e.clipboardData.files);
        items && $.each(items, function (i, it) {
            dataItemUpload(it)
        })
    };
    $("#text")[0].ondragover = function (e) {
    };
    $("#text")[0].ondrop = function (e) {
        console.log("ondrop", e);
        if (e.preventDefault) {
            e.preventDefault()
        } else {
            e.returnValue = false
        }
        if (e.stopPropagation) {
            e.stopPropagation()
        } else {
            e.cancelBubble = true
        }
        var files = e.dataTransfer && (e.dataTransfer.items || e.dataTransfer.files);
        files && $.each(files, function (i, it) {
            dataItemUpload(it)
        })
    };

    function dataItemUpload(it) {
        if (/datatransferitem/i.test(it.toString().toLowerCase())) {
            if (it.kind === "file") {
                var file = it.getAsFile();
                if (file && /png|jpg|jpeg|bmp/i.test(file.type)) {
                    var type = "file";
                    if (/Blob|blob/i.test(file.toString())) {
                        type = "blob"
                    }
                    file && $.upload(function (json) {
                        store.dispatch("SEND_MSG", {
                            id: json.imgId || $.sendid(),
                            dir: 1,
                            type: 2,
                            stamp: $.stamp(),
                            json: json,
                            status: json.error ? -1 : 0
                        })
                    }, void 0, file)
                }
            }
        } else if (/file/i.test(it.toString().toLowerCase())) {
            var file = it;
            if (file && /png|jpg|jpeg|bmp/i.test(file.type)) {
                $.upload(function (json) {
                    store.dispatch("SEND_MSG", {
                        id: json.imgId || $.sendid(),
                        dir: 1,
                        type: 2,
                        stamp: $.stamp(),
                        json: json,
                        status: json.error ? -1 : 0
                    })
                }, void 0, file)
            }
        }
    }

    $("#show").click(function (e) {
        var atcomShow = !~$("#atcomPnl")[0].className.indexOf("hide");
        if (atcomShow) {
            $("#atcomPnl").addClass("hide");
            e.returnValue = false;
            e.preventDefault();
            return false
        }
    });
    $("#atcom").click(function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (target && /atcom-item/i.test(target.className)) {
            $("#atcomPnl").addClass("hide");
            $("#text").val(target.innerHTML);
            $("#text")[0].oninput();
            $("#text")[0].focus();
            $("#text")[0].selectionStart = $("#text")[0].selectionEnd = $("#text")[0].value.length;
            setTimeout(function () {
                $("#text")[0].oninput()
            }, 300)
        }
    });
    var selectIndex = -1, atcomArray = [];

    function autoCompleteSelected(code) {
        if (code == 38) {
            if (selectIndex < atcomArray.length - 1) {
                selectIndex++;
                changeSelectItem(selectIndex);
                return true
            } else if (selectIndex === atcomArray.length - 1) {
                selectIndex = 0;
                changeSelectItem(selectIndex);
                return true
            }
        } else if (code == 40) {
            if (selectIndex > 0) {
                selectIndex--;
                changeSelectItem(selectIndex);
                return true
            } else if (selectIndex === -1 || selectIndex === 0) {
                selectIndex = atcomArray.length - 1;
                changeSelectItem(selectIndex);
                return true
            }
        }
        return false
    }

    function changeSelectItem(idx) {
        var atcomCon = $("#atcom"), itemArr = atcomCon.children();
        $.each(itemArr, function (i, it) {
            $(it).rmClass("active")
        });
        $(itemArr[itemArr.length - 1 - idx]).addClass("active");
        var atcomPnl = $("#atcomPnl")[0];
        var itemHeight = itemArr[0].clientHeight, pos = atcomPnl.scrollHeight - itemHeight * (idx + 1);
        if (atcomPnl.scrollHeight - itemHeight * (idx + 1) < atcomPnl.scrollTop) {
            $("#atcomPnl")[0].scrollTop = pos
        } else if (atcomPnl.scrollHeight - itemHeight * idx > atcomPnl.clientHeight + atcomPnl.scrollTop) {
            $("#atcomPnl")[0].scrollTop = atcomPnl.scrollHeight - itemHeight * idx - atcomPnl.clientHeight
        }
    }

    function renderAtcomQues(txt) {
        selectIndex = -1;
        if (!txt) {
            return []
        }
        var ques = store.getState().site.autoQues || [];
        if (ques.length == 0) {
            return []
        }
        var rst = [], base = .6;
        for (var i = 0; i < ques.length; i++) {
            var rel = getRelative(txt, ques[i]);
            if (rel > base) {
                base += rst.length / ques.length * (.98 - base);
                rst.push({rel: rel, text: ques[i]})
            }
        }
        rst = rst.sort(function (l, r) {
            if (l.rel > r.rel) {
                return 1
            } else if (l.rel < r.rel) {
                return -1
            }
            return 0
        });
        if (rst.length > 30) {
            rst = rst.slice(-30)
        }
        if (rst.length > 0) {
            var atcomCon = $("#atcom");
            atcomCon.html("");
            for (var i = 0; i < rst.length; i++) {
                var li = document.createElement("li");
                li.className = "atcom-item";
                li.title = $.i18n("回车或点击选择");
                li.innerHTML = $.emojiInChange(rst[i].text);
                atcomCon.append(li)
            }
        }
        return rst
    }

    function getRelative(src, ques) {
        if (ques.length > 50) {
            ques = ques.substring(0, 50)
        }
        if (src.length > 50) {
            src = src.substring(0, 50)
        }
        return simAtoB(src, ques)
    }

    function txtInArray(txt, ques) {
        var rst = [];
        var re = new RegExp(txt, "im");
        for (var i = 0; i < ques.length; i++) {
            re.test(ques[i]) && rst.push(ques[i])
        }
        return rst
    }

    $.txtInArray = txtInArray;
    $.simAtoB = simAtoB;
    $("#emojiPnl").click(function (e) {
        e || (e = window.event);
        var target = e.target || e.srcElement;
        if (target !== $("#emojiPnl")[0]) {
            var key = $.attr(target, "key");
            if (key) {
                $("#text").val($("#text")[0].value + key);
                $("#text")[0].oninput()
            }
        }
    });
    var iClick = 0;
    (function () {
        $("#pnlWarn").click(function (e) {
            e || (e = window.event);
            var target = e.target || e.srcElement, state = store.getState();
            if (/diy-btn/i.test(target.className)) {
                var ind = target.attributes.index.value, item = state.site.webChatBtns.btns[ind];
                var msg = {id: $.sendid(), type: 1, dir: 1, stamp: $.stamp(), json: {text: item.value}};
                if (btnClickLimit(target)) return;
                if (item.type === "link") {
                    window.open(item.value, "_blank")
                } else if (item.type === "click") {
                    store.dispatch("SEND_MSG", msg)
                }
            } else if (/diy-pre/.test(target.className)) {
                iClick++;
                var btns = state.site.webChatBtns.btns, aIndex = state.site.webChatBtns.index;
                var aIndexLen = aIndex.length, num;
                if (aIndex[aIndexLen - 1] === btns.length - 1) {
                    num = aIndexLen
                } else {
                    num = aIndexLen + 1
                }
                $(".diy-btn").addClass("hide");
                if (iClick % num === 0) {
                    $.each($(".diy-btn"), function (index, item) {
                        if (index >= 0 && index <= aIndex[iClick % num]) {
                            $(item).rmClass("hide")
                        }
                    })
                } else if (iClick % num === num - 1) {
                    $.each($(".diy-btn"), function (index, item) {
                        if (index > aIndex[iClick % num - 1]) {
                            $(item).rmClass("hide")
                        }
                    })
                } else {
                    $.each($(".diy-btn"), function (index, item) {
                        if (index > aIndex[iClick % num - 1] && index <= aIndex[iClick % num]) {
                            $(item).rmClass("hide")
                        }
                    })
                }
            } else if (/\bwarn-btn\b/i.test(target.className)) {
                var pnl;
                var _winHeight = 0;
                var btnsShow = ~$("#pnlBtns")[0].className.indexOf("show");
                var quesShow = !~$("#quesPnl")[0].className.indexOf("hide");
                var extShow = !~$("#pnlExt")[0].className.indexOf("hide");
                if (window.innerHeight) _winHeight = window.innerHeight; else if (document.body && document.body.clientHeight) _winHeight = document.body.clientHeight;
                switch (target.id) {
                    case"emojiBtn": {
                        pnl = $("#emojiPnl");
                        if (/hide/i.test(pnl[0].className) || /hide/i.test(pnl[0].parentElement.className)) {
                            var timeout = document.activeElement.id == "text" || _winHeight < window.winHeight ? 200 : 0;
                            setTimeout(function () {
                                if (!$("#emojiPnl").children().length) {
                                    var emotions = $.emojiZh(), baseUrl = emotions.baseUrl,
                                        frag = document.createDocumentFragment();
                                    emotions = emotions.emotions;
                                    for (var key in emotions) {
                                        var img = new Image;
                                        img.className = "emoji-item";
                                        img.src = baseUrl + emotions[key];
                                        $.attr(img, "key", key);
                                        frag.appendChild(img)
                                    }
                                    $("#emojiPnl").append(frag)
                                }
                                if ($.isPhone && (btnsShow || quesShow)) {
                                    showBottomPnl(false)
                                }
                                if (!$.isPhone) {
                                    $("#quesPnl").addClass("hide")
                                }
                                $("#pnlExt").rmClass("hide");
                                pnl.tabClass("hide", true)
                            }, timeout)
                        } else {
                            pnl.addClass("hide");
                            $("#pnlExt").addClass("hide")
                        }
                        $("#pnlBtns").rmClass("show");
                        break
                    }
                    case"menuBtn":
                        $("#quesPnl").addClass("hide");
                        if ($.isPhone) {
                            if (btnsShow) {
                                $("#pnlBtns").rmClass("show");
                                showBottomPnl(false)
                            } else if (extShow) {
                                $("#pnlBtns").addClass("show");
                                $("#pnlExt").addClass("hide");
                                showBottomPnl(true)
                            } else {
                                var timeout = document.activeElement.id == "text" || _winHeight < window.winHeight ? 200 : 0;
                                setTimeout(function () {
                                    $("#pnlBtns").addClass("show");
                                    showBottomPnl(true)
                                }, timeout)
                            }
                        } else {
                            $("#pnlBtns").toggleClass("show");
                            $("#pnlExt").addClass("hide")
                        }
                        break;
                    case"quesBtn":
                        pnl = $("#quesPnl");
                        if (!$.isPhone) {
                            $("#emojiPnl").addClass("hide")
                        }
                        if (/hide/i.test(pnl[0].className) || /hide/i.test(pnl[0].parentElement.className)) {
                            pnl.rmClass("hide")
                        } else {
                            pnl.addClass("hide")
                        }
                        $("#pnlBtns").rmClass("show");
                        break;
                    case"humanBtn":
                        if (btnClickLimit(target)) return;
                        store.dispatch("GET_HUMAN", Object.assign({}, store.getState().system.human, {human: 1}));
                        break;
                    case"uploadBtn":
                        if (store.getState().site.id == "133844" && navigator.userAgent.indexOf("qukan") != -1) {
                            location.href = "tools?target=upload";
                            if (!window.__echos__ || !window.__echos__.uploaderImage) {
                                window.__echos__ = {
                                    uploaderImage: function (url) {
                                        if (!url) return;
                                        store.dispatch("SEND_MSG", {
                                            id: $.sendid(),
                                            dir: 1,
                                            type: 2,
                                            stamp: $.stamp(),
                                            json: {src: url, name: ""}
                                        })
                                    }
                                }
                            }
                        } else {
                            $.upload(function (json) {
                                store.dispatch("SEND_MSG", {
                                    id: json.imgId || $.sendid(),
                                    dir: 1,
                                    type: 2,
                                    stamp: $.stamp(),
                                    json: json,
                                    status: json.error ? -1 : 0
                                })
                            })
                        }
                        break;
                    case"cameraBtn":
                        if (store.getState().site.id == "133844" && navigator.userAgent.indexOf("qukan") != -1) {
                            location.href = "tools?target=upload";
                            if (!window.__echos__ || !window.__echos__.uploaderImage) {
                                window.__echos__ = {
                                    uploaderImage: function (url) {
                                        if (!url) return;
                                        store.dispatch("SEND_MSG", {
                                            id: $.sendid(),
                                            dir: 1,
                                            type: 2,
                                            stamp: $.stamp(),
                                            json: {src: url, name: ""}
                                        })
                                    }
                                }
                            }
                        } else {
                            $.upload(function (json) {
                                store.dispatch("SEND_MSG", {
                                    id: json.imgId || $.sendid(),
                                    dir: 1,
                                    type: 2,
                                    stamp: $.stamp(),
                                    json: json,
                                    status: json.error ? -1 : 0
                                })
                            }, "camera")
                        }
                        break;
                    case"scrshot":
                        preview({type: "img", src: "lib/files/images/help/scrshot_kh_1ede4368b7.png"});
                        break;
                    case"evalBtn":
                        store.dispatch("SEND_MSG", {
                            o_type: "message",
                            id: $.sendid(),
                            dir: 1,
                            type: 5,
                            stamp: $.stamp(),
                            json: {event: "questionnaire"}
                        });
                        break;
                    case"msgBtn":
                        store.dispatch("BACK_MSG", {
                            o_type: "message",
                            message_id: $.sendid(),
                            message_type: 24,
                            direction: 2,
                            unsend: true
                        });
                        break;
                    default:
                }
            }
        });

        function btnClickLimit(select) {
            var num = JSON.parse($(select).attr("data-click")[0]);
            var timeOld = JSON.parse($(select).attr("data-time")[0]) || 0;
            var timeNew = (new Date).getTime();
            var expires = 30 * 1e3;
            var isClick = false;
            if (timeOld == 0) {
                $(select).attr("data-time", timeNew)
            } else {
                if (timeNew - timeOld > expires) {
                    num = 2;
                    $(select).attr("data-time", timeNew)
                } else {
                    if (num == 0) {
                        if (timeNew - timeOld > expires) {
                            num = 2;
                            $(select).attr("data-time", timeNew)
                        } else {
                            num = 1;
                            $.showinfo("温馨提示，无须重复性提问，问题发送一次即可");
                            console.warn("温馨提示，无须重复性提问，问题发送一次即可");
                            isClick = true
                        }
                    }
                }
            }
            num--;
            $(select).attr("data-click", num);
            return isClick
        }

        function showBottomPnl(open) {
            if (open) {
                $(".pnl-text").css("height", "18.2em");
                var head = window.phone && window.parent ? 4.5 : 3.2;
                $(".pnl-msgs").css("height", document.body.clientHeight - (head + 18.2) * 13 + "px");
                $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
                setTimeout(function () {
                    $(".pnl-msgs").css("height", document.body.clientHeight - (head + 18.2) * 13 + "px");
                    $("#show")[0].scrollTop = $("#show")[0].scrollHeight
                }, 300)
            } else {
                $(".pnl-text").css("height", "3.2em");
                $(".pnl-msgs").css("height", "");
                setTimeout(function () {
                    $(".pnl-msgs").css("height", "")
                }, 300)
            }
        }

        if ($.isPhone) {
            $(".kh.warn-btn").on("touchstart", function () {
                $(this).css("color", "#A6A6A6")
            });
            $(".kh.warn-btn").on("touchend", function () {
                $(this).css("color", "#7c7c7c");
                $("#sendBtn").css("color", $.autoColor(store.getState().site.theme.color || store.getState().site.header.smcolor || "#484A4B"))
            });
            $(".pnl-list").click(function (e) {
                var btnsShow = ~$("#pnlBtns")[0].className.indexOf("show");
                var extShow = !~$("#pnlExt")[0].className.indexOf("hide");
                if (btnsShow || extShow) {
                    hideMask(e);
                    e.returnValue = false;
                    e.preventDefault();
                    return false
                }
            });
            var hideMask = function (e) {
                showBottomPnl(false);
                $("#pnlBtns").rmClass("show");
                $("#emojiPnl").addClass("hide");
                $("#pnlExt").addClass("hide");
                e.returnValue = false;
                e.preventDefault();
                return false
            };
            document.getElementById("quesBtn").innerHTML = "&#xe81F";
            document.getElementById("uploadBtn").innerHTML = "&#xe826";
            document.getElementById("evalBtn").innerHTML = "&#xe81A";
            document.getElementById("msgBtn").innerHTML = "&#xe81B";
            document.getElementById("humanBtn").innerHTML = "&#xe81E";
            document.getElementById("voiceMbBtn").innerHTML = "&#xe81C";
            document.getElementById("videoBtn").innerHTML = "&#xe845";
            document.getElementById("voiceBtn").innerHTML = "&#xe846";
            $("#cameraBtn").addClass("hide");
            $("#muteBtn").addClass("hide");
            $(".pnl-input").css("left", "0.5em")
        } else {
            $("#pnlExt").on("mouseleave", function () {
                if ($.isPhone) {
                    return
                }
                $("#pnlExt").addClass("hide")
            });
            $("#quesPnl").on("mouseleave", function () {
                if ($.isPhone) {
                    return
                }
                $("#quesPnl").addClass("hide")
            })
        }
        if ($.ua.isIe || $.ua.browser.isSafari) {
            $("#scrshot").css("display", "none")
        }
        if ($.ua.isIe && $.ua.ie < 10) {
            if ($.ua.ie < 9) {
                $(".pop-about").css("border", "1px solid #c9cacb")
            }
            $("#uploadBtn").css("display", "none")
        }
        $("#recon").click(function () {
        });
        $("#refresh").click(function () {
            window.location.reload()
        });
        $("#startChat").click(function () {
            store.dispatch("OPEN_WS")
        })
    })();

    function bldQQList(arr) {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (item.name && item.id) {
                str += '<div class="v5-pnl-link qq" href="https://wpa.qq.com/msgrd?v=3&uin=' + item.id + '&site=qq&menu=yes">' + item.name + "</div>"
            }
        }
        return str
    }

    function bldWWList(arr) {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (item.name && item.id) {
                str += '<div class="v5-pnl-link ww" href="https://amos.im.alisoft.com/msg.aw?v=2&amp;uid=' + item.id + '&amp;site=cntaobao&amp;s=1&amp;charset=utf-8">' + item.name + "</div>"
            }
        }
        return str
    }

    function bldQrList(arr) {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (item.name && item.url) {
                str += '<div class="v5-pnl-qrbox"' + (arr.length === 1 ? ' style="width: 100%;height:auto;"' : "") + '><img class="v5-pnl-qr" src="' + item.url + '"/><div class="v5-pnl-qrtxt">' + item.name + "</div></div>"
            }
        }
        return str
    }

    function validEmail(email) {
        if (email.length > 0) {
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{1,10}$/;
            if (!reg.test(email)) {
                return false
            }
        } else {
            return false
        }
        return true
    }

    function validPhone(phone) {
        var isPhone = /^([0-9]{3,4}-?)?[0-9]{7,8}$/;
        var isMob = /^((\+?[0-9]{2})|(\(\+?[0-9]{2}\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
        if (isMob.test(phone) || isPhone.test(phone)) {
            return true
        } else {
            return false
        }
    }

    function validQQ(qq) {
        var isQQ = /^[1-9][0-9]{4,}$/;
        if (isQQ.test(qq)) {
            return true
        } else {
            return false
        }
    }

    function ajaxInst() {
        function noop() {
        }

        function config(options) {
            var def = {
                url: "",
                method: "GET",
                data: "",
                async: true,
                success: noop,
                error: noop,
                done: noop,
                headers: {}
            }, key;
            for (key in def) {
                if (!(key in options)) {
                    options[key] = def[key]
                }
            }
            return options
        }

        function preprocess(options) {
            var arr, key;
            options.method = options.method.toUpperCase();
            if (typeof options.data === "object") {
                arr = [];
                for (key in options.data) {
                    arr.push(key + "=" + encodeURIComponent(options.data[key]))
                }
                options.data = arr.join("&")
            }
        }

        function listen(xhr, options) {
            xhr.onreadystatechange = function () {
                if (this.readyState === 4) {
                    if (this.status >= 200 && this.status < 300) {
                        options.success(this.responseText, this.status)
                    } else {
                        options.error(this.responseText, this.status)
                    }
                    options.done()
                }
            }
        }

        function open(xhr, options) {
            if (/post/i.test(options.method)) {
                xhr.open(options.method, options.url, options.async)
            } else if (/get/i.test(options.method)) {
                options.data = options.data ? "?" + options.data : "";
                xhr.open(options.method, options.url + options.data, options.async)
            }
        }

        function headers(xhr, options) {
            if (/post/i.test(options.method)) {
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            }
            for (var key in options.headers) {
                xhr.setRequestHeader(key, options.headers[key])
            }
        }

        function send(xhr, options) {
            if (/get/i.test(options.method)) {
                xhr.send()
            } else if (/post/i.test(options.method)) {
                xhr.send(options.data)
            }
        }

        function request(options) {
            config(options);
            preprocess(options);
            if (/jsonp?/i.test(options.method)) {
                if (!options.url) {
                    options.error("URL为空")
                } else if (options.data) {
                    options.url += "?" + options.data
                }
                return jsonp(options)
            } else {
                if (typeof XMLHttpRequest === "undefined" && typeof ActiveXObject === "undefined") {
                    console.warn("%c环境：%c您的浏览器可能不支持AJAX", "font-weight:bold", "font-weight:normal");
                    return null
                }
                var xhr = typeof XMLHttpRequest !== "undefined" ? new XMLHttpRequest : typeof ActiveXObject !== "undefined" ? new window.ActiveXObject("Microsoft.XMLHTTP") : null;
                listen(xhr, options);
                open(xhr, options);
                headers(xhr, options);
                send(xhr, options);
                return xhr
            }
        }

        function jsonp(options) {
            var script = document.createElement("script");
            script.type = "text/javascript";
            script.src = $.setProtocol(options.url);
            script.async = options.async;

            function callback(e) {
                this.onload = this.onerror = undefined;
                document.body.removeChild(this);
                if (e) {
                    if (e.type === "load") {
                        options.success("脚本加载成功：" + this.src, 200)
                    } else {
                        options.error("脚本加载失败：" + this.src, 404)
                    }
                }
                options.done();
                script = null
            }

            script.onload = callback;
            script.onerror = callback;
            script.onreadystatechange = function (e) {
                if (this.readyState === "loaded") {
                    options.done()
                }
            };
            document.body.appendChild(script);
            return callback
        }

        return function (url, options) {
            if (typeof url === "object") {
                options = url
            } else {
                options.url = url
            }
            return request(options)
        }
    }

    function appendList(opt) {
        var divHead = document.createElement("div");
        divHead.className = "btn-head";
        divHead.id = "btnHead";
        var divCov = document.createElement("div");
        divCov.className = "fix cover v5-pnlbtn hide";
        divCov.id = "minBtnPnl";
        if (!document.getElementById("btnHead")) {
            document.getElementById("pnlHead").appendChild(divHead);
            var str = "";
            document.getElementById("pnlBody").appendChild(divCov);
            var strr = '<div class="v5-head-btn v5chat active" id="v5Chat"><i class="v5-btn-img v5chat">&#xE838;</i><em class="v5-btn-txt v5chat">' + (opt.cells.chat.name || "在线交流") + "</em></div>";
            var v5QQList = bldQQList(opt.qqlist);
            var v5WWList = bldWWList(opt.wwlist);
            var v5QrList = bldQrList(opt.qrlist);
            if (!!v5QQList && opt.qqlist.length > 0 && opt.cells.qq) {
                strr += '<div class="v5-head-btn v5qq" id="qqChat"><i class="v5-btn-img v5qq">&#xE836;</i><em class="v5-btn-txt v5qq">' + (opt.cells.qq.name || "咨询QQ") + "</em></div>";
                str += '<div class="v5-btn-pnl v5-qq hide"><div class="v5-pnl-box">\x3c!--img class="v5-pnl-img" src="' + "https://static.v5kf.com/" + 'images/v5_chat/tpl2/qqq.png" alt="QQ"/--\x3e<i class="v5-pnl-img" alt="QQ">&#xE836;</i><div class="v5-pnl-list">' + v5QQList + "</div></div></div>"
            }
            if (!!v5WWList && opt.wwlist.length > 0 && opt.cells.wangwang) {
                strr += '<div class="v5-head-btn v5ww" id="wwChat"><i class="v5-btn-img v5ww">&#xE834;</i><em class="v5-btn-txt v5ww">' + (opt.cells.wangwang.name || "咨询旺旺") + "</em></div>";
                str += '<div class="v5-btn-pnl v5-ww hide"><div class="v5-pnl-box">\x3c!--img class="v5-pnl-img" src="' + "https://static.v5kf.com/" + 'images/v5_chat/tpl2/wangwang.png" alt="旺旺"/--\x3e<i class="v5-pnl-img" alt="旺旺">&#xE834;</i><div class="v5-pnl-list">' + v5WWList + "</div></div></div>"
            }
            if (!!v5QrList && opt.qrlist.length > 0 && opt.cells.qrcode) {
                strr += '<div class="v5-head-btn v5qr" id="qrCode"><i class="v5-btn-img v5qr">&#xE837;</i><em class="v5-btn-txt v5qr">' + (opt.cells.qrcode.name || "扫码关注") + "</em></div>";
                str += '<div class="v5-btn-pnl v5-qr hide"><div class="v5-pnl-box v5-full-height">' + v5QrList + "</div></div>"
            }
            if (opt.cells.message) {
                strr += '<div class="v5-head-btn v5guest" id="guestBook"><i class="v5-btn-img v5guest">&#xE830;</i><em class="v5-btn-txt v5guest">' + (opt.cells.message.name || "我要留言") + "</em></div>";
                str += '<div class="v5-btn-pnl v5-guest hide"><div class="v5-pnl-box v5-full-height" id="v5Comment">';
                opt.msglist.username != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">姓名' + (opt.msglist.username == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="username" type="text"/></div>');
                opt.msglist.gender != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">性别' + (opt.msglist.gender == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="gender" type="text"/></div>');
                opt.msglist.phone != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">电话' + (opt.msglist.phone == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="phone" type="text"/></div>');
                opt.msglist.email != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">邮箱' + (opt.msglist.email == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="email" type="text"/></div>');
                opt.msglist.qq != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">QQ' + (opt.msglist.qq == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="qq" type="text"/></div>');
                opt.msglist.address != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">地址' + (opt.msglist.address == 2 ? "*" : "") + '</span><input class="v5-pnl-input" name="address" type="text"/></div>');
                opt.msglist.content != 0 && (str += '<div class="v5-pnl-tr"><span class="v5-pnl-span">留言' + (opt.msglist.content == 2 ? "*" : "") + '</span><textarea class="v5-pnl-text" name="content"></textarea></div>');
                str += '<div class="v5-pnl-btn" id="v5MsgSubmit">提交留言</div></div></div></div>'
            }
            document.getElementById("btnHead").innerHTML = strr;
            document.getElementById("minBtnPnl").innerHTML = str
        }
    }

    function genHeader(opt) {
        console.log("opt", opt);

        function msgSubmit(arr) {
            var submitting;
            if (submitting) {
                return
            }
            var that = this, v5Comment = document.getElementById("v5Comment"), inputs, textarea, len;
            inputs = v5Comment.getElementsByTagName("input");
            len = inputs.length;
            for (var i = 0; i < len; i++) {
                if (opt.msglist[inputs[i].name] == 2 && !inputs[i].value) {
                    inputs[i].style.backgroundColor = "#F7C4C4";
                    return
                } else {
                    inputs[i].style.backgroundColor = "#EEF2F3"
                }
            }
            for (var j = 0; j < len; j++) {
                if (!!inputs[j].value && inputs[j].name == "email" && validEmail(inputs[j].value) == false) {
                    inputs[j].style.backgroundColor = "#D0F6F2";
                    return
                } else if (!!inputs[j].value && inputs[j].name == "phone" && validPhone(inputs[j].value) == false) {
                    inputs[j].style.backgroundColor = "#D0F6F2";
                    return
                } else if (!!inputs[j].value && inputs[j].name == "qq" && validQQ(inputs[j].value) == false) {
                    inputs[j].style.backgroundColor = "#D0F6F2";
                    return
                } else {
                    inputs[j].style.backgroundColor = "#EEF2F3"
                }
            }
            textarea = v5Comment.getElementsByTagName("textarea")[0];
            if (textarea && opt.msglist[textarea.name] == 2 && !textarea.value) {
                textarea.style.backgroundColor = "#F7C4C4";
                return
            } else {
                textarea.style.backgroundColor = "#EEF2F3"
            }
            var d = {url: encodeURIComponent(window.location.href), site_id: opt.siteId || store.getState().site.id};
            for (var i = 0; i < len; i++) {
                d[inputs[i].name] = inputs[i].value
            }
            textarea && (d[textarea.name] = textarea.value);
            that.innerHTML = "提交中...";
            var ajax = ajaxInst();
            submitting = ajax("//www.v5kf.com/public/chat/" + "ajax_visitor_message", {
                method: "jsonp",
                data: d,
                success: function (text) {
                    if (typeof v5_msgres !== "undefined" && v5_msgres == "ok") {
                        that.innerHTML = "提交成功"
                    } else {
                        that.innerHTML = "失败(重试)"
                    }
                },
                error: function (err) {
                    that.innerHTML = "失败(重试)"
                },
                done: function () {
                    submitting = null
                }
            })
        }

        var diatpl = 0, mainbgd, mainfont;
        if (opt.dialog) {
            diatpl = opt.dialog.tpl;
            mainbgd = opt.dialog.color.mainbg || "#ee1543";
            mainfont = opt.dialog.color.main || "#fff"
        } else {
            mainbgd = opt.beforebgd || "#ee1543";
            mainfont = opt.beforefont || "#ee1543";
            if (opt.floattpl == 0 || opt.floattpl == 1) {
                diatpl = 3
            } else if (opt.floattpl == 2) {
                diatpl = 3
            } else if (opt.floattpl == 3) {
                diatpl = 1;
                mainbgd = "#5573b7";
                mainfont = "#fff"
            } else if (opt.floattpl == 4) {
                diatpl = 0
            } else if (opt.floattpl == 5) {
                diatpl = 2
            } else if (opt.floattpl == 6) {
                diatpl = 2
            } else if (opt.floattpl == 7) {
                diatpl = 1
            } else if (opt.floattpl == 8) {
                diatpl = 0;
                mainbgd = "#fc5b47";
                mainfont = "#fff"
            } else if (opt.floattpl == 9) {
                diatpl = 0;
                mainbgd = "#ee1543";
                mainfont = "#fff"
            }
        }
        if (!opt.cells) {
            opt.cells = {
                wangwang: opt.wangwang && {id: opt.wangwang.id, name: opt.wangwang.text},
                qq: opt.qq && {id: opt.qq.id, name: opt.qq.text},
                qrcode: opt.qrcode && {id: opt.qrcode.id, name: opt.qrcode.text},
                message: opt.message && {id: opt.message.id, name: opt.message.text},
                chat: opt.chat && {id: opt.chat.id, name: opt.chat.text}
            }
        }
        if (opt.dialog && opt.dialog.isPhone) {
            $.isPhone = window.phone = true;
            $.isPhone && !/phone/i.test(document.body.className) && (document.body.className += " phone");
            mainbgd = opt.phone.bgcolor || opt.dialog.color.mainbg || "#484a4b";
            mainfont = opt.phone.color || opt.dialog.color.main || "#fff";
            if (mainbgd) {
                $("#sendBtn").css("color", $.autoColor(mainbgd));
                $("#pnlHead").css("background-color", $.autoColor(mainbgd))
            }
        } else if (diatpl == 0) {
            $("body").addClass("ifr0");
            if (store.getState().site.id == 164233 || store.getState().site.id == 105723) {
                $("#pnlHead").addClass("vip-center");
                $("#pnlHead").css("background-image", "");
                $("#workerPhoto").addClass("hide")
            }
        } else if (diatpl == 1) {
            var siteName = store.getState().site.name;
            !siteName && $("body").addClass("ifr1")
        } else if (diatpl == 2) {
            appendList(opt);
            $("body").addClass("ifr2");
            $("#btnHead").rmClass("hide")
        } else if (diatpl == 4) {
            $("body").addClass("ifr4");
            $(".pnl-right-content").css("top", opt.dialog.swiper && opt.dialog.swiper.showmode ? "8em" : "")
        }
        $(".pnl-head").css("background-color", $.autoColor(mainbgd));
        $(".pnl-worker-icon").css("color", $.autoColor(mainfont));
        $(".pnl-worker-name").css("color", $.autoColor(mainfont));
        $(".pnl-worker-site").css("color", $.autoColor(mainfont));
        $(".pnl-opt").css("color", $.autoColor(mainfont));
        $(".pnl-btn").css("background-color", $.autoColor(mainbgd));
        $(".pnl-btn").css("color", $.autoColor(mainfont));
        $(".v5-head-btn").css("color", $.autoColor(mainfont));
        $(".v5-pnl-img").css("color", $.autoColor(mainbgd));
        $(".v5-pnl-link").css("background-color", $.autoColor(mainbgd));
        $("#v5MsgSubmit").css("background-color", $.autoColor(mainbgd));
        $("#v5MsgSubmit").click(msgSubmit);
        $(".v5-head-btn").click(function (e) {
            e || (e = window.event);
            var target = e.target || e.srcElement;
            if (/v5-btn-img/.test(target.className) || /v5-btn-txt/.test(target.className) || /v5-head-btn/.test(target.className)) {
                $(".v5-head-btn").rmClass("active");
                if (!target.children || target.children.length == 0) {
                    target = target.parentNode
                }
                $(target).addClass("active");
                if (/v5chat/.test(target.className)) {
                    $(".v5-pnlbtn").addClass("hide")
                } else if (/v5qq/.test(target.className)) {
                    $(".v5-pnlbtn").rmClass("hide");
                    $(".v5-btn-pnl").addClass("hide");
                    $(".v5-qq").rmClass("hide")
                } else if (/v5ww/.test(target.className)) {
                    $(".v5-pnlbtn").rmClass("hide");
                    $(".v5-btn-pnl").addClass("hide");
                    $(".v5-ww").rmClass("hide")
                } else if (/v5qr/.test(target.className)) {
                    $(".v5-pnlbtn").rmClass("hide");
                    $(".v5-btn-pnl").addClass("hide");
                    $(".v5-qr").rmClass("hide")
                } else if (/v5guest/.test(target.className)) {
                    $(".v5-pnlbtn").rmClass("hide");
                    $(".v5-btn-pnl").addClass("hide");
                    $(".v5-guest").rmClass("hide")
                }
            }
        });
        $(".v5-pnl-link").click(function (e) {
            e || (e = window.event);
            var target = e.target || e.srcElement;
            var url = target.getAttribute("href");
            window.open(url, "_blank")
        })
    }

    window.store = store;
    /ifr/.test(document.body.className) && $.addEvent(window, "message", onmessage);

    function showLoading() {
        $("#mask").addClass("unclose");
        $("#mask").rmClass("hide")
    }

    function hideLoading() {
        $("#mask").addClass("hide");
        $("#mask").rmClass("unclose")
    }

    function onmessage(e) {
        if (!e) {
            e = window.event
        }
        var json = e.data && eval("(" + e.data + ")");
        console.log("onmessage", json);
        if (json && json.v5) {
            switch (json.v5) {
                case"loading":
                    showLoading();
                    break;
                case"loaded":
                    hideLoading();
                    break;
                case"open":
                    showLoading();
                    setTimeout(function () {
                        hideLoading()
                    }, 0);
                    break;
                case"opt":
                    console.log("genHeader", json.opt);
                    try {
                        genHeader(json.opt)
                    } catch (e) {
                        console.error("genHeader err:", e)
                    }
                    break;
                case"human":
                    (json.wid || json.gid || json.human) && !store.getState().system.human && store.dispatch("GET_HUMAN", {
                        human: json.human || 1,
                        gid: json.gid,
                        wid: json.wid
                    });
                    break;
                case"magic":
                    var data = {magic: json.magic || [], real: json.real || {}};
                    (json.magic || json.real) && store.dispatch("SEND_MAGIC", data);
                    break;
                case"greeting":
                    if (json.greeting && typeof json.greeting == "string") {
                        store.getState().robot.intro = json.greeting;
                        $("#welcomeBall").html("").append(buildWelcomeMsg(json.greeting))
                    }
                    break;
                case"defHot":
                    store.getState().site.defHot = json;
                    $("#defHot").html('<div class="defhot-box">' + '<div class="defhot-main">' + '<div class="defhot-img' + (json.price ? " info-price" : "") + '">' + '<img class="defhot-img-url" src="' + json.img + '">' + "</div>" + '<div class="defhot-info">' + '<div class="defhot-info-title" title="' + json.name + '">' + json.name + "</div>" + '<div class="defhot-info-desc' + (json.price ? " info-price" : "") + '" title="' + json.desc + '">' + json.desc + "</div>" + (json.price ? '<div class="defhot-info-price">' + json.price + "</div>" : "") + "</div>" + '<div class="defhot-btn">' + '<div class="defhot-btn-send" id="defHotBtn" href="' + json.link + '">' + json.btn + "</div>" + "</div>" + "</div>" + "</div>");
                    !sessionStorage[store.getState().site.id + "_defHot"] && $("#defHot").rmClass("hide");
                    if (store.getState().current.wid && sessionStorage[store.getState().site.id + "_defHot"]) {
                        $("#defHot").addClass("hide")
                    }
                    $("#defHotBtn").click(defHotClick);
                    break;
                case"connect":
                    store.dispatch("OPEN_WS");
                    break;
                case"msg":
                    if (json.msg && json.msg.type && json.msg.json) {
                        store.dispatch("SEND_MSG", {
                            id: json.msg.id || $.sendid(),
                            dir: 1,
                            type: json.msg.type,
                            stamp: json.msg.stamp || (new Date).getTime(),
                            json: json.msg.json
                        })
                    }
                    break;
                case"faq":
                    if (json.faq && json.faq.length > 0) {
                        store.dispatch("INIT_STATE", {site: {hotQues: json.faq}})
                    }
                    break;
                case"unread":
                    $("#show")[0].scrollTop = $("#show")[0].scrollHeight;
                    break
            }
        }
    }

    var overscroll = function (el) {
        $.addEvent(el, "touchstart", function () {
            var top = el.scrollTop, totalScroll = el.scrollHeight, currentScroll = top + el.offsetHeight;
            if (top === 0) {
                el.scrollTop = 1
            } else if (currentScroll === totalScroll) {
                el.scrollTop = top - 1
            }
        });
        $.addEvent(el, "touchmove", function (evt) {
            if (el.offsetHeight < el.scrollHeight) evt._isScroller = true
        })
    };
    if ($.isPhone) {
        var scrollArr = document.querySelectorAll(".scroll");
        for (var i = 0; i < scrollArr.length; i++) {
            overscroll(scrollArr[i])
        }
    }
    $.isPhone && $.addEvent(document.body, "touchmove", function (evt) {
        if (!evt._isScroller) {
            evt.preventDefault()
        }
    });
    if (window.phone && window.parent && window.parent !== window) {
        $("#minBtn").rmClass("hide");
        $("#minBtn").click(function () {
            window.parent && window.parent.postMessage('{v5: "min"}', "*")
        })
    } else if (window.history.length > 1) {
        $("#histBackBtn").rmClass("hide");
        $("#histBackBtn").click(function () {
            if (!/hide/.test($("#mask")[0].className)) {
                $("#mask").addClass("hide")
            } else {
                window.history.back()
            }
        })
    }
    window.onunload = function () {
        console.warn("onunload -> SYSTEM_EXIT wsstate:", store.getState().system.wsstate);
        if (store.getState().system.wsstate) {
            store.dispatch("SYSTEM_EXIT")
        }
        window.parent && window.parent.postMessage('{v5: "unload"}', "*");
        /ifr/.test(document.body.className) && $.removeEvent(window, "message", onmessage)
    };
    window.onresize = function (e) {
        var isPhone = window.phone || $.mediaPhone();
        var change = !($.isPhone === isPhone);
        if (change) {
            $.isPhone = isPhone;
            var text = $("#text")[0];
            if ($.isPhone) {
                $("body").addClass("phone");
                if (text.value.length === 0) {
                    $("#sendBtn").css("display", "none");
                    $("#menuBtn").css("display", "block")
                } else {
                    $("#sendBtn").css("display", "block");
                    $("#menuBtn").css("display", "none")
                }
                $("#submit").css("display", "none");
                $("#cameraBtn").addClass("hide");
                $("#muteBtn").addClass("hide");
                $(".pnl-input").css("left", "0.5em");
                $(".pnl-text").css("height", "3.2em");
                $("#quesBtn")[0].innerHTML = "&#xe81F";
                $("#uploadBtn")[0].innerHTML = "&#xe826";
                $("#evalBtn")[0].innerHTML = "&#xe81A";
                $("#msgBtn")[0].innerHTML = "&#xe81B";
                $("#humanBtn")[0].innerHTML = "&#xe81E";
                document.getElementById("videoBtn").innerHTML = "&#xe845";
                document.getElementById("voiceBtn").innerHTML = "&#xe846";
                if (store.getState().system.voice) {
                    $("#voiceMbBtn")[0].innerHTML = "&#xe81C"
                } else {
                    $("#voiceMbBtn")[0].innerHTML = "&#xe81D"
                }
            } else {
                if (!window.phone) {
                    $("body").rmClass("phone")
                }
                $(".pnl-input").css("left", "0");
                $("#submit").css("display", "block");
                $("#menuBtn").css("display", "none");
                $("#sendBtn").css("display", "none");
                $(".pnl-text").css("height", "10em");
                $(".pnl-msgs").css("height", "");
                $(".pnl-mask").addClass("hide");
                $("#quesPnl").addClass("hide");
                $("#quesBtn")[0].innerHTML = "&#xe811";
                $("#uploadBtn")[0].innerHTML = "&#xe810";
                $("#evalBtn")[0].innerHTML = "&#xe809";
                $("#msgBtn")[0].innerHTML = "&#xe807";
                $("#humanBtn")[0].innerHTML = "&#xeA01";
                document.getElementById("videoBtn").innerHTML = "&#xe840";
                document.getElementById("voiceBtn").innerHTML = "&#xe841";
                if (store.getState().system.voice) {
                    $("#voiceMbBtn")[0].innerHTML = "&#xe801"
                } else {
                    $("#voiceMbBtn")[0].innerHTML = "&#xe804"
                }
                $("#copyrightSm").css("display", "none")
            }
        }
    };
    window.ondrop = function (e) {
        if (e.preventDefault) {
            e.preventDefault()
        } else {
            e.returnValue = false
        }
        if (e.stopPropagation) {
            e.stopPropagation()
        } else {
            e.cancelBubble = true
        }
    };
    window.ondragover = function (e) {
        if (e.preventDefault) {
            e.preventDefault()
        } else {
            e.returnValue = false
        }
        if (e.stopPropagation) {
            e.stopPropagation()
        } else {
            e.cancelBubble = true
        }
    };
    $("#exit").click(function () {
        if (window.opener) {
            window.close()
        } else {
            var homeurl = store.getState().site.home || "//" + $.autoHost("") + "/";
            !/^(http|\/\/)/i.test(homeurl) && (homeurl = "//" + homeurl);
            window.location.href = homeurl
        }
    });
    $("#max").click(function () {
        store.dispatch("IFR_MAX");
        $.disconType = 2
    });
    $("#min").click(function () {
        store.dispatch("IFR_MIN")
    });
    $("#voicebtn").click(function () {
        store.dispatch("TOGGLE_VOICE")
    });
    $("#voiceMbBtn").click(function () {
        store.dispatch("TOGGLE_VOICE")
    });
    $("#muteBtn").click(function () {
        store.dispatch("TOGGLE_VOICE")
    });
    $("#mask").click(function (e) {
        e || (e = window.event);
        var that = this;
        that === window && (that = $("#mask")[0]);
        if ((e.target || e.srcElement) === that) {
            $.addClass(that, "hide");
            $.rmClass(that, "center");
            $("#iframe").src("");
            if ($.isPhone) {
                $("#mask")[0].style.backgroundColor = "rgba(0,0,0,0.7)"
            } else {
            }
        }
    });
    $("#loading").click(function () {
        if (/refresh/i.test(this.className)) {
            $("#loading").rmClass("refresh");
            store.dispatch("GET_SITE_INFO")
        }
    });

    function defHotClick() {
        var state = store.getState(), json = state.site.defHot, msg = {
            id: $.sendid(),
            dir: 1,
            type: 9,
            stamp: $.stamp(),
            json: {name: json.name, img: json.img, link: json.link, desc: json.desc}
        };
        !state.current.wid && store.dispatch("GET_HUMAN", Object.assign({}, state.system.human, {human: 1}));
        setTimeout(function () {
            store.dispatch("SEND_MSG", msg)
        }, 200);
        sessionStorage.setItem(store.getState().site.id + "_defHot", true);
        $("#defHot").addClass("hide")
    }

    var APP_ID = "8a216da8623c38fd01623d0cf3fc00d3" || "8aaf0708624670f2016275a913ea14e2";
    var APP_TOKEN = "fcd992ed30f5fb47088e722ce5f44e89" || "9eeccb424f3991f0f24c76d48402c61a";
    var TIMESTAMP = $.fTime(Date.now(), "YYYYMMDDhhmmss");
    var _userName, _login = false, _caller, _called, _callId, _callType, _state = 0, _isCalling = false;
    var _view = $("[data-video=distance]")[0], _voiceView = $("#voiceMsg")[0], _localView = $("[data-video=local]")[0];
    $("#videoBtn").click(function () {
        var state = store.getState();
        var calledName = state.current.wid + "at" + state.site.id;
        makeCall(calledName, 1)
    });
    $("#voiceBtn").click(function () {
        var state = store.getState();
        var calledName = state.current.wid + "at" + state.site.id;
        makeCall(calledName, 0)
    });
    $(".call-accept").click(function () {
        acceptCall()
    });
    $(".call-reject").click(function () {
        if (_callOut) {
            releaseCall()
        } else {
            rejectCall()
        }
    });
    $(".hangup").click(function () {
        if (_callOut) {
            releaseCall()
        } else {
            rejectCall()
        }
    });
    $(".mute").click(function () {
        var f = $(this).attr("data-silence")[0] == "true" ? true : false;
        RL_YTX.deployVideoVoice(f, "audio");
        $(this).attr("data-silence", !f);
        $(this).toggleClass("active")
    });
    $(".video-off").click(function () {
        var f = $(this).attr("data-cameraoff")[0] == "true" ? true : false;
        RL_YTX.deployVideoVoice(f, "video");
        $(this).attr("data-cameraoff", !f);
        $(this).toggleClass("active")
    });

    function onOpenCall(callOut, type) {
        _callOut = callOut;
        if (callOut) {
            $(".call-title")[0].innerHTML = $.i18n("正在呼叫") + " - " + (type === 0 ? $.i18n("语音通话") : $.i18n("视频通话"));
            $(".call-accept")[0].style.display = "none"
        } else {
            $(".call-title")[0].innerHTML = $.i18n("客服来电") + " - " + (type === 0 ? $.i18n("语音通话") : $.i18n("视频通话"));
            $(".call-accept")[0].style.display = "inline-block"
        }
        if (type === 0) {
            $(".call-ring i")[0].innerHTML = "&#xe846"
        } else if (type === 1) {
            $(".call-ring i")[0].innerHTML = "&#xe845"
        }
        $(".media-control").rmClass("hide")
    }

    function onCallAccept(callOut) {
        if (_callType === 0) {
            $(".call-title")[0].innerHTML = $.i18n("通话中") + " - " + (_callType === 0 ? $.i18n("语音通话") : $.i18n("视频通话"));
            $(".call-accept")[0].style.display = "none"
        } else {
            if (!/hide/.test($(".media-control")[0].className)) {
                $(".media-control").addClass("hide")
            }
            $(".media-container").rmClass("hide")
        }
    }

    function onCloseCall(callOut, reason) {
        if (!/hide/.test($(".media-control")[0].className)) {
            $(".media-control").addClass("hide")
        }
        if (!/hide/.test($(".media-container")[0].className)) {
            $(".media-container").addClass("hide")
        }
        switch (reason) {
            case"-1":
                $.showinfo($.i18n("通话请求异常"), 3e3);
                break;
            case"0":
            case"ok":
                $.showinfo($.i18n("通话结束"), 3e3);
                break;
            case"175604":
            case"175404":
                $.showinfo($.i18n("对方无法接通"), 3e3);
                break;
            case"175603":
                $.showinfo($.i18n("已挂断"), 3e3);
                break;
            case"175486":
                $.showinfo($.i18n("对方忙"), 3e3);
                break;
            case"175715":
                $.showinfo($.i18n("对方无法接通"), 3e3);
                break;
            default:
                $.showinfo($.i18n("呼叫异常中断"), 3e3);
                break
        }
    }

    function onStateChange(state, obj) {
        switch (state) {
            case 1:
                _isCalling = true;
                break;
            case 2:
                _isCalling = true;
                document.getElementById("call_ring").play();
                onOpenCall(true, _callType);
                $.showinfo($.i18n("正在呼叫"), 3e3);
                break;
            case 3:
                _isCalling = true;
                document.getElementById("call_ring").pause();
                onCallAccept(true);
                $.showinfo($.i18n("建立通话"), 3e3);
                break;
            case 4:
                _isCalling = false;
                document.getElementById("call_ring").pause();
                onCloseCall(true, obj.reason);
                break;
            case 5:
                _isCalling = false;
                document.getElementById("call_ring").pause();
                onCloseCall(false, obj.reason);
                break;
            case 6:
                _isCalling = false;
                document.getElementById("call_ring").play();
                onOpenCall(false, _callType);
                $.showinfo($.i18n("收到呼叫"), 3e3);
                break;
            case 7:
                onCallAccept(false);
                $.showinfo($.i18n("建立通话"), 3e3);
                break
        }
    }

    function isLogin() {
        return _login
    }

    function init(appid) {
        APP_ID = appid || APP_ID;
        var resp = RL_YTX.init(APP_ID);
        if (170002 == resp.code) {
            return void 0
        } else if (174001 == resp.code) {
            return void 0
        } else if (200 == resp.code) {
            var unsupport = resp.unsupport
        }
        RL_YTX.setLogClose();
        console.log("--- RL_YTX.init", resp)
    }

    function login(oid) {
        _userName = oid;
        TIMESTAMP = $.fTime(Date.now(), "YYYYMMDDhhmmss");
        var sig = getLocalSig(_userName);
        console.log("--- RL_YTX.login userName:" + _userName + " sig:" + sig);
        var loginBuilder = new RL_YTX.LoginBuilder;
        loginBuilder.setType(1);
        loginBuilder.setUserName(_userName);
        loginBuilder.setSig(sig);
        loginBuilder.setTimestamp(TIMESTAMP);
        RL_YTX.login(loginBuilder, function (obj) {
            console.log("--- RL_YTX.login", obj);
            _login = true;
            RL_YTX.onMsgReceiveListener(function (obj) {
                console.log("--- RL_YTX.onMsgReceiveListener", obj)
            });
            RL_YTX.onNoticeReceiveListener(function (obj) {
                console.log("--- RL_YTX.onNoticeReceiveListener", obj)
            });
            RL_YTX.onConnectStateChangeLisenter(function (obj) {
                obj.code;
                console.log("--- RL_YTX.onConnectStateChangeLisenter", obj);
                if (obj.code == 5) {
                    onCloseCall(true, "-1");
                    login(_userName)
                }
            });
            RL_YTX.onCallMsgListener(function (obj) {
                console.info("--- RL_YTX.onCallMsgListener", obj);
                if (obj.code == 200) {
                    _callId = obj.callId;
                    _caller = obj.caller;
                    _called = obj.called;
                    _callType = obj.callType;
                    _state = obj.state;
                    onStateChange(obj.state, obj)
                } else {
                }
            })
        }, function (obj) {
        })
    }

    function logout() {
        RL_YTX.logout(function () {
            _login = false;
            console.log("--- RL_YTX.logout success")
        }, function (obj) {
            console.warn("--- RL_YTX.logout failed")
        })
    }

    function acceptCall() {
        var voipAcceptBuilder = new RL_YTX.AcceptCallBuilder;
        voipAcceptBuilder.setCallId(_callId);
        voipAcceptBuilder.setCaller(_caller);
        if (_callType === 0) {
            RL_YTX.setCallView(_voiceView, null)
        } else {
            RL_YTX.setCallView(_view, _localView)
        }
        RL_YTX.accetpCall(voipAcceptBuilder, function () {
            console.log("RL_YTX.accetpCall success")
        }, function callback(obj) {
            console.warn("RL_YTX.accetpCall err:", obj)
        });
        document.getElementById("call_ring").pause()
    }

    function rejectCall() {
        var VoipRejectBuilder = new RL_YTX.RejectCallBuilder;
        VoipRejectBuilder.setCallId(_callId);
        VoipRejectBuilder.setCaller(_caller);
        RL_YTX.rejectCall(VoipRejectBuilder, function () {
            console.log("RL_YTX.rejectCall success")
        }, function (obj) {
            console.warn("RL_YTX.rejectCall err:", obj)
        });
        onCloseCall(false, "ok");
        document.getElementById("call_ring").pause()
    }

    function makeCall(called, callType) {
        _called = called || "jfkqdfbk18ok8we";
        _caller = _userName;
        _callType = callType;
        console.log("RL_YTX.makeCall called:" + called + " callType:" + callType);
        var makeCallBuilder = new RL_YTX.MakeCallBuilder;
        makeCallBuilder.setCalled(_called);
        makeCallBuilder.setCallType(callType);
        if (_callType === 0) {
            RL_YTX.setCallView(_voiceView, null)
        } else {
            RL_YTX.setCallView(_view, _localView)
        }
        RL_YTX.makeCall(makeCallBuilder, function () {
            console.log("RL_YTX.makeCall success")
        }, function callback(obj) {
            console.warn("RL_YTX.makeCall failed:", obj)
        });
        $.showinfo("对外呼叫", 3e3)
    }

    function releaseCall() {
        var VoipReleaseBuilder = new RL_YTX.ReleaseCallBuilder;
        VoipReleaseBuilder.setCallId(_callId);
        VoipReleaseBuilder.setCaller(_caller);
        VoipReleaseBuilder.setCalled(_called);
        RL_YTX.releaseCall(VoipReleaseBuilder, function () {
            console.log("RL_YTX.releaseCall success")
        }, function (obj) {
            console.warn("RL_YTX.releaseCall failed:", obj)
        });
        onCloseCall(true, "ok");
        document.getElementById("call_ring").pause()
    }

    function getLocalSig(userName) {
        return hex_md5(APP_ID + userName + TIMESTAMP + APP_TOKEN)
    }

    $.cc = {
        isLogin: isLogin,
        init: init,
        login: login,
        logout: logout,
        makeCall: makeCall,
        acceptCall: acceptCall,
        rejectCall: rejectCall,
        releaseCall: releaseCall,
        getLocalSig: getLocalSig
    };
    window.store = store;
    if (conf && conf.site && conf.site.id == "10000" && $.public && /kehu\.html/.test(window.location.href)) {
        window.location.href = window.location.href.replace(/kehu\.html/, "kehu_new.html")
    }
    if (conf.site.id != "10000") {
        $("#favicon")[0].href = "//desk.v5kf.com/favicon1.ico"
    }
    var ifr = document.createElement("iframe");
    ifr.id = "iframe";
    ifr.className = "link-pnl hide";
    if (!$.isPhone || $.isAndroid || $.isiOS && $.isWeChat) {
        $("#mask").appendChild(ifr)
    }
    window.store.dispatch("INIT_STATE", conf);
    window.store.dispatch("GET_SITE_INFO");
    window.store.dispatch("GET_SITE_QUES")
});
