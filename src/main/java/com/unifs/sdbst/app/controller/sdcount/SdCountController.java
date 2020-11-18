/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.controller.sdcount;


import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.sdcount.SdCount;
import com.unifs.sdbst.app.bean.sdcount.SdIndex;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.sdcount.SdCountService;
import com.unifs.sdbst.app.service.sdcount.SdIndexService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * aController
 *
 * @author chengpeng
 * @version 2017-11-23
 */
@RestController
@RequestMapping(value = "/sdCount")
public class SdCountController {

    @Autowired
    private SdCountService sdCountService;
    @Autowired
    private SdIndexService sdIndexService;
    private Resp resp;

    @ModelAttribute
    public SdCount get(@RequestParam(required = false) String id) {
        SdCount entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sdCountService.get(id);
        }
        if (entity == null) {
            entity = new SdCount();
        }
        return entity;
    }

    //@RequiresPermissions("sdcount:sdCount:view")
/*	@RequestMapping(value = {"list", ""})
	public String list(SdCount sdCount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SdCount> page = sdCountService.findPage(new Page<SdCount>(request, response), sdCount); 
		model.addAttribute("page", page);
		return "modules/sdcount/sdCountList";
	}*/

    //@RequiresPermissions("sdcount:sdCount:view")
/*	@RequestMapping(value = "form")
	public String form(SdCount sdCount, Model model) {
		model.addAttribute("sdCount", sdCount);
		return "modules/sdcount/sdCountForm";
	}*/

    //@RequiresPermissions("sdcount:sdCount:edit")
/*	@RequestMapping(value = "save")
	public String save(SdCount sdCount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sdCount)){
			return form(sdCount, model);
		}
		sdCountService.save(sdCount);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/sdcount/sdCount/?repage";
	}*/

    //@RequiresPermissions("sdcount:sdCount:edit")
/*	@RequestMapping(value = "delete")
	public String delete(SdCount sdCount, RedirectAttributes redirectAttributes) {
		sdCountService.delete(sdCount);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/sdcount/sdCount/?repage";
	}*/

    /**
     * 指标列表
     *
     * @param sdCount
     * @param beginCreateTime
     * @param cDate
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/zbMonth")
    public Resp zbMonth(SdCount sdCount, String cDate, Model model) {
        if (null != sdCount.getBeginCreateTime()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cDate = sdf.format(sdCount.getBeginCreateTime()).substring(0, 7);
        }
        List<SdCount> list = sdCountService.findCountMonth(cDate);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(list);
        return resp;
    }

    /**
     * 环比
     *
     * @param sdCount
     * @param name
     * @param redirectAttributes
     * @param model
     * @return
     */
    @SuppressWarnings({"unused", "null"})
    @RequestMapping(value = "/sameCompara")
    public Resp sameCompara(SdCount sdCount, String cDate, String name, RedirectAttributes redirectAttributes, Model model,
                            HttpServletResponse response, HttpServletRequest request) {
        List<SdCount> list = new ArrayList<SdCount>();
        int num = 0;
        String aa = null;
        if (!StringUtils.isEmpty(cDate)) {
            int year = Integer.parseInt(cDate.substring(0, 4));
            int a = Integer.parseInt(cDate.substring(5, 7));
            for (int i = a; i < 13; i--) {
                if (num == 5) {
                    break;
                }
                if (i < 10) {
                    aa = "0" + i;
                    if (i == 0)
                        break;
                } else {
                    aa = i + "";
                }
                SdCount sd = sdCountService.findCountAll(name, year + "-" + aa);
                if (null == sd) {
                    sd = new SdCount();
                    sd.setcDate(year + "-" + aa);
                    sd.setTotal(0.0);
                    sd.setTbRate(0.0);
                    sd.setHbRate(0.0);
                }
                list.add(sd);
                num++;
            }
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            int year = Integer.parseInt(sdf.format(date).substring(0, 4));
            int a = Integer.parseInt(sdf.format(date).substring(5, 7));
            for (int i = a; i < 13; i--) {
                if (num == 5) {
                    break;
                }
                if (i < 10) {
                    aa = "0" + i;
                    if (i == 0)
                        break;
                } else {
                    aa = i + "";
                }
                SdCount sd = sdCountService.findCountAll(name, year + "-" + aa);
                if (null != sd) {
                    list.add(sd);
                }
                num++;
            }
        }
        resp=new Resp(RespCode.SUCCESS);
        resp.setData(list);
        return resp;
    }

    /**
     * 同比
     *
     * @param sdCount
     * @param name
     * @param cDate
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/tb")
    public Resp tb(String name, String cDate, Model model) {
        List<SdCount> list = new ArrayList<SdCount>();
        int num = 0;
        if (!StringUtils.isEmpty(cDate)) {
            int year = Integer.parseInt(cDate.substring(0, 4));
            String a = cDate.substring(5, 7);
            for (int i = year; i < 2200; i--) {
                if (num == 5) {
                    break;
                }
                SdCount sd = sdCountService.tb(name, i + "-" + a);
                if (null == sd) {
                    sd = new SdCount();
                    sd.setcDate(i + "-" + a);
                    sd.setTotal(0.0);
                    sd.setTbRate(0.0);
                    sd.setHbRate(0.0);
                }
                list.add(sd);
                num++;
            }
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            int year = Integer.parseInt(sdf.format(date).substring(0, 4));
            String a = sdf.format(date).substring(5, 7);
            for (int i = year; i < 2200; i--) {
                if (num == 5) {
                    break;
                }
                SdCount sd = sdCountService.tb(name, i + "-" + a);
                if (null == sd) {
                    sd = new SdCount();
                    sd.setcDate(i + "-" + a);
                    sd.setTotal(0.0);
                    sd.setTbRate(0.0);
                    sd.setHbRate(0.0);
                }
                list.add(sd);
                num++;
            }
        }
        resp=new Resp(RespCode.SUCCESS);
        resp.setData(list);
        return resp;
    }

    @RequestMapping(value = "year")
    public Resp year(String cDate, Model model) {
        List<SdIndex> sd = sdIndexService.findName();
        List<SdCount> list = new ArrayList<SdCount>();

        for (SdIndex sdIndex : sd) {
            for (int i = 2; i <= 12; i++) {
                String a = null;
                if (i < 10) {
                    a = cDate + "-" + "0" + i;
                } else {
                    a = cDate + "-" + i + "";
                }
                SdCount sc = sdCountService.tb(sdIndex.getName(), a);
                if (null == sc) {
                    sc = new SdCount();
                    sc.setcDate(a);
                    sc.setName(sdIndex.getName());
                    sc.setTotal(0.0);
                    sc.setNuit("0");
                    sc.setGrowthRate(0.0);
                    sc.setTbRate(0.0);
                    sc.setHbRate(0.0);
                }
                list.add(sc);
            }
        }
        resp=new Resp(RespCode.SUCCESS);
        resp.setData(list);
        return resp;
    }
    /**
     *
     * @param 指标数据导入
     * @param redirectAttributes
     * @return
     */
/*	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/checkwork/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SdCount> list = ei.getDataList(SdCount.class);
			for (SdCount sdCount : list) {
				try {
					BeanValidators.validateWithException(validator, sdCount);// 服务端验证
					sdCountService.save(sdCount);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/> 指标数据:" + sdCount.getName() + ",导入失败<br/>");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/> 指标数据:" + sdCount.getName() + ",导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sdcount/sdCount/?repage";
	}*/

}