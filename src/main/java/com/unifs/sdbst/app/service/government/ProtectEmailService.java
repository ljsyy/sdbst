package com.unifs.sdbst.app.service.government;

import com.unifs.sdbst.app.bean.government.ProtectInfo;
import com.unifs.sdbst.app.utils.SendMailUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 消费投诉
 * @author DL04
 *
 */
public class ProtectEmailService {
	public String sendEmail(ProtectInfo pi) {
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", pi.getType());
		map.put("subject", pi.getSubject());
		map.put("name", pi.getName());
		map.put("sex", pi.getSex());
		map.put("addJob", pi.getAddJob());
		map.put("addContact", pi.getAddContact());
		map.put("phone", pi.getPhone());
		map.put("email", pi.getEmail());
		map.put("content", pi.getContent());
		String templatePath = "templates/mail.ftl";
		String result=SendMailUtil.sendFtlMail("quchenshiya@sina.com", pi.getSubject(), templatePath, map);*/
		
		String message="<div style='text-align:center;font-size:20px;font-weight:600;line-height:40px;border-bottom:2px solid #0FC;'> “顺德民生百事通”用户发来的消费维权 </div>";
		message+="<div style='padding:15px 10px;border-bottom:2px solid #0FC;'>";
		message+="<div>投诉类型： "+pi.getType()+"</div>";
		message+="<div>主题： "+pi.getSubject()+"</div>";
		message+="<div>姓名： "+pi.getName()+"</div>";
		message+="<div>性别/组别： "+pi.getSex()+"</div>";
		message+="<div>工作单位： "+pi.getAddJob()+"</div>";
		message+="<div>联系地址： "+pi.getAddContact()+"</div>";
		message+="<div>联系电话： "+pi.getPhone()+"</div>";
		message+="<div>电子邮箱： "+pi.getEmail()+"</div>";
		message+="<div>投诉内容： </div>";
		message+="<div style='padding-left:80px;'>"+pi.getContent()+"</div>";
		message+="</div>";
		System.out.println(message);
		String toMailAddr="quchenshiya@sina.com";
		if(StringUtils.isNotBlank(pi.getSubject()) && pi.getSubject().indexOf("@qq.com")>0) {
			toMailAddr=pi.getSubject();
		}
		SendMailUtil.sendCommonMail(toMailAddr, pi.getSubject(), message);
		return "邮件发送成功!";
	}
}
