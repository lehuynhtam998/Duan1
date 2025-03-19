/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.poly.sms.controller.site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.entity.Employee;
import com.poly.sms.service.EmployeeService;

@Controller
@RequestMapping("sms")

public class MyAccountController {

      @Autowired
    private EmployeeService employeeService;
    @GetMapping("myaccount")
    public String account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Employee employee = employeeService.findByUsername(username);
        if (employee != null) {
            model.addAttribute("employee", employee);
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin tài khoản.");
        }
        return "site/myaccount";
    }

    @PostMapping("/account/update")
    public String updateAccount(Employee employee, Model model) {
        employeeService.updateNew(employee);
        model.addAttribute("message", "Thông tin tài khoản đã được cập nhật thành công.");
        return "site/myaccount";
    }


}
