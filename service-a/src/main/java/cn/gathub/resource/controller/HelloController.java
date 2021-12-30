package cn.gathub.resource.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sixCake 2021/12/28
 */
@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World !";
  }

}
