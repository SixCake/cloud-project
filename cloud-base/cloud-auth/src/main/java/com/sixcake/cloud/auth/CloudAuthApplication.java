package com.sixcake.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author sixCake 2021/12/28
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudAuthApplication.class, args);
  }

}
