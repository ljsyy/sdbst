package com.unifs.sdbst.app.service.enterprise;

import com.unifs.sdbst.app.bean.enterprise.EnterpriseReserve;
import com.unifs.sdbst.app.bean.enterprise.EnterpriseUser;
import com.unifs.sdbst.app.dao.primary.enterprise.EnterpriseReserveMapper;
import com.unifs.sdbst.app.dao.primary.enterprise.EnterpriseUserMapper;
import com.unifs.sdbst.app.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @version V1.0
 * @title: EnterpriseService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2020/2/19 21:32
 */
@Service
@Transactional
public class EnterpriseService {
    @Autowired
    private EnterpriseReserveMapper reserveMapper;
    @Autowired
    private EnterpriseUserMapper userMapper;

    public void saveReserve(EnterpriseReserve reserve) {
        //根据社会统一信用代码获取企业信息
        EnterpriseUser user = userMapper.selectByPrimaryKey(reserve.getCreditCode());
        //判断提交的口罩数量是否合法
//        if (reserve.getNumberMasks() > user.getInsuredNumber() * 10) {
//            //口罩数量不合规
//            throw new MyException("口罩数量不合规");
//        }
        reserve.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        reserve.setCreateDate(new Date());
        reserveMapper.insert(reserve);
    }

    //登录校验
    public EnterpriseUser verifyLogin(String creditCode, String identityNumber) {
        EnterpriseUser user = userMapper.selectByPrimaryKey(creditCode);
        if (user == null) {
            throw new MyException("没有找到该企业代码!");
        }
        //判断企业类型是否为空
        if (user.getType() == null || "".equals(user.getType())) {
            throw new MyException("登录失败!");
        }
//        //验证身份证后六位是否正确
//        identityNumber = identityNumber.toLowerCase();
//        String dbIdentity = user.getIdentityNumber().substring(user.getIdentityNumber().length() - 6,
//                user.getIdentityNumber().length()).toLowerCase();
//        if (!dbIdentity.equals(identityNumber)) {
//            throw new MyException("密码错误!");
//        }
        return user;
    }

    public EnterpriseReserve getLatestEnterpriseReserveByCreditCode(String creditCode) {
        return reserveMapper.getLatestEnterpriseReserveByCreditCode(creditCode);
    }

    public List<EnterpriseReserve> getEnterpriseReserveByCreditCode(String creditCode) {
        return reserveMapper.getEnterpriseReserveByCreditCode(creditCode);
    }

    public List<EnterpriseReserve> getEnterpriseReserveByCreditCodeSeven(String creditCode) {
        return reserveMapper.getEnterpriseReserveByCreditCodeSeven(creditCode);
    }

    //更新预约信息
    public int updateReserve(EnterpriseReserve reserve) {
        //根据社会统一信用代码获取企业信息
        EnterpriseUser user = userMapper.selectByPrimaryKey(reserve.getCreditCode());
        //判断提交的口罩数量是否合法
//        if (reserve.getNumberMasks() > user.getInsuredNumber() * 10) {
//            //口罩数量不合规
//            throw new MyException("口罩数量不合规");
//        }
        reserve.setUpdateDate(new Date());
        return reserveMapper.update(reserve);
    }


}
