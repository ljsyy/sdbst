package com.unifs.sdbst.app.dao.primary.life.bank;

import com.unifs.sdbst.app.bean.life.BankEntity.BankService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankServiceMapper {
    int insert(BankService record);

    List<BankService> selectAll();

    List<BankService> getServiceList();
}
