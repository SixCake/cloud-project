package cn.gathub.resource.controller;


import com.sixcake.cloud.service.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import cn.gathub.resource.domain.User;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;

/**
 * 获取登录用户信息接口
 *
 * @author sixCake 2021/12/28
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @DubboReference(application = "${dubbo.application.id}", version = "1.0.0")
  private UserService userService;

  @GetMapping("/currentUser")
  public User currentUser(HttpServletRequest request) {
    // 从Header中获取用户信息
    String userStr = request.getHeader("user");
    JSONObject userJsonObject = new JSONObject(userStr);
    return User.builder()
        .username(userJsonObject.getStr("user_name"))
        .id(Convert.toLong(userJsonObject.get("id")))
        .roles(Convert.toList(String.class, userJsonObject.get("authorities"))).build();
  }


  @GetMapping("/say")
  public String say(@RequestParam("name") String name) {
    return userService.sayName(name);
  }
}
