package com.unifs.sdbst.app.utils;

import com.jcraft.jsch.*;
import com.unifs.sdbst.app.bean.server.HardLog;
import jdk.nashorn.internal.runtime.logging.Logger;

import java.io.*;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class SftpUtil {
    static Session sshSession = null;

    /**
     * 获取ChannelSftp
     */
    @Logger
    public static ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connected Exception ");

        }
        return sftp;
    }


    /**
     * 上传
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }

    private static boolean isExistDir(String path, ChannelSftp sftp)
    {
        try
        {
            sftp.cd(path);
            return true;
        }
        catch (SftpException e)
        {
            return false;
        }
    }
    public static void createDir(String path, ChannelSftp sftp) throws SftpException {
        if(!isExistDir(path,sftp)){
            sftp.mkdir(path);
        }
    }

    /**
     * 关闭资源
     */
    public static void disconnect(ChannelSftp sftp) throws JSchException {
        if (sftp != null) {
            if (sftp.isConnected()) {
                System.out.println(sshSession);
//               使用 sftp.getSession().disconnect();关闭session连接*****
                sftp.getSession().disconnect();
                System.out.println("getSession"+sftp.getSession()+"disconnect");
                sftp.disconnect();
                System.out.println("sftp disconnect ");


            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
                System.out.println("sshSession disconnect ");
            }
        }
    }

    /**
     * sftp is connected
     *
     * @return
     */
    public static boolean isConnected(ChannelSftp sftp) {
        return sftp != null && sftp.isConnected();
    }

//    读取txt文件
    public static HardLog SftpReadTxt(String fileName,String path, ChannelSftp sftp) throws SftpException, IOException {
        HardLog hard = new HardLog();
        sftp.cd(path);
        InputStream inputStream =sftp.get(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        int n=0;
        for (String line = br.readLine(); line != null ; line = br.readLine()) {
            if(Pattern.matches(".*Disk Usage.*",line)){
                hard.setDisk(line.split(":")[1]);
            }else if(Pattern.matches(".*Memory Usage.*",line)){
                hard.setMemory(line.split(":")[1]);
            }else if (Pattern.matches(".*CPU Load.*",line)){
                hard.setCpu(line.split(":")[1]);
            }else{
                if (!line.equals("")){
                    if (n==0){
//                    读到没有匹配的第一行，则为Tomcat
                        n++;
                        hard.setTomcat(line);
                    }else if (n==1){
//                    读到没有匹配的第二行，则为下载量
                        n++;
                        hard.setDown(line);
                    }else if (n==2 ){
//                    读到没有匹配的第三行，则为带宽
                        n++;
                        hard.setNetwork(line.split(" ")[0]+","+line.split(" ")[1]);
                    }else if (n==3 ){
//                    读到没有匹配的第四行，则为带宽
                        hard.setRedis(line);
                    }
                }


            }
//            br.close();
//            System.out.println("br close first");
        }
        br.close();
        return hard;
        }


}
