package com.unifs.sdbst.app.dao.primary.fingertips;

import com.unifs.sdbst.app.bean.fingertips.FingertipsMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FingertipsMessageMapper {
    int insert(FingertipsMessage record);

    int insertSelective(FingertipsMessage record);

//    获取需要更新状态的人员信息
    List<FingertipsMessage> selectNeedUpdate();
//    获取需要发送短信的人员信息
    List<FingertipsMessage> needSend();
//    update指定caseid的状态
    int updateByCaseid(FingertipsMessage record);

}