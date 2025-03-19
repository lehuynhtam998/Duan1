package com.poly.sms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.Employee;
import com.poly.sms.repository.EmployeeRepository;
import com.poly.sms.repository.EmployeeRoleRepository;
import com.poly.sms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(String username) {
        return employeeRepository.findById(username);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(String username) {
        employeeRepository.deleteById(username);
    }

    @Override
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public List<Employee> getEmployeesByRole(String roleId) {
        return employeeRoleRepository.findEmployeesByRoleName(roleId);
    }


    @Override
    public void updateNew(Employee employee) {
        Employee existingEmployee = employeeRepository.findByUsername(employee.getUsername());
        if (existingEmployee != null) {
            // Giữ nguyên mật khẩu cũ
            employee.setPassword(existingEmployee.getPassword());
            employeeRepository.save(employee);
        }

    }
}
