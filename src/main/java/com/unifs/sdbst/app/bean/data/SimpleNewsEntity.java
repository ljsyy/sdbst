package com.unifs.sdbst.app.bean.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @version V1.0
 * @title: SimpleNewsEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/9 15:12
 */
@Data
public class SimpleNewsEntity implements Serializable {
    private String Id;
    private String fold_id;
    private String bt;
    private String lx;
    private String cjrq;
    private String cjsj;
    private String ly;
    private String zz;
    private String content;
    private String href;
    private String my1;
}