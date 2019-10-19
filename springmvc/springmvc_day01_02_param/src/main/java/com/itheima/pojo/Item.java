package com.itheima.pojo;

import java.util.Date;

/**
 * 商品实体类
 */
public class Item {

    private Integer id;
    private String name;
    private Float price;
    private Date createtime;
    private String pic;
    private String detail;

    public Item() {
    }

    public Item(Integer id, String name, Float price, Date createtime, String pic, String detail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createtime = createtime;
        this.pic = pic;
        this.detail = detail;
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
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createtime=" + createtime +
                ", pic='" + pic + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
