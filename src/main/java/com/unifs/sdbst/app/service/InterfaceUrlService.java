package com.unifs.sdbst.app.service;

import com.unifs.sdbst.app.base.RequestInfo;
import com.unifs.sdbst.app.utils.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterfaceUrlService {
	public static final String URL="/data/";
	private static Map<String,String> map=new HashMap<String,String>();

	//初始化map集合
	static {
		String fileName= "interface-url.properties";
		//获取names集合
		Set<String> names= PropertiesUtil.getNames(fileName);
		for(String str:names){
			map.put(str,URL+PropertiesUtil.getKey(fileName,str));
		}

	}

	//通过titleName获取URL
	public static String getUrl(RequestInfo ri) {
		return map.get( ri.getHeadName() );
	}

	public static String getUrl(String type) {
		return map.get(type);
	}

}

