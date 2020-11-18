package com.unifs.sdbst.app.exception;

import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.enums.RespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * @version V1.0
 * @title: MyExceptionHandler
 * @projectName BaseFrame
 * @description: 自定义异常处理类
 * @author： 张恭雨
 * @date 2019/4/25 16:20
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * 自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Resp handleMyException(MyException e) {
        Resp resp = new Resp(e);
        return resp;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Resp handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        resp.setMsg("数据库中已存在该记录");
        return resp;
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Resp handleSqlException(SQLException e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        resp.setMsg("sql异常！");
        return resp;
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Resp noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        resp.setMsg("没找到页面");
        return resp;
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public Resp validationException(org.springframework.validation.BindException e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        resp.setMsg(e.getMessage());
        return resp;
    }

    @ExceptionHandler(RequestLimitException.class)
    public Resp requestLimitException(RequestLimitException e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        resp.setMsg(e.getMessage());
        return resp;
    }

    @ExceptionHandler(Exception.class)
    public Resp handleException(Exception e) {
        log.error(e.getMessage(), e);
        Resp resp = new Resp(RespCode.ERROR);
        return resp;
    }
}
