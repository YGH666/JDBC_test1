package com.yangguohao.transaction;

/**
 * @author Mr.Yang
 * @date 2020/02/25
 **/
public class Goods {
    private String name;
    private int money;

    public Goods() {
    }

    public Goods(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
