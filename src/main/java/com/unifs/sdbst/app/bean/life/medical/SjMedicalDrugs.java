/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.life.medical;

import com.unifs.sdbst.app.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 医疗药品Entity
 * @author liuzibo
 * @version 2018-02-06
 */
public class SjMedicalDrugs  {
	
	private static final long serialVersionUID = 1L;
	private String chineseName;		// 中文名称
	private String fenClass;		// 分类
	private String formulation;		// 剂型
	private String remarks;         //备注
	private String numbering;		// 编号
	private String bigClass;		// 大类
	private String inClass;			// 中类
	private String smallClass;		// 小类
	private String fineClass;		// 细类
	private String englishName;		// 英文名称
	private  String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SjMedicalDrugs() {
		super();
	}


	@ExcelField(title = "中文名称", align = 2, sort = 1)
	@Length(min=0, max=64, message="中文名称长度必须介于 0 和 64 之间")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@ExcelField(title = "分类", align = 2, sort = 2)
	@Length(min=0, max=64, message="分类长度必须介于 0 和 64 之间")
	public String getFenClass() {
		return fenClass;
	}

	public void setFenClass(String fenClass) {
		this.fenClass = fenClass;
	}

	@ExcelField(title = "剂型", align = 2, sort = 3)
	@Length(min=0, max=64, message="剂型长度必须介于 0 和 64 之间")
	public String getFormulation() {
		return formulation;
	}

	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}

	@ExcelField(title = "备注", align = 2, sort = 4)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ExcelField(title = "编号", align = 2, sort = 5)
	@Length(min=0, max=64, message="编号长度必须介于 0 和 64 之间")
	public String getNumbering() {
		return numbering;
	}

	public void setNumbering(String numbering) {
		this.numbering = numbering;
	}

	@ExcelField(title = "大类", align = 2, sort = 6)
	@Length(min=0, max=64, message="大类长度必须介于 0 和 64 之间")
	public String getBigClass() {
		return bigClass;
	}

	public void setBigClass(String bigClass) {
		this.bigClass = bigClass;
	}

	@ExcelField(title = "中类", align = 2, sort = 7)
	@Length(min=0, max=64, message="中类长度必须介于 0 和 64 之间")
	public String getInClass() {
		return inClass;
	}

	public void setInClass(String inClass) {
		this.inClass = inClass;
	}

	@ExcelField(title = "小类", align = 2, sort = 8)
	@Length(min=0, max=64, message="小类长度必须介于 0 和 64 之间")
	public String getSmallClass() {
		return smallClass;
	}

	public void setSmallClass(String smallClass) {
		this.smallClass = smallClass;
	}

	@ExcelField(title = "细类", align = 2, sort = 9)
	@Length(min=0, max=64, message="细类长度必须介于 0 和 64 之间")
	public String getFineClass() {
		return fineClass;
	}

	public void setFineClass(String fineClass) {
		this.fineClass = fineClass;
	}

	@ExcelField(title = "英文名称", align = 2, sort = 10)
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
}