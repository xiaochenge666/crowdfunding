package com.crowd.entity;

public class Auth {
    private Integer id;

    private String name;

    private String title;

    private Integer pid;

    private Boolean isAssign = false;//是否被分配,默认是未分配

    public Boolean getAssign() {
        return isAssign;
    }

    public void setAssign(Boolean assign) {
        isAssign = assign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj!=null){
            return this.getId().equals(((Auth) obj).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", isAssign=" + isAssign +
                '}';
    }
}