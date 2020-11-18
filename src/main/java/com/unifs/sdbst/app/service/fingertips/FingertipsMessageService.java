package com.unifs.sdbst.app.service.fingertips;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.bean.fingertips.FingertipsMessage;
import com.unifs.sdbst.app.dao.primary.fingertips.FingertipsMessageMapper;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.FingertipsMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FingertipsMessageService {
    @Value("${aes.key}")
    private String aesKey;

    @Autowired
    private FingertipsMessageMapper fingertipsMessageMapper;

    private FingertipsMessageUtils fingertipsMessageUtils;

    public void insert(FingertipsMessage fingertipsMessage, JSONObject gotInfos) throws Exception {

//        String dePhoneEnc = gotInfos.getString("dePhoneEnc");
        String matterId = gotInfos.getString("matterId");
        String agentCertificateNum = gotInfos.getString("agentCertificateNum");
        String agentName = gotInfos.getString("agentName");
        String agentPhone = gotInfos.getString("agentPhone");
        String caseId = gotInfos.getString("caseId");

        gotInfos.remove("dePhoneEnc");
        gotInfos.remove("matterId");
        gotInfos.remove("agentCertificateNum");
        gotInfos.remove("agentName");
        gotInfos.remove("agentPhone");
        gotInfos.remove("caseId");

        fingertipsMessage.setMatterid(matterId);
        fingertipsMessage.setAgentCertificatenum(agentCertificateNum);
//        fingertipsMessage.setPhone(EncryptUtil.aesDecrypt(dePhoneEnc,aesKey));
        fingertipsMessage.setAgentphone(agentPhone);
        fingertipsMessage.setName(agentName);
        fingertipsMessage.setCaseid(caseId);
        fingertipsMessage.setCasecreatedate(new Date());
        fingertipsMessage.setStatus("待预接件");
        fingertipsMessage.setStatuschangetime(new Date());

        fingertipsMessageMapper.insert(fingertipsMessage);
    }



//    获取需要发送短信的列表
    public List<FingertipsMessage> needSend(){
        List<FingertipsMessage> list=fingertipsMessageMapper.needSend();
        return list;
    }


//    获取需要查询status的caseID
    public List<FingertipsMessage> needUpdate(){
        List<FingertipsMessage> list=fingertipsMessageMapper.selectNeedUpdate();
        return list;

    }

//    更新指定caseID的状态
    public void updateByCaseId(FingertipsMessage fingertipsMessage){
        fingertipsMessageMapper.updateByCaseid(fingertipsMessage);

    }
}
