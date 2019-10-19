package com.itheima.dao.system;

import com.itheima.domain.system.Dept;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

// 测试类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class DeptDaoTest {

    @Autowired
    private DeptDao deptDao;

    @Test
    public void findAll() {
        List<Dept> deptList = deptDao.findAll("1");
        System.out.println(deptList);
    }

}
