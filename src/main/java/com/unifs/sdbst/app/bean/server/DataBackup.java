package com.unifs.sdbst.app.bean.server;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * DATA_BACKUP_LOG
 * @author 
 */
@Data
public class DataBackup implements Serializable {
    private String id;

    private Date backTime;

    private Date operateTime;

    private static final long serialVersionUID = 1L;
}