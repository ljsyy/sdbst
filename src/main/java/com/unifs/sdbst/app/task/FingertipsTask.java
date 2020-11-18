package com.unifs.sdbst.app.task;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.unifs.sdbst.app.bean.server.HardLog;
import com.unifs.sdbst.app.bean.server.ServerInfo;
import com.unifs.sdbst.app.common.constant.GlobalURL;
import com.unifs.sdbst.app.service.ServerService;
import com.unifs.sdbst.app.service.fingertips.FingertipsService;
import com.unifs.sdbst.app.utils.FingertipsMessageUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.IdGen;
import com.unifs.sdbst.app.utils.SftpUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

@Configuration
@EnableScheduling   //定时任务
public class FingertipsTask {
    @Autowired
    private FingertipsService fingertipsService;
    @Autowired
    private ServerService serverService;
    @Autowired
    private FingertipsMessageUtils fingertipsMessageUtils;

//    指尖办定时获取已办事项状态，并定时给用户发短信

 //   @Scheduled(cron = "0 2,12,22,32,42,52 * * * ?")
    public void FingertipsMessage(){
//        更新状态信息
        fingertipsMessageUtils.updateStatus();

        try {
            fingertipsMessageUtils.sendMessage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    @Autowired
    StringEncryptor encryptor;

//    @Scheduled(cron = "0 2,12,22,32,42,52 * * * ?")
 //   @Logger
//    定时任务：通报服务器状况
    public void readTxtTest() throws IOException, SftpException {
        String username = "s1UEdT5BVzVWHSGf0oOBAg==";
        int port = 22;
        String Path = "/home/file";
        String host = "RRuuEac8lJC7cIWbM3xyDfG2rjvMspPJ";
        String password = "ZPfbqLorL/cOxppz+UqhBqXQozvzSEbq";

//        获取服务器列表
        List<ServerInfo> list=serverService.getServerInfo(new ServerInfo());
        List<HardLog> hardLogList=new ArrayList<>();
        ChannelSftp sftp = SftpUtil.connect(encryptor.decrypt(host), port, encryptor.decrypt(username), encryptor.decrypt(password));
        // 获取filename，写入list
        Vector<ChannelSftp.LsEntry> files = sftp.ls(Path);
        List<String> fileNamesList=new ArrayList<>();
        for (ChannelSftp.LsEntry lsEntry : files) {
            if (Pattern.matches(".*txt.*", lsEntry.getFilename())) {
                fileNamesList.add(lsEntry.getFilename().split("\\.")[0]);
            }
        }

        for(int i=0;i<list.size();i++){
            if (fileNamesList.contains(list.get(i).getIpNumber())){
                HardLog hard = SftpUtil.SftpReadTxt(list.get(i).getIpNumber()+"\\.txt", Path, sftp);
                hard.setCreateDate(new Date());
                hard.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                hard.setCol(list.get(i).getType().toString());
                hard.setIp(list.get(i).getIp());
                hard.setDelFlag(0);
                hardLogList.add(hard);
            }
        }
        try {
            SftpUtil.disconnect(sftp);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("hardLogList.size()"+hardLogList.size());
        int count=serverService.updateLog();
        if (count>=1){
            System.out.println("update" +count+"條數據");
        }
        for (int i=0;i<hardLogList.size();i++){
            serverService.insertLog(hardLogList.get(i));
        }
        list.clear();
        hardLogList.clear();
        files.clear();
        fileNamesList.clear();
    }


 //   @Scheduled(cron = "0 5,15,25,35,45,55 * * * ?")
  //  @Logger
//    定时通报服务器异常任务,并且发短信
    public void sendWarningMessage() throws UnsupportedEncodingException {
        HardLog hardLog=new HardLog();
        hardLog.setDelFlag(1);
        List<HardLog> list=serverService.getLogs(hardLog);
        if (list.size()>=1){
            for (int i=0;i<list.size();i++){
                StringBuffer content = new StringBuffer();
                String phone="18688286030";
                String ip=list.get(i).getIp();
                String cpu=list.get(i).getCpu();
                String disc=list.get(i).getDisk();
                String memory=list.get(i).getMemory();
                Date createDate=list.get(i).getCreateDate();
                content.append("尊敬的i顺德开发运维组"+ip+"服务器出现异常，请及时登录排查");
                content.append("createDate:"+createDate);
                content.append("cpu:"+cpu);
                content.append("disc:"+disc);
                content.append("memery:"+memory);
                System.out.println("发送短信中"+content+phone);
                HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content.toString(), "gb2312")
                        +"&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
            }
        }else {
            System.out.println("系统运行正常");
        }
    }


    //@Scheduled(cron = "0 5 * * * ?")
 //   @Logger
//    获取数据库备份时间，并入库
    public void backUpLog() {
        serverService.getBackTime();
    }
}
