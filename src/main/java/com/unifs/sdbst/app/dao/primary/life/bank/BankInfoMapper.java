package com.unifs.sdbst.app.dao.primary.life.bank;

import com.unifs.sdbst.app.bean.life.BankEntity.BankInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankInfoMapper {
    int insert(BankInfo record);

    List<BankInfo> selectAll();
}
