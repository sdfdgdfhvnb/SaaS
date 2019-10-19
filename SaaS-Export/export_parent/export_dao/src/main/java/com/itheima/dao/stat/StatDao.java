package com.itheima.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {

    /**
     * 生产厂家销售统计
     */
    List<Map<String, Object>> factorySale();

    /**
     * 商品销售统计排行
     */
    List<Map<String, Object>> productSale(int top);

    /**
     * 系统访问压力图
     */
    List<Map<String, Object>> online();
}
