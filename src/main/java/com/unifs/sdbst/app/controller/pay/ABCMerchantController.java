package com.unifs.sdbst.app.controller.pay;

import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.pay.PaymentMerchant;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.pay.ABCMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @title: ABCMerchantController
 * @projectName sdbst
 * @description: 农行商户控制器
 * @author： 张恭雨
 * @date 2020/3/31 11:43
 */
@RestController
@RequestMapping(value = "/ABCMerchant")
public class ABCMerchantController {
    @Autowired
    private ABCMerchantService merchantService;

    public Resp list(String type, String merchantName) {
        List<PaymentMerchant> merchantList = merchantService.findList(type, merchantName);
        if (merchantList == null) {
            Resp resp = new Resp(RespCode.DEFAULT);
            return resp;
        }
        Resp resp = new Resp(RespCode.DEFAULT);
        resp.setData(merchantList);
        return resp;
    }
}
