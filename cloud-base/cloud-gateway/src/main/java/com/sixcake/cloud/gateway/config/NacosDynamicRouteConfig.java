package com.sixcake.cloud.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class NacosDynamicRouteConfig implements ApplicationEventPublisherAware {

    private String dataId = "gateway-router";

    private String group = "DEFAULT_GROUP";

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String config = configService.getConfig(dataId, group, 5000);
            log.info("NacosDynamicRouteConfig,{}", config);
            publishNacosConfig(config);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    clearRoute();
                    publishNacosConfig(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("dynamicRouteByNacosListener error" , e);
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
            List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                addRoute(routeDefinition);
            }
            publish();
        } catch (Exception e) {
            log.error("receiveConfigInfo error" , e);
        }
    }

    private void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            log.error("addRoute error" , e);
        }
    }

    /**
     * 更新路由
     * 只能是先删除在添加，由于没有提供更新路由的方法
     *
     * @param definition
     * @return
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "删除路由失败: RouteId:" + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "更新路由成功";
        } catch (Exception e) {
            return "更新路由失败";
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}
