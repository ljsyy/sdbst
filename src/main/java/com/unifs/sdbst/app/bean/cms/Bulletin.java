package com.unifs.sdbst.app.bean.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Bulletin {
    private String id;

    private String title;

    private String link;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private String delFlag;
}