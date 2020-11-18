package com.unifs.sdbst.app.bean.data;

import lombok.Data;

/**
 * @version V1.0
 * @title: FoldEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/1 14:31
 */
@Data
public class FoldEntity {
    private String fold_id;
    private String fold_name;
    private String fold_tab;
    private String fold_main_id;
    private String fold_father_id;
    private String fold_bz;
    private String fold_ExtUrl;
}