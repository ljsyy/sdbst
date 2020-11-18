package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import rxframework.base.model.BaseEntity;
import rxframework.persistent.annotation.FieldMapperAnnotation;
import rxframework.persistent.annotation.TableMapperAnnotation;
import rxframework.persistent.annotation.UniqueKeyType;

/**
 * @version V1.0
 * @title: SmzcTerminalEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/31 15:51
 */

@TableMapperAnnotation(tableName = "T_SMZC_TERMINAL", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "ID")
@Data
public class SmzcTerminalEntity extends BaseEntity {
    public static final String F_TOWN_ID = "TOWN_ID";
    public static final String F_SERVICE_POINT = "SERVICE_POINT";
    public static final String F_ADDRESS = "ADDRESS";
    public static final String F_LNG = "LNG";
    public static final String F_LAT = "LAT";
    public static final String F_CONTACTS = "CONTACTS";
    public static final String F_MOBILE = "MOBILE";
    public static final String F_TERMINAL_NO = "TERMINAL_NO";
    public static final String F_TERMINAL_TYPE = "TERMINAL_TYPE";
    public static final String F_OPENING_HOURS = "OPENING_HOURS";
    public static final String F_REMARK = "REMARK";
    @FieldMapperAnnotation(dbFieldName = "TOWN_ID", jdbcType = JdbcType.VARCHAR)
    private String townId;
    @FieldMapperAnnotation(dbFieldName = "SERVICE_POINT", jdbcType = JdbcType.VARCHAR)
    private String servicePoint;
    @FieldMapperAnnotation(dbFieldName = "ADDRESS", jdbcType = JdbcType.VARCHAR)
    private String address;
    @FieldMapperAnnotation(dbFieldName = "LNG", jdbcType = JdbcType.FLOAT)
    private Float lng;
    @FieldMapperAnnotation(dbFieldName = "LAT", jdbcType = JdbcType.FLOAT)
    private Float lat;
    @FieldMapperAnnotation(dbFieldName = "CONTACTS", jdbcType = JdbcType.VARCHAR)
    private String contacts;
    @FieldMapperAnnotation(dbFieldName = "MOBILE", jdbcType = JdbcType.VARCHAR)
    private String mobile;
    @FieldMapperAnnotation(dbFieldName = "TERMINAL_NO", jdbcType = JdbcType.VARCHAR)
    private String terminalNo;
    @FieldMapperAnnotation(dbFieldName = "TERMINAL_TYPE", jdbcType = JdbcType.VARCHAR)
    private String terminalType;
    @FieldMapperAnnotation(dbFieldName = "OPENING_HOURS", jdbcType = JdbcType.VARCHAR)
    private String openingHours;
    @FieldMapperAnnotation(dbFieldName = "REMARK", jdbcType = JdbcType.VARCHAR)
    private String remark;
}
