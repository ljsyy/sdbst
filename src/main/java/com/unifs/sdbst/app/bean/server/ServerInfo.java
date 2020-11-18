package com.unifs.sdbst.app.bean.server;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * SERVER_INFO
 * @author 
 */
@Data
public class ServerInfo implements Serializable {
    private String id;

    private String ip;

    private String password;

    private BigDecimal type;

    private BigDecimal type2;

    private BigDecimal type3;

    private BigDecimal delFlag;

    private Integer count;

    private String remarks;

    private String ipNumber;

    private static final long serialVersionUID = 1L;
}