package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jt.pojo.User;
@Mapper
public interface UserMapper {
      List<User>findAll();
}
