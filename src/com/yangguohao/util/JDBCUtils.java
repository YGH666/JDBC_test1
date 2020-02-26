package com.yangguohao.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Mr.Yang
 * @date 2020/02/24
 * @Description 获取数据库连接
 **/
public class JDBCUtils {

    public static Connection getConnnection() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);

        conn = DriverManager.getConnection(url, user, password);

        if (conn == null) {
            System.out.println("数据库连接失败");
        }else {
            System.out.println("数据库连接成功");
        }

        return conn;
    }

    public static void closeResourse(Connection conn,PreparedStatement ps){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null){
                conn.close();
                System.out.println("关闭数据库连接");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResourse(Connection conn,PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null){
                conn.close();
                System.out.println("关闭数据库连接");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
