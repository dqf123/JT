package com.jt.vo;
//vo:后台数据与页面中的JS交互

import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITable {
   private Integer total;  //记录总数
   private List<Item>rows;  //记录
}
