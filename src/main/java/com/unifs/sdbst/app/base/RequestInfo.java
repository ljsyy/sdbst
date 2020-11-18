package com.unifs.sdbst.app.base;

import lombok.Data;

/**
 * 请求路径参数
 * @author 张恭雨
 *
 */
@Data
public class RequestInfo {
	private String url;	//接口路径
	private String function; //功能名称
	private String returnType; // 点击列表项,详情返回类型 如: json jsonp 网页 xml
	private String headName;	//网页头名
	private String titleName;	//标题名
	private Integer pageIndex;  //当前页
	private String data;		//返回的接口数据
	
	private String id;		//处理jsonp格式 时的属性
	private String fid;     //处理jsonp格式 时的属性
	private Boolean flag;

	
	
}
