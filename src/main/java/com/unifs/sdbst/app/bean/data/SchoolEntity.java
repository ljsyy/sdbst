package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.jeecgframework.core.annotation.Excel;
import rxframework.base.model.BaseEntity;
import rxframework.persistent.annotation.FieldMapperAnnotation;

/**
 * @version V1.0
 * @title: SchoolEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/2 9:43
 */
@Data
public class SchoolEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public static final String F_ZJNAME = "ZJNAME";

    private String zjname;
    public static final String F_LEVELS = "LEVELS";

    private String levels;
    public static final String F_SCHOOL = "SCHOOL";

    private String school;
    public static final String F_ADDRESS = "ADDRESS";

    private String address;
    public static final String F_REMARK = "REMARK";

    private String remark;
    public static final String F_MOBILE = "MOBILE";

    private String mobile;
    public static final String F_JD = "JD";

    private String jd;
    public static final String F_WD = "WD";

    private String wd;

}

