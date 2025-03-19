package com.poly.sms.service;

import com.poly.sms.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findById(String id);

    Role save(Role role);

    void deleteById(String id);
}
