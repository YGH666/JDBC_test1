package com.yangguohao2.DAO;

import com.yangguohao2.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 **/
public interface CustomerDAO {
    void insert(Connection conn, Customer cust);

    void deleteById(Connection conn,int id);

    void update(Connection conn,Customer cust);

    Customer getCustomerById(Connection conn,int id);

    List<Customer> getAll(Connection conn);

    Long getCount(Connection conn);

    Date getMaxDate(Connection conn);
}
