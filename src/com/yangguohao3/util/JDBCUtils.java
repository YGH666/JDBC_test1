package com.yangguohao3.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Mr.Yang
 * @date 2020/02/24
 * @Description 获取数据库连接   阿里云  Druid数据库池实现
 **/
public class JDBCUtils {
    private static DataSource source;
    static {
        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src\\druid.properties"));
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnnection() throws Exception {

        Connection conn = source.getConnection();

        if (conn == null) {
            System.out.println("数据库连接失败");
        }else {
            System.out.println("数据库连接成功");
        }

        return conn;
    }

    public static void closeResourse(Connection conn,PreparedStatement ps){
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
    }

    public static void closeResourse(Connection conn,PreparedStatement ps,ResultSet rs){
//        try {
//            DbUtils.close(conn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            DbUtils.close(ps);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            DbUtils.close(rs);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }

}
