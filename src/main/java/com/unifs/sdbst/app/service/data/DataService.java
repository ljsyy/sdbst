package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.base.ServiceResult;
import com.unifs.sdbst.app.bean.data.FoldEntity;
import com.unifs.sdbst.app.bean.data.NewsEntity;
import com.unifs.sdbst.app.utils.CommUtils;
import com.unifs.sdbst.app.utils.StringUtils;
import com.unifs.sdbst.app.utils.ZFWDBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rxframework.base.model.OutPut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @title: DataService
 * @projectName sdbst
 * @description: sundeAPI
 * @author： 张恭雨
 * @date 2019/10/9 9:25
 */
@Service
public class DataService {
    private Logger log = LoggerFactory.getLogger(DataService.class);
    private static String selectString = "id, station_sh, station_name, fold_name, fold_id, bt, lx, bz, cjrq, cjsj, xgrq, xgsj, ly, fn, href";
    private static final String selectStrGklm = "gkxx_id as id,station_sh,station_name,fold_name,fold_id,gkxx_title as bt,\"t\" as lx ,gkxx_bz as bz,gkxx_date as cjrq,\"\" as cjsj,\"\" as xgrq,\"\" as xgsj,\"\" as ly, \"\" as fn,\"\" as href";

    @Autowired
    private ZFWDBHelper dbHelper;


