package com.yangguohao.transaction;

import com.yangguohao.util.JDBCUtils;

import java.sql.Connection;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 **/
public class Tets {
    public static void main(String[] args) throws Exception {
        Connection conn = JDBCUtils.getConnnection();
        System.out.println("conn = " + conn);

    }
}
