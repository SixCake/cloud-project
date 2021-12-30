package com.sixcake.cloud.service.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author sixCake 2021/12/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class User {
  private Long id;
  private String username;
  private String password;
  private List<String> roles;

}
