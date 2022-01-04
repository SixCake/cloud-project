package com.sixcake.auth.exception;

import com.sixcake.cloud.common.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局处理Oauth2抛出的异常
 *
 * @author sixCake 2021/12/28
 */
@ControllerAdvice
public class Oauth2ExceptionHandler {
  @ResponseBody
  @ExceptionHandler(value = OAuth2Exception.class)
  public CommonResult<String> handleOauth2(OAuth2Exception e) {
    return CommonResult.failed(e.getMessage());
  }
}
