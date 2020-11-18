package com.unifs.sdbst.app.service.user;

import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.bean.user.UserSwt;
import com.unifs.sdbst.app.dao.primary.user.UserMapper;
import com.unifs.sdbst.app.dao.primary.user.UserSwtMapper;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @version V1.0
 * @title: UserSwtService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/22 16:11
 */
@Service
@Transactional
public class UserSwtService {
    @Autowired
    private UserSwtMapper swtMapper;
    @Autowired
    private UserMapper userMapper;

    public User saveAndLogin(UserSwt swt, String loginFlag, HttpServletRequest request) throws Exception {

        //获取该用户对应的百事通帐户信息
        User user = userMapper.selectByFactor(swt.getMobile(), swt.getCid(), swt.getName());
        if (user == null) {
            //关联账户不存在，生成百事通帐号
            User saveUser = new User();
            saveUser.setLoginName(swt.getName());
            saveUser.setPhone(swt.getMobile());
            //设置身份证，以及类型
            saveUser.setIdentityNumber(swt.getCid());
            saveUser.setIdentityType("mainland");
            saveUser.setAccount(swt.getAccount());
            saveUser.setAccountType(swt.getAccountType());
            saveUser.setClevel(swt.getClevel());
            saveUser.setCtype(swt.getCtype());
            saveUser.setOrigin(swt.getOrigin());
            saveUser.setLoginType(swt.getLoginType());
            saveUser.setUid(swt.getUid());
            saveUser.setUversion(swt.getUversion());
            saveUser.setLinkPersonCid(swt.getLinkPersonCid());
            saveUser.setLinkPersonCtype(swt.getLinkPersonCtype());
            saveUser.setLinkPersonName(swt.getLinkPersonName());
            //随机生成默认密码
            String password = UUID.randomUUID().toString();
            saveUser.setPassword(EncryptUtil.entryptPassword(password));
            saveUser.preInsert();
            saveUser.setLoginDate(new Date());
            saveUser.setLoginCount(1);
            saveUser.setLoginIp(HttpUtil.getClientIp(request));
            //设置登录类型，android/ios
            if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
                saveUser.setLoginFlag(loginFlag);
            }
            userMapper.insert(saveUser);

            return saveUser;
        } else {

            //更新登录信息
            user.setLoginDate(new Date());

            if (user.getLoginCount() == null) {
                user.setLoginCount(1);
            } else {
                user.setLoginCount(user.getLoginCount() + 1);
            }
            user.setLoginIp(HttpUtil.getClientIp(request));
            user.setLoginName(swt.getName());
            user.setPhone(swt.getMobile());
            user.setAccount(swt.getAccount());
            user.setAccountType(swt.getAccountType());
            user.setClevel(swt.getClevel());
            user.setCtype(swt.getCtype());
            user.setOrigin(swt.getOrigin());
            user.setLoginType(swt.getLoginType());
            user.setUid(swt.getUid());
            user.setUversion(swt.getUversion());
            user.setLinkPersonCid(swt.getLinkPersonCid());
            user.setLinkPersonCtype(swt.getLinkPersonCtype());
            user.setLinkPersonName(swt.getLinkPersonName());
            //设置登录类型，android/ios
            if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
                user.setLoginFlag(loginFlag);
            }
            //记入数据库
            userMapper.update(user);
            return user;
        }

    }

}
