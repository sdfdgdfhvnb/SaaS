package com.itheima.dao.cargo;

import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    // 根据部门id，查询当前部门及所有子部门的登录用户创建的购销合同
    List<Contract> selectByDeptId(String deptId);
}