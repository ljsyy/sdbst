var map = "";
var map_mk = "";//定位坐标对象
var navigationControl ="";
//百度地图
var baidumap = {
	data : {
		mylng : "",
		mylat : "",
	},
	init : function(str){
		// 百度地图API功能
		map = new BMap.Map(str);
		map.centerAndZoom(new BMap.Point(113.24887,22.84264), 12);
		// 添加带有定位的导航控件
		navigationControl = new BMap.NavigationControl({
			// 靠左上角位置
			anchor: BMAP_ANCHOR_TOP_LEFT,
			// LARGE类型
			type: BMAP_NAVIGATION_CONTROL_LARGE,
			// 启用显示定位
			enableGeolocation: true
		});
		map.addControl(navigationControl);
		// 添加定位控件
		// var geolocationControl = new BMap.GeolocationControl();
		// geolocationControl.addEventListener("locationSuccess", function(e){
		//   // 定位成功事件
		//   var address = '';
		//   address += e.addressComponent.province;
		//   address += e.addressComponent.city;
		//   address += e.addressComponent.district;
		//   address += e.addressComponent.street;
		//   address += e.addressComponent.streetNumber;
		//   alert("当前定位地址为：" + address);
		// });
		// geolocationControl.addEventListener("locationError",function(e){
		//   // 定位失败事件
		//   alert(e.message);
		// });
		//map.addControl(geolocationControl);
	},
	setlocation : function(point){
		var my = this;
		map.setZoom(15);
		if(map_mk != ""){
			map.removeOverlay(map_mk);
		}
		var new_point = new BMap.Point(point.lng,point.lat);
		map.centerAndZoom(new_point, 15);
		map_mk = new BMap.Marker(new_point);
		map.addOverlay(map_mk);
		map.panTo(new_point);
		my.data.mylng = point.lng;
		my.data.mylat = point.lat;
	},
	geolocation : function(callback){
		var my = this;
		var geolocation = new BMap.Geolocation();
		// 开启SDK辅助定位
		geolocation.enableSDKLocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				map.setZoom(13);
				if(map_mk != ""){
					map.removeOverlay(map_mk);
				}
				console.log(r)
				map.centerAndZoom(r.point, 15);
				map_mk = new BMap.Marker(r.point);
				map.addOverlay(map_mk);
				map.panTo(r.point);
				my.data.mylng = r.point.lng;
				my.data.mylat = r.point.lat;
				if(typeof callback != "undefined")
					callback();
				//alert('您的位置：'+r.point.lng+','+r.point.lat);
			}
			else {
				alert('failed' + this.getStatus());
			}        
		});
	},
	driverroute : function(pointS,pointE){
			//p1 获取的定位坐标
           var p1=new BMap.Point(pointS.lng,pointS.lat);
           var p2=new BMap.Point(pointE.lng,pointE.lat);
          // alert(pointS.lng + " " + pointS.lat)
           var walking = new BMap.WalkingRoute(map, {renderOptions: {map: map, panel: "r-result", autoViewport: true}});
           walking.search(p1, p2);
	}

}