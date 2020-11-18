package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.ZtreeEntity;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version V1.0
 * @title: VideoMonitorService
 * @projectName sdbst
 * @description: 视频监控业务逻辑层
 * @author： 张恭雨
 * @date 2019/8/27 9:57
 */
@Service
public class VideoMonitorService {
    public int checkUser(String cmsUrl, String username, String password) {
        Document doc = null;
        byte status = 0;

        try {
            HttpUtil.executeMethod(cmsUrl, "GetUser", (NameValuePair[])null, username, password);
        } catch (DocumentException var7) {
            status = -1;
        } catch (MyException var8) {
            if (var8.getAppcode() == 401) {
                status = 2;
            } else {
                status = 1;
            }
        } catch (IOException var9) {
            status = -1;
        }

        return status;
    }

    public List<ZtreeEntity> getVcuList(String cmsUrl, String username, String password) {
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><MESSAGE Version=\"1.0\"><CV_HEADER MsgType=\"MSG_GET_VCU_LIST_REQ\" MsgSeq=\"12345678\" /><USER_PROFILE ScId=\"" + username + "\" StampTime=\"20100619200000\" GetProfileFlag=\"1\" SCType=\"1\" /></MESSAGE>");
        if (!cmsUrl.endsWith("/")) {
            cmsUrl = cmsUrl + "/";
        }

        Document doc = HttpUtil.sendStringVapBody(username, password, cmsUrl, requestBody.toString());
        List<ZtreeEntity> ztreeList = new ArrayList();
        if (doc != null) {
            Node resultNode = doc.selectSingleNode("//MESSAGE/RESULT");
            String code = resultNode.valueOf("./@ErrorCode");
            if (code != null && code.equals("0")) {
                List<Node> list = doc.selectNodes("//MESSAGE/SUBJECT_INFO_LIST/SUBJECT_INFO");
                String name;
                String vcuId;
                String status;
                if (list != null && !list.isEmpty()) {
                    for(int i = 0; i < list.size(); ++i) {
                        Node oneNode = (Node)list.get(i);
                        ZtreeEntity ze = new ZtreeEntity();
                        name = oneNode.valueOf("./@SubjectId");
                        vcuId = oneNode.valueOf("./@SubjectName");
                        status = oneNode.valueOf("./@ParentId");
                        if (name.equals("<ROOT>") || name.equals("&lt;ROOT&gt;")) {
                            status = null;
                            vcuId = "跟地区";
                            ze.setOpen(true);
                        }

                        ze.setId(name);
                        ze.setName(vcuId);
                        ze.setpId(status);
                        ze.setParent(true);
                        ze.setIconSkin("areaSkin");
                        ztreeList.add(ze);
                    }
                }

                List<Node> vculist = doc.selectNodes("//MESSAGE/VCU_LIST/VCU");
                if (vculist != null && !vculist.isEmpty()) {
                    for(int i = 0; i < vculist.size(); ++i) {
                        Node oneNode = (Node)vculist.get(i);
                        name = oneNode.valueOf("./@Name");
                        vcuId = oneNode.valueOf("./@VcuId");
                        status = oneNode.valueOf("./@Status");
                        String commType = oneNode.valueOf("./@CommType");
                        String type = oneNode.valueOf("./@Type");
                        List<Node> camList = oneNode.selectNodes("./CAMERA_LIST/CAMERA");
                        if (camList != null && !camList.isEmpty()) {
                            for(int j = 0; j < camList.size(); ++j) {
                                Node camNode = (Node)camList.get(j);
                                ZtreeEntity ze = new ZtreeEntity();
                                String subjectId = camNode.valueOf("./@SubjectId");
                                String camName = camNode.valueOf("./@Name");
                                String cameraId = camNode.valueOf("./@CameraId");
                                String comments = camNode.valueOf("./@Comments");
                                String pTZType = camNode.valueOf("./@PTZType");
                                ze.setId(cameraId);
                                ze.setpId(subjectId);
                                ze.setParent(false);
                                ze.setName(camName);
                                ze.setCam(true);
                                if ("1".equals(status)) {
                                    ze.setIsOnline(true);
                                    ze.setIconSkin("camSkinOnline");
                                } else {
                                    ze.setIsOnline(false);
                                    ze.setIconSkin("camSkinOffline");
                                }

                                ztreeList.add(ze);
                            }
                        }
                    }
                }
            }
        }

