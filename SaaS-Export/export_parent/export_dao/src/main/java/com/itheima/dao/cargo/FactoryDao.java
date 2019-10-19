package com.itheima.dao.cargo;

import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryDao {
	
	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Factory record);

	//条件查询
    List<Factory> selectByExample(FactoryExample example);

	//id查询
    Factory selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Factory record);

    // 根据名称查询
    List<Factory> findByFactoryName(String factoryName);
}