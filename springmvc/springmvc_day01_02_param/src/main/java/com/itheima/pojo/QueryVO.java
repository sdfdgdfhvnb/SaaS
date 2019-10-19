package com.itheima.pojo;

import java.util.List;

/**
 * pojo包装类型
 */
public class QueryVO {

    // 商品名称: <input type="text" name="item.name" value=""/>
    // 包装商品对象
    private Item item;

    // 包装商品集合List
    private List<Item> itemList;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
