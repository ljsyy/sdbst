package com.unifs.sdbst.app.service;


import com.unifs.sdbst.app.bean.server.DataBackup;
import com.unifs.sdbst.app.bean.server.HardLog;
import com.unifs.sdbst.app.bean.server.ServerInfo;
import com.unifs.sdbst.app.dao.second.server.DataBackupLogMapper;
import com.unifs.sdbst.app.dao.second.server.HardLogDao;
import com.unifs.sdbst.app.dao.second.server.ServerInfoMapper;
import com.unifs.sdbst.app.dao.third.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ServerService {
    @Autowired
    private HardLogDao hardLogDao;
    public void insertLog(HardLog hardLog){
        hardLogDao.insert(hardLog);
    }

    public int updateLog(){
        int count=hardLogDao.update();
        return count;
    }

    //    查询有问题的服务器数据
    public List<HardLog> getLogs(HardLog hardLog){
        List<HardLog> list=hardLogDao.selectNewLog(hardLog);

        return list;
    }
    //    获取服务器信息
    @Autowired
    private ServerInfoMapper serverInfoMapper;
    public List<ServerInfo> getServerInfo(ServerInfo serverInfo){
        List<ServerInfo> serverList=serverInfoMapper.selectByType(serverInfo);
        return serverList;
    }

//    获取最近备份APPUSER表的的时间并入库
    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private DataBackupLogMapper dataBackupLogMapper;
    public void getBackTime(){
        DataBackup dataBackup=new DataBackup();
        Date backTime=appUserMapper.getBackTime();
        dataBackup.setBackTime(backTime);
        dataBackup.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        dataBackup.setOperateTime(new Date());
        int count=dataBackupLogMapper.insert(dataBackup);
        if (count>=1){
            System.out.println("最近备份时间"+backTime);
        }
    }


}
