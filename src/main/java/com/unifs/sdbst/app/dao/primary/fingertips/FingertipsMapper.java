package com.unifs.sdbst.app.dao.primary.fingertips;

import com.unifs.sdbst.app.bean.fingertips.UserHistoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface FingertipsMapper {

    public int findUserHistoryByPhone(UserHistoryVO userHistoryVO);

    public int addUserHistory(UserHistoryVO userHistoryVO);

    public int updateUserHistoryByPhone(UserHistoryVO userHistoryVO);

    public UserHistoryVO findUserHistory(@Param("phone") String phone);

    public int findUserAddressCountByAgentPhone(Map<String,String> userAddress);

    public int updateUserAddress(Map<String,String> userAddress);

    public int addUserAddress(Map<String,String> userAddress);

    public Map<String,String> findUserAddressByAgentPhone(@Param("agentPhone") String agentPhone);
}
