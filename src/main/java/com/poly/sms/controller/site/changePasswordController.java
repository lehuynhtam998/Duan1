/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.poly.sms.controller.site;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 
 import com.poly.sms.entity.Employee;
 import com.poly.sms.service.EmployeeService;
 
 /**
  *
  * @author hoang
  */
 
  
 @Controller
 @RequestMapping("sms")
 public class changePasswordController {
     
     @Autowired
     private PasswordEncoder passwordEncoder;
 
     @Autowired
     EmployeeService employeeService; // Repository để tìm kiếm người dùng
 
      @GetMapping("/reset-password")
     public String showResetPasswordForm(Model model) {
         model.addAttribute("employee", new Employee());
         return "site/changePassword";
     }
 
     @PostMapping("/reset-password")
     public String resetPassword(@RequestParam String username, 
                                 @RequestParam String currentPassword, 
                                 @RequestParam String newPassword, 
                                 @RequestParam String confirmNewPassword, 
                                 Model model) {
         Employee employee = employeeService.findByUsername(username);
         if (employee == null) {
             model.addAttribute("error", "Tên người dùng không tồn tại.");
             return "site/changePassword";
         }
         if (!passwordEncoder.matches(currentPassword, employee.getPassword())) {
             model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
             return "site/changePassword";
         }
         if (passwordEncoder.matches(newPassword, employee.getPassword())) {
             model.addAttribute("error", "Mật khẩu mới không được trùng với mật khẩu hiện tại.");
             return "site/changePassword";
         }
         if (!newPassword.equals(confirmNewPassword)) {
             model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
             return "site/changePassword"; 
         }
         employee.setPassword(passwordEncoder.encode(newPassword));
         employeeService.save(employee);
     
         // Thông báo thành công
         model.addAttribute("message", "Mật khẩu đã được thay đổi thành công, vui lòng đăng nhập.");
         return "site/login";
     }
     

 }
 