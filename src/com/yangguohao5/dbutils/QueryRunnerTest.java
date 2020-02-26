package com.yangguohao5.dbutils;

import com.yangguohao2.bean.Customer;
import com.yangguohao3.util.JDBCUtils;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Yang
 * @date 2020/02/26
 **/
public class QueryRunnerTest {
    @Test
    public void testInsert(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();

            conn = JDBCUtils.getConnnection();

            String sql = "insert into customer(name,email,birth)values(?,?,?)";
            int count = runner.update(conn, sql, "蔡徐坤", "caixukun@126.com", "1997-7-8");
            System.out.println("添加了"+count+"条记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    /**
     * BeanHandler使ResultSetHandler接口的实现类，用于封装表中的一条记录
     * 返回的是一个对象
     */
    @Test
    public void testQuery(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnnection();
            String sql = "select id,name,email,birth from customer where id = ?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(conn, sql, handler, 12);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    /**
     * BeanListHandler
     */
    @Test
    public void testQuery1(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnnection();
            String sql = "select id,name,email,birth from customer where id < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(conn, sql, handler, 12);
            list.forEach(s-> System.out.println(s));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    /**
     * Maphandler对于表中的一条记录，将表的字段作为key，值作为value
     */
    @Test
    public void testQuery3(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            QueryRunner runner = new QueryRunner();

            String sql = "select id,name,email,birth from customer where id = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = runner.query(conn, sql, handler, "10");
            System.out.println("map = " + map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    /**
     * MapListhandler对于表中的多条记录，将表的字段作为key，值作为value
     */
    @Test
    public void testQuery4(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            QueryRunner runner = new QueryRunner();

            String sql = "select id,name,email,birth from customer where id < ?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> mapList = runner.query(conn, sql, handler, "10");
            mapList.forEach(map-> System.out.println(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    /**
     * ScalarHandler用于查询特殊值
     */
    @Test
    public void testQuery5(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            QueryRunner runner = new QueryRunner();

            String sql = "select count(*) from customer where birth > ?";
            ScalarHandler handler = new ScalarHandler<>();
            Long count = (Long) runner.query(conn, sql, handler, "1950-1-1");
            System.out.println("count = " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @Test
    public void testQuery6(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            QueryRunner runner = new QueryRunner();

            String sql = "select id,name,birth,email from customer where id = ?";
            ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {
                /**
                 *
                 * @param rs  sql语句执行后返回的结果集
                 * @return
                 * @throws SQLException
                 */
                @Override
                public Customer handle(ResultSet rs) throws SQLException {
//                    return null;
//                    return new Customer(15,"爱哭的看","843513@1321.csa",new Date(1536451213));
                    if (rs.next()){
                        Customer customer = new Customer();
                        ResultSetMetaData metaData = rs.getMetaData();
                        int n = metaData.getColumnCount();
                        for (int i = 0; i < n; i++) {
                            Object value = rs.getObject(i + 1);
                            String name = metaData.getColumnLabel(i+1);
                            Field f = null;
                            try {
                                f = Customer.class.getDeclaredField(name);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            f.setAccessible(true);
                            try {
                                f.set(customer,value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        return customer;
                    }
                    return null;
                }
            };
            Customer c = runner.query(conn, sql, handler, 4);
            System.out.println("c = " + c);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }
}
