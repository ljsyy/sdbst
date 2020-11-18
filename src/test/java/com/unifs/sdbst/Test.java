package com.unifs.sdbst;

import com.unifs.sdbst.app.utils.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version V1.0
 * @title: Test
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/12 15:01
 */
public class Test {
    @org.junit.Test
    public void test() {
        /*for (int i = 0; i < 10000; i++) {
            System.out.println(IdGen.uuid());
        }*/
        System.out.println(IdGen.randomLong());
    }



    @org.junit.Test
    public void passwordEncryptor() throws Exception {
        /*String key = "shunde8180buffet";
        String s = "{\"mobile\":\"18688289005\",\"name\":\"ceshi\"}";
//        String s = "subsidySystem|" + System.currentTimeMillis();
        System.out.println(s);
        System.out.println(AES.encrypt(s, key));
*/
        String s1="7e6a1aad533bca279e0163cb658da530aebdcc1b23c4c1f084514b51566ad5b570f3230dd059438c051bebf8c101699d2d7dddabab4bcec5d71ea8f98a0022b5c0a0a17d1ffdb09769de508d7e19768ec5c9ae8b97ff240e294c5c5aff8e375eabecbe9a11b5c2826c45b76b42421297df63173d4a090c51291e955ea26d3243";
        String s2="{\"phone\":\"15981921069\",\"code\":\"123\",\"token\":\"93889541d23d4be486375cc99f4a9565\",\"loginFlag\":\"android\"}";
        String key1="reservefoshan224";
        System.out.println(AES.decrypt(s1,key1));
//        System.out.println(AES.encrypt(s2,key1));
    }

    @org.junit.Test
    public void deskey() throws Exception {
        String key = "shundesubsidy043";
        String s = "f78dd28f8566b9061ef20f0e49828244700c019c6d7639833408070cc01d54e44a50c0bfb0fcdb4bc9262d81d6ea8f6f41e9bb73fa9e6fe98469b7a88524fb26708bf99d4589d6a5cca918ba6ba5fcbfcdb1204fc86167b202a637c175fd943c528f28361a7449b7088570d1bd88f2a6099fcb21cfadfa51853a4e2c33f2681e309b5bf2bc6d98ab1c8e82135e4787f7ed81ef08faaedcac88c0682d57eb8f00d25207d077d1022d2f5d50a830dd4be4b930dcd55ef9e15d6d973a70cb9732954d5933c45e223c0b8da49ef36f2988a5261407c12293fc892a9e1cd1bba9fb7b6dafd3331a0f1963936705726e8cb58bc3d5e49d05de9f20ba084cadfcfce12e2eaff67aa4052010f2d380cb37d53caaf1547b639aa1984d58cd63057d93a7fde0ef9583b236fda7a5da2c86c9b2a366ff80ee7d9e59191f0c8ddd7afd1eaeac2d64ac232fa44723f740d960959bdc72a264b53480e70e687aba54f5fd7d62ba9da6419c6d21a30000a9bb1ff4ac43c5";
        String s1 = "{\"openId\":\"a13690222131\",\"phone\":\"18688289005\",\"identityNumber\":\"440603197711153471\",\"name\":\"ceshi\",\"usersource\":\"isdapp\"}";
        System.out.println(AES.decrypt(s, key));

    }


    @org.junit.Test
    public void testUrl() {
        for (int i = 0; i < 20; i++) {
            StringBuffer param = new StringBuffer("systemid=dsdsads232&carNumber=1234&name=测试&phone=18688281234&age=33&sex=女&townId=11&townName=1234&siteId=1234234132&siteName=测试&idCard=234324324324324&temperature=33&opermeasure=转送定点医院&zjzl=证件&mz=汉&cfd=出发地&mdd=目的地&jcs1=是&jcs1xxqk=详细情况说明&jcs2=是&jcs2xxqk=详细情况说明2&sffr=是&zgtw=33&sfqtzz=无&sfgrbd=否&qtzzxl=发力&rq1=2020-02-15 11:22:33&hsd1=护送&jsd1=接收&jsr1lxdh=18688828888&jsr1xm=姓名&operType=XZ&txrq=2020-02-15 11:22:34&sccjsj=2020-02-15 11:22:35&opermeasure=酒店集中隔离&rq2=2020-02-15 11:22:36&hsd2=点&jsd2=接收2&jsr2xm=姓名2&jsr2lxdh=接收2&ysjsdw=单位&gh=234&ysjsr=移送接收人&jsrlxfs=联系方式&ywryqm=签名1&ywrydh=电话&dbmjqm=签名2&zbmjdh=电话2&zbmjqrrq=2020-02-15 11:22:37&sfbh=是&zzsbsj=2020-02-15 11:22:38&rylb=01&lhjyzdlx=99&bz=备注&zxzt=1&djsj=2020-02-10 11:22:13&xgsj=2020-02-10 11:22:14&djrId=登记&xgrId=修改&remark1=1&remark=2&remark3=3&remark4=4&remark5=5&remark6=6&remark7=7&remark8=8&remark9=9&remark10=10&remark11=11&remark12=12&remark13=13&remark14=14&remark15=15&remarkDate1=2020-02-15 11:21:38&remarkDate2=2020-02-16 11:22:38&remarkDate3=2020-02-15 12:22:38&remarkDate4=2020-02-15 11:23:38&remarkDate5=2020-02-15 11:24:38");
            long time = System.currentTimeMillis() / 1000;
            String keyCode = "fslt_fcgj_interface18688281234" + time;
            String key = DigestUtils.shaHex(keyCode);
            param.append("&authorization=" + key + "&timestamp=" + time);
//        authorization=84bb90f8a0ae8b88e0767de67e42ae97565e877c&timestamp=1582000512& systemid=adfssdfdsafasfasfasdfdsfas&  +"&systemid="+UUID.randomUUID().toString().replaceAll("-","")
            long time1 = System.currentTimeMillis();
            String result = HttpUtil.sendPost("http://sdbst2.shunde.gov.cn:8010/sdbst/data/police/fcgjInterface", param.toString());
            long time2 = System.currentTimeMillis();
            System.out.println(time2 - time1);
        }
    }

    public void testRequset() {
        String url = "http://isd3.shunde.gov.cn/sdbst/app/menu/homeMenuData?article.posid=1&startUp=1&type=0";

        while (true) {
            Long startTime = System.currentTimeMillis();
            HttpUtil.sendGet(url, null, "utf-8");
            Long entTime = System.currentTimeMillis();
            System.out.println(entTime - startTime);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void testYaml() {
        String path = this.getClass().getClassLoader().getResource("sharding.yml").getPath();
        System.out.println(path);
        File yamlFile = new File(path);
    }


}
