package com.itheima.controller;

import com.itheima.pojo.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 学习restful支持
 */
@Controller
public class RestController {

    /**
     * 访问rest列表页面:
     *      http://localhost:8080/item/list
     */
    @RequestMapping("item/list")
    public String list() {
        return "rest/list";
    }

    /**
     * 添加商品:
     *      http://localhost:8080/item/add
     */
    @RequestMapping(value = {"item/add"},method = {RequestMethod.POST})
    @ResponseBody
    public Item add(Item item) {
        return item;
    }

    /**
     * 修改商品:
     *      http://localhost:8080/item/1
     */
    @RequestMapping(value = {"item/{id}"},method = {RequestMethod.PUT})
    @ResponseBody
    public Item update(@PathVariable(name = "id") Integer id, Item item) {
        return item;
    }

    /**
     * 查询商品:
     *      http://localhost:8080/item/1
     *
     * 关于路径变量和@PathVariable注解:
     *  {id}: 路径变量(模板参数)
     *  @Pathariable:
     *      作用: 把路径变量的值绑定到方法的形参上
     *      该注解的写法:
     *          @PathVariable(name = "id")
     *          @PathVariable(value = "id")
     *          @PathVariable("id")
     *
     *  以下两种写法是有前提，路径变量的名称和形参的名称一样
     *      @PathVariable()
     *      @PathVariable
     */
    @RequestMapping(value = {"item/{id}"},method = {RequestMethod.GET})
    @ResponseBody
    public Item get(@PathVariable Integer id) {

        // 创建商品对象
        Item item = new Item();
        item.setId(id);
        item.setName("查询商品");

        return item;
    }

    /**
     * 删除商品:
     *      http://localhost:8080/item/1
     */
    @RequestMapping(value = {"item/{id}"},method = {RequestMethod.DELETE})
    @ResponseBody
    public Item del(Integer id) {

        // 创建商品对象
        Item item = new Item();
        item.setId(id);
        item.setName("删除商品");

        return item;
    }
}
