package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {
    ItemCat findItemCatById(Long ItemCatId);

	List<EasyUITree> findItemCat(Long parentId);


	List<EasyUITree> findItemCatCahe(Long parentId);
}
