package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import rxframework.base.model.BaseEntity;
import rxframework.persistent.annotation.FieldMapperAnnotation;
import rxframework.persistent.annotation.TableMapperAnnotation;
import rxframework.persistent.annotation.UniqueKeyType;

/**
 * @version V1.0
 * @title: SmzcFunctionEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/31 16:06
 */
@TableMapperAnnotation(tableName = "T_SMZC_FUNCTION", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "ID")
@Data
public class SmzcFunctionEntity extends BaseEntity {
    public static final String F_FUN_NAME = "FUN_NAME";
    public static final String F_FUN_INTRODUCE = "FUN_INTRODUCE";
    public static final String F_BUSINESS_NO = "BUSINESS_NO";
    public static final String F_TERMINAL_TYPE = "TERMINAL_TYPE";
    public static final String F_FUN_SORT = "FUN_SORT";
    @FieldMapperAnnotation(dbFieldName = "FUN_NAME", jdbcType = JdbcType.VARCHAR)
    private String funName;
    @FieldMapperAnnotation(dbFieldName = "FUN_INTRODUCE", jdbcType = JdbcType.VARCHAR)
    private String funIntroduce;
    @FieldMapperAnnotation(dbFieldName = "BUSINESS_NO", jdbcType = JdbcType.VARCHAR)
    private String businessNo;
    @FieldMapperAnnotation(dbFieldName = "TERMINAL_TYPE", jdbcType = JdbcType.VARCHAR)
    private String terminalType;
    @FieldMapperAnnotation(dbFieldName = "FUN_SORT", jdbcType = JdbcType.INTEGER)
    private Integer funSort;
}