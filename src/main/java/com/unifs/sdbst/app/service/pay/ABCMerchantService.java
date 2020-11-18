package com.unifs.sdbst.app.service.pay;

import com.unifs.sdbst.app.bean.pay.PaymentMerchant;
import com.unifs.sdbst.app.dao.primary.pay.PaymentMerchantMapper;
import com.unifs.sdbst.app.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @title: ABCMerchantService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2020/3/31 14:06
 */
@Service
public class ABCMerchantService {
    @Autowired
    private PaymentMerchantMapper merchantMapper;

    public List<PaymentMerchant> findList(String type, String merchantName) {
        if (type == null || "".equals(type.trim())) {
            throw new MyException("类型不能为空！", 0);
        }
        return merchantMapper.selectByFactor(type, merchantName);
    }
}
