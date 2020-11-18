package com.unifs.sdbst.app.bean.user;

import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

import java.util.Date;
/**
 　　* @description: 满意度实体
 　　* @param ${tags}
 　　* @return ${return_type}
 　　* @throws
 　　* @author 张恭雨
 　　* @date 2019/8/28 11:12 
 　　*/
@Data
public class Satisfaction extends DataEntity<Satisfaction> {
    private String id;

    private String title;

    private Long sort;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private Long countA;

    private Long countB;

    private Long countC;

    private Long countD;

    private Date createDate;

    private String delFlag;

}