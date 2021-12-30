package com.sixcake.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import com.sixcake.auth.constant.RedisConstant;
import com.sixcake.auth.service.ResourceService;
import cn.hutool.core.collection.CollUtil;

/**
 * 资源与角色匹配关系管理业务类
 *
 * @author sixCake 2021/12/28
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ResourceServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/service/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/service/user/currentUser", CollUtil.toList("ADMIN", "USER"));
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }


    @Override
    public void updateResourceRolesRelation(String resource, List<String> roles) {
        redisTemplate.opsForHash().put(RedisConstant.RESOURCE_ROLES_MAP, resource, roles);
    }
}
