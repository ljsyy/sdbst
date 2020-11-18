package com.unifs.sdbst.app.bean.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @version V1.0
 * @title: VoNewsEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/9 10:06
 */
@Data
public class VoNewsEntity
        extends NewsEntity implements Serializable {
    private String Id;
    private String station_sh;
    private String station_name;
    private String fold_name;
    private String fold_id;
    private String bt;
    private String lx;
    private String bz;
    private String cjrq;
    private String cjsj;
    private String xgrq;
    private String xgsj;
    private String ly;
    private String zz;
    private String content;
    private String fn;
    private String href;
    private String my1;
    private String jsrq;
}