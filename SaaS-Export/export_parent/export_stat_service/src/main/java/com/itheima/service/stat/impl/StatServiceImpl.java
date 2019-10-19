package com.itheima.service.stat.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.stat.StatDao;
import com.itheima.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {

    // 注入dao
    @Autowired
    private StatDao statDao;

    /**
     * 需求：生产厂家销售统计 (货物)
     */
    @Override
    public List<Map<String, Object>> factorySale() {
        return statDao.factorySale();
    }

    /**
     * 商品销售排行榜
     *
     * @param top
     */
    @Override
    public List<Map<String, Object>> productSale(int top) {
        return statDao.productSale(top);
    }

    /**
     * 系统访问压力图
     */
    @Override
    public List<Map<String, Object>> online() {
        return statDao.online();
    }
}