    @Cacheable(value = "GetView", key = "'fold_id'+#fold_id")
    public String GetView(Integer fold_id) {

        String viewName = "";
        try {
            Connection conn = dbHelper.getConn();
            String sql = String.format("select * from v_fold where fold_id=%d", new Object[]{fold_id});
            log.info("GetView Query=" + sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                viewName = rst.getString("fold_tab");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewName;
    }

    @Cacheable(value = "DataDetail", key = "'id'+#id+#view")
    public NewsEntity Detail(String view, Integer id) {
        NewsEntity news = null;
        try {
            Connection conn = dbHelper.getConn();
            String sql = String.format("select * from %s where bz>=1 and id=%s", new Object[]{view, id});
            this.log.info("Detail Query=" + sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                news = new NewsEntity();
                news.setId(rst.getString("id"));
                //处理字符串，截去二级目录
                String stationSh = rst.getString("station_sh");
                if (stationSh != null && stationSh != "") {
                    int index = StringUtils.strIndexByTime(rst.getString("station_sh"), 3, "/");
                    if (index != -1) {
                        stationSh = (rst.getString("station_sh")).substring(0, index);
                    }
                }
                news.setStation_sh(stationSh);

                news.setStation_name(rst.getString("station_name"));
                news.setFold_name(rst.getString("fold_name"));
                news.setFold_id(rst.getString("fold_id"));
                news.setBt(rst.getString("bt"));
                news.setLx(rst.getString("lx"));
                news.setBz(rst.getString("bz"));
                news.setCjrq(rst.getString("cjrq"));
                news.setCjsj(rst.getString("cjsj"));
                news.setXgrq(rst.getString("xgrq"));
                news.setXgsj(rst.getString("xgsj"));
                news.setLy(rst.getString("ly"));
                news.setZz(rst.getString("zz"));
                news.setFn(rst.getString("fn"));
                //对链接做截取处理
                news.setHref(rst.getString("href"));

                String bt = rst.getString("id");
                System.out.println("bt=" + bt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    @Cacheable(value = "DataListNew", key = "'foldIds'+#foldIds+#start+#limit")
    public ServiceResult getListNew(int start, int limit, OutPut<Long> total, Boolean needFoldId, String foldIds, String whereSQL) {
        ServiceResult result = new ServiceResult();
        List<NewsEntity> list = new ArrayList();
        String selectStr = selectString;
        long count = 0L;
        try {
            if (StringUtils.hasText(whereSQL)) {
                whereSQL = "and " + whereSQL;
            }
            if (whereSQL.contains("status")) {
                selectStr = selectString + ",status";
            }
            Connection conn = dbHelper.getConn();
            ResultSet rst = null;
            String union;
            String sql;
            ArrayList<HashMap<String, Object>> lst;
            if (whereSQL.contains("gkxx_lm")) {
                int fid = CommUtils.safeInt(foldIds);
                String view = GetView(Integer.valueOf(fid));
                if ((view.length() <= 0) && (needFoldId.booleanValue())) {
                    result.setErrorCode(Integer.valueOf(-1));
                    result.setErrorMsg("找不到栏目！");
                    return result;
                }
                String sqlIn = (foldIds.length() > 0) && (needFoldId.booleanValue()) ? "and fold_id = " + foldIds : "";

                union = "";
                sql = "";

                lst = new ArrayList();

                union = union + String.format("(SELECT %s FROM %s WHERE bz=1 %s) UNION ALL(SELECT %s from v_www_hxsj_gkxx WHERE  gkxx_bz=1 %s) ", new Object[]{selectStr, "v_" + view,
                        sqlIn, "gkxx_id as id,station_sh,station_name,fold_name,fold_id,gkxx_title as bt,\"t\" as lx ,gkxx_bz as bz,gkxx_date as cjrq,\"\" as cjsj,\"\" as xgrq,\"\" as xgsj,\"\" as ly, \"\" as fn,\"\" as href", whereSQL});
                sql = String.format(
                        "select %s from (%s) as t where bz=1  order by cjrq desc,cjsj desc limit %d,%d", new Object[]{selectStr, union, Integer.valueOf(start), Integer.valueOf(limit)});

                System.out.println(sql);
                this.log.info("getList Query=" + sql);

                PreparedStatement pstmt_out = conn.prepareStatement(sql);
                ResultSet rst_out = pstmt_out.executeQuery();
                while (rst_out.next()) {
                    HashMap<String, Object> map = new HashMap();
                    String out_fold_id = rst_out.getString("fold_id");
                    int out_fid = CommUtils.safeInt(out_fold_id);
                    String out_view = GetView(Integer.valueOf(out_fid));

                    String out_id = rst_out.getString("id");
                    if (out_view.length() <= 0) {
                        result.setErrorCode(Integer.valueOf(-1));
                        result.setErrorMsg("找不到栏目！");
                        return result;
                    }
                    String out_view_id = out_id.length() > 0 ? "and id = " + out_id : "";

                    sql = String.format("select %s from %s as t where bz=1 %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{selectStr, "v_" + out_view, out_view_id, Integer.valueOf(0), Integer.valueOf(1)});
                    System.out.println(sql);
                    this.log.info("getList Query=" + sql);
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    rst = pstmt.executeQuery();
                    if (rst.next()) {
                        String href = rst.getString("href");
                        String id = rst.getString("id");
                        //处理字符串，截去二级目录

                        String stationSh = rst.getString("station_sh");
                        int index = StringUtils.strIndexByTime(stationSh, 3, "/");
                        if (index != -1) {
                            stationSh = (stationSh.substring(0, index));
                        }
                        String station_sh = (stationSh);

                        String station_name = rst.getString("station_name");
                        String fold_name = rst.getString("fold_name");
                        String fold_id = rst.getString("fold_id");
                        String bt = rst.getString("bt");
                        String lx = rst.getString("lx");
                        String bz = rst.getString("bz");
                        String cjrq = rst.getString("cjrq");
                        String cjsj = rst.getString("cjsj");
                        String xgrq = rst.getString("xgrq");
                        String xgsj = rst.getString("xgsj");
                        String ly = rst.getString("ly");
                        String fn = rst.getString("fn");

                        map.put("id", id);
                        map.put("href", href);
                        map.put("station_sh", station_sh);
                        map.put("station_name", station_name);
                        map.put("fold_name", fold_name);
                        map.put("fold_id", fold_id);
                        map.put("bt", bt);
                        map.put("lx", lx);
                        map.put("bz", bz);
                        map.put("cjrq", cjrq);
                        map.put("cjsj", cjsj);
                        map.put("xgrq", xgrq);
                        map.put("xgsj", xgsj);
                        map.put("ly", ly);
                        map.put("fn", fn);
                    }
                    lst.add(map);
                    count += 1L;
                }
                result.setStatus(Integer.valueOf(1));
                result.setData(lst);
            } else {
                if (foldIds.contains(",")) {
                    String[] foldIdArr = foldIds.split(",");
                    List<Map<String, Object>> foldView = new ArrayList();
                    int length = foldIdArr.length;
                    String view;
                    for (int i = 0; i < length; i++) {
                        String fidStr = foldIdArr[i];
                        Integer fid = Integer.valueOf(CommUtils.safeInt(fidStr));
                        view = GetView(fid);
                        if (view.length() <= 0) {
                            result.setErrorCode(Integer.valueOf(-1));
                            result.setErrorMsg("找不到栏目！" + fid);
                            return result;
                        }
                        Map<String, Object> map = new HashMap();
                        map.put("fold_id", fid);
                        map.put("fold_tab", "v_" + view);
                        foldView.add(map);
                    }
                    boolean isSingleView = true;
                    String viewName = ((Map) foldView.get(0)).get("fold_tab").toString();
                    for (Map<String, Object> map : foldView) {
                        if (!viewName.equals(map.get("fold_tab"))) {
                            isSingleView = false;
                            break;
                        }
                    }
                    String sqlTemp = "";
                    if (isSingleView) {
                        String sqlIn = "and fold_id in (" + foldIds + ")";
                        sqlTemp = String.format("select %s from %s where bz=1 %s %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{
                                selectStr, viewName, sqlIn, whereSQL, Integer.valueOf(start), Integer.valueOf(limit)});
                    } else {
                        String unionTemp = "";
                        for (Map<String, Object> map : foldView) {
                            unionTemp = unionTemp + String.format("(SELECT %s FROM %s WHERE FOLD_ID = %d) UNION ALL ", new Object[]{selectStr, map.get("fold_tab"),
                                    map.get("fold_id")});
                        }
                        unionTemp = unionTemp.substring(0, unionTemp.lastIndexOf("UNION ALL") - 1);
                        sqlTemp = String.format(
                                "select %s from (%s) as t where bz=1 %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{selectStr, unionTemp,
                                        whereSQL, Integer.valueOf(start), Integer.valueOf(limit)});
                    }
                    System.out.println(sqlTemp);
                    this.log.info("getList Query=" + sqlTemp);
                    PreparedStatement pstmt = conn.prepareStatement(sqlTemp);
                    rst = pstmt.executeQuery();
                } else {
                    int fid = CommUtils.safeInt(foldIds);
                    String view = GetView(Integer.valueOf(fid));
                    if ((view.length() <= 0) && (needFoldId.booleanValue())) {
                        result.setErrorCode(Integer.valueOf(-1));
                        result.setErrorMsg("找不到栏目！");
                        return result;
                    }
                    String sqlIn = (foldIds.length() > 0) && (needFoldId.booleanValue()) ? "and fold_id = " + foldIds : "";
                    String sqlTemp1 = String.format(
                            "select %s from %s where bz=1 %s %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{selectStr, "v_" + view, sqlIn,
                                    whereSQL, Integer.valueOf(start), Integer.valueOf(limit)});
                    System.out.println(sqlTemp1);
                    this.log.info("getList Query=" + sqlTemp1);
                    PreparedStatement pstmt = conn.prepareStatement(sqlTemp1);
                    rst = pstmt.executeQuery();
                }
                while (rst.next()) {
                    NewsEntity news = new NewsEntity();

                    news.setId(rst.getString("id"));
                    String stationSh = rst.getString("station_sh");
                    if (stationSh != null && stationSh != "") {
                        int index = StringUtils.strIndexByTime(rst.getString("station_sh"), 3, "/");
                        if (index != -1) {
                            stationSh = (rst.getString("station_sh")).substring(0, index);
                        }
                    }
                    news.setStation_sh(stationSh);

                    news.setStation_name(rst.getString("station_name"));
                    news.setFold_name(rst.getString("fold_name"));
                    news.setFold_id(rst.getString("fold_id"));
                    news.setBt(rst.getString("bt"));
                    news.setLx(rst.getString("lx"));
                    news.setBz(rst.getString("bz"));
                    news.setCjrq(rst.getString("cjrq"));
                    news.setCjsj(rst.getString("cjsj"));
                    news.setXgrq(rst.getString("xgrq"));
                    news.setXgsj(rst.getString("xgsj"));
                    news.setLy(rst.getString("ly"));

                    news.setFn(rst.getString("fn"));
                    news.setHref(rst.getString("href"));
                    list.add(news);
                    count += 1L;
                }
                result.setStatus(Integer.valueOf(0));
                result.setData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        total.set(count);
        return result;
    }

    @Cacheable(value = "DataList2", key = "'foldIds_view_2'+#foldIds+#start+#limit")
    public List<NewsEntity> getList2(int start, int limit, OutPut<Long> total, String view, String foldIds, String whereSQL) {
        List<NewsEntity> list = new ArrayList();
        long count = 0L;
        try {
            Connection conn = dbHelper.getConn();
            String sqlIn = foldIds.length() > 0 ? "and fold_id in(" + foldIds + ")" : "";
            String sql = String.format("select * from %s where bz=1 %s %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{
                    view, sqlIn, whereSQL, Integer.valueOf(start), Integer.valueOf(limit)});
            this.log.info("getList Query=" + sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                NewsEntity news = new NewsEntity();
                news.setId(rst.getString("id"));

                String stationSh = rst.getString("station_sh");
                if (stationSh != null && stationSh != "") {
                    int index = StringUtils.strIndexByTime(rst.getString("station_sh"), 3, "/");
                    if (index != -1) {
                        stationSh = (rst.getString("station_sh")).substring(0, index);
                    }
                }
                news.setStation_sh(stationSh);

                news.setStation_name(rst.getString("station_name"));
                news.setFold_name(rst.getString("fold_name"));
                news.setFold_id(rst.getString("fold_id"));
                news.setBt(rst.getString("bt"));
                news.setLx(rst.getString("lx"));
                news.setBz(rst.getString("bz"));
                news.setJsrq(rst.getString("jsrq"));
                news.setCjrq(rst.getString("cjrq"));
                news.setCjsj(rst.getString("cjsj"));
                news.setXgrq(rst.getString("xgrq"));
                news.setXgsj(rst.getString("xgsj"));
                news.setLy(rst.getString("ly"));
                news.setZz(rst.getString("zz"));
                news.setFn(rst.getString("fn"));
                news.setHref(rst.getString("href"));
                list.add(news);
                count += 1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        total.set(Long.valueOf(count));
        return list;
    }

    @Cacheable(value = "DataList", key = "'foldIds_view'+#foldIds+#start+#limit")
    public List<NewsEntity> getList(int start, int limit, OutPut<Long> total, String view, String foldIds, String whereSQL) {
        List<NewsEntity> list = new ArrayList();
        long count = 0L;
        try {
            Connection conn = dbHelper.getConn();
            String sqlIn = foldIds.length() > 0 ? "and fold_id in(" + foldIds + ")" : "";
            String sql = String.format("select * from %s where bz=1 %s %s order by cjrq desc,cjsj desc limit %d,%d", new Object[]{
                    view, sqlIn, whereSQL, Integer.valueOf(start), Integer.valueOf(limit)});
            this.log.info("getList Query=" + sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                NewsEntity news = new NewsEntity();
                news.setId(rst.getString("id"));

                String stationSh = rst.getString("station_sh");
                if (stationSh != null && stationSh != "") {
                    int index = StringUtils.strIndexByTime(rst.getString("station_sh"), 3, "/");
                    if (index != -1) {
                        stationSh = (rst.getString("station_sh")).substring(0, index);
                    }
                }
                news.setStation_sh(stationSh);

                news.setStation_name(rst.getString("station_name"));
                news.setFold_name(rst.getString("fold_name"));
                news.setFold_id(rst.getString("fold_id"));
                news.setBt(rst.getString("bt"));
                news.setLx(rst.getString("lx"));
                news.setBz(rst.getString("bz"));
                news.setCjrq(rst.getString("cjrq"));
                news.setCjsj(rst.getString("cjsj"));
                news.setXgrq(rst.getString("xgrq"));
                news.setXgsj(rst.getString("xgsj"));
                news.setLy(rst.getString("ly"));
                news.setZz(rst.getString("zz"));
                news.setFn(rst.getString("fn"));
                news.setHref(rst.getString("href"));
                list.add(news);
                count += 1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        total.set(Long.valueOf(count));
        return list;
    }

    public List<FoldEntity> getMenuList(OutPut<Long> total, Integer fold_id, String orderByString) {
        List<FoldEntity> list = new ArrayList();
        long count = 0L;
        try {
            ZFWDBHelper dbHelper = new ZFWDBHelper();
            Connection conn = dbHelper.getConn();
            String orderBy = " ";
            if (!StringUtils.isNullOrEmpty(orderByString)) {
                if (orderByString.equals("fold_name")) {
                    orderBy = "ORDER BY fold_name DESC";
                }
            }
            String sql = String.format("select * from v_fold where fold_father_id=%d AND fold_bz=1 " + orderBy, new Object[]{fold_id});
            log.info("getList Query=" + sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                FoldEntity fold = new FoldEntity();
                fold.setFold_father_id(rst.getString("fold_father_id"));
                fold.setFold_name(rst.getString("fold_name"));
                fold.setFold_id(rst.getString("fold_id"));
                fold.setFold_bz(rst.getString("fold_bz"));
                fold.setFold_main_id(rst.getString("fold_main_id"));
                fold.setFold_tab(rst.getString("fold_tab"));
                fold.setFold_ExtUrl(rst.getString("fold_ExtUrl"));
                list.add(fold);
                count += 1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        total.set(Long.valueOf(count));
        return list;
    }

    //缓存清除方法
    @CacheEvict(value = {"DataListNew", "DataList", "DataList2"}, allEntries = true)
    public void clear() {
        System.out.println("列表缓存清除成功！");
    }

    //菜单新闻缓存清除方法
    @CacheEvict(value = {"articleMap", "find1To3Menu",
            "epidemicColumnMenu", "bannerMenu", "hotMenu",
            "appVersion", "androidAppVersion"}, allEntries = true)
    public void clearCache() {
        System.out.println("首页，轮播图缓存清除成功！");
    }


}
