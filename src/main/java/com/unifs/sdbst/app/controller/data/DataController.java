package com.unifs.sdbst.app.controller.data;

import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.base.ServiceResult;
import com.unifs.sdbst.app.bean.data.FoldEntity;
import com.unifs.sdbst.app.bean.data.NewsEntity;
import com.unifs.sdbst.app.bean.data.SimpleNewsEntity;
import com.unifs.sdbst.app.bean.data.VoNewsEntity;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.data.DataService;
import com.unifs.sdbst.app.utils.CommUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rxframework.base.model.OutPut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V1.0
 * @title: DataController
 * @projectName sdbst
 * @description: RXWSSTWeb（sundeAPI）
 * @author： 张恭雨
 * @date 2019/10/9 8:55
 */
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;


    @ControlLog(context = "获取newList数据")
    @RequestMapping(value = {"/newList"})
    public AjaxJson newList(String fid, Boolean needFoldId, String whereSql, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (needFoldId == null) {
            needFoldId = Boolean.valueOf(true);
        }
        if (whereSql == null) {
            whereSql = "";
        }
        int start = CommUtils.safeInt(request.getParameter("start"));
        int limit = CommUtils.safeInt(request.getParameter("limit"), 10);

        OutPut total = new OutPut();
        ServiceResult result = dataService.getListNew(start, limit, total, needFoldId, fid, whereSql);
        if (result.getErrorCode().intValue() != -1) {
            List<NewsEntity> list = (List) result.getData();

            ArrayList<HashMap<String, Object>> list2 = (ArrayList) result.getData();

            List<VoNewsEntity> simpleVoList = new ArrayList();
            if (result.getStatus().intValue() == 1) {
                if (list2 == null) {
                    for (HashMap<String, Object> entity : list2) {
                        simpleVoList.add(getSimpleNewsVo(entity));
                    }
                }
            } else {
                for (NewsEntity entity : list) {
                    simpleVoList.add(getSimpleNewsVo(entity));
                }
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("count", total.get());

            j.setObj(simpleVoList);
            j.setAttributes(hashMap);
            j.setSuccess(true);
            return j;
        }
        j.setSuccess(false);
        j.setMsg(result.getErrorMsg());
        return j;
    }

    @ControlLog(context = "获取voteList数据")
    @RequestMapping(value = {"/voteList"})
    public AjaxJson voteList(HttpServletRequest request) throws ParseException {
        AjaxJson j = new AjaxJson();
        int fid = CommUtils.safeInt(request.getParameter("fid"));
        if (fid < 0) {

            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        String view = dataService.GetView(Integer.valueOf(fid));
        if (view.length() <= 0) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        int start = CommUtils.safeInt(request.getParameter("start"));
        int limit = CommUtils.safeInt(request.getParameter("limit"), 10);
        OutPut total = new OutPut();
        List<NewsEntity> list = dataService.getList2(start, limit, total, "v_" + view, String.valueOf(fid), "");
        List<SimpleNewsEntity> simpleList = new ArrayList();
        for (NewsEntity entity : list) {
            String id = entity.getId();
            String jsrq = entity.getJsrq();

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            int number = 0;
            Date jsrqDate = dateFormat.parse(jsrq);
            number = jsrqDate.compareTo(now);
            if (number == -1) {
                entity.setLx("m");
                simpleList.add(getSimpleNews(entity));
            } else {
                entity.setLx("t");
                simpleList.add(getSimpleNews(entity));
            }
        }
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("count", total.get());

        j.setObj(simpleList);
        j.setAttributes(hashMap);
        j.setSuccess(true);
        return j;
    }

    @ControlLog(context = "获取listMix数据")
    @RequestMapping(value = {"/listMix"})
    public AjaxJson listMix(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String fids = request.getParameter("fids");
        String view = request.getParameter("view");
        if ((StringUtils.isNullOrEmpty(fids)) ||
                (StringUtils.isNullOrEmpty(view))) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        int start = CommUtils.safeInt(request.getParameter("start"));
        int limit = CommUtils.safeInt(request.getParameter("limit"));
        OutPut total = new OutPut();
        List<NewsEntity> list = dataService.getList(start, limit, total, "v_" + view, fids, "");
        List<SimpleNewsEntity> simpleList = new ArrayList();
        for (NewsEntity entity : list) {
            simpleList.add(getSimpleNews(entity));
        }
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("count", total.get());

        j.setObj(simpleList);
        j.setAttributes(hashMap);
        j.setSuccess(true);
        return j;
    }

    @ControlLog(context = "获取详细信息")
    @RequestMapping(value = {"/newsDetail"}, produces = {"application/json;charset=UTF-8"})
    public Object newsDetail(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        String long_id = request.getParameter("id");
        if (StringUtils.isNullOrEmpty(long_id)) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        int index = long_id.indexOf('_');
        if (index < 0) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        String fold_id = long_id.substring(index + 1);
        String id = long_id.substring(0, index);
        int fid = CommUtils.safeInt(fold_id);

        String view = dataService.GetView(Integer.valueOf(fid));
        if (view.length() <= 0) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        NewsEntity newsEntity = dataService.Detail("v_" + view, Integer.valueOf(Integer.parseInt(id)));
        if (newsEntity == null) {
            j.setSuccess(false);
            j.setMsg("找不到记录");
            return j;
        }
        SimpleNewsEntity simpleNewEntity = getSimpleNewsDetail(newsEntity, true);

        j.setObj(simpleNewEntity);
        j.setSuccess(true);
        return CommUtils.getJsonp(request, j);
    }


    @RequestMapping(value = {"/menuList"})
    @ResponseBody
    public AjaxJson menuList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        int fid = CommUtils.safeInt(request.getParameter("fid"));
        String orderByString = request.getParameter("orderByString");
        if (fid < 0) {
            j.setSuccess(false);
            j.setMsg("找不到栏目");
            return j;
        }
        OutPut total = new OutPut();
        List<FoldEntity> list = dataService.getMenuList(total, Integer.valueOf(fid), orderByString);

        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("count", total.get());

        j.setObj(list);
        j.setAttributes(hashMap);
        j.setSuccess(true);
        return j;
    }

    private String getContentDetail(String sh, String fn) {
        String html = "请求失败";
        //将域名转换成ip地址，解决服务器无法解析域名问题
        sh = sh.replace("www.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("zwgk.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("wmsd.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("www1.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("zx.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("rd.shunde.gov.cn", "221.4.169.241");

        String url = sh + fn;
        System.out.println(url);
        html = HttpUtil.sendGetData(url, "", "gbk");

        if (StringUtils.hasText(html)) {
            html = replaceHtml(html, sh);
        } else {
            html = "请求失败";
        }
        return html;
    }

    private SimpleNewsEntity getSimpleNewsDetail(NewsEntity newsEntity, boolean isGetContent) {
        SimpleNewsEntity simpleNewsEntity = new SimpleNewsEntity();
        simpleNewsEntity.setId(newsEntity.getId());
        simpleNewsEntity.setBt(newsEntity.getBt());
        simpleNewsEntity.setFold_id(newsEntity.getFold_id());
        simpleNewsEntity.setZz(newsEntity.getZz());
        simpleNewsEntity.setLy(newsEntity.getLy());
        simpleNewsEntity.setCjrq(newsEntity.getCjrq());
        simpleNewsEntity.setCjsj(newsEntity.getCjsj());
        simpleNewsEntity.setLx(newsEntity.getLx());

        String lx = newsEntity.getLx();
        if (lx.equals("l")) {
            simpleNewsEntity.setContent("");
            simpleNewsEntity.setHref(newsEntity.getHref());
        } else {
            String content = "加载中..";
            if (isGetContent) {
                String sh = newsEntity.getStation_sh();
                String fn = newsEntity.getFn();
                content = getContentDetail(sh, fn);
            }
            simpleNewsEntity.setContent(content);
            String href = newsEntity.getHref();
            if (!StringUtils.isNullOrEmpty(href)) {
                if ((href.startsWith("http://")) || (href.startsWith("https://"))) {
                    simpleNewsEntity.setHref(newsEntity.getHref());
                } else {
                    String fn = newsEntity.getFn();
                    if ((StringUtils.isNullOrEmpty(fn)) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else if ((!fn.endsWith(".txt")) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else {
                        simpleNewsEntity.setHref("");
                    }
                }
            } else {
                simpleNewsEntity.setHref("");
            }
        }
        return simpleNewsEntity;
    }

    private VoNewsEntity getSimpleNewsVo(HashMap<String, Object> entity) {
        boolean isGetContent = false;

        VoNewsEntity simpleNewsEntity = new VoNewsEntity();
        simpleNewsEntity.setId((String) entity.get("id"));
        simpleNewsEntity.setStation_sh((String) entity.get("station_sh"));
        simpleNewsEntity.setStation_name((String) entity.get("station_name"));
        simpleNewsEntity.setFold_name((String) entity.get("fold_name"));
        simpleNewsEntity.setFold_id((String) entity.get("fold_id"));
        simpleNewsEntity.setBt((String) entity.get("bt"));
        simpleNewsEntity.setLx((String) entity.get("lx"));
        simpleNewsEntity.setBz((String) entity.get("bz"));
        simpleNewsEntity.setCjrq((String) entity.get("cjrq"));
        simpleNewsEntity.setCjsj((String) entity.get("cjsj"));
        simpleNewsEntity.setXgrq((String) entity.get("xgrq"));
        simpleNewsEntity.setXgsj((String) entity.get("xjsj"));
        simpleNewsEntity.setLy((String) entity.get("ly"));
        simpleNewsEntity.setZz((String) entity.get("zz"));
        simpleNewsEntity.setFn((String) entity.get("fn"));
        simpleNewsEntity.setHref((String) entity.get("href"));
        simpleNewsEntity.setMy1((String) entity.get("my1"));
        simpleNewsEntity.setJsrq((String) entity.get("jsrq"));

        String lx = (String) entity.get("lx");
        if (lx.equals("l")) {
            simpleNewsEntity.setContent("");
            simpleNewsEntity.setHref((String) entity.get("href"));
        } else {
            String content = "加载中..";
            if (isGetContent) {
                String sh = (String) entity.get("station_sh");
                String fn = (String) entity.get("fn");
                content = getContent(sh, fn);
            }
            simpleNewsEntity.setContent(content);
            String href = (String) entity.get("href");
            if (!StringUtils.isNullOrEmpty(href)) {
                if ((href.startsWith("http://")) || (href.startsWith("https://"))) {
                    simpleNewsEntity.setHref((String) entity.get("href"));
                } else {
                    String fn = (String) entity.get("fn");
                    if ((StringUtils.isNullOrEmpty(fn)) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref((String) entity.get("station_sh") + (String) entity.get("href"));
                    } else if ((!fn.endsWith(".txt")) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref((String) entity.get("station_sh") + (String) entity.get("href"));
                    } else {
                        simpleNewsEntity.setHref("");
                    }
                }
            } else {
                simpleNewsEntity.setHref("");
            }
        }
        return simpleNewsEntity;
    }

    private String getContent(String sh, String fn) {
        String html = "请求失败";
        //将域名转换成ip地址，解决服务器无法解析域名问题
        sh = sh.replace("www.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("zwgk.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("wmsd.shunde.gov.cn", "221.4.169.241");
        sh = sh.replace("www1.shunde.gov.cn", "221.4.169.241");
        String url = sh + fn;
        System.out.println(url);
        html = HttpUtil.sendGetData(url, "", "gbk");
        if (StringUtils.hasText(html)) {
            html = replaceHtml(html);
        } else {
            html = "请求失败！";
        }
        return html;
    }

    private String replaceHtml(String htmlStr, Integer no) {
        String regEx_body = "<div class=\"template_content\">([\\S\\s]*)</div>\\s*</div>\\s*</div>\\s*<div id=\"BottomHtml\">";
        Pattern p_body = Pattern.compile(regEx_body, 2);
        Matcher m_body = p_body.matcher(htmlStr);
        if (m_body.find()) {
            htmlStr = m_body.group(1);
        }
        String regEx_comm = "<!--[\\s\\S]*?-->";
        Pattern p_comm = Pattern.compile(regEx_comm, 2);
        Matcher m_comm = p_comm.matcher(htmlStr);
        htmlStr = m_comm.replaceAll("");

        String regEx_tables = "</a>([\\s\\S]*?)<a[\\s\\S]*?name=\\d+>";
        Pattern p_tables = Pattern.compile(regEx_tables, 2);
        Matcher m_tables = p_tables.matcher(htmlStr + "<A name=99>");

        int index = 0;
        while (m_tables.find()) {
            index++;
            if (index == no.intValue()) {
                htmlStr = m_tables.group(1);
                break;
            }
        }
        if (index != no.intValue()) {
            return "找不到匹配内容";
        }
        return htmlStr.trim();
    }

    private static String replaceHtml(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html = "<(?!br|a|/a).*?>";
        String regEx_space = "\\s+";
        String regEx_br = "<br[\\s/]*>";
        String regEx_a = "<a([\\s\\S]+)href=[\"'>]([^\"']*)[\"'>]([^>]*)>([\\s\\S]*)</a>";
        String regEx_br2 = "<br[\\s/]*><br[\\s/]*>";
        String regEx_a2 = "<A([\\s]+)name=([^\"'>]*)>([\\S]*)</A>";

        Pattern p_script = Pattern.compile(regEx_script, 2);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_style, 2);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        Pattern p_a = Pattern.compile(regEx_a, 2);
        Matcher m_a = p_a.matcher(htmlStr);
        if (m_a.find()) {
            String url = m_a.group(2);
            if (!url.startsWith("http://")) {
                url = "http://www.shunde.gov.cn" + url;
            }
            htmlStr = m_a.replaceAll("<subfont urltype='openfile' href='directurl:" + url + "' >$4</subfont>");
        }
        Pattern p_a2 = Pattern.compile(regEx_a2, 2);
        Matcher m_a2 = p_a2.matcher(htmlStr);
        htmlStr = m_a2.replaceAll("<A name='$2'>$3</A>");

        Pattern p_space = Pattern.compile(regEx_space, 2);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(" ");

        Pattern p_br2 = Pattern.compile(regEx_br2, 2);
        Matcher m_br2 = p_br2.matcher(htmlStr);
        htmlStr = m_br2.replaceAll("<br size='8' />");

        Pattern p_br = Pattern.compile(regEx_br, 2);
        Matcher m_br = p_br.matcher(htmlStr);
        htmlStr = m_br.replaceAll("<br />");

        htmlStr = htmlStr.replace("  ", "<tab />");
        htmlStr = htmlStr.replace("\t", "<tab />");
        htmlStr = htmlStr.replace("&#160;&#160;&#160;", "<tab />");
        htmlStr = htmlStr.replace("&#160;", " ");
        htmlStr = htmlStr.replace("&nbsp;&nbsp;", "<tab />");
        htmlStr = htmlStr.replace("&nbsp;", " ");
        htmlStr = htmlStr.replace("\"", "\\\"");
        htmlStr = htmlStr.replace("&not;", "");

        return htmlStr.trim();
    }

    private static String replaceHtml(String htmlStr, String sh) {
        String regEx_img = "<img([^>]+?)src\\s*=\\s*['\"]([^'\"]+)['\"]([^>]*)>";
        String regEx_a = "<a([^>]+?)href=[\"']?([^\"']+)[\"']?([^>]*)>([^<]+)</a>";

        Pattern p_img = Pattern.compile(regEx_img, 2);
        Matcher m_img = p_img.matcher(htmlStr);
        StringBuffer sbImg = new StringBuffer();
        while (m_img.find()) {
            String src = m_img.group(2);
            if ((!src.startsWith("http://")) && (!src.startsWith("https://"))) {
                src = sh + src;
            }
            m_img.appendReplacement(sbImg, "<img$1src='" + src + "'$3>");
        }
        m_img.appendTail(sbImg);
        htmlStr = sbImg.toString();

        Pattern p_a = Pattern.compile(regEx_a, 2);
        Matcher m_a = p_a.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        while (m_a.find()) {
            String url = m_a.group(2);
            if ((!url.startsWith("http://")) && (!url.startsWith("https://"))) {
                url = sh + url;
            }
            m_a.appendReplacement(sb, "<a$1href='" + url + "'$3>$4</a>");
        }
        m_a.appendTail(sb);

        htmlStr = sb.toString();

        return htmlStr.trim();
    }

    private List<HashMap<String, Object>> replaceHtml3(String htmlStr) {
        List<HashMap<String, Object>> list = new ArrayList();

        String regEx_body = "<div class=\"zwp_right\">([\\s\\S]+?)<div class=\"bg_bottomline\">\\s*</div>";
        Pattern p_body = Pattern.compile(regEx_body, 2);
        Matcher m_body = p_body.matcher(htmlStr);
        if (m_body.find()) {
            htmlStr = m_body.group(1);
        }
        String regEx_comm = "<!--[\\s\\S]*?-->";
        Pattern p_comm = Pattern.compile(regEx_comm, 2);
        Matcher m_comm = p_comm.matcher(htmlStr);
        htmlStr = m_comm.replaceAll("");

        String regEx_tables = "<DIV class=fold_title>\\s*<DIV class=fold_txt>([\\s\\S]+?)</DIV>\\s*</DIV>\\s*<DIV class=fold_3>([\\s\\S]+?)</DIV>\\s*<DIV class=fold_split>\\s*</DIV>";
        Pattern p_tables = Pattern.compile(regEx_tables, 2);
        Matcher m_tables = p_tables.matcher(htmlStr);
        while (m_tables.find()) {
            HashMap<String, Object> category = new HashMap();
            List<HashMap<String, Object>> items = new ArrayList();
            category.put("title", m_tables.group(1));
            String itemStr = m_tables.group(2);
            String regEx_items = "<DIV>\\s*<A[\\s\\S]*?href=\"\\S+?\\?id=(\\d+)-(\\d+)#(\\d+)\"[\\s\\S]*?>([\\s\\S]+?)</A>\\s*</DIV>";
            Pattern p_items = Pattern.compile(regEx_items, 2);
            Matcher m_items = p_items.matcher(itemStr);
            while (m_items.find()) {
                HashMap<String, Object> hashMap = new HashMap();
                hashMap.put("id", m_items.group(1));
                hashMap.put("fid", m_items.group(2));
                hashMap.put("no", m_items.group(3));
                hashMap.put("name", m_items.group(4));
                items.add(hashMap);
            }
            category.put("items", items);

            list.add(category);
        }
        return list;
    }

    private VoNewsEntity getSimpleNewsVo(NewsEntity entity) {
        return getSimpleNewsVo(entity, false);
    }

    private VoNewsEntity getSimpleNewsVo(NewsEntity newsEntity, boolean isGetContent) {
        VoNewsEntity simpleNewsEntity = new VoNewsEntity();
        simpleNewsEntity.setId(newsEntity.getId());
        simpleNewsEntity.setStation_sh(newsEntity.getStation_sh());
        simpleNewsEntity.setStation_name(newsEntity.getStation_name());
        simpleNewsEntity.setFold_name(newsEntity.getFold_name());
        simpleNewsEntity.setFold_id(newsEntity.getFold_id());
        simpleNewsEntity.setBt(newsEntity.getBt());
        simpleNewsEntity.setLx(newsEntity.getLx());
        simpleNewsEntity.setBz(newsEntity.getBz());
        simpleNewsEntity.setCjrq(newsEntity.getCjrq());
        simpleNewsEntity.setCjsj(newsEntity.getCjsj());
        simpleNewsEntity.setXgrq(newsEntity.getXgrq());
        simpleNewsEntity.setXgsj(newsEntity.getXgsj());
        simpleNewsEntity.setLy(newsEntity.getLy());
        simpleNewsEntity.setZz(newsEntity.getZz());
        simpleNewsEntity.setFn(newsEntity.getFn());
        simpleNewsEntity.setHref(newsEntity.getHref());
        simpleNewsEntity.setMy1(newsEntity.getMy1());
        simpleNewsEntity.setJsrq(newsEntity.getJsrq());

        String lx = newsEntity.getLx();
        if (lx.equals("l")) {
            simpleNewsEntity.setContent("");
            simpleNewsEntity.setHref(newsEntity.getHref());
        } else {
            String content = "加载中..";
            if (isGetContent) {
                String sh = newsEntity.getStation_sh();
                String fn = newsEntity.getFn();
                content = getContent(sh, fn);
            }
            simpleNewsEntity.setContent(content);
            String href = newsEntity.getHref();
            if (!StringUtils.isNullOrEmpty(href)) {
                if ((href.startsWith("http://")) || (href.startsWith("https://"))) {
                    simpleNewsEntity.setHref(newsEntity.getHref());
                } else {
                    String fn = newsEntity.getFn();
                    if ((StringUtils.isNullOrEmpty(fn)) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else if ((!fn.endsWith(".txt")) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else {
                        simpleNewsEntity.setHref("");
                    }
                }
            } else {
                simpleNewsEntity.setHref("");
            }
        }
        return simpleNewsEntity;
    }

    private SimpleNewsEntity getSimpleNews(NewsEntity newsEntity) {
        return getSimpleNews(newsEntity, false);
    }

    private SimpleNewsEntity getSimpleNews(NewsEntity newsEntity, boolean isGetContent) {
        SimpleNewsEntity simpleNewsEntity = new SimpleNewsEntity();
        simpleNewsEntity.setId(newsEntity.getId());
        simpleNewsEntity.setBt(newsEntity.getBt());
        simpleNewsEntity.setFold_id(newsEntity.getFold_id());
        simpleNewsEntity.setZz(newsEntity.getZz());
        simpleNewsEntity.setLy(newsEntity.getLy());
        simpleNewsEntity.setCjrq(newsEntity.getCjrq());
        simpleNewsEntity.setCjsj(newsEntity.getCjsj());
        simpleNewsEntity.setLx(newsEntity.getLx());

        String lx = newsEntity.getLx();
        if (lx.equals("l")) {
            simpleNewsEntity.setContent("");
            simpleNewsEntity.setHref(newsEntity.getHref());
        } else {
            String content = "加载中..";
            if (isGetContent) {
                String sh = newsEntity.getStation_sh();
                String fn = newsEntity.getFn();
                content = getContent(sh, fn);
            }
            simpleNewsEntity.setContent(content);
            String href = newsEntity.getHref();
            if (!StringUtils.isNullOrEmpty(href)) {
                if ((href.startsWith("http://")) || (href.startsWith("https://"))) {
                    simpleNewsEntity.setHref(newsEntity.getHref());
                } else {
                    String fn = newsEntity.getFn();
                    if ((StringUtils.isNullOrEmpty(fn)) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else if ((!fn.endsWith(".txt")) && (href.startsWith("/"))) {
                        simpleNewsEntity.setHref(newsEntity.getStation_sh() + newsEntity.getHref());
                    } else {
                        simpleNewsEntity.setHref("");
                    }
                }
            } else {
                simpleNewsEntity.setHref("");
            }
        }
        return simpleNewsEntity;
    }

    @RequestMapping(value = "/test")
    public String test() {
        String url = "http://221.4.169.241/data/2019/08/22/1566463521.txt?";
        return HttpUtil.sendGet("", "", "gbk");
    }

    /**
     * 功能描述 : 轮播图，防疫专栏，九宫格菜单缓存数据清理
     *
     * @param * @param null
     * @return
     * @author 张恭雨
     * @date 2020/2/15
     */
    @ControlLog(operateType = "/cacheClear", context = "轮播图，防疫专栏，九宫格菜单缓存数据清理")
    @RequestMapping(value = "/cacheClear")
    @ResponseBody
    public Resp cacheClear() {
        dataService.clearCache();
        Resp resp = new Resp(RespCode.SUCCESS);
        resp.setMsg("清理成功！");
        return resp;
    }
}
