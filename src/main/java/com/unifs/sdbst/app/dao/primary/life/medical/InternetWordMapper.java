package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.InternetWord;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface InternetWordMapper {
    int insert(InternetWord record);

    List<InternetWord> selectAll();

    List<String> test();
}