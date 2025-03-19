package com.poly.sms.service;

import com.poly.sms.entity.EmployeeRole;

import java.util.List;
import java.util.Optional;

public interface EmployeeRoleService {

    List<EmployeeRole> findByEMploy(String username);

    List<EmployeeRole> findAll();

    Optional<EmployeeRole> findById(Integer id);

    EmployeeRole save(EmployeeRole employeeRole);

    void deleteById(Integer id);

    List<EmployeeRole> findByRole(String role);
}
