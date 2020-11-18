/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.menus;


import com.unifs.sdbst.app.common.entity.DataEntity;

/**
 * 菜单Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Tag extends DataEntity<Tag> {

	private static final long serialVersionUID = 1L;
	private String tag;			// 标签，用于搜索
	private int nums;
	private String tags;	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	
}