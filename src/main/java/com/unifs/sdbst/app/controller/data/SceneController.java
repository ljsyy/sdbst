package com.unifs.sdbst.app.controller.data;


import com.unifs.sdbst.app.bean.data.ChildNavEntity;
import com.unifs.sdbst.app.bean.data.ParseNavHandler;
import com.unifs.sdbst.app.bean.data.ShundeNavEntity;
import com.unifs.sdbst.app.utils.CommUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping({"/scene"})
public class SceneController {
    private Logger log = LoggerFactory.getLogger(SceneController.class);

    @RequestMapping(value = {"/getNav"})
    @ResponseBody
    public AjaxJson getNav(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        String url = "http://www1.shunde.gov.cn/scene/getNav.php";

        String optFlag = CommUtils.safeString(request.getParameter("optFlag"));
        if (optFlag == "") {
            j.setMsg("参数optFlag不能为空！");
            j.setSuccess(false);
            return j;
        }
        url = url + "?optFlag=" + optFlag;
        String idStr;
        if (optFlag.equals("homeNav")) {
            String bid = CommUtils.safeString(request.getParameter("bid"));
            if (bid == "") {
                j.setMsg("参数bid不能为空！");
                j.setSuccess(false);
                return j;
            }
            url = url + "&bid=" + bid;
            idStr = "bId:" + bid;
        } else if ((optFlag.equals("childNav")) || (optFlag.equals("contentNav"))) {
            idStr = CommUtils.safeString(request.getParameter("idStr"));
            if (idStr == "") {
                j.setMsg("参数idStr不能为空！");
                j.setSuccess(false);
                return j;
            }
            url = url + "&idStr=" + idStr;
        } else {
            j.setMsg("参数optFlag有误！");
            j.setSuccess(false);
            return j;
        }
        String xml = "";
        xml = HttpUtil.sendPost(url, "");

        ShundeNavEntity shundeNav = new ShundeNavEntity();
        ParseNavHandler handler = new ParseNavHandler(shundeNav);

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xml)));
            shundeNav = handler.getShundeNav();
        } catch (Exception e) {
            writeFile(xml, "getNav_out.xml");
            j.setMsg("文档解析失败！");
            j.setSuccess(false);
            return j;
        }
        List<ChildNavEntity> childList = shundeNav.getChildNavList();
        for (ChildNavEntity entity : childList) {
            String myurl = entity.getUrl();
            if (optFlag.equals("contentNav")) {
                String regEx_split = "(\\d+)-(\\d+)";
                Pattern p_split = Pattern.compile(regEx_split);
                Matcher m_split = p_split.matcher(myurl);
                if (m_split.find()) {
                    String id = m_split.group(1);
                    String fid = m_split.group(2);

                    myurl = id + "_" + fid;
                } else {
                    int index = myurl.indexOf("http");
                    if (index >= 0) {
                        entity.setXl("l");
                    } else {
                        myurl = "";
                    }
                }
                entity.setHasChildren(false);
            } else {
                boolean hasChildren = entity.isHasChildren();
                if (hasChildren) {
                    myurl = "/scene/getNav?optFlag=childNav";
                } else {
                    myurl = "/scene/getNav?optFlag=contentNav";
                }
                myurl = myurl + "&idStr=" + idStr + "-" + entity.getId();
                entity.setHasChildren(true);
            }
            entity.setUrl(myurl);
        }
        j.setObj(childList);
        j.setSuccess(true);
        j.setMsg("操作成功！");

        return j;
    }

    private static void writeFile(String content, String filename) {
        String logpath = ResourceUtil.getConfigByName("logpath");
        try {
            FileWriter file = new FileWriter(logpath + "\\" + filename, false);
            file.write(content);
            file.close();
        } catch (IOException localIOException) {
        }
    }

}
