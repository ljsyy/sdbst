package com.unifs.sdbst.app.bean.data;

import lombok.Data;

/**
 * @version V1.0
 * @title: ChildNavEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/28 15:32
 */
@Data
public class ChildNavEntity {
    private String id;
    private String name;
    private String xl = "t";
    private boolean hasChildren;
    private String url;
}
