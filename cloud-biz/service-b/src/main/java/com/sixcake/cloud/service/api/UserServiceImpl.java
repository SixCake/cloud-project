package com.sixcake.cloud.service.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;

@DubboService(version = "${dubbo.service.version}")
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${dubbo.service.name}")
    private String serviceName;

    @Override
    public String sayName(String name) {
        RpcContext rpcContext = RpcContext.getContext();
        log.info("sayName call.{}", JSON.toJSONString(rpcContext));
        return String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s",
                serviceName,
                rpcContext.getLocalPort(),
                rpcContext.getMethodName(),
                name,
                name);
    }
}
