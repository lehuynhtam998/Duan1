package com.poly.sms.service;

import java.util.List;
import java.util.Optional;

import com.poly.sms.entity.Employee;

public interface EmployeeService {

    List<Employee> findAll();

    Optional<Employee> findById(String username);

    Employee findByUsername(String username);

    Employee save(Employee employee);

    void deleteById(String username);

    Employee findByEmail(String email);

    List<Employee> getEmployeesByRole(String roleId);

    void updateNew(Employee employee);
}
