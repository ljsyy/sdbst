package com.unifs.sdbst.app.bean.user;

import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

import java.util.Date;
/**
 　　* @description: 投诉意见实体 　
 　　* @return ${return_type}
 　　* @throws
 　　* @author 张恭雨
 　　* @date 2019/8/28 11:11
 　　*/
@Data
public class Advice extends DataEntity<Advice> {
    private String id;

    private String content;

    private String phone;

    private String versions;

    private Date createDate;

    private String type;

    private String userId;//用户id

    private String replyContent;//回复信息

    private String replyDate;//回复时间

}