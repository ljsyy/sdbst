/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.sdcount;

import com.unifs.sdbst.app.bean.sdcount.SdIndex;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * sDAO接口
 * @author chengpneng
 * @version 2017-11-23
 */
@Component
public interface SdIndexMapper {
	public SdIndex findId(String name);

	public List<SdIndex> findName();

	public SdIndex get(String id);

	public List<SdIndex> findList(SdIndex sdIndex);

	public void insert(SdIndex sdIndex);

	public void delete(SdIndex sdIndex);


}