package com.unifs.sdbst.app.utils;

import com.unifs.sdbst.app.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class ZFWDBHelper {
    @Value("${JDBC.ZFW.DRIVER}")
    private String driverName;
    @Value("${JDBC.ZFW.URL}")
    private String url;
    @Value("${JDBC.ZFW.USERNAME}")
    private String userName;
    @Value("${JDBC.ZFW.PASSWORD}")
    private String password;


    public Connection getConn()
            throws Exception {
        try {
            Class.forName(this.driverName);
            return DriverManager.getConnection(this.url, this.userName, this.password);
        } catch (Exception e) {
            throw new MyException("连接mysql出错",e);
        }
    }

    public ResultSet getRs(PreparedStatement pstmt)
            throws SQLException {
        return pstmt.executeQuery();
    }

    public void insert(PreparedStatement pstmt, String sql)
            throws SQLException {
        pstmt.executeUpdate(sql);
    }

    public void update(PreparedStatement pstmt, String sql)
            throws SQLException {
        pstmt.executeUpdate(sql);
    }

    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void begionTrans(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commitTrans(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void rollBackTrans(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
