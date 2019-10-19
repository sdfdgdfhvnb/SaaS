package com.itheima.web.controller.cargo;

import com.itheima.vo.ContractProductVo;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.web.controller.BaseController;
import com.itheima.web.utils.DownloadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class OutProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    //1. 进入出货表打印页面
    @RequestMapping("print")
    public String print() {
        return "cargo/print/contract-print";
    }

    //2. 导出出货表： XSSFWorkbook普通导出
    @RequestMapping("printExcel")
    public void printExcel(String inputDate) throws Exception {
        //第一步：创建工作簿、创建工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("出货表");
        // 参考模板：出货表.xls， 设置每列列宽。
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 26 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 8 * 256);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));

        // 第二步： 创建第一行
        Row row = sheet.createRow(0);
        row.setHeightInPoints(36);
        Cell cell = row.createCell(1);
        cell.setCellStyle(this.bigTitle(workbook));
        //指定第一行标题行的内容:2012-08  --->  2012年8月份出货表
        String value = inputDate.replace("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(value);

        // 第三步： 创建第二行
        row = sheet.createRow(1);
        row.setHeightInPoints(26);
        String titles[] = new String[]{"客户", "订单号", "货号", "数量", "工厂",
                "工厂交期", "船期", "贸易条款"};
        for (int i=0; i<titles.length; i++){
            cell = row.createCell(i+1);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(this.title(workbook));
        }

        // 第四步： 调用service查询、导出数据行
        List<ContractProductVo> list =
                contractProductService.findByShipTime(getLoginCompanyId(), inputDate);
        if (list != null && list.size() > 0){
            int index = 2;
            for (ContractProductVo cp : list) {
                row = sheet.createRow(index++);
                row.setHeightInPoints(24);
                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(7);
                cell.setCellValue(
                        new SimpleDateFormat("yyyy-MM-dd").format(cp.getShipTime()));
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(this.text(workbook));
            }
        }

        // 第五步：导出下载
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        new DownloadUtil().download(bos,response,"出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
