package com.poly.sms.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.sms.entity.EmployeeRole;
import com.poly.sms.service.EmployeeRoleService;
import com.poly.sms.service.EmployeeService;
import com.poly.sms.service.RoleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/authorities")
public class EmployeeRoleController {
    @Autowired
    private EmployeeRoleService employeeRoleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public Map<String, Object> getAuthority() {
        Map<String, Object> data = new HashMap<>();
        data.put("authorities", employeeRoleService.findAll());
        data.put("roles", roleService.findAll());
        data.put("accounts", employeeService.findAll());
        return data;
    }

    @GetMapping("{username}")
    public List<EmployeeRole> getMethodName(@PathVariable String username) {
        return employeeRoleService.findByEMploy(username);
    }

    @GetMapping("role")
    public List<EmployeeRole> getManager() {
        return employeeRoleService.findByRole("MANAGER");
    }
    

    @PostMapping
    public EmployeeRole create(@RequestBody EmployeeRole employeeRole) {
        return employeeRoleService.save(employeeRole);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        employeeRoleService.deleteById(id);
    }

}
