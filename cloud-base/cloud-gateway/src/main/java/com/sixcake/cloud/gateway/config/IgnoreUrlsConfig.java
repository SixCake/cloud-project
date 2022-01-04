package com.sixcake.cloud.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网关白名单配置
 *
 * @author sixCake 2021/12/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
  private List<String> urls;
}
