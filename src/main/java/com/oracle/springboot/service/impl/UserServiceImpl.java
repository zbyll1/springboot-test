package com.oracle.springboot.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.oracle.springboot.po.User;
import com.oracle.springboot.mapper.UserMapper;
import com.oracle.springboot.service.UserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author  张滨宇
 * @date  2022/3/28 11:56
 * @version 1.0
 */
@Service
@CacheConfig(cacheManager = "cacheManager",cacheNames = "redisCache")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    @Cacheable(key = "#id")
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

}
