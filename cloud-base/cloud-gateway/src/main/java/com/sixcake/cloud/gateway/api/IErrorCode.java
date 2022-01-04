package com.sixcake.cloud.gateway.api;

/**
 * 封装API的错误码
 *
 * @author sixCake 2021/12/28
 */
public interface IErrorCode {
  long getCode();

  String getMessage();
}
