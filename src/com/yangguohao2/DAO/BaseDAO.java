package com.yangguohao2.DAO;

import com.yangguohao.util.JDBCUtils;
import javafx.beans.binding.ObjectExpression;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 * 封装了数据表的通用操作
 *
 **/
public abstract class BaseDAO {

    //增删改等无返回结果操作
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

    //查询返回一条数据
    public <T> T getInstance(Connection conn, Class<T> clazz, String sql, Object ...args){
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

    //查询返回多个数据
    public <T> List<T> getForList(Connection conn,Class<T> clazz, String sql, Object ...args){
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

            //创建集合
            ArrayList<T> list = new ArrayList<>();

            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    String name = metaData.getColumnLabel(i+1);

                    Field f = clazz.getDeclaredField(name);
                    f.setAccessible(true);
                    f.set(t,value);
                }
                list.add(t);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(null,ps,resultSet);
        }
        return null;
    }

    //查询特殊值
    public <E> E getValue(Connection conn, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            resultSet = ps.executeQuery();
            if (resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResourse(null,ps,resultSet);
        }
        return null;
    }
}
