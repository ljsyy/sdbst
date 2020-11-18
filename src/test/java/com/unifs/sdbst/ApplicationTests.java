package com.unifs.sdbst;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.sdcb.payment.client.SignatureService;
import com.unifs.sdbst.app.bean.fingertips.FingertipsEMS;
import com.unifs.sdbst.app.bean.server.HardLog;
import com.unifs.sdbst.app.bean.server.ServerInfo;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.common.security.Digests;
import com.unifs.sdbst.app.dao.primary.user.UserMapper;
import com.unifs.sdbst.app.service.ServerService;
import com.unifs.sdbst.app.service.data.DataService;
import com.unifs.sdbst.app.service.fingertips.FingertipsService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.collections.map.HashedMap;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    StringEncryptor encryptor;
    @Autowired
    RedisUtil redisUtil;
    @Value("${aes.key}")
    private String key;
    @Autowired
    UserService userService;
    @Autowired
    private DataService dataService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    //加密
    @Test
    public void testBsslocal() {

        String url = encryptor.encrypt("jdbc:oracle:thin:@19.202.137.139:1521:sdbst");
        String name = encryptor.encrypt("sdwx");
        String password = encryptor.encrypt("Sdbst#202o");
        System.out.println("url:" + url);
        System.out.println("用户名:" + name);
        System.out.println("密码:" + password);
    }

    @Test
    public void testDe() {
        String s = "BM0afFgRUn44eXC30e4Y4BTZREqdnMHw1nA8DDUIzzNATUmlshFl9lFm6dv1r/cSOJQ4RvN7flM=";
        System.out.println(encryptor.decrypt(s));
    }

    /**
     * 　　* @description: redis测试
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/14 11:29
     */
    @Test
    public void redisTest() {
        //redisUtil.set("user","张三");
        System.out.println(redisUtil.get("user"));
    }


    @Test
    public void aseStrTest() throws Exception {
//        System.out.println(key);
//        String str = "zhanggongyu|"+System.currentTimeMillis();
//        String sb = AES.encrypt(str, key);
//        System.out.println(sb);
        System.out.println(AES.decrypt("vpr4IfUf49ZYGyp7vbke/k0Lv5Q9dECNSqizaPglSXS2QW29H5QjBmVmeR28gCzmt+EsyZ+qZ8M=", key));
    }

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    @Test
    public void entryptPassword() throws Exception{
        String plainPassword="test123456";
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt,
                HASH_INTERATIONS);
        System.out.println(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }


//    @Value("${hospital.key}")
//    private String aesKey;
//    @Test
//    public void desStrTest() throws Exception {
////        System.out.println(key);
////        String str = "zhanggongyu|"+System.currentTimeMillis();
////        String sb = AES.encrypt(str, key);
////        System.out.println(sb);
//        System.out.println(EncryptUtil.aesDecryptString("934dbfc87d6c4cdf9b4cf477eb46603c", aesKey));
//    }

    @Test
    public void aseTest() throws Exception {
        System.out.println(key);
        String str = "{\"phone\":\"13288250767\",\"loginName\":\"qingtian\",\"token\":\"7e52bfd814004e5985d3ea4328396cb5\",\"identity\":\"0001\",,\"identityType\":\"dl\"}";
        String sb = AES.encrypt(str, key);
        System.out.println(sb);
        System.out.println(AES.decrypt(sb, key));
    }

    @Test
    public void stringTest() {
        String str = "";
        int index = StringUtils.strIndexByTime(str, 3, "/");
        if (index != -1) {
            str = str.substring(0, index);
        }
        System.out.println(str);
    }

    @Test
    public void urlTest() throws IOException {
//       String url="http://10.117.156.31/jcms/content_updateLearnTime.jspx?rnd=0.600042396086699&contentId=599&userId=u_90031543&learnedCount=6&status=1";
        String url = "http://sdztc.shunde.gov.cn/QYZTCWS/policy.do?getPolicyHotList&start=0&limit=20";
        String result = HttpUtil.sendGet(url, "", "");
        System.out.println(result);
       /*int i=0;
       while (i<1000){
           HttpUtil.sendGet(url,"","");
           i++;
       }*/
        ;
    }

    //将用户数据加载到缓存中，并读取指定的值
