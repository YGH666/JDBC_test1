package com.yangguohao4;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author Mr.Yang
 * @date 2020/02/26
 **/
public class DruidTest {
    public static void main(String[] args) throws Exception {
        DruidTest d = new DruidTest();
        d.getConnection();
    }
    public void getConnection() throws Exception{
//        DruidDataSource source = new DruidDataSource();
        Properties pros = new Properties();
        FileInputStream is = new FileInputStream(new File("src\\druid.properties"));
        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();

        System.out.println("conn = " + conn);
    }
}
