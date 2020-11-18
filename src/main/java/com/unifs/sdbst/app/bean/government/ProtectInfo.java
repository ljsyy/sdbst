package com.unifs.sdbst.app.bean.government;
/**
 * 用于 消费维权 信息
 * @author DL04
 *
 */
public class ProtectInfo {
	private String type;		//投诉类型
	private String subject;		//主题
	private String name;		//姓名
	private String sex;			//性别/组别
	private String addJob;		//工作单位
	private String addContact;	//联系地址
	private String phone;		//联系电话
	private String email;		//电子邮箱
	private String content;		//投诉内容
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddJob() {
		return addJob;
	}
	public void setAddJob(String addJob) {
		this.addJob = addJob;
	}
	public String getAddContact() {
		return addContact;
	}
	public void setAddContact(String addContact) {
		this.addContact = addContact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
