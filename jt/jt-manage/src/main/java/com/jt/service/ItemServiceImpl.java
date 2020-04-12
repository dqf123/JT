package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//查询总记录数
		int total = itemMapper.selectCount(null);
		//分页查询记录
		int start =(page-1)*rows;
		List<Item> items =
				itemMapper.findItemByPage(start,rows);
		return new EasyUITable(total,items);


	}



	/**
	 * 关于主键自增赋值问题
	 * 	说明:一般条件下,当数据入库之后,才会动态的生成主键
	 * 信息.
	 * 	如果在关联操作时,需要用到主键信息时,一般id为null.
	 * 需要动态查询!!!!
	 * 
	 * 思路1: 先入库   在查询 获取主键Id 98%正确
	 * 思路2: 先入库   动态回传主键信息 mysql配合MP实现
	 * 一般解决思路:需要开启mysql数据的主键回传配置.
	 */

	//需要添加事务控制
	@Transactional
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
		.setCreated(new Date())
		.setUpdated(item.getCreated());
		itemMapper.insert(item); //动态赋值Id

		//实现数据封装
		itemDesc.setItemId(item.getId()); //?????
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}
	@Transactional
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId())
		        .setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);

	}
	/**
	 * sql:delete from tb_item where id in(100,101,102)
	 */
	@Override
	@Transactional
	public void deleteItems(Long[] ids) {

		//itemMapper.deleteByIds(ids);

		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		
		itemDescMapper.deleteBatchIds(idList);
	}

	/**
	 * 方式1:
	 * 	Sql: update tb_item status = #{status},updated=#{date}
	 * 		 where id in (101,102)
	 * 	
	 * 方式2:MP
	 * 	entity:修改的数据
	 * 	updateWrapper:修改的条件
	 */
	@Override
	public void updateStatus(Long[] ids, Integer status) {

		Item item = new Item();
		item.setStatus(status)
		.setUpdated(new Date());
		List<Long> idList = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}



	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}








}
