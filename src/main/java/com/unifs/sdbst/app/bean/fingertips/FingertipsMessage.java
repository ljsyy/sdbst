package com.unifs.sdbst.app.bean.fingertips;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * APP_FINGERTIPS_MESSAGE
 * @author 
 */
@Data
public class FingertipsMessage implements Serializable {
    private String id;

    private String matterid;

    private String caseid;

    private Date casecreatedate;

    private String status;

    private Date statuschangetime;

    private Date messagetime;

    private BigDecimal messagestatu;

    private String name;

    private String phone;

    private String agentCertificatenum;

    private BigDecimal count;

    private String remark;

    private String col1;

    private String col2;

    private String col3;

    private String agentphone;

    private String caseName;

    private static final long serialVersionUID = 1L;
}