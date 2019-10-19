package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;

    /**
     * 1. 货物列表
     * 功能入口：购销合同列表，点击货物
     * 请求地址：http://localhost:8080/cargo/contractProduct/list.do?contractId=4
     * 业务分析：
     * A. 根据购销合同id，查询货物
     * B. 查询货物生产厂家
     */
    @RequestMapping("list")
    public String list(String contractId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        // 1.根据购销合同查询id,查询货物
        ContractProductExample cPExample = new ContractProductExample();
        cPExample.createCriteria().andContractIdEqualTo(contractId);

        // 分页
        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(cPExample, pageNum, pageSize);

        // 2.查询厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        // 3.保存
        request.setAttribute("contractId", contractId);
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("factoryList", factoryList);

        // 返回
        return "cargo/product/product-list";
    }

    /**
     * 2.添加或者修改
     * <div class="col-md-2 title">货物照片</div>
     * <div class="col-md-4 data">
     * <input type="file" placeholder="请选择" name="productPhoto">
     * </div>
     */
    // @Autowired
    // private FileUploadUtil fileUploadUtil;
    @RequestMapping("edit")
    public String edit(ContractProduct contractProduct) {

        // 获取企业id和名称
        contractProduct.setCompanyId(getLoginCompanyId());
        contractProduct.setCompanyName(getLoginCompanyName());

        // 根据是否存在id判断是修改还是添加
        if (StringUtils.isEmpty(contractProduct.getId())) {

            // 保存
            contractProductService.save(contractProduct);

        } else {

            // 修改
            contractProductService.update(contractProduct);

        }

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }

    /**
     * 进入货物修改页面
     * http://localhost:8080/cargo/contractProduct/toUpdate.do?id=1
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id) {

        // 1.根据货物id查询
        ContractProduct contractProduct = contractProductService.findById(id);

        // 2.查询厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        // 3.保存
        request.setAttribute("factoryList", factoryList);
        request.setAttribute("contractProduct", contractProduct);

        // 4.返回
        return "cargo/product/product-update";
    }

    /**
     * 删除货物
     * 请求地址：http://localhost:8080/cargo/contractProduct/delete.do
     * 请求参数：
     * id          货物id        作用：删除
     * contractId  购销合同id     作用：跳转
     */
    @RequestMapping("delete")
    public String delete(String id, String contractId) {

        // 删除
        contractProductService.delete(id);

        // 跳转
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    /**
     * 进入上传页面
     * 请求地址: /cargo/contractProduct/toImport.do?contractId=
     */
    @RequestMapping("toImport")
    public String toImport(String contractId) {

        request.setAttribute("contractId", contractId);

        return "cargo/product/product-import";
    }

    /**
     * 上传文件
     * 请求地址: /cargo/contractProduct/import.do
     */
    @RequestMapping("import")
    public String importExcel(String contractId, MultipartFile file) throws Exception {

        //1. 根据上传的文件创建工作簿
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //2. 获取工作表
        Sheet sheet = workbook.getSheetAt(0);

        //3. 获取总行数
        int totalRows = sheet.getPhysicalNumberOfRows();

        // 获取企业
        String loginCompanyId = getLoginCompanyId();
        String loginCompanyName = getLoginCompanyName();

        //4. 第一行是标题，所以从第二行开始遍历
        for (int i = 1; i < totalRows; i++) {
            //5. 获取每一行
            Row row = sheet.getRow(i);

            //6. 获取每一行的每一列, 设置到对象属性中
            ContractProduct contractProduct = new ContractProduct();

            //空列	生产厂家	货号	数量	包装单位(PCS/SETS)	装率	箱数	单价	货物描述	要求
            contractProduct.setFactoryName(row.getCell(1).getStringCellValue());
            contractProduct.setProductNo(row.getCell(2).getStringCellValue());
            contractProduct.setCnumber((int) row.getCell(3).getNumericCellValue());
            contractProduct.setPackingUnit(row.getCell(4).getStringCellValue());
            contractProduct.setLoadingRate(row.getCell(5).getNumericCellValue() + "");
            contractProduct.setBoxNum((int) row.getCell(6).getNumericCellValue());
            contractProduct.setPrice(row.getCell(7).getNumericCellValue());
            contractProduct.setProductDesc(row.getCell(8).getStringCellValue());
            contractProduct.setProductRequest(row.getCell(9).getStringCellValue());

            // 设置购销合同id
            contractProduct.setContractId(contractId);

            // 设置企业
            contractProduct.setCompanyId(loginCompanyId);
            contractProduct.setCompanyName(loginCompanyName);

            // 设置工厂id
            String factoryId =
                    factoryService.findFactoryNameById(contractProduct.getFactoryName());
            contractProduct.setFactoryId(factoryId);

            //7. 调用service，保存货物
            contractProductService.save(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }


/*public Object getCellValue(Cell cell)  {
 *//**
 * 获取单元格的类型
 * <p>
 * 判断
 *//*
        CellType type = cell.getCellType();

        Object result = null;

        switch (type) {
            case STRING:{
                result = cell.getStringCellValue();//获取string类型数据
                break;
            }
            case NUMERIC:{
                *//**
 * 判断
 *//*
                if(DateUtil.isCellDateFormatted(cell)) {  //日期格式
                    result = cell.getDateCellValue();
                }else{
                    //double类型
                    result = cell.getNumericCellValue(); //数字类型
                }
                break;
            }
            case BOOLEAN:{
                result = cell.getBooleanCellValue();//获取boolean类型数据
                break;
            }
            default:{
                break;
            }
        }

        return  result;
    }*/
}
