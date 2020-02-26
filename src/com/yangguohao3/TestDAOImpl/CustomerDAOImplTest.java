package com.yangguohao3.TestDAOImpl;

import com.yangguohao3.util.JDBCUtils;
import com.yangguohao2.bean.Customer;
import com.yangguohao3.impl.CustomerDAOImpl;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 **/
public class CustomerDAOImplTest {

    CustomerDAOImpl dao = new CustomerDAOImpl();

    @org.junit.Test
    public void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            Customer c = new Customer(1, "哀伤的", "aishangde@la.csl", new Date(1513421451L));
            dao.insert(conn,c);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();

            dao.deleteById(conn,7);

            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void update() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            Customer customer = new Customer(4,"杨国豪","841219366@qq.com",new Date(45445121));
            dao.update(conn,customer);

            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void getCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            Customer c = dao.getCustomerById(conn, 11);

            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            List<Customer> all = dao.getAll(conn);
            all.forEach(msg-> System.out.println(msg));
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            Long count = dao.getCount(conn);

            System.out.println("共有"+count+"条数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }

    @org.junit.Test
    public void getMaxDate() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnnection();
            Date maxDate = dao.getMaxDate(conn);

            System.out.println(maxDate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(conn,null);
        }
    }
}