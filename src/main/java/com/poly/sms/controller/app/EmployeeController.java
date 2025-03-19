package com.poly.sms.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.sms.entity.Employee;
import com.poly.sms.service.EmployeeService;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/roleUser")
    public List<Employee> getEmployeesByRole() {
        return employeeService.getEmployeesByRole("USER");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("username") String username) {
        return employeeService.findById(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Employee savedEmployee = employeeService.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("username") String username,
            @RequestBody Employee employee) {
        if (!username.equals(employee.getUsername())) {
            return ResponseEntity.badRequest().build();
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Employee updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("username") String username) {
        employeeService.deleteById(username);
        return ResponseEntity.noContent().build();
    }
}
