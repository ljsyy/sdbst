//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.unifs.sdbst.app.base;

import lombok.Data;

import java.io.Serializable;
@Data
public class ServiceResult implements Serializable {
    private Integer errorCode = 0;
    private String errorMsg;
    private Object data;
    private Long total;
    private Integer status = 1;
}
