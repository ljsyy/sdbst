package com.unifs.sdbst.app.bean.fever;

import java.io.Serializable;
import lombok.Data;

/**
 * FEVER_USER_ROLE
 * @author 
 */
@Data
public class FeverUserRole implements Serializable {
    private String userId;

    private String roleId;

    private static final long serialVersionUID = 1L;
}