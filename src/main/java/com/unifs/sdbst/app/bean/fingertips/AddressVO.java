package com.unifs.sdbst.app.bean.fingertips;

import lombok.Data;

@Data
public class AddressVO {

    private String city;//市区划编码
    private String cityName;//市名称
    private String detail;//详细地址
    private String district;//区区划编码
    private String districtName;//区名称
    private String province;//省区划编码
    private String provinceName;//省名称

}
