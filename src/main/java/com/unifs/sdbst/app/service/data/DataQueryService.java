/**
 * DataQueryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unifs.sdbst.app.service.data;

public interface DataQueryService extends java.rmi.Remote {
    public String fileList(String sId) throws java.rmi.RemoteException;
    public String query(String sId, String type, String requestJson) throws java.rmi.RemoteException;
}
