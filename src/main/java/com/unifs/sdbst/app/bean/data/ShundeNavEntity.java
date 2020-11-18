package com.unifs.sdbst.app.bean.data;

import lombok.Data;

import java.util.List;

/**
 * @version V1.0
 * @title: ShundeNavEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/28 15:30
 */
@Data
public class ShundeNavEntity {
    private String id;
    private String name;
    private List<ChildNavEntity> childNavList;
}
