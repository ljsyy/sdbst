package com.unifs.sdbst.app.dao.primary.pay;

import com.unifs.sdbst.app.bean.pay.PaymentMerchant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentMerchantMapper {
    int deleteByPrimaryKey(String projectCode);

    int insert(PaymentMerchant record);

    PaymentMerchant selectByPrimaryKey(String projectCode);

    List<PaymentMerchant> selectAll();

    int updateByPrimaryKey(PaymentMerchant record);

    List<PaymentMerchant> selectByFactor(@Param("type") String type, @Param("merchantName") String merchantName);
}