package com.pinyougou.pojo;

import java.io.Serializable;

/**
 * 品牌实体类
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 21:52
 **/
public class Brand implements Serializable{
    private Long id;
    private String name;
    private String firstChar;

    public Brand() {
    }

    public Brand(Long id, String name, String firstChar) {
        this.id = id;
        this.name = name;
        this.firstChar = firstChar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstChar='" + firstChar + '\'' +
                '}';
    }
}
