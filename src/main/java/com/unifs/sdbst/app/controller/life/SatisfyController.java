package com.unifs.sdbst.app.controller.life;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.Satisfaction;
import com.unifs.sdbst.app.bean.life.SatisfyWinners;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.life.SatisfyWinnersService;
import com.unifs.sdbst.app.service.user.SatisfactionService;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import com.unifs.sdbst.app.utils.TypeTransformUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @title: SatisfyController
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/29 17:10
 */
@Controller
@RequestMapping(value = "/app/satisfy")
class SatisfyController {
    @Autowired
    private SatisfactionService satisfactionService;
    @Autowired
    private SatisfyWinnersService satisfyWinnersService;
    @Autowired
    private RedisUtil redisUtil;
    private Resp resp;

    //获取满意度调查题目
    @ControlLog(context = "获取满意度调查题目")
    @RequestMapping(value = "/getSatisfactionList")
    @ResponseBody
    public Resp getSatisfactionList() {
        List<Satisfaction> list = satisfactionService.findList(new Satisfaction());
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(list);
        return resp;
    }


    /**
     * 满意度调查提交
     * ids元素样式 205ee4b6c1f34db9a6178e37eaf32b51-A
     */
    @RequestMapping(value = "/satisfySubmit")
    @ResponseBody
    public Resp satisfySubmit(@RequestParam("ids[]") List<String> ids, SatisfyWinners winners, HttpServletRequest request) throws Exception {


        //满意度调查答案
        if (winners != null && ids != null && ids.size() > 0) {
            //判断手机号码是否已存在
            String phone = winners.getPhone();
            if (StringUtils.isNotBlank(phone)) {
                Integer count = satisfyWinnersService.countPhone(phone);
                //已存在
                if (count != null && count > 0) {
                    resp = new Resp(RespCode.DEFAULT);
                    resp.setMsg("手机号码已存在，提交失败！");
                    return resp;
                }

            } else {
                resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("手机号码为空，提交失败！");
                return resp;
            }

            String answer = "";
            //修改满意度调查答案A B C D的选择次数
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i).indexOf("-") >= 0) {
                    //拼接答案
                    if (i < ids.size() - 1) {
                        answer += ids.get(i) + ",";
                    } else {
                        answer += ids.get(i);
                    }
                    String[] ss = StringUtils.split(ids.get(i), "-");
                    if (ss.length == 2) {
                        Satisfaction satisfaction = new Satisfaction();
                        satisfaction.setId(ss[0]);
                        //判断选择的是A B C D
                        if ("A".equals(ss[1])) {
                            satisfaction.setCountA(1);
                        } else if ("B".equals(ss[1])) {
                            satisfaction.setCountB(1);
                        } else if ("C".equals(ss[1])) {
                            satisfaction.setCountC(1);
                        } else if ("D".equals(ss[1])) {
                            satisfaction.setCountD(1);
                        }
                        //修改ABCD的选择次数
                        satisfactionService.updateCount(satisfaction);
                    }
                }
            }
            winners.setAnswer(answer);
            winners.setAwardNum(-1);

            //获取登录名
            //获取user对象
            String userId = CookieUtil.getCookie(request, "userId");
            //判断userId是否存在
            if (!StringUtils.isEmpty(userId)) {
                //从redis中获取user对象
                String userStr = redisUtil.get(userId);
                Map userMap = JSON.parseObject(userStr);
                User user=(User) TypeTransformUtil.mapToObject(userMap, User.class);
                winners.setLoginName(user.getLoginName());
            }

            //保存
            satisfyWinnersService.save(winners);
            resp = new Resp(RespCode.SUCCESS);
            return resp;
        } else {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("答案不能为空！");
            return resp;
        }

    }
}