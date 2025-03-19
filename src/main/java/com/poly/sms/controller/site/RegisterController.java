package com.poly.sms.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.entity.Employee;
import com.poly.sms.service.EmployeeService;

@Controller
@RequestMapping("sms")
public class RegisterController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "site/register";
    }
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("register")
    public String registerEmployee(@ModelAttribute Employee employee, BindingResult result, Model model) {

        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        // Bạn có thể thực hiện mã hóa mật khẩu ở đây trước khi lưu
        employeeService.save(employee);
        model.addAttribute("message", "Đăng ký thành công, Vui lòng đăng nhập");
        return "site/login";
    }
}
