package com.unifs.sdbst.app.bean.fever;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * FEVER_ROLE
 * @author 
 */
@Data
public class FeverRole implements Serializable {
    private String roleId;

    private String roleName;

    private Date roleCreateDate;

    private Date roleUpdateDate;

    private String remark;

    private static final long serialVersionUID = 1L;
}