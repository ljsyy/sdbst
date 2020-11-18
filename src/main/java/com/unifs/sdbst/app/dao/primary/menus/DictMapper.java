package com.unifs.sdbst.app.dao.primary.menus;

import com.unifs.sdbst.app.bean.menus.Dict;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictMapper {

    List<Dict> findList(Dict dict);
}