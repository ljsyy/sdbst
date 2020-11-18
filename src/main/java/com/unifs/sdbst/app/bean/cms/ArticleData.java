/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.cms;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 文章Entity
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Data
public class ArticleData extends DataEntity<ArticleData> {

    private static final long serialVersionUID = 1L;
    private String id;        // 编号
    private String content;    // 内容
    private String copyfrom;// 来源
    private String relation;// 相关文章
    private String allowComment;// 是否允许评论

    private Article article;

    public ArticleData() {
        super();
        this.allowComment = "1";
    }

    public ArticleData(String id) {
        this();
        this.id = id;
    }


}