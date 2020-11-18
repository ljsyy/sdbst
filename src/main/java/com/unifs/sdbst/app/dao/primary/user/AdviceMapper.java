package com.unifs.sdbst.app.dao.primary.user;

import com.unifs.sdbst.app.bean.user.Advice;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AdviceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Advice record);

    Advice selectByPrimaryKey(String id);

    List<Advice> selectAll();

    int updateByPrimaryKey(Advice record);
}