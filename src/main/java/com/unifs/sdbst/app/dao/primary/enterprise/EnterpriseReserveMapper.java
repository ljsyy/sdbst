package com.unifs.sdbst.app.dao.primary.enterprise;

import com.unifs.sdbst.app.bean.enterprise.EnterpriseReserve;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EnterpriseReserveMapper {
    int insert(EnterpriseReserve record);

    List<EnterpriseReserve> selectAll();

    EnterpriseReserve getLatestEnterpriseReserveByCreditCode(String creditCode);

    List<EnterpriseReserve> getEnterpriseReserveByCreditCode(String creditCode);

    List<EnterpriseReserve> getEnterpriseReserveByCreditCodeSeven(String creditCode);

    int update(EnterpriseReserve reserve);
}
