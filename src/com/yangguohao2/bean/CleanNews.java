package com.yangguohao2.bean;

/**
 * @author Mr.Yang
 * @date 2020/02/24
 **/
public class CleanNews {
    private int id;
    private String fMetaDescription;

    public CleanNews() {
    }

    public CleanNews(int id, String fMetaDescription) {
        this.id = id;
        this.fMetaDescription = fMetaDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfMetaDescription() {
        return fMetaDescription;
    }

    public void setfMetaDescription(String fMetaDescription) {
        this.fMetaDescription = fMetaDescription;
    }

    @Override
    public String toString() {
        return "CleanNews{" +
                "id=" + id +
                ", fMetaDescription='" + fMetaDescription + '\'' +
                '}';
    }
}
