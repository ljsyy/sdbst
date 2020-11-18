package com.unifs.sdbst.app.bean.menus.vo;

import com.unifs.sdbst.app.bean.cms.Article;
import lombok.Data;

/**
 * @version V1.0
 * @title: HomeMenuVo
 * @projectName sdbst
 * @description: 首页接口参数实体封装类
 * @author： 张恭雨
 * @date 2020/2/12 17:15
 */
@Data
public class HomeMenuVo {
    private Article article;
    private String infoType;
    private int pageIndex;
    private Integer pageSize;
    private String startUp;
    private String type;

}
