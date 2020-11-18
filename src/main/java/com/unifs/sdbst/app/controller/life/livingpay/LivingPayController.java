package com.unifs.sdbst.app.controller.life.livingpay;

import com.alibaba.fastjson.JSONObject;
import com.sdcb.payment.client.SignatureService;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.livingPay.Agribusiness;
import com.unifs.sdbst.app.bean.life.livingPay.PayOrder;

import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.life.LivingPayService;
import com.unifs.sdbst.app.service.life.PayOrderService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.DateUtils;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import java.text.SimpleDateFormat;
/**
 * 生活缴费controller
 * @author ljs
 */
@Controller
@RequestMapping("/livingPay")
public class LivingPayController {


    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private LivingPayService livingPayService;

    @Autowired
    private UserService userService;

    @Value("${aes.key}")
    private String aesKey;

    @Value("${agribusiness.legalDepId}")
    private String legalDepId;

    @Value("${agribusiness.platNo}")
    private String platNo;

    /**
     * 农商银行卡支付商户号
     */
    @Value("${agribusiness.merchantId1}")
    private String merchantId1;

    /**
     * 银联卡支付商户号
     */
    @Value("${agribusiness.merchantId2}")
    private String merchantId2;


    @Value("${agribusiness.queryUrl}")
    private String queryUrl ;


