package com.unifs.sdbst.app.dao.primary.cms;

import com.unifs.sdbst.app.bean.cms.Bulletin;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface BulletinMapper {
    int deleteByPrimaryKey(String id);

    int insert(Bulletin record);

    Bulletin selectByPrimaryKey(String id);

    List<Bulletin> selectAll();

    int updateByPrimaryKey(Bulletin record);
}