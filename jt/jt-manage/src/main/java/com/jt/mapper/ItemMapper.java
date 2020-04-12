package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	@Select("SELECT * FROM tb_item ORDER BY updated DESC LIMIT #{start},#{rows}")
	List<Item> findItemByPage(int start, Integer rows);

	void deleteByIds(@Param("ids")Long[] ids);
	
}
