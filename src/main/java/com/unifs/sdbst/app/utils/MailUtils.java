package com.unifs.sdbst.app.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class MailUtils{

    private final  Logger logger = LoggerFactory.getLogger(MailUtils.class);

//    @Autowired
//    private JavaMailSender javaMailSender;

    /****
     *
     * @param subject 主题
     * @param text 内容 ===此邮件不需要回复，请勿回复
     * @param attachmentMap 附件
     * @param receiver 邮件接受者
     * @param sender 发送者
     * @throws MessagingException
     */
    public String sendHtmlMail(JavaMailSender javaMailSender,String subject, String text, Map<String, String> attachmentMap,String receiver,String sender) throws MessagingException {
//        System.out.println("javaMailSender:"+javaMailSender);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //是否发送的邮件是富文本（附件，图片，html等）
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(sender);
        messageHelper.setTo(receiver);
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);//重点，默认为false，显示原始html代码，无效果
        int flag = 0 ;
        if (attachmentMap != null) {
//            attachmentMap.entrySet().stream().forEach(entrySet -> {
            for (Map.Entry<String, String> entrySet : attachmentMap.entrySet()) {
//                System.out.println("key:"+entrySet.getKey()+":value:"+entrySet.getValue());
                // 获取文件后缀
                String prefix=entrySet.getValue().substring(entrySet.getValue().lastIndexOf("."));
//                System.out.println("获取文件后缀"+prefix);
                // 构建文件信息
                String radomName = UUID.randomUUID().toString().replace("-","");
                String localPath = "/home/file/temp/";
                String localFile = localPath+File.separator +radomName+prefix;//web 的服务器的路径
//                System.out.println("localFile:"+localFile);
                File file = new File(localFile);
                URL url = null;
                try {
                    url = new URL(entrySet.getValue());
                    FileUtils.copyURLToFile(url,file);//拷贝url到文件
                } catch (MalformedURLException e) {
                    flag = 1;
                    System.out.println("出错了："+e.getMessage());
//                    e.printStackTrace();
                } catch (IOException e) {
                    flag = 2;
                    System.out.println("出错："+e.getMessage());
//                    e.printStackTrace();
                }
                if (file.exists()) {
                    try {
                        messageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
                    } catch (MessagingException e) {
                        flag = 3;
                        e.printStackTrace();
                    }
                }
            }
        }
        if (flag == 0){
            javaMailSender.send(mimeMessage);
            System.out.println("发送邮件成功。");
            return "200";
        }else if (flag == 1){
            System.out.println("文件拷贝失败");
            return "文件拷贝失败";
        }else if (flag == 2){
            System.out.println("网络连接超时");
            return "网络连接超时";
        }else {
            System.out.println("发送邮件失败。");
            return "发送邮件失败";
        }

    }
}
