package com.unifs.sdbst.app.service.user;

import com.unifs.sdbst.app.bean.user.UserOthers;
import com.unifs.sdbst.app.dao.primary.user.UserOthersMapper;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @title: OtherService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/19 14:54
 */
@Service
@Transactional
public class OtherService {
    @Autowired
    private UserOthersMapper othersMapper;

    public void save(UserOthers userOthers) throws Exception {
        //判断该类型帐号是否已有绑定
        UserOthers userOthers1=othersMapper.selectByFilter(userOthers.getUserId(),userOthers.getType());
        if(userOthers1!=null){
            throw new MyException("此用户已绑定第三方账号，不可再次绑定",0);
        }
        //生成ID
        userOthers.setId(IdGen.uuid());
        //密码加密
        userOthers.setPsd(EncryptUtil.entryptPassword(userOthers.getPsd()));
        //保存帐户信息
        othersMapper.insert(userOthers);
    }
    /*解除绑定*/
    public void untie(String id){
        othersMapper.delete(id);
    }

    public void update(UserOthers others){
        othersMapper.update(others);
    }

    public List<UserOthers> getList(String userId){
        return othersMapper.selectByUserId(userId);
    }
}
