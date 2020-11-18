package com.unifs.sdbst.app.bean.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * SYS_HARD_LOG
 * @author 
 */
@Data
public class HardLog implements Serializable {
    private String id;

    private String ip;

    private String cpu;

    private String disk;

    private String memory;

    private Date createDate;

    private int delFlag;

    private String col;

    private String down;

    private String network;

    private String tomcat;

    private String redis;

    private static final long serialVersionUID = 1L;
}
