package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// 测试类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {

    @Autowired
    //private CompanyService companyService;

    @Test
    public void findByPage() {

        int pageNum=1;
        int pageSize=2;
        //PageInfo<Company> pageInfo = companyService.findByPage(pageNum, pageSize);
        //System.out.println(pageInfo);
    }

}
