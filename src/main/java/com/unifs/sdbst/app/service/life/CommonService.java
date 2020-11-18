package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.SmallFoodWorkshop;
import com.unifs.sdbst.app.bean.life.WIFI;
import com.unifs.sdbst.app.common.constant.GlobalURL;
import com.unifs.sdbst.app.dao.primary.life.SmallFoodWorkshopMapper;
import com.unifs.sdbst.app.dao.primary.life.WIFIMapper;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @version V1.0
 * @title: WIFIService
 * @projectName sdbst
 * @description: 生活公共业务逻辑处理
 * @author： 张恭雨
 * @date 2019/12/5 15:15
 */
@Service
public class CommonService {
    @Autowired
    private WIFIMapper wifiMapper;
    @Autowired
    private SmallFoodWorkshopMapper foodWorkshopMapper;

    public void sendWIFIMsg(String phone) throws UnsupportedEncodingException {
        //获取需要发送短信的内容
        List<WIFI> wifiList = wifiMapper.selectAll();
        StringBuffer content = new StringBuffer("联通5G WIFI ");
        for (WIFI wifi : wifiList) {
            content.append("名称：");
            content.append(wifi.getName());
            content.append(" 登录密码：");
            content.append(wifi.getPassword());
        }
        content.append("(温馨提示：本密码有效使用时限为1天)");
        //调用短信接口发送短信
        HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content.toString(), "gb2312") + "&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
    }

    //查找小作坊信息通过登记编号或者名称
    public SmallFoodWorkshop find(String keyword) {
        return foodWorkshopMapper.selectByKeyword(keyword.trim().toUpperCase());
    }
}
