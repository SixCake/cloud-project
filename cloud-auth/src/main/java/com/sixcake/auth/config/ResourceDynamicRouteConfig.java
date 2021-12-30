package com.sixcake.auth.config;

import com.sixcake.auth.domain.dto.ResourceRoleRelationDto;
import com.sixcake.auth.service.ResourceService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 资源与角色权限动态配置类
 */
@Component
@Slf4j
public class ResourceDynamicRouteConfig {

    private String dataId = "auth-resource-role-relation";

    private String group = "DEFAULT_GROUP";

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Autowired
    private ResourceService resourceService;

    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String config = configService.getConfig(dataId, group, 5000);
            log.info("ResourceDynamicRouteConfig", config);
            publishNacosConfig(config);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    publishNacosConfig(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("dynamicRouteByNacosListener error" + e);
        }
    }

    /**
     * 推送配置
     * @param configInfo
     */
    private void publishNacosConfig(String configInfo) {
        try {
            //配置被删除
            if (StringUtil.isNullOrEmpty(configInfo)) {
                return;
            }

            List<ResourceRoleRelationDto> resourceRoleRelationDtos = JSONObject.parseArray(configInfo, ResourceRoleRelationDto.class);
            for (ResourceRoleRelationDto resourceRoleRelationDto : resourceRoleRelationDtos) {
                resourceService.updateResourceRolesRelation(resourceRoleRelationDto.getRoute(),resourceRoleRelationDto.getRoles());
            }
        } catch (Exception e) {
            log.error("publishNacosConfig error", e);
        }
    }
}