        return ztreeList;
    }

    public Map<String, Object> getVideoQuality(String videoQualityUrl, String username, String password) {
        String rtnStr = HttpUtil.sendGetRequest(username, password, videoQualityUrl);
        Map vqRsp = null;

        try {
            vqRsp = (Map)HttpUtil.convertJsonStr2Bean(rtnStr, HashMap.class);
        } catch (IOException var7) {
            ;
        } catch (Exception var8) {
            ;
        }

        return vqRsp;
    }

    public Map<String, Object> getPlayUrlRspFromCMS(String getPlayUrl, String username, String password, Map<String, Object> params) {
        String deviceId = (String)params.get("deviceId");
        String type = (String)params.get("type");
        Date beginTime = params.get("beginTime") == null ? null : new Date((Long)params.get("beginTime"));
        Date endTime = params.get("endTime") == null ? null : new Date((Long)params.get("endTime"));
        String videoQuality = (String)params.get("videoQuality");
        if (!getPlayUrl.endsWith("/")) {
            getPlayUrl = getPlayUrl + "/";
        }

        String url = getPlayUrl + deviceId;
        url = url + "?Type=" + type;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if ("cv_vod".equals(type) || "pu_vod".equals(type)) {
            if (beginTime != null) {
                url = url + "&BeginTime=" + sdf.format(beginTime).replaceAll(" ", "%20");
            }

            if (endTime != null) {
                url = url + "&EndTime=" + sdf.format(endTime).replaceAll(" ", "%20");
            }
        }

        if ("original".equals(videoQuality) || "high".equals(videoQuality) || "normal".equals(videoQuality) || "standard".equals(videoQuality)) {
            url = url + "&VideoQuality=" + videoQuality;
        }

        String rtnStr = HttpUtil.sendGetRequest(username, password, url);
        Map rsp = null;

        try {
            rsp = (Map)HttpUtil.convertJsonStr2Bean(rtnStr, HashMap.class);
        } catch (IOException var15) {
            var15.printStackTrace();
        }

        return rsp;
    }

    public Map<String, Object> getPlayList(String getPlayList, String username, String password, Map<String, Object> params) {
        String vapCommand = (String)params.get("vapCommand");
        String deviceId = (String)params.get("deviceId");
        String scId = (String)params.get("scId");
        Integer beginIndex = (Integer)params.get("beginIndex");
        Map<String, Object> result = new HashMap();
        if (deviceId != null && deviceId.length() > 0 && vapCommand != null && vapCommand.length() > 0) {
            Date nowTime = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(nowTime);
            calendar.add(5, -6);
            Date dateBef = calendar.getTime();
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
            StringBuffer requestBody = new StringBuffer();
            requestBody.append("<?xml version='1.0' encoding='UTF-8' ?>");
            requestBody.append("<MESSAGE Version='1.0'>");
            if (vapCommand.equals("GetVcuRecordInfo")) {
                requestBody.append("<CV_HEADER MsgType='MSG_MSC_GET_VCU_RECORD_INFO_REQ' MsgSeq='1234' />");
                requestBody.append("<CAMERA_ID>" + deviceId + "</CAMERA_ID>");
            } else {
                requestBody.append("<CV_HEADER MsgType='MSG_GET_RECORD_INFO_REQ' MsgSeq='1234' />");
                requestBody.append("<SC_INFO ScId='" + scId + "'/>");
                requestBody.append("<CAMERA_ID_LIST>");
                requestBody.append("<CAMERA_ID>" + deviceId + "</CAMERA_ID>");
                requestBody.append("</CAMERA_ID_LIST>");
            }

            requestBody.append("<PAGE_INFO BeginIndex='" + beginIndex + "' />");
            requestBody.append("<RECORD_QUERY_INFO BeginTime='" + time.format(dateBef) + " 00:00:00' EndTime='" + time.format(nowTime) + " 23:59:59'>");
            requestBody.append("<EVENT_LIST>");
            requestBody.append("<EVENT>ALL</EVENT>");
            requestBody.append("</EVENT_LIST>");
            requestBody.append("</RECORD_QUERY_INFO>");
            requestBody.append("</MESSAGE>");
            Document doc = HttpUtil.sendStringVapBody(username, password, getPlayList, requestBody.toString());
            List<Map<String, Object>> recordList = new ArrayList();
            String beginIndexStr = "0";
            String endIndex = "0";
            String totalNum = "0";
            Node resultNode = doc.selectSingleNode("//MESSAGE/RESULT");
            String code = resultNode.valueOf("./@ErrorCode");
            if (code != null && code.equals("0")) {
                List<Node> list = doc.selectNodes("//MESSAGE/RECORD_INFO_LIST/RECORD_INFO");
                if (list != null) {
                    for(int i = 0; i < list.size(); ++i) {
                        Node nodeRecInfo = (Node)list.get(i);
                        String event = nodeRecInfo.valueOf("./@Event");
                        String contentSize = nodeRecInfo.valueOf("./@ContentSize");
                        String beginTime = nodeRecInfo.valueOf("./@BeginTime");
                        String endTime = nodeRecInfo.valueOf("./@EndTime");
                        String camId = nodeRecInfo.valueOf("./@CameraId");
                        String contentId = nodeRecInfo.valueOf("./@contentId");
                        Date beginDate = HttpUtil.stringTranDate("yyyy-MM-dd HH:mm:ss", beginTime);
                        Date endDate = HttpUtil.stringTranDate("yyyy-MM-dd HH:mm:ss", endTime);
                        Map<String, Object> record = new HashMap();
                        record.put("event", event);
                        record.put("contentSize", contentSize);
                        record.put("beginDate", beginDate != null ? "" + beginDate.getTime() : beginTime);
                        record.put("endDate", endDate != null ? "" + endDate.getTime() : endTime);
                        record.put("cameraId", camId);
                        record.put("contentId", contentId);
                        recordList.add(record);
                    }
                }

                Node pageInfo = doc.selectSingleNode("//MESSAGE/PAGE_INFO");
                beginIndexStr = pageInfo.valueOf("./@BeginIndex");
                endIndex = pageInfo.valueOf("./@EndIndex");
                totalNum = pageInfo.valueOf("./@TotalNum");
            }

            result.put("playList", recordList);
            result.put("beginIndex", beginIndex);
            result.put("endIndex", endIndex);
            result.put("totalNum", totalNum);
            return result;
        } else {
            return result;
        }
    }
}
