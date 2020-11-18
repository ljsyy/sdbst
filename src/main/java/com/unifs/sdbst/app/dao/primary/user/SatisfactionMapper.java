package com.unifs.sdbst.app.dao.primary.user;

import com.unifs.sdbst.app.bean.user.Satisfaction;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SatisfactionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Satisfaction record);

    Satisfaction selectByPrimaryKey(String id);

    List<Satisfaction> selectAll();

    int updateByPrimaryKey(Satisfaction record);
}