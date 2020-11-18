package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.livingPay.PayOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PayOrderMapper {

	 //
     int insert(PayOrder payOrder);

     List<PayOrder> getOrderList(PayOrder payOrder);

     PayOrder getOrderMess(PayOrder payOrder);

    int update(PayOrder payOrder);

    int updateStatusByDdbh(PayOrder payOrder);
}