package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import rxframework.base.model.BaseEntity;

/**
 * @version V1.0
 * @title: EducationEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/2 9:52
 */
@Data
public class EducationEntity
        extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public static final String F_ZJNAME = "ZJNAME";

    private String zjname;
    public static final String F_JYJNAME = "JYJNAME";

    private String jyjname;
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