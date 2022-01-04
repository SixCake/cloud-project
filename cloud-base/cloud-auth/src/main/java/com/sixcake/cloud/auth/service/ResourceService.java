package com.sixcake.cloud.auth.service;

import java.util.List;

/**
 * @author sixCake 2021/12/28
 */
public interface ResourceService {

    void updateResourceRolesRelation(String resource, List<String> roles);

}
