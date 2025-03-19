package com.poly.sms.service.impl;

import com.poly.sms.entity.Employee;
import com.poly.sms.entity.EmployeeRole;
import com.poly.sms.entity.Role;
import com.poly.sms.repository.EmployeeRepository;
import com.poly.sms.repository.EmployeeRoleRepository;
import com.poly.sms.repository.RoleRepository;
import com.poly.sms.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<EmployeeRole> findAll() {
        return employeeRoleRepository.findAll();
    }

    @Override
    public Optional<EmployeeRole> findById(Integer id) {
        return employeeRoleRepository.findById(id);
    }

    @Override
    public EmployeeRole save(EmployeeRole employeeRole) {
        return employeeRoleRepository.save(employeeRole);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRoleRepository.deleteById(id);
    }

    @Override
    public List<EmployeeRole> findByEMploy(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        return employeeRoleRepository.findByEmployee(employee);
    }

    @Override
    public List<EmployeeRole> findByRole(String roleID) {
        Role role = roleRepository.findByRoleId(roleID);
        return employeeRoleRepository.findByRole(role);
    }
}
