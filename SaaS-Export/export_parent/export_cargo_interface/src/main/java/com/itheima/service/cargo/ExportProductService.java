package com.itheima.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;

import java.util.List;

/**
 * 出口货物表接口
 */
public interface ExportProductService {

    ExportProduct findById(String id);

    void save(ExportProduct exportProduct);

    void update(ExportProduct exportProduct);

    void delete(String id);

    PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample,
                                       int pageNum, int pageSize);

    List<ExportProduct> findAll(ExportProductExample example);
}
