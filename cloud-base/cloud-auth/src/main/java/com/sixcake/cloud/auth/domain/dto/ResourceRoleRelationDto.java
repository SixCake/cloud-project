package com.sixcake.cloud.auth.domain.dto;

import lombok.Data;

import java.util.List;


/**
 * 资源路径与权限映射关系
 */
@Data
public class ResourceRoleRelationDto {

    private String route;

    private List<String> roles;
}
