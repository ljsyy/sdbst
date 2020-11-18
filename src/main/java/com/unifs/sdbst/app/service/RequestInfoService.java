package com.unifs.sdbst.app.service;


import com.unifs.sdbst.app.base.RequestInfo;
import com.unifs.sdbst.app.utils.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestInfoService {
    private static Map<String, String> map = new HashMap();

    //初始化静态代码库
    static {
        String fileName = "request-url.properties";
        String baseUrl = PropertiesUtil.getKey(fileName, "baseUrl");
        //获取names集合
        Set<String> names = PropertiesUtil.getNames(fileName);
        for (String str : names) {
            //判断url是否包含http，不包含则拼接上基础路径
            if (PropertiesUtil.getKey(fileName, str).contains("http")) {
                map.put(str, PropertiesUtil.getKey(fileName, str));
            } else {
                map.put(str, baseUrl + PropertiesUtil.getKey(fileName, str));
            }
        }
    }


    public static String getUrl(RequestInfo ri) {
        if (ri == null)
            return null;
        String function = ri.getFunction();
        Integer pageIndex = ri.getPageIndex();

        if (function == null || !map.containsKey(function))
            return null;

        if (pageIndex == null)
            pageIndex = 0;
        String url = map.get(function);
        String titleName = null;
        String headName = null;
        String returnType = null;

        String functionName = function.substring(0, function.indexOf("/"));    // 获取功能名,用于减少比较次数

        // 网上信访
        if (functionName.equals("petition")) {
            if (function.equals("petition/rule")) {// 网上信访/信访法规
                headName = "网上信访/信访法规";
                titleName = "信访法规";
            } else if (function.equals("petition/knowledge")) {// 网上信访/信访常识
                headName = "网上信访/信访常识";
                titleName = "信访常识";
            } else if (function.equals("petition/feedback")) {// 网上信访/信访反馈
                headName = "网上信访/信访反馈";
                titleName = "信访反馈";
            }
        }


        // 场景式服务
        else if (functionName.equals("scene")) {
            pageIndex = -1;
            if (function.equals("scene/pregnant")) {//场景式服务/怀孕生育
                headName = "场景式服务/怀孕生育";
                titleName = "怀孕生育";
            } else if (function.equals("scene/huji")) {//场景式服务/户籍办理
                headName = "场景式服务/户籍办理";
                titleName = "户籍办理";
            } else if (function.equals("scene/churu")) {//场景式服务/出入境
                headName = "场景式服务/出入境";
                titleName = "出入境";
            } else if (function.equals("scene/zizhi")) {//场景式服务/资质认定
                headName = "场景式服务/资质认定";
                titleName = "资质认定";
            } else if (function.equals("scene/gongshang")) {//场景式服务/工商注册
                headName = "场景式服务/工商注册";
                titleName = "工商注册";
            } else if (function.equals("scene/driver")) {//场景式服务/驾驶员
                headName = "场景式服务/驾驶员";
                titleName = "驾驶员";
            } else if (function.equals("scene/business")) {//场景式服务/买卖租住房
                headName = "场景式服务/买卖租住房";
                titleName = "买卖租住房";
            } else if (function.equals("scene/job")) {//场景式服务/求职就业
                headName = "场景式服务/求职就业";
                titleName = "求职就业";
            } else if (function.equals("scene/project")) {//场景式服务/工程报建
                headName = "场景式服务/工程报建";
                titleName = "工程报建";
            } else if (function.equals("scene/traffic")) {//场景式服务/交通出行
                headName = "场景式服务/交通出行";
                titleName = "交通出行";
            }
        }


        // 部门通讯录
        else if (functionName.equals("contact")) {
			/*if(function.equals("contact/dangzheng")) {// 部门通讯录/党政机关
				headName="部门通讯录/党政机关";
			}else if(function.equals("contact/shiye")) {// 部门通讯录/事业单位
				headName="部门通讯录/事业单位";
			}else if(function.equals("contact/zhiguan")) {// 部门通讯录/直管单位
				headName="部门通讯录/直管单位";
			}else if(function.equals("contact/zhengjie")) {// 部门通讯录/各镇街道
				headName="部门通讯录/各镇街道";
			}
			titleName="部门通讯录";*/

        }


        // 应急管理
        else if (functionName.equals("emergency")) {
            if (function.equals("emergency/know")) {// 应急管理/ 应急知识
                headName = "应急管理/应急知识";
                titleName = "应急知识";
            } else if (function.equals("emergency/message")) {// 应急管理/ 应急信息
                headName = "应急管理/应急信息";
                titleName = "应急信息";
            } else if (function.equals("emergency/plan")) {// 应急管理/ 应急预案
                headName = "应急管理/应急预案";
                titleName = "应急预案";
            } else if (function.equals("emergency/refuge")) {// 应急管理/ 避难场所
                headName = "应急管理/避难场所";
                titleName = "避难场所";
                returnType = "page";
            } else if (function.equals("emergency/mobile/part")) {// 应急管理/ 应急电话/组织机构和职责
                headName = "应急管理/应急电话/组织机构和职责";
                titleName = "组织机构和职责";
                returnType = "jsonp";
                ri.setId("318");
                ri.setFid("10623");
            } else if (function.equals("emergency/mobile/phone")) {// 应急管理/ 应急电话/紧急服务电话
                headName = "应急管理/应急电话/紧急服务电话";
                titleName = "紧急服务电话";
                returnType = "jsonp";
                ri.setId("319");
                ri.setFid("10623");
            }
        }


        // 机构职能
        else if (functionName.equals("institution")) {  // 机构职能
            if (function.equals("institution/")) {// 机构职能/领导班子
                headName = "机构职能";
                titleName = "机构职能";
            }
        }


        // 走进顺德
        else if (functionName.equals("inshunde")) {
            if (function.equals("inshunde/things")) {  // 走进顺德 / 顺德大事
                headName = "走进顺德 /顺德大事";
                titleName = "顺德大事";
            } else if (function.equals("inshunde/travel/ten")) {  // 走进顺德 / 顺德旅游 / 顺德十景
                headName = "走进顺德 /顺德旅游/顺德十景";
                titleName = "顺德十景";
                returnType = "href";
            } else if (function.equals("inshunde/travel/ren")) {  // 走进顺德 / 顺德旅游 / 风土人情
                headName = "走进顺德 /顺德旅游/风土人情";
                titleName = "风土人情";
            } else if (function.equals("inshunde/travel/food")) {  // 走进顺德 / 顺德旅游 / 饮食文化
                headName = "走进顺德 /顺德旅游/饮食文化";
                titleName = "饮食文化";
                returnType = "href";
            } else if (function.equals("inshunde/travel/green")) {  // 走进顺德 / 顺德旅游 / 绿色出行
                headName = "走进顺德 /顺德旅游/绿色出行";
                titleName = "绿色出行";
            } else if (function.equals("inshunde/travel/info")) {  // 走进顺德 / 顺德旅游 / 旅游信息
                headName = "走进顺德 /顺德旅游/旅游信息";
                titleName = "旅游信息";
            } else if (function.equals("inshunde/appliance/information")) {  // 走进顺德 / 顺德家电/产业介绍
                headName = "走进顺德 /顺德家电/产业介绍";
                titleName = "产业介绍";
            } else if (function.equals("inshunde/appliance/info")) {  // 走进顺德 / 顺德家电 /产业信息
                headName = "走进顺德 /顺德家电 /产业信息";
                titleName = "产业信息";
            } else if (function.equals("inshunde/security")) {  // 走进顺德 / 社会保障
                headName = "走进顺德 /社会保障";
                titleName = "社会保障";
            } else if (function.equals("inshunde/satuation")) {  // 走进顺德 / 基本概况
                headName = "走进顺德 /基本概况";
                titleName = "基本概况";
            } else if (function.equals("inshunde/manage")) {  // 走进顺德 / 行政区划
                headName = "走进顺德 /行政区划";
                titleName = "行政区划";
            } else if (function.equals("inshunde/history")) {  // 走进顺德 / 历史沿革
                headName = "走进顺德 /历史沿革";
                titleName = "历史沿革";
            } else if (function.equals("inshunde/facility")) {  // 走进顺德 / 基础设施
                headName = "走进顺德 /基础设施";
                titleName = "基础设施";
            } else if (function.equals("inshunde/doctor")) {  // 走进顺德 / 医疗卫生
                headName = "走进顺德 /医疗卫生";
                titleName = "医疗卫生";
            } else if (function.equals("inshunde/cityplan")) {  // 走进顺德 / 城市规划
                headName = "走进顺德 /城市规划";
                titleName = "城市规划";
            } else if (function.equals("inshunde/celebrity")) {  // 走进顺德 / 历史名人
                headName = "走进顺德 /历史名人";
                titleName = "历史名人";
            } else if (function.equals("inshunde/education")) {  // 走进顺德 / 教育强区
                headName = "走进顺德 /教育强区";
                titleName = "教育强区";
            } else if (function.equals("inshunde/appliance")) {  // 走进顺德 / 顺德家电
                headName = "走进顺德 /顺德家电";
                titleName = "顺德家电";
            } else if (function.equals("inshunde/citygood")) {  // 走进顺德 / 文化名城
                headName = "走进顺德 /文化名城";
                titleName = "文化名城";
            }
        }


        // 采购交易
        else if (functionName.equals("trade")) {
            if (function.equals("trade/institutionNotice")) {  // 采购交易/采购信息/代理机构采购/招标公告
                titleName = "招标公告";
                headName = "采购交易>采购信息>代理机构采购>招标公告";
            } else if (function.equals("trade/institutionBid")) {// 采购交易/采购信息/代理机构采购/预中标公告
                titleName = "预中标公告";
                headName = "采购交易/采购信息/代理机构采购/预中标公告";
            } else if (function.equals("trade/institutionBided")) {// 采购交易/采购信息/代理机构采购/中标公告
                titleName = "中标公告";
                headName = "采购交易/采购信息/代理机构采购/中标公告";
            } else if (function.equals("trade/projectNotice")) {// 采购交易/采购信息/建设工程交易/招标公告
                headName = "采购交易/采购信息/建设工程交易/招标公告";
                titleName = "招标公告";
                returnType = "page";
            } else if (function.equals("trade/projectBid")) {// 采购交易/采购信息/建设工程交易/预中标公告
                headName = "采购交易/采购信息/建设工程交易/预中标公告";
                titleName = "预中标公告";
                returnType = "page";
            } else if (function.equals("trade/projectBided")) {// 采购交易/采购信息/建设工程交易/中标公告
                headName = "采购交易/采购信息/建设工程交易/中标公告";
                titleName = "中标公告";
                returnType = "page";
            } else if (function.equals("trade/streetNotice")) {// 采购交易/采购信息/各镇街道采购/招标公告
                headName = "采购交易/采购信息/各镇街道采购/招标公告";
                titleName = "招标公告";
            } else if (function.equals("trade/streetBid")) {// 采购交易/采购信息/各镇街道采购/预中标公告
                headName = "采购交易/采购信息/各镇街道采购/预中标公告";
                titleName = "预中标公告";
            } else if (function.equals("trade/streetBided")) {// 采购交易/采购信息/各镇街道采购/中标公告
                headName = "采购交易/采购信息/各镇街道采购/中标公告";
                titleName = "中标公告";
            } else if (function.equals("trade/tenderProcure")) {// 采购交易/采购信息/政府招标采购/采购公告
                headName = "采购交易/采购信息/政府招标采购/采购公告";
                titleName = "采购公告";
            } else if (function.equals("trade/tenderOver")) {// 采购交易/采购信息/政府招标采购/成交公告
                headName = "采购交易/采购信息/政府招标采购/成交公告";
                titleName = "中标(成交)公告";
            } else if (function.equals("trade/tenderUpdate")) {// 采购交易/采购信息/政府招标采购/更正通知
                headName = "采购交易/采购信息/政府招标采购/更正通知";
                titleName = "澄清/更正通知";
            } else if (function.equals("trade/tenderInvalid")) {// 采购交易/采购信息/政府招标采购/废标公告
                headName = "采购交易/采购信息/政府招标采购/废标公告";
                titleName = "废标公告";
            } else if (function.equals("trade/tenderNotice")) {// 采购交易/采购信息/政府招标采购/公告栏
                headName = "采购交易/采购信息/政府招标采购/公告栏";
                titleName = "公告栏";
            } else if (function.equals("trade/fagui")) {// 采购交易/采购信息/采购法律法规
                headName = "采购交易/采购信息/采购法律法规";
                titleName = "采购法律法规";
            } else if (function.equals("trade/groupMessage")) {// 采购交易/农村集体交易/..信息公告
                headName = "采购交易/农村集体交易/..信息公告";
                titleName = "农村集体资产交易信息公告";
                returnType = "pagey";
            } else if (function.equals("trade/groupResult")) {// 采购交易/农村集体交易/..结果公告
                headName = "采购交易/农村集体交易/..结果公告";
                titleName = "农村集体资产交易结果公告";
                returnType = "pagey";
            }
        }

        // 三农服务
        else if (functionName.equals("farmers")) {
            if (function.equals("farmers/news")) {  // 三农服务 / 三农要闻
                headName = "三农服务 / 三农要闻";
                titleName = "三农要闻";
            } else if (function.equals("farmers/laws")) {  // 三农服务 / 三农政策法规
                headName = "三农服务 / 三农政策法规";
                titleName = "三农政策法规";
            }
        }

        // 三农服务（无头部）
        else if (functionName.equals("noheadfarmers")) {
            if (function.equals("noheadfarmers/news")) {  // 三农服务 / 三农要闻
                headName = "三农服务 / 三农要闻";
                titleName = "三农要闻";
            } else if (function.equals("noheadfarmers/laws")) {  // 三农服务 / 三农政策法规
                headName = "三农服务 / 三农政策法规";
                titleName = "三农政策法规";
            }
        }


        //户籍管理
        else if (functionName.equals("others")) {
            if (function.equals("others/householdRegistration")) {
                returnType = "href";
                pageIndex = -1;
            }
        }

        //出入境申请
        else if (functionName.equals("others")) {
            if (function.equals("others/entryAndExit")) {
                returnType = "href";
                pageIndex = -1;
            }
        }

        //网上车管所
        else if (functionName.equals("others")) {
            if (function.equals("others/onlineParking")) {
                returnType = "href";
                pageIndex = -1;
            }
        }

        //超市促销
        else if (functionName.equals("others")) {
            if (function.equals("others/shopping/AEON")) { //永旺
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/shopping/Everyday")) { //天天商场
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/shopping/shunkelong")) { //顺客隆
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/shopping/seven")) { //七十一便利店
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/shopping/jinye")) { //金业
                returnType = "href";
                pageIndex = -1;
            }
        }

        //顺德农讯
        else if (functionName.equals("others")) {
            if (function.equals("others/agriculture/registration")) { //办证指南
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/agriculture/markets")) { //市场信息
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/agriculture/agribusiness")) { //农企园地
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/agriculture/technique")) { //最新技术
                returnType = "href";
                pageIndex = -1;
            } else if (function.equals("others/agriculture/experts")) { //专家咨询
                returnType = "href";
                pageIndex = -1;
            }
        } else if (functionName.equals("")) {

        }


        ri.setTitleName(titleName);
        ri.setHeadName(headName);
        ri.setPageIndex(pageIndex);
        ri.setReturnType(returnType);
        ri.setUrl(url);
        if (pageIndex.equals(-1))
            return url;
        return url + pageIndex;
    }

    public static String getUrl(String type) {
        return map.get(type);
    }


    ///
    public static String getUrlByFunction(String function) {
        if (function == null || !map.containsKey(function))
            return null;
        return map.get(function);
    }
}
