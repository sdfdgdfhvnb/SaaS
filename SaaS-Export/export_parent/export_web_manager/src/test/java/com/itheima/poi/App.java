package com.itheima.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;

public class App {

    @Test
    public void write() throws Exception {
        //1.创建一个工作簿
        //Workbook wb = new HSSFWorkbook(); //处理excel2003版本,后缀.xls
        Workbook wb = new XSSFWorkbook();//处理excel2007及以上版本 ,后缀.xlsx
        //2.创建工作表sheet
        Sheet sheet = wb.createSheet("测试");
        //3.创建第二行
        Row row = sheet.createRow(1);
        //4.创建第二行第二列
        Cell cell = row.createCell(1);
        //5.设置单元格内容
        cell.setCellValue("传智播客");

        /**
         * 6. 设置样式
         */
        // 列宽
        sheet.setColumnWidth(1,20*256);
        // 行高
        row.setHeightInPoints(50);
        // 创建样式 -- 设置边框
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setBorderBottom(BorderStyle.THICK);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        // 设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 设置字体
        Font font = wb.createFont();
        font.setFontName("华文行楷");
        font.setFontHeightInPoints((short) 26);
        cellStyle.setFont(font);

        // 设置单元格样式
        cell.setCellStyle(cellStyle);

        //7.将excel保存到本地磁盘中
        FileOutputStream fos = new FileOutputStream("d:\\text.xlsx");
        wb.write(fos);
        fos.close();
    }

    @Test
    public void read() throws Exception {
        //1.根据excel文件加载工作簿
        Workbook wb = new XSSFWorkbook("D:\\text.xlsx");
        //2.读取第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        //3.获取第二行
        Row row = sheet.getRow(1);
        //4.获取第二行第二列
        Cell cell = row.getCell(1);
        //5.获取单元格内容
        String value = cell.getStringCellValue();
        System.out.println("第二行第二列：" + value);
        // 这里是有效数据行、有效数据列
        System.out.println("总记录行：" + sheet.getPhysicalNumberOfRows());
        System.out.println("总列数：" + row.getPhysicalNumberOfCells());

        wb.close();
    }
}
