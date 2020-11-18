package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.livingPay.PayOrder;
import com.unifs.sdbst.app.dao.primary.life.PayOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;


    public int insert(PayOrder payOrder){
        return payOrderMapper.insert(payOrder);
    }


    public List<PayOrder> getOrderList(PayOrder payOrder){
        return payOrderMapper.getOrderList(payOrder);
        // return null;
    }
    public PayOrder getOrderMess(PayOrder payOrder){
        return payOrderMapper.getOrderMess(payOrder);
        // return null;
    }

    public int update(PayOrder payOrder){
        return payOrderMapper.update(payOrder);
    }

    public int updateStatusByDdbh(PayOrder payOrder){
        return payOrderMapper.updateStatusByDdbh(payOrder);
    }
}