//    @Test
    public void redisCacheUser() {
        List<User> userList = userService.allUser();
        HashMap<Object, Object> map = new HashMap<>();
        //遍历集合
        for (User user : userList) {
            if (!"".equals(user.getLoginName()) && user.getLoginName() != null) {
                map.put(user.getLoginName(), JSON.toJSONString(user));
            } else {
                System.out.println(user.toString());
            }
        }
        //将map放入缓存中
        redisUtil.hsetAll("totalUser", map);
    }

    @Test
    public void redisReadUser() {
        //从redis中读取指定的值
        String str = redisUtil.hget("totalUser", "13928288238");
        Map<Object, Object> map = redisUtil.hgetall("totalUser");
        System.out.println(str);

    }


    @Test
    public void redisClear() {
        redisUtil.del("DataListNew");
        Map<Object, Object> redis = redisUtil.hgetall("limitedIpMap");
        if (redis != null) {
            for (Map.Entry<Object, Object> entry : redis.entrySet()) {
                System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
            }
        }
    }

    @Test
    public void dataClear() {
        dataService.clear();
    }


    //    @Test
    public void testSql() {
        User user = new User();
        user.setIdentityNumber("9999");
        user.setIdentityType("123");
        user.setLoginName("32");
        user.setPhone("3");
        user.setIdentityType("mainland");
        user.setAccount("3");
        user.setAccountType("332");
        user.setClevel("3232");
        user.setCtype("000");
        user.setOrigin("545");
        user.setLoginType("45");
        user.setUid("32");
        user.setUversion("99999");
        //随机生成默认密码
        String password = UUID.randomUUID().toString();
        user.setPassword("94");
        user.setId("5419be3ce3624c94b6423a48644e0e11");
        userMapper.updateByIdentity(user);
    }

    //用户信息加载到缓存中
    public void userLoadCache() {
        //获取用户信息
        int startRow = 0;      //开始行
        int endRow = 1000;     //结束行
        int step = 1000;         //步长
        int endFlag = 1;          //循环结束标识
        while (endFlag != 0) {
            //从数据库获取用户信息
            List<User> users = userMapper.selectByLimit(startRow, endRow);
            //遍历集合
            for (User user : users) {
                //以phone为key
                redisUtil.hset("userPhone", user.getPhone(), user.toString());
                //以身份证号为key
                redisUtil.hset("userIdentity", user.getIdentityNumber(), user.toString());
            }
            //查询条件增加
            startRow = endRow;
            endRow += step;
            //重置循环条件
            endFlag = users.size();
        }

    }


    @Autowired
    private FingertipsService fingertipsService;

    //    @Value("${zhijianban.operation.url}")
    private String operUrl = "http://19.202.141.193:8080/sdbst/app/fingertips";

    @Test
    public void sendEMSTask() {
        List<FingertipsEMS> list = fingertipsService.getFingertipsEMSByNotEms();//获取所有没调用EMS接口的订单
        for (int i = 0; i < list.size(); i++) {
            FingertipsEMS fingertipsEMS = list.get(i);
            if (fingertipsEMS.getCaseId().equals("19346")) {
                Map map = new HashMap();
                map.put("caseId", fingertipsEMS.getCaseId());
                String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);

                JSONObject jsonObject = JSONObject.parseObject(string);

                if ("200".equals(jsonObject.getString("code"))) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String status = data.getString("status");
                    System.out.println("接口调用成功" + status);
                    fingertipsEMS.setCol1(status);
                    fingertipsEMS.setCol2((new Date()).toString());
                    fingertipsService.updateStatusById(fingertipsEMS);
                    if ("预受理".equals(status)) {
                        //揽收地址信息（上门取件地址）
                        JSONObject collect = JSONObject.parseObject(fingertipsEMS.getCollects());
                        //寄件人地址信息（面单使用）
                        JSONObject sender = JSONObject.parseObject(fingertipsEMS.getSender());
                        //收件人地址
                        JSONObject receiver = JSONObject.parseObject(fingertipsEMS.getReceiver());
                        JSONObject gotInfo = new JSONObject();
                        gotInfo.put("collect", collect);
                        gotInfo.put("sender", sender);
                        gotInfo.put("receiver", receiver);
                        gotInfo.put("orderType", 2);
                        gotInfo.put("remark", fingertipsEMS.getRemark());

                        gotInfo.put("txLogisticID", fingertipsEMS.getId());
                        gotInfo.put("custCode", "90000011275444");
                        fingertipsService.createOrder(gotInfo);
                    }
                }
            }

        }
    }


    @Value("${ems.url}")
    private String EMS_URL;
    @Value("${ems.appkey}")
    private String EMS_APPKEY;
    @Value("${ems.appsecret}")
    private String EMS_APPSECRET;
    @Value("${ems.authorization}")
    private String EMS_authorization;

    private String DEFAULTCHARSET = "UTF-8";
    @Value("${aes.key}")
    private String aesKey;

    @Test
    public void cancellation() {
//        int count = emsWaybillMapper.findByTxLogisticID(gotInfos.getString("txLogisticID"));
//        if(count > 0){
//            PointUtil.info("添加ems订单接口，txLogisticID不可重复："+gotInfos.getString("txLogisticID"));
//            return false;
//        }
        Map<String, String> params = new HashedMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        //构造必要参数
        params.put("timestamp", date);
        params.put("version", "V3.01");
        params.put("method", "ems.inland.waybill.got.cancellation");
        params.put("format", "json");
        params.put("app_key", EMS_APPKEY);
        params.put("authorization", EMS_authorization);
//        params.put("txLogisticID",gotInfos.getString("txLogisticID"));
        params.put("txLogisticID", "84d478ce42de400e84648c74c4bc3ad9");
        params.put("cancelCode", "28");
        params.put("charset", DEFAULTCHARSET);

        //合成签名
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
        stringBuilder.append(EMS_APPSECRET);

        //签名加密
        String sign = CreateEMSParamUtils.sign(stringBuilder.toString(), DEFAULTCHARSET);
        params.put("sign", sign);
        //参数排列并转为字符串
        String body = CreateEMSParamUtils.toKey(params);
        String result = null;
        try {
            //请求
            result = EMSHttpUtil.sendHttpPost(EMS_URL, body, DEFAULTCHARSET, null, "application/x-www-form-urlencoded; charset=UTF-8");
//            PointUtil.info("EMS上门取件结果："+result);
            System.out.println("EMS撤单结果：" + result);
            JSONObject resultObject = JSONObject.parseObject(result);
            if (resultObject.getString("success").equals("T")) {
//                addEMSInfo(gotInfos,1);
                System.out.println("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("false");
        }
    }

    @Test
    public void shenfen() throws Exception {
       //String json = "{\"account_type\":\"human\",\"link_person_cid\":\"510921199204195359\",\"link_person_name\":\"陈财\",\"mobile\":\"18688286060\",\"from\":\"ishunde\"}";

        String json = "{\"account_type\":\"human\",\"link_person_cid\":\"445381199407156317\",\"link_person_name\":\"李家升\",\"mobile\":\"18707676996\",\"from\":\"ishunde\"}";


        System.out.println(json);
//        String jsons = "{\"account\":\"a13690222131\", \"account_type\":\"corp\", \"cid\":\"91440605MA52NLFN4G\", \"ctype\":\"49\", \"level\":\"L1\", \"link_person_cid\":\"440603197711153472\", \"link_person_ctype\":\"10\", \"link_person_name\":\"陈玉伦\", \"login_type\":\"password\", \"mobile\":\"13690222131\", \"name\":\"广东思源政通科技有限公司\", \"origin\":\"gdbs\", \"uid\":\"485434b2b3604c4a915186170b5310b0\", \"uversion\":\"1.0\", \"ufrom\":\"ishunde\"}";
//        System.out.println(jsons);
        String authKey = EncryptUtil.aesEncrypt(json, aesKey);
        System.out.println(authKey);

//        System.out.println(" ---------------");
//        String  str = EncryptUtil.aesDecrypt("bb28e62cdbfc3e341610192a401f4f4bbeff7e9facfcb5daa5b6c0585f4e8abbcbd5c577e14d00d8b8a0ad06e0198c60a496c53dd989a8171a3d1778101a44f9e8322f3bfff05f5a4a2f7fa148e14bd81a72f1a646d3224e6b57749f125bc6304a9c2a464c87710dd9c389ba94c3ebbd6cad9deb43b9e52e195d0762c76ae7a69346353a6ea30fb9765854fcf2b40592", aesKey);
//        System.out.println(str);

    }

    @Autowired
    private ServerService serverService;
    @Autowired
    private FingertipsMessageUtils fingertipsMessageUtils;




    @Test
    public void jsonToString() {
        String info = "{\n" +
                "    \"userId\":\"441481199312051370\",\n" +
                "    \"deviceId\":\"7474\",\n" +
                "    \"alive\":\"1\",\n" +
                "    \"mobileType\":\"北京\",\n" +
                "    \"mobileTypeDetail\":\"中国\"\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(info);
        System.out.println(jsonObject.toString());
    }



    @Test
    public void readTxtTest() throws IOException, SftpException {
        String username = "s1UEdT5BVzVWHSGf0oOBAg==";
        int port = 22;
        String Path = "/home/file";
        String host = "RRuuEac8lJC7cIWbM3xyDfG2rjvMspPJ";
        String password = "ZPfbqLorL/cOxppz+UqhBqXQozvzSEbq";

//        获取服务器列表
        List<ServerInfo> list=serverService.getServerInfo(new ServerInfo());
        List<HardLog> hardLogList=new ArrayList<>();
//        ChannelSftp sftp = SftpUtil.connect(encryptor.decrypt(host), port, encryptor.decrypt(username), encryptor.decrypt(password));
        System.out.println(encryptor.decrypt(host));
        ChannelSftp sftp = SftpUtil.connect("19.202.141.219", port, "root", "kf7W3FCNbgXe");

        // 获取filename，写入list
        Vector<ChannelSftp.LsEntry> files = sftp.ls(Path);
        List<String> fileNamesList=new ArrayList<>();
        for (ChannelSftp.LsEntry lsEntry : files) {
            if (Pattern.matches(".*txt.*", lsEntry.getFilename())) {
                fileNamesList.add(lsEntry.getFilename().split("\\.")[0]);
            }
        }

        for(int i=0;i<list.size();i++){
            if (fileNamesList.contains(list.get(i).getIpNumber())){
                HardLog hard = SftpUtil.SftpReadTxt(list.get(i).getIpNumber()+"\\.txt", Path, sftp);
                hard.setCreateDate(new Date());
                hard.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                hard.setCol(list.get(i).getType().toString());
                hard.setIp(list.get(i).getIp());
                hard.setDelFlag(0);
                hardLogList.add(hard);
            }
        }
        try {
            SftpUtil.disconnect(sftp);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("hardLogList.size()"+hardLogList.size());
        int count=serverService.updateLog();
        if (count>=1){
            System.out.println("update" +count+"條數據");
        }
        for (int i=0;i<hardLogList.size();i++){
            serverService.insertLog(hardLogList.get(i));
        }
        list.clear();
        hardLogList.clear();
        files.clear();
        fileNamesList.clear();
    }

    @Test
    public void backUpLog() {
        serverService.getBackTime();
    }


    @Test
    public void payTest(){
//        String url = "https://mapi.alipay.com/gateway.do?&seller_id=2088421938331521&show_url=http%3A%2F%2Fwww.baidu.com";
//        System.out.println(url.substring(0,url.indexOf("show_url")));



        String LegalDepId = "01";
        String PlatNo = "iShunde";
        String MerchantDateTime = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        String OrderId = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        String MerchantId = "1234";
        String TermCode = "1111";
        String PayWaterFeeNo = "123";

        String Plain = "LegalDepId="+LegalDepId+"|PlatNo="+PlatNo+"|MerchantDateTime="+MerchantDateTime+"|OrderId="+OrderId+"|MerchantId="
                +MerchantId +"|TermCode="+TermCode+"|PayWaterFeeNo="+PayWaterFeeNo;
        System.out.println(Plain);
        System.out.println(SignatureService.sign(Plain));

        HashedMap map = new HashedMap();
        map.put("transName","iWaterOweQuery");
        map.put("Plain",Plain);
        map.put("Signature",SignatureService.sign(Plain));
        Gson gson = new Gson();
        String s = gson.toJson(map);
        System.out.println(s);
        System.out.println(Charset.defaultCharset());

        String result = HttpUtil.sendPost("http://test.sdebank.com:8527/pweb/iWaterOweQuery.do",s);
        System.out.println(result);


        System.out.println(SignatureService.verify(Plain,SignatureService.sign(Plain)));



    }

    @Test
    public void add(){
        String value1 = "0.00";
        String value2 = "0.00";
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        System.out.println(b1.add(b2).doubleValue());
    }


}
