package com.yangguohao3.DAO;

import com.yangguohao.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 * 封装了数据表的通用操作
 *
 **/
public abstract class BaseDAO<T> {

    private Class<T> clazz = null;

    {
        //获取BaseDAO子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //将类型参数化
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        //获取父类的泛型的实际类型参数
        Type[] actualTypeArguments = type.getActualTypeArguments();
        clazz = (Class) actualTypeArguments[0];
    }

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
    public T getInstance(Connection conn, String sql, Object ...args){
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
    public List<T> getForList(Connection conn, String sql, Object ...args){
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
