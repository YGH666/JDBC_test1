package com.yangguohao.transaction;

import com.yangguohao.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 *
 * 数据库事务：一组逻辑操作单元(1或多个DML操作)，使数据从一种状态变换到另一种状态（要么都执行，要么都不执行）
 * 1.自己获取连接
 * 2.设置自动提交conn.setAutoCommit(false)
 * 3.将连接传入DML方法
 * 4.等事务处理完后commit
 * 5.若出现异常，再catch内rollback
 * 6.关闭连接
 **/
public class TransactionTest {
    public static void main(String[] args) throws Exception {
        TransactionTest test = new TransactionTest();
//        test.TestUpdate();

        test.testSelect();
//        test.testTransactionUpdate();
    }

    //对于goods表两个用户转账，如果出错，无法回滚
    public void TestUpdate(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            conn.setAutoCommit(false);

            String sql1 = "update goods set money = money - 1000 where name = ?";
            update(conn,sql1,"AAA");

            System.out.println(10/0);

            String sql2 = "update goods set money = money + 1000 where name = ?";
            update(conn,sql2,"BBB");

            System.out.println("转账成功");
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResourse(conn,null);
        }
    }


    /**
     * 未考虑事务性的update
     * @version 1.0
     * @param sql
     * @param args
     * @return
     */
//    public int update(String sql,Object ...args){
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = JDBCUtils.getConnnection();
//
//            ps = conn.prepareStatement(sql);
//
//            for (int i = 0; i < args.length; i++) {
//                ps.setObject(i+1,args[i]);
//            }
//
////            ps.execute();
//            return ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.closeResourse(conn,ps);
//        }
//        return 0;
//    }

    /**
     * 考虑事务性的update
     * @version 1.1
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public int update(Connection conn,String sql,Object ...args){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

//            ps.execute();
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(null,ps);
        }
        return 0;
    }

    /**
     * 通用的查询操作
     * @param conn
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T getInstance(Connection conn,Class<T> clazz,String sql,Object ...args){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    String name = metaData.getColumnLabel(i+1);

                    Field f = clazz.getDeclaredField(name);
                    f.setAccessible(true);
                    f.set(t,value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(null,ps,resultSet);
        }
        return null;
    }

    public void testSelect() throws Exception{
        Connection conn1 = JDBCUtils.getConnnection();
        //数据库的隔离级别
        System.out.println(conn1.getTransactionIsolation());
        //设置
        conn1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        conn1.setAutoCommit(false);

        String sql = "select name,money from goods where name = ?";
        Goods aaa = getInstance(conn1, Goods.class, sql, "AAA");
        System.out.println(aaa);

    }

    public void testTransactionUpdate() throws Exception {
        Connection conn1 = JDBCUtils.getConnnection();
        conn1.setAutoCommit(false);

        String sql = "update goods set money = ? where name = ?";
        update(conn1, sql,5000, "AAA");

        Thread.sleep(15000);
    }


}
