package com.oracle.springboot.mapper;

import com.oracle.springboot.po.User;

/**
 * @author  张滨宇
 * @date  2022/3/28 11:56
 * @version 1.0
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}