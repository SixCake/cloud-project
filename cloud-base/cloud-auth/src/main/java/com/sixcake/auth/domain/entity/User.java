package com.sixcake.auth.domain.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author sixCake 2021/12/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
  private Long id;
  private String username;
  private String password;
  private Integer status;
  private List<String> roles;
}
