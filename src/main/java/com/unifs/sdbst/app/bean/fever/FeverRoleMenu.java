package com.unifs.sdbst.app.bean.fever;

import java.io.Serializable;
import lombok.Data;

/**
 * FEVER_ROLE_MENU
 * @author
 */
@Data
public class FeverRoleMenu implements Serializable {
    private String roleId;

    private String menuId;

    private static final long serialVersionUID = 1L;

    public FeverRoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
