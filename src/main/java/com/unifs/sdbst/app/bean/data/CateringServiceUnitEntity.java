package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import rxframework.base.model.BaseEntity;

/**
 * @version V1.0
 * @title: CateringServiceUnitEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/5 16:41
 */
@Data
public class CateringServiceUnitEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public static final String F_UNIT_NAME = "UNIT_NAME";
    public static final String F_UNIT_ADDRESS = "UNIT_ADDRESS";
    public static final String F_DETAILED_ADDRESS = "DETAILED_ADDRESS";
    public static final String F_GRADE = "GRADE";
    public static final String F_LNG = "LNG";
    public static final String F_LAT = "LAT";
    public static final String F_POI = "POI";
    public static final String F_TOWN = "TOWN";
    public static final String F_CATEGORY = "CATEGORY";

    private String unitName;

    private String unitAddress;

    private String detailedAddress;

    private String grade;

    private String lng;

    private String lat;

    private String poi;

    private String town;

    private String category;
}
