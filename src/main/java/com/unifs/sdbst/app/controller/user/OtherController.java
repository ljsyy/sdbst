package com.unifs.sdbst.app.controller.user;

import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.UserOthers;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.OtherService;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version V1.0
 * @title: OtherController
 * @projectName sdbst
 * @description: 其他用户控制层
 * @author： 张恭雨
 * @date 2019/8/19 14:53
 */
@Api(value = "OtherController",description = "其他用户")
@RestController
@RequestMapping(value = "/app/otherUser")
public class OtherController {
    @Autowired
    private OtherService otherService;
    private Resp resp;

    /**
     　　* @description: 帐号绑定
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author 张恭雨
     　　* @date 2019/8/19 14:56
     　　*/
    @ApiOperation(value = "帐号绑定", notes = "200：成功")     //swagger2 接口说明注解
    @ControlLog(operateType = "/binding", context = "帐号绑定")       //日志记录注解
    @RequestMapping(value = "/binding",method = RequestMethod.POST)
    public Resp binding(UserOthers userOthers, HttpServletRequest request) throws Exception {
        String userId=CookieUtil.getCookie(request,"userId");
        //设置userId
        userOthers.setUserId(userId);
        otherService.save(userOthers);
        resp=new Resp(RespCode.SUCCESS);
        return resp;
    }
    
    /**
     　　* @description: 解绑
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author 张恭雨
     　　* @date 2019/8/20 10:18
     　　*/
    @ApiOperation(value = "解除绑定", notes = "200：成功")     //swagger2 接口说明注解
    @ControlLog(operateType = "/unBinding", context = "解除帐号绑定")       //日志记录注解
    @RequestMapping(value = "/unBinding",method = RequestMethod.POST)
    public Resp unBinding(String id){
        otherService.untie(id);
        resp=new Resp(RespCode.SUCCESS);
        return resp;
    }
    
    /**
     　　* @description: 更新绑定信息
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author 张恭雨
     　　* @date 2019/8/20 10:27
     　　*/
    @ApiOperation(value = "更新绑定信息", notes = "200：成功")     //swagger2 接口说明注解
    @ControlLog(operateType = "/update", context = "更新绑定信息")       //日志记录注解
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Resp update(UserOthers others) throws Exception {
        others.setPsd(EncryptUtil.entryptPassword(others.getPsd()));
        //更新帐号绑定信息
        otherService.update(others);
        resp=new Resp(RespCode.SUCCESS);
        return resp;
    }

    /**
     　　* @description: 获取当前用户的账号绑定集合
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author 张恭雨
     　　* @date 2019/8/20 10:45
     　　*/
    @ApiOperation(value = "获取帐号绑定集合", notes = "200：成功")     //swagger2 接口说明注解
    @ControlLog(operateType = "/list", context = "获取帐号绑定集合")       //日志记录注解
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Resp list(HttpServletRequest request){
        //获取当前登录用户对象
        String userId=CookieUtil.getCookie(request,"userId");
        List<UserOthers> others=otherService.getList(userId);
        resp=new Resp(RespCode.SUCCESS);
        if(others!=null){
            resp.setData(others);
        }
        return resp;
    }
}
