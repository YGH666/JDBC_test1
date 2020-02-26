package com.yangguohao3.impl;

import com.yangguohao2.bean.Customer;
import com.yangguohao3.DAO.BaseDAO;
import com.yangguohao3.DAO.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 **/
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customer(name,email,birth)values(?,?,?)";
        update(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql ="delete from customer where id = ?";
        update(conn,sql,id);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql ="update customer set name = ?,email = ?,birth = ? where id = ?";
        update(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id,name,birth,email from customer where id = ?";
        Customer c = getInstance(conn, sql, id);
        return c;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,birth,email from customer";
        List<Customer> list = getForList(conn, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customer";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxDate(Connection conn) {
        String sql = "select max(birth) from customer";
        return getValue(conn,sql);
    }
}
