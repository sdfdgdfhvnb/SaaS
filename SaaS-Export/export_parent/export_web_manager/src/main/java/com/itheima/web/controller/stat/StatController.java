package com.itheima.web.controller.stat;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.stat.StatService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    // 注入service
    @Reference
    private StatService statService;

    /**
     * 进入统计分析页面
     * 请求地址:
     *     http://localhost:8080/stat/toCharts.do?chartsType=factory
     *     http://localhost:8080/stat/toCharts.do?chartsType=sell
     *     http://localhost:8080/stat/toCharts.do?chartsType=online
     * 响应页面：
     *     stat-factory.jsp
     *     stat-sell.jsp
     *     stat-online.jsp
     */
    @RequestMapping("toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-" + chartsType;
    }

    /**
     * 需求：统计生产厂家销售金额（货物、附件）
     */
    @RequestMapping("factorySale")
    @ResponseBody   // 自动把方法返回结果转换为json
    public List<Map<String,Object>> factorySale(){
        List<Map<String,Object>> result = statService.factorySale();
        return result;
    }

    /**
     * 商品销售排行榜
     */
    @RequestMapping("productSale")
    @ResponseBody       // 自动将数据转换为json格式
    public List<Map<String,Object>> productSale() {
        List<Map<String,Object>> result = statService.productSale(5);
        return result;
    }

    /**
     * 系统访问压力图
     */
    @RequestMapping("online")
    @ResponseBody       // 自动将数据转换为json格式
    public List<Map<String,Object>> online() {
        List<Map<String,Object>> result = statService.online();
        return result;
    }
}
