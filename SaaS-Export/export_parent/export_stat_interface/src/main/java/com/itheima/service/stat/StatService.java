package com.itheima.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {

    /**
     *  需求：生产厂家销售统计 (货物)
     */
    List<Map<String,Object>> factorySale();

    /**
     * 商品销售排行榜
     */
    List<Map<String,Object>> productSale(int top);

    /**
     * 系统访问压力图
     */
    List<Map<String, Object>> online();
}
