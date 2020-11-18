package com.unifs.sdbst.app.bean.fever;

import java.io.Serializable;
import lombok.Data;

/**
 * FEVER_MENU
 * @author 
 */
@Data
public class FeverMenu implements Serializable {
    private String menuId;

    private String menuName;

    private String menuUrl;

    private static final long serialVersionUID = 1L;
}