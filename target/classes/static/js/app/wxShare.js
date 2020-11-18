//lib:<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
//微信分享接口

/*
 * newhref:接口地址
 * newurl:分享地址
 * newtitle:分享标题
 * newdesc:分享描述
 * newlink:分享链接
 * newimgUrl:分享图片
 * */

function wxShare(newhref,newurl,newtitle,newdesc,newlink,newimgUrl){
	//jquery:url
	var href=newhref;
	var url=newurl;
	//config
	var timestamp;
	var noncestr;
	var signature;
	var appId;
	//share
	var title=newtitle;
	var desc=newdesc;
	var link=newlink;
	var imgUrl=newimgUrl;
	 //获取签名
	function jQueryReady(){
		console.log(url)
		jQuery.ajax({
			url: href,
			type: "POST",  
			dataType : "json",
			async: false,
			data:{url:url},
			cache:true,  
			success: function(data) {
				 	timestamp=data.timestamp; 
			        noncestr=data.noncestr; 
			        signature=data.signature;
			        appId=data.appId;
			        //alert("timestamp:"+timestamp+",noncestr:"+noncestr+",appId:"+appId+",signature:"+signature);
			        shareConfig();
			}
		});
	}
	function shareConfig(){
		//console.log("timestamp:"+timestamp+",noncestr:"+noncestr+",appId:"+appId+",signature:"+signature);
		wx.config({
			debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId:appId, // 必填，公众号的唯一标识
			timestamp:timestamp , // 必填，生成签名的时间戳
			nonceStr:noncestr, // 必填，生成签名的随机串
			signature:signature,// 必填，签名，见附录1
			jsApiList:[ 'onMenuShareAppMessage','onMenuShareQZone','onMenuShareQQ','onMenuShareWeibo','onMenuShareTimeline']
		});
	}
	function wxReady(){
		// 获取"分享到朋友圈"按钮点击状态及自定义分享内容接口  
	    /* wx.onMenuShareTimeline({  
	        title: '融讯', // 分享标题  
	        link: 'http://www.wrtrd.net/book/',  
	        imgUrl: 'http://www.wrtrd.net/book/images/wxbook.jpg' // 分享图标  
	    });   */
		wx.onMenuShareTimeline({
	        title: title, // 分享标题
	        link: link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        imgUrl: imgUrl, // 分享图标
	        success: function () {},
	        cancel: function () {}
	    });
	    // 获取"分享给朋友"按钮点击状态及自定义分享内容接口  
	    wx.onMenuShareAppMessage({  
	        title: title, // 分享标题  
	        desc: desc, // 分享描述  
	        link: link,  
	        imgUrl: imgUrl, // 分享图标  
	        type: 'link' // 分享类型,music、video或link，不填默认为link  
	    });    
	    //获取"分享到QQ"按钮点击状态及自定义分享内容接口  
	    wx.onMenuShareQQ({  
	    	title:title, // 分享标题  
	        desc: desc, // 分享描述  
	        link: link,  
	        imgUrl: imgUrl, // 分享图标  
	    });  
	    //获取"分享到腾讯微博"按钮点击状态及自定义分享内容接口  
	    wx.onMenuShareWeibo({  
	    	title:title, // 分享标题  
	        desc: desc, // 分享描述  
	        link: link,  
	        imgUrl: imgUrl, // 分享图标  
	    });  
	    //获取"分享到QQ空间"按钮点击状态及自定义分享内容接口  
	    wx.onMenuShareQZone({  
	    	title:title, // 分享标题  
	        desc: desc, // 分享描述  
	        link: link,  
	        imgUrl: imgUrl, // 分享图标  
	    }); 
	}
	return {run:jQueryReady,wxReady:wxReady}
} 
wx.error(function(res){
	alert("验证失败！res:"+res.errMsg);
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});
