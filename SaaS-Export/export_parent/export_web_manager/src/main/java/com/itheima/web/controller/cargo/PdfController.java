package com.itheima.web.controller.cargo;

import com.itheima.web.controller.BaseController;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 打印控制器
 */
@Controller
@RequestMapping("/cargo/export")
public class PdfController extends BaseController {

    /**
     * 导出pdf报运单(1) 入门案例
     * http://localhost:8080/cargo/export/exportPdf.do?id=...
     */
    @RequestMapping("exportPdf1")
    public void exportPdf1() throws Exception {

        // 1.获取jasper文件流
        InputStream in =
                session.getServletContext().getResourceAsStream("/jasper/test01.jasper");

        // 2.创建JasperPrint对象,用于往模板中添加信息
         JasperPrint jasperPrint =
                 JasperFillManager.fillReport(in, new HashMap<>(), new JREmptyDataSource());

        // 3.导出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    /**
     * 导出pdf报运单(2) 中文字体问题
     * http://localhost:8080/cargo/export/exportPdf.do?id=...
     */
    @RequestMapping("exportPdf2")
    public void exportPdf2() throws Exception {

        // 1.获取jasper文件流
        InputStream in =
                session.getServletContext().getResourceAsStream("/jasper/test02.jasper");

        // 2.创建JasperPrint对象,用于往模板中添加信息
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(in, new HashMap<>(), new JREmptyDataSource());

        // 3.导出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    /**
     * 导出pdf报运单(3) 填充数据 map
     * http://localhost:8080/cargo/export/exportPdf.do?id=...
     */
    @RequestMapping("exportPdf3")
    public void exportPdf3() throws Exception {

        // 1.获取jasper文件流
        InputStream in =
                session.getServletContext().getResourceAsStream("/jasper/test03_parameter.jasper");

        // 创建一个map集合用来构建数据,注意:map的key要与设计模板时parameter的参数名称一样
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "张三");
        map.put("deptName", "行政部");
        map.put("email", "zhangsan@export.com");
        map.put("companyName", "阿里巴巴有限公司");

        // 2.创建JasperPrint对象,用于往模板中添加信息
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(in, map, new JREmptyDataSource());

        // 3.导出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    /**
     * 导出pdf报运单(4) 填充数据 数据源-jdbc
     * http://localhost:8080/cargo/export/exportPdf.do?id=...
     */
    // 注入datasource
    @Autowired
    private DataSource dataSource;

    @RequestMapping("exportPdf4")
    public void exportPdf4() throws Exception {

        // 1.获取jasper文件流
        InputStream in =
                session.getServletContext().getResourceAsStream("/jasper/test04_jdbc.jasper");

        // 2.创建JasperPrint对象,用于往模板中添加信息
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(in, new HashMap<>(), dataSource.getConnection());

        // 3.导出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