    /**
     * 农商行交易查询
     * @param agribusiness 交易参数
     * @param queryType 查询交易类型
     * @return
     */
    @ResponseBody
    @RequestMapping("/payQuery")
    public Resp payQuery(Agribusiness agribusiness,
                           @RequestParam(name="queryType",required = true) String queryType ){
        Resp resp = null;
        try{
            System.out.println("queryType -- "+queryType);
            agribusiness.setLegalDepId(legalDepId);//法人机构Id
            agribusiness.setPlatNo(platNo);//平台编号
            agribusiness.setMerchantId(merchantId1);//商户号
            String dateStr = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
            agribusiness.setMerchantDateTime(dateStr);//商户日期时间
            agribusiness.setTermCode("");

            String payAddr = queryUrl;
            //接口url
            queryUrl = queryUrl+"/"+queryType+".do";
            System.out.println("--queryUrl--： "+queryUrl);
            agribusiness.setQueryUrl(queryUrl);
            agribusiness.setPayAddr(payAddr+"/iMPay.do");
            queryUrl = payAddr;
            //查询结果
            String result = livingPayService.queryResult(agribusiness,queryType);System.out.println("ffffffffffffff"+result);
            if(result.contains("Error")){
                System.out.println("error： "+result);
                resp = new Resp(RespCode.ERROR,result.substring(result.indexOf("Error>")+6,result.indexOf("</")));
            }else{
                //返回
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                String Plain = (String) jsonObject.get("Plain");
//                String Signature = (String) jsonObject.get("Signature");
//                System.out.println(Plain);
//                System.out.println(Signature);
//                boolean verify = SignatureService.verify(Plain, Signature);
//                System.out.println("verify : "+verify);
                resp = new Resp(RespCode.SUCCESS,result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 获取自助缴费参数
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPayParam")
    public Resp payQuery(@RequestBody String data,@RequestParam(name="userId",required = false) String userId){
        try{
            JSONObject jsonObject = JSONObject.parseObject(data);
            String resultData = (String) jsonObject.get("data");
            String payType = (String) jsonObject.get("payType");
            String AcctNo =  (String) jsonObject.get("AcctNo");
            if(StringUtils.isNullOrEmpty(AcctNo)){
                return new Resp(RespCode.ERROR,"卡号不能为空");
            }
            JSONObject jsonObject1 = JSONObject.parseObject(resultData);
            jsonObject1.put("AcctNo",AcctNo);

            System.out.println("fshpayType================:"+payType);
            System.out.println("fshjsonObject1================:"+jsonObject1);
            String payData = livingPayService.getPayData(jsonObject1, payType);
            System.out.println(payData);
            String sign = SignatureService.sign(payData);
            HashMap<String ,Object> map = new HashMap<>();
            map.put("Plain",payData);
            map.put("Signature",sign);
            System.out.println(sign);
            //生成订单 fsh
            PayOrder payOrder = new PayOrder();
           System.out.println("fshresultData================:"+payData);
           String ddbh="";
           String PayTrsCode="";
           String Fee="";
           if(payData.indexOf("|")>=0){
                String[] payDataArr=payData.split("\\|");
                for(String payDataArrStr:payDataArr){
                    if(payDataArrStr.indexOf("OrderId=")>=0){
                        ddbh=payDataArrStr.substring(payDataArrStr.indexOf("=")).replace("=","");
                        System.out.println("ddbh================:"+ddbh);
                    }
                    if(payDataArrStr.indexOf("PayTrsCode=")>=0){
                        PayTrsCode=payDataArrStr.substring(payDataArrStr.indexOf("=")).replace("=","");
                        System.out.println("PayTrsCode================:"+PayTrsCode);
                    }
                    if(payDataArrStr.indexOf("TransAmount=")>=0){
                        Fee=payDataArrStr.substring(payDataArrStr.indexOf("=")).replace("=","");
                        System.out.println("PayTrsCode================:"+PayTrsCode);
                    }
                }
           }
           payOrder.setDdbh(ddbh);
           payOrder.setUserId(userId);
           System.out.println("userId=========fsh===="+userId);
           payOrder.setCreateDate(new Date());//创建时间
           payOrder.setSourceType(PayTrsCode);
            payOrder.setStatus("0");
            payOrder.setFee(Fee);
           payOrder.setId(UUID.randomUUID().toString().replace("-",""));//id
           payOrderService.insert(payOrder);
            return new Resp(RespCode.SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有订单
     * @param link 订单查询条件
     * @param actionType 取值判断条件
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrderList")
    public ModelAndView getOrderList(@RequestParam(name="actionType",required = false) String actionType ,
                             @RequestParam(name = "link",required = false) String link
                             ){
        ModelAndView mav = new ModelAndView();
        PayOrder n_payOrder=new PayOrder();
        try{
            if(link != null && link.length()>0) {
                //用户信息
                String string = EncryptUtil.aesDecrypt(link, aesKey);
                JSONObject jsonObject = JSONObject.parseObject(string);
                String identity = (String)jsonObject.get("link_person_cid");
                String phone = (String)jsonObject.get("mobile");
                User user = userService.selectByFactor(phone, identity, null);
                if(user != null){
                    n_payOrder.setUserId(user.getId());
                }
            }
            n_payOrder.setUserId("");
            n_payOrder.setStatus("0");
            List<PayOrder> orderList = payOrderService.getOrderList(n_payOrder);
            List<PayOrder> newOrderList=new ArrayList<PayOrder>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            if(!CollectionUtils.isEmpty(orderList)){
                if("noFee".equalsIgnoreCase(actionType)){
                    //取未回设金额的订单
                    orderList = orderList.stream().filter(order -> {
                        return StringUtils.isNullOrEmpty(order.getFee());
                    }).collect(Collectors.toList());
                }
                String orderTime="";
                String newOrderTime="xxxx年xx月";
                for(PayOrder newPayOrder:orderList){
                    newOrderTime=sdf.format(newPayOrder.getCreateDate());
                    if("".equals(orderTime)||!newOrderTime.equals(orderTime)){
                        orderTime=newOrderTime;
                        newPayOrder.setTime(orderTime);
                    }else{
                        newPayOrder.setTime("");
                    }
                    newOrderList.add(newPayOrder);
                }
            }
            mav.addObject("orderList",newOrderList);
            mav.setViewName("app/livingpay/getOrderList");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 查询所有订单
     * @param id 用户信息
     * @param actionType 取值判断条件
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrderMess")
    public ModelAndView getOrderMess(@RequestParam(name="actionType",required = false) String actionType ,
                                     @RequestParam(name = "id",required = false) String id
    ){
        ModelAndView mav = new ModelAndView();
        PayOrder n_payOrder=new PayOrder();
        try{
            n_payOrder.setId(id);
            PayOrder order = payOrderService.getOrderMess(n_payOrder);
            System.out.println("ffffffffffffffffff");
            System.out.println(order.getDdbh());
            System.out.println(order.getCreateDate());
            System.out.println(order.getSourceType());
            System.out.println(order.getUserId());
            System.out.println(order.getStatus());
            List<PayOrder> orderList=new ArrayList<PayOrder>();
            orderList.add(order);
            mav.addObject("orderList",orderList);
            mav.setViewName("app/livingpay/getOrderMess");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }
    /**
     * 作页面跳转
     * @param pageType
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView jumpPage(@RequestParam(name = "type",required = false) String pageType,
                                 @RequestParam(name = "link",required = false) String link){
        ModelAndView mav = new ModelAndView();
        try{
            if(StringUtils.isNullOrEmpty(pageType)) return null;
            if(link != null && link.length()>0) {
                //用户信息
                String string = EncryptUtil.aesDecrypt(link, aesKey);
                JSONObject jsonObject = JSONObject.parseObject(string);
                //userMapper.selectByFactor(phone, identity, null);
                String identity = (String)jsonObject.get("link_person_cid");
                String phone = (String)jsonObject.get("mobile");
                User user = userService.selectByFactor(phone, identity, null);
                if(user != null){
                    mav.addObject("userId",user.getId());
                    System.out.println("fsh=====userId========"+user.getId());
                }
            }
            mav.setViewName("app/livingpay/"+pageType);

        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }



    /**
     * 页面回调
     * @param ddbh
     * @return
     */
    @RequestMapping("/nonTax/callbackPage")
    public ModelAndView nonTaxCallBackPage(@RequestParam(name="ddbh",required = false) String ddbh,
                                           @RequestParam(name="Plain",required = false) String Plain,
                                           @RequestParam(name="Signature",required = false) String Signature){
        System.out.println("fshPlain=="+Plain);
        System.out.println("fshSignature=="+Signature);
        ModelAndView mav = new ModelAndView();
        mav.addObject("ddbh",ddbh);
        Resp resp = null;
        String PlainStr="";
        String SignatureStr="";
        HashMap<String ,String> PlainMap = new HashMap<>();
        Plain="LegalDepId=01|PlatNo=iShunde|MerchantDateTime=20201016111440|OrderId=sd20201014173713|MerchantId=908441160000235|TermCode=|MerchantUrl=http%3A%2F%2Fsdbst.shunde.gov.cn%2Fsdbst%2FlivingPay%2FnonTax%2FcallbackPage|TranAbbr=MBER|PayTrsCode=iPayForEducationFee|TrsCode=iMPay|SchoolNo=0001|StudentPayeeNo=201930107|StudentName=测试学生7";

        try{
            //解析参数
            if(Plain!=null&&!"".equals(Plain)&&Plain.indexOf("|")>=0) {
                String[] PlainArr = Plain.split("\\|");
                for (String PlainArrStr : PlainArr) {
                    if (PlainArrStr.indexOf("=")>=0) {
                        //System.out.println("PlainArrStr=" + PlainArrStr);
                        PlainMap.put(PlainArrStr.substring(0, PlainArrStr.indexOf("=")), PlainArrStr.substring(PlainArrStr.indexOf("=")).replace("=",""));
                    }
                }
                System.out.println(PlainMap.get("OrderId"));
                if(!"".equals(PlainMap.get("OrderId"))){
                    PayOrder payOrder = new PayOrder();
                    payOrder.setStatus("1");
                    payOrder.setDdbh(PlainMap.get("OrderId"));
                    payOrderService.updateStatusByDdbh(payOrder);
                    mav.addObject("OrderId",PlainMap.get("OrderId"));

                    PayOrder n_payOrder=new PayOrder();
                    n_payOrder.setDdbh(PlainMap.get("OrderId"));
                    PayOrder order = payOrderService.getOrderMess(n_payOrder);
                    List<PayOrder> orderList=new ArrayList<PayOrder>();
                    orderList.add(order);
                    mav.addObject("orderList",orderList);
                    mav.addObject("payStatus","1");
                }
                //mav.addObject("PayTrsCode",PlainMap.get("PayTrsCode"));
                mav.setViewName("app/livingpay/getOrderMess");
                //mav.setViewName("app/livingpay/PayForBack");
            }else{
                mav.setViewName("app/livingpay/nonTaxCallBack");
            }
        }catch(Exception e){
            e.printStackTrace();
            //resp = new Resp(RespCode.WARN);
        }
        return mav;
    }

    /**
     *缴费结果回调
     * 参数是银行方面规定
     * @param jylx
     * @param status
     * @param time
     * @param ddbh
     * @param sign
     * @return
     */
    @ControlLog(operateType = "/pay/result", context = "建行后台缴费结果回调")
    @ResponseBody
    @RequestMapping("/pay/result")
    public String nonTaxResult(@RequestParam(name="jylx",required = false) String jylx,
            @RequestParam(name="status",required = false) String status,
            @RequestParam(name="time",required = false) String time,
                               @RequestParam(name="ddbh",required = false) String ddbh,
                               @RequestParam(name="sign",required = false) String sign){
        //abb1c8ccfa7d24215aa63e095c5f84c772020090411003101
        try {
            PayOrder payOrder = new PayOrder();

            String c = ddbh.substring(0,1);
            if("a".equalsIgnoreCase(c)){
                    payOrder.setStatus(status);
                    payOrder.setDdbh(ddbh);
                    payOrder.setTime(time);//回调时间戳
                    payOrder.setSign(sign);
                    payOrder.setJylx(jylx);
                    payOrder.setId(UUID.randomUUID().toString().replace("-",""));//id
                    payOrder.setCreateDate(new Date());//创建时间
                    payOrder.setClient("ios");
                    if(ddbh.endsWith("a")) {
                        ddbh = ddbh.substring(0,ddbh.length()-1);
                        payOrder.setClient("android");
                    }
                    payOrder.setUserId(ddbh.substring(1,ddbh.length()-16));//用户id
                    payOrder.setPlatform(ddbh.substring(ddbh.length()-2,ddbh.length()-1));//付款方式
                    payOrder.setPreJylx("1".equals(ddbh.substring(ddbh.length()-1))?"03021":"03023");
                    payOrder.setSourceType("nonTax");
            }
            //更新
            payOrderService.insert(payOrder);
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /**
     *修改订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateOrder")
    public Resp updateOrder(PayOrder n_payOrder){
        Resp resp = null;      //返回对象声明
        try{
            //通过订单编号查询
            List<PayOrder> payOrderList = payOrderService.getOrderList(n_payOrder);
            //更新
            if(!CollectionUtils.isEmpty(payOrderList)){
                PayOrder payOrder = payOrderList.get(0);
                payOrder.setFee(n_payOrder.getFee());
                payOrderService.update(payOrder);
                resp = new Resp(RespCode.SUCCESS);
            }
        }catch(Exception e){
            e.printStackTrace();
            resp = new Resp(RespCode.WARN);
        }
        return resp;
    }

    /**
     *修改订单 fsh
     * @return
     */
    @RequestMapping("/getcalll")
    public String payCallBack(@RequestParam(name="Plain",required = false) String Plain,
                            @RequestParam(name="Signature",required = false) String Signature){
        Resp resp = null;
        System.out.println("Plain="+Plain);
        System.out.println("Signature="+Signature);
        try{


        }catch(Exception e){
            e.printStackTrace();
            //resp = new Resp(RespCode.WARN);
        }
        return "app/";
    }


}
